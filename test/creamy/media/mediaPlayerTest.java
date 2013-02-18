/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package creamy.media;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

/**
 *
 * @author tadao
 */
public class mediaPlayerTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        //primaryStage.setTitle("media player Test");

        Group root = new Group();
        Scene scene = new Scene(root, 600, 300, Color.rgb(100, 100, 100, 0));
        String uri = "http://download.oracle.com/otndocs/products/javafx/oow2010-2.flv";
        //String uri = "/Users/tadao/Downloads/oow2010-2.flv";
        CFMediaPlayer mediaPlayer = new CFMediaPlayer(uri);
        mediaPlayer.setRepeat(true);
        mediaPlayer.setMediaViewHeight(200.0);
        mediaPlayer.setMediaViewWidth(400.0);
        mediaPlayer.create();
        root.getChildren().add(mediaPlayer);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

