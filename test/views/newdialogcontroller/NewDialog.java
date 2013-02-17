package views.newdialogcontroller;

import creamy.activity.Activity;
import creamy.activity.AvailableActivity;
import creamy.activity.requestor.CallBack;
import creamy.mvc.Status;
import creamy.scene.layout.ChildPane;
import java.util.HashMap;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import views.testeditablecontroller.TestEditableIndex;

/**
 * 小画面アクティビティ.
 * 部分書き換え方法で実装
 * @author ahayama
 * @param <T> 
 */
public class NewDialog<T> extends AvailableActivity {

    // アクティビティを書き換える領域
    @FXML private ChildPane inputArea;
    // 画面遷移のステップ表示ラベル
    @FXML private Label step1Label;
    @FXML private Label step2Label;
    // 次へボタン
    @FXML private Button nextButton;
    // 前へボタン
    @FXML private Button prevButton;
    // 完了ボタン
    @FXML private Button finishButton;
    
    // Company選択画面へインスタンス
    private SelectCompany company;
    // Computer情報入力画面のインスタンス
    private NewComputer computer;

    /**
     * ラベル、ボタンの初期状態を設定.
     * 画面遷移によるステップラベルのフォントを変更する。<br>
     * 前へ、次へ　ボタンのイベントハンドラをセットする。<br>
     * 初期状態ではNewComputerアクティビティを表示するため、
     * NewDialog.vm.fxmlのChildPaneタグに、<!--% #render("NewComputer") -->
     * を記述している。
     */
@Override
public void initialize() {
    // ラベル、ボタンの初期状態を設定
    step1Label.getStyleClass().add("label-current");
    step2Label.getStyleClass().add("label-past");
    prevButton.setDisable(true);
    finishButton.setDisable(true);

    // 前へボタンのイベントハンドラをセット
    prevButton.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent t) {
            // ステップ１ラベルを太字に変更
            step1Label.getStyleClass().clear();
            step1Label.getStyleClass().add("label-current");
            step2Label.getStyleClass().clear();
            step2Label.getStyleClass().add("label-past");
            // 次へボタンを選択可に変更
            prevButton.setDisable(true);
            nextButton.setDisable(false);
            finishButton.setDisable(true);
        }
    });
    // 次へボタンのイベントハンドラをセット
    nextButton.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent t) {
            // ステップ２ラベルを太字に変更
            step1Label.getStyleClass().clear();
            step1Label.getStyleClass().add("label-past");
            step2Label.getStyleClass().clear();
            step2Label.getStyleClass().add("label-current");
            // 前へ、完了ボタンを選択可に変更
            prevButton.setDisable(false);
            nextButton.setDisable(true);
            finishButton.setDisable(false);
        }
    });
}

    /*
     * 前へ　ボタンアクション.
     * 遷移先のアクティビティをinputAreaに置き換える。
     */
    @FXML private void handlePrevAction(ActionEvent event) {
        // 次に遷移するパス
        String path = "/NewDialogController/newComputer";
        
        requestActivity(path)
                .onSuccess(new CallBack<Activity>() {
                    @Override
                    public void call(Activity activity, Status status) {
                        // 検索結果を反映したアクティビティに置き換える。
                        inputArea.getChildren().clear();
                        inputArea.getChildren().add(activity.getScene());
                    }
                })
                .execute();
    }
    
    /*
     * 次へ　ボタンアクション.
     * 遷移先のアクティビティをinputAreaに置き換える。<br>
     * ステップ１画面、ステップ２画面のそれぞれで入力されたデータを取得するため、
     * それぞれのインスタンスを保持する。
     */
@FXML private void handleNextAction(ActionEvent event) {
    // NewComputerアクティビティを保持
    computer = (NewComputer) this.getChildActivities(NewComputer.class).get(0);

    // 次に遷移するパス
    String path = "/NewDialogController/selectCompany";

    requestActivity(path)
            .onSuccess(new CallBack<Activity>() {
                @Override
                public void call(Activity activity, Status status) {
                    // 検索結果を反映したアクティビティに置き換える。
                    inputArea.getChildren().clear();
                    inputArea.getChildren().add(activity.getScene());

                    // SelectComputerアクティビティを保持
                    company = (SelectCompany) activity;
                }
            })
            .execute();
}
    
    /*
     * 取消し　ボタンアクション.
     * 自ダイアログをクローズする。
     */
    @FXML private void handleCancelAction(ActionEvent event) {
        //cancelCallBack.call(null, null);
        // 自身をクローズする
        ((Stage)this.scene.getScene().getWindow()).close();
    }
    
    /*
     * 完了　ボタンアクション.
     * ステップ１画面、ステップ２画面で入力されたデータを登録する。<br>
     * 登録完了後は、親アクティビティで登録内容を表示させるため、
     * 検索条件を追加して親アクティビティの検索メソッドを実行する。
     */
    @FXML private void handleCreateAction(ActionEvent event) {
        // 入力されたデータをセットする
        final Map<String,Object> params = new HashMap<String,Object>() {{
           put("computer", computer.getName());
           put("companyId", company.getSelectedId());
           put("company", company.getSelectedName());
           put("introduced", computer.getIntoroduced());
           put("discontinued", computer.getDiscontinued());
        }};
        
        requestData("/NewDialogController/createComputer")
                .params(params)
                .onSuccess(new CallBack<Object>() {
                    @Override
                    public void call(Object data, Status status) {
                        //createCallBack.call(params, null);
                        
                        // 登録内容を表示するように、検索条件を追加
                        params.put("from", computer.getIntoroduced());
                        params.put("to", computer.getIntoroduced());
                        // 検索実行
                        ((TestEditableIndex)ownerActivity).searchAction(params);
                    }      
                })
                .execute();
        
        // 自身をクローズする
        ((Stage)this.scene.getScene().getWindow()).close();
    }
    
}
