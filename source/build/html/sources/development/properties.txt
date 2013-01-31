=============================================
プロパティファイル
=============================================
Creamyにはデフォルトで３つのpropertiesファイルがあります。

以下、それぞれの設定について解説します。

creamy.propertiesのパラメータ
=============================================
creamy.propertiesはCreamy独自の設定ファイルです。

creamy.propertiesの設定項目は以下の通りです。

.. csv-table:: 

	"パラメータ", "説明"
	"debug.showVTL", "画面表示時に対応するFXMLがVTLが未解決の状態で標準出力されます。"
	"debug.showFXML", "画面表示時に対応するFXMLがVTLを解決した状態で標準出力されます。"


設定ファイル例


.. code-block:: java
	:linenos:
	
		
	## -------------------------------------------------------------
	## creamy.properties
	## -------------------------------------------------------------
	## This file have properties setting values of Creamy
	## 
	## -------------------------------------------------------------
	## Debugging
	## -------------------------------------------------------------

	debug.showVTL=false
	debug.showFXML=false



ebean.propertiesのパラメータ
=============================================
ebean.propertiesはCreamyで採用しているORMであるEBeanの設定ファイルです。

ここでは特に注意の必要な設定項目についてのみ解説します。

詳細は `EBean本家 <http://www.avaje.org/>`_ のドキュメントを参照してください。


.. csv-table:: 
	
	"パラメータ", "説明"
	"ebean.ddl.generate", "trueの場合、アプリケーション起動時にモデル（エンティティ）クラスからDDL文を生成します。"
	"ebean.ddl.run" , "trueの場合、アプリケーション起動時にモデル（エンティティ）クラスから生成されたDDL文を発行します。**既存のテーブルは作り直されるため、データが全て消えることに注意してください。**"
	"datasource.default.*" , "RDBの接続設定です。コメントアウトされているサンプルを参照して設定してください。"


設定ファイル例（一部省略）


.. code-block:: java
	:linenos:
		
	
	## -------------------------------------------------------------
	## Load (Dev/Test/Prod) server specific properties
	## -------------------------------------------------------------
	## This is a possible alternative to using JNDI to set environment
	## properties externally (to the WAR file).  This is another way 
	## your Dev, Test and Prod servers can have different properties.
	
	#load.properties.override=${CATALINA_HOME}/conf/myapp.ebean.properties
	#ebean.search.jars=userapp.jar
	
	ebean.ddl.generate=true
	ebean.ddl.run=true

	〜　略　〜
	
	#SQLite接続設定
	datasource.default.username=username                                                    -- ユーザ名
	datasource.default.password=password                                                     -- パスワード
	datasource.default.databaseUrl=jdbc:sqlite:computer_database.sqlite3      -- 接続先 
	datasource.default.databaseDriver=org.sqlite.JDBC                                    -- 利用するドライバ
	datasource.default.heartbeatsql=select 1                                                   -- 動作確認用のSQL
	datasource.default.isolationlevel=read_uncommitted                                 -- トランザクション分離レベルの指定



velocity.propertiesのパラメータ
=============================================
velocity.propertiesはCreamyで採用しているテンプレートエンジンであるVelocityの設定ファイルです。
Creamyでは最小限の設定のみを記述されています。
基本的に変更する必要はありませんが変更する場合は、
`Velocity本家 <http://velocity.apache.org/>` のドキュメント等を参照してください。
以下、Creamyで設定している項目です。

.. csv-table::

	"パラメータ", "説明"
	"resource.loader" , "テンプレートをクラスパスから読み込むための設定"
	"class.resource.loader.class" , "テンプレートをクラスパスから読み込むための設定"
	"input.encoding" , "入力エンコーディング設定"
	"output.encoding" , "出力エンコーディング設定"



設定ファイル例(デフォルト)

.. code-block:: java
	:linenos:
		
	resource.loader = class
	class.resource.loader.class = org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader
	input.encoding = UTF-8
	output.encoding = UTF-8



