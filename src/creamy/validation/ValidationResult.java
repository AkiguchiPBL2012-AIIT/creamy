/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package creamy.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;

/**
 *
 * @author ATakahashi
 */
public class ValidationResult {
    Set<ConstraintViolation<Object>> violations;
    
    public ValidationResult(Set<ConstraintViolation<Object>> violations) {
        this.violations = violations;
    }
    
    public List<ConstraintViolation<Object>> getViolations() {
        ArrayList<ConstraintViolation<Object>> list = new ArrayList<>();
        list.addAll(this.violations);
        return list;
    }
    
    public boolean hasError() {
        return violations == null || violations.isEmpty() ? false : true;
    }
    
    public static ValidationResult getEmptyResult() {
        return new ValidationResult(null);
    }
}
