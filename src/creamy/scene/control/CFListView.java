package creamy.scene.control;

import creamy.scene.layout.FormInput;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;

/**
 * CreamyのListViewクラス.
 * <p>
 * Formインターフェースの実装クラス（CFGridForm, CFHFormなど）の要素として使用する。<br>
 * HTMLの&lt;form&gt;タグのINPUT要素を想定しているため、name属性、value属性を保持する。
 * </p>
 * @see creamy.scene.layout.CFGridForm
 * @see creamy.scene.layout.CFHForm
 * @author ahayama
 */
@SuppressWarnings("unchecked")
public class CFListView<T> extends ListView<T> implements FormInput {
    private String name;
    private Object value;
    private Map map;
    
    /**
     * CFListViewを生成し、name値をセットする.
     * @param name CFListView名
     */
    public CFListView(String name) {
        this.name = name;
    }
    /**
     * INPUT要素のname属性に相当する値をセットする.
     * @param name CFListView名
     */
    @Override
    public void setCFName(String name) {
        this.name = name;
    }
    /**
     * name値を返す.
     * @return CFListView名
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
        if (map != null) {
            this.getSelectionModel().select((T)map.get(value));
        }
        else {
            this.getSelectionModel().select((T)value);
        }
        this.value = (T)value;
    }
    /**
     * value値を返す.
     * @return value値
     */
    @Override
    public Object getCFValue() {
        T item = this.getSelectionModel().getSelectedItem();
        if (map != null) {
            Set entrySet = map.entrySet();  
            Iterator entryIte = entrySet.iterator();  
            while(entryIte.hasNext()) {  
                Map.Entry obj = (Map.Entry)entryIte.next();  
                if (obj.getValue().equals(item)) {
                    return obj.getKey();
                }
            }
        }
        else {
            return item;
        }
        return null;
    }
    /**
     * ListViewの要素(item)をセットする.
     * @param entrySet ListViewの要素
     * @return 自身のオブジェクト
     */
    public CFListView items(Set<T> entrySet) {
        this.map = new HashMap();
        Collection list = new ArrayList();
        Iterator entryIte = entrySet.iterator();  
        while(entryIte.hasNext()) {  
            Map.Entry obj = (Map.Entry)entryIte.next();
            map.put(obj.getKey(), obj.getValue());
            list.add(obj.getValue());
        }
        this.setItems(FXCollections.observableArrayList(list));
        this.getSelectionModel().selectFirst();
        return this;
    }
    /**
     * ListViewの要素(item)をセットする.
     * @param map ListViewの要素のMap
     * @return 自身のオブジェクト
     */
    public CFListView items(Map<T, T> map) {
        this.map = map;
        this.setItems(FXCollections.observableArrayList(map.values()));
        this.getSelectionModel().selectFirst();
        return this;
    }

}
