/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package creamy.browser;

import creamy.activity.AvailableActivity;
import creamy.mvc.Response;
import creamy.mvc.Status;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.Pane;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author miyabetaiji
 */
public class TabBrowserBodyTest {
    
    public TabBrowserBodyTest() {
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
     * Test of changed method, of class TabBrowserBody.
     */
    @Test
    public void testChanged() {
        System.out.println("changed");
        // Setup
        TransitionManager manager = new TransitionManager();
        TabBrowserBody body = new TabBrowserBody(manager, null);
        manager.addSceneListener(body);
        Pane content = null;
        for (int i = 0; i < 3; i++) {
            AvailableActivity activity = new TestActivity();
            String checktext = "testing" + i;
            Pane scene = HBoxBuilder.create().children(new TextField(checktext)).build();
            activity.setScene(scene);
            if (i == 2) content = scene;
            //BrowserScene browserScene = new BrowserScene(activity, scene);
            Response response = new Response(Status.OK, checktext, activity);
            manager.goTo(response);
        }
        // Assertion
        assertEquals(content, body.getContent());
    }
    
    public class TestActivity extends AvailableActivity {
    }
}
