/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package creamy.activity.requestor;

import creamy.activity.Activity;
import creamy.browser.Broker;
import creamy.browser.CreamyTestBase;
import creamy.mvc.Status;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Platform;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author miyabetaiji
 */
public class DataRequestTest extends CreamyTestBase {
    
    public DataRequestTest() {
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

    private DataRequest request;
    private Request request2;
    
    /**
     * Test of getPath and setPath method, of class Request.
     */
    @Test
    public void testGetSetPath() {
        System.out.println("GetSetPath");
        // Setup
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                synchronized(lock) {
                    Broker borker = browser.getBrowserPanel().getBroker();
                    request = new DataRequest(borker);
                    request.setPath("testing");
                }
            }
        });
        // Assertion
        try {Thread.sleep(100);} catch (InterruptedException ex) {}
        synchronized(lock) {
            assertEquals("testing", request.getPath());
        }
    }

    /**
     * Test of getParams method, of class Request.
     */
    private Map<String,Object> params = new HashMap<String,Object>();
    @Test
    public void testGetSetParams() {
        System.out.println("GetSetParams");
        // Setup
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                synchronized(lock) {
                    Broker borker = browser.getBrowserPanel().getBroker();
                    request = new DataRequest(borker);
                    request.setParams(params);
                }
            }
        });
        // Assertion
        try {Thread.sleep(100);} catch (InterruptedException ex) {}
        synchronized(lock) {
            assertEquals(params, request.getParams());
        }
    }

    /**
     * Test of params method, of class Request.
     */
    @Test
    public void testParams() {
        System.out.println("params");
        // Setup
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                synchronized(lock) {
                    Broker borker = browser.getBrowserPanel().getBroker();
                    request = new DataRequest(borker);
                    request2 = request.params(params);
                }
            }
        });
        // Assertion
        try {Thread.sleep(100);} catch (InterruptedException ex) {}
        synchronized(lock) {
            assertEquals(params, request.getParams());
            assertEquals(request, request2);
        }
    }

    private CallBack<Object> callBack = new CallBack<Object>() {
        @Override
        public void call(Object data, Status status) {}
    };
    
    /**
     * Test of getOnSuccess method, of class Request.
     */
    @Test
    public void testGetSetOnSuccess() {
        System.out.println("GetSetOnSuccess");
        // Setup
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                synchronized(lock) {
                    Broker borker = browser.getBrowserPanel().getBroker();
                    request = new DataRequest(borker);
                    request.setOnSuccess(callBack);
                }
            }
        });
        // Assertion
        try {Thread.sleep(100);} catch (InterruptedException ex) {}
        synchronized(lock) {
            assertEquals(callBack, request.getOnSuccess());
        }
    }

    /**
     * Test of onSuccess method, of class Request.
     */
    @Test
    public void testOnSuccess() {
        System.out.println("onSuccess");
        // Setup
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                synchronized(lock) {
                    Broker borker = browser.getBrowserPanel().getBroker();
                    request = new DataRequest(borker);
                    request2 = request.onSuccess(callBack);
                }
            }
        });
        // Assertion
        try {Thread.sleep(100);} catch (InterruptedException ex) {}
        synchronized(lock) {
            assertEquals(callBack, request.getOnSuccess());
            assertEquals(request, request2);
        }
    }

    private CallBack<Object> failCallBack = new CallBack<Object>() {
        @Override
        public void call(Object data, Status status) {}
    };
       
    /**
     * Test of getOnFail method, of class Request.
     */
    @Test
    public void testGetSetOnFail() {
        System.out.println("GetSetOnFail");
        // Setup
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                synchronized(lock) {
                    Broker borker = browser.getBrowserPanel().getBroker();
                    request = new DataRequest(borker);
                    request.setOnFail(failCallBack);
                }
            }
        });
        // Assertion
        try {Thread.sleep(100);} catch (InterruptedException ex) {}
        synchronized(lock) {
            assertEquals(failCallBack, request.getOnFail());
        }
    }

    /**
     * Test of setOnFail method, of class Request.
     */
    @Test
    public void testOnFail() {
        System.out.println("onFail");
        // Setup
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                synchronized(lock) {
                    Broker borker = browser.getBrowserPanel().getBroker();
                    request = new DataRequest(borker);
                    request2 = request.onFail(failCallBack);
                }
            }
        });
        // Assertion
        try {Thread.sleep(100);} catch (InterruptedException ex) {}
        synchronized(lock) {
            assertEquals(failCallBack, request.getOnFail());
            assertEquals(request, request2);
        }
    }

    /**
     * Test of execute method, of class DataRequest.
     */
    private Object data;
    
    @Test
    public void testExecuteScuccess() {
        System.out.println("executeScuccess");
        // Setup
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                synchronized(lock) {
                    Broker borker = browser.getBrowserPanel().getBroker();
                    DataRequest request = new DataRequest(borker);
                    request.path = "/TestEditableController/dataRequest";
                    request
                        .onSuccess(new CallBack<Object>() {
                            @Override
                            public void call(Object dat, Status status) {
                                data = dat;
                            }
                        })
                        .execute();
                }
            }
        });
        try {Thread.sleep(100);} catch (InterruptedException ex) {}
        synchronized(lock) {
            assertNotNull(data);
            assertEquals("testing", data.toString());
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
                    DataRequest request = new DataRequest(borker);
                    request.path = "/TestEditableController/badRequestTest";
                    request
                        .onFail(new CallBack<Object>() {
                            @Override
                            public void call(Object data, Status status) {
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
