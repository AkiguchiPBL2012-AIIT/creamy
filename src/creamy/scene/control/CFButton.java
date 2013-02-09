package creamy.scene.control;

import javafx.scene.control.Button;

/**
 * CreamyのButtonクラス.
 * <p>
 * Formインターフェースの実装クラス（CFGridForm, CFHFormなど）の要素として使用する。<br>
 * HTMLの&lt;form&gt;タグのINPUT要素を想定しているため、name属性を保持する。
 * </p>
 * @see creamy.scene.layout.CFGridForm
 * @see creamy.scene.layout.CFHForm
 * @author ahayama
 */
public class CFButton extends Button {
    
    private String name;
    
    /**
     * CFButtonを生成し、ボタンタイトルをセットする.
     * @param text ボタンタイトル
     */
    public CFButton(String text) {
        this.setText(text);
    }

    /**
     * ボタンタイトルをセットする.
     * @param name ボタンタイトル
     * @return 自身のオブジェクト
     */
    public CFButton setName(String name) {
        this.name = name;
        return this;
    }
    /**
     * ボタンタイトルを返す.
     * @return ボタンタイトル
     */
    public String getName() {
        return this.name;
    }
    
}
