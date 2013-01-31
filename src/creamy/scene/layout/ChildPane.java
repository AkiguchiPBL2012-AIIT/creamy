package creamy.scene.layout;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.Pane;

/**
 * 子FXMLをレンダリングする際に使用するPane
 * 
 * @author miyabetaiji
 */
public class ChildPane extends Pane {
    private StringProperty child = new SimpleStringProperty();;
    
    public ChildPane() {}
    
    public void setChild(String child) { this.child.set(child); }
    
    public String getChild() { return this.child.get(); }
}

