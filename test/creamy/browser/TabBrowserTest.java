/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package creamy.browser;

import creamy.browser.control.DefaultHeader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author miyabetaiji
 */
public class TabBrowserTest {
    
    public TabBrowserTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
        ExecutorService service = Executors.newFixedThreadPool(1);
        service.submit(new Runnable() {
            @Override
            public void run() {
                Application.launch(TestApplication.class);
            }
        });
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException ex) { }
    }
    
    @After
    public void tearDown() {
    }

    private TabBrowser browser;
    private final Object lock = new Object();
    /**
     * Test of buildContent method, of class TabBrowser.
     */
    @Test
    public void testBuildContent() {
        System.out.println("buildContent");
        // Setup
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                synchronized(lock) {
                    TestApplication.getInstance().getStage().setScene(browser = new TabBrowser());
                }
            }
        });
        // Assertion
        try {Thread.sleep(100);} catch (InterruptedException ex) {ex.printStackTrace();};
        synchronized(lock) {
            assertTrue(((BorderPane)browser.getRoot()).getCenter() instanceof TabPane);
            TabPane tabPane = (TabPane)browser.root.getCenter();
            Tab tab = tabPane.getSelectionModel().getSelectedItem();
            assertEquals(1, tabPane.getTabs().size());
            assertFalse(tab.closableProperty().get());
            browser.createBrowserPanel();
            assertEquals(2, tabPane.getTabs().size());
            assertTrue(tabPane.getTabs().get(0).closableProperty().get());
            assertTrue(tabPane.getTabs().get(1).closableProperty().get());
        }
    }

    private DefaultHeader header;
    /**
     * Test of createBrowserPanel method, of class TabBrowser.
     */
    @Test
    public void testCreateBrowserPanel() {
        System.out.println("createBrowserPanel");
        // Setup
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                synchronized(lock) {
                    TestApplication.getInstance().getStage().setScene(browser = new TabBrowser());
                    browser.setHeader(header = new DefaultHeader());
                }
            }
        });
        // Assertion
        try {Thread.sleep(100);} catch (InterruptedException ex) {ex.printStackTrace();}
        synchronized(lock) {
            assertTrue(((BorderPane)browser.getRoot()).getCenter() instanceof TabPane);
            assertEquals(header, browser.getHeader());
            TabPane tabPane = (TabPane)browser.root.getCenter();
            Tab tab = tabPane.getSelectionModel().getSelectedItem();
            assertEquals(1, tabPane.getTabs().size());
            BrowserPanel panel = browser.createBrowserPanel();
            assertTrue(panel instanceof BrowserPanel);
            assertEquals(2, tabPane.getTabs().size());
            assertTrue(tabPane.getTabs().get(0).getContent() instanceof TabBrowserPanel);
            assertTrue(browser.getHeader() instanceof DefaultHeader);
        }
    }

    /**
     * Test of getBrowserPanel method, of class TabBrowser.
     */
    @Test
    public void testGetBrowserPanel() {
        System.out.println("getBrowserPanel");
        // Setup
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                synchronized(lock) {
                    TestApplication.getInstance().getStage().setScene(browser = new TabBrowser());
                    browser.setHeader(header = new DefaultHeader());
                }
            }
        });
        // Assertion
        try {Thread.sleep(100);} catch (InterruptedException ex) {ex.printStackTrace();}
        synchronized(lock) {
            assertNotNull(browser.getBrowserPanel());
            BrowserPanel panel1 = browser.createBrowserPanel();
            assertEquals(panel1, browser.getBrowserPanel());
            BrowserPanel panel2 = browser.createBrowserPanel();
            assertEquals(panel2, browser.getBrowserPanel());
            TabPane tabPane = (TabPane)((BorderPane)browser.getRoot()).getCenter();
            tabPane.getSelectionModel().select(1);
            assertEquals(panel1, browser.getBrowserPanel());
        }
    }

    /**
     * Test of getHeader method, of class TabBrowser.
     */
    @Test
    public void testGetHeader() {
        System.out.println("getHeader");
    }

    /**
     * Test of setHeader method, of class TabBrowser.
     */
    @Test
    public void testSetHeader() {
        System.out.println("setHeader");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                synchronized(lock) {
                    TestApplication.getInstance().getStage().setScene(browser = new TabBrowser());
                }
            }
        });
        // Assertion
        try {Thread.sleep(100);} catch (InterruptedException ex) {ex.printStackTrace();}
        synchronized(lock) {
            browser.createBrowserPanel();
            browser.setHeader(header = new DefaultHeader());
            TabPane tabPane = (TabPane)((BorderPane)browser.getRoot()).getCenter();
            /*
            for (Tab tab : tabPane.getTabs()) {
                TabBrowserPanel panel = (TabBrowserPanel)tab.getContent();
                assertNotNull(panel.getHeader());
                assertTrue(panel.getHeader() instanceof DefaultHeader);
                if (tab != tabPane.getSelectionModel().getSelectedItem()) {
                    assertEquals(header, panel.getHeader());
                }
            }
            */
        }
    }

    public static class TestApplication extends Application {
        private static TestApplication instance;
        private Stage stage;
        
        @Override
        public void start(Stage stage) throws Exception {
            instance = this;
            this.stage = stage;
        }
        public static TestApplication getInstance() {
            return instance;
        }
        public Stage getStage() { return stage; }
    }
}
