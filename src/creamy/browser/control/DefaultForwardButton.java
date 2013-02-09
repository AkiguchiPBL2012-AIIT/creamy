package creamy.browser.control;

import creamy.mvc.Response;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * ForwardButtonのデフォルト実装
 *
 * @author Taiji Miyabe (Professor Akiguchi's PBL 2012, AIIT)
 */
public class DefaultForwardButton extends ForwardButton {

    public DefaultForwardButton() {
        Image image = new Image(getClass().getResourceAsStream("forward-button.png"));
        setGraphic(new ImageView(image));
        setStyle("-fx-graphic-text-gap: 0;");
        setStyle("-fx-background-color: mintcream;");
    }
    
    @Override
    protected void onForwardHistoryCleared() {
        setDisable(true);
    }

    @Override
    protected void onForwardHistoryChanged(ObservableList<? extends Response> history) {
        setDisable(false);
    }
}
