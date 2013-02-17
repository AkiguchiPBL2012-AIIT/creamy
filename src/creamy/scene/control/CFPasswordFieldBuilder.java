package creamy.scene.control;

import java.text.Format;
import javafx.scene.control.PasswordFieldBuilder;

/**
 * CFPasswordFieldを生成するBuilderクラス.
 * build()メソッドが呼ばれてCFPasswordFieldオブジェクトが生成されるまでCFPasswordFieldの変数値を保持する。
 * @see creamy.scene.control.CFPasswordField
 * @author ahayama
 */
public class CFPasswordFieldBuilder<B extends CFPasswordFieldBuilder<B>> extends PasswordFieldBuilder<B> {
    
    private String name;
    private Object value;
    private Format format;
    
    protected CFPasswordFieldBuilder() {
    }
    /**
     * CFPasswordFieldオブジェクトを生成する.
     * @return 生成したCFPasswordFieldオブジェクト
     */
    public static CFPasswordFieldBuilder<?> create() {
        return new CFPasswordFieldBuilder();
    }
    /**
     * name値をセットする.
     * @param s CFPasswordFieldのname値
     * @return 自身のオブジェクト
     */
    public CFPasswordFieldBuilder<B> name(String s) {
        name = s;
        return this;
    }
    /**
     * value値をセットする.
     * @param value CFPasswordFieldのvalue値
     * @return 自身のオブジェクト
     */
    public CFPasswordFieldBuilder<B> value(Object value) {
        this.value = value;
        return this;
    }
    /**
     * format値をセットする.
     * @param format Formatオブジェクト
     * @return 
     */
    public CFPasswordFieldBuilder<B> format(Format format) {
        this.format = format;
        return this;
    }
    /**
     * 生成したCFPasswordFieldオブジェクトに、name値、format値、value値をセットする.
     * @param choicebox 
     */
    public void applyTo(CFPasswordField passwordfield) {
        super.applyTo(passwordfield);
        passwordfield.setCFName(name);
        passwordfield.setFormat(format);
        passwordfield.setCFValue(value);
    }
    /**
     * CFPasswordFieldオブジェクトを生成し、name値をセットする.
     * @return 生成したCFPasswordFieldオブジェクト
     */
    @Override
    public CFPasswordField build() {
        CFPasswordField passwordfield = new CFPasswordField(name);
        applyTo(passwordfield);
        return passwordfield;
    }
}
