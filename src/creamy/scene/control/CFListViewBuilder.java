package creamy.scene.control;

import java.util.Map;
import java.util.Set;
import javafx.scene.control.ListViewBuilder;

/**
 * CFListViewを生成するBuilderクラス.
 * build()メソッドが呼ばれてCFListViewオブジェクトが生成されるまでname値を保持する。
 * @see creamy.scene.control.CFListView
 * @author ahayama
 */
@SuppressWarnings("unchecked")
public class CFListViewBuilder<T,B extends CFListViewBuilder<T,B>> extends ListViewBuilder<T,B> {
    
    private String name;
    private Object value;
    private Set set;
    private Map map;
    
    protected CFListViewBuilder() {
    }
    /**
     * CFListViewBuilderオブジェクトを生成する.
     * @return 生成したCFListViewBuilderオブジェクト
     */
    public static CFListViewBuilder<?,?> create() {
        return new CFListViewBuilder();
    }
    /**
     * 生成したCFListViewオブジェクトに、name値をセットする.
     * @param CFListView 
     */
    public void applyTo(CFListView<T> listView) {
        super.applyTo(listView);
        listView.setCFName(name);
        listView.setCFValue(value);
    }
    /**
     * name値をセットする.
     * @param s CFListViewのname値
     * @return 自身のオブジェクト
     */
    public CFListViewBuilder<T,B> name(String s) {
        name = s;
        return this;
    }
    /**
     * value値をセットする.
     * @param value CFChoiceBoxのvalue値
     * @return 自身のオブジェクト
     */
    public CFListViewBuilder<T,B> value(Object value) {
        this.value = value;
        return this;
    }
    /**
     * CFListViewオブジェクトを生成し、name値をセットする.
     * @return 生成したCFListViewオブジェクト
     */
    @Override
    public CFListView<T> build() {
        CFListView listView = new CFListView(name);
        if (this.set != null) {
            listView.items(this.set);
        }
        if (this.map != null) {
            listView.items(this.map);
        }
        applyTo(listView);
        return listView;
    }
    /**
     * CFListViewの要素をitems値にセットする.
     * @param items CFListView要素
     * @return 
     */
    public CFListViewBuilder<T,B> items(Set<T> entrySet) {
        this.set = entrySet;
        return this;
    }
    /**
     * CFListViewの要素をitems値にセットする.
     * @param map CFListView要素
     * @return 
     */
    public CFListViewBuilder<T,B> items(Map map) {
        this.map = map;
        return this;
    }
}
