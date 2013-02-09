/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package creamy.browser;

import creamy.activity.Activity;
import creamy.activity.AvailableActivity;
import creamy.mvc.Response;
import creamy.mvc.Status;
import javafx.beans.value.ObservableValue;
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
public class BrowserBodyTest {
    
    public BrowserBodyTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {}
    
    @After
    public void tearDown() {}

    /**
     * Test of changed method, of class BrowserBody.
     */
    @Test
    public void testChanged() {
        System.out.println("changed");
        // Setup
        TransitionManager manager = new TransitionManager();
        BrowserBody body = new BrowserBody(manager);
        manager.addSceneListener(body);
        Pane content = null;
        for (int i = 0; i < 3; i++) {
            Activity activity = new TestActivity();
            String checktext = "testing" + i;
            Pane scene = HBoxBuilder.create().children(new TextField(checktext)).build();
            activity.setScene(scene);
            if (i == 2) content = scene;
            Response response = new Response(Status.OK, checktext, activity);
            manager.goTo(response);
        }
        // Assertion
        assertEquals(content, body.getContent());
    }

    public class TestActivity extends AvailableActivity {
    }
}
