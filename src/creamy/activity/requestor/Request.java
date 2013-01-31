package creamy.activity.requestor;

import creamy.activity.Activity;
import creamy.browser.Broker;
import java.util.Map;

/**
 * Requesを表すクラス
 * path, parameters, callbackオブジェクトをメソッドチェーンで取得し、
 * executeの呼び出しによってRequestを送信する
 * 
 * @author miyabetaiji
 */
public abstract class Request<T> {
    /**
     * Request送信/Response受信に使用するBroker
     */
    protected Broker broker;
    /**
     * Request送信先のパス
     */
    protected String path;
    /**
     * Reqestパラメータ
     */
    protected Map<String,Object> params;
    /**
     * Requestが成功した際にコールバックされるコールバックオブジェクト
     */
    protected CallBack<T> successCallBack;
    /**
     * Requestが失敗した際にコールバックされるコールバックオブジェクト
     */
    protected CallBack<T> failCallBack;
    /**
     * 子ActivityのParent
     */
    protected Activity parent;
    
    /**
     * Requestorから生成される
     * @param broker 
     * @see Requestor
     */
    Request(Broker broker) { this.broker = broker; }

    /**
     * pathを取得する
     * @return path
     */
    public String getPath() { return this.path; }

    /**
     * pathを設定する
     * @return path
     */
    public void setPath(String path) { this.path = path; }
    
    /**
     * paramsを取得する
     * @return params
     */
    public Map<String,Object> getParams() { return this.params; }
    
    /**
     * parentを取得する
     * @return parent
     */
    public Activity getParent() { return this.parent; }

    /**
     * parentを設定する
     * @return path
     */
    public void setParent(Activity parent) { this.parent = parent; }
    
    /**
     * paramsを設定する
     * @param params
     */
    public void setParams(Map<String,Object> params) { this.params = params; }
        
    /**
     * paramsを設定する(メソッドチェーン)
     * @param params
     * @return request
     */
    public Request<T> params(Map<String,Object> params) {
        setParams(params);
        return this;
    }
        
    /**
     * successCallBackを取得する
     * @return successCallBack
     */
    public CallBack<T> getOnSuccess() { return this.successCallBack; }

    /**
     * successCallBackを設定する
     * @param successCallBack
     */
    public void setOnSuccess(CallBack<T> callBack) { this.successCallBack = callBack; }

    /**
     * successCallBackを設定する(メソッドチェーン)
     * @param callBack
     * @return request
     */
    public Request<T> onSuccess(CallBack<T> callBack) {
        setOnSuccess(callBack);
        return this;
    }

    /**
     * failCallBackを取得する
     * @return failCallBack
     */
    public CallBack<T> getOnFail() { return this.failCallBack; }

    /**
     * failCallBackを設定する
     * @param callBack 
     */
    public void setOnFail(CallBack<T> callBack) { this.failCallBack = callBack; }

    /**
     * failCallBackを設定する(メソッドチェーン)
     * @param callBack
     * @return request
     */
    public Request<T> onFail(CallBack<T> callBack) {
        setOnFail(callBack);
        return this;
    }
    /**
     * Requestを送信する
     * Responseを受けた後コールバックオブジェクトを呼び出す
     */
    public abstract void execute();
}
