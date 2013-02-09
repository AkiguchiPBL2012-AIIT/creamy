package creamy.activity;

import creamy.activity.AvailableActivity;
import creamy.activity.AvailableActivity;
import creamy.annotation.Template;
import creamy.mvc.Request;
import java.text.SimpleDateFormat;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.layout.StackPane;
import models.Company;
import views.application.Main;

/**
 * ActivityFactoryクラスのテスト用.
 * ebeanアクセス部分を削除
 * 
 * @author TadaoIsobe(original)
 * @author ahayama(test)
 */
@Template(Main.class)
public class TestCreate_Template extends AvailableActivity {

    @FXML private StackPane createForm;
    
    // date formatter
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
    
    //public String title() { return "Add a Computer"; }

    public void initialize() {
        //super.initialize(parentActivity, null);
        createForm.getChildren().add(
            gridForm("/Application/save").method(Request.POST).styleClass("grid-form")
                .row(   label("Computer Name:"),
                        text("name"),
                        label("Required").styleClass("guide-text")  )
                .row(   label("Introduced Date :"),
                        text("introduced").format(format),
                        label("Date (" + DATE_FORMAT + ")").styleClass("guide-text")    )
                .row(   label("Discontinued Date :"),
                        text("discontinued").format(format),
                        label("Date (" + DATE_FORMAT + ")").styleClass("guide-text")    )
//                .row(   label("Company :"),
//                        choice("company.id").items(Company.options()).prefWidth(275))
                .row(   hbox(submit("Create this computer").styleClass("btn-primary"),
                             label(" or "),
                             linkbutton("/Application/index").text("Cancel").styleClass("btn"))
                        .padding(new Insets(15,0,15,140)).spacing(5)
                        .styleClass("actions")
                        ,3  )
                );
    }
}
