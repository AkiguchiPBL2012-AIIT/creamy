package creamy.browser.control;

import creamy.browser.control.BackButton;
import creamy.mvc.Response;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * BackButtonのデフォルト実装
 *
 * @author Taiji Miyabe (Professor Akiguchi's PBL 2012, AIIT)
 */
public class DefaultBackButton extends BackButton {

    /**
     * DefaultBackButtonを生成する
     */
    public DefaultBackButton() {
        Image image = new Image(getClass().getResourceAsStream("back-button.png"));
        setGraphic(new ImageView(image));
        getStyleClass().add("history-button");
        setWidth(0.0);
        setStyle("-fx-background-color: mintcream;");
    }
    
    @Override
    protected void onBackHistoryCleared() {
        setDisable(true);
    }

    @Override
    protected void onBackHistoryChanged(ObservableList<? extends Response> backHistory) {
        setDisable(false);
    }
}
