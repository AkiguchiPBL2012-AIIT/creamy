package views.newdialogcontroller;

import creamy.browser.control.DefaultBrowserMenuBar;
import creamy.browser.control.DefaultHeader;
import creamy.browser.*;
import creamy.entrypoint.CreamyApp;
import javafx.stage.Stage;

/**
 *
 * @author miyabetaiji
 */
public class TestNewDialog extends CreamyApp {
  
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Test New Dialog - partial transition");
        
        //TabBrowser browser = new TabBrowser("/Application/index");
        TabBrowser browser = new TabBrowser("/TestEditableController/testEditableIndex");
        DefaultBrowserMenuBar menubar = new DefaultBrowserMenuBar();
        menubar.setUseSystemMenuBar(true);
        browser.setMenuBar(menubar);
        browser.setHeader(new DefaultHeader());
        
        primaryStage.setScene(browser);
        primaryStage.show();   
    }
}
