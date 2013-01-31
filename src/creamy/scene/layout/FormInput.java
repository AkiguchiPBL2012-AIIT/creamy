package creamy.scene.layout;

/**
 * HTMLの&lt;form&gt;タグのINPUT要素に相当するインターフェース.
 * <p>
 * CFNameはINPUT要素のname、CFValueはINPUT要素のvalueにあたる。
 * </p>
 */
public interface FormInput {
    public String getCFName();
    public void setCFName(String name);
    public Object getCFValue();
    public void setCFValue(Object value);
}
