/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package creamy.validation;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;

/**
 *
 * @author ATakahashi
 */ 
public class Validator {
    static private javax.validation.Validator validator;
    static {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
   
    /**
     * 検証を実行し、検証結果オブジェクト返す。
     * @param model
     * @return 検証結果オブジェクト
     */
    static public ValidationResult valid(Object model) {
        return new ValidationResult(validator.validate(model));
    }
}
