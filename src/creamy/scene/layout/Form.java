package creamy.scene.layout;

/**
 * HTMLの&lt;form&gt;タグに相当するインターフェース.
 * <p>
 * 実装クラスでは、methodにGETまたはPOST、pathにはURLをセットする。
 * </p>
 */
public interface Form {
    /**
     * method値を返す.
     */
    public String getMethod();
    /**
     * method値をセットする.
     * @param method メソッド
     */
    public void setMethod(String method);
    /**
     * path値を返す.
     */
    public String getPath();
    /**
     * path値をセットする.
     * @param path メソッド
     */
    public void setPath(String path);
}
