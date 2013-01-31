/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package creamy.media;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.control.SliderBuilder;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaErrorEvent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayerBuilder;
import javafx.scene.media.MediaView;
import javafx.scene.media.MediaViewBuilder;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcBuilder;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CircleBuilder;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineBuilder;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

/**
 *
 * @author tadao
 */
public class CFMediaPlayer {

    private MediaPlayer mediaPlayer;
    private MediaView mediaView;
    private Point2D anchorPt;
    private Point2D previousLocation;
    private Slider progressSlider;
    private ChangeListener<Duration> progressListener;
    private boolean poused = false;
    private String mediaFile;
    private Double height;
    private Double width;

    CFMediaPlayer(String mediaFile, Double height, Double width) {
        this.mediaFile = mediaFile;
        this.height = height;
        this.width = width;
    }

    public Scene create() {
        final Group root = new Group();
        final Scene scene = new Scene(root, height, width, Color.rgb(0, 0, 0, 0));
        Node applicationArea = createBackground(scene);
        root.getChildren().add(applicationArea);

        progressSlider = createSlider(scene);
        root.getChildren().add(progressSlider);

        progressListener = new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                progressSlider.setValue(newValue.toSeconds());
            }
        };

        final File file = new File(mediaFile);
        Media media = new Media(file.toURI().toString());

        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.currentTimeProperty().removeListener(progressListener);
            mediaPlayer.setOnPaused(null);
            mediaPlayer.setOnPlaying(null);
            mediaPlayer.setOnReady(null);
        }

        mediaPlayer = MediaPlayerBuilder.create()
                .media(media)
                .build();

        mediaPlayer.currentTimeProperty().addListener(progressListener);

        mediaPlayer.setOnReady(new Runnable() {
            @Override
            public void run() {
                progressSlider.setValue(1);
                progressSlider.setMax(mediaPlayer.getMedia().getDuration().toMillis() / 1000);
                mediaPlayer.play();
            }
        });
        if (mediaView == null) {
            mediaView = MediaViewBuilder.create()
                    .mediaPlayer(mediaPlayer)
                    .x(4)
                    .y(4)
                    .preserveRatio(true)
                    .opacity(.85)
                    .smooth(true)
                    .build();

            mediaView.fitWidthProperty().bind(scene.widthProperty().subtract(30));
            mediaView.fitHeightProperty().bind(scene.heightProperty().subtract(20));

            root.getChildren().add(1, mediaView);
        }
        mediaView.setOnError(new EventHandler<MediaErrorEvent>() {
            @Override
            public void handle(MediaErrorEvent event) {
                event.getMediaError().printStackTrace();
            }
        });
        mediaView.setMediaPlayer(mediaPlayer);

        final Group buttonArea = createButtonArea(scene);

        Node stopButton = createStopControl();

        final Node playButton = createPlayControl();

        final Node pauseButton = createPauseControl();

        stopButton.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent evnet) {
                if (mediaPlayer != null) {
                    buttonArea.getChildren().removeAll(pauseButton, playButton);
                    buttonArea.getChildren().add(playButton);
                    mediaPlayer.stop();
                }
            }
        });

        pauseButton.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent evnet) {
                if (mediaPlayer != null) {
                    buttonArea.getChildren().removeAll(pauseButton, playButton);
                    buttonArea.getChildren().add(playButton);
                    mediaPlayer.pause();
                    poused = true;
                }
            }
        });

        playButton.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent evnet) {
                if (mediaPlayer != null) {
                    buttonArea.getChildren().removeAll(pauseButton, playButton);
                    buttonArea.getChildren().add(pauseButton);
                    poused = false;
                    mediaPlayer.play();
                }
            }
        });

        buttonArea.getChildren().add(stopButton);
        buttonArea.getChildren().add(pauseButton);

        root.getChildren().add(buttonArea);

        Node closeButton = createCloseButton(scene);

        root.getChildren().add(closeButton);

        return scene;
    }

    private Node createCloseButton(Scene scene) {
        final Group closeApp = new Group();
        Circle closeButton = CircleBuilder.create()
                .centerX(5)
                .centerY(0)
                .radius(7)
                .fill(Color.rgb(255, 255, 255, .80))
                .build();
        Text closeXmark = new Text(2, 4, "X");
        closeApp.translateXProperty().bind(scene.widthProperty().subtract(15));
        closeApp.setTranslateY(12);
        closeApp.getChildren().addAll(closeButton, closeXmark);
        closeApp.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Platform.exit();
            }
        });
        return closeApp;
    }

    private Node createPauseControl() {
        final Group pause = new Group();
        final Circle pauseButton = CircleBuilder.create()
                .centerX(12)
                .centerY(16)
                .radius(10)
                .fill(Color.rgb(255, 255, 255, .90))
                .stroke(Color.rgb(1, 1, 1, .90))
                .translateX(30)
                .build();
        final Line firstLine = LineBuilder.create()
                .startX(6)
                .startY(6)
                .endX(6)
                .endY(12)
                .strokeWidth(3)
                .translateX(34)
                .translateY(6)
                .stroke(Color.rgb(1, 1, 1, .90))
                .build();
        final Line secondLine = LineBuilder.create()
                .startX(12)
                .startY(6)
                .endX(12)
                .endY(12)
                .strokeWidth(3)
                .translateX(34)
                .translateY(6)
                .stroke(Color.rgb(1, 1, 1, .90))
                .build();
        pause.getChildren().addAll(pauseButton, firstLine, secondLine);
        return pause;
    }

    private Node createPlayControl() {
        final Arc playButton = ArcBuilder.create()
                .type(ArcType.ROUND)
                .centerX(12)
                .centerY(16)
                .radiusX(15)
                .radiusY(15)
                .startAngle(180 - 30)
                .length(60)
                .fill(Color.rgb(255, 255, 255, .90))
                .translateX(40)
                .build();
        return playButton;
    }

    private Node createStopControl() {
        Rectangle stopButton = RectangleBuilder.create()
                .arcWidth(5)
                .arcHeight(5)
                .fill(Color.rgb(255, 255, 255, .80))
                .x(0)
                .y(0)
                .width(10)
                .height(10)
                .translateX(15)
                .translateY(10)
                .stroke(Color.rgb(255, 255, 255, .70))
                .build();
        return stopButton;
    }

    private Group createButtonArea(final Scene scene) {
        final Group buttonGroup = new Group();
        Rectangle buttonArea = RectangleBuilder.create()
                .arcWidth(15)
                .arcHeight(20)
                .fill(Color.rgb(0, 0, 0, .55))
                .x(0)
                .y(0)
                .width(60)
                .height(30)
                .stroke(Color.rgb(255, 255, 255, .70))
                .build();
        buttonGroup.getChildren().add(buttonArea);

        buttonGroup.translateXProperty().bind(scene.widthProperty().subtract(buttonArea.getWidth() + 6));
        buttonGroup.translateYProperty().bind(scene.heightProperty().subtract(buttonArea.getHeight() + 6));
        return buttonGroup;
    }

    private Slider createSlider(Scene scene) {
        Slider slider = SliderBuilder.create()
                .min(0)
                .max(100)
                .value(10)
                .showTickLabels(true)
                .showTickMarks(true)
                .build();
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (poused) {
                    long dur = newValue.intValue() * 1000;
                    mediaPlayer.seek(new Duration(dur));
                }
            }
        });

        slider.translateYProperty().bind(scene.yProperty().subtract(30));
        return slider;
    }

    private Node createBackground(Scene scene) {
        Rectangle applicationArea = RectangleBuilder.create()
                .arcWidth(20)
                .arcHeight(20)
                .fill(Color.rgb(0, 0, 0, .80))
                .x(0)
                .y(0)
                .strokeWidth(2)
                .stroke(Color.rgb(255, 255, 255, .70))
                .build();
        applicationArea.widthProperty().bind(scene.widthProperty());
        applicationArea.heightProperty().bind(scene.heightProperty());
        return applicationArea;

    }
}
