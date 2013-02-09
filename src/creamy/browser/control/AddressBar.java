package creamy.browser.control;

import creamy.browser.RequestHelper;
import creamy.mvc.Request;
import creamy.scene.control.UnitRequest;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;

/**
 * Browserに表示するアドレスバー。<br/>
 * パスの入力による遷移、ページ遷移に伴うテキストの変更を行う
 *
 * @author Taiji Miyabe (Professor Akiguchi's PBL 2012, AIIT)
 */
public class AddressBar extends TextField implements UnitRequest {
    /**
     * RequestHelper
     */
    private RequestHelper<AddressBar> helper;
    /**
     * メソッド
     */
    private String method;
    /**
     * パス
     */
    private String path;
    
    /**
     * AddressBarを生成する
     */
    public AddressBar() {
        helper = new RequestHelper<AddressBar>(this);
        method = Request.GET;
        path = null;
        
        this.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                path = AddressBar.this.getText();
                helper.getBroker().sendRequest(AddressBar.this);
            }
        });
    }
    
    /**
     * Methodを取得する
     * @return メソッド
     */
    @Override
    public String getMethod() { return method; }

    /**
     * Pathを取得する
     * @return パス
     */
    @Override
    public String getPath() { return path; }
}
