/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package creamy.activity;

import creamy.property.CreamyProps;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author ATakahashi
 */
public class FXMLGeneratorTest {
    
    public FXMLGeneratorTest() {
    }

    @Before
    public void setup() {
        CreamyProps.loadProperties();
    }
    
    /**
     * Test of generate method, of class FXMLGenerator.
     */
    @Test
    public void testGenerate() {
        System.out.println("generate");
        // Setup
        FXMLGenerator generator = new FXMLGenerator();
        URL url = getClass().getResource("TestFXML.vm.fxml");
        RenderParameter renderParam = new RenderParameter();
        Map<String,Object> takeoverParam = new HashMap<String,Object>() {{ put("test", "TESTING"); }};
        String result =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                "<AnchorPane>\n" +
                "<ChildPane child=\"CHILD\" fx:id=\"C000000000\" />\n" +
                "<ChildPane child=\"CHILDS\" fx:id=\"C000000001\" />\n" +
                "TESTING\n" +
                "</AnchorPane>\n";
        String out = generator.generate(url, new HashMap<Field,Object>(), takeoverParam, renderParam);
        // Assertion
        String[] results = result.split("\n");
        String[] outs = out.split("\n");
        // Check FXML
        assertEquals(results.length, outs.length);
        for (int i = 0; i < results.length; i++) {
            assertEquals(results[i].trim(), outs[i].trim());
        }
        // Check data
        assertEquals("value", renderParam.getParams("C000000000").get("key"));
        for (int i = 0; i < 2; i++) {
            assertEquals("value" + i, renderParam.getParams("C000000001").get("key" + i));
        }
    }
    
    @Test
    public void testUncommentVTL() {
        // Setup
        FXMLGenerator generator = new FXMLGenerator();
        URL url = getClass().getResource("VTLUncomment.vm.fxml");
        RenderParameter renderParam = new RenderParameter();
        Map<String,Object> takeoverParam = new HashMap<String,Object>() {{ put("test", "TESTING"); }};
        String result =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                "<AnchorPane>\n" +
                "<ChildPane child=\"CHILD\" fx:id=\"C000000000\" />\n" +
                "\n" +
                "<ChildPane child=\"CHILDS\" fx:id=\"C000000001\" />\n" +
                "TESTING\n" +
                "\n" +
                "<VBox>\n" +
                "  <HBox>\n" +
                "    NESTED\n" +
                "\n" +
                "    NESTED2\n" +
                "\n" +
                "  </HBox>\n" +
                "</VBox>\n" +
                "<!-- COMMENT -->\n" + 
                "</AnchorPane>\n";
        String out = generator.generate(url, new HashMap<Field,Object>(), takeoverParam, renderParam);
        // Assertion
        String[] results = result.split("\n");
        String[] outs = out.split("\n");
        // Check FXML
        System.out.println(out);
        assertEquals(results.length, outs.length);
        for (int i = 0; i < results.length; i++) {
            assertEquals(results[i].trim(), outs[i].trim());
        }
        // Check data
        assertEquals("value", renderParam.getParams("C000000000").get("key"));
        for (int i = 0; i < 2; i++) {
            assertEquals("value" + i, renderParam.getParams("C000000001").get("key" + i));
        }
    }
}
