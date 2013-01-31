/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package creamy.browser.control;

import creamy.mvc.Request;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author miyabetaiji
 */
public class AddressBarTest {
    
    public AddressBarTest() {
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
     * Test of getMethod method, of class AddressBar.
     */
    @Test
    public void testGetMethod() {
        System.out.println("getMethod");
        AddressBar addresbar = new AddressBar();
        assertEquals(Request.GET, addresbar.getMethod());
    }

    /**
     * Test of getPath method, of class AddressBar.
     */
    @Test
    public void testGetPath() {
        System.out.println("getPath");
        AddressBar addresbar = new AddressBar();
        addresbar.setText("/test1/test2");
        try {
            addresbar.fireEvent(new ActionEvent());
        } catch(NullPointerException ex) {
            System.out.println("NullPointerException was caught. because borker nothing.");
        }
        assertEquals("/test1/test2", addresbar.getPath());
    }
}
