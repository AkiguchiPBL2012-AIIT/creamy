package views.dialogcontroller;

import creamy.activity.Activity;
import creamy.activity.AvailableActivity;
import creamy.scene.layout.CFVForm;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * 新コンピュータ登録画面.
 * 小画面として生成され、全画面書き換え方法で画面遷移を行う。<br>
 * ChildPaneに、Create, Select, Confirmアクティビティを順次切り替えて表示する。
 * @author ahayama
 * @param <T> 
 */
public class DialogBase<T> extends AvailableActivity {

    @FXML private CFVForm dialogForm;
    // 何番目のステップかを示すラベル
    @FXML private Label step1Label;
    @FXML private Label step2Label;
    @FXML private Label step3Label;
    // 次へボタン
    @FXML private Button nextButton;
    // 前へボタン
    @FXML private DialogBackButton prevButton;
    // 登録ボタン
    @FXML private Button finishButton;
    // 取消しボタン
    @FXML private Button cancelButton;

    // 現在表示中の画面番号
    enum Status { FIRST_WINDOW, SECOND_WINDOW, THIRD_WINDOW; }

    // Label, Buttonの状態、pathを変更する
    protected void setStatus(Status status) {
        if (status == Status.FIRST_WINDOW) {        
            // pathを書き換えて遷移先を指定する
            dialogForm.setPath("/DialogController/select");
            // Labelの状態を変更する
            step1Label.setFont(Font.font("Arial", FontWeight.BOLD, 16));
            step2Label.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
            step3Label.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
            // Buttonの状態を変更する
            //prevButton.setDisable(true);
            nextButton.setDisable(false);
            finishButton.setDisable(true);
        }
        else if (status == Status.SECOND_WINDOW) {
            // pathを書き換えて遷移先を指定する
            dialogForm.setPath("/DialogController/createComputer");
             // Labelの状態を変更する
            step1Label.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
            step2Label.setFont(Font.font("Arial", FontWeight.BOLD, 16));
            step3Label.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
            // Buttonの状態を変更する
            //prevButton.setDisable(false);
            nextButton.setDisable(true);
            finishButton.setDisable(false);
       }
        else if (status == Status.THIRD_WINDOW) {
            // pathを書き換えて遷移先を指定する
            dialogForm.setPath("/DialogController/confirm");
             // Labelの状態を変更する
            step1Label.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
            step2Label.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
            step3Label.setFont(Font.font("Arial", FontWeight.BOLD, 16));
            // Buttonの状態を変更する
            prevButton.setVisible(false);
            nextButton.setVisible(false);
            finishButton.setVisible(false);
            cancelButton.setText("終　了");
       }
    }
    
    /*
     * 取消し　ボタンアクション.
     */
    @FXML private void handleCancelAction(ActionEvent event) {
        ((Stage)this.scene.getScene().getWindow()).close();
    }
    
    // ownerを呼び出す場合
    private Activity owner;
    
    public void setOwner(Activity activity) {
        owner = activity;
    }

}
