/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package creamy.activity;

import creamy.animation.CFAnimation;
import creamy.scene.control.*;
import creamy.scene.layout.CFGridForm;
import creamy.scene.layout.CFHForm;
import creamy.scene.layout.CFVForm;
import java.lang.reflect.Field;
import java.util.Map;
import javafx.scene.Node;
import javafx.scene.control.LabelBuilder;
import javafx.scene.layout.HBoxBuilder;
import javafx.util.Builder;

/**
 *
 * @author ATakahashi
 */
public class AvailableActivity extends Activity implements Available {
    @Override
    public void initialize() {}
    
    private ActivityHelper helper = new ActivityHelper();
    
    public CFGridForm gridForm(String path) {
        return helper.gridForm(path);
    }
    
    public CFHForm hform(String path) {
        return helper.hform(path);
    }
    
    public CFVForm vform(String path) {
        return helper.vform(path);
    }
    
    public LabelBuilder<? extends LabelBuilder> label(String text) {
        return helper.label(text);
    }
    
    public CFTextFieldBuilder<? extends CFTextFieldBuilder> text(String name) {
        return helper.text(name);
    }
    
    public CFChoiceBoxBuilder<?, ? extends CFChoiceBoxBuilder> choice(String name) {
        return helper.choice(name);
    }
    
    public CFButtonBuilder<? extends CFButtonBuilder> button(String name) {
        return helper.button(name);
    }
    
    public CFHyperlinkBuilder<? extends CFHyperlinkBuilder> hyperlink(String path) {
        return helper.hyperlink(path);
    }
    
    public CFLinkButtonBuilder<? extends CFLinkButtonBuilder> linkbutton(String path) {
        return helper.linkbutton(path);
    }
    
    public CFSubmitButtonBuilder<? extends CFSubmitButtonBuilder> submit(String text) {
        return helper.submit(text);
    }
    
    public HBoxBuilder<?> hbox(Builder... builders) {
        return helper.hbox(builders);
    }

    public HBoxBuilder<?> hbox(Node... children) {
        return helper.hbox(children);
    }
    
    protected CFAnimation animate(Node node) {
        return new CFAnimation(node);
    }
    
    protected void replaceStyleClass(Node node, String styleClass) {
        node.getStyleClass().clear();
        node.getStyleClass().add(styleClass);
    }
}
