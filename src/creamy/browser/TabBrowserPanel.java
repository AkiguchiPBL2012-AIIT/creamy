package creamy.browser;

import javafx.scene.layout.Pane;

/**
 * タブ機能を持つBrowser。<br/>
 * JavaFXのプレーンなSceneと異なり、Browserとして以下の機能を提供する。<br/>
 * ページ遷移の履歴管理、ページ履歴の移動(Back/Forward)<br/>
 * デフォルトではHeader、Menubarは設定されていない。Header、Menubarはそれぞれ、
 * setHeader、setMenuBarを使用してユーザが任意のコントロールを配置する。
 * 
 * @author Taiji Miyabe (Professor Akiguchi's PBL 2012, AIIT)
 */
public class TabBrowserPanel extends BrowserPanel {
    /**
     * TabBrowser
     */
    private TabBrowser browser;
    /**
     * TabBrowserのヘッダ
     */
    private TabBrowserContent header;
    /**
     * TabBrowserのボディ
     */
    private TabBrowserBody body;
    
    public TabBrowserPanel(TabBrowser browser) {
        super();
        this.browser = browser;
    }
    
    public TabBrowserPanel(TabBrowser browser, String startPath) {
        super(startPath);
        this.browser = browser;   
    }
    
    @Override
    public TabBrowser getBrowser() { return browser; }
    
    @Override
    public Pane getHeader() {
        return header.getContent();
    }

    @Override
    public void setHeader(Pane headerContent) {
        if (header == null) buildHeader();
        header.setContent(headerContent);
    }
    
    @Override
    protected void buildHeader() {
        header = new TabBrowserContent(transitionManager, this);
        setTop(header);
    }
    
    @Override
    protected void buildBody() {
        body = new TabBrowserBody(transitionManager, this);
        setCenter(body);
    }
}
