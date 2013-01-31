/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package creamy.validation;

/**
 *
 * @author ATakahashi
 */
public class ValidationException extends Exception {
    
    private ValidationResult validationResult;

    public ValidationException(ValidationResult validationResult) {
        this.validationResult = validationResult;
    }    
    
    public ValidationResult getValidationResult() {
        return this.validationResult;
    }
}
