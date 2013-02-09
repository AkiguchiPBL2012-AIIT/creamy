/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package creamy.browser;

import creamy.activity.AvailableActivity;
import creamy.mvc.Response;
import creamy.mvc.Status;
import creamy.scene.control.CFHyperlink;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author miyabetaiji
 */
public class TabBrowserContentTest {
    
    public TabBrowserContentTest() {
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
        } catch (InterruptedException ex) {}
    }
    
    @After
    public void tearDown() {
    }

    private final Object lock = new Object();
    private TabBrowser browser;
    private CFHyperlink link;
    /**
     * Test of registerBrowserFunction method, of class TabBrowserContent.
     */
    @Test
    public void testRegisterBrowserFunction() {
        System.out.println("registerBrowserFunction");
        // Setup
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                synchronized(lock) {
                    TestApplication.getInstance().getStage().setScene(browser = new TabBrowser());
                    AvailableActivity activity = new TestActivity();
                    String checktext = "testing";
                    link = new CFHyperlink("/Application/list/0/name/asc/");
                    Pane content = HBoxBuilder.create()
                                    .children(new TextField(checktext), link)
                                    .build();
                    activity.setScene(content);
                    //BrowserScene browserScene = new BrowserScene(activity, content);
                    Response response = new Response(Status.OK, checktext, activity);
                    TabBrowserPanel panel = browser.getBrowserPanel();
                    panel.receiveResponse(response);
                }
            }
        });
        // Assertion
        try {Thread.sleep(100);} catch (InterruptedException ex) {ex.printStackTrace();}
        synchronized(lock) {

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

    public class TestActivity extends AvailableActivity {
    }
}
