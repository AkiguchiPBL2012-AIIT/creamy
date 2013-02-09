package creamy.browser.db;

import javafx.collections.ObservableList;

/**
 * ユーザが作成するEntityの基底クラス。ユーザはこのクラスを拡張してEntityを作成する
 * 
 * @author miyabetaiji
 */
public class Entity {
    /**
     * このエンティティのストア
     */
    protected Store store;
    /**
     * エンティティオブジェクトを一意に識別するid
     */
    protected Integer id;
    
    public Entity() {
        //store = new Store(this);
        store = EntityManager.getInstance().getStore(this);
    }
    /**
     * ストア内のデータをロードする
     */
    protected void load() {
        if (!store.loaded()) store.load();
    }
    /**
     * idを取得する
     * @return id
     */
    public final Integer getId() {
        return id;
    }
    /**
     * idをセットする
     * @param id id
     */
    public final void setId(Integer id) {
        this.id = id;
    }
    /**
     * idで検索する
     * @param id 検索するid
     * @return 見つかったエンティティのインスタンス
     */
    @SuppressWarnings("unchecked")
    public <T> T findById(Integer id) {
        load();
        return (T)store.findById(id);
    }
    /**
     * すべてのエンティティオブジェクトを返す
     * @return 見つかったエンティティのリスト
     */
    @SuppressWarnings("unchecked")
    public <T> ObservableList<T> findAll() {
        load();
        return store.findAll();
    }    
    /**
     * エンティティを保存する
     */
    @SuppressWarnings("unchecked")
    public void save() {
        load();
        store.add(this);
        store.save();
    }    
}
