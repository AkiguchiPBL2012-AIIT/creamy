package creamy.scene.control;

import javafx.scene.control.ButtonBuilder;

/**
 * CFButtonを生成するBuilderクラス.
 * build()メソッドが呼ばれてCFButtonオブジェクトが生成されるまでCFButtonの変数値を保持する。
 * @see creamy.scene.control.CFButton
 * @author ahayama
 */
public class CFButtonBuilder<B extends CFButtonBuilder<B>> extends ButtonBuilder<B> {
    
    private String name;

    protected CFButtonBuilder() {
    }
    /**
     * CFButtonBuilderオブジェクトを生成する.
     * @return CFButtonBuilderオブジェクト
     */
    public static CFButtonBuilder<?> create() {
        return new CFButtonBuilder();
    }
    /**
     * ボタンタイトルをname変数にセットする.
     * @param s ボタンタイトル
     * @return 自身のオブジェクト
     */
    public CFButtonBuilder<B> name(String s) {
        name = s;
        return this;
    }
    /**
     * 生成したCFButtonオブジェクトに、name値をセットする.
     * @param button 生成したCFButtonオブジェクト
     */
    public void applyTo(CFButton button) {
        super.applyTo(button);
        button.setName(name);
    }
    /**
     * CFButtonオブジェクトを生成し、name値をセットする.
     * @return 生成したCFButtonオブジェクト
     */
    @Override
    public CFButton build() {
        CFButton button = new CFButton(name);
        applyTo(button);
        return button;
    }
}
