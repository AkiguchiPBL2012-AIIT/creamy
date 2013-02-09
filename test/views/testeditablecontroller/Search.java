package views.testeditablecontroller;

import creamy.activity.AvailableActivity;
import java.util.HashMap;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * 検索ダイアログ.
 * @author ahayama
 */
public class Search<T> extends AvailableActivity {
    
    @FXML private TextField computerName;
    @FXML private TextField companyName;
    @FXML private TextField introducedFrom;
    @FXML private TextField introducedTo;
    
    // 検索ボタンクリック
    @FXML private void handleSearchAction(ActionEvent event) {
        Map<String,Object> params = new HashMap<String,Object>() {{
           put("computer", computerName.getText());
           put("company", companyName.getText());
           put("from", introducedFrom.getText());
           put("to", introducedTo.getText());
        }};
        ((TestEditableIndex)ownerActivity).searchAction(params);
    }
    // キャンセルボタンクリック
    @FXML private void handleCancelAction(ActionEvent event) {
        ((Stage)this.scene.getScene().getWindow()).close();
    }
    
    // ownerアクティビティ
    private TestEditableIndex owner;
    
    public void setOwner(TestEditableIndex activity) {
        owner = activity;
    }

}
