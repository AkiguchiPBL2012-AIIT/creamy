package views.testcontroller;

import creamy.activity.Activity;
import creamy.activity.AvailableActivity;
import creamy.mvc.Request;
import creamy.scene.control.CFHyperlink;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class TestActivity extends AvailableActivity{
    @FXML CFHyperlink linkTest;
    @FXML StackPane testForm;
    
    private String testStr;
    private Integer testInt;
    private Object testObj;
    
    private String message;
    
    public void initialize() {
        // for link test
        linkTest.setPath("/TestController/testActivity/Hello world/0.5555");
                
        // for form test
        testForm.getChildren().add(
            gridForm("path").gaps(10, 10).padding(20).method(Request.POST)
                .row(label("Add a Computer").font(new Font("Arial", 30)), 2)
                .row()
                .row(label("Computer Name:"), 
                     text("computer"), 
                     label("Required").textFill(Color.DARKGRAY))
                .row(label("Introduced Date :"), 
                     text("introduced_date"), 
                     label("Date (yyyy-MM-dd)").textFill(Color.DARKGRAY))
                .row(label("Discontinued Date :"), 
                     text("discontinued_date"), 
                     label("Date (yyyy-MM-dd)").textFill(Color.DARKGRAY))
                .row(label("Company :"), 
                     choice("company").items("aaa", "bbb", "ccc"))
                .row()
                .row(hbox(
                         submit("submit").build()
                        ,label(" or ").build()
                        ,linkbutton("/test5").build()
                     ))
        );
    }

    public void mediate() {
        System.out.println("mediating...");
    }

    public String title() {
        return "Parent";
    }
}
