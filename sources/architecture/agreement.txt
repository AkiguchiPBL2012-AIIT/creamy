==============
Creamyの規約
==============
Creamyは、「設定より規約」（convention over configuration）という考え方に則って設計されています。そのため、Creamyでの開発では多くの設定を把握する必要はなく、いくつかの規約に従うだけです。

また、Creamyの規約はweb開発経験者にとって馴染み易いものとして設計されています。


ファイルとクラス名の規約
======================================

Creamyではモデル、コントローラ、ビューに対応するクラスがあり、
特にコントローラとビューに関しては命名規約によってその関連が表されています。
ただし、その命名規約は非常にシンプルです。

- コントローラ名に対応するパッケージをviews以下に配置
- コントローラメソッドに対応するファイルを上記パッケージ下に配置


以下、例を示します。


.. csv-table::
	
	"コントローラ名", "コントローラメソッド名", "ビューパッケージ名", "ビューファイル名"
	"controller.Application", "create", "views.application", "Create.vm.fxml, Create.java"


モデルとRDBの規約
======================================

Creamyでは、O/RマッパーとしてEBeanを採用しています。
そのため、EBeanの規約に従うことで、リレーショナルデータベース(以下RDB)との強力な連携が可能となります。


命名規約
--------------

EBeanの命名規約は非常にシンプルです。
RDBのテーブル名とクラス名、テーブルの列名とフィールド変数名が対応します。
またその際、SampleTableクラスはsample_tableテーブル、sampleFieldはsample_filed列のように複合語はキャメルケースとアンダースコア区切りに対応します。



.. csv-table:: 

	"", "RDB", "モデルクラス"
	"テーブル/モデルクラス名" , "computer",  "Computer"
	"テーブル/モデルクラス名" , "user_info", "UserInfo" 
	"列/フィールド名", "date", "date"
	"列/フィールド名", "user_name", "userName"
	 

アノテーション
---------------
EBeanでは様々なアノテーションを利用することで、RDB上のテーブルの構成を表現します。
また、アノテーション等を設定したクラスを元にRDBを自動で構成することも可能です。
詳細は `モデルの項
<../development/models.html>`_ 、もしくは `EBeanの公式ページ
<http://www.avaje.org/>`_ を参照してください。

.. code-block:: java
	:linenos:
	
	@Entity                                //テーブルを表すクラスとして明示
	@Table(name="company")   //companyテーブルと関連を明示
	public class Company extends Model {
		@Id //主キーであることを明示
	    public Integer id;
	    
	    @NotNull //NotNull制約があることを明示
	    public String name;
	}




コントローラの規約
======================================
パスについて
----------------------------------
Creamyでは、コントローラメソッドの呼び出しをパスに対するリクエストという形で
行います。
パスは/コントローラ名/メソッド名/パラメータ1/パラメータ2...という形で表されます。
この際、パラメータの数や順序はメソッド名に対応するメソッドのシグネチャと整合している必要があります。


.. code-block:: java
	:linenos:

	public class Application extends Controller {
	
	    //   パス /Aplication/edit/”数値” を呼び出すと
	    public Result edit(Integer id) {
	        computer = Computer.find.byId(id);
	        return ok(this);
	    }
	    
	    //GET Aplication/create
	   public Result create() {
	        computer = new Computer();
	        return ok(this);
	    }
	}

ビューとの関連について
---------------------------------
リクエストが正常に発行され、コントローラのメソッドが呼び出された場合、
メソッド内で別のパスにリダイレクト（フォワード）するなどしなければ、
そのメソッド名に対応するViewが表示されます。


.. code-block:: java
	:linenos:

	public class Application extends Controller {
	
		// パス "/Application/home" にアクセスすると
		// views.application.home.vm.fxmlの画面が表示される
	    public Result home() {
	        return ok(this);
	    }
	    
	    //  パス "/Aplication/redirect"にアクセスすると
	    //  パス "/Application/home"にアクセスしたときと同じ画面が表示される
	    public Result redirect() {
	        Computer.find.ref(id).delete();
	        return redirect("/Application/home"); //リダイレクト
	    }
	}


ビューの規約
======================================
ビューに関する規約は非常にシンプルです。
Ceamyでは、ビューはFXMLとActivityクラスの組み合わせから成ります。
ファイルとクラス名の規約を守っていれば問題ありません。


.. csv-table:: 

	"コントローラ名", "コントローラメソッド名", "ビューパッケージ名", "ビューファイル名"
	"controller.Application", "create", "views.application", "Create.vm.fxml, Create.java"
