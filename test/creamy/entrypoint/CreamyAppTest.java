package creamy.entrypoint;

import creamy.entrypoint.CreamyApp;
import creamy.property.CreamyProps;
import javafx.stage.Stage;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * CreamyAppのテストクラス.
 * 
 * @author ahayama
 */
public class CreamyAppTest {
    
    public CreamyAppTest() {
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
    public void testSomeMethod() {
        System.out.println("constructor");
        
        CreamyAppSample sample = new CreamyAppSample();
        // configファイルが読み込めたかどうかの確認
        assertNotNull(CreamyProps.getValue("main.fxml"));
        assertEquals("Main.vm.fxml", CreamyProps.getValue("main.fxml"));
    }
    // テスト用サンプルアプリ
    public class CreamyAppSample extends CreamyApp {

        @Override
        public void start(Stage arg0) throws Exception {
            
        }
    
    }
}
