package creamy.scene.control;

import javafx.scene.control.ButtonBuilder;

/**
 *
 * @author miyabetaiji
 */
public class CFSubmitButtonBuilder<B extends CFSubmitButtonBuilder<B>> extends ButtonBuilder<B> {
    
    protected CFSubmitButtonBuilder() {}
    
    public static CFSubmitButtonBuilder<?> create() {
        return new CFSubmitButtonBuilder();
    }

    public void applyTo(CFButton textfield) {
        super.applyTo(textfield);
    }
    
    @Override
    public CFSubmitButton build() {
        CFSubmitButton button = new CFSubmitButton();
        applyTo(button);
        return button;
    }
}
