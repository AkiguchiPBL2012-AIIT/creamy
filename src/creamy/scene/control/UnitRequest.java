package creamy.scene.control;

/**
 * UnitRequestインターフェース.
 */
public interface UnitRequest extends Requestable {
    /**
     * メソッドをセットする.
     */
    public String getMethod();
    /**
     * パスをセットする.
     */
    public String getPath();
}
