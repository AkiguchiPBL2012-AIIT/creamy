package creamy.browser.control;

import creamy.browser.control.AddressBar;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

/**
 * Headerのデフォルト実装
 * DefaultBackButton, DefaultForwardButton, AddressBarを持つ
 *
 * @author Taiji Miyabe (Professor Akiguchi's PBL 2012, AIIT)
 */
public class DefaultHeader extends StackPane {
    /**
     * DefaultHeaderを生成する
     */
    public DefaultHeader() {
        BorderPane rootPane = new BorderPane();
        rootPane.setStyle("-fx-background-color: mintcream;");
        
        // back,forward buttons
        HBox hbox = new HBox();
        hbox.setSpacing(3);
        hbox.getChildren().addAll(new DefaultBackButton(), new DefaultForwardButton());
        rootPane.setLeft(hbox);
        BorderPane.setMargin(hbox, new Insets(7,5,5,5));
        BorderPane.setAlignment(hbox, Pos.CENTER);
        
        // address bar
        AddressBar addressBar = new AddressBar();
        BorderPane.setMargin(addressBar, new Insets(7,5,5,5));
        BorderPane.setAlignment(addressBar, Pos.CENTER);
        rootPane.setCenter(addressBar);
        
        /*
        // bookmark button
        rootPane.setRight(bookmarkButton);
        BorderPane.setMargin(bookmarkButton, new Insets(5,5,5,5));
        BorderPane.setAlignment(bookmarkButton, Pos.CENTER);        
        */
        getChildren().add(rootPane);
    }
    
    /*
    protected final void buildBookmarkButton() {
        bookmarkButton = new Button();
        Image image = new Image(getClass().getResourceAsStream("bookmark.png"));
        bookmarkButton.setGraphic(new ImageView(image));
        bookmarkButton.setStyle("-fx-background-color: mintcream;");
        
        bookmarkButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                String title = transitionManager.getCurrentTitle();
                String path  = transitionManager.getCurrentPath();
                
                (new Bookmark(title, path)).save();
            }
        });
    }
    * 
    */
}
