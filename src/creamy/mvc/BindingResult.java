package creamy.mvc;

/**
 * パラメータバインドの結果（エラー）を格納する
 * 
 */
public class BindingResult {
    private boolean _isError;
    private String _message;
    
    /**
     * バインド結果の作成
     * @param isError エラーの有無
     * @param message エラー内容
     */
    protected BindingResult(boolean isError, String message){
        _isError = isError;
        _message = message;
    }
    
    /**
     * エラーの有無を取得する
     * @return エラーの有無
     */
    public boolean isError() {
        return _isError;
    }
    /**
     * エラーがある場合、エラーメッセージを取得する
     * @return エラーメッセージ
     */
    public String getMessage() {
        return _message;
    }
    
}
