/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package creamy.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author miyabetaiji
 */
public class RenderParameterTest {
    
    public RenderParameterTest() {
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
     * Test of putParam method, of class RenderParameter.
     */
    @Test
    public void testPutParam() {
        System.out.println("putParam");
        // Setup
        RenderParameter rp = new RenderParameter();
        for (int i = 0; i < 10; i++) {
            rp.putParam("k" + i, "v" + i);
        }
        // Assertion
        assertEquals("v0", rp.getParams("C000000000").get("k0"));
        assertEquals("v9", rp.getParams("C000000009").get("k9"));
    }

    /**
     * Test of putParams method, of class RenderParameter.
     */
    @Test
    public void testPutParams() {
        System.out.println("putParams");
        // Setup
        RenderParameter rp = new RenderParameter();
        List<String> keys = new ArrayList<String>();
        List<Object> values = new ArrayList<Object>();
        for (int i = 0; i < 10; i++) {
            keys.add("k" + i);
            values.add("v" + i);
        }
        rp.putParams(keys, values);
        // Assertion
        assertEquals("v0", rp.getParams("C000000000").get("k0"));
        assertEquals("v9", rp.getParams("C000000000").get("k9"));
    }
}
