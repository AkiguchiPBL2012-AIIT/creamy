package creamy.scene.control;

import javafx.scene.control.ButtonBuilder;

/**
 *
 * @author miyabetaiji
 */
public class CFLinkButtonBuilder<B extends CFLinkButtonBuilder<B>> extends ButtonBuilder<B> {

    private String path;
    
    protected CFLinkButtonBuilder() {}
        
    public static CFLinkButtonBuilder<?> create() {
        return new CFLinkButtonBuilder();
    }

    public CFLinkButtonBuilder<B> path(String path) {
        this.path = path;
        return this;
    }
    
    public void applyTo(CFLinkButton button) {
        super.applyTo(button);
        button.setPath(path);
    }
    
    @Override
    public CFLinkButton build() {
        CFLinkButton button = new CFLinkButton();
        applyTo(button);
        return button;
    }
}
