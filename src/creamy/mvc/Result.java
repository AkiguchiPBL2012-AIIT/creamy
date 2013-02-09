package creamy.mvc;

import creamy.activity.Activity;

/**
 * Controllerメソッドの結果、レスポンスデータを表すクラス
 * 
 * @author miyabetaiji
 */
public class Result {
    /**
     * Controllerメソッドの結果. Responseに設定される
     */
    private Status status;
    /**
     * Controllerメソッドの戻り値
     */
    private Controller controller;
    /**
     * Controllerメソッドの戻り値
     */
    private Class<? extends Activity> activity;
    /**
     * Controllerメソッドの戻り値
     */
    private Object data;
    /**
     * Controllerメソッドの戻り値
     */
    private String redirectPath;

    /**
     * ステータスを含むResultを生成する
     * @param status 
     */
    Result(Status status) { this.status = status; }

    Result(Status status, Controller controller) {
        this(status);
        this.controller = controller;
    }

    Result(Status status, Object data) {
        this(status);
        this.data = data;
    }
    
    Result(Status status, String redirectPath) {
        this(status);
        this.redirectPath = redirectPath;
    }

    Result(Status status, Controller controller, Class<? extends Activity> activty) {
        this(status, controller);
        this.activity = activty;
    } 
    /**
     * リクエスト先のコントローラを取得する
     * @return リクエスト先のコントローラ
     */
    Controller getController() { return controller; }

    /**
     * データリクエストに返却するデータを取得する
     * @return データリクエストに返却するデータ
     */
    Object getData() { return data; }

    /**
     * レスポンスに対応するアクティビティを取得する
     * @return レスポンスに対応するアクティビティ
     */
    Class<? extends Activity> getActivity() { return activity; }

    /**
     * レスポンスのステータスを取得する
     * @return レスポンスのステータス
     */
    Status getStatus() { return status; }

    /**
     * リダイレクト先のパスを取得する
     * @return リダイレクト先のパス
     */
    String getRedirectPath() { return redirectPath; }

    /**
     * レスポンスかどうかを判定する
     * @return レスポンスかどうか
     */
    boolean isNoResponse() { return controller == null && data == null && redirectPath == null; }

    /**
     * リダイレクトを行うレスポンスであるかどうかを判定する
     * @return リダイレクトを行うレスポンスであるかどうか
     */
    boolean isRedirectResponse() { return redirectPath != null; }

    /**
     * アクティビティを含むレスポンスであるかどうかを判定する
     * @return アクティビティを含むレスポンスであるかどうか 
     */
    boolean isActivityResponse() { return controller != null || activity != null; }

    /**
     * データリクエストに対するレスポンスかどうかを判定する
     * @return データリクエストに対するレスポンス
     */
    boolean isDataResponse() { return data != null; }
}
