package creamy.validation;

/**
 * 検証失敗通知用のExceptionクラス
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
