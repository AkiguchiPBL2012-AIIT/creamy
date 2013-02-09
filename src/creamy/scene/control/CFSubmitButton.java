package creamy.scene.control;

import javafx.scene.Node;
import javafx.scene.control.Button;

/**
 * CreamyのSubmitボタンクラス.
 * <p>
 * Formインターフェースの実装クラス（CFGridForm, CFHFormなど）の要素として使用する。<br>
 * HTMLの&lt;form&gt;タグのINPUT要素を想定しているため、name属性、format属性を保持する。
 * </p>
 *
 * @author miyabetaiji
 */
public class CFSubmitButton extends Button implements FormRequest {
    /**
     * CFSubmitButtonを生成する.
     */
    public CFSubmitButton() { super(); }

    /**
     * CFSubmitButtonを生成し、ボタンのテキストをセットする.
     * @param text CFSubmitButton名
     */
    public CFSubmitButton(String text) { super(text); }
    
    /**
     * CFSubmitButtonを生成し、ボタンのテキスト、イメージをセットする.
     * @param text CFSubmitButton名
     * @param graphic イメージ名
     */
    public CFSubmitButton(String text, Node graphic) { super(text, graphic); }
}
