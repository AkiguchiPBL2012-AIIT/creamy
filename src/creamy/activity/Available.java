/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package creamy.activity;

import creamy.scene.control.*;
import creamy.scene.layout.CFGridForm;
import creamy.scene.layout.CFHForm;
import javafx.scene.Node;
import javafx.scene.control.LabelBuilder;
import javafx.scene.layout.HBoxBuilder;
import javafx.util.Builder;

/**
 * ヘルパーメソッドの定義
 * 
 * @author ATakahashi
 */
public interface Available {
    /**
     * CFGridFormを生成する
     * @param path フォームのパス
     * @return 生成したCFGridForm
     */
    public CFGridForm gridForm(String path);
    /**
     * CFHFormを生成する
     * @param path フォームのパス
     * @return 生成したCFHForm
     */
    public CFHForm hform(String path);
    /**
     * LabelBuilderを生成する
     * @param text ラベルに表示するテキスト
     * @return 生成したLabelBuilder
     */
    public LabelBuilder<? extends LabelBuilder> label(String text);
    /**
     * CFTextFieldBuilderを生成する
     * @param name CFTextFieldにセットするname属性
     * @return 生成したCFTextFieldBuilder
     */
    public CFTextFieldBuilder<? extends CFTextFieldBuilder> text(String name);
    /**
     * CFChoiceBoxBuilderを生成する
     * @param name CFChoiceBoxにセットするname属性
     * @return 生成したCFChoiceBoxBuilder
     */
    public CFChoiceBoxBuilder<?, ? extends CFChoiceBoxBuilder> choice(String name);
    /**
     * CFButtonBuilderを生成する
     * @param name CFButtonにセットするname属性
     * @return 生成したCFButtonBuilder
     */
    public CFButtonBuilder<? extends CFButtonBuilder> button(String name);
    /**
     * CFHyperlinkBuilderを生成する
     * @param path CFHyperlinkにセットするpath属性
     * @return 生成したCFHyperlinkBuilderを生成する
     */
    public CFHyperlinkBuilder<? extends CFHyperlinkBuilder> hyperlink(String path);
    /**
     * CFLinkButtonBuilderを生成する
     * @param path CFLinkButtonにセットするpath属性
     * @return 生成したCFLinkButtonBuilder
     */
    public CFLinkButtonBuilder<? extends CFLinkButtonBuilder> linkbutton(String path);
    /**
     * CFSubmitButtonBuilderを生成する
     * @param text CFSubmitButtonのセットするtext(表示)
     * @return 生成したCFSubmitButtonBuilder
     */
    public CFSubmitButtonBuilder<? extends CFSubmitButtonBuilder> submit(String text);
    /**
     * HBoxBuilderを生成する
     * @param builders HBoxの子要素となるBuilder
     * @return 生成したHBoxBuilder
     */
    public HBoxBuilder<?> hbox(Builder... builders);
    /**
     * HBoxBuilderを生成する
     * @param children HBoxの子要素となるノード
     * @return 生成したHBoxBuilder
     */
    public HBoxBuilder<?> hbox(Node... children);
}
