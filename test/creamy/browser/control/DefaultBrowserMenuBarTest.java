/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package creamy.browser.control;

import creamy.browser.Browser;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author miyabetaiji
 */
public class DefaultBrowserMenuBarTest {
    
    public DefaultBrowserMenuBarTest() {
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

    private Browser browser;
    private final Object lock = new Object();
    private DefaultBrowserMenuBar menuBar;
    @Test
    public void testGetBrowser() {
        System.out.println("getBrowser");
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
        try {Thread.sleep(100);} catch (InterruptedException ex) {ex.printStackTrace();};
        synchronized(lock) {
            assertNotNull(menuBar.getBrowser());
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
