package creamy.scene.control;

import javafx.scene.control.HyperlinkBuilder;

/**
 * CFHyperlinkを生成するBuilderクラス.
 * build()メソッドが呼ばれてCFHyperlinkオブジェクトが生成されるまでCFHyperlinkの変数値を保持する。
 * @see creamy.scene.control.CFHyperlink
 * @author miyabetaiji
 */
public class CFHyperlinkBuilder<B extends CFHyperlinkBuilder<B>> extends HyperlinkBuilder<B> {

    private String path;
    
    /**
     * CFHyperlinkBuilderコンストラクタ.
     */
    protected CFHyperlinkBuilder() {}
        
    /**
     * CFHyperlinkBuilderオブジェクトを生成する.
     * @return 生成したCFHyperlinkBuilderオブジェクト
     */
    public static CFHyperlinkBuilder<?> create() {
        return new CFHyperlinkBuilder();
    }

    /**
     * path値をセットする.
     * @param path CFHyperlinkBuilderのpath値
     * @return 自身のオブジェクト
     */
    public CFHyperlinkBuilder<B> path(String path) {
        this.path = path;
        return this;
    }
    
    /**
     * 生成したCFHyperlinkオブジェクトに、path値をセットする.
     * @param hyperlink CFHyperlinkオブジェクト
     */
    public void applyTo(CFHyperlink hyperlink) {
        super.applyTo(hyperlink);
        hyperlink.setPath(path);
    }
    
    /**
     * CFHyperlinkオブジェクトを生成する.
     * @return 生成したCFHyperlinkオブジェクト
     */
    @Override
    public CFHyperlink build() {
        CFHyperlink hyperlink = new CFHyperlink();
        applyTo(hyperlink);
        return hyperlink;
    }
}
