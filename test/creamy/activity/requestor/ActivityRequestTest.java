/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package creamy.activity.requestor;

import creamy.activity.Activity;
import creamy.activity.ActivityTest;
import creamy.browser.Broker;
import creamy.browser.CreamyTestBase;
import creamy.mvc.Response;
import creamy.mvc.Status;
import javafx.application.Platform;
import javafx.scene.layout.HBox;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author miyabetaiji
 */
public class ActivityRequestTest extends CreamyTestBase {
    
    public ActivityRequestTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    @Override
    public void setUp() {
        super.setUp();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of execute method, of class ActivityRequest.
     */
    
    private Activity activity;
    
    @Test
    public void testExecuteScuccess() {
        System.out.println("executeScuccess");
        // Setup
        Activity act = null;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                synchronized(lock) {
                    Broker borker = browser.getBrowserPanel().getBroker();
                    ActivityRequest request = new ActivityRequest(borker);
                    request.path = "/TestEditableController/testEditableIndex";
                    request
                        .onSuccess(new CallBack<Activity>() {
                            @Override
                            public void call(Activity data, Status status) {
                                activity = data;
                            }
                        })
                        .execute();
                }
            }
        });
        try {Thread.sleep(100);} catch (InterruptedException ex) {}
        synchronized(lock) {
            assertNotNull(activity);
        }    
    }
    
    private Status stat;
    
    @Test
    public void testExecuteFail() {
        System.out.println("executeFail");
        // Setup
        Activity act = null;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                synchronized(lock) {
                    Broker borker = browser.getBrowserPanel().getBroker();
                    ActivityRequest request = new ActivityRequest(borker);
                    request.path = "/TestEditableController/badRequestTest";
                    request
                        .onFail(new CallBack<Activity>() {
                            @Override
                            public void call(Activity data, Status status) {
                                stat = status;
                            }
                        })
                        .execute();
                }
            }
        });
        try {Thread.sleep(100);} catch (InterruptedException ex) {}
        synchronized(lock) {
            assertEquals(Status.BAD_REQUEST, stat);
        }    
    }
}
