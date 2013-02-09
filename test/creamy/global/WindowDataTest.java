/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package creamy.global;

import creamy.browser.Browser;
import creamy.browser.CreamyTestBase;
import java.util.Map;
import javafx.application.Platform;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author miyabetaiji
 */
public class WindowDataTest extends CreamyTestBase {
    
    public WindowDataTest() {
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
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getInstance method, of class WindowData.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        // Assertion
        try {Thread.sleep(100);} catch (InterruptedException ex) {ex.printStackTrace();}
        synchronized(lock) {
            assertNotNull(WindowData.getInstance());
        }
    }

    /**
     * Test of getData method, of class WindowData.
     */
    @Test
    public void testGetData() {
        System.out.println("getData");
        // Assertion
        try {Thread.sleep(100);} catch (InterruptedException ex) {ex.printStackTrace();}
        synchronized(lock) {
            assertNotNull(WindowData.getInstance().getData(browser));
        }
    }

    /**
     * Test of removeData method, of class WindowData.
     */
    @Test
    public void testRemoveData() {
        System.out.println("removeData");
        // Setup
        final String key = "testing";
        final Object value = new Object();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                synchronized(lock) {
                    WindowData.getInstance().getData(browser).put(key, value);
                }
            }
        });
        // Assertion
        try {Thread.sleep(100);} catch (InterruptedException ex) {ex.printStackTrace();}
        synchronized(lock) {
            assertEquals(value, WindowData.getInstance().getData(browser).get(key));
            WindowData.getInstance().removeData(browser);
            assertNull(WindowData.getInstance().getData(browser).get(key));
        }
    }
}
