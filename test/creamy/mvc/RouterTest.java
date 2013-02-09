/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package creamy.mvc;

import creamy.validation.ValidationResult;
import org.junit.*;
import static org.junit.Assert.*;


/**
 *
 * @author ATakahashi
 */
public class RouterTest {

    /**
     * Test of getInstance method, of class Router.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        Router result = Router.getInstance();
        //Nullではなくて取得するたびに同じ
        assertNotNull(result);
        assertEquals(Router.getInstance(), result);
    }

    /**
     * Test of processRequest method, of class Router.
     */
    @Test
    public void testProcessRequest() {
        System.out.println("processRequest");
        Request request = null;
        Router instance = null;
        Response expResult = null;
        Response result = instance.processRequest(request, ValidationResult.getEmptyResult());
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of processNoTransitionRequest method, of class Router.
     */
    @Test
    public void testProcessNoTransitionRequest() {
        System.out.println("processNoTransitionRequest");
        Request request = null;
        Router instance = null;
        Controller expResult = null;
        Controller result = instance.processNoTransitionRequest(request);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
