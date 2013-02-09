package creamy.scene.layout;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.util.Builder;

/**
 * CreamyのVBoxクラス.
 * Formインターフェースの実装クラス<br>
 * HTMLの&lt;form&gt;タグを想定しているため、path属性、method属性を保持する。
 * @author ahayama
 */
public class CFVForm extends VBox implements Form {
    
    private String path;
    private String method;
    
    public CFVForm() {}
    /**
     * CFVFormオブジェクトを生成する.
     * @param path パス
     */
    public CFVForm(String path) {
        this.path = path;
    }
    /**
     * パスをセットする.
     * @param path パス
     */
    @Override
    public void setPath(String path) {
        this.path = path;
    }
    /**
     * パスを返す.
     * @return パス
     */
    @Override
    public String getPath() {
        return this.path;
    }
    /**
     * メソッド（GET/POST）をセットする.
     * @param path メソッド
     */
    @Override
    public void setMethod(String path) {
        this.method = path;
    }
    /**
     * メソッド（GET/POST）を返す.
     * @return メソッド
     */
    @Override
    public String getMethod() {
        return this.method;
    }
    /**
     * メソッド（GET/POST）をセットする.
     * @param method メソッド
     * @return 自身のオブジェクト
     */
    public CFVForm method(String method) {
        setMethod(method);
        return this;
    }
    /**
     * CFVFormにNodeを追加する.
     * @param children 追加するNode
     * @return 自身のオブジェクト
     */
    public CFVForm add(Node... children) {
        this.getChildren().addAll(children);
        return this;
    }
    /**
     * CFVFormにObjectを追加する.
     * @param children 追加するObject
     * @return 自身のオブジェクト
     */
    public CFVForm add(Object... children) {
        for (Object child : children) {
            if (child instanceof Builder) {
                this.getChildren().add((Node)((Builder)child).build());
            }
            else {
                this.getChildren().add((Node)child);
            }
        }
        return this;
    }
    /**
     * CFVFormのalignmentをセットする.
     * @param align alignment
     * @return 自身のオブジェクト
     */
    public CFVForm align(Pos align) {
        this.setAlignment(align);
        return this;
    }
    /**
     * CFVFormのspacingをセットする.
     * @param space spacing
     * @return 自身のオブジェクト
     */
    public CFVForm spacing(double space) {
        this.setSpacing(space);
        return this;
    }
}
