/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package creamy.validation;


import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author ATakahashi
 */
public class ValidationTest2 {
    
    public ValidationTest2() {
    }
    
    
    /**
     * 手動での検証テスト
     */
    @Test
    public void manualValidTest() {
        TestBean bean = new TestBean();
        ValidationResult result;
        
        //両方エラー
        bean.setNotNull(null);
        bean.setNumOnly("string");
        result = Validator.valid(bean);
        assert(result.hasError());
        assert(2 == result.getViolations().size());
        
        // 数字のみの入力についてエラー
        bean.setNotNull("NotNull");
        bean.setNumOnly("string");
        result = Validator.valid(bean);
        assert(result.hasError());
        assert(1 == result.getViolations().size());
        assertEquals("string", (String)result.getViolations().get(0).getInvalidValue());
        
        // NotNullの入力についてエラー
        bean.setNotNull(null);
        bean.setNumOnly("1234567890");
        result = Validator.valid(bean);
        assert(result.hasError());
        assert(1 == result.getViolations().size());
        assertEquals(null, (String)result.getViolations().get(0).getInvalidValue());
    }
    
    @Test
    public void bindValidTest() {
        
        
    }
    
    
}
