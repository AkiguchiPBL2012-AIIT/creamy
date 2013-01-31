package creamy.browser.control;

import creamy.mvc.Response;
import javafx.collections.ObservableList;

/**
 * ForwardButtonの基底クラス
 * BrowserにForwardButtonを表示する際は、このクラスを拡張する
 * (onForwardHistoryCleared, onForwardHistoryChangedを実装する)
 *
 * @author Taiji Miyabe (Professor Akiguchi's PBL 2012, AIIT)
 */
public abstract class ForwardButton extends HistoryButton {
    /**
     * ForwaedButtonを生成する
     */
    public ForwardButton() { super(); }
   
    /**
     * ForwaedButtonを生成する
     * @param text Text
     */
    public ForwardButton(String text) { super(text); }
    
    @Override
    protected void onHistoryCleared() {
        onForwardHistoryCleared();
    }
    
    /**
     * ForwardHistoryが空になった時にコールバックされる
     */
    abstract protected void onForwardHistoryCleared();

    @Override
    protected void onHistoryChanged(ObservableList<? extends Response> history) {
        onForwardHistoryChanged(history);
    }
    
    /**
     * ForwardHistoryの状態が変更された時にコールバックされる
     * @param ForwardHistory
     */
    abstract protected void onForwardHistoryChanged(ObservableList<? extends Response> forwardHistory);
}
