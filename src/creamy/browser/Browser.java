package creamy.browser;

import creamy.browser.control.BrowserMenuBar;
import creamy.global.ApplicationData;
import creamy.global.WindowData;
import java.util.Map;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

/**
 * BrowserはcreamyアプリケーションのSceneとなる
 * JavaFXのプレーンなSceneと異なり、Browserとして以下の機能を提供する
 * ページ遷移の履歴管理、ページ履歴の移動(Back/Forward)
 * デフォルトではHeader、Menubarは設定されていない。Header、Menubarはそれぞれ、
 * setHeader、setMenuBarを使用してユーザが任意のコントロールを配置する
 * 
 * @author Taiji Miyabe (Professor Akiguchi's PBL 2012, AIIT)
 */
public class Browser extends Scene {
    /**
     * Root Pane。CenterにBrowserPanelを、TopにMenuBarを配置
     */
    protected BorderPane root;
    /**
     * コンテンツ表示領域。BorderPaneのCenterに配置される
     */
    private BrowserPanel browserPanel;
    /**
     * メニューバー。BorderPaneのTopに配置される
     */
    private BrowserMenuBar menuBar;
   
    /**
     * 空のコンテンツを持つTabBrowserを生成する
     */
    public Browser() {
        super(new BorderPane());
        root = (BorderPane)getRoot();
        buildContent();
        setCloseOperation();
    }
    
    /**
     * 引数(startPath)で指定されたコンテンツを持つBrowserPanelを生成する
     * @param startPath 初期表示するコンテンツのパス
     */
    public Browser(String startPath) {
        this();
        buildContent(startPath);
    }
    
    /**
     * コンテンツ領域を初期化する(BrowserPanelを配置する)
     */
    protected void buildContent() {
        root.setCenter(browserPanel = new BrowserPanel());
    }
    
    /**
     * コンテンツ領域を初期化する(BrowserPanelを配置する)
     * @param startPath 初期表示するコンテンツのパス
     */
    protected void buildContent(String startPath) {
        root.setCenter(browserPanel = new BrowserPanel(startPath));
    }
    
    /**
     * コンテンツ領域に配置されたBrowserPanelを取得する
     * @return BrowserPanel
     */
    public BrowserPanel getBrowserPanel() {return browserPanel; }

    /**
     * コンテンツ領域にBrowserPanelを配置する
     * @param browserPanel コンテンツ領域に配置するBrowserPanel
     */
    public void setBrowserPanel(BrowserPanel browserPanel) { 
        root.setCenter(this.browserPanel = browserPanel);
    }

    /**
     * MenuBarを取得する
     * @return MenuBar
     */
    public BrowserMenuBar getMenuBar() {
        return menuBar;
    }

    /**
     * MenuBarをメニューバー領域に配置する
     * @param menuBar 配置するMenuBar
     */
    public void setMenuBar(BrowserMenuBar menuBar) {
        root.setTop(this.menuBar = menuBar);
        menuBar.setBrowser(this);
    }

    /**
     * BrowserPane上のヘダーコンテンツを取得する
     * @return ヘダーコンテンツ
     */
    @SuppressWarnings("unchecked")
    public <T extends Pane> T getHeader() {
        return (T)browserPanel.getHeader();
    }

    /**
     * BrowserPane上のヘダーコンテンツを配置する
     * @param header 配置するヘダーコンテンツ
     */
    public <T extends Pane> void setHeader(T header) {
        browserPanel.setHeader(header);
    }
    
    /**
     * Window closeオペレーションを登録する
     */
    protected void setCloseOperation() {
        windowProperty().addListener(new ChangeListener<Window>() {
            @Override
            public void changed(ObservableValue<? extends Window> ov, Window t, Window window) {
                if (window == null) return;
                window.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        WindowData.getInstance().removeData(Browser.this);
                    }
                });
            }
        });
    }
}
