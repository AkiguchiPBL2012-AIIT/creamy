package creamy.scene.control;

import javafx.scene.control.HyperlinkBuilder;

/**
 *
 * @author miyabetaiji
 */
public class CFHyperlinkBuilder<B extends CFHyperlinkBuilder<B>> extends HyperlinkBuilder<B> {

    private String path;
    
    protected CFHyperlinkBuilder() {}
        
    public static CFHyperlinkBuilder<?> create() {
        return new CFHyperlinkBuilder();
    }

    public CFHyperlinkBuilder<B> path(String path) {
        this.path = path;
        return this;
    }
    
    public void applyTo(CFHyperlink hyperlink) {
        super.applyTo(hyperlink);
        hyperlink.setPath(path);
    }
    
    @Override
    public CFHyperlink build() {
        CFHyperlink hyperlink = new CFHyperlink();
        applyTo(hyperlink);
        return hyperlink;
    }
}
