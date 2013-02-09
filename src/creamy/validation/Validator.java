
package creamy.validation;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;

/**
 * 検証実行用クラス
 */ 
public class Validator {
    static private javax.validation.Validator validator;
    static {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
   
    /**
     * 検証を実行し、検証結果オブジェクト返す。
     * @param model 検証対象オブジェクト
     * @return 検証結果オブジェクト
     */
    static public ValidationResult valid(Object model) {
        return new ValidationResult(validator.validate(model));
    }
}
