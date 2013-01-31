package creamy.activity;

/**
 * FXMLロードの実行時例外クラス
 * 
 * @author miyabetaiji
 */
public class FXMLLoadException extends RuntimeException {
    public FXMLLoadException() { super(); }
    public FXMLLoadException(String message) { super(message); }
    public FXMLLoadException(Throwable cause) { super(cause); }
    public FXMLLoadException(String message, Throwable cause) { super(message, cause); }    
}