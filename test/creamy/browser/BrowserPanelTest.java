/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package creamy.browser;

import creamy.activity.AvailableActivity;
import creamy.browser.control.DefaultHeader;
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
public class BrowserPanelTest {
    
    public BrowserPanelTest() {
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
     * Test of receiveResponse method, of class BrowserPanel.
     */
    @Test
    public void testReceiveResponse() {
        System.out.println("receiveResponse");
        // Setup
        BrowserPanel panel = new BrowserPanel();
        AvailableActivity activity = new TestActivity();
        String checktext = "testing";
        Pane content = HBoxBuilder.create().children(new TextField(checktext)).build();
        activity.setScene(content);
        //BrowserScene browserScene = new BrowserScene(activity, content);
        Response response = new Response(Status.OK, checktext, activity);
        panel.receiveResponse(response);
        // Assertion
        assertEquals(content, ((BrowserContent)panel.getCenter()).getContent());
    }

    /**
     * Test of getTransitionManager method, of class BrowserPanel.
     */
    @Test
    public void testGetTransitionManager() {
        System.out.println("getTransitionManager");
        // Setup
        BrowserPanel panel = new BrowserPanel();
        // Assertion
        assertNotNull(panel.getTransitionManager());
    }

    /**
     * Test of getBroker method, of class BrowserPanel.
     */
    @Test
    public void testGetBroker() {
        System.out.println("getBroker");
        // Setup
        BrowserPanel panel = new BrowserPanel();
        // Assertion
        assertNotNull(panel.getBroker());
    }

    /**
     * Test of setBroker method, of class BrowserPanel.
     */
    @Test
    public void testSetBroker() {
        System.out.println("setBroker");
        // Setup
        BrowserPanel panel = new BrowserPanel();
        Broker broker = new Broker(panel);
        panel.setBroker(broker);
        // Assertion
        assertEquals(broker, panel.getBroker());
    }

    /**
     * Test of getHeader method, of class BrowserPanel.
     */
    @Test
    public void testGetHeader() {
        System.out.println("getHeader");
        // Setup
        BrowserPanel panel = new BrowserPanel();
        // Assertion
        assertNull(panel.getHeader());
    }

    /**
     * Test of setHeader method, of class BrowserPanel.
     */
    @Test
    public void testSetHeader() {
        System.out.println("setHeader");
        // Setup
        BrowserPanel panel = new BrowserPanel();
        DefaultHeader header = new DefaultHeader();
        panel.setHeader(header);
        // Assertion
        assertEquals(header, panel.getHeader());
        assertEquals(header, ((BrowserContent)panel.getTop()).getContent());
    }

    /**
     * Test of buildHeader method, of class BrowserPanel.
     */
    @Test
    public void testBuildHeader() {
        System.out.println("buildHeader");
        // Setup
        BrowserPanel panel = new BrowserPanel();
        panel.buildHeader();
        // Assertion
        assertNotNull(panel.getTop());
        assertTrue(panel.getTop() instanceof BrowserContent);
    }

    /**
     * Test of buildBody method, of class BrowserPanel.
     */
    @Test
    public void testBuildBody() {
        System.out.println("buildBody");
        // Setup
        BrowserPanel panel = new BrowserPanel();
        panel.buildBody();
        // Assertion
        assertNotNull(panel.getCenter());
        assertTrue(panel.getCenter() instanceof BrowserBody);
    }
    
    public class TestActivity extends AvailableActivity {
    }
}
