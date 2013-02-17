package creamy.activity;

/**
 *
 * @author miyabetaiji
 */
public class Parent extends Activity {
    @Override
    public void initialize() {}

    public String getField0() {
        return field0;
    }

    public String getField1() {
        return field1;
    }
    
    private String field0 = "field0";
    private String field1 = "field1";
}
