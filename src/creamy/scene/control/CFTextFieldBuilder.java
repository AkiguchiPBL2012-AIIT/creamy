package creamy.scene.control;

import java.text.Format;
import javafx.scene.control.TextFieldBuilder;

/**
 * CFTextFieldを生成するBuilderクラス.
 * build()メソッドが呼ばれてCFTextFieldオブジェクトが生成されるまでCFTextFieldの変数値を保持する。
 * @see creamy.scene.control.CFTextField
 * @author ahayama
 */
public class CFTextFieldBuilder<B extends CFTextFieldBuilder<B>> extends TextFieldBuilder<B> {
    
    private String name;
    private Object value;
    private Format format;
    
    protected CFTextFieldBuilder() {
    }
    /**
     * CFTextFieldオブジェクトを生成する.
     * @return 生成したCFTextFieldオブジェクト
     */
    public static CFTextFieldBuilder<?> create() {
        return new CFTextFieldBuilder();
    }
    /**
     * name値をセットする.
     * @param s CFTextFieldのname値
     * @return 自身のオブジェクト
     */
    public CFTextFieldBuilder<B> name(String s) {
        name = s;
        return this;
    }
    /**
     * value値をセットする.
     * @param value CFTextFieldのvalue値
     * @return 自身のオブジェクト
     */
    public CFTextFieldBuilder<B> value(Object value) {
        this.value = value;
        return this;
    }
    /**
     * format値をセットする.
     * @param format Formatオブジェクト
     * @return 
     */
    public CFTextFieldBuilder<B> format(Format format) {
        this.format = format;
        return this;
    }
    /**
     * 生成したCFTextFieldオブジェクトに、name値、format値、value値をセットする.
     * @param choicebox 
     */
    public void applyTo(CFTextField textfield) {
        super.applyTo(textfield);
        textfield.setCFName(name);
        textfield.setFormat(format);
        textfield.setCFValue(value);
    }
    /**
     * CFTextFieldオブジェクトを生成し、name値をセットする.
     * @return 生成したCFTextFieldオブジェクト
     */
    @Override
    public CFTextField build() {
        CFTextField textfield = new CFTextField(name);
        applyTo(textfield);
        return textfield;
    }
}
