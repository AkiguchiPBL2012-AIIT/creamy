package creamy.activity;

/**
 * Velocityの実行時例外クラス
 * 
 * @author miyabetaiji
 */
public class VelocityException extends RuntimeException {
    public VelocityException() { super(); }
    public VelocityException(String message) { super(message); }
    public VelocityException(Throwable cause) { super(cause); }
    public VelocityException(String message, Throwable cause) { super(message, cause); }    
}