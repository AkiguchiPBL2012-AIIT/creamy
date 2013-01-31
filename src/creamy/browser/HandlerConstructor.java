package creamy.browser;

import creamy.utils.FinderUtil;
import creamy.activity.requestor.RequestCancel;
import creamy.scene.control.CFHyperlink;
import creamy.scene.control.CFLinkButton;
import creamy.scene.control.CFSubmitButton;
import creamy.scene.control.UnitRequest;
import java.util.HashSet;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ButtonBase;

/**
 * 遷移に係る以下コントロールのHandlerを作成、登録するためのクラス
 *
 * @author miyabetaiji
 */
class HandlerConstructor {
    private Broker broker;

    HandlerConstructor(Broker broker) {
        this.broker = broker;
    }

    void constructHandler(Node root) {
        constructSubmitHandler(root);
        constructLinkHandler(root);
    }

    private void constructSubmitHandler(Node root) {
        Set<CFSubmitButton> buttons = FinderUtil.lookupAll(root, CFSubmitButton.class);
        for (final CFSubmitButton button : buttons) {
            final EventHandler<ActionEvent> userHandler = button.getOnAction();
            if (userHandler != null) {
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            userHandler.handle(event);
                            broker.sendRequest(button);
                        } catch (RequestCancel ex) {}
                    }
                });
            } else {
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        broker.sendRequest(button);
                    }
                });
            }
        }
    }
    
    private void constructLinkHandler(Node root) {
        Set<ButtonBase> controls = new HashSet<ButtonBase>();
        controls.addAll(FinderUtil.lookupAll(root, CFHyperlink.class));
        controls.addAll(FinderUtil.lookupAll(root, CFLinkButton.class));
        
        for (final ButtonBase control : controls) {
            final EventHandler<ActionEvent> userHandler = control.getOnAction();
            if (userHandler != null) {
                control.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            userHandler.handle(event);
                            broker.sendRequest((UnitRequest)control);
                        } catch (RequestCancel ex) {}
                    }
                });
            } else {
                control.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        broker.sendRequest((UnitRequest)control);
                    }
                });
            }
        }
    }
}
