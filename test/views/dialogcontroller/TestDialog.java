package views.dialogcontroller;

import creamy.activity.Activity;
import creamy.browser.control.DefaultBrowserMenuBar;
import creamy.browser.control.DefaultHeader;
import creamy.browser.*;
import creamy.entrypoint.CreamyApp;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author miyabetaiji
 */
public class TestDialog extends CreamyApp {
  
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Test New Dialog - page transition");
        
        final TabBrowser browser = new TabBrowser("/TestEditableController/testEditableIndex");
        DefaultBrowserMenuBar menubar = new DefaultBrowserMenuBar();
        menubar.setUseSystemMenuBar(true);
        browser.setMenuBar(menubar);
        browser.setHeader(new DefaultHeader());
        primaryStage.setScene(browser);
        
        // メニューからダイアログを表示する
        MenuItem item = new MenuItem("コンピュータを登録...");
        item.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                // 新規ウィンドウを生成
                Activity window = Activity.createWindow(
                        browser, "/DialogController/create", Modality.APPLICATION_MODAL);
/*
                Browser newDialog = new Browser("/DialogController/create");
                Stage newStage = new Stage();
                newStage.setTitle("Create Computer");
                newStage.initModality(Modality.APPLICATION_MODAL);
                newStage.setScene(newDialog);
                
                // コンピュータ追加後の再表示のために、activityのownerをセット
                DialogBase activity = (DialogBase)newDialog.getBrowserPanel()
                        .getTransitionManager().getCurrentResponse().getActivity();
                
                // Mainが返ってくる => TestEditableIndexを返せないか？
                Main owner = (Main)browser.getBrowserPanel()
                        .getTransitionManager().getCurrentResponse().getActivity();
                activity.setOwner(owner);
                
                // 新規ダイアログを表示
                newStage.show();
*/
            }
        });
        // 'コンピュータを登録...' メニューを追加
        menubar.getMenus().get(0).getItems().add(0, item);
        
        primaryStage.show();   
    }
}
