package creamy.scene.control;

import creamy.scene.layout.FormInput;
import javafx.scene.control.Label;

/**
 * CreamyのLabelクラス.
 * <p>
 * Formインターフェースの実装クラス（CFGridForm, CFHFormなど）の要素として使用する。<br>
 * HTMLの&lt;form&gt;タグのINPUT要素を想定しているため、name属性、value属性を保持する。
 * </p>
 * @see creamy.scene.layout.CFGridForm
 * @see creamy.scene.layout.CFHForm
 * @author ahayama
 */
public class CFLabel extends Label implements FormInput {
    private String name;
    private Object value;
    /**
     * CFLabelを生成し、name値をセットする.
     * @param name CFLabel名
     */
    public CFLabel(String name) {
        this.name = name;
    }
    /**
     * INPUT要素のname属性に相当する値をセットする.
     * @param name CFLabel名
     */
    @Override
    public void setCFName(String name) {
        this.name = name;
    }
    /**
     * name値を返す.
     * @return CFLabel名
     */
    @Override
    public String getCFName() {
        return this.name;
    }
    /**
     * INPUT要素のvalue属性に相当するvalue値をセットする.
     * @param value value値
     */
    @Override
    public void setCFValue(Object value) {
        this.value = value;
    }
    /**
     * value値を返す.
     * @return value値
     */
    @Override
    public Object getCFValue() {
        return this.value;
    }

}
