package views.newdialogcontroller;

import creamy.activity.AvailableActivity;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * コンピュータデータ登録用アクティビティ.
 * 部分書き換えするため、@Template は使わない。
 * 
 * @author ahayama
 */

//@Template(NewDialog.class)
public class NewComputer extends AvailableActivity {
    
    @FXML private TextField name;
    @FXML private TextField intoroduced;
    @FXML private TextField discontinued;
    
    /**
     * @return コンピュータ名 String
     */
    protected String getName() {
        return name.getText();
    }
    /**
     * @return 発表日 String
     */
    protected String getIntoroduced() {
        return intoroduced.getText();
    }
    /**
     * @return 製造中止日 String
     */
    protected String getDiscontinued() {
        return discontinued.getText();
    }
}
