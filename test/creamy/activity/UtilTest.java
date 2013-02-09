/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package creamy.activity;

import creamy.utils.ResourceUtil;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarFile;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author miyabetaiji
 */
public class UtilTest {
    
    public UtilTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    List<URL> urls = new ArrayList<URL>();
    List<URL> jars = new ArrayList<URL>();
    
    @Before
    public void setUp() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        
        urls.add(loader.getResource("creamy/activity/fortest/F0"));
        urls.add(loader.getResource("creamy/activity/fortest/F1"));
        urls.add(loader.getResource("creamy/activity/fortest/sub/"));
        urls.add(loader.getResource("creamy/activity/fortest/sub/F2"));
        
        jars.add(loader.getResource("junit/textui/ResultPrinter.class"));
        jars.add(loader.getResource("junit/textui/TestRunner.class"));
        jars.add(loader.getResource("junit/textui/package-info.class"));
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getResources method, of class Util.
     */
    @Test
    public void testGetResources_String() throws Exception {
        System.out.println("getResources");
        // Setup
        List<URL> res = ResourceUtil.getResources("creamy/activity/fortest");
        // Assertion
        assertEquals(urls.size(), res.size());
        for (int i = 0; i < urls.size(); i++) {
            assertEquals(urls.get(i), res.get(i));
        }
    }

    /**
     * Test of getResources method, of class Util.
     */
    @Test
    public void testGetResources_URL() throws Exception {
        System.out.println("getResources");
        // Setup
        URL url = Thread.currentThread().getContextClassLoader().getResource("creamy/activity/fortest");
        List<URL> res = ResourceUtil.getResources(url);
        // Assertion
        assertEquals(urls.size(), res.size());
        for (int i = 0; i < urls.size(); i++) {
            assertEquals(urls.get(i), res.get(i));
        }
    }

    /**
     * Test of getResourcesFromFile method, of class Util.
     */
    @Test
    public void testGetResourcesFromFile_URL() throws Exception {
        System.out.println("getResourcesFromFile");
        // Setup
        URL url = Thread.currentThread().getContextClassLoader().getResource("creamy/activity/fortest");
        List<URL> res = ResourceUtil.getResourcesFromFile(url);
        // Assertion
        assertEquals(urls.size(), res.size());
        for (int i = 0; i < urls.size(); i++) {
            assertEquals(urls.get(i), res.get(i));
        }
    }

    /**
     * Test of getResourcesFromFile method, of class Util.
     */
    @Test
    public void testGetResourcesFromFile_File() throws Exception {
        System.out.println("getResourcesFromFile");
        // Setup
        URL url = Thread.currentThread().getContextClassLoader().getResource("creamy/activity/fortest");
        List<URL> res = ResourceUtil.getResourcesFromFile(new File(url.getFile()));
        // Assertion
        assertEquals(urls.size(), res.size());
        for (int i = 0; i < urls.size(); i++) {
            assertEquals(urls.get(i), res.get(i));
        }
    }

    /**
     * Test of getResourcesFromJar method, of class Util.
     */
    @Test
    public void testGetResourcesFromJar_URL() throws Exception {
        System.out.println("getResourcesFromJar");
        // Setup
        URL url = Thread.currentThread().getContextClassLoader().getResource("junit/textui");
        List<URL> res = ResourceUtil.getResourcesFromJar(url);
        // Assertion
        assertEquals(jars.size(), res.size());
        for (int i = 0; i < jars.size(); i++) {
            assertEquals(jars.get(i), res.get(i));
        }
    }

    /**
     * Test of inputStreamToString method, of class Util.
     */
    @Test
    public void testInputStreamToString_InputStream() throws Exception {
        System.out.println("inputStreamToString");
        // Setup
        InputStream is = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("creamy/activity/fortest/F0");
        String str = ResourceUtil.inputStreamToString(is);
        // Assertion
        assertEquals("TESTING", str);
    }

    /**
     * Test of inputStreamToString method, of class Util.
     */
    @Test
    public void testInputStreamToString_InputStream_int() throws Exception {
        System.out.println("inputStreamToString");
        // Setup
        InputStream is = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("creamy/activity/fortest/F0");
        String str = ResourceUtil.inputStreamToString(is, 1024);
        // Assertion
        assertEquals("TESTING", str);
    }
}
