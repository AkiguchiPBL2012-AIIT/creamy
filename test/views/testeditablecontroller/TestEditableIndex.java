package views.testeditablecontroller;

import creamy.activity.Activity;
import creamy.activity.AvailableActivity;
import creamy.activity.requestor.CallBack;
import creamy.annotation.Template;
import creamy.mvc.Status;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import views.application.Main;

/**
 *
 * @author miyabetaiji
 */
@Template(Main.class)
public class TestEditableIndex extends AvailableActivity {
    @FXML private AnchorPane listArea;
    
    @FXML private void refresh(ActionEvent event) {
        requestActivity("/TestEditableController/testEditableList/0/name/asc")
                .onSuccess(new CallBack<Activity>() {
                    @Override
                    public void call(Activity activity, Status status) {
                        listArea.getChildren().clear();
                        listArea.getChildren().add(activity.getScene());
                    }
                })
                .execute();
    }
/*    
    // 検索ダイアログ
    private Stage stage;
    // 新規コンピュータ作成ダイアログ
    private Stage newStage;
*/
    
    @FXML private void search(ActionEvent event) {
        
        // 検索ダイアログを表示して、Searchなら続行、Cancelなら中断
        Activity dialog = createWindow("/TestEditableController/search", Modality.NONE);
/*
        Browser browser = new Browser("/TestEditableController/search");
        stage = new Stage();
        stage.setTitle("検索");
        stage.setScene(browser);
        Search search = (Search)browser.getBrowserPanel()
                .getTransitionManager().getCurrentResponse().getActivity();
        
        // owner Windowセット
        stage.initOwner(this.getScene().getParent().getScene().getWindow());
        
        // activity ownerセット
        search.setOwner(this);
 
        stage.show();
*/
    }

    /**
     * Searhボタンアクション.
     * 新規コンピュータ登録、検索処理の結果を元画面に反映させる処理。
     * 
     * @param data 再表示のためのパラメータMap
     */
    public void searchAction(Map<String,Object> data) {
        
        requestActivity("/TestEditableController/testEditableList/0/name/asc")
                .params(data)
                .onSuccess(new CallBack<Activity>() {
                    @Override
                    public void call(Activity activity, Status status) {
                        // 検索結果を反映したアクティビティに置き換える。
                        listArea.getChildren().clear();
                        listArea.getChildren().add(activity.getScene());
                    }
                })
                .execute();
    }
    
    @FXML private void newComputer(ActionEvent event) {
        
        // 新規ダイアログを表示
        Activity dialog = createWindow("/NewDialogController/newDialog", Modality.APPLICATION_MODAL);
/*
        Browser browser = new Browser("/NewDialogController/newDialog");
        newStage = new Stage();
        newStage.initModality(Modality.APPLICATION_MODAL);
        // owner Windowセット
        newStage.initOwner(this.getScene().getParent().getScene().getWindow());

        newStage.setScene(browser);
        NewDialog dialog = (NewDialog)browser.getBrowserPanel()
                .getTransitionManager().getCurrentResponse().getActivity();
        
        // activity ownerセット
        dialog.setOwner(this);
 
        newStage.show();
*/
    }
}
