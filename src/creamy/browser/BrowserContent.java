package creamy.browser;

import creamy.utils.FinderUtil;
import creamy.browser.control.ForwardButton;
import creamy.browser.control.AddressBar;
import creamy.browser.control.BackButton;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 * ユーザコンテンツの表示領域
 * ユーザコンテンツが配置されるタイミングで、Browser機能を利用する
 * 以下コントロールのEventHandlerを登録することで機能を有効化する
 * - BackButton, ForwardButton, AddressBar
 * @see BackButton
 * @see ForwardButton
 * @see AddressBar
 * 
 * @author Taiji Miyabe (Professor Akiguchi's PBL 2012, AIIT)
 */
public class BrowserContent extends StackPane {
    /**
     * ページ遷移管理
     */
    protected TransitionManager manager;
    /**
     * ユーザコンテンツ
     */
    protected Pane content;

    /**
     * BrowserContentを生成する
     * @param manager TransitionManager
     */
    public BrowserContent(TransitionManager manager) {
        this.manager = manager;
        this.setAlignment(Pos.TOP_LEFT);
    }

    /**
     * ユーザコンテンツを取得する
     * @return ユーザコンテンツ
     */
    public Pane getContent() { return content; }

    /**
     * ユーザコンテンツを配置する。コンテンツが更新された際にコールバックされる
     * Browser機能を利用するにコントロールを探索し、EventHandlerを登録する
     * @param content ユーザコンテンツ
     */
    public void setContent(Pane content) {
        registerBrowserFunction(content);
        replaceContent(content);
    }

    /**
     * ユーザコンテンツを置き換える
     * @param content ユーザコンテンツ
     */
    protected void replaceContent(Pane content) {
        getChildren().clear();
        getChildren().add(this.content = content);
    }
    
    /**
     * Browser機能を利用するにコントロールを探索し、EventHandlerを登録する
     * @param content ユーザコンテンツ
     */
    protected void registerBrowserFunction(Pane content) {
        registerBack(content);
        registerForward(content);
        regsiterAddress(content);
    }

    private void registerBack(Pane content) {
        Set<BackButton> buttons = FinderUtil.lookupAll(content, BackButton.class);
        if (buttons.isEmpty()) return;
        for (BackButton button : buttons) {
            // Listener登録
            manager.addBackListener(button);
            if (manager.backHistoryIsEmpty())
                button.setDisable(true);
            else
                button.setDisable(false);
            // Handler登録
            button.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    manager.goBack();
                }
            });
        }
    }

    private void registerForward(Pane content) {
        Set<ForwardButton> buttons = FinderUtil.lookupAll(content, ForwardButton.class);
        if (buttons.isEmpty()) return;
        for (ForwardButton button : buttons) {
            // Listener登録
            manager.addForwardListener(button);
            if (manager.forwardHistoryIsEmpty())
                button.setDisable(true);
            else
                button.setDisable(false);
            // Handler登録
            button.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    manager.goForward();
                }
            });
        }
    }
    
    private void regsiterAddress(Pane content) {
        Set<AddressBar> addrs = FinderUtil.lookupAll(content, AddressBar.class);
        if (addrs.isEmpty()) return;
        for (AddressBar addr : addrs)
            addr.textProperty().bindBidirectional(manager.currentPathProperty());
    }
}
