package creamy.activity.requestor;

/**
 * リクエストのキャンセルを通知する例外クラス
 * 
 * @author miyabetaiji
 * @see HandlerConstructor
 */
public class RequestCancel extends RuntimeException {
    RequestCancel() { super(); }

    RequestCancel(String desc) { super(desc); }

    RequestCancel(String desc, Throwable cause) { super(desc, cause); }

    RequestCancel(Throwable cause) { super(cause); }
}
