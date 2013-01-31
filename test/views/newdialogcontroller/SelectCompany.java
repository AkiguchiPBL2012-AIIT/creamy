package views.newdialogcontroller;

import creamy.activity.AvailableActivity;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import models.Company;

/**
 * メーカ選択用アクティビティ.
 * 部分書き換えするため、@Template は使わない。
 * 
 * @author ahayama
 */

//@Template(NewDialog.class)
public class SelectCompany extends AvailableActivity {
    
    @FXML private ListView<String> companyList;
    
    /**
     * メーカ名をListViewにセット.
     */
    @Override
    public void initialize() {
        companyList.getItems().setAll(Company.options().values());
    }    
    
    /**
     * 選択されているメーカ名からIDを取得する.
     * @return 
     */
    protected Integer getSelectedId() {
        String value = (String)companyList.getSelectionModel().getSelectedItem();
        if (value == null) return null;
        for(Company c: Company.find.findList()) {
            if (c.getName().equals(value)) {
                return c.getId();
            }
        }
        return null;
    }
    /**
     * 選択されているメーカ名を取得する.
     * @return 
     */
    protected String getSelectedName() {
        return (String)companyList.getSelectionModel().getSelectedItem();
    }
}
