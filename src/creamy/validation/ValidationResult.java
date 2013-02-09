
package creamy.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;

/**
 * Validationによる検証結果を格納するクラス
 */
public class ValidationResult {
    private Set<ConstraintViolation<Object>> violations;
    
    /**
     * 検証結果クラスを生成する
     * @param violations 検証結果
     */
    public ValidationResult(Set<ConstraintViolation<Object>> violations) {
        this.violations = violations;
    }
    
    /**
     * 検証結果のリストを取得する
     * @return 検証結果のリストを取得する 
     */
    public List<ConstraintViolation<Object>> getViolations() {
        ArrayList<ConstraintViolation<Object>> list = new ArrayList<>();
        list.addAll(this.violations);
        return list;
    }
    
    /**
     * 検証によるエラーがあるかどうか
     * @return エラーがあればtrue
     */
    public boolean hasError() {
        return violations == null || violations.isEmpty() ? false : true;
    }
    
    /**
     * 空の検証結果を取得する
     * @return 空の検証結果
     */
    public static ValidationResult getEmptyResult() {
        return new ValidationResult(null);
    }
}
