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
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import models.Company;
import models.Computer;

/**
 *
 * @author TadaoIsobe
 */
@Template(Main.class)
public class Edit extends AvailableActivity {
    
    @FXML StackPane editForm;
    @FXML StackPane deleteForm;

    // date formatter
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);    
    
    //public String title() { return "Edit Computer"; }

    // data
    private Computer computer;
    
    public void initialize() {
        // edit form
        editForm.getChildren().add(
            gridForm("/Application/update/" + computer.getId()).method(Request.POST).styleClass("grid-form")
                .row(   label("Computer Name:"),
                        text("name").value(computer.getName()),
                        label("Required").styleClass("guide-text")  )
                .row(   label("Introduced Date :"),
                        text("introduced").format(format).value(computer.getIntroduced()),
                        label("Date (" + DATE_FORMAT + ")").styleClass("guide-text")    )
                .row(   label("Discontinued Date :"),
                        text("discontinued").format(format).value(computer.getDiscontinued()),
                        label("Date (" + DATE_FORMAT + ")").styleClass("guide-text")    )
                .row(   label("Company :"),
                        choice("company.id").items(Company.options()).prefWidth(275)
                               .value(computer.getCompany() != null ? computer.getCompany().getId() : null) )
                .row(   hbox(submit("Save this computer").styleClass("btn-primary"),
                             label(" or "),
                             linkbutton("/Application/index").text("Cancel").styleClass("btn"))
                        .padding(new Insets(15,0,15,140)).spacing(5)
                        .styleClass("actions")
                        ,3)
                );
        // delete from
        deleteForm.getChildren().add(
             hform("/Application/delete/" + computer.getId()).method(Request.POST).align(Pos.CENTER_RIGHT)
                .add(   submit("Delete this computer").styleClass("btn-danger") )
                );
    }
}
