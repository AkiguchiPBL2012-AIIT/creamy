package creamy.scene.control;

import javafx.scene.control.LabelBuilder;

/**
 * CFLabelを生成するBuilderクラス.
 * build()メソッドが呼ばれてCFLabelオブジェクトが生成されるまでname値を保持する。
 * @see creamy.scene.control.CFLabel
 * @author ahayama
 */
public class CFLabelBuilder<B extends CFLabelBuilder<B>> extends LabelBuilder<B> {
    
    private String name;
    
    protected CFLabelBuilder() {
    }
    /**
     * CFLabelBuilderオブジェクトを生成する.
     * @return 生成したCFLabelBuilderオブジェクト
     */
    public static CFLabelBuilder<?> create() {
        return new CFLabelBuilder();
    }
    /**
     * 生成したCFLabelオブジェクトに、name値をセットする.
     * @param CFLabel 
     */
    public void applyTo(CFLabel label) {
        super.applyTo(label);
        label.setCFName(name);
    }
    /**
     * name値をセットする.
     * @param s CFLabelのname値
     * @return 自身のオブジェクト
     */
    public CFLabelBuilder<B> name(String s) {
        name = s;
        return this;
    }
    /**
     * CFLabelオブジェクトを生成し、name値をセットする.
     * @return 生成したCFLabelオブジェクト
     */
    @Override
    public CFLabel build() {
        CFLabel label = new CFLabel(name);
        applyTo(label);
        return label;
    }
}
