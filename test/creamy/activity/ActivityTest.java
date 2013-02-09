/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package creamy.activity;

import creamy.browser.Browser;
import creamy.browser.CreamyTestBase;
import creamy.mvc.Response;
import creamy.mvc.Status;
import creamy.property.CreamyProps;
import creamy.scene.layout.ChildPane;
import creamy.validation.ValidationResult;
import java.lang.reflect.Field;
import java.util.*;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author ATakahashi
 */
public class ActivityTest extends CreamyTestBase {
    
    public ActivityTest() {
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
        CreamyProps.loadProperties();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getScene method, of class Activity.
     */
    @Test
    public void testGetScene() {
        System.out.println("getScene");
        // Setup
        Activity act = new ChildActivity();
        VBox vbox = new VBox();
        act.setScene(vbox);
        // Assertion
        assertEquals(vbox, act.getScene());
    }

    /**
     * Test of setScene method, of class Activity.
     */
    @Test
    public void testSetScene() {
        System.out.println("setScene");
        // Setup
        ChildActivity act = new ChildActivity();
        VBox vbox = new VBox();
        act.setScene(vbox);
        // Assertion
        assertEquals(vbox, act.getScene());
    }

    /**
     * Test of initialize method, of class Activity.
     */
    @Test
    public void testInitialize_0args() {
        System.out.println("initialize");
        // Setup
        ChildActivity act = new ChildActivity();
        act.initialize();
        // Assertion
        assertEquals("TESTING", act.getTest());
    }

    /**
     * Test of initialize method, of class Activity.
     */
    @Test
    public void testInitialize_Activity_Map() throws Exception {
        System.out.println("initialize");
        // Setup
        ParentActivity parent = new ParentActivity();
        Data data = new Data();
        Map<Field,Object> fieldParams = new HashMap<Field,Object>();
        for (Field field : data.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            fieldParams.put(field, field.get(data));
        }
        final String field2 = "field2value";
        final List<String> field3 = new ArrayList<String>();
        Map<String,Object> takeoverParams = new HashMap<String,Object>() {{
            put("field2", field2);
            put("field3", field3);
        }};
        ChildActivity child = new ChildActivity();
        child.initialize(parent, fieldParams, takeoverParams);
        // Assertion
        assertEquals(parent, child.getParent());
        assertEquals(data.getField0(), child.field0);
        assertEquals(data.getField1(), child.field1);
        assertEquals(field2, child.field2);
        assertEquals(field3, child.field3);
        assertEquals("TESTING", child.getTest());
    }

    /**
     * Test of setParent method, of class Activity.
     */
    @Test
    public void testSetParent() {
        System.out.println("setParent");
        // Setup
        Activity parent = new ParentActivity();
        Activity child = new ChildActivity();
        child.setParent(parent);
        // Assertion
        assertEquals(parent, child.getParent());
    }

    /**
     * Test of getParent method, of class Activity.
     */
    @Test
    public void testGetParent() {
        System.out.println("getParent");
        // Setup
        Activity parent = new ParentActivity();
        Activity child = new ChildActivity();
        child.setParent(parent);
        // Assertion
        assertEquals(parent, child.getParent());
    }

    /**
     * Test of renderChildren method, of class Activity.
     */
    @Test
    public void testCreateChildren() throws Exception {
        System.out.println("renderChildren");
        // Setup
        ChildPane childPane = new ChildPane();
        childPane.setChild("Child");
        VBox vbox = VBoxBuilder.create().children(childPane).build();
        Parent parent = new Parent();
        parent.setScene(vbox);
        Data data = new Data();
        Map<Field,Object> fieldParams = new HashMap<Field,Object>();
        for (Field field : data.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            fieldParams.put(field, field.get(data));
        }
        RenderParameter renderParam = new RenderParameter();
        String id = renderParam.putParam("checkForLabel", "CheckForLabel");
        childPane.setId(id);
        parent.createChildren(fieldParams, renderParam, ValidationResult.getEmptyResult());
        // Assertoin
        Child child = (Child)parent.getChildActivities(Child.class).get(0);
        assertNotNull(child);
        assertNotNull(child.getScene());
        assertEquals(childPane, child.getScene());
        assertEquals(parent, child.getParent());
        VBox cvbox = (VBox)child.getScene().getChildren().get(0);
        assertTrue(cvbox.getChildren().get(0) instanceof Button);
        assertTrue(cvbox.getChildren().get(1) instanceof Label);
        assertEquals(data.checkForButton, ((Button)cvbox.getChildren().get(0)).getText());
        assertEquals("CheckForLabel", ((Label)cvbox.getChildren().get(1)).getText());
    }

    /**
     * Test of getNodesByTag method, of class Activity.
     */
    @Test
    public void testGetNodesByTag() {
        System.out.println("getNodesByTag");
        Class<? extends Node> clazz = null;
        Activity instance = new ChildActivity();
        List expResult = null;
        List result = instance.getNodesByTag(clazz);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addChildActivity method, of class Activity.
     */
    @Test
    public void testAddChildActivity() {
        System.out.println("addChildActivity");
        Activity childActivity = null;
        Activity instance = new ChildActivity();
        instance.addChildActivity(childActivity);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getChildActivities method, of class Activity.
     */
    @Test
    public void testGetChildActivities() {
        System.out.println("getChildActivities");
        Class<? extends Activity> clazz = null;
        Activity instance = new ChildActivity();
        List expResult = null;
        List result = instance.getChildActivities(clazz);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetApplicationData() {
        System.out.println("getApplicationData");
        AvailableActivity activity = new AvailableActivity();
        String key = "testKey";
        String value = "testValue";
        activity.getApplicationData().put(key, value);
        assertEquals(value, activity.getApplicationData().get(key));
    }
    
    private ChildActivity act;
    
    @Test
    public void testCreateBroker() {
        System.out.println("testCreateBroker");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                synchronized(lock) {
                    // Setup
                    act = new ChildActivity();
                    act.setScene(new HBox());
                    Response response = new Response();
                    response.setStatus(Status.OK);
                    response.setActivity(act);
                    browser.getBrowserPanel().receiveResponse(response);
                }
            }
        });
        try {Thread.sleep(100);} catch (InterruptedException ex) {ex.printStackTrace();}
        synchronized(lock) {
            assertNotNull(act.createBroker());
        }        
    }
    
    public class ChildActivity extends Activity {
        private String field0;
        private List<String> field1;
        private String field2;
        private List<String> field3;
        private String test;
        public void initialize() {
            test = "TESTING";
        }
        private String getTest() { return test; }
    }
    
    public class ParentActivity extends Activity {
        @Override
        public void initialize() {}
    }
    
    public class Data {
        private String field0 = "field0";
        private List<String> field1 = new ArrayList<String>();
        private String checkForButton = "CheckForButton";
        
        private String getField0() { return field0; }
        private List<String> getField1() { return field1; }
    }
}
