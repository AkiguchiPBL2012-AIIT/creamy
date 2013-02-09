package creamy.scene.control;

import javafx.scene.control.ButtonBuilder;

/**
 * CFSubmitButtonを生成するBuilderクラス.
 * build()メソッドが呼ばれてCFSubmitButtonオブジェクトが生成されるまでCFSubmitButtonの変数値を保持する。
 * @see creamy.scene.control.CFSubmitButton
 * @author miyabetaiji
 */
public class CFSubmitButtonBuilder<B extends CFSubmitButtonBuilder<B>> extends ButtonBuilder<B> {
    
    /**
     * CFSubmitButtonBuilderコンストラクタ.
     */
    protected CFSubmitButtonBuilder() {}
    
    /**
     * CFSubmitButtonBuilderオブジェクトを生成する.
     * @return 生成したCFSubmitButtonBuilderオブジェクト
     */
    public static CFSubmitButtonBuilder<?> create() {
        return new CFSubmitButtonBuilder();
    }

    /**
     * 生成したCFSubmitButtonオブジェクトに、CFButtonオブジェクトをセットする.
     * @param textfield CFButtonオブジェクト
     */
    public void applyTo(CFButton textfield) {
        super.applyTo(textfield);
    }
    
    /**
     * CFSubmitButtonオブジェクトを生成する.
     * @return 生成したCFSubmitButtonオブジェクト
     */
    @Override
    public CFSubmitButton build() {
        CFSubmitButton button = new CFSubmitButton();
        applyTo(button);
        return button;
    }
}
