package creamy.activity.requestor;

import creamy.browser.Broker;
import creamy.mvc.Response;
import creamy.mvc.Status;

/**
 * データリクエストを表すクラス
 * 
 * @author miyabetaiji
 */
public class DataRequest extends Request<Object> {
    /**
     * Requestorを介して生成される
     * @param broker ブローカ
     */
    DataRequest(Broker broker) { super(broker); }
    
    @Override
    public void execute() {
        Response response = broker.sendDataRequest(path, params);
        if (response.getStatus() == Status.OK) {
            if (successCallBack == null) return;
            successCallBack.call(response.getData(), response.getStatus());
        } else {
            if (failCallBack == null) return;
            failCallBack.call(response.getData(), response.getStatus());
        }
    }
}
