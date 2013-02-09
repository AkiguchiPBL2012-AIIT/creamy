package creamy.scene.control;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;

/**
 * CreamyのLinkButtonボタンクラス.
 * <p>
 * Formインターフェースの実装クラス（CFGridForm, CFHFormなど）の要素として使用する。<br>
 * HTMLの&lt;form&gt;タグのINPUT要素を想定しているため、name属性、format属性を保持する。
 * </p>
 *
 * @author miyabetaiji
 */
public class CFLinkButton extends Button implements UnitRequest {
    private StringProperty method = new SimpleStringProperty();
    private StringProperty path = new SimpleStringProperty();
    
    /**
     * CFLinkButtonを生成する.
     */
    public CFLinkButton() { super(); }

    /**
     * CFLinkButtonを生成し、ボタンのテキストをセットする.
     * @param text CFLinkButton名
     */
    public CFLinkButton(String text) { super(text); }
    
    /**
     * CFLinkButtonを生成し、ボタンのテキスト、ボタンのイメージをセットする.
     * @param text ボタンのテキスト
     * @param graphic ボタンのイメージ
     */
    public CFLinkButton(String text, Node graphic) { super(text, graphic); }
    
    /**
     * CFLinkButtonを生成し、ボタンのテキスト、パスをセットする.
     * @param text ボタンのテキスト
     * @param path パス
     */
    public CFLinkButton(String text, String path) {
        this(text);
        this.path.set(path);
    }
    
    /**
     * CFLinkButtonを生成し、ボタンのテキスト、ボタンのイメージ、パスをセットする.
     * @param text ボタンのテキスト
     * @param path パス
     * @param graphic ボタンのイメージ
     */
    public CFLinkButton(String text, String path, Node graphic) {
        this(text, graphic);
        this.path.set(path);
    }
    
    /**
     * CFLinkButtonのmethod値を返す.
     * @return method値
     */
    @Override
    public String getMethod() { return method.get(); }
    
    /**
     * CFLinkButtonのpath値を返す.
     * @return path値
     */
    @Override
    public String getPath() { return path.get(); }

    /**
     * CFLinkButtonのパスをセットする.
     * @param path パス値
     */
    public void setPath(String path) { this.path.set(path); }
}
