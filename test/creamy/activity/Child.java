package creamy.activity;

/**
 *
 * @author miyabetaiji
 */
public class Child extends Activity {
    private String field0 = "field0";
    private String field1 = "field1";
    private String field2;
    private String field3;
    
    @Override
    public void initialize() {
        field3 = "field3";
    }

    public String getField0() {
        return field0;
    }

    public String getField1() {
        return field1;
    }

    public String getField2() {
        return field2;
    }
    
    public String getField3() {
        return field3;
    }    
}
