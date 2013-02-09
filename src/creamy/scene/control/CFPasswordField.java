package creamy.scene.control;

import creamy.scene.layout.FormInput;
import java.lang.reflect.Method;
import java.text.Format;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.PasswordField;

/**
 * CreamyのPasswordFieldクラス.
 * <p>
 * Formインターフェースの実装クラス（CFGridForm, CFHFormなど）の要素として使用する。<br>
 * HTMLの&lt;form&gt;タグのINPUT要素を想定しているため、name属性、format属性を保持する。
 * </p>
 * @see creamy.scene.layout.CFGridForm
 * @see creamy.scene.layout.CFHForm
 * @author ahayama
 */
public class CFPasswordField extends PasswordField implements FormInput {
    private String name;
    private Format format;

    public CFPasswordField() {}
    /**
     * CFPasswordFieldを生成し、name値をセットする.
     * @param name CFPasswordField名
     */
    public CFPasswordField(String name) {
        this.name = name;
    }
    /**
     * INPUT要素のname属性に相当する値をセットする.
     * @param name CFName名
     */
    @Override
    public void setCFName(String name) {
        this.name = name;
    }
    /**
     * name値を返す.
     * @return CFName名
     */
    @Override
    public String getCFName() {
        return this.name;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    /**
     * INPUT要素のvalue属性に相当するvalue値をセットする.
     * Formatオブジェクトがあればフォーマット処理する。
     * @param value value値
     */
    @Override
    public void setCFValue(Object value) {
        if (value == null) return;
        if (format == null) {
            this.setText(value.toString());
            return;
        }
        try {
            Method[] methods = format.getClass().getMethods();
            List<Method> formats = new ArrayList<Method>();
            for (Method m : methods)
                if (m.getName().equals("format")) formats.add(m);
                    //System.out.println("okokoko");
            for (Method f : formats) {
                if (f.getParameterTypes().length == 1 && f.getParameterTypes()[0] == value.getClass()) {
                    this.setText(f.invoke(format, value).toString());
                    return;
                }
            }
            for (Method f : formats) {
                if (f.getParameterTypes().length == 1)
                   this.setText((f.invoke(format, value).toString()));
            }
        } catch (Exception ex) {
            Logger.getLogger(CFPasswordField.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * value値を返す.
     * @return value値
     */
    @Override
    public Object getCFValue() {
        if (format == null) return this.getText();
        try {
            Method pasrse = format.getClass().getMethod("parse", String.class);
            return pasrse.invoke(format, this.getText());
        } catch (Exception ex) {
            Logger.getLogger(CFPasswordField.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    /**
     * フォーマット処理するFormatオブジェクトをセットする.
     * @param format Formatオブジェクト
     */
    public void setFormat(Format format) {
        this.format = format;
    }

}
