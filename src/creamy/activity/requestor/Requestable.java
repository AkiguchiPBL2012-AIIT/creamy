package creamy.activity.requestor;

import creamy.activity.Activity;

/**
 * Request可能なクラスが実装するインターフェイス
 * 
 * @author miyabetaiji
 */
interface Requestable {
    /**
     * Activityをリクエストする
     * @param path リクエスト先パス
     * @return Requestオブジェクト
     */
    public Request<Activity> requestActivity(String path);
    /**
     * データをリクエストする
     * @param path リクエスト先パス
     * @return Requestオブジェクト
     */
    public Request<Object> requestData(String path);
}
