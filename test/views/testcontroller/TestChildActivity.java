package views.testcontroller;

import creamy.activity.Activity;
import creamy.activity.AvailableActivity;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class TestChildActivity extends AvailableActivity{    
    private String testStr;
    private Integer testInt;
    private Object testObj;
    
    private String message;
        
    @FXML TextField dataCheck;
    
    public void initialize() {
        /*
        if (testStr == null) testStr = "he";
        if (testInt == null) testInt = 20;
        dataCheck.setText(testStr + " is " + testInt.toString() + "years old.");
        if (testObj != null) System.out.println("Object copy ok");
        */
        dataCheck.setText(message);
    }
    
    @FXML
    public void checkParent(ActionEvent event) {
        ((TestActivity)getParent()).mediate();
    }
}
