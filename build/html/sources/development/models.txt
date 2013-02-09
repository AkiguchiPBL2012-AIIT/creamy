=============================================
Models
=============================================

はじめに
=============================================

CreamyのModelsは、「EBean」というO/Rマッピングのフレームワークをサポートしています。

エンティティクラス（EBean）を作成した後、アプリケーションにアクセスすると、EBeanがDBテーブルの作成SQLを自動で作成してくれます。

これで、DBテーブル作成の手間が省けます。

また、confフォルダ内の「ebean.properties」が以下のようになっているか、確認してください。

Creamyでは、デフォルトでSQLiteへの設定がしてあります。

.. code-block:: java

	datasource.default.username=
	datasource.default.password=
	datasource.default.databaseUrl=jdbc:sqlite:computer_database.sqlite3
	datasource.default.databaseDriver=org.sqlite.JDBC
	datasource.default.heartbeatsql=select 1
	datasource.default.isolationlevel=read_uncommitted


他のリレーショナルデータベースを利用する場合は、（例えば、PostgreSQL）

#. Creamy toolで作成された、lib/の下に、データベースのドライバーを配置し、ライブラリへのパスを通してください。
#. ebean.propertiesを開き、PostgreSQLにアクセスできるように下記のよう書き換えてください。


.. code-block:: java

	datasource.default.username=｛ユーザー名｝
	datasource.default.password=｛パスワード｝
	datasource.default.databaseUrl=jdbc:postgresql://127.0.0.1:5432/｛DB名｝
	datasource.default.databaseDriver=org.postgresql.Driver
	datasource.default.heartbeatsql=select 1



modelsのディレクトリ内に置くべきファイル
=============================================

Modelsは、modelsフォルダの下に、データベースのテーブル名の同じクラス名のファイル。

そのテーブル内に含まれる、カラムを実装したクラス変数、と各クラス変数へのsetter,getterを追加します。


継承すべきクラス
=============================================

CreamyのModelsである「creamy.db.model」を継承してください。

.. code-block:: java

	@Entity
	@Table(name="company")  
	public class Company extends Model{


継承すべきクラスが提供してくれる機能
=============================================

「creamy.db.model」には、基本的なDB操作としてINSERT、UPDATE、DELETEが用意されています。

* INSERT
	単純なINSERTなら、そのEBeanオブジェクトのsave()メソッドを呼べば、その状態で保存されます。
* UPDATE
	UPDATEも、そのEBeanオブジェクトのupdate()メソッドを呼べば、変更した内容で更新されます。
* DELETE
	UPDATE同様に対象のオブジェクトのdelete()メソッドを呼べば、そのオブジェクトは削除されます。
* SELECT
	Creamyでは、検索を行う「creamy.db.model.Finder」クラスが用意されています。このクラスを使うには、以下のようにFinderオブジェクトを作成します。

.. code-block:: java

	Finder<Long, Parent> finder = new Finder<Long, Parent>(Long.class, Parent.class);
　
ここでのLongクラスはPrimary Keyのクラスを表し、Parentクラスは結果として返すクラスを意味します。下記は、このFinderクラスで提供するメソッドの例です。

* all()：全件検索を行う
* byId(Long id)：Primary Keyによる検索を行う。引数のクラスはPrimary Keyとして指定したクラス
* where(String arg)：検索条件を指定し、Queryオブジェクトを取得。取得したQueryオブジェクトより、結果を取得

また、EBeanで用意されているアノテーションを提供します。

@Entity：このクラスがDBテーブルを表すオブジェクト

@Id：DBテーブルのprimary key

@NotNull：Not Null制約

@CreatedTimestamp：作成時の時間

@Version：更新時の時間

