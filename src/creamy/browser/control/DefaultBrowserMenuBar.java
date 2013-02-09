package creamy.browser.control;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBuilder;
import javafx.scene.control.MenuItemBuilder;
import javafx.stage.Stage;

/**
 * MenuBarのデフォルト実装。<br/>
 * BrowserのCloseメニューだけを持つ
 *
 * @author Taiji Miyabe (Professor Akiguchi's PBL 2012, AIIT)
 */
public class DefaultBrowserMenuBar extends BrowserMenuBar {
    /**
     * DefaultBrowserMenuBarを生成する
     */
    public DefaultBrowserMenuBar() {
        getMenus().add(buildFileMenu());
    }
    
    private Menu buildFileMenu() {
        Menu menu = MenuBuilder.create()
            .items(
                MenuItemBuilder.create()
                    .text("close")
                    .onAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent t) {
                            ((Stage)DefaultBrowserMenuBar.this.getScene().getWindow()).close();
                        }
                    })
                    .build()
            )
            .build();
        menu.setText("File");
        return menu;
    }
}
