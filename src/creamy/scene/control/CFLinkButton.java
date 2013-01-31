package creamy.scene.control;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;

public class CFLinkButton extends Button implements UnitRequest {
    private StringProperty method = new SimpleStringProperty();
    private StringProperty path = new SimpleStringProperty();
    
    public CFLinkButton() { super(); }

    public CFLinkButton(String text) { super(text); }
    
    public CFLinkButton(String text, Node graphic) { super(text, graphic); }
    
    public CFLinkButton(String text, String path) {
        this(text);
        this.path.set(path);
    }
    
    public CFLinkButton(String text, String path, Node graphic) {
        this(text, graphic);
        this.path.set(path);
    }
    
    @Override
    public String getMethod() { return method.get(); }
    
    @Override
    public String getPath() { return path.get(); }

    public void setPath(String path) { this.path.set(path); }
}
