/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package creamy.activity;

import creamy.scene.control.*;
import creamy.scene.layout.CFGridForm;
import creamy.scene.layout.CFHForm;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.LabelBuilder;
import javafx.scene.layout.HBoxBuilder;
import javafx.util.Builder;

/**
 *
 * @author ATakahashi
 */
public interface Available {

    public CFGridForm gridForm(String path);
    
    public CFHForm hform(String path);
    
    public LabelBuilder<? extends LabelBuilder> label(String text);
    
    public CFTextFieldBuilder<? extends CFTextFieldBuilder> text(String name);
    
    public CFChoiceBoxBuilder<?, ? extends CFChoiceBoxBuilder> choice(String name);
    
    public CFButtonBuilder<? extends CFButtonBuilder> button(String name);
    
    public CFHyperlinkBuilder<? extends CFHyperlinkBuilder> hyperlink(String path);
    
    public CFLinkButtonBuilder<? extends CFLinkButtonBuilder> linkbutton(String path);
    
    public CFSubmitButtonBuilder<? extends CFSubmitButtonBuilder> submit(String text);
    
    public HBoxBuilder<?> hbox(Builder... builders);

    public HBoxBuilder<?> hbox(Node... children);
}
