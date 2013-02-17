/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package views.application;

import creamy.activity.AvailableActivity;
import creamy.annotation.Template;
import creamy.mvc.Request;
import java.text.SimpleDateFormat;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.layout.StackPane;
import models.Company;

/**
 *
 * @author TadaoIsobe
 */
@Template(Main.class)
public class Create extends AvailableActivity {

    @FXML private StackPane createForm;
    
    // date formatter
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
    
    //public String title() { return "Add a Computer"; }

    @Override
    public void initialize() {
        //super.initialize(parentActivity, null);
        createForm.getChildren().add(
            gridForm("/Application/save").method(Request.POST).styleClass("grid-form")
                .row(   label("Computer Name:"),
                        text("name"),
                        label("Required").styleClass(this.validationResult.hasError() ? "err-text" : "guide-text"))
                .row(   label("Introduced Date :"),
                        text("introduced").format(format),
                        label("Date (" + DATE_FORMAT + ")").styleClass("guide-text"))
                .row(   label("Discontinued Date :"),
                        text("discontinued").format(format),
                        label("Date (" + DATE_FORMAT + ")").styleClass("guide-text")    )
                .row(   label("Company :"),
                        choice("company.id").items(Company.options()).prefWidth(275))
                .row(   hbox(submit("Create this computer").styleClass("btn-primary"),
                             label(" or "),
                             linkbutton("/Application/index").text("Cancel").styleClass("btn"))
                        .padding(new Insets(15,0,15,140)).spacing(5)
                        .styleClass("actions")
                        ,3  )
                );
    }
}
