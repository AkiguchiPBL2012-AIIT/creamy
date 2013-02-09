package creamy.scene.control;

import creamy.scene.layout.FormInput;
import javafx.scene.control.CheckBox;

/**
 * CreamyのCheckBoxクラス.
 * <p>
 * Formインターフェースの実装クラス（CFGridForm, CFHFormなど）の要素として使用する。<br>
 * HTMLの&lt;form&gt;タグのINPUT要素を想定しているため、name属性を保持する。
 * </p>
 * @see creamy.scene.layout.CFGridForm
 * @see creamy.scene.layout.CFHForm
 */
public class CFCheckBox extends CheckBox implements FormInput {
    private String name;

    /**
     * name値を返す.
     * @return CFCheckBox名
     */
    public String getName() {
        return name;
    }

    /**
     * name属性に値をセットする.
     * @param name CFCheckBox名
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * INPUT要素のname値を返す.
     * @return CFCheckBox名
     */
    @Override
    public String getCFName() {
        return getName();
    }

    /**
     * INPUT要素のname属性に相当する値をセットする.
     * @param name CFCheckBox名
     */
    @Override
    public void setCFName(String name) {
        setName(name);
    }

    /**
     * 要素のvalue値を返す.
     * @return value値
     */
    @Override
    public Object getCFValue() {
        return isSelected();
    }

    /**
     * INPUT要素のvalue属性に相当するvalue値をセットする.<br>
     * @param value value値
     */
    @Override
    public void setCFValue(Object value) {
        if (!(value instanceof Boolean)) return;
        this.setSelected((Boolean)value);
    }
    
}
