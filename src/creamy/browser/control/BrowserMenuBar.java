package creamy.browser.control;

import creamy.browser.Browser;
import javafx.scene.control.MenuBar;

/**
 * BrowserMenuBarの基底クラス
 *
 * @author Taiji Miyabe (Professor Akiguchi's PBL 2012, AIIT)
 */
public class BrowserMenuBar extends MenuBar {
    /**
     * 配置されるBrowser
     */
    protected Browser browser;

    /**
     * Browserを取得する
     * @return 
     */
    public Browser getBrowser() { return browser; }
    
    /**
     * Browserをセットする
     * @param browser Browser
     */
    public void setBrowser(Browser browser) { this.browser = browser; }
}
