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
        primaryStage.setTitle("media player Test");
        primaryStage.centerOnScreen();
        primaryStage.initStyle(StageStyle.TRANSPARENT);

        String uri = "http://download.oracle.com/otndocs/products/javafx/oow2010-2.flv";
        //String uri = "/Users/tadao/Downloads/oow2010-2.flv";
        CFMediaPlayer mediaplayer = new CFMediaPlayer(uri, 600.0, 300.0);
        primaryStage.setScene(mediaplayer.create());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

