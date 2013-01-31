package creamy.browser.control;

import creamy.mvc.Response;
import javafx.collections.ObservableList;

/**
 * BackButtonの基底クラス
 * BrowserにBackButtonを表示する際は、このクラスを拡張する
 * (onBackHistoryCleared, onBackHistoryChangedを実装する)
 *
 * @author Taiji Miyabe (Professor Akiguchi's PBL 2012, AIIT)
 */
public abstract class BackButton extends HistoryButton {
    /**
     * BackButtonを生成する
     */
    public BackButton() { super(); }
   
    /**
     * BackButtonを生成する
     * @param text Text
     */
    public BackButton(String text) { super(text); }
    
    @Override
    protected void onHistoryCleared() {
        onBackHistoryCleared();
    }
    
    /**
     * BackHistoryが空になった時にコールバックされる
     */
    abstract protected void onBackHistoryCleared();

    @Override
    protected void onHistoryChanged(ObservableList<? extends Response> history) {
        onBackHistoryChanged(history);
    }
    
    /**
     * BackHistoryの状態が変更された時にコールバックされる
     * @param BackHistory
     */
    abstract protected void onBackHistoryChanged(ObservableList<? extends Response> backHistory);
}
