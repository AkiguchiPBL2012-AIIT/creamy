/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package creamy.mvc;

import creamy.browser.Browser;
import creamy.browser.CreamyTestBase;
import java.util.LinkedHashMap;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author miyabetaiji
 */
public class RequestTest extends CreamyTestBase {
    
    public RequestTest() {
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
     * Test of getBrowser method, of class Request.
     */
    @Test
    public void testGetBrowser() {
        System.out.println("getBrowser");
        // Setup
        Request request = new Request(browser, Request.GET, "/testing", "");
        // Assertion
        assertEquals(browser, request.getBrowser());
    }

    /**
     * Test of setBrowser method, of class Request.
     */
    @Test
    public void testSetBrowser() {
        System.out.println("setBrowser");
        // Setup
        Request request = new Request(null, Request.GET, "/testing", "");
        request.setBrowser(browser);
        // Assertion
        assertEquals(browser, request.getBrowser());
    }
}
