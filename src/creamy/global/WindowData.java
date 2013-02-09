package creamy.global;

import creamy.browser.Browser;
import java.util.HashMap;
import java.util.Map;

/**
 * Window(Browser)をスコープとするグローバルオブジェクト
 * データはMap形式で保持
 * 
 * @author Taiji Miyabe (Professor Akiguchi's PBL 2012, AIIT)
 */
public class WindowData {
    /**
     * シングルトンインスタンス
     */
    private static WindowData instance = new WindowData();
    /**
     * データオブジェクト。Browserごとに1つMapオブジェクトを保持
     */
    private Map<Browser,Map<String,Object>> data = new HashMap<Browser,Map<String,Object>>();

    /**
     * シングルトン
     */
    private WindowData() {}

    /**
     * インスタンス取得メソッド
     * creamy.mvc.browserから呼び出される
     * @return instance
     * @see creamy.mvc.Router
     */
    public static WindowData getInstance() { return instance; }

    /**
     * データオブジェクト(Map)を返す
     * @param browser
     * @return data データオブジェクト(Map)
     */
    public Map<String,Object> getData(Browser browser) {
        Map<String,Object> map = data.get(browser);
        if (map == null) data.put(browser, map = new HashMap<String,Object>());
        return map;
    }

    /**
     * データオブジェクトを削除する
     * @param browser
     */
    public void removeData(Browser browser) {
        Map<String,Object> map = data.get(browser);
        if (map == null) return;
        data.remove(browser);
    }
}
