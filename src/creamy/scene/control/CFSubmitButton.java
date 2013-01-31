package creamy.scene.control;

import javafx.scene.Node;
import javafx.scene.control.Button;

public class CFSubmitButton extends Button implements FormRequest {
    public CFSubmitButton() { super(); }

    public CFSubmitButton(String text) { super(text); }
    
    public CFSubmitButton(String text, Node graphic) { super(text, graphic); }
}
