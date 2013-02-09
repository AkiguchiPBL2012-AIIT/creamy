package creamy.entrypoint;

import creamy.property.CreamyProps;
import javafx.application.Application;

/**
 * Creamyのアプリケーションクラス.
 * <p>
 * このクラスのサブクラスとしてユーザアプリクラスを作成する。
 * </p>
 * @author ahayama
 */
public abstract class CreamyApp extends Application {
    /**
     * Creamyアプリケーションのコンストラクタ.
     * アプリケーションの処理化処理を実行する。
     */
    public CreamyApp() {
        // configファイルの読み込み処理を実行
        System.out.println("load config.");
        CreamyProps.loadProperties();
    }
}
