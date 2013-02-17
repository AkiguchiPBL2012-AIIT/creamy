package creamy.scene.control;

import javafx.scene.control.ButtonBuilder;

/**
 * CFLinkButtonを生成するBuilderクラス.
 * build()メソッドが呼ばれてCFLinkButtonオブジェクトが生成されるまでCFLinkButtonの変数値を保持する。
 * @see creamy.scene.control.CFLinkButton
 * @author miyabetaiji
 */
public class CFLinkButtonBuilder<B extends CFLinkButtonBuilder<B>> extends ButtonBuilder<B> {

    private String path;
    
    /**
     * CFLinkButtonBuilderコンストラクタ.
     */
    protected CFLinkButtonBuilder() {}
        
    /**
     * CFLinkButtonBuilderオブジェクトを生成する.
     * @return 生成したCFLinkButtonBuilderオブジェクト
     */
    public static CFLinkButtonBuilder<?> create() {
        return new CFLinkButtonBuilder();
    }

    /**
     * path値をセットする.
     * @param value CFLinkButtonBuilderのpath値
     * @return 自身のオブジェクト
     */
    public CFLinkButtonBuilder<B> path(String path) {
        this.path = path;
        return this;
    }
    
    /**
     * 生成したCFLinkButtonオブジェクトに、path値をセットする.
     * @param button CFLinkButtonオブジェクト
     */
    public void applyTo(CFLinkButton button) {
        super.applyTo(button);
        button.setPath(path);
    }
    
    /**
     * CFLinkButtonオブジェクトを生成する.
     * @return 生成したCFLinkButtonオブジェクト
     */
    @Override
    public CFLinkButton build() {
        CFLinkButton button = new CFLinkButton();
        applyTo(button);
        return button;
    }
}
