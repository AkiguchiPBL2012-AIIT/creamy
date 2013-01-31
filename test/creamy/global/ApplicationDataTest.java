/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package creamy.global;

import creamy.browser.Browser;
import creamy.browser.CreamyTestBase;
import creamy.browser.control.DefaultHeader;
import java.util.Map;
import javafx.application.Platform;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author miyabetaiji
 */
public class ApplicationDataTest extends CreamyTestBase {
    
    public ApplicationDataTest() {
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
     * Test of getInstance method, of class ApplicationData.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        // Assertion
        try {Thread.sleep(100);} catch (InterruptedException ex) {ex.printStackTrace();};
        synchronized(lock) {
            assertNotNull(ApplicationData.getInstance());
        }
    }

    /**
     * Test of getData method, of class ApplicationData.
     */
    @Test
    public void testGetData() {
        System.out.println("getData");
        // Assertion
        try {Thread.sleep(100);} catch (InterruptedException ex) {ex.printStackTrace();};
        synchronized(lock) {
            assertNotNull(ApplicationData.getInstance().getData());
        }
    }
}
