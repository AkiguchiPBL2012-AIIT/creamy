package creamy.annotation;

import creamy.activity.Activity;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * レイアウトテンプレート設定用アノテーション.
 * Activityクラスに設定し、要素にはレイアウトテンプレートのクラス名を設定する。
 * @author ahayama
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Template {
    Class<? extends Activity> value();
}
