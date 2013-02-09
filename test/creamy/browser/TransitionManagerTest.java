/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package creamy.browser;

import creamy.activity.Activity;
import creamy.activity.AvailableActivity;
import creamy.mvc.Response;
import creamy.mvc.Status;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.Pane;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author miyabetaiji
 */
public class TransitionManagerTest {
    
    public TransitionManagerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of goTo method, of class TransitionManager.
     */
    @Test
    public void testGoTo() {
        System.out.println("goTo");
        // Setup
        Response currentResponse = null;
        TransitionManager manager = new TransitionManager();
        for (int i = 0; i < 3; i++) {
            TestActivity activity = new TestActivity();
            String checktext = "testing" + i;
            HBox scene = HBoxBuilder.create().children(new TextField(checktext)).build();
            activity.setScene(scene);
            //BrowserScene browserScene = new BrowserScene(activity, scene);
            Response response = new Response(Status.OK, checktext, activity);
            if (i == 2) currentResponse = response;
            manager.goTo(response);
        }
        // Assertion
        assertEquals("testing2", manager.currentPathProperty().get());
        assertEquals(currentResponse, manager.getCurrentResponse());
    }

    /**
     * Test of goBack method, of class TransitionManager.
     */
    @Test
    public void testGoBack() {
        System.out.println("goBack");
        // Setup
        Response currentResponse = null;
        TransitionManager manager = new TransitionManager();
        for (int i = 0; i < 3; i++) {
            TestActivity activity = new TestActivity();
            String checktext = "testing" + i;
            HBox scene = HBoxBuilder.create().children(new TextField(checktext)).build();
            activity.setScene(scene);
            //BrowserScene browserScene = new BrowserScene(activity, scene);
            Response response = new Response(Status.OK, checktext, activity);
            if (i == 1) currentResponse = response;
            manager.goTo(response);
        }
        manager.goBack();
        // Assertion
        assertEquals("testing1", manager.currentPathProperty().get());
        assertEquals(currentResponse, manager.getCurrentResponse());
    }

    /**
     * Test of goForward method, of class TransitionManager.
     */
    @Test
    public void testGoForward() {
        System.out.println("goForward");
        // Setup
        Response currentResponse = null;
        TransitionManager manager = new TransitionManager();
        for (int i = 0; i < 3; i++) {
            TestActivity activity = new TestActivity();
            String checktext = "testing" + i;
            HBox scene = HBoxBuilder.create().children(new TextField(checktext)).build();
            activity.setScene(scene);
            //BrowserScene browserScene = new BrowserScene(activity, scene);
            Response response = new Response(Status.OK, checktext, activity);
            if (i == 2) currentResponse = response;
            manager.goTo(response);
        }
        manager.goBack();
        manager.goForward();
        // Assertion
        assertEquals("testing2", manager.currentPathProperty().get());
        assertEquals(currentResponse, manager.getCurrentResponse());
    }

    /**
     * Test of addSceneListener method, of class TransitionManager.
     */
    @Test
    public void testAddSceneListener() {
        System.out.println("addSceneListener");
        // Setup
        TransitionManager manager = new TransitionManager();
        ChangeListener<Node> listener = createScenelistener();
        manager.addSceneListener(listener);
        Response response;
        manager.goTo(response = createResponse());
        // Assertion
        assertEquals(response.getActivity().getScene(), checkNode);
    }

    /**
     * Test of removeSceneListener method, of class TransitionManager.
     */
    @Test
    public void testRemoveSceneListener() {
        System.out.println("removeSceneListener");
        // Setup
        TransitionManager manager = new TransitionManager();
        ChangeListener<Node> listener = createScenelistener();
        manager.addSceneListener(listener);
        manager.removeSceneListener(listener);
        Response response;
        manager.goTo(response = createResponse());
        // Assertion
        assertNull(checkNode);
    }

    /**
     * Test of addBackListener method, of class TransitionManager.
     */
    @Test
    public void testAddBackListener() {
        System.out.println("addBackListener");
        // Setup
        Response currentResponse = null;
        TransitionManager manager = new TransitionManager();
        ListChangeListener<Response> listener = createListListener();
        manager.addBackListener(listener);
        for (int i = 0; i < 3; i++) {
            TestActivity activity = new TestActivity();
            String checktext = "testing" + i;
            HBox scene = HBoxBuilder.create().children(new TextField(checktext)).build();
            //BrowserScene browserScene = new BrowserScene(activity, scene);
            Response response = new Response(Status.OK, checktext, activity);
            if (i == 1) currentResponse = response;
            manager.goTo(response);
        }
        // Assertion
        assertEquals(2, checkList.size());
        assertEquals(currentResponse, checkList.get(checkList.size() - 1));
    }

    /**
     * Test of removeBackListener method, of class TransitionManager.
     */
    @Test
    public void testRemoveBackListener() {
        System.out.println("removeBackListener");
        // Setup
        Response currentResponse = null;
        TransitionManager manager = new TransitionManager();
        ListChangeListener<Response> listener = createListListener();
        manager.addBackListener(listener);
        manager.removeBackListener(listener);
        for (int i = 0; i < 3; i++) {
            TestActivity activity = new TestActivity();
            String checktext = "testing" + i;
            HBox scene = HBoxBuilder.create().children(new TextField(checktext)).build();
            //BrowserScene browserScene = new BrowserScene(activity, scene);
            Response response = new Response(Status.OK, checktext, activity);
            if (i == 1) currentResponse = response;
            manager.goTo(response);
        }
        // Assertion
        assertNull(checkList);
    }

    /**
     * Test of addForwardListener method, of class TransitionManager.
     */
    @Test
    public void testAddForwardListener() {
        System.out.println("addForwardListener");
        // Setup
        Response currentResponse = null;
        TransitionManager manager = new TransitionManager();
        ListChangeListener<Response> listener = createListListener();
        manager.addForwardListener(listener);
        for (int i = 0; i < 3; i++) {
            TestActivity activity = new TestActivity();
            String checktext = "testing" + i;
            HBox scene = HBoxBuilder.create().children(new TextField(checktext)).build();
            activity.setScene(scene);
            //BrowserScene browserScene = new BrowserScene(activity, scene);
            Response response = new Response(Status.OK, checktext, activity);
            if (i == 1) currentResponse = response;
            manager.goTo(response);
        }
        manager.goBack();
        manager.goBack();
        // Assertion
        assertEquals(2, checkList.size());
        assertEquals(currentResponse, checkList.get(0));
    }

    /**
     * Test of removeForwardListener method, of class TransitionManager.
     */
    @Test
    public void testRemoveForwardListener() {
        System.out.println("removeForwardListener");
        // Setup
        Response currentResponse = null;
        TransitionManager manager = new TransitionManager();
        ListChangeListener<Response> listener = createListListener();
        manager.addForwardListener(listener);
        manager.removeForwardListener(listener);
        for (int i = 0; i < 3; i++) {
            TestActivity activity = new TestActivity();
            String checktext = "testing" + i;
            HBox scene = HBoxBuilder.create().children(new TextField(checktext)).build();
            activity.setScene(scene);
            //BrowserScene browserScene = new BrowserScene(activity, scene);
            Response response = new Response(Status.OK, checktext, activity);
            if (i == 1) currentResponse = response;
            manager.goTo(response);
        }
        manager.goBack();
        manager.goBack();
        // Assertion
        assertNull(checkList);
    }

    /**
     * Test of CurrentTitleProperty method, of class TransitionManager.
     */
    @Test
    public void testCurrentTitleProperty() {
        System.out.println("CurrentTitleProperty");
        // Setup
        Response currentResponse = null;
        TransitionManager manager = new TransitionManager();
        ListChangeListener<Response> listener = createListListener();
        manager.addForwardListener(listener);
        manager.removeForwardListener(listener);
        for (int i = 0; i < 3; i++) {
            TestActivity activity = new TestActivity();
            activity.initialize(null, new HashMap<Field, Object>());
            String checktext = "testing" + i;
            HBox scene = HBoxBuilder.create().children(new TextField(checktext)).build();
            activity.setScene(scene);
            //BrowserScene browserScene = new BrowserScene(activity, scene);
            Response response = new Response(Status.OK, checktext, activity);
            if (i == 1) currentResponse = response;
            manager.goTo(response);
        }
        // Assertion
        assertNotNull(manager.currentTitleProperty());
        assertEquals("testing", manager.currentTitleProperty().get());
    }

    /**
     * Test of CurrentPathProperty method, of class TransitionManager.
     */
    @Test
    public void testCurrentPathProperty() {
        System.out.println("CurrentPathProperty");
        // Setup
        Response currentResponse = null;
        TransitionManager manager = new TransitionManager();
        ListChangeListener<Response> listener = createListListener();
        manager.addForwardListener(listener);
        manager.removeForwardListener(listener);
        for (int i = 0; i < 3; i++) {
            TestActivity activity = new TestActivity();
            activity.initialize(null, new HashMap<Field, Object>());
            String checktext = "testing" + i;
            HBox scene = HBoxBuilder.create().children(new TextField(checktext)).build();
            activity.setScene(scene);
            //BrowserScene browserScene = new BrowserScene(activity, scene);
            Response response = new Response(Status.OK, checktext, activity);
            if (i == 1) currentResponse = response;
            manager.goTo(response);
        }
        // Assertion
        assertNotNull(manager.currentPathProperty());
        assertEquals("testing2", manager.currentPathProperty().get());
    }

    /**
     * Test of getCurrentResponse method, of class TransitionManager.
     */
    @Test
    public void testGetCurrentResponse() {
        System.out.println("getCurrentResponse");
        // Setup
        Response currentResponse = null;
        TransitionManager manager = new TransitionManager();
        for (int i = 0; i < 3; i++) {
            TestActivity activity = new TestActivity();
            activity.initialize(null, new HashMap<Field, Object>());
            String checktext = "testing" + i;
            HBox scene = HBoxBuilder.create().children(new TextField(checktext)).build();
            activity.setScene(scene);
            //BrowserScene browserScene = new BrowserScene(activity, scene);
            Response response = new Response(Status.OK, checktext, activity);
            if (i == 2) currentResponse = response;
            manager.goTo(response);
        }
        // Assertion
        assertNotNull(manager.getCurrentResponse());
        assertEquals(currentResponse, manager.getCurrentResponse());
    }

    /**
     * Test of isNewScene method, of class TransitionManager.
     */
    @Test
    public void testIsNewScene() {
        System.out.println("isNewScene");
        // Setup
        TransitionManager manager = new TransitionManager();
        List<Pane> scenes = new ArrayList<Pane>();
        for (int i = 0; i < 3; i++) {
            TestActivity activity = new TestActivity();
            activity.initialize(null, new HashMap<Field, Object>());
            String checktext = "testing" + i;
            Pane scene = HBoxBuilder.create().children(new TextField(checktext)).build();
            activity.setScene(scene);
            scenes.add(scene);
            //BrowserScene browserScene = new BrowserScene(activity, scene);
            Response response = new Response(Status.OK, checktext, activity);
            manager.goTo(response);
        }
        // Assertion
        assertTrue(manager.isNewScene(scenes.get(2)));
        manager.goBack();
        for (Pane scene : scenes) assertFalse(manager.isNewScene(scene));
        manager.goForward();
        for (Pane scene : scenes) assertFalse(manager.isNewScene(scene));
    }

    public class TestActivity extends AvailableActivity {
        public TestActivity() {
            this.title = "testing";
        }
        public void initialize(Activity parent, Map<Field,Object> params) {
            initialize(parent, params, new HashMap<String,Object>());
        }
    }

    private Response createResponse() {
        TestActivity activity = new TestActivity();
        activity.initialize(null, new HashMap<Field, Object>());
        String checktext = "/testing";
        HBox scene = HBoxBuilder.create().children(new TextField(checktext)).build();
        activity.setScene(scene);
        //BrowserScene browserScene = new BrowserScene(activity, scene);
        return new Response(Status.OK, checktext, activity);
    }

    private String checkString;
    private ChangeListener<String> createStringlistener() {
        checkString = null;
        return new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                checkString = t1;
            }
        };
    }

    private Node checkNode;
    private ChangeListener<Node> createScenelistener() {
        checkNode = null;
        return new ChangeListener<Node>() {
            @Override
            public void changed(ObservableValue<? extends Node> ov, Node t, Node t1) {
                checkNode = t1;
            }
        };
    }

    private ObservableList<Response> checkList;
    private ListChangeListener<Response> createListListener() {
        return new ListChangeListener<Response>() {
            @Override
            public void onChanged(Change<? extends Response> change) {
                checkList = (ObservableList<Response>)change.getList();
            }
        };
    }
}
