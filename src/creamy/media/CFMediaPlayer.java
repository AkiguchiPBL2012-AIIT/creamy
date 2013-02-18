package creamy.media;

import java.io.File;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

/**
 * 簡易なMediaPlayerを作成します。
 * 
 * @author tadao
 */
public class CFMediaPlayer extends BorderPane{
    private String MEDIA_PATH;
    private Double height = null;
    private Double width = null;
    private boolean isAutoPlay = true;
    private HBox mediaBar;
    private Slider timeSlider;
    private MediaPlayer mediaPlayer;
    private MediaView mediaView;
    private String startButton = ">";
    private String pauseButton = "||";
    private boolean repeat = false;
    private boolean stopRequested = false;
    private boolean atEndOfMedia = false;
    private Duration duration;
    private Slider volumeSlider;
    private Label playTime;
    private String mediaPlayerBackGroundStyle = "-fx-background-color: #bfc2c7;";
    
    
    /**
     * CFMediaPlayerのコンストラクタ。
     */
    public CFMediaPlayer(){
    }
    
    /**
     * CFMediaPlayerのコンストラクタ。mediaのURIもしくは、pathを指定します。
     * @param mediaPath　mediaのURIもしくは、FileへのPath
     */
    public CFMediaPlayer(String mediaPath){
        MEDIA_PATH = mediaPath;
    }
    
    /**
     * CFMediaPlayerのコンストラクタ。
     * @param mediaPath　MediaのURIもしくは、Fileへのパス。
     * @param height MediaPlayerの高さ。
     * @param width MediaPlayerの幅。
     */
    public CFMediaPlayer(String mediaPath, Double height, Double width){
        MEDIA_PATH = mediaPath;
        this.height = height;
        this.width = width;
    }
    
    /**
     * 自動で再生を開始するかどうかを設定。
     * @param isAutoPlay true: 自動再生
     */
    public void setAutoPlay(boolean isAutoPlay){
        this.isAutoPlay = isAutoPlay;
    }
    
    /**
     * MediaPlayerの高さを設定。
     * 
     * @param height 
     */
    public void setMediaViewHeight(Double height){
        this.height = height;
    }
    
    /**
     * MediaPlayerの幅を設定。
     * 
     * @param width 
     */
    public void setMediaViewWidth(Double width){
        this.width = width;
    }
    
    /**
     * MediaのURIもしくは、Fileへのパスを指定。
     * 
     * @param mediaPath 
     */
    public void setMediaPath(String mediaPath){
        MEDIA_PATH = mediaPath;
    }
    
    /**
     * Mediaを繰り返すかの指定。
     * 
     * @param isRepeat true:繰り返し
     */
    public void setRepeat(boolean isRepeat){
        repeat = isRepeat;
    }
    
    /**
     * Start時のボタンの文字列を設定。
     * 
     * @param startButton 例えば、">"
     */
    public void setStartButton(String startButton){
        this.startButton = startButton;
    }
    
    /**
     * Pause時のボタンの文字列を設定。
     * 
     * @param pauseButton 例えば、"||" 
     */
    public void setPauseButton(String pauseButton){
        this.pauseButton = pauseButton;
    }
    
    /**
     * MediaPlayerのバックグランドのスタイルを設定。
     * 
     * @param backGroundStyle 例えば、"-fx-background-color: #bfc2c7;"
     */
    public void setMediaPlayerBackGroundStyle(String backGroundStyle){
        mediaPlayerBackGroundStyle = backGroundStyle;
    }
    
    /**
     * CFMediaPlayerのオブジェクトを作成。
     * 
     * CFMediaPlayer mediaPlayer = new CFMediaPlayer();
     * mediaPlayer.setMediaPath(/path/to/media);
     * mediaPlayer.create();
     */
    public void create(){
        final File file = new File(MEDIA_PATH);
        // mediaの作成
        String path;
        // fileがファイルだった場合。
        if(file.isFile()){
            path = file.toURI().toString();
        }else{
            // fileがURIだった場合。
            path = file.getPath();
        }
        setStyle(mediaPlayerBackGroundStyle);
        Media media = new Media (path);
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(isAutoPlay);
        mediaView = createMediaView();
        Pane mvPane = new Pane() {
        };
        mvPane.getChildren().add(mediaView);
        mvPane.setStyle("-fx-background-color: black;");
        setCenter(mvPane);
 
        mediaBar = createMediaBar();
        
        //playBotton
        final Button playButton = new Button(startButton);

        playButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                MediaPlayer.Status status = mediaPlayer.getStatus();
                if (status == MediaPlayer.Status.UNKNOWN || status == MediaPlayer.Status.HALTED) {
                    return;
                }
                if (status == MediaPlayer.Status.PAUSED
                        || status == MediaPlayer.Status.READY
                        || status == MediaPlayer.Status.STOPPED) {
                    if (atEndOfMedia) {
                        mediaPlayer.seek(mediaPlayer.getStartTime());
                        atEndOfMedia = false;
                    }
                    mediaPlayer.play();
                } else {
                    mediaPlayer.pause();
                }
            }
        });
        
        mediaPlayer.currentTimeProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                updateValues();
            }
        });

        mediaPlayer.setOnPlaying(new Runnable() {
            public void run() {
                if (stopRequested) {
                    mediaPlayer.pause();
                    stopRequested = false;
                } else {
                    playButton.setText(pauseButton);
                }
            }
        });

        mediaPlayer.setOnPaused(new Runnable() {
            public void run() {
                playButton.setText(startButton);
            }
        });

        mediaPlayer.setOnReady(new Runnable() {
            public void run() {
                duration = mediaPlayer.getMedia().getDuration();
                updateValues();
            }
        });

        mediaPlayer.setCycleCount(repeat ? MediaPlayer.INDEFINITE : 1);
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            public void run() {
                if (!repeat) {
                    playButton.setText(pauseButton);
                    stopRequested = true;
                    atEndOfMedia = true;
                }
            }
        });
        mediaBar.getChildren().add(playButton);
        Label spacer = new Label("  ");
        mediaBar.getChildren().add(spacer);
        Label timeLabel = new Label("Time: ");
        mediaBar.getChildren().add(timeLabel);
        
        timeSlider = createTimeSlider();
        timeSlider.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                if (timeSlider.isValueChanging()) {
                    mediaPlayer.seek(duration.multiply(timeSlider.getValue() / 100.0));
                }
            }
        });
        mediaBar.getChildren().add(timeSlider);
        playTime = new Label();
        playTime.setPrefWidth(130);
        playTime.setMinWidth(50);
        mediaBar.getChildren().add(playTime);

        Label volumeLabel = new Label("Vol: ");
        mediaBar.getChildren().add(volumeLabel);

        volumeSlider = new Slider();
        volumeSlider.setPrefWidth(70);
        volumeSlider.setMaxWidth(Region.USE_PREF_SIZE);
        volumeSlider.setMinWidth(30);
                volumeSlider.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                if (volumeSlider.isValueChanging()) {
                    mediaPlayer.setVolume(volumeSlider.getValue() / 100.0);
                }
            }
        });
        mediaBar.getChildren().add(volumeSlider);
        setBottom(mediaBar);
    }
    
    private HBox createMediaBar(){
        HBox mediaB = new HBox();
        mediaB.setAlignment(Pos.CENTER);
        mediaB.setPadding(new Insets(5, 10, 5, 10));
        if(null != width){
            mediaB.setMaxWidth(width);
        }
        BorderPane.setAlignment(mediaB, Pos.CENTER);
        return mediaB;
    }
    
    private MediaView createMediaView(){
        MediaView mv = new MediaView(mediaPlayer);
        if(null != height){
            mv.setFitHeight(height);  
        }
        if(null != width){
            mv.setFitWidth(width);    
        }
        mv.setStyle("-fx-background-color: black;");
        return mv;
    }
    
    private Slider createTimeSlider(){
        Slider timeSl = new Slider();
        HBox.setHgrow(timeSl, Priority.ALWAYS);
        timeSl.setMinWidth(50.0);
        timeSl.setMaxWidth(Double.MAX_VALUE);
        return timeSl;
    }
    
    private void updateValues() {
        if (playTime != null && timeSlider != null && volumeSlider != null) {
            Platform.runLater(new Runnable() {
                public void run() {
                    Duration currentTime = mediaPlayer.getCurrentTime();
                    playTime.setText(formatTime(currentTime, duration));
                    timeSlider.setDisable(duration.isUnknown());
                    if (!timeSlider.isDisabled()
                            && duration.greaterThan(Duration.ZERO)
                            && !timeSlider.isValueChanging()) {
                        timeSlider.setValue(currentTime.divide(duration).toMillis() * 100.0);
                    }
                    if (!volumeSlider.isValueChanging()) {
                        volumeSlider.setValue((int) Math.round(mediaPlayer.getVolume()
                                * 100));
                    }
                }
            });
        }
    }

    private static String formatTime(Duration elapsed, Duration duration) {
        int intElapsed = (int) Math.floor(elapsed.toSeconds());
        int elapsedHours = intElapsed / (60 * 60);
        if (elapsedHours > 0) {
            intElapsed -= elapsedHours * 60 * 60;
        }
        int elapsedMinutes = intElapsed / 60;
        int elapsedSeconds = intElapsed - elapsedHours * 60 * 60
                - elapsedMinutes * 60;

        if (duration.greaterThan(Duration.ZERO)) {
            int intDuration = (int) Math.floor(duration.toSeconds());
            int durationHours = intDuration / (60 * 60);
            if (durationHours > 0) {
                intDuration -= durationHours * 60 * 60;
            }
            int durationMinutes = intDuration / 60;
            int durationSeconds = intDuration - durationHours * 60 * 60
                    - durationMinutes * 60;
            if (durationHours > 0) {
                return String.format("%d:%02d:%02d/%d:%02d:%02d",
                        elapsedHours, elapsedMinutes, elapsedSeconds,
                        durationHours, durationMinutes, durationSeconds);
            } else {
                return String.format("%02d:%02d/%02d:%02d",
                        elapsedMinutes, elapsedSeconds, durationMinutes,
                        durationSeconds);
            }
        } else {
            if (elapsedHours > 0) {
                return String.format("%d:%02d:%02d", elapsedHours,
                        elapsedMinutes, elapsedSeconds);
            } else {
                return String.format("%02d:%02d", elapsedMinutes,
                        elapsedSeconds);
            }
        }
    }   
}