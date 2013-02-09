package creamy.property;

import org.junit.*;

/**
 *
 * @author Ryusaburo Tanaka (Professor Akiguchi's PBL 2012, AIIT)
 */
public class CreamyPropsTest {
    
    public CreamyPropsTest() {
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
    public void testLoadProperties() {
        System.out.println("loadProperties");
        
    }
    
    @Test
    public void testGetValue() {
        System.out.println("getValue");
        Assert.assertEquals(CreamyProps.getValue("main.fxml"), "Main.vm.fxml");
    }
    
}
