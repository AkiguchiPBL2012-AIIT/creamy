package creamy.mvc;

import creamy.activity.Activity;
import creamy.validation.ValidationResult;

/**
 * Viewからのリクエストに対して返却されるレスポンス
 * 
 */
public class Response {
    private Status status;
    private String path;
    private String redirectPath;
    private Activity activity ;
    private Object data;
    private ValidationResult validationResult;

    public Response() {}

    /**
     * ステータスとリクエストされたパスを含むレスポンスを生成する
     * @param status レスポンスのステータス
     * @param path リクエストされたパス
     */
    public Response(Status status, String path) {
        this.path = path;
        this.status = status;
    }
    
    /**
     * ステータスとリクエストされたパス、対応するアクティビティを含むレスポンスを生成する
     * @param status レスポンスのステータス
     * @param path リクエストされたパス
     * @param activity 対応するアクティビティ
     */
    public Response(Status status, String path, Activity activity) {
        this(status, path);
        this.activity = activity;
    }
    
    /**
     * データリクエストに対するレスポンスを生成する
     * @param status レスポンスのステータス
     * @param path リクエストされたパス
     * @param data 返却するデータ
     */
    public Response(Status status, String path, Object data) {
        this(status, path);
        this.data = data;
    }
    
    /**
     * レスポンスのステータスを取得する
     * @return レスポンスのステータス
     */
    public Status getStatus() {
        return status;
    }

    /**
     * リクエストされたパスを取得する
     * @return リクエストされたパス
     */
    public String getPath() {
        return path;
    }

    /**
     * リダイレクト先のパスを取得する
     * @return リダイレクト先のパス
     */
    public String getRedirectPath() {
        return redirectPath;
    }

    /**
     * リダイレクト先のパスをセットする
     * @param redirectPath リダイレクト先のパス
     */
    public void setRedirectPath(String redirectPath) {
        this.redirectPath = redirectPath;
    }

    /**
     * レスポンスに対応するビュー（アクティビティ）を取得する
     * @return レスポンスに対応するビュー（アクティビティ）
     */
    public Activity getActivity() {
        return activity;
    }
    
    /**
     * データリクエストに対して返却されたデータを取得する
     * @return 返却されたデータ
     */
    public Object getData() {
        return data;
    }

    /**
     * リクエストを処理する際に検証された結果を取得する
     * @return 検証結果オブジェクト
     */
    public ValidationResult getValidationResult() {
        return this.validationResult;
    }
    
    /**
     * リクエストを処理する際に検証された結果をセットする
     * @param validationResult 検証結果オブジェクト
     */
    void setValidationResult(ValidationResult validationResult) {
        this.validationResult = validationResult;
    }
}
