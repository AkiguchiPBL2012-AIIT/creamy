package creamy.activity;

import creamy.scene.control.*;
import creamy.scene.layout.CFGridForm;
import creamy.scene.layout.CFHForm;
import creamy.scene.layout.CFVForm;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.LabelBuilder;
import javafx.scene.layout.HBoxBuilder;
import javafx.util.Builder;

/**
 * Activity要素を記述するためのヘルパークラス.
 * メソッドチェーンで記述できるように、レイアウトクラスは自身オブジェクトを、コントロールクラスはBuilderクラスを返す。
 * @author ahayama
 */
public class ActivityHelper {
    /**
     * CFGridFormオブジェクトを生成する.
     * @param path パス
     * @return 生成したCFGridFormオブジェクト
     */
    public CFGridForm gridForm(String path) {
        CFGridForm gForm = new CFGridForm(path);
        return gForm;
    }
    /**
     * CFHFormオブジェクトを生成する.
     * @param path パス
     * @return 生成したCFHFormオブジェクト
     */
    public CFHForm hform(String path) {
        CFHForm hForm = new CFHForm(path);
        return hForm;
    }
    /**
     * CFVFormオブジェクトを生成する.
     * @param path パス
     * @return 生成したCFVFormオブジェクト
     */
    public CFVForm vform(String path) {
        CFVForm vForm = new CFVForm(path);
        return vForm;
    }
    /**
     * Labelオブジェクトを生成してラベルテキストをセットする.
     * @param text ラベルテキスト
     * @return LabelBuilderオブジェクト
     */
    public LabelBuilder<? extends LabelBuilder> label(String text) {
        return LabelBuilder.create().text(text);
    }
    /**
     * CFTextFieldBuilderオブジェクトを生成してname属性値をセットする.
     * @param name name属性値
     * @return CFTextFieldBuilderオブジェクト
     */
    public CFTextFieldBuilder<? extends CFTextFieldBuilder> text(String name) {
        return CFTextFieldBuilder.create().name(name);
    }
    /**
     * CFChoiceBoxBuilderオブジェクトを生成してname属性値をセットする.
     * @param name name属性値
     * @return CFChoiceBoxBuilderオブジェクト
     */
    public CFChoiceBoxBuilder<?, ? extends CFChoiceBoxBuilder> choice(String name) {
        return CFChoiceBoxBuilder.create().name(name);
    }
    /**
     * CFButtonBuilderオブジェクトを生成してname属性値をセットする.
     * @param name name属性値
     * @return CFButtonBuilderオブジェクト
     */
    public CFButtonBuilder<? extends CFButtonBuilder> button(String name) {
        return CFButtonBuilder.create().name(name);
    }
    /**
     * CFHyperlinkBuilderオブジェクトを生成してパスをセットする.
     * @param path パス
     * @return CFHyperlinkBuilderオブジェクト
     */
    public CFHyperlinkBuilder<? extends CFHyperlinkBuilder> hyperlink(String path) {
        return CFHyperlinkBuilder.create().path(path);
    }
    /**
     * CFLinkButtonBuilderオブジェクトを生成してパスをセットする.
     * @param path パス
     * @return CFLinkButtonBuilderオブジェクト
     */
    public CFLinkButtonBuilder<? extends CFLinkButtonBuilder> linkbutton(String path) {
        return CFLinkButtonBuilder.create().path(path);
    }    
    /**
     * CFSubmitButtonBuilderオブジェクトを生成してボタンタイトルをセットする.
     * @param text ボタンタイトル
     * @return CFSubmitButtonBuilderオブジェクト
     */
    public CFSubmitButtonBuilder<? extends CFSubmitButtonBuilder> submit(String text) {
        return CFSubmitButtonBuilder.create().text(text);
    }
    /**
     * HBoxBuilderオブジェクトを生成し、子要素を追加する.
     * @param builders 追加するNodeを生成するBuilder
     * @return 生成したHBoxBuilderオブジェクト
     */
    public HBoxBuilder<?> hbox(Builder... builders) {
        HBoxBuilder<?> builder = HBoxBuilder.create();
        List<Node> children = new ArrayList<Node>();
        for (Builder b : builders) children.add((Node)b.build());
        return builder.children(children);
    }
    /**
     * HBoxBuilderオブジェクトを生成し、子要素を追加する.
     * @param children 追加するNode
     * @return 生成したHBoxBuilderオブジェクト
     */
    public HBoxBuilder<?> hbox(Node... children) {
        HBoxBuilder<?> builder = HBoxBuilder.create();
        builder.children(children);
        return builder;
    }
}
