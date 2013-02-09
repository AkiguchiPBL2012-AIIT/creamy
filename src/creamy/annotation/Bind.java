package creamy.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * コントローラメソッドの引数にBindするリクエストパラメータを指定する。
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Bind {
    /**
     * Bind対象のリクエストパラメータのKey
     */
    String value();
}
