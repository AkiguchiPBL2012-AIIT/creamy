package creamy.property;

import java.io.IOException;
import java.util.Properties;
import org.junit.*;

/**
 *
 * @author Ryusaburo Tanaka (Professor Akiguchi's PBL 2012, AIIT)
 */
public class PropertyUtilTest {
    
    public PropertyUtilTest() {
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

    @Test
    public void testGetPropertiesFromFile() throws IOException{
        System.out.println("getPropertiesFromFile");
        Properties prop = PropertyUtil.loadProperty("creamy.properties");
        Assert.assertEquals(prop.getProperty("main.fxml"), "Main.vm.fxml");
    }
}
