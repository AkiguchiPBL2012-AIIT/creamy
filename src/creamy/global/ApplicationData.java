package creamy.global;

import java.util.HashMap;
import java.util.Map;

/**
 * Application全体をスコープとするグローバルオブジェクト
 * データはMap形式で保持
 * 
 * @author Taiji Miyabe (Professor Akiguchi's PBL 2012, AIIT)
 */
public class ApplicationData {
    /**
     * シングルトンインスタンス
     */
    private static ApplicationData instance = new ApplicationData();
    /**
     * データオブジェクト
     */
    private Map<String,Object> data = new HashMap<String,Object>();

    /**
     * シングルトン
     */
    private ApplicationData() {}

    /**
     * インスタンス取得メソッド
     * creamy.browser.Browserから呼び出される
     * @return instance
     * @see creamy.Browser.browser
     */
    public static ApplicationData getInstance() { return instance; }

    /**
     * データオブジェクト(Map)を返す
     * @return data データオブジェクト(Map)
     */
    public Map<String,Object> getData() { return data; }
}
