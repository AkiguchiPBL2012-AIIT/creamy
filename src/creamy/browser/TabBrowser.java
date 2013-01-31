package creamy.browser;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;

/**
 * タブ機能を持つBrowser
 * ユーザはTabBrowserをSceneにすることだけで、タブ機能が利用可能となる
 * 
 * @author Taiji Miyabe (Professor Akiguchi's PBL 2012, AIIT)
 */
public class TabBrowser extends Browser {
    /**
     * (BorderPaneの)Centerに配置されるTabPane
     */
    private TabPane tabPane;
    /**
     * 現在選択(表示)されているTab
     */
    private Tab currentTab;
    /**
     * 現在選択(表示)されているTabBrowserPanel
     */
    private TabBrowserPanel currentPanel;
   
    /**
     * 空のコンテンツを持つTabBrowserを生成する
     */
    public TabBrowser() {
        super();
        addBrwoserPanel(new TabBrowserPanel(this));
    }
    
    /**
     * 指定のコンテンツを持つTabBrowserを生成する
     * @param startPath 初期表示するコンテンツのパス
     */
    public TabBrowser(String startPath) {
        super();
        addBrwoserPanel(new TabBrowserPanel(this, startPath));
    }

    /**
     * コンテンツ領域を初期化する(BrowserPanelを配置する)
     * ツリー構造は以下のとおり
     * BorderPane.Center
     *  - TabPane
     *    - Tab
     *      - TabBrowserPanel
     */
    @Override
    protected void buildContent() {
        root.setCenter(tabPane = new TabPane());
        tabPane.getTabs().addListener(new ListChangeListener<Tab>() {
            @Override
            public void onChanged(Change<? extends Tab> change) {
                if (change.getList().size() == 1) {
                    Tab firstTab = change.getList().get(0);
                    firstTab.setClosable(false);
                } else {
                    for (Tab tab : change.getList())
                        tab.setClosable(true);
                }
            }
        });
        tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
                currentTab = t1;
                currentPanel = (TabBrowserPanel)currentTab.getContent();
            }
        });
    }

    private void addBrwoserPanel(TabBrowserPanel panel) {
        Tab tab = new Tab();
        tab.textProperty().bind(panel.getTransitionManager().currentTitleProperty());
        tab.setContent(panel);
        int index = tabPane.getTabs().indexOf(currentTab);
        if (index < 0)
            tabPane.getTabs().add(tab);
        else
            tabPane.getTabs().add(tabPane.getTabs().indexOf(currentTab) + 1, tab);
        tabPane.getSelectionModel().select(tab);
    }
    
    public TabBrowserPanel createBrowserPanel() {
        TabBrowserPanel panel = new TabBrowserPanel(this);
        if (headerClass != null) {
            try {
                Pane genHeader = (Pane)headerClass.getConstructor().newInstance();
                panel.setHeader(genHeader);
            } catch (NoSuchMethodException | SecurityException | InstantiationException | 
                     IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                Logger.getLogger(TabBrowser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        addBrwoserPanel(panel);
        return panel;
    }
    
    /**
     * コンテンツ領域に配置されたBrowserPanelを取得する
     * @return TabBrowserPanel
     */
    @Override
    public TabBrowserPanel getBrowserPanel() { return currentPanel; }

    /**
     * コンテンツ領域にBrowserPanelを配置する
     * @param tabBrowserPanel コンテンツ領域に配置するTabBrowserPanel
     */
    @Override
    public void setBrowserPanel(BrowserPanel browserPanel) { 
        currentTab.setContent(browserPanel);
    }

    private Class<? extends Pane> headerClass;
    
    /**
     * BrowserPane上のヘダーコンテンツを取得する
     * @return ヘダーコンテンツ
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T extends Pane> T getHeader() {
        return (T)currentPanel.getHeader();
    }

    /**
     * BrowserPane上のヘダーコンテンツを配置する
     * @param header 配置するヘダーコンテンツ
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T extends Pane> void setHeader(T header) {
        currentPanel.setHeader(header);
        headerClass = header.getClass();
        for (Tab tab : tabPane.getTabs()) {
            TabBrowserPanel panel = (TabBrowserPanel)currentTab.getContent();
            if (panel == currentPanel) continue;
            try {
                T genHeader = (T)headerClass.getConstructor().newInstance();
                panel.setHeader(header);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException
                        | SecurityException | NoSuchMethodException ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
