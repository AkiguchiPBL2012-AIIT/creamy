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
public class DefaultBackButtonTest {
    
    public DefaultBackButtonTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    private DefaultBackButton backButton;
    private TransitionManager manager;
    @Before
    public void setUp() {
        backButton = new DefaultBackButton();
        manager = new TransitionManager();
        manager.addBackListener(backButton);
        manager.goTo(createResponse());
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of onBackHistoryCleared method, of class DefaultBackButton.
     */
    @Test
    public void testOnBackHistoryCleared() {
        System.out.println("onBackHistoryCleared");
        manager.goBack();
        assertTrue(backButton.disableProperty().get());
    }

    /**
     * Test of onBackHistoryChanged method, of class DefaultBackButton.
     */
    @Test
    public void testOnBackHistoryChanged() {
        System.out.println("onBackHistoryChanged");
        manager.goTo(createResponse());
        assertFalse(backButton.disableProperty().get());
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
