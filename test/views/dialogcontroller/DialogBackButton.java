package views.dialogcontroller;

import creamy.browser.control.BackButton;
import creamy.mvc.Response;
import javafx.collections.ObservableList;

/**
 * 戻るボタン.
 * @author ahayama
 */
public class DialogBackButton extends BackButton {
    /**
     * 最初の画面に戻ったら戻るボタンを選択不可にする.
     */
    @Override
    protected void onBackHistoryCleared() {
        setDisable(true);
    }
    /**
     * BackHistoryが変更されたら（次画面に遷移したら）戻るボタンを選択可にする.
     * @param backHistory 
     */
    @Override
    protected void onBackHistoryChanged(ObservableList<? extends Response> backHistory) {
        setDisable(false);
    }
}
