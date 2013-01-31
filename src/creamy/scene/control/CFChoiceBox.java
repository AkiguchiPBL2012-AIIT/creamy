package creamy.scene.control;

import creamy.scene.layout.FormInput;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;

/**
 * CreamyのChoiceBoxクラス.
 * <p>
 * Formインターフェースの実装クラス（CFGridForm, CFHFormなど）の要素として使用する。<br>
 * HTMLの&lt;form&gt;タグのINPUT要素を想定しているため、name属性、value属性を保持する。<br>
 * ChoiceBoxの要素(item)はMapで保持する。
 * </p>
 * Example:
 * <pre><code>
 * 
 * // ChoiceBoxの要素をMapで渡す
 * HashMap<String, String> choiceMap = new HashMap<String, String>();
 * choiceMap.put("001", "aaa");
 * choiceMap.put("002", "bbb");
 * choiceMap.put("003", "ccc");
 * 
 * // CFChoiceBoxのitemを取得
 * ChangeListener<String> choiceItemListener = new ChangeListener<String>() {
 *     public void changed(ObservableValue<? extends String> ov, String value, String newValue) {
 *         System.out.println("ChangeListener.. " + value + " => " + newValue);
 *     }
 * };
 * 
 * form.getChildren().add(
 *   gridForm(...)
 *      .row(choice("company").items(choiceMap).addItemListener(choiceItemListener).value("003"))
 * );
 * </code></pre>
 * @see creamy.scene.layout.CFGridForm
 * @see creamy.scene.layout.CFHForm
 * @author ahayama
 */
@SuppressWarnings("unchecked")
public class CFChoiceBox<T> extends ChoiceBox<T> implements FormInput {
    private String name;
    private Object value;
    private Map map;
    
    /**
     * CFChoiceBoxを生成し、name値をセットする.
     * @param name CFChoiceBox名
     */
    public CFChoiceBox(String name) {
        this.name = name;
    }
    /**
     * INPUT要素のname属性に相当する値をセットする.
     * @param name CFChoiceBox名
     */
    @Override
    public void setCFName(String name) {
        this.name = name;
    }
    /**
     * name値を返す.
     * @return CFChoiceBox名
     */
    @Override
    public String getCFName() {
        return this.name;
    }
    /**
     * INPUT要素のvalue属性に相当するvalue値をセットする.<br>
     * セットされた要素が選択状態になる。
     * @param value value値
     */
    @Override
    public void setCFValue(Object value) {
        if (map != null) {
            this.getSelectionModel().select((T)map.get(value));
        }
        else {
            this.getSelectionModel().select((T)value);
        }
        this.value = (T)value;
    }
    /**
     * 選択された要素のvalue値を返す.
     * @return value値
     */
    @Override
    public Object getCFValue() {
        if (map != null) {
            Set entrySet = map.entrySet();  
            Iterator entryIte = entrySet.iterator();  
            while(entryIte.hasNext()) {  
                Map.Entry obj = (Map.Entry)entryIte.next();  
                if (obj.getValue().equals((String)this.getValue())) {
                    return obj.getKey();
                }
            }
        }
        else {
            return this.getValue();
        }
        return null;
    }
    /**
     * ChoiceBoxの要素(item)をセットする.
     * @param items ChoiceBoxの要素
     * @return 自身のオブジェクト
     */
    public CFChoiceBox items(T... items) {
        this.setItems(FXCollections.observableArrayList(items));
        this.getSelectionModel().selectFirst();
        return this;
    }
    /**
     * ChoiceBoxの要素(item)をセットする.
     * @param map ChoiceBoxの要素のMap
     * @return 自身のオブジェクト
     */
    public CFChoiceBox items(Map<T, T> map) {
        this.map = map;
        this.setItems(FXCollections.observableArrayList(map.values()));
        this.getSelectionModel().selectFirst();
        return this;
    }
    /**
     * ChoiceBoxの選択が変更されたときのChangeListenerをセットする。
     * @param listener ChangeListener
     * @return 自身のオブジェクト
     */
    public CFChoiceBox addListener(ChangeListener listener) {
        this.getSelectionModel().selectedItemProperty().addListener(listener);
        return this;
    }
    /**
     * ChoiceBoxの選択が変更されたときのChangeListenerをセットする。
     * Example:
     * <pre><code>
     * // CFChoiceBoxのindexを取得
     * ChangeListener<Number> choiceIndexListener = new ChangeListener<Number>() {
     *     public void changed(ObservableValue<? extends Number> ov, Number value, Number newValue) {
     *         System.out.println("ChangeListener.. " + value + " => " + newValue);
     *     }
     * };
     * </code></pre>
     * @param listener ChangeListener
     * @return 自身のオブジェクト
     */
    public CFChoiceBox addIndexListener(ChangeListener listener) {
        this.getSelectionModel().selectedIndexProperty().addListener(listener);
        return this;
    }
    /**
     * ChoiceBoxの選択が変更されたときのChangeListenerをセットする。
     * @param listener ChangeListener
     * @return 自身のオブジェクト
     */
    public CFChoiceBox addItemListener(ChangeListener listener) {
        this.getSelectionModel().selectedItemProperty().addListener(listener);
        return this;
    }

}
