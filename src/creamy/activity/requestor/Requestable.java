package creamy.activity.requestor;

import creamy.activity.Activity;

/**
 * Request可能なクラスが実装するインターフェイス
 * @author miyabetaiji
 * @see Requestor
 */
interface Requestable {
    public Request<Activity> requestActivity(String path);
    public Request<Object> requestData(String path);
}
