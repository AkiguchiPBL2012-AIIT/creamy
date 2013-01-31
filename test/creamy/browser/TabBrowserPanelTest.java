/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package creamy.browser;

import javafx.scene.layout.Pane;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author miyabetaiji
 */
public class TabBrowserPanelTest {
    
    public TabBrowserPanelTest() {
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
     * Test of getBrowser method, of class TabBrowserPanel.
     */
    @Test
    public void testGetBrowser() {
        System.out.println("getBrowser");
        TabBrowserPanel instance = null;
        TabBrowser expResult = null;
        TabBrowser result = instance.getBrowser();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getHeader method, of class TabBrowserPanel.
     */
    @Test
    public void testGetHeader() {
        System.out.println("getHeader");
        TabBrowserPanel instance = null;
        Pane expResult = null;
        Pane result = instance.getHeader();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setHeader method, of class TabBrowserPanel.
     */
    @Test
    public void testSetHeader() {
        System.out.println("setHeader");
        Pane headerContent = null;
        TabBrowserPanel instance = null;
        instance.setHeader(headerContent);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of buildHeader method, of class TabBrowserPanel.
     */
    @Test
    public void testBuildHeader() {
        System.out.println("buildHeader");
        TabBrowserPanel instance = null;
        instance.buildHeader();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of buildBody method, of class TabBrowserPanel.
     */
    @Test
    public void testBuildBody() {
        System.out.println("buildBody");
        TabBrowserPanel instance = null;
        instance.buildBody();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
