/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package views.testanimation;

import creamy.activity.AvailableActivity;
import creamy.animation.CFAnimation;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

/**
 *
 * @author miyabetaiji
 */
public class TestAnimation extends AvailableActivity {
    
    @FXML private VBox testArea;
    @FXML private Label testLabel;
    @FXML private Button effectPulsate;
    @FXML private ToggleGroup nodeSelect;
    @FXML private RadioButton radioVbox;
    @FXML private RadioButton radioLabel;
    
    private Node testNode;
    
    @Override
    public void initialize() {
        testNode = testArea;
        nodeSelect.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {
                if (t1 == radioVbox) {
                    testNode = testArea;
                } else {
                    testNode = testLabel;
                }
            }
        });
    }
    
    @FXML private void effectPulsate(ActionEvent event) {
        animate(testNode).effectPulsate(1500, 3);
    }

    @FXML private void showPulsate(ActionEvent event) {
        animate(testNode).showPulsate(1500, 3);
    }
    
    @FXML private void hidePulsate(ActionEvent event) {
        animate(testNode).hidePulsate(1500, 3);
    }
    
    @FXML private void togglePulsate(ActionEvent event) {
        animate(testNode).togglePulsate(1500, 3);
    }
    
    @FXML private void hideBlind(ActionEvent event) {
        animate(testNode).hideBlind(1000);
    }
    
    @FXML private void showBlind(ActionEvent event) {
        animate(testNode).showBlind(1000);
    }
    
    @FXML private void toggleBlind(ActionEvent evnet) {
        animate(testNode).toggleBlind(500);
    }
 
    @FXML private void effectFade(ActionEvent evnet) {
        animate(testNode).effectFade(1000);
    }
 
    @FXML private void showFade(ActionEvent evnet) {
        animate(testNode).showFade(1000);
    }
    
    @FXML private void hideFade(ActionEvent evnet) {
        animate(testNode).hideFade(1000);
    }

    @FXML private void toggleFade(ActionEvent evnet) {
        animate(testNode).toggleFade(1000);
    }

    @FXML private void effectScale(ActionEvent evnet) {
        animate(testNode).effectScale(1000, 0.7);
    }
    
    @FXML private void showScale(ActionEvent evnet) {
        animate(testNode).showScale(1000);
    }
    
    @FXML private void hideScale(ActionEvent evnet) {
        animate(testNode).hideScale(1000);
    }

    @FXML private void toggleScale(ActionEvent evnet) {
        animate(testNode).toggleScale(1000);
    }

    @FXML private void showSlide(ActionEvent evnet) {
        animate(testNode).showSlide(1000);
    }
    
    @FXML private void hideSlide(ActionEvent evnet) {
        animate(testNode).hideSlide(1000);
    }

    @FXML private void toggleSlide(ActionEvent evnet) {
        animate(testNode).toggleSlide(1000);
    }
}
