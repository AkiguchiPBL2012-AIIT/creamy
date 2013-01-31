Controllers
===============


はじめに
-------------------
CreamyのControllerは他のWebアプリケーションフレームワークで使われるControllerと同様の意味を持っています。
具体的には、モデルへのアクセス、Viewとのパラメータのやりとりとその遷移の制御などです。
また、Creamyではビュー等からPathに対するリクエストという形でコントローラにアクセスするため、よりWebアプリケーションフレームワークを連想させる使用感となっています。
過去に何らかのフレームワークを利用したことがある開発者であれば、その習得は容易でしょう。

準備
-------------------
Creamyがクラスをコントローラとして認識するには、いくつか条件があります。
まず、クラスの配置場所ですが、controllersパッケージ配下に配置してください。
そして、creamy.mvc.Controllerを継承してください。
これでコントローラの実装準備は整いました。
ここでの例ではApplicationクラスとします。::

	public class Application extends Controller {
	}

実装
-------------------
Creamyでコントローラのメソッドを実装する上で、いくつか特徴があります。

- 戻り値をcreamy.mvc.Resultにします
	正常終了時はokメソッド、異常なリクエストの場合はbadRequestメソッド、
	画面遷移が必要な場合はredirectメソッドの戻り値を返してください。

- パスで実行されるメソッドが決定します

- パスで引数を渡します
	リクエスト時のパスがそのままメソッドの引数として渡されます。

- パス以外でもパラメータを渡せます
	フィールド変数paramsを参照することで、Form等からポストされたパラメータを取得できます。
	
以下、簡単な実装例です。::

	public class Application extends Controller {
		
	    private Computer computer;
	
	    //リクエスト先パスは  /Application/edit/:id になります
	    public Result edit(Integer id) {
	    	if (id == null) {
	    	    //不正なリクエストとして結果を返します
	    		return badRequest(this);
	    	}
	        computer = Computer.find.byId(id);
	        if (computer == null) {
	        	//createにリダイレクトします。
	            return redirect("/Application/create");
	        }
	        //正常終了。editに対応するビューが表示されます
	        return ok(this);
	    }
	
	    //リクエスト先パスは /Application/create になります
	    public Result create() {
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

- パラーメータバインディング TODO:リンクを貼る
- データのバリデーション TODO:リンクを貼る

