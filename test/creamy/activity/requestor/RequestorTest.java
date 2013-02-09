/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package creamy.activity.requestor;

import creamy.activity.Activity;
import creamy.browser.Broker;
import creamy.browser.Browser;
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
public class RequestorTest extends CreamyTestBase {
    
    public RequestorTest() {
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
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                synchronized(lock) {
                    activity = new ActivityImpl();
                    activity.setScene(new HBox());
                    Response response = new Response();
                    response.setStatus(Status.OK);
                    response.setActivity(activity);
                    browser.getBrowserPanel().receiveResponse(response);
                }
            }
        });
        try { Thread.sleep(100L); } catch (InterruptedException ex) {}
        synchronized(lock) {}
    }
    
    @After
    public void tearDown() {
    }

    private Activity activity;
    private Request<Activity> activityRequest;
    private Request<Object> dataRequest;
    
    /**
     * Test of requestActivity method, of class Requestor.
     */
    @Test
    public void testRequestActivity() {
        System.out.println("requestActivity");
        // Setup
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                synchronized(lock) {
                    activityRequest = activity.requestActivity("testing");
                }
            }
        });
        // Assertion
        try {Thread.sleep(100);} catch (InterruptedException ex) {}
        synchronized(lock) {
            assertEquals("testing", activityRequest.getPath());
        }
    }

    /**
     * Test of requestData method, of class Requestor.
     */
    @Test
    public void testRequestData() {
        System.out.println("requestData");
        // Setup
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                synchronized(lock) {
                    dataRequest = activity.requestData("testing2");
                }
            }
        });
        // Assertion
        try {Thread.sleep(100);} catch (InterruptedException ex) {}
        synchronized(lock) {
            assertEquals("testing2", dataRequest.getPath());
        }
    }

    private RequestCancel requestCancel;
    @Test(expected = RequestCancel.class)
    public void testCancelRequest() {
        System.out.println("cancelRequest");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                synchronized(lock) {
                    try {
                        activity.cancelRequest();
                    } catch (RequestCancel rc) {
                        requestCancel = rc;
                    }
                }
            }
        });
        try {Thread.sleep(100);} catch (InterruptedException ex) {}
        synchronized(lock) {
            throw requestCancel;
        }
    }
    
    public class ActivityImpl extends Activity {
        @Override
        public void initialize() {}
    }
}
