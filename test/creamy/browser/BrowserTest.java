/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package creamy.browser;

import creamy.browser.control.BrowserMenuBar;
import creamy.browser.control.DefaultBrowserMenuBar;
import creamy.browser.control.DefaultHeader;
import creamy.global.WindowData;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author miyabetaiji
 */
public class BrowserTest {
    
    public BrowserTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
        // JavaFX EDTスレッドを起動するためのダミーアプリケーション
        ExecutorService service = Executors.newFixedThreadPool(1);
        service.submit(new Runnable() {
            @Override
            public void run() {
                synchronized(lock) {
                    Application.launch(TestApplication.class);
                }
            }
        });
        try { Thread.sleep(100L); } catch (InterruptedException ex) {}
        synchronized(lock) {}
    }
    
    @After
    public void tearDown() {
    }

    private Browser browser;
    private final Object lock = new Object();
    /**
     * Test of buildContent method, of class Browser.
     */
    @Test
    public void testBuildContent_0args() {
        System.out.println("buildContent");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                synchronized(lock) {
                    TestApplication.getInstance().getStage().setScene(browser = new Browser());
                }
            }
        });
        try {Thread.sleep(100);} catch (InterruptedException ex) {ex.printStackTrace();};
        synchronized(lock) {
            assertEquals(((BorderPane)browser.getRoot()).getCenter(), browser.getBrowserPanel());
        }
    }

    /**
     * Test of buildContent method, of class Browser.
     */
    @Test
    public void testBuildContent_String() {
        System.out.println("buildContent");
        String path = "/Application/list/0/name/asc/";
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                synchronized(lock) {
                    TestApplication.getInstance().getStage().setScene(browser = new Browser("/Application/list/0/name/asc/"));
                }
            }
        });
        try {Thread.sleep(100);} catch (InterruptedException ex) {ex.printStackTrace();};
        synchronized(lock) {
            assertEquals(path, browser.getBrowserPanel().getTransitionManager().currentPathProperty().get());
        }
    }

    /**
     * Test of getBrowserPanel method, of class Browser.
     */
    @Test
    public void testGetBrowserPanel() {
        System.out.println("getBrowserPanel");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                synchronized(lock) {
                    TestApplication.getInstance().getStage().setScene(browser = new Browser());
                }
            }
        });
        try {Thread.sleep(100);} catch (InterruptedException ex) {ex.printStackTrace();};
        synchronized(lock) {
            assertEquals(((BorderPane)browser.getRoot()).getCenter(), browser.getBrowserPanel());
        }
    }

    private BrowserPanel panel;
    /**
     * Test of setBrowserPanel method, of class Browser.
     */
    @Test
    public void testSetBrowserPanel() {
        System.out.println("setBrowserPanel");
        // Setup
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                synchronized(lock) {
                    TestApplication.getInstance().getStage().setScene(browser = new Browser());
                    panel = new BrowserPanel();
                    browser.setBrowserPanel(panel);
                }
            }
        });
        // Assertion
        try {Thread.sleep(100);} catch (InterruptedException ex) {ex.printStackTrace();};
        synchronized(lock) {
            assertEquals(panel, browser.getBrowserPanel());
        }
    }

    BrowserMenuBar menuBar;
    /**
     * Test of getMenuBar method, of class Browser.
     */
    @Test
    public void testGetMenuBar() {
        System.out.println("getMenuBar");
        // Setup
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                synchronized(lock) {
                    TestApplication.getInstance().getStage().setScene(browser = new Browser());
                    menuBar = new DefaultBrowserMenuBar();
                    browser.setMenuBar(menuBar);
                }
            }
        });
        // Assertion
        try {Thread.sleep(100);} catch (InterruptedException ex) {ex.printStackTrace();};
        synchronized(lock) {
            assertEquals(menuBar, browser.getMenuBar());
        }
    }

    /**
     * Test of setMenuBar method, of class Browser.
     */
    @Test
    public void testSetMenuBar() {
        System.out.println("setMenuBar");
        // Setup
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                synchronized(lock) {
                    TestApplication.getInstance().getStage().setScene(browser = new Browser());
                    menuBar = new DefaultBrowserMenuBar();
                    browser.setMenuBar(menuBar);
                }
            }
        });
        // Assertion
        try {Thread.sleep(100);} catch (InterruptedException ex) {ex.printStackTrace();};
        synchronized(lock) {
            assertEquals(menuBar, browser.getMenuBar());
        }
    }

    private DefaultHeader header;
    /**
     * Test of getHeader method, of class Browser.
     */
    @Test
    public void testGetHeader() {
        System.out.println("getHeader");
        // Setup
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                synchronized(lock) {
                    TestApplication.getInstance().getStage().setScene(browser = new Browser());
                    header = new DefaultHeader();
                    browser.setHeader(header);
                }
            }
        });
        // Assertion
        try {Thread.sleep(100);} catch (InterruptedException ex) {ex.printStackTrace();};
        synchronized(lock) {
            assertEquals(menuBar, browser.getMenuBar());
        }
    }

    /**
     * Test of setHeader method, of class Browser.
     */
    @Test
    public void testSetHeader() {
        System.out.println("setHeader");
        // Setup
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                synchronized(lock) {
                    TestApplication.getInstance().getStage().setScene(browser = new Browser());
                    header = new DefaultHeader();
                    browser.setHeader(header);
                }
            }
        });
        // Assertion
        try {Thread.sleep(100);} catch (InterruptedException ex) {ex.printStackTrace();};
        synchronized(lock) {
            assertEquals(menuBar, browser.getMenuBar());
        }
    }

    private Map<String,Object> windowData;
    @Test
    public void testSetCloseOperation() {
        System.out.println("setCloseOperation");
        // Setup
        final String key = "testKey";
        final String value = "testValue";
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                synchronized(lock) {
                    browser = new Browser();
                    TestApplication.getInstance().getStage().setScene(browser);
                    TestApplication.getInstance().getStage().show();
                    windowData = WindowData.getInstance().getData(browser);
                    windowData.put(key, value);
                }
            }
        });
        // Assertion
        try {Thread.sleep(100);} catch (InterruptedException ex) {ex.printStackTrace();};
        synchronized(lock) {
            assertNotNull(WindowData.getInstance().getData(browser));
            assertEquals(windowData, WindowData.getInstance().getData(browser));
            assertEquals(value, windowData.get(key));
        }
       Platform.runLater(new Runnable() {
            @Override
            public void run() {
                synchronized(lock) {
                    TestApplication.getInstance().getStage().close();
                }
            }
        });
        try {Thread.sleep(100);} catch (InterruptedException ex) {ex.printStackTrace();};
        synchronized(lock) {
            assertEquals(TestApplication.getInstance().getStage(), browser.windowProperty().get());
            assertEquals(windowData, WindowData.getInstance().getData(browser));
            assertTrue(WindowData.getInstance().getData(browser).isEmpty());
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
