package creamy.scene.layout;

/**
 * HTMLの&lt;form&gt;タグに相当するインターフェース.
 * <p>
 * 実装クラスでは、methodにGETまたはPOST、pathにはURLをセットする。
 * </p>
 */
public interface Form {
    public String getMethod();
    public void setMethod(String method);
    public String getPath();
    public void setPath(String path);
}
