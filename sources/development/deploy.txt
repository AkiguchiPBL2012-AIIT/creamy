=============================================
Creamyのデプロイ方法
=============================================

JavaFX のツール群

JavaFX SDK で提供されている主なツールは以下のようになっています。javafx は JDK でいうと java, javafxc は javac, javafxdoc は javadoc というように JDK とだいたい同じです。

================ =================================================================================================
javafx           JavaFX アプリケーションの実行
javafxc          JavaFXファイルのコンパイル
javafxdoc        JavaFXのソースからHTML形式でドキュメントを生成
javafxpackager   JavaFX アプリケーションを配備用にパッケージングアプレット用HTML,Web Start用jnlpの生成
================ =================================================================================================

JavaFX SDK のインストール場所

コマンドは JavaFX SDK をインストールしている場所の bin/ ディレクトリ以下にあります。NetBeans のプラグイン経由でインストールした場合はユーザーディレクトリ以下か、NetBeans のインストールディレクトリの javafx2 クラスタ以下にありますので探してみてください。

======================= =============================================================================================
プラットフォーム        	SDK の場所(デフォルトのインストールオプションの場合)
Mac OS X        		| SDK をインストールした場合:
		                | /Library/Frameworks/JavaFX.framework/Versions/Current
		                | (/usr/bin にリンクされます)
		                | NetBeans からプラグインをインストールした場合:
		                | <ユーザーディレクトリ>/javafx-sdk
		                | <NetBeans インストールディレクトリ>/javafx2/javafx-sdk
Windows         		| SDK をインストールした場合:
                		| C:\\Program Files\\JavaFX\\javafx-sdk
                		| NetBeans からプラグインをインストールした場合:
                		| C:\\Users\\<ユーザー>\\.netbeans\\7.1\\javafx-sdk
                		| C:\\Program Files\\NetBeans 7.1\\javafx2/javafx-sdk
======================= =============================================================================================

ドキュメントは docs/ 以下にあります。ここでは簡単に説明するだけですので詳細は SDK に付いている各ツールのドキュメントを参照してください。

.. code-block:: c

	javafxc - JavaFX ソースをコンパイル

まずコンパイルしてみましょう。javafxc コマンドにソースファイルを指定します。javac コマンドと同じですね。

.. code-block:: c

	% javafxc javafxapplication/
	Main.fx

例えば、パッケージ javafxapplication に Main.fx があるとします。

.. code-block:: c

	% ls javafxapplication/Main.fx

以下のようにファイルを指定します。

.. code-block:: c

	% javafxc javafxapplication/Main.fx

クラスファイルは何も指定しないとソースファイルと同じ場所に作成されます。

.. code-block:: c

	% ls javafxapplication/
	Main$Intf.class        
	Main.fx      
	Main.class
	

リンク
=============================================
