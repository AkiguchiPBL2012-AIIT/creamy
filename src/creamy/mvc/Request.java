package creamy.mvc;

import creamy.browser.Browser;
import java.util.HashMap;
import java.util.Map;

/**
 * Requestオブジェクト
 * 
 * @author Taiji Miyabe (Professor Akiguchi's PBL 2012, AIIT)
 */
public class Request {
    public static final String GET = "GET";
    public static final String POST = "POST";
    
    /**
     * Browser
     */
    private Browser browser;
    /**
     * Method(GET/POST)
     */
    private String method;
    /**
     * Path(/xxx/xxx/xxx)
     */
    private String path;
    /**
     * Form paramter
     */
    private Map<String, Object> params;

    private String senderPath; //送り元のページパス
    private boolean isDataRequest; //データリクエスト判別
    /**
     * Requestを生成する
     */
    //public Request() {}
    
    /**
     * Requestを生成する
     * @param method
     * @param path
     */
    public Request(Browser browser, String method, String path, String senderPath) {
        this(browser, method, path, new HashMap<String, Object>(), senderPath);
    }

    /**
     * Requestを生成する
     * @param method
     * @param path
     * @param params
     */
    public Request(Browser browser, String method, String path, Map<String, Object> params, String senderPath) {
        this(browser, method, path, params, senderPath, false);
    }
    
    /**
     * Requestを生成する
     * @param method
     * @param path
     * @param params
     */
    public Request(Browser browser, String method, String path, Map<String, Object> params, String senderPath,boolean isDataRequest) {
        this.browser = browser;
        this.method = method;
        this.path = path;
        this.params = params;
        this.senderPath = senderPath;
        this.isDataRequest = isDataRequest;
    }
    
    
    /**
     * Browserを取得する
     * @return browser
     */
    public Browser getBrowser() {
        return browser;
    }

    /**
     * Browserを取得する
     * @return browser
     */
    public void setBrowser(Browser browser) {
        this.browser = browser;
    }
    
    /**
     * Methodを取得する
     * @return method
     */
    public String getMethod() {
        return method;
    }

    /**
     * Methodを設定する
     * @param method
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * Parametersを取得する
     * @return params
     */
    public Map<String, Object> getParams() {
        return params;
    }

    /**
     * Parametersを設定する
     * @return params
     */
    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
 
    /**
     * Pathを取得する
     * @return path
     */
    public String getPath(){
        return path;
    }
    
    /**
     * Pathを設定する
     * @param path
     */
    public void setPath(String path) {
        this.path = path;
    }
    
    /**
     * リクエストの送信元パス
     * @return 
     */
    public String getSenderPath() {
        return this.senderPath;
    }

    /**
     * @return the isDataRequest
     */
    public boolean isDataRequest() {
        return isDataRequest;
    }

    /**
     * @param isDataRequest the isDataRequest to set
     */
    public void setIsDataRequest(boolean isDataRequest) {
        this.isDataRequest = isDataRequest;
    }
}
