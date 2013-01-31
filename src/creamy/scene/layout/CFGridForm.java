package creamy.scene.layout;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.util.Builder;

/**
 * CreamyのGridPaneクラス.
 * Formインターフェースの実装クラス<br>
 * HTMLの&lt;form&gt;タグを想定しているため、path属性、method属性を保持する。
 * @author ahayama
 */
public class CFGridForm extends GridPane implements Form {
    
    private String path;
    private String method;
    private int rowIndex;
    private int colIndex;
    
    public CFGridForm() {}
    
    /**
     * CFGridFormオブジェクトを生成する.
     * @param path パス
     */
    public CFGridForm(String path) {
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
     * １行分のNodeを追加する.
     * @param children 追加するNode
     * @return 自身のオブジェクト
     */
    public CFGridForm row(Node... children) {
        this.addRow(rowIndex++, children);
        return this;
    }
    /**
     * １行分のNodeを追加する.
     * 追加する要素がBuilderの場合、Nodeオブジェクトを生成して追加する。
     * @param children 追加するNode
     * @return 自身のオブジェクト
     */
    public CFGridForm row(Object... children) {
        colIndex = 0;
        for (Object child : children) {
            if (child instanceof Builder) {
                this.add((Node)((Builder)child).build(), colIndex++, rowIndex);
            }
            else {
                this.add((Node)child, colIndex++, rowIndex);
            }
        }
        rowIndex++;
        return this;
    }
    /**
     * １行分のNodeを追加する.
     * @return 自身のオブジェクト
     */
    public CFGridForm row() {
        rowIndex++;
        return this;
    }
    /**
     * １行分のNodeを追加し、Node間隔をセットする.
     * @param child 追加するNode
     * @param colSpan Node間隔
     * @return 自身のオブジェクト
     */
    public CFGridForm row(Node child, int colSpan) {
        this.addRow(rowIndex++, child);
        setColumnSpan(child, colSpan);
        return this;
    }
    /**
     * １行分のNodeを追加し、Node間隔をセットする.
     * 追加する要素がBuilderの場合、Nodeオブジェクトを生成して追加する。
     * @param child 追加するNode
     * @param colSpan Node間隔
     * @return 自身のオブジェクト
     */
    public CFGridForm row(Object child, int colSpan) {
        if (child instanceof Builder) {
            Node node = (Node)((Builder)child).build();
            this.addRow(rowIndex++, node);
            setColumnSpan(node, colSpan);
        }
        else {
            this.addRow(rowIndex++, (Node)child);
            setColumnSpan((Node)child, colSpan);
        }
        return this;
    }
    /**
     * １列分のNode要素を追加し、行間隔をセットする.
     * @param child 追加するNode
     * @param rowSpan 行間隔
     * @return 自身のオブジェクト
     */
    public CFGridForm column(Node child, int rowSpan) {
        this.addRow(rowIndex, child);
        setRowSpan(child, rowSpan);
        return this;
    }
    /**
     * １列分のNode要素を追加し、行間隔をセットする.
     * 追加する要素がBuilderの場合、Nodeオブジェクトを生成して追加する。
     * @param child 追加するNode
     * @param rowSpan 行間隔
     * @return 自身のオブジェクト
     */
    public CFGridForm column(Object child, int rowSpan) {
        if (child instanceof Builder) {
            Node node = (Node)((Builder)child).build();
            this.addRow(rowIndex, node);
            setRowSpan(node, rowSpan);
        }
        else {
            this.addRow(rowIndex, (Node)child);
            setRowSpan((Node)child, rowSpan);
        }
        return this;
    }
    /**
     * 行間、列間をセットする.
     * @param hgap 列間
     * @param vgap 行間
     * @return 自身のオブジェクト
     */
    public CFGridForm gaps(int hgap, int vgap) {
        this.setHgap(hgap);
        this.setVgap(vgap);
        return this;
    }
    /**
     * Paddingをセットする.
     * @param padding
     * @return 自身のオブジェクト
     */
    public CFGridForm padding(int padding) {
        this.setPadding(new Insets(padding));
        return this;
    }
    /**
     * パスをセットする.
     * @param path パス
     * @return 自身のオブジェクト
     */
    public CFGridForm path(String path) {
        setPath(path);
        return this;
    }
    /**
     * メソッド（GET/POST）をセットする.
     * @param method メソッド
     * @return 自身のオブジェクト
     */
    public CFGridForm method(String method) {
        setMethod(method);
        return this;
    }
    /**
     * スタイルクラスをセットする.
     * @param classes スタイルクラス名
     * @return 自身のオブジェクト
     */
    public CFGridForm styleClass(String... classes) {
        this.getStyleClass().addAll(classes);
        return this;
    }
    /**
     * 最小幅をセットする.
     * @param size 最小幅
     * @return 自身のオブジェクト
     */
    public CFGridForm minWidth(int size) {
        this.setMinWidth(size);
        return this;
    }
}
