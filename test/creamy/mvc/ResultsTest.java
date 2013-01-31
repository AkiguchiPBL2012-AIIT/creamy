/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package creamy.mvc;

import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author miyabetaiji
 */
public class ResultsTest {
    
    public ResultsTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    private ResultsImpl instance;
    @Before
    public void setUp() {
        instance = new ResultsImpl();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of ok method, of class Results.
     */
    @Test
    public void testOk_0args() {
        System.out.println("ok");
        Result result = instance.ok();
        assertEquals(Status.OK, result.getStatus());
        assertTrue(result.isNoResponse());
    }

    /**
     * Test of ok method, of class Results.
     */
    @Test
    public void testOk_Controller() {
        System.out.println("ok");
        Controller controller;
        Result result = instance.ok(controller = new Controller() {});
        assertEquals(Status.OK, result.getStatus());
        assertTrue(result.isActivityResponse());
        assertEquals(controller, result.getController());
    }

    /**
     * Test of ok method, of class Results.
     */
    @Test
    public void testOk_Object() {
        System.out.println("ok");
        Object obj;
        Result result = instance.ok(obj = new Object());
        assertEquals(Status.OK, result.getStatus());
        assertTrue(result.isDataResponse());
        assertEquals(obj, result.getData());
    }

    /**
     * Test of badRequest method, of class Results.
     */
    @Test
    public void testBadRequest_0args() {
        System.out.println("badRequest");
        Result result = instance.badRequest();
        assertEquals(Status.BAD_REQUEST, result.getStatus());
        assertTrue(result.isNoResponse());
    }

    /**
     * Test of badRequest method, of class Results.
     */
    @Test
    public void testBadRequest_Controller() {
        System.out.println("badRequest");
        Controller controller;
        Result result = instance.badRequest(controller = new Controller() {});
        assertEquals(Status.BAD_REQUEST, result.getStatus());
        assertTrue(result.isActivityResponse());
        assertEquals(controller, result.getController());
    }

    /**
     * Test of badRequest method, of class Results.
     */
    @Test
    public void testBadRequest_Object() {
        System.out.println("badRequest");
        Object obj;
        Result result = instance.badRequest(obj = new Object());
        assertEquals(Status.BAD_REQUEST, result.getStatus());
        assertTrue(result.isDataResponse());
        assertEquals(obj, result.getData());
    }

    /**
     * Test of redirect method, of class Results.
     */
    @Test
    public void testRedirect() {
        System.out.println("redirect");
        String path = "testing";
        Result result = instance.redirect(path);
        assertEquals(Status.OK, result.getStatus());
        assertTrue(result.isRedirectResponse());
        assertEquals(path, result.getRedirectPath());
    }

    public class ResultsImpl extends Results {
    }
}
