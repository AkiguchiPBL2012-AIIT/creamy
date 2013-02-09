package creamy.activity;

import creamy.animation.CFAnimation;
import creamy.scene.control.*;
import creamy.scene.layout.CFGridForm;
import creamy.scene.layout.CFHForm;
import creamy.scene.layout.CFVForm;
import javafx.scene.Node;
import javafx.scene.control.LabelBuilder;
import javafx.scene.layout.HBoxBuilder;
import javafx.util.Builder;

/**
 * ヘルパーメソッドを実装して拡張したActivity
 * 
 * @author ATakahashi
 */
public class AvailableActivity extends Activity implements Available {
    /**
     * 初期化メソッド。ユーザアクティビティはこのメソッドをオーバライドして使用する
     */
    @Override
    public void cfinitialize() {}
    
    /**
     * ActivityHelper。リクエストを送信する際に利用される
     */
    private ActivityHelper helper = new ActivityHelper();
    
    
    @Override
    public CFGridForm gridForm(String path) {
        return helper.gridForm(path);
    }
    
    @Override
    public CFHForm hform(String path) {
        return helper.hform(path);
    }
    
    public CFVForm vform(String path) {
        return helper.vform(path);
    }
    
    @Override
    public LabelBuilder<? extends LabelBuilder> label(String text) {
        return helper.label(text);
    }
    
    @Override
    public CFTextFieldBuilder<? extends CFTextFieldBuilder> text(String name) {
        return helper.text(name);
    }
    
    @Override
    public CFChoiceBoxBuilder<?, ? extends CFChoiceBoxBuilder> choice(String name) {
        return helper.choice(name);
    }
    
    @Override
    public CFButtonBuilder<? extends CFButtonBuilder> button(String name) {
        return helper.button(name);
    }
    
    @Override
    public CFHyperlinkBuilder<? extends CFHyperlinkBuilder> hyperlink(String path) {
        return helper.hyperlink(path);
    }
    
    @Override
    public CFLinkButtonBuilder<? extends CFLinkButtonBuilder> linkbutton(String path) {
        return helper.linkbutton(path);
    }
    
    @Override
    public CFSubmitButtonBuilder<? extends CFSubmitButtonBuilder> submit(String text) {
        return helper.submit(text);
    }
    
    @Override
    public HBoxBuilder<?> hbox(Builder... builders) {
        return helper.hbox(builders);
    }

    @Override
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
