/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package creamy.browser;

import creamy.utils.FinderUtil;
import creamy.activity.AvailableActivity;
import creamy.browser.control.AddressBar;
import creamy.browser.control.DefaultBackButton;
import creamy.browser.control.DefaultForwardButton;
import creamy.mvc.Response;
import creamy.mvc.Status;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author miyabetaiji
 */
public class BrowserContentTest {
    
    public BrowserContentTest() {
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
     * Test of getContent method, of class BrowserContent.
     */
    @Test
    public void testGetContent() {
        System.out.println("getContent");
        // Setup
        String checktext = "testing";
        TransitionManager manager = new TransitionManager();
        BrowserContent brcon = new BrowserContent(manager);
        Pane content = HBoxBuilder.create().children(new TextField(checktext)).build();
        brcon.setContent(content);
        // Assertion
        assertEquals(content, brcon.getContent());
    }

    /**
     * Test of setContent method, of class BrowserContent.
     */
    @Test
    public void testSetContent() {
        System.out.println("setContent");
        // Setup
        String checktext = "testing";
        TransitionManager manager = new TransitionManager();
        BrowserContent brcon = new BrowserContent(manager);
        Pane content = HBoxBuilder.create().children(new TextField(checktext)).build();
        brcon.setContent(content);
        // Assertion
        assertEquals(content, brcon.getContent());
        assertEquals(content, brcon.getChildren().get(0));
    }

    /**
     * Test of replaceContent method, of class BrowserContent.
     */
    @Test
    public void testReplaceContent() {
        System.out.println("replaceContent");
        // Setup
        String checktext = "testing";
        TransitionManager manager = new TransitionManager();
        BrowserContent brcon = new BrowserContent(manager);
        Pane content0 = HBoxBuilder.create().children(new TextField(checktext)).build();
        brcon.setContent(content0);
        Pane content1 = HBoxBuilder.create().children(new TextField(checktext)).build();
        brcon.replaceContent(content1);
        // Assertion
        assertEquals(content1, brcon.getContent());
    }

    /**
     * Test of registerBrowserFunction method, of class BrowserContent.
     */
    @Test
    public void testRegisterBrowserFunction() {
        System.out.println("registerBrowserFunction");
        // Setup
        TransitionManager manager = new TransitionManager();
        BrowserContent content = new BrowserContent(manager);
        DefaultBackButton backButton = new DefaultBackButton();
        DefaultForwardButton forwardButton = new DefaultForwardButton();
        AddressBar addresBar = new AddressBar();
        VBox vbox = new VBox();
        vbox.getChildren().addAll(backButton, forwardButton, addresBar);
        content.setContent(vbox);
        for (int i = 0; i < 3; i++) {
            AvailableActivity activity = new TestActivity();
            String checktext = "testing" + i;
            Pane scene = HBoxBuilder.create().children(new TextField(checktext)).build();
            //BrowserScene browserScene = new BrowserScene(activity, scene);
            Response response = new Response(Status.OK, checktext, activity);
            manager.goTo(response);
        }
        // Assertion
        assertEquals(false, backButton.disableProperty().get());
        assertEquals(true, forwardButton.disableProperty().get());
        assertEquals("testing2", addresBar.getText());
        manager.goBack();
        manager.goBack();
        assertEquals(true, backButton.disableProperty().get());
        assertEquals(false, forwardButton.disableProperty().get());
        assertEquals("testing0", addresBar.getText());
    }

    /**
     * Test of getElementsByClass method, of class BrowserContent.
     */
    @Test
    public void testGetElementsByClass() {
        System.out.println("getElementsByClass");
        // Setup
        TransitionManager manager = new TransitionManager();
        BrowserContent content = new BrowserContent(manager);
        DefaultBackButton backButton = new DefaultBackButton();
        DefaultForwardButton forwardButton = new DefaultForwardButton();
        AddressBar addressBar = new AddressBar();
        VBox vbox = new VBox();
        vbox.getChildren().addAll(backButton, forwardButton, addressBar);
        HBox hbox = new HBox();
        Button button = new Button();
        hbox.getChildren().addAll(button, vbox);
        content.setContent(hbox);
        // Assertion
        assertEquals(backButton, (FinderUtil.lookup(content, DefaultBackButton.class)));
        assertEquals(forwardButton, (FinderUtil.lookup(content, DefaultForwardButton.class)));
        assertEquals(addressBar, (FinderUtil.lookup(content, AddressBar.class)));
        List<Button> list = new ArrayList<Button>();
        list.add(button);
        list.add(backButton);
        list.add(forwardButton);
        Set<Button> result = FinderUtil.lookupAll(content, Button.class);
        assertEquals(list.size(), result.size());
        /*
        for (int i = 0; i < list.size(); i++) {
            assertEquals(list.get(i), result.get(i));
        }
        * 
        */
    }

    public class TestActivity extends AvailableActivity {
    }
}
