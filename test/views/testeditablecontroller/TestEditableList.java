package views.testeditablecontroller;

import creamy.activity.Activity;
import creamy.activity.AvailableActivity;
import creamy.activity.requestor.CallBack;
import creamy.mvc.Status;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import models.Computer;

/**
 *
 * @author miyabetaiji
 */
public class TestEditableList extends AvailableActivity {
    @FXML private VBox listPane;
    
    @Override
    public void initialize() {
        //createList();
    }
    
    public void removed(TestEditableItem item) {
        listPane.getChildren().remove(item.getScene());
    }
    /*
    private java.util.List<Computer> computers;
    
    private void createList() {
        listPane.getChildren().clear();
        for (Computer computer : computers) {
            String path = "/TestEditableController/testEditableItem/" + computer.getId();
            requestActivity(path)
                    .onSuccess(new CallBack<Activity>() {
                        @Override
                        public void call(Activity activity, Status status) {
                            listPane.getChildren().add(activity.getScene());
                        }
                    })
                    .execute();
                    
        }
    }
    * 
    */
}
