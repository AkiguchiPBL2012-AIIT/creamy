/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package creamy.activity;

import creamy.scene.control.*;
import creamy.scene.layout.CFGridForm;
import creamy.scene.layout.CFHForm;
import javafx.scene.Node;
import javafx.scene.control.LabelBuilder;
import javafx.scene.layout.HBoxBuilder;
import javafx.util.Builder;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author ATakahashi
 */
public class HelperTest {
    
    public HelperTest() {
    }

    /**
     * Test of gridForm method, of class Helper.
     */
    @Test
    public void testGridForm() {
        System.out.println("gridForm");
        String path = "TestPath";
        ActivityHelper instance = new ActivityHelper();
        CFGridForm expResult = null;
        CFGridForm result = instance.gridForm(path);
        assertEquals(path, result.getPath());
    }

    /**
     * Test of hform method, of class Helper.
     */
    @Test
    public void testHform() {
        System.out.println("hform");
        String path = "TestPath";
        ActivityHelper instance = new ActivityHelper();
        CFHForm expResult = null;
        CFHForm result = instance.hform(path);
        assertEquals(path, result.getPath());
    }

    /**
     * Test of label method, of class Helper.
     */
    @Test
    public void testLabel() {
        System.out.println("label");
        String text = "TestString";
        ActivityHelper instance = new ActivityHelper();
        LabelBuilder expResult = null;
        //TODO:未実装？
        LabelBuilder result = instance.label(text);
        
    }

    /**
     * Test of text method, of class Helper.
     */
    @Test
    public void testText() {
        System.out.println("text");
        String name = "TestName";
        ActivityHelper instance = new ActivityHelper();
        CFTextFieldBuilder result = instance.text(name);
        //TODO:未実装？
        assertEquals(name, result.build().getCFName());
    }

    /**
     * Test of choice method, of class Helper.
     */
    @Test
    public void testChoice() {
        System.out.println("choice");
        String name = "TestName";
        ActivityHelper instance = new ActivityHelper();
        CFChoiceBoxBuilder result = instance.choice(name);
        assertEquals(name, result.build().getCFName());
    }

    /**
     * Test of button method, of class Helper.
     */
    @Test
    public void testButton() {
        System.out.println("button");
        String name = "TestName";
        ActivityHelper instance = new ActivityHelper();
        CFButtonBuilder result = instance.button(name);
        assertEquals(name, result.build().getName());
    }

    /**
     * Test of hyperlink method, of class Helper.
     */
    @Test
    public void testHyperlink() {
        System.out.println("hyperlink");
        String path = "TestPath";
        ActivityHelper instance = new ActivityHelper();
        CFHyperlinkBuilder result = instance.hyperlink(path);
        assertEquals(path, result.build().getPath());
    }

    /**
     * Test of linkbutton method, of class Helper.
     */
    @Test
    public void testLinkbutton() {
        System.out.println("linkbutton");
        String path = "TestPath";
        ActivityHelper instance = new ActivityHelper();
        CFLinkButtonBuilder result = instance.linkbutton(path);
        assertEquals(path, result.build().getPath());
    }

    /**
     * Test of submit method, of class Helper.
     */
    @Test
    public void testSubmit() {
        System.out.println("submit");
        String text = "TestText";
        ActivityHelper instance = new ActivityHelper();
        CFSubmitButtonBuilder result = instance.submit(text);
        //TODO:未実装？
        assertEquals(text, result.build());
    }

    /**
     * Test of hbox method, of class Helper.
     */
    @Test
    public void testHbox_BuilderArr() {
        System.out.println("hbox");
        Builder[] builders = new Builder[] { };
        ActivityHelper instance = new ActivityHelper();
        HBoxBuilder result = instance.hbox(builders);
        //TODO:assertEquals(expResult, result.build());
    }

    /**
     * Test of hbox method, of class Helper.
     */
    @Test
    public void testHbox_NodeArr() {
        System.out.println("hbox");
        Node[] children = null;
        ActivityHelper instance = new ActivityHelper();
        HBoxBuilder expResult = null;
        HBoxBuilder result = instance.hbox(children);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
