/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package creamy.utils;

import creamy.browser.BrowserContent;
import java.lang.reflect.Field;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;

/**
 *
 * @author miyabetaiji
 */
public class FinderUtil {
    /**
     * Nodeツリーを全探索して最初に見つかったNodeを返す
     * @param <T>
     * @param root
     * @param target
     * @return T 見つかったノード
     */
    @SuppressWarnings("unchecked")
    public static <T extends Node> T lookup(Node root, Class<T> target) {
        Set<Node> allNodes = root.lookupAll("*");
        // クラスをチェック
        for (Node node : allNodes) {
            if (target.isAssignableFrom(node.getClass()))
                return (T)node;
        }
        return null;
    }
    
    /**
     * Nodeツリーを全探索し、見つかったノードのSetを返す
     * @param node 探索対象のnode
     * @param target 探索するNodeのClass
     * @return 見つかったノードのSet
     */
    @SuppressWarnings("unchecked")
    public static <T extends Node> Set<T> lookupAll(Node root, Class<T> target) {
        Set<T> founds = new HashSet<T>();
        Set<Node> allNodes = root.lookupAll("*");
        // クラスをチェック
        for (Node node : allNodes) {
            if (target.isAssignableFrom(node.getClass()))
                founds.add((T)node);
            if (node instanceof TableView)
                founds.addAll(lookupAllInTableView((TableView)node, target));
            if (node instanceof ScrollPane) {
                Node nested = ((ScrollPane)node).getContent();
                founds.addAll(lookupAll(nested, target));
            }
        }
        return founds;
    }
    
    /**
     * TableView内の要素を探索し、見つかったノードのSetを返す
     * @param <T>
     * @param tableView
     * @param target
     * @return 見つかったノードのSet
     */
    @SuppressWarnings("unchecked")
    public static <T extends Node> Set<T> lookupAllInTableView(TableView tableView, Class<T> target) {
        Set<T> founds = new HashSet<T>();
        ObservableList items = tableView.getItems();
        if (items.isEmpty()) return founds;
        Class<?> clazz = items.get(0).getClass();
        List<Field> targetFields = new ArrayList<Field>();
        List<Field> remainFields = new ArrayList<Field>();
        for (Field field : clazz.getDeclaredFields()) {
            if (target.isAssignableFrom(field.getType()))
                targetFields.add(field);
            else 
                if (Node.class.isAssignableFrom(field.getType()))
                    remainFields.add(field);
        }
        for (Field field : targetFields) {
            field.setAccessible(true);
            for (Object item : items) {
                try {
                    founds.add((T)field.get(item));
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    Logger.getLogger(BrowserContent.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        for (Field field : remainFields) {
            field.setAccessible(true);
            for (Object item : items) {
                try {
                    founds.addAll(lookupAll((Node)field.get(item), target));
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    Logger.getLogger(BrowserContent.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return founds;
    }   
}
