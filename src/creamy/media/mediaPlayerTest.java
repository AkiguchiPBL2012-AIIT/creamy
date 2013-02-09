/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package creamy.media;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import creamy.media.CFMediaPlayer;
import javafx.scene.Scene;

/**
 *
 * @author tadao
 */
public class mediaPlayerTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Chapter 3-2 Playing Video");
        primaryStage.centerOnScreen();
        primaryStage.initStyle(StageStyle.TRANSPARENT);

        CFMediaPlayer mediaplayer = new CFMediaPlayer("/Users/tadao/NetBeansProjects/2012CA/trunk/creamy/oow2010-2.flv", 350.0, 600.0);
        primaryStage.setScene(mediaplayer.create());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
