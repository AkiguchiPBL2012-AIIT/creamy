package creamy.scene.control;

import creamy.scene.layout.FormInput;
import javafx.scene.control.CheckBox;

public class CFCheckBox extends CheckBox implements FormInput {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String getCFName() {
        return getName();
    }

    @Override
    public void setCFName(String name) {
        setName(name);
    }

    @Override
    public Object getCFValue() {
        return isSelected();
    }

    @Override
    public void setCFValue(Object value) {
        if (!(value instanceof Boolean)) return;
        this.setSelected((Boolean)value);
    }
    
}
