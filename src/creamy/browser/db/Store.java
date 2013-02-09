package creamy.browser.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.arnx.jsonic.JSON;

/**
 * エンティティストア。エンティティのロード、変更、追加、削除を行う
 * 
 * @author miyabetaiji
 * @param <T> 
 */
public class Store<T extends Entity> {
    /**
     * ファイル保存先のルート
     */
    private static final String rootPath ="";
    /**
     * ロードしたエンティティ。リスト形式
     */
    private ObservableList<T> pojos;
    /**
     * エンティティの型
     */
    private T type;
    /**
     * エンティティの保存先ファイル
     */
    private File saveFile;
    /**
     * ロード状態
     */
    private boolean loaded;
    
    public Store(T pojo) {
        this.pojos = FXCollections.observableArrayList();
        this.type = pojo;
        this.saveFile = getFile();
        this.loaded = false;
    }
    
    /**
     * ロード状態を取得する
     * @return ロード状態
     */
    public boolean loaded() {
        return loaded;
    }
    /**
     * エンティティをファイルからロードする
     */
    public synchronized void load() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(saveFile));
            String line;
            while ((line = reader.readLine()) != null)
            {
                T pojo = decode(line);
                pojos.add(pojo);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Store.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Store.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(Store.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        loaded = true;
    }
    /**
     * エンティティを一括で保存する
     */
    public synchronized void save() {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(saveFile);
            for (Iterator<T> it = pojos.iterator(); it.hasNext();)
            {
                T elem = it.next();
                String jsonString = encode(elem);
                pw.println(jsonString + ",");
            }
            pw.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Store.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            pw.close();
        }
    }
    /**
     * エンティティを追加する
     * @param element エンティティ
     */
    public synchronized void add(T element) {
        element.setId(getNewId());
        pojos.add(element);
    }
    /**
     * エンティティを削除する
     * @param element エンティティ
     */
    public synchronized void remove(T element) {
        pojos.remove(element);
    }
    /**
     * エンティティをすべて取得する
     * @return すべてのエンティティ。リスト形式
     */
    public ObservableList<T> findAll() {
        return pojos;
    }
    /**
     * idを指定してエンティティを検索する
     * @param id 検索するキー(id)
     * @return 見つかったエンティティ
     */
    public T findById(Integer id) {
        for (Iterator<T> it = pojos.iterator(); it.hasNext();)
        {
            T pojo = it.next();
            if (pojo.getId() == id)
                return pojo;
        }
        return null;
    }

    private File getFile() {
        String path = getPath();
        saveFile = new File(path);
        if (!saveFile.exists()) {
            try {
                saveFile.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Store.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return saveFile;
    }
    
    private String getPath()
    {
        return rootPath + type.getClass().getName() + ".json";
    }
    
    private String encode(T pojo) {
        return JSON.encode(pojo);
    }
    
    @SuppressWarnings("unchecked")
    private T decode(String line) {
        return (T) JSON.decode(line.substring(0, line.length() -1), type.getClass());
    }
    
    private int getNewId() {
        if (pojos.isEmpty())
            return 0;
        else
            return getMaxId() + 1;
    }
    
    private int getMaxId() {
        Integer max = 0;
        for (Iterator<T> it = pojos.iterator(); it.hasNext();)
        {
            T elem = it.next();
            if (elem.getId() > max)
                max = elem.getId();
        }
        return max;
    }    
}
