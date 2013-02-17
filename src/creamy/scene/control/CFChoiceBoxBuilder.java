package creamy.scene.control;

import java.util.Map;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.ChoiceBoxBuilder;

/**
 * CFChoiceBoxを生成するBuilderクラス.
 * build()メソッドが呼ばれてCFChoiceBoxオブジェクトが生成されるまでCFChoiceBoxの変数値を保持する。
 * @see creamy.scene.control.CFChoiceBox
 * @author ahayama
 */
@SuppressWarnings("unchecked")
public class CFChoiceBoxBuilder<T,B extends CFChoiceBoxBuilder<T,B>> extends ChoiceBoxBuilder<T,B> {
    
    private String name;
    private Object value;
    private T[] items;
    private Map map;
    private ChangeListener listener;
    private ChangeListener indexlistener;
    private ChangeListener itemlistener;
    
    protected CFChoiceBoxBuilder() {
    }
    /**
     * CFChoiceBoxBuilderオブジェクトを生成する.
     * @return 生成したCFChoiceBoxBuilderオブジェクト
     */
    public static CFChoiceBoxBuilder<?,?> create() {
        return new CFChoiceBoxBuilder();
    }
    /**
     * name値をセットする.
     * @param s CFChoiceBoxのname値
     * @return 自身のオブジェクト
     */
    public CFChoiceBoxBuilder<T,B> name(String s) {
        name = s;
        return this;
    }
    /**
     * value値をセットする.
     * @param value CFChoiceBoxのvalue値
     * @return 自身のオブジェクト
     */
    @Override
    public CFChoiceBoxBuilder<T,B> value(Object value) {
        this.value = value;
        return this;
    }
    /**
     * 生成したCFChoiceBoxオブジェクトに、name値、value値をセットする.
     * @param choicebox 
     */
    public void applyTo(CFChoiceBox<T> choicebox) {
        super.applyTo(choicebox);
        choicebox.setCFName(name);
        choicebox.setCFValue(value);
    }
    /**
     * CFChoiceBoxオブジェクトを生成し、name値、items値、ChangeListenerをセットする.
     * @return 生成したCFChoiceBoxオブジェクト
     */
    @Override
    public CFChoiceBox<T> build() {
        CFChoiceBox choicebox = new CFChoiceBox(name);
        if (this.items != null) {
            choicebox.items(this.items);
        }
        if (this.map != null) {
            choicebox.items(this.map);
        }
        if (this.listener != null) {
            choicebox.addListener(this.listener);
        }
        if (this.indexlistener != null) {
            choicebox.addIndexListener(this.indexlistener);
        }
        if (this.itemlistener != null) {
            choicebox.addItemListener(this.itemlistener);
        }
        this.applyTo(choicebox);
        return choicebox;
    }
    /**
     * CFChoiceBoxの要素をitems値にセットする.
     * @param items CFChoiceBox要素
     * @return 
     */
    public CFChoiceBoxBuilder<T,B> items(T... items) {
        this.items = items;
        return this;
    }
    /**
     * CFChoiceBoxの要素をitems値にセットする.
     * @param map CFChoiceBox要素
     * @return 
     */
    public CFChoiceBoxBuilder<T,B> items(Map map) {
        this.map = map;
        return this;
    }
    /**
     * ChoiceBoxのListenerをlistener値にセットする.
     * @param listener ChangeListener
     * @return 自身のオブジェクト
     */
    public CFChoiceBoxBuilder<T,B> addListener(ChangeListener listener) {
        this.listener = listener;
        return this;
    }
    /**
     * ChoiceBoxのIndexListenerをlistener値にセットする
     * @param listener ChangeListener
     * @return 自身のオブジェクト
     */
    public CFChoiceBoxBuilder<T,B> addIndexListener(ChangeListener listener) {
        this.indexlistener = listener;
        return this;
    }
    /**
     * ChoiceBoxのItemListenerをlistener値にセットする
     * @param listener ChangeListener
     * @return 自身のオブジェクト
     */
    public CFChoiceBoxBuilder<T,B> addItemListener(ChangeListener listener) {
        this.itemlistener = listener;
        return this;
    }

}
