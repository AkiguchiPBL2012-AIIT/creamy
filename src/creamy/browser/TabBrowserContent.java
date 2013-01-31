package creamy.browser;

import creamy.utils.FinderUtil;
import creamy.scene.control.CFHyperlink;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItemBuilder;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * ユーザコンテンツの表示領域
 * ユーザコンテンツが配置されるタイミングで、Browser機能を利用する
 * 以下コントロールのEventHandlerを登録することで機能を有効化する
 * - BackButton, ForwardButton, AddressBar, Link
 * @see BackButton
 * @see ForwardButton
 * @see AddressBar
 * @see CFHyperLink
 * 
 * @author Taiji Miyabe (Professor Akiguchi's PBL 2012, AIIT)
 */
public class TabBrowserContent extends BrowserContent {
    /**
     * TabBrowserPanel(ノードツリーの親要素)
     */
    private TabBrowserPanel panel;
    
    /**
     * TabBrowserContentを生成する
     * @param manager TransitionManager
     * @param panel TabBrowserPanel
     */
    TabBrowserContent(TransitionManager manager, TabBrowserPanel panel) {
        super(manager);
        this.panel = panel;
    }

    /**
     * Browser機能を利用するにコントロールを探索し、EventHandlerを登録する
     * @param content ユーザコンテンツ
     */
    @Override
    protected void registerBrowserFunction(Pane content) {
        super.registerBrowserFunction(content);
        registerLink(content);
    }

    private void registerLink(Pane content) {
        Set<CFHyperlink> links = FinderUtil.lookupAll(content, CFHyperlink.class);
        if (links.isEmpty()) return;
        for (final CFHyperlink link : links) {
            final RightMenu rightMenu = new RightMenu(link);
            link.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getButton() == MouseButton.SECONDARY)
                        rightMenu.show(link, event.getScreenX(), event.getScreenY());
                }
            });
        }
    }
    
    private class RightMenu extends ContextMenu {
        private RightMenu(final CFHyperlink link) {
            getItems().addAll(
                MenuItemBuilder.create()
                    .text("Open in new tab")
                    .onAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent t) {
                            TabBrowserPanel newPanel = panel.getBrowser().createBrowserPanel();
                            newPanel.getBroker().sendRequest(link);
                        }
                    })
                    .build()
            );
        }
    }
}
