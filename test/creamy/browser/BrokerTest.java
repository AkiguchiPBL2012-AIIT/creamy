/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package creamy.browser;

import creamy.mvc.Response;
import creamy.scene.control.*;
import creamy.scene.layout.CFGridForm;
import javafx.scene.Node;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author miyabetaiji
 */
public class BrokerTest {
    
    public BrokerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    private BrowserPanel panel;
    @Before
    public void setUp() {
        panel = new BrowserPanel();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of sendRequest method, of class Broker.
     */
    @Test
    public void testSendRequest_String() {
        System.out.println("sendRequest");
        // Setup
        Broker broker = new Broker(panel);
        String path = "/Application/list/0/name/asc/";
        Response response = broker.sendRequest(path);
        // Assertion
        assertEquals(path, response.getPath());
    }

    /**
     * Test of sendRequest method, of class Broker.
     */
    @Test
    public void testSendRequest_UnitRequest() {
        System.out.println("sendRequest");
        // Setup
        Broker broker = new Broker(panel);
        String path = "/Application/list/0/name/asc";
        CFHyperlink link = new CFHyperlink(path);
        broker.sendRequest(link);
        // Assertion
        assertEquals(path,panel.getTransitionManager().currentPathProperty().get());
    }

    /**
     * Test of sendRequest method, of class Broker.
     */
    @Test
    public void testSendRequest_GenericType() {
        System.out.println("sendRequest");
        // Setup
        Broker broker = new Broker(panel);
        String path = "/Application/list/0/name/asc";
        CFGridForm form = new CFGridForm(path);
        CFSubmitButton button = new CFSubmitButton();
        form.row(CFTextFieldBuilder.create().name("page").text("0").build())
            .row(CFTextFieldBuilder.create().name("sortBy").text("name").build())
            .row(CFTextFieldBuilder.create().name("order").text("asc").build())
            .row(CFTextFieldBuilder.create().name("filter").text("").build())    
            .row(button);
        broker.sendRequest(button);
        // Assertion
        Response response = panel.getTransitionManager().getCurrentResponse();
        System.out.println(response.getActivity().getScene().getClass().getName());
        assertEquals(path,panel.getTransitionManager().currentPathProperty().get());
    }
}
