package creamy.browser;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * BrowserPanelのボディ領域
 * ページ遷移の度にchangedメソッドがコールバックされコンテンツが書き換わる
 * 
 * @author Taiji Miyabe (Professor Akiguchi's PBL 2012, AIIT)
 */
public class BrowserBody extends BrowserContent implements ChangeListener<Node> {
    
    /**
     * BrowserBodyを生成する
     * 生成の際にTransitionManagerに自身をListenerとして登録する
     * @param manager TransitionManager
     */
    public BrowserBody(TransitionManager manager) {
        super(manager);
        manager.addSceneListener(this);
    }

    /**
     * ページ遷移が発生した際のコールバックメソッド
     */
    @Override
    public void changed(ObservableValue<? extends Node> ov, Node oldNode, Node newNode) {
        if (manager.isNewScene(newNode))
            setContent((Pane)newNode);
        else
            replaceContent((Pane)newNode);
    }
}
