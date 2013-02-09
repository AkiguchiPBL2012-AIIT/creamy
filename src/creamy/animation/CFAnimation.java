package creamy.animation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * CreamyフレームワークのAnimationクラス。<br />
 * 機能は「effect/show/hide/toggle」、「pulsate/blind/fade/scale/slide」に分かれる。<br />
 * effect/show/hide/toggle <br />
 * <ul>
 *   <li>effect:対象のノードに指定したアニメーションを動作させる。ノードの表示/非表示は制御しない</li>
 *   <li>show:指定したアニメーションを動作させながら、ノードを表示する</li>
 *   <li>hide:指定したアニメーションを動作させながら、ノードを非表示にする</li>
 *   <li>toggle:指定したアニメーションを動作させながら、ノードを表示/非表示する</li>
 * </ul>
 * pulsate/blind/fade/scale/slide <br />
 * <ul>
 *   <li>pulsate:点滅させる</li>
 *   <li>blind:ブラインドを開閉するように、縦方向に伸縮しながら画面の一部を表示/非表示する</li>
 *   <li>fade:徐々に透明(非透明)にする</li>
 *   <li>scale:徐々に拡大/縮小する</li>
 *   <li>slide:左からスライドしながら表示する/右にスライドしながら非表示する</li>
 * </ul>
 * ノードの表示/非表示は、minWidthProperty/maxWidthtProperty、minHeightProperty/maxHeightProperty
 * をTimelineで変更することによって行う。基準となる値はprefWidthProperty、preHeightProperty。
 * ノードからprefWidthProperty、preHeightPropertyが取得でいない、あるいは、負の値の場合には、
 * ノードの表示/非表示(サイズ変更)は行わない。
 * 
 * @author miyabetaiji
 */
public class CFAnimation {
    /**
     * Animationを適用するNode
     */
    private Node node;
    /**
     * NodeのWidthtProperty、HeightProperty、ScalePropertyを格納するオブジェクト
     */
    private NodeInfo nodeInfo;
    
    /**
     * CFAnimationを生成する
     * @param node Animationを適用するNode
     */
    public CFAnimation(Node node) {
        this.node = node;
        this.nodeInfo = new NodeInfo(node);
    }
    
    /**
     * effectPulsateを実行する
     * @param time 1回の点滅時間(msec)
     * @param times 点滅回数
     */
    public void effectPulsate(double time, int times) {
        createEffectPulsate(time, times, 1.0, 0.0).play();
    }

    /**
     * effectPulsateを実行する
     * @param time 1回の点滅時間(msec)
     * @param times 点滅回数
     * @param callback Animation終了後に呼び返されるCallbackオブジェクト
     */
    public void effectPulsate(double time, int times, EventHandler<ActionEvent> callback) {
        setCallback(createEffectPulsate(time, times, 1.0, 0.0), callback).play();
    }
    
    /**
     * effectPulsate Animationを生成する
     * @return Animation
     */
    protected Animation createEffectPulsate(double time, int times, double fromValue, double toValue) {
        double interval = time / (times * 2.0d);
        return SequentialTransitionBuilder.create()
                .interpolator(Interpolator.EASE_BOTH)
                .cycleCount(times)
                .children(
                    TimelineBuilder.create()
                        .keyFrames(
                            new KeyFrame(Duration.ZERO, new KeyValue(nodeInfo.opacityProperty, fromValue)),
                            new KeyFrame(Duration.millis(interval),
                                new KeyValue(nodeInfo.opacityProperty, toValue))
                        )
                    .build(),
                    TimelineBuilder.create()
                        .keyFrames(
                            new KeyFrame(Duration.ZERO, new KeyValue(nodeInfo.opacityProperty, toValue)),
                            new KeyFrame(Duration.millis(interval),
                                new KeyValue(nodeInfo.opacityProperty, fromValue))
                        )
                    .build()
                )
                .build();
    }
    
    /**
     * showPulsateを実行する
     * @param time 1回の点滅時間(msec)
     * @param times 点滅回数
     */
    public void showPulsate(double time, int times) {
        createShowPulsate(time, times).play();
    }

    /**
     * showPulsateを実行する
     * @param time 1回の点滅時間(msec)
     * @param times 点滅回数
     * @param callback Animation終了後に呼び返されるCallbackオブジェクト 
     */
    public void showPulsate(double time, int times, EventHandler<ActionEvent> callback) {
        setCallback(createShowPulsate(time, times), callback).play();
    }
    
    /**
     * showPulsate Animationを生成する
     * @return Animation
     */
    protected Animation createShowPulsate(double time, int times) {
        if (nodeInfo.isRestorable()) {
            return SequentialTransitionBuilder.create()
                    .children(
                        createShowAnimation(),
                        createEffectPulsate(time, times, 1.0, 0.0)
                    )
                    .build();
        } else {
            return createEffectPulsate(time, times, 1.0, 0.0);
        }
    }
    
    /**
     * hidePulsateを実行する
     * @param time 1回の点滅時間(msec)
     * @param times 点滅回数
     */
    public void hidePulsate(double time, int times) {
        createHidePulsate(time, times).play();
    }

    /**
     * hidePulsateを実行する
     * @param time 1回の点滅時間(msec)
     * @param times 点滅回数
     * @param callback Animation終了後に呼び返されるCallbackオブジェクト 
     */
    public void hidePulsate(double time, int times, EventHandler<ActionEvent> callback) {
        setCallback(createHidePulsate(time, times), callback).play();
    }

    /**
     * hidePulsate Animationを生成する
     * @return Animation
     */
    protected Animation createHidePulsate(double time, int times) {
        if (nodeInfo.isRestorable()) {
            return SequentialTransitionBuilder.create()
                    .children(
                        createEffectPulsate(time, times, 0.0, 1.0),
                        createHideAnimation()
                    )
                    .build();
        } else {
            return createEffectPulsate(time, times, 0.0, 1.0);
        }
    }
    
    /**
     * togglePulsateを実行する
     * @param time 1回の点滅時間(msec)
     * @param times 点滅回数
     */
    public void togglePulsate(double time, int times) {
        if (nodeInfo.isHidden())
            createShowPulsate(time, times).play();
        else
            createHidePulsate(time, times).play();
    }
    
    /**
     * togglePulsateを実行する
     * @param time 1回の点滅時間(msec)
     * @param times 点滅回数
     * @param callback Animation終了後に呼び返されるCallbackオブジェクト 
     */
    public void togglePulsate(double time, int times, EventHandler<ActionEvent> callback) {
        if (nodeInfo.isHidden())
            setCallback(createShowPulsate(time, times), callback).play();
        else
            setCallback(createHidePulsate(time, times), callback).play();
    }
    
    /**
     * showBlindを実行する
     * @param time 時間(msec)
     */
    public void showBlind(double time) {
        if (!nodeInfo.isRestorable()) return;
        createShowBlind(time).play();
    }

    /**
     * showBlindを実行する
     * @param time 時間(msec)
     * @param callback Animation終了後に呼び返されるCallbackオブジェクト 
     */
    public void showBlind(double time, EventHandler<ActionEvent> callback) {
        if (!nodeInfo.isRestorable()) return;
        setCallback(createShowBlind(time), callback).play();
    }
    
    /**
     * showBlind Animationを生成する
     * @return Animation
     */
    protected Animation createShowBlind(double time) {
        return SequentialTransitionBuilder.create()
                .children(
                    createHorizontalShowAnimation(),
                    createVerticalShowAnimation(time)
                )
                .build();
    }
    
    /**
     * hideBlindを実行する
     * @param time 時間(msec)
     */
    public void hideBlind(double time) {
        if (!nodeInfo.isRestorable()) return;
        createHideBlind(time).play();
    }
    
    /**
     * hideBlindを実行する
     * @param time 時間(msec)
     * @param callback Animation終了後に呼び返されるCallbackオブジェクト 
     */
    public void hideBlind(double time, EventHandler<ActionEvent> callback) {
        if (!nodeInfo.isRestorable()) return;
        setCallback(createHideBlind(time), callback).play();
    }
    
    /**
     * hideBlind Animationを生成する
     * @return Animation
     */
    protected Animation createHideBlind(double time) {
        nodeInfo.maxHeightProperty.set(nodeInfo.prefHeightProperty.get());
        return SequentialTransitionBuilder.create()
                .children(
                    createVerticalHideAnimation(time),
                    createHorizontalHideAnimation()
                )
                .build();
    }
    
    /**
     * toggleBlindを実行する
     * @param time 時間(msec)
     */
    public void toggleBlind(double time) {
        if (!nodeInfo.isRestorable()) return;
        if (nodeInfo.isHidden())
            createShowBlind(time).play();
        else
            createHideBlind(time).play();
    }

    /**
     * toggleBlindを実行する
     * @param time 時間(msec)
     * @param callback Animation終了後に呼び返されるCallbackオブジェクト 
     */
    public void toggleBlind(double time, EventHandler<ActionEvent> callback) {
        if (!nodeInfo.isRestorable()) return;
        if (nodeInfo.isHidden())
            setCallback(createShowBlind(time), callback).play();
        else
            setCallback(createHideBlind(time), callback).play();
    }
    
    /**
     * effectFadeを実行する
     * @param time 時間(msec)
     */
    public void effectFade(double time) {
        createEffectFade(time, 1.0, 0.0).play();
    }
 
    /**
     * effectFadeを実行する
     * @param time 時間(msec)
     * @param callback Animation終了後に呼び返されるCallbackオブジェクト 
     */
    public void effectFade(double time, EventHandler<ActionEvent> callback) {
        setCallback(createEffectFade(time, 1.0, 0.0), callback).play();
    }
    
    /**
     * effectFade Animationを生成する
     * @return Animation
     */
    protected Animation createEffectFade(double time, double fromValue, double toValue) {
        return TimelineBuilder.create()
                .keyFrames(
                    new KeyFrame(Duration.ZERO, new KeyValue(nodeInfo.opacityProperty, fromValue)),
                    new KeyFrame(Duration.millis(time),
                        new KeyValue(nodeInfo.opacityProperty, toValue,  Interpolator.EASE_BOTH))
                )
                .build();
    }

    /**
     * showFadeを実行する
     * @param time 時間(msec)
     */
    public void showFade(double time) {
        createShowFade(time).play();
    }

    /**
     * showFadeを実行する
     * @param time 時間(msec)
     * @param callback Animation終了後に呼び返されるCallbackオブジェクト 
     */
    public void showFade(double time, EventHandler<ActionEvent> callback) {
        setCallback(createShowFade(time), callback).play();
    }

    /**
     * showFade Animationを生成する
     * @return Animation
     */
    protected Animation createShowFade(double time) {
        if (nodeInfo.isRestorable()) {
            return SequentialTransitionBuilder.create()
                    .children(
                        createShowAnimation(),
                        createEffectFade(time, 0.0, 1.0)
                    )
                    .build();
        } else {
            return createEffectFade(time, 0.0, 1.0);
        }
    }
    
    /**
     * hideFadeを実行する
     * @param time 時間(msec)
     */
    public void hideFade(double time) {
        createHideFade(time).play();
    }

    /**
     * hideFadeを実行する
     * @param time 時間(msec)
     * @param callback Animation終了後に呼び返されるCallbackオブジェクト 
     */
    public void hideFade(double time, EventHandler<ActionEvent> callback) {
        setCallback(createHideFade(time), callback).play();
    }
    
    /**
     * hideFade Animationを生成する
     * @return Animation
     */
    protected Animation createHideFade(double time) {
        if (nodeInfo.isRestorable()) {
            return SequentialTransitionBuilder.create()
                    .children(
                        createEffectFade(time, 1.0, 0.0),
                        createHideAnimation()
                    )
                    .build();
        } else {
            return createEffectFade(time, 1.0, 0.0);
        }
    }
    
    /**
     * toggleFadeを実行する
     * @param time 時間(msec)
     */
    public void toggleFade(double time) {
        if (nodeInfo.isHidden())
            createShowFade(time).play();
        else
            createHideFade(time).play();
    }
    
    /**
     * toggleFadeを実行する
     * @param time 時間(msec)
     * @param callback Animation終了後に呼び返されるCallbackオブジェクト 
     */
    public void toggleFade(double time, EventHandler<ActionEvent> callback) {
        if (nodeInfo.isHidden())
            setCallback(createShowFade(time), callback).play();
        else
            setCallback(createHideFade(time), callback).play();
    }
    
    /**
     * effectScaleを実行する
     * @param time 時間(msec)
     * @param percent 拡大/縮小率
     */
    public void effectScale(double time, double percent) {
        createEffectScale(time, percent).play();
    }
    
    /**
     * effectScaleを実行する
     * @param time 時間(msec)
     * @param percent 拡大/縮小率
     * @param callback Animation終了後に呼び返されるCallbackオブジェクト 
     */
    public void effectScale(double time, double percent, EventHandler<ActionEvent> callback) {
        setCallback(createEffectScale(time, percent), callback).play();
    }
    
    /**
     * effectScale Animationを生成する
     * @return 
     */
    protected Animation createEffectScale(double time, double percent) {
        double scaleX = nodeInfo.scaleXProperty.get() * percent;
        double scaleY = nodeInfo.scaleYProperty.get() * percent;
        double sizeX = nodeInfo.prefWidthProperty.get() * scaleX;
        double sizeY = nodeInfo.prefHeightProperty.get() * scaleY;
        if (nodeInfo.isRestorable()) {
            return ParallelTransitionBuilder.create()
                    .interpolator(Interpolator.EASE_BOTH)
                    .children(
                        createScaleAnimation(time, scaleX, scaleY),
                        createSizeAnimation(time, sizeX, sizeY)
                    )
                    .build();
        } else {
            return createScaleAnimation(time, scaleX, scaleY);
        }
    }

    /**
     * showScaleを実行する
     * @param time 時間(msec)
     * @param callback Animation終了後に呼び返されるCallbackオブジェクト 
     */
    public void showScale(double time) {
        createShowScale(time).play();
    }
 
    /**
     * showScaleを実行する
     * @param time 時間(msec)
     * @param callback Animation終了後に呼び返されるCallbackオブジェクト 
     */
    public void showScale(double time, EventHandler<ActionEvent> callback) {
        setCallback(createShowScale(time), callback).play();
    }
    
    /**
     * showScale Animationを生成する
     * @return Animation
     */
    protected Animation createShowScale(double time) {
        if (nodeInfo.isRestorable()) {
            double prefWidth = nodeInfo.prefWidthProperty.get();
            double prefHeight = nodeInfo.prefHeightProperty.get();
            return SequentialTransitionBuilder.create()
                    .interpolator(Interpolator.EASE_BOTH)
                    .children(
                        createSizeAnimation(DEFAULT_SHOW_TIME, prefWidth, prefHeight),
                        createScaleAnimation(time, 1.0, 1.0)
                    )
                    .build();
        } else {
            return createScaleAnimation(time, 1.0, 1.0);
        }
    }
    
    /**
     * hideScaleを実行する
     * @param time 時間(msec)
     * @param callback Animation終了後に呼び返されるCallbackオブジェクト 
     */
    public void hideScale(double time) {
        createHideScale(time).play();
    }

    /**
     * hideScaleを実行する
     * @param time 時間(msec)
     */
    public void hideScale(double time, EventHandler<ActionEvent> callback) {
        setCallback(createHideScale(time), callback).play();
    }

    /**
     * hideScale Animationを生成する
     * @return Animation
     */
    protected Animation createHideScale(double time) {
        if (nodeInfo.isRestorable()) {
            return SequentialTransitionBuilder.create()
                    .interpolator(Interpolator.EASE_BOTH)
                    .children(
                        createScaleAnimation(time, 0.0, 0.0),
                        createHideAnimation()
                    )
                    .build();
        } else {
            return createScaleAnimation(time, 0.0, 0.0);
        }
    }
    
    /**
     * toggleScaleを実行する
     * @param time 時間(msec)
     */
    public void toggleScale(double time) {
        if (nodeInfo.isHidden())
            createShowScale(time).play();
        else
            createHideScale(time).play();
    }

    /**
     * toggleScaleを実行する
     * @param time 時間(msec)
     */
    public void showSlide(double time) {
        if (!nodeInfo.isRestorable()) return;
        createShowSlide(time).play();
    }

    /**
     * showSlideを実行する
     * @param time 時間(msec)
     * @param callback Animation終了後に呼び返されるCallbackオブジェクト 
     */
    public void showSlide(double time, EventHandler<ActionEvent> callback) {
        if (!nodeInfo.isRestorable()) return;
        setCallback(createShowSlide(time), callback).play();
    }
    
    /**
     * showSlide Animationを生成する
     * @return 
     */
    protected Animation createShowSlide(double time) {
        return SequentialTransitionBuilder.create()
                .children(
                    createVerticalShowAnimation(),
                    createHorizontalShowAnimation(time)
                )
                .build();
    }
    
    /**
     * hideSlideを実行する
     * @param time 時間(msec)
     */
    public void hideSlide(double time) {
        if (!nodeInfo.isRestorable()) return;
        createHideSlide(time).play();
    }
    
    /**
     * hideSlideを実行する
     * @param time 時間(msec)
     * @param callback Animation終了後に呼び返されるCallbackオブジェクト 
     */
    public void hideSlide(double time, EventHandler<ActionEvent> callback) {
        if (!nodeInfo.isRestorable()) return;
        setCallback(createHideSlide(time), callback).play();
    }
    
    /**
     * hideSlide Animationを生成する
     * @return Animation
     */
    protected Animation createHideSlide(double time) {
        nodeInfo.maxWidthProperty.set(nodeInfo.prefWidthProperty.get());
        return SequentialTransitionBuilder.create()
                .children(
                    createHorizontalHideAnimation(time),
                    createVerticalHideAnimation()
                )
                .build();
    }
    
    /**
     * toggleSlideを実行する
     * @param time 時間(msec)
     */
    public void toggleSlide(double time) {
        if (!nodeInfo.isRestorable()) return;
        if (nodeInfo.isHidden())
            createShowSlide(time).play();
        else
            createHideSlide(time).play();
    }

    /**
     * toggleSlideを実行する
     * @param time 時間(msec)
     * @param callback Animation終了後に呼び返されるCallbackオブジェクト 
     */
    public void toggleSlide(double time, EventHandler<ActionEvent> callback) {
        if (!nodeInfo.isRestorable()) return;
        if (nodeInfo.isHidden())
            setCallback(createShowSlide(time), callback).play();
        else
            setCallback(createHideSlide(time), callback).play();
    }
    
    protected static final double DEFAULT_SHOW_TIME = 10.0d;  // msec
    
    /**
     * 表示Animationを生成する
     * @return Animation
     */
    protected Animation createShowAnimation() {
        return createShowAnimation(DEFAULT_SHOW_TIME);
    }

    /**
     * 表示Animationを生成する
     * @param time 時間(msec)
     * @return Animation
     */
    protected Animation createShowAnimation(double time) {
        return ParallelTransitionBuilder.create()
                .children(
                    createHorizontalShowAnimation(time),
                    createVerticalShowAnimation(time)
                )
                .build();
    }

    protected Animation createHorizontalShowAnimation() {
        return createHorizontalShowAnimation(DEFAULT_SHOW_TIME);
    }
    
    protected Animation createHorizontalShowAnimation(double time) {
        Double prefWidth = nodeInfo.prefWidthProperty.get();
        return ParallelTransitionBuilder.create()
                .children(
                    createHorizontalScaleAnimation(time, 1.0),
                    createHorizontalSizeAnimation(time, prefWidth)
                )
                .build();
    }
    
    protected Animation createVerticalShowAnimation() {
        return createVerticalShowAnimation(DEFAULT_SHOW_TIME);
    }
    
    protected Animation createVerticalShowAnimation(double time) {
        Double prefHeight = nodeInfo.prefHeightProperty.get();
        return ParallelTransitionBuilder.create()
                .children(
                    createVerticalScaleAnimation(time, 1.0),
                    createVerticalSizeAnimation(time, prefHeight)
                )
                .build();
    }

    protected static final double DEFAULT_HIDE_TIME = 10.0d;  // msec
    
    /**
     * 表示Animationを生成する
     * @return Animation
     */
    protected Animation createHideAnimation() {
        return createHideAnimation(DEFAULT_HIDE_TIME);
    }
    
    /**
     * 表示Animationを生成する
     * @param time 時間(msec)
     * @return Animation
     */
    protected Animation createHideAnimation(double time) {
        return ParallelTransitionBuilder.create()
                .children(
                    createHorizontalHideAnimation(time),
                    createVerticalHideAnimation(time)
                )
                .build();
    }
    
    protected Animation createHorizontalHideAnimation() {
        return createHorizontalHideAnimation(DEFAULT_HIDE_TIME);
    }
    
    protected Animation createHorizontalHideAnimation(double time) {
        return ParallelTransitionBuilder.create()
                .children(
                    createHorizontalScaleAnimation(time, 0.0),
                    createHorizontalSizeAnimation(time, 0.0)
                )
                .build();
    }
 
    protected Animation createVerticalHideAnimation() {
        return createVerticalHideAnimation(DEFAULT_HIDE_TIME);
    }
    
    protected Animation createVerticalHideAnimation(double time) {
        return ParallelTransitionBuilder.create()
                .children(
                    createVerticalScaleAnimation(time, 0.0),
                    createVerticalSizeAnimation(time, 0.0)
                )
                .build();
    }

    protected Animation createScaleAnimation(double time, double scaleX, double scaleY) {
        return ParallelTransitionBuilder.create()
                .children(
                    createHorizontalScaleAnimation(time, scaleX),
                    createVerticalScaleAnimation(time, scaleY)
                )
                .build();
    }
    
    protected Animation createHorizontalScaleAnimation(double time, double scale) {
        return TimelineBuilder.create()
                .keyFrames(
                    new KeyFrame(Duration.millis(time), new KeyValue(nodeInfo.scaleXProperty, scale))
                )
                .build();
    }
 
    protected Animation createVerticalScaleAnimation(double time, double scale) {
        return TimelineBuilder.create()
                .keyFrames(
                    new KeyFrame(Duration.millis(time), new KeyValue(nodeInfo.scaleYProperty, scale))
                )
                .build();
    }
    
    protected Animation createSizeAnimation(double time, double sizeX, double sizeY) {
        return ParallelTransitionBuilder.create()
                .children(
                    createHorizontalSizeAnimation(time, sizeX),
                    createVerticalSizeAnimation(time, sizeY)
                )
                .build();
    }
 
    protected Animation createHorizontalSizeAnimation(double time, double size) {
        return TimelineBuilder.create()
                .keyFrames(
                    new KeyFrame(Duration.millis(time), new KeyValue(nodeInfo.minWidthProperty, size)),
                    new KeyFrame(Duration.millis(time), new KeyValue(nodeInfo.maxWidthProperty, size))
                )
                .build();
    }
    
    protected Animation createVerticalSizeAnimation(double time, double size) {
        return TimelineBuilder.create()
                .keyFrames(
                    new KeyFrame(Duration.millis(time), new KeyValue(nodeInfo.minHeightProperty, size)),
                    new KeyFrame(Duration.millis(time), new KeyValue(nodeInfo.maxHeightProperty, size))
                )
                .build();
    }
    
    protected Animation setCallback(Animation animation, EventHandler<ActionEvent> callback) {
        animation.setOnFinished(callback);
        return animation;
    }
    
    /**
     * 対象NodeのWidthProprty, HeightProperty, scalePropertyを格納するクラス。 <br />
     * AnimationではTimelineでの値変更に使用される。
     */
    protected class NodeInfo {
        protected DoubleProperty minWidthProperty;
        protected DoubleProperty minHeightProperty;
        protected DoubleProperty prefWidthProperty;
        protected DoubleProperty prefHeightProperty;
        protected DoubleProperty maxWidthProperty;
        protected DoubleProperty maxHeightProperty;
        protected DoubleProperty scaleXProperty;
        protected DoubleProperty scaleYProperty;
        protected DoubleProperty opacityProperty;
        
        protected NodeInfo(Node node) {
            minWidthProperty   = getProperty("minWidthProperty", DoubleProperty.class);
            minHeightProperty  = getProperty("minHeightProperty", DoubleProperty.class);
            prefWidthProperty  = getProperty("prefWidthProperty", DoubleProperty.class);
            prefHeightProperty = getProperty("prefHeightProperty", DoubleProperty.class);
            maxWidthProperty   = getProperty("maxWidthProperty", DoubleProperty.class);
            maxHeightProperty  = getProperty("maxHeightProperty", DoubleProperty.class);
            scaleXProperty     = node.scaleXProperty();
            scaleYProperty     = node.scaleYProperty();
            opacityProperty    = node.opacityProperty();
        }
        
        protected boolean hasSizePropery() {
            return minWidthProperty != null && minHeightProperty != null &&
                    prefWidthProperty != null && prefHeightProperty != null &&
                    maxWidthProperty != null && maxHeightProperty != null;
        }
        
        protected boolean hasDefiniteWidth() {
            return prefWidthProperty != null && prefWidthProperty.get() > 0;
        }

        protected boolean hasDefiniteHegiht() {
            return prefHeightProperty != null && prefHeightProperty.get() > 0;
        }
        
        protected boolean isRestorable() {
            return hasSizePropery() && hasDefiniteWidth() && hasDefiniteHegiht();
        }
        
        protected boolean isHidden() {
            if (isRestorable()) {
                return (maxWidthProperty.get() == 0.0 && maxHeightProperty.get() == 0.0) &&
                       (scaleXProperty.get() == 0.0 && scaleYProperty.get() == 0.0);
            } else {
                return (scaleXProperty.get() == 0.0 && scaleYProperty.get() == 0.0) ||
                        node.getOpacity() == 0.0;
            }
            
        }
        
        @SuppressWarnings("unchecked")
        private <T> T getProperty(String name, Class<T> clazz) {
            Class<?> superClass = node.getClass();
            while (Node.class.isAssignableFrom(superClass)) {
                for (Method method : superClass.getDeclaredMethods()) {
                    try {
                        if (!method.getName().equals(name)) continue;
                        if (!clazz.isAssignableFrom(method.getReturnType())) continue;
                        return (T)method.invoke(node);
                    } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException ex) {
                        Logger.getLogger(CFAnimation.class.getName()).log(Level.SEVERE, null, ex);
                        return null;
                    }
                }
                superClass = superClass.getSuperclass();
            }
            return null;
        }
    }
    
    /*
    private void showNodeInfo() {
        System.out.println("minWidthProperty: " + (nodeInfo.minWidthProperty == null ? "null" : nodeInfo.minWidthProperty.get()));
        System.out.println("minHeightProperty: " + (nodeInfo.minHeightProperty == null ? "null" :nodeInfo.minHeightProperty.get()));
        System.out.println("prefWidthProperty: " + (nodeInfo.prefWidthProperty == null ? "null" :nodeInfo.prefWidthProperty.get()));
        System.out.println("prefHeightProperty: " + (nodeInfo.prefHeightProperty == null ? "null" :nodeInfo.prefHeightProperty.get()));
        System.out.println("maxWidthProperty: " + (nodeInfo.maxWidthProperty == null ? "null" :nodeInfo.maxWidthProperty.get()));
        System.out.println("maxHeightProperty: " + (nodeInfo.maxHeightProperty == null ? "null" :nodeInfo.maxHeightProperty.get()));
        System.out.println("scaleXProperty: " + node.getScaleX());
        System.out.println("scaleYProperty: " + node.getScaleY());
        System.out.println("opacity: " + node.getOpacity());
    }
    */
}
