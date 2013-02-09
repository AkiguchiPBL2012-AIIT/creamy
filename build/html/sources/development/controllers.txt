Controllers
===============


はじめに
-------------------
Creamyのコントローラは他のWebアプリケーションフレームワークで使われるコントローラと同様の意味を持っています。
また、Creamyではビュー等からパスに対するリクエストという形でコントローラにアクセスするため、
よりWebアプリケーションフレームワークを連想させる使用感となっています。


controllersのディレクトリ内に置くべきファイル
----------------------------------------------------
コントローラはcontrollersパッケージの配下にクラスを配置してください。
コントローラクラスのクラス名はメソッド呼び出し時のパスに利用されるので、
それを踏まえてクラスを命名してください。


継承すべきクラス
-------------------
CreamyにおけるコントローラクラスはControllerクラスを継承する必要があります。
以下、に例を示します。ここでの例ではApplicationクラスとします。


.. code-block:: java
	:linenos:
	
	public class Application extends Controller {
	}


継承すべきクラスが提供する機能
------------------------------
Creamyにおけるコントローラの役割はビューとモデルを仲介、制御することです。
具体的には、モデルへのアクセス、ビューとのパラメータのやりとりとその遷移の制御などです。
コントローラを実装する上でそれらを行うためのルールとControllerクラスが提供する便利な機能を紹介します。

まずは実際のコントローラの例を示します。

.. code-block:: java
	:linenos:
	
	public class Application extends Controller {
		private Computer computer;
	    
		//リクエスト先パスは  /Application/show/{ id } になります  --- (1)
		public Result show(Integer id) {
			if (id == null) {
				return badRequest(this);   //不正なリクエストとして結果を返す。 ---- (2)
			}
			computer = Computer.find.byId(id);
			if (computer == null) {
				//redirectTestにリダイレクトします。  ---- (3)
				return redirect("/Application/redirectTest");
			}
			// 正常終了 editに対応するビューが表示される。 ---(4)
			// この場合は、/views/application/show.vm.fxmlと
			//                    /views/application/show.java       
			return ok(this);
		}
		
		//( 3)でリダイレクトされた場合呼び出される。
		public Result redirectTest() {
			// 正常終了 redirectTestに対応するビューが表示される。
			// この場合は、/views/application/rediretTest.vm.fxmlと
			//                    /views/application/rediretTest.java       
			return ok(this);
		}
	}
	
- パスで実行されるメソッドが決定します (1)
	
	コントローラのメソッド呼び出しは、ビューからのリクエストされたパスで決定されます。
	パスはWebのURLやファイルパスをイメージしてください。
	パスとメソッドの対応は "/コントローラ名/メソッド名/パラメータ1/パラメータ2/..." のようになっています。
	以下、いくつか例を示します。
	
.. csv-table:: 

   "パス", "コントローラ名","シグネチャ"
   "/Application/show/{ id }", "Application", "public Result show(String id) "
   "/Application/show/{ id }/{ name }", "Application",  "public Result show(String id, String name)"
   "/Test/exec", "Test",  "public Result exec()"

- パスで引数を渡します　(1)
	
	先に挙げた例のようにリクエスト時のパスにメソッドの引数を渡すことが可能です。
	また、大量のパラメータを渡す場合など、パスを利用しにくい場合は、
	ポストすることで別途パラメータを取得出来ます。
	パスを利用しないパラメータの取得方法は後述します。
	

- 戻り値をcreamy.mvc.Resultにします  (2) (3) (4)
	creamy.mvc.Resultを返す際、creamy.mvc.Resultを取得する３つの方法があります。

.. csv-table:: 
   :header: "メソッド名", "引数","概要"

   "ok", "Controller or  Activity or Object", ""
   "badRequest", "Controller or  Activity or Object",  "" 
   "redirect", "String", ""

	
	正常終了時はokメソッド、異常なリクエストの場合はbadRequestメソッド、
	画面遷移が必要な場合はredirectメソッドの戻り値を返してください。
	

- 規約により表示されるビューを決定します (4)
	
	正常終了時はコントローラ、メソッドに対応したビューが自動的に決定され表示されます。
	基本的には、'views.コントローラ名.メソッド名.vm.fxml'と'views.コントローラ名.メソッド名.java'が
	対応するビューとなります。
	規約の詳細については :doc:`こちら <../architecture/agreement>` を参照してください。

- パス以外でもパラメータを渡せます
	
	フィールド変数paramsを参照することで、Formコントロールからポストされたパラメータを取得できます。
	Formコントロールについての解説は :doc:`こちら <form_control>` を参照してください。 
	
以下、簡単な実装例です。



.. code-block:: java
	:linenos:
	
	public class PostTest extends Controller {
	
		//リクエスト先パスは /PostTest/post になります
		public Result post() {
			//ポストされたパラメータをparam(key)で取得します。
			if (param("id") != null) {
				computer = new Computer(param("Id"));
			} else {
				computer = new Computer();
			}
			//正常終了。createに対応するビューが表示されます。
			return ok(this);
		}
	}


その他、機能
-------------------
Creamyでは以下のようなコントローラの実装に利用出来る便利な機能が用意されています。

- `パラーメータバインディング <./development/parameter_binding.html>`_
- `データのバリデーション <./development/validation.html>`_

