package creamy.activity.requestor;

import creamy.mvc.Status;

/**
 * Responseを受信した際にコールバックされるコールバックオブジェクトを表すクラス
 * 
 * @author miyabetaiji
 */
public interface CallBack<T> {
    public void call(T data, Status status);
}
