package creamy.scene.layout;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.Pane;

/**
 * 子FXMLをレンダリングする際に使用するPane.
 * 
 * @author miyabetaiji
 */
public class ChildPane extends Pane {
    private StringProperty child = new SimpleStringProperty();;
    
    public ChildPane() {}
    
    /**
     * 子要素名をセットする.
     * @param child 子要素名
     */
    public void setChild(String child) { this.child.set(child); }
    
    /**
     * 子要素名を返す.
     * @return 子要素名
     */
    public String getChild() { return this.child.get(); }
}

