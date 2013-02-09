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
     * Resultsから生成される
     * @param status 
     * @see Result
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
     * controllerを取得する
     * @return controller
     */
    Controller getController() { return controller; }

    /**
     * dataを取得する
     * @return data
     */
    Object getData() { return data; }

    /**
     * Activityクラスを取得する
     * @return data
     */
    Class<? extends Activity> getActivity() { return activity; }

    /**
     * statusを取得する
     * @return status
     */
    Status getStatus() { return status; }

    /**
     * redirectPathを取得する
     * @return redirectPath
     */
    String getRedirectPath() { return redirectPath; }

    boolean isNoResponse() { return controller == null && data == null && redirectPath == null; }

    boolean isRedirectResponse() { return redirectPath != null; }

    boolean isActivityResponse() { return controller != null || activity != null; }

    boolean isDataResponse() { return data != null; }
}
