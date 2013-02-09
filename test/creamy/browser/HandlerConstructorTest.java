/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package creamy.browser;

import creamy.activity.Activity;
import creamy.browser.CreamyTestBase;
import creamy.mvc.Response;
import creamy.mvc.Status;
import creamy.scene.control.CFHyperlink;
import creamy.scene.control.CFLinkButton;
import creamy.scene.control.CFSubmitButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author miyabetaiji
 */
public class HandlerConstructorTest extends CreamyTestBase {
    
    public HandlerConstructorTest() {
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
        act = new ActivityImpl();
        constructor = new HandlerConstructor(browser.getBrowserPanel().getBroker());
    }
    
    @After
    public void tearDown() {
    }

    private ActivityImpl act;
    private HandlerConstructor constructor;
    private String checkText;
    
    @Test
    public void testSubmitNoHandler() {
        System.out.println("testSubmitNoHandler");
        // Setup
        CFSubmitButton button = new CFSubmitButton();
        assertNull(button.getOnAction());
        HBox root = HBoxBuilder.create().children(button).build();
        constructor.constructHandler(root);
        // Assertion
        assertNotNull(button.getOnAction());
    }

    @Test
    public void testSubmitWithHandler() {
        System.out.println("testSubmitWithHandler");
        // Setup
        checkText = null;
        CFSubmitButton button = new CFSubmitButton();
        button.setOnAction(new EventHandler<ActionEvent> () {
            @Override
            public void handle(ActionEvent t) {
                checkText = "testSubmitWithHandler";
            }
        });
        HBox root = HBoxBuilder.create().children(button).build();
        constructor.constructHandler(root);
        // Assertion
        button.fireEvent(new ActionEvent());
        assertNotNull(button.getOnAction());
        assertEquals("testSubmitWithHandler", checkText);
    }
    
    @Test
    public void testSubmitWithHandlerAndCacnel() {
        System.out.println("testSubmitWithHandler");
        // Setup
        checkText = null;
        CFSubmitButton button = new CFSubmitButton();
        button.setOnAction(new EventHandler<ActionEvent> () {
            @Override
            public void handle(ActionEvent t) {
                act.cancelTest();
                checkText = "testSubmitWithHandler";
            }
        });
        HBox root = HBoxBuilder.create().children(button).build();
        constructor.constructHandler(root);
        // Assertion
        button.fireEvent(new ActionEvent());
        assertNotNull(button.getOnAction());
        assertNull(checkText);
    }

    @Test
    public void testHyperlinkNoHandler() {
        System.out.println("testHyperlinkNoHandler");
        // Setup
        CFHyperlink link = new CFHyperlink();
        assertNull(link.getOnAction());
        HBox root = HBoxBuilder.create().children(link).build();
        constructor.constructHandler(root);
        // Assertion
        assertNotNull(link.getOnAction());
    }

    @Test
    public void testHyperlinkWithHandler() {
        System.out.println("testHyperlinkWithHandler");
        // Setup
        checkText = null;
        CFHyperlink link = new CFHyperlink();
        link.setOnAction(new EventHandler<ActionEvent> () {
            @Override
            public void handle(ActionEvent t) {
                checkText = "testHyperlinkWithHandler";
            }
        });
        HBox root = HBoxBuilder.create().children(link).build();
        constructor.constructHandler(root);
        // Assertion
        boolean checkBln = false;
        try {
            link.fireEvent(new ActionEvent());
        } catch (Exception ex) {
            checkBln = true;
        }
        assertNotNull(link.getOnAction());
        assertEquals("testHyperlinkWithHandler", checkText);
        assertTrue(checkBln);
    }
    
    @Test
    public void testHyperlinkWithHandlerAndCacnel() {
        System.out.println("testHyperlinkWithHandlerAndCacnel");
        // Setup
        checkText = null;
        CFHyperlink link = new CFHyperlink();
        link.setOnAction(new EventHandler<ActionEvent> () {
            @Override
            public void handle(ActionEvent t) {
                act.cancelTest();
                checkText = "testHyperlinkWithHandlerAndCacnel";
            }
        });
        HBox root = HBoxBuilder.create().children(link).build();
        constructor.constructHandler(root);
        // Assertion
        boolean checkBln = false;
        try {
            link.fireEvent(new ActionEvent());
        } catch (Exception ex) {
            checkBln = true;
        }
        assertNotNull(link.getOnAction());
        assertNull(checkText);
        assertTrue(!checkBln);
    }

    @Test
    public void testlinkButton() {
        System.out.println("testlinkButton");
        // Setup
        CFLinkButton link = new CFLinkButton();
        assertNull(link.getOnAction());
        HBox root = HBoxBuilder.create().children(link).build();
        constructor.constructHandler(root);
        // Assertion
        assertNotNull(link.getOnAction());
    }
    
    public class ActivityImpl extends Activity {
        @Override
        public void initialize() {}
        public void cancelTest() { cancelRequest(); }
    }
}
