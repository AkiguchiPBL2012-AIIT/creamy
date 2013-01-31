=============================================
グローバルオブジェクト
=============================================
Creamyではリクエストを跨いでパラメータを授受する方法のひとつとして、
グローバルオブジェクトが用意されています。

グローバルオブジェクトはその名の通り、
パラメータを格納出来てどこからでもアクセス可能なオブジェクトです。


グローバルオブジェクトの種類
=============================================
グローバルオブジェクトは、そのスコープの違いによって２種類存在します。

.. csv-table::
	
	"種類", "説明"
	"ApplicationData", "アプリケーション起動時に生成され、アプリケーション終了時に破棄されます。コントローラおよびアクティビティからアクセスが可能です。"
	"ContinualData", "Window(Browser)を開いたときに生成され、Windowを閉じるときに破棄されます。コントローラからのみアクセスが可能です。"
	

グローバルオブジェクトの利用
=============================================

ApplicationDataの利用はコントローラおよびアクティビティから利用が可能です。

ただし、ContinualDataの利用はコントローラからのみ利用が可能です。

以下にコントローラからのアクセス例を示します。

.. code-block:: java
	:linenos:
	
	//コントローラからのアクセス
	public class GlobalTestController extends Controller {
		public Result GlobalTest() {
	        // getApplicationData()の戻り値型は Map<String, Object>
	        getApplicationData().put("AppKey", "AppValue");
	        getApplicationData().get("AppKey"); //"AppValue"が取得できる
	        
	        // getContinualData()の戻り値型も同様に Map<String, Object>なので
	        // String型以外でも格納可能
	        getContinualData().put("ContinualKey", 12345);
	    	getContinualData().get("ContinualKey"); // 12345 が取得できる
	    	
	    }
	}
	
getApplicationDataメソッドはアクティビティでも利用可能です。


