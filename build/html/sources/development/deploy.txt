===========================================================
Creamyで作成されたApplicationの実行可能JAR作成方法
===========================================================

使用方法: 

.. code-block:: c

   // buildしたclassesがある場所に移動します。
   cd ./computer_database/build/
  
   // javafxpackagerでjarを作成します。その際依存するjarがある場合は、--classpathで指定してください。
   javafxpackager -createjar -appclass computerdatabase.ComputerDatabase.class -srcdir classes -classpath ../lib/jfxrt.jar:
      ../lib/creamy.jar:../lib/ebean-2.7.3.jar:../lib/hibernate-validator-4.3.0.Final.jar:../lib/javax.validation-1.0.0.GA.jar:
      ../lib/jsonic-1.2.11.jar:../lib/persistence-api-1.0.jar:../lib/sqlite-jdbc-3.7.2.jar:../lib/velocity-1.7-dep.jar:
      ../lib/velocity-1.7.jar -nocss2bin -outdir ../dist -outfile comp_data
 
  　// jar実行コマンドで実行します。
   java -jar ../dist/comp_data.jar

このコマンドの解説していきます。
-------------------------------------- 

===============  =======================================================================================
===============  =======================================================================================
  -createjar     パッケージャは、その他のパラメータに従ってjarアーカイブを生成します。 
===============  =======================================================================================

createjarコマンドのオプションは次のとおりです。
------------------------------------------------------------
 

========================================= =======================================================================================================================
========================================= =======================================================================================================================
  -appclass <application class>           | 実行するアプリケーション・クラスの修飾名。
  -classpath <files>                      | 依存するjarファイルの名前のリスト。(jfxrt.jarとcreamy.jarは必須です。）
					                                | jfxrt.jarは
                                          | Windowsの場合は、C:\¥Program Files\¥Java\¥jdk1.7.x\¥jre\¥lib\¥jfxrt.jar
                                          | Macの場合は、/Library/Java/JavaVirtualMachines/jdk1.7.0_xx.jdk/Contents/Home/jre/lib/jfxrt.jar
					                                | にあります。
                                          | 複数ある場合は、Windowsは「セミコロン」、Macは「コロン」で繋げて記述します。
  -nocss2bin                              | パッケージャは、CSSファイルをバイナリ形式に変換せずにjarにコピーします。
                                          | (CSSを利用している場合は、こちらを指定してください。) 
  -outdir <dir>                           | 出力ファイルが生成されるディレクトリの名前。
  -outfile <filename>                     | 生成されるファイルの(拡張子なしの)名前。
  -srcdir <dir>                           | パッケージ化するファイルのベース・ディレクトリ。
  -srcfiles <files>                       | srcdir内のファイルのリスト。省略した場合は、srcdir内のすべてのファイルがパッケージ化されます。
========================================= =======================================================================================================================


`javafxpackagerを知りたい方はこちらをクリック <http://docs.oracle.com/javafx/2/deployment/javafxpackager001.htm>`_
