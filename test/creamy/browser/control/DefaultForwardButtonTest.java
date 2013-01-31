/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package creamy.browser.control;

import creamy.activity.Activity;
import creamy.activity.AvailableActivity;
import creamy.browser.TransitionManager;
import creamy.mvc.Response;
import creamy.mvc.Status;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.Pane;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author miyabetaiji
 */
public class DefaultForwardButtonTest {
    
    public DefaultForwardButtonTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    private DefaultForwardButton forwardButton;
    private TransitionManager manager;
    @Before
    public void setUp() {
        forwardButton = new DefaultForwardButton();
        manager = new TransitionManager();
        manager.addForwardListener(forwardButton);
        manager.goTo(createResponse());
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of onForwardHistoryCleared method, of class DefaultForwardButton.
     */
    @Test
    public void testOnForwardHistoryCleared() {
        System.out.println("onForwardHistoryCleared");
        manager.goTo(createResponse());
        assertTrue(forwardButton.disableProperty().get());
    }

    /**
     * Test of onForwardHistoryChanged method, of class DefaultForwardButton.
     */
    @Test
    public void testOnForwardHistoryChanged() {
        System.out.println("onForwardHistoryChanged");
        manager.goTo(createResponse());
        manager.goBack();
        assertFalse(forwardButton.disableProperty().get());
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
        Pane scene = HBoxBuilder.create().children(new TextField(checktext)).build();
        activity.setScene(scene);
        //BrowserScene browserScene = new BrowserScene(activity, scene);
        return new Response(Status.OK, checktext, activity);
    }
}
