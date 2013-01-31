package creamy.activity.requestor;

import creamy.activity.Activity;
import creamy.browser.Broker;
import creamy.mvc.Response;
import creamy.mvc.Status;

/**
 * Activityリクエストを送信するRequestオブジェクト
 * 
 * @author miyabetaiji
 */
public class ActivityRequest extends Request<Activity> {
    /**
     * Requestorを介して生成される
     * @param broker
     */
    ActivityRequest(Broker broker) { super(broker); }
    
    /**
     * Requestを送信する
     * Responseを受けた後コールバックオブジェクトを呼び出す
     */
    @Override
    public void execute() {
        Response response = broker.sendRequest(path, params);
        if (response.getStatus() == Status.OK) {
            if (successCallBack == null) return;
            response.getActivity().setParent(parent);
            parent.addChild(response.getActivity());
            successCallBack.call(response.getActivity(), response.getStatus());
        } else {
            if (failCallBack == null) return;
            failCallBack.call(response.getActivity(), response.getStatus());
        }
    }
}
