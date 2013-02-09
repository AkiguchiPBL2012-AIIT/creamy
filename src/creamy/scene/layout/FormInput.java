package creamy.scene.layout;

/**
 * HTMLの&lt;form&gt;タグのINPUT要素に相当するインターフェース.
 * <p>
 * CFNameはINPUT要素のname、CFValueはINPUT要素のvalueにあたる。
 * </p>
 */
public interface FormInput {
    /**
     * name値を返す.
     * @return CFName名
     */
    public String getCFName();
    /**
     * INPUT要素のname属性に相当する値をセットする.
     * @param name CFName名
     */
    public void setCFName(String name);
    /**
     * value値を返す.
     * @return value値
     */
    public Object getCFValue();
    /**
     * INPUT要素のvalue属性に相当するvalue値をセットする.
     * @param value value値
     */
    public void setCFValue(Object value);
}
