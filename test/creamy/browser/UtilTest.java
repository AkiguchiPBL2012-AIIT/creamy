/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package creamy.browser;

import creamy.utils.FinderUtil;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author miyabetaiji
 */
public class UtilTest {
    
    public UtilTest() {
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
     * Test of lookup method, of class Util.
     */
    @Test
    public void testLookup() {
        System.out.println("lookup");
        // Setup
        Button btnEx = new ButtonEx();
        Button btn = new Button();
        HBox root = HBoxBuilder.create()
                .children(
                    VBoxBuilder.create()
                        .children(
                            new TextField(),
                            btnEx,
                            btn
                            
                        )
                        .build()
                )
                .build();
        // Assertion
        Button res = FinderUtil.lookup(root, Button.class);
        assertEquals(btnEx, res);
    }

    /**
     * Test of lookupAll method, of class Util.
     */
    @Test
    public void testLookupAll() {
        System.out.println("lookupAll");
        // Setup
        Button btnEx = new ButtonEx();
        Button btn = new Button();
        HBox root = HBoxBuilder.create()
                .children(
                    VBoxBuilder.create()
                        .children(
                            new TextField(),
                            btnEx,
                            btn
                            
                        )
                        .build()
                )
                .build();
        // Assertion
        Set<Button> buttons = FinderUtil.lookupAll(root, Button.class);
        assertEquals(2, buttons.size());
        for (Button button : buttons) {
            if (button != btnEx)
                assertEquals(btn, button);
            else
                assertEquals(btnEx, button);
        }
    }

    /**
     * Test of lookupAllInTableView method, of class Util.
     */
    @Test
    public void testLookupAllInTableView() {
        System.out.println("lookupAll");
        // Setup
        TableView table = new TableView();
        TableColumn text = new TableColumn();
        text.setCellValueFactory(new PropertyValueFactory<TableItem,TextField>("text"));
        TableColumn button = new TableColumn();
        button.setCellValueFactory(new PropertyValueFactory<TableItem,ButtonEx>("button"));
        TableColumn pane = new TableColumn();
        pane.setCellValueFactory(new PropertyValueFactory<TableItem,StackPane>("pane"));
        table.getColumns().addAll(text, button, pane);
        TableItem item0, item1;
        ObservableList<TableItem> data = FXCollections.observableArrayList(
                item0 = new TableItem(),
                item1 = new TableItem()
                );
        table.setItems(data);
        // Assertion
        Set<Button> result = FinderUtil.lookupAllInTableView(table, Button.class);
        assertEquals(4, result.size());
        
    }
    
    public class ButtonEx extends Button {}
    
    public class TableItem {
        private TextField text = new TextField();
        private ButtonEx button = new ButtonEx();
        private StackPane pane = StackPaneBuilder.create()
                .children(new Button())
                .build();
        public TextField getText() { return text; }
        public void setText(TextField text) { this.text = text; }
        public ButtonEx getButton() { return button; }
        public void setButton(ButtonEx button) { this.button = button; }
        public StackPane getPane() { return pane; }
        public void setPane(StackPane pane) { this.pane = pane; }
    }
}
