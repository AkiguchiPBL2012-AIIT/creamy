package creamy.browser;

import creamy.scene.control.Requestable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Node;
import javafx.scene.control.Control;

/**
 * ユーザコントロールにリクエスト機能を付加するためのヘルパ
 * Nodeツリーを探索し、Browserパネルを取得し、Brokerを返す
 * 
 * @author Taiji Miyabe (Professor Akiguchi's PBL 2012, AIIT)
 */
public class RequestHelper<T extends Control & Requestable> {
    /**
     * BrowserPanel
     */
    BrowserPanel panel;
    /**
     * RequestHelperを保有するコントロール
     */
    T requestControl;
    /**
     * Broker
     */
    Broker broker;
    
    /**
     * RequestHelperを生成する
     * @param requestControl リクエストのトリガとなるコントロール
     */
    public RequestHelper(T requestControl) {
        this.requestControl = requestControl;
        this.broker = null;
    }

    /**
     * Brokerを取得する
     * @return Broker
     */
    public Broker getBroker() {
       // return cache
       if (broker != null) {return broker;}
       
       // find broker
       BrowserPanel panel = findPanel();
       if (panel == null) {
           Logger.getLogger(getClass().getName()).log(Level.WARNING, "Display not found");
           return null;
       }
       return panel.getBroker();
    }
    
    private BrowserPanel findPanel() {
        if (panel != null) return panel;
        Node node = requestControl;
        while (node != null) {
            node = node.getParent();
            if (node instanceof BrowserPanel) {
                return panel = (BrowserPanel)node;
            }
        }
        return null;
    }
    
    /*
    public void openInNewTab() {
        if (requestControl instanceof UnitRequest) {
            BrowserPanel panel = findDisplay();
            panel.openInNewTab(((UnitRequest)requestControl).getPath());
        }
    }
    * 
    */
}
