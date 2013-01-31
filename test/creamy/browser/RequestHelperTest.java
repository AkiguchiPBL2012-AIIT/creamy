/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package creamy.browser;

import creamy.scene.control.CFHyperlink;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author miyabetaiji
 */
public class RequestHelperTest {
    
    public RequestHelperTest() {
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
     * Test of getBroker method, of class RequestHelper.
     */
    @Test
    public void testGetBroker() {
        System.out.println("getBroker");
        // Setup
        CFHyperlink link = new CFHyperlink("testing");
        RequestHelper helper = new RequestHelper(link);
        BrowserPanel panel = new BrowserPanel();
        ((BrowserContent)panel.getCenter()).setContent(VBoxBuilder.create().children(link).build());
        // Assertion
        assertEquals(panel.getBroker(), helper.getBroker());
    }
}
