package creamy.browser;

import creamy.mvc.Response;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * CreamyアプリケーションのRootとなるPane(Node)
 * リクエスト送信、レスポンス受信機能とページ遷移の制御機能を持つ
 * ユーザコンテンツは当該Paneの孫として配置される
 * BorderPaneを継承しており、Topにヘダーコンテンツが、Centerにボディ
 * コンテンツが配置される
 * 
 * @author Taiji Miyabe (Professor Akiguchi's PBL 2012, AIIT)
 */
public class BrowserPanel extends BorderPane {
    /**
     * ページ遷移管理
     */
    protected TransitionManager transitionManager;
    /**
     * リクエスト・レスポンスを仲介するBroker
     */
    protected Broker broker;
    /**
     * ヘダーコンテンツ
     */
    private BrowserContent header;
    /**
     * ボディコンテンツ
     */
    private BrowserBody body;
    
    /**
     * 空のコンテンツを持つBrowserPanelを生成する
     */
    public BrowserPanel() {
        transitionManager = new TransitionManager();
        broker = new Broker(this);
        buildBody();
    }
    
    /**
     * 引数(startPath)で指定されたコンテンツを持つBrowserPanelを生成する
     * @param startPath 表示するコンテンツのパス
     */
    public BrowserPanel(String startPath) {
        this();
        Response response = broker.sendRequest(startPath);
        transitionManager.goTo(response);   
    }
    
    /**
     * リクエスト受信のインターフェイス。Borkerから呼び出される
     * @param response レスポンス
     */
    public void receiveResponse(Response response) {
        transitionManager.goTo(response);
    }
    
    /**
     * TransitionManagerを取得する
     * @return TransitionManager
     */
    public TransitionManager getTransitionManager() { return transitionManager; }
    
    /**
     * Brokerを取得する
     * @return Broker
     */
    public Broker getBroker() { return broker; }

    /**
     * Brokerをセットする
     * @param borker ブローカ
     */
    public void setBroker(Broker broker) { this.broker = broker; }
    
    /**
     * ヘダーコンテンツを取得する
     * @return ヘダーコンテンツ
     */
    public Pane getHeader() {
        if (header == null) return null;
        return header.getContent();
    }

    /**
     * ヘダーコンテンツを配置する
     * @param headerContent ヘダーコンテンツ
     */
    public void setHeader(Pane headerContent) {
        if (header == null) buildHeader();
        header.setContent(headerContent);
    }
    
    /**
     * ボディコンテンツを取得する
     * @return ヘダーコンテンツ
     */
    public Pane getBody() { return body.getContent(); }
    
    /**
     * ヘダーを生成する
     */
    protected void buildHeader() {
        header = new BrowserContent(transitionManager);
        setTop(header);
    }
    
    /**
     * ボディを生成する
     */
    protected void buildBody() {
        body = new BrowserBody(transitionManager);
        setCenter(body);
    }
    
    /**
     * Browserを取得する
     * @return browser
     */
    protected Browser getBrowser() {
        return (Browser)getScene();
    }
}
