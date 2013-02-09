package creamy.scene.control;

import creamy.mvc.Request;
import java.util.Comparator;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;

/**
 * CreamyのHyperlinkリンククラス.
 * <p>
 * Formインターフェースの実装クラス（CFGridForm, CFHFormなど）の要素として使用する。<br>
 * HTMLの&lt;form&gt;タグのINPUT要素を想定しているため、name属性、format属性を保持する。
 * </p>
 *
 * @author miyabetaiji
 */
public class CFHyperlink extends Hyperlink implements UnitRequest {
    private StringProperty method = new SimpleStringProperty();
    private StringProperty path = new SimpleStringProperty(Request.GET);
    
    /**
     * CFHyperlinkを生成する.
     */
    public CFHyperlink() { super(); }

    /**
     * CFHyperlinkを生成する.
     */
    public CFHyperlink(String text) { super(text); }
    
    /**
     * CFHyperlinkを生成し、リンクのテキスト、リンクのイメージをセットする.
     * @param text リンクのテキスト
     * @param graphic リンクのイメージ
     */
    public CFHyperlink(String text, Node graphic) { super(text, graphic); }
    
    /**
     * CFHyperlinkを生成し、リンクのテキスト、パスをセットする.
     * @param text リンクのテキスト
     * @param path パス
     */
    public CFHyperlink(String text, String path) {
        this(text);
        this.path.set(path);
    }
    
    /**
     * CFHyperlinkを生成し、リンクのテキスト、リンクのイメージ、パスをセットする.
     * @param text リンクのテキスト
     * @param path パス
     * @param graphic リンクのイメージ
     */
    public CFHyperlink(String text, String path, Node graphic) {
        this(text, graphic);
        this.path.set(path);
    }
    
    /**
     * CFHyperlinkのmethod値を返す.
     * @return method値
     */
    @Override
    public String getMethod() { return method.get(); }
    
    /**
     * CFHyperlinkのpath値を返す.
     * @return path値
     */
    @Override
    public String getPath() { return path.get(); }

    /**
     * CFHyperlinkのパスをセットする.
     * @param path パス値
     */
    public void setPath(String path) { this.path.set(path); }
    
    /**
     * Compratorクラス.
     * CFHyperlink同士を大文字小文字の区別なくテキストで較するクラス
     */
    public static class Comprator implements Comparator<CFHyperlink> {
        @Override
        public int compare(CFHyperlink c1, CFHyperlink c2) {
            return c1.getText().toLowerCase().compareTo(c2.getText().toLowerCase());
        }
    }
    
    /**
     * CaseCompratorクラス.
     * CFHyperlink同士を大文字小文字の区別ありテキストで較するクラス
     */
    public static class CaseComprator implements Comparator<CFHyperlink> {
        @Override
        public int compare(CFHyperlink c1, CFHyperlink c2) {
            return c1.getText().compareTo(c2.getText());
        }
    }
}
