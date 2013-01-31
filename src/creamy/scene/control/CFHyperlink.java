package creamy.scene.control;

import creamy.mvc.Request;
import java.util.Comparator;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;

public class CFHyperlink extends Hyperlink implements UnitRequest {
    private StringProperty method = new SimpleStringProperty();
    private StringProperty path = new SimpleStringProperty(Request.GET);
    
    public CFHyperlink() { super(); }

    public CFHyperlink(String text) { super(text); }
    
    public CFHyperlink(String text, Node graphic) { super(text, graphic); }
    
    public CFHyperlink(String text, String path) {
        this(text);
        this.path.set(path);
    }
    
    public CFHyperlink(String text, String path, Node graphic) {
        this(text, graphic);
        this.path.set(path);
    }
    
    @Override
    public String getMethod() { return method.get(); }
    
    @Override
    public String getPath() { return path.get(); }

    public void setPath(String path) { this.path.set(path); }
    
    public static class Comprator implements Comparator<CFHyperlink> {
        @Override
        public int compare(CFHyperlink c1, CFHyperlink c2) {
            return c1.getText().toLowerCase().compareTo(c2.getText().toLowerCase());
        }
    }
    
    public static class CaseComprator implements Comparator<CFHyperlink> {
        @Override
        public int compare(CFHyperlink c1, CFHyperlink c2) {
            return c1.getText().compareTo(c2.getText());
        }
    }
}
