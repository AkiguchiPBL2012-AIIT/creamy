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
public class ResultTest {
    
    public ResultTest() {
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
     * Test of getController method, of class Result.
     */
    @Test
    public void testGetController() {
        System.out.println("getController");
        // Setup
        Controller controller = new Controller() {};
        Result result = new Result(Status.OK, controller);
        // Assertion
        assertEquals(controller, result.getController());
    }

    /**
     * Test of getData method, of class Result.
     */
    @Test
    public void testGetData() {
        System.out.println("getData");
        // Setup
        Object data = new Object();
        Result result = new Result(Status.OK, data);
        // Assertion
        assertEquals(data, result.getData());
    }

    /**
     * Test of getStatus method, of class Result.
     */
    @Test
    public void testGetStatus() {
        System.out.println("getStatus");
        // Setup
        Object data = new Object();
        Result result = new Result(Status.OK, data);
        // Assertion
        assertEquals(Status.OK, result.getStatus());
    }

    /**
     * Test of getRedirectPath method, of class Result.
     */
    @Test
    public void testGetRedirectPath() {
        System.out.println("getRedirectPath");
        // Setup
        String path = "testing";
        Result result = new Result(Status.OK, path);
        // Assertion
        assertEquals(path, result.getRedirectPath());
    }

    /**
     * Test of isNoResponse method, of class Result.
     */
    @Test
    public void testIsNoResponse() {
        System.out.println("isNoResponse");
        // Setup
        Result result = new Result(Status.OK);
        // Assertion
        assertTrue(result.isNoResponse());
    }

    /**
     * Test of isRedirectResponse method, of class Result.
     */
    @Test
    public void testIsRedirectResponse() {
        System.out.println("isRedirectResponse");
        // Setup
        String path = "testing";
        Result result = new Result(Status.OK, path);
        // Assertion
        assertTrue(result.isRedirectResponse());
        assertTrue(!result.isNoResponse());
    }

    /**
     * Test of isActivityResponse method, of class Result.
     */
    @Test
    public void testIsActivityResponse() {
        System.out.println("isActivityResponse");
        // Setup
        Controller controller = new Controller() {};
        Result result = new Result(Status.OK, controller);
        // Assertion
        assertTrue(result.isActivityResponse());
        assertTrue(!result.isNoResponse());
    }

    /**
     * Test of isDataResponse method, of class Result.
     */
    @Test
    public void testIsDataResponse() {
        System.out.println("isDataResponse");
        // Setup
        Object data = new Object();
        Result result = new Result(Status.OK, data);
        // Assertion
        assertTrue(result.isDataResponse());
        assertTrue(!result.isNoResponse());
    }
}
