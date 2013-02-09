/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package creamy.activity;

import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author ATakahashi
 */
public class WebActivityTest {

    /**
     * Test of title method, of class WebActivity.
     */
    @Test
    public void testgetTitle() {
        System.out.println("title");
        WebActivity instance = new WebActivity();
        String expResult = "Web View";
        String result = instance.getTitle();
        assertEquals(expResult, result);
    }

    /**
     * Test of initialize method, of class WebActivity.
     */
    @Test
    public void testInitialize() {
        System.out.println("initialize");
        WebActivity instance = new WebActivity();
        try {
            instance.initialize();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
