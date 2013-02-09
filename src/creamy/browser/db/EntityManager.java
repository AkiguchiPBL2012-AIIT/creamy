package creamy.browser.db;

import java.util.HashMap;
import java.util.Map;

/**
 * エンティティストアを管理するクラス。シングルトン
 * 
 * @author miyabetaiji
 */
public class EntityManager {
    private static EntityManager manager = new EntityManager();
    private Map<Class, Store> stores = new HashMap<Class, Store>();

    private EntityManager() {}

    /**
     * エンティティストアマネージャインスタンスを取得する
     * @return manager エンティティストアマネージャインスタンス
     */
    public static EntityManager getInstance() {
        return manager;
    }

    /**
     * エンティティストアを取得する
     * @param obj エンティティ
     * @return エンティティストア
     */
    @SuppressWarnings("unchecked")
    public <T extends Entity> Store getStore(T obj) {
        Class clazz = obj.getClass();
        Store store = stores.get(clazz);
        if (store == null) {
            store = new Store(obj);
        }
        return store;
    }    
}
