package views.computercontroller;

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
    
    // 検索ボタンクリック
    @FXML private void handleSearchAction(ActionEvent event) {
        Map<String,Object> params = new HashMap<String,Object>() {{
           put("computer", computerName.getText());
           put("company", companyName.getText());
        }};
        //((List)getOwner()).searchAction(params);
    }
    // キャンセルボタンクリック
    @FXML private void handleCancelAction(ActionEvent event) {
        ((Stage)this.scene.getScene().getWindow()).close();
    }
    
    // ownerアクティビティ
    private List owner;
    
    public void setOwner(List activity) {
        owner = activity;
    }

}
