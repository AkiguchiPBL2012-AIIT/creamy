/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package creamy.activity;

import creamy.mvc.Controller;
import creamy.property.CreamyProps;
import creamy.validation.ValidationResult;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.layout.VBox;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author ATakahashi
 */
public class ActivityFactoryTest {
    
    public ActivityFactoryTest() {
    }

    @Before
    public void setUp() {
        CreamyProps.loadProperties();
    }
    
    /**
     * Test of getInstance method, of class ActivityFactory.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        ActivityFactory expResult = ActivityFactory.getInstance();
        ActivityFactory result = ActivityFactory.getInstance();
        //参照先が同じか
        assert(expResult == result);
        //Nullではないこと
        assertNotNull(result);
    }

    public class ActivityFactoryTest_TestActivity extends Activity {

        public String title() { return "TestActivity"; }

        @Override
        public void initialize() {}
    }
    
    public class ActivityFactoryTest_TestClass {
        private String param1 = "value1";
        private String param2 = "value2";
        private String param3 = "value3";
    }

    /**
     * Test of createActivity method, of class ActivityFactory.
     * Templateありの場合のテスト
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testCreateActivity_Class_Controller_Template() {
        System.out.println("createActivity...use Template");
        
        // Activityインスタンスを作成
        Class<? extends Activity> activityClass = null;
        try {
            activityClass = (Class<? extends Activity>) Class.forName("creamy.activity.TestCreate_Template");
        } catch (ClassNotFoundException ex) {
            fail("ClassNotFoundException:" + ex.getMessage());
        }
        // Controllerインスタンスを作成
        Controller controller = null;
        try {
            Class<?> clazz = Class.forName("controllers.Application");
            controller = (Controller) clazz.getConstructor().newInstance();
        } catch (ClassNotFoundException ex) {
            fail("ClassNotFoundException:" + ex.getMessage());
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException |
                InvocationTargetException | NoSuchMethodException | SecurityException ex) {
            fail("InstantiationException:" + ex.getMessage());
        }
        // ActivityFactoryインスタンスを作成
        ActivityFactory instance = ActivityFactory.getInstance();
        Activity result = instance.createActivity(activityClass, controller, ValidationResult.getEmptyResult());
        
        // TestCreateクラスはMainをTemplateとして使うので、タイトルに「Creamy」が付く
        assertEquals("Creamy:Test Create Activity", result.getTitle());
    }

    /**
     * Test of createActivity method, of class ActivityFactory.
     * Templateなしの場合のテスト
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testCreateActivity_Class_Controller() {
        System.out.println("createActivity...not use Template");
        
        // Activityインスタンスを作成
        Class<? extends Activity> activityClass = null;
        try {
            activityClass = (Class<? extends Activity>) Class.forName("creamy.activity.TestCreate");
        } catch (ClassNotFoundException ex) {
            fail("ClassNotFoundException:" + ex.getMessage());
        }
        // Controllerインスタンスを作成
        Controller controller = null;
        try {
            Class<?> clazz = Class.forName("controllers.Application");
            controller = (Controller) clazz.getConstructor().newInstance();
        } catch (ClassNotFoundException ex) {
            fail("ClassNotFoundException:" + ex.getMessage());
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException |
                InvocationTargetException | NoSuchMethodException | SecurityException ex) {
            fail("InstantiationException:" + ex.getMessage());
        }
        // ActivityFactoryインスタンスを作成
        ActivityFactory instance = ActivityFactory.getInstance();
        Activity result = instance.createActivity(activityClass, controller, ValidationResult.getEmptyResult());
        
        // TestCreateクラスはMainをTemplateとして使うので、タイトルに'Creamy:'が付く
        assertEquals("Test Create Activity", result.getTitle());
    }

    /**
     * Test of createActivity method, of class ActivityFactory.
     */
    @Test
    public void testCreateActivity_Class_Activity() {
        System.out.println("createActivity ... as Child");
        // Setup
        Parent parent = new Parent();
        Map<String,Object> takeoverParams = new HashMap<String,Object>() {{ put("field2", "field2"); }};
        ActivityFactory factory = ActivityFactory.getInstance();
        Child child = (Child)factory.createActivity(Child.class, 
                                                    parent, 
                                                    new HashMap<Field,Object>(), 
                                                    takeoverParams,
                                                    ValidationResult.getEmptyResult());
        // Assertion
        // from parent fields
        assertEquals(parent.getField0(), child.getField0());
        assertEquals(parent.getField1(), child.getField1());
        // from takeover values
        assertEquals("field2", child.getField2());
        // initialize
        assertEquals("field3", child.getField3());
        // Node tree
        assertTrue(child.getScene() instanceof VBox);
        assertEquals("childTest", child.getScene().getId());
    }    
}
