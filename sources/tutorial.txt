================
チュートリアル
================

Overview
=============================================

ここでは、Creamyを利用した簡単なアプリケーションの作成方法を説明していきます。

次のような画面を作成します。

**画面例**

.. image:: tutorial.Step6.png
    :width: 600px

開発を始める前に、`JavaFX開発環境をセットアップする <getting_start/set_up>`_ を参考に準備をしておいてください。

次の手順で作成します。

1. Creamyプロジェクトを作成する

2. Creamyアプリケーションの自動作成

3. 日付データを登録する

4. 検索機能を追加する

Creamyプロジェクトの作成
=============================================

まず、NetBeansにCreamyプロジェクトを作成します。

`Creamyプロジェクトの作成 <getting_start/create_creamy_project>`_ の手順通りに行えば、簡単に作成することができます。

ここでは、NetBeansに **NewProjeSample** というプロジェクトが作成できますが、プロジェクト名は適宜変更可能です。


Creamyアプリケーションの自動作成
=============================================

scaffoldコマンドを使うことで、テーブル作成からmodelsクラスの作成までを自動的に行うことができます。

では、`scaffoldによるアプリケーション作成 <getting_start/scaffold>`_ の手順に従って作成していきましょう。

Computerテーブルは以下の構成にします。

.. list-table:: 
   
   * - **カラム名**
     - **型**
     - **備考**
   * - name
     - String
     - コンピュータ名
   * - company_name
     - String
     - メーカ名
   * - introduced
     - Date
     - 発表日

**scaffoldコマンドの実行**

上記のテーブル構成ですので、次のコマンドを実行してください。

.. code-block:: c
 
 creamy_tools scaffold Computer name:String company_name:String introduced:Date

**初期起動画面パスの記述**

newprojesample.NewProjeSampleクラスの、Browserコンストラクタの引数部分にパスを追記します。

.. code-block:: java
 
        Browser browser = new Browser("/ComputerController/list");

**アプリケーションの実行** からの手順は、`scaffoldによるアプリケーション作成 <getting_start/scaffold>`_ と同様ですので、それに従ってください。

実行結果は、以下の通りです。

**実行例**

.. image:: tutorial.Step1.png
    :width: 600px

**データ登録**

では、データを登録してみましょう。

New Computer リンクをクリックして、登録画面に遷移させます。

**登録画面**

.. image:: tutorial.Step2.png
    :width: 600px

Name、Company_name、Introduced（yyyy-mm-dd形式）を入力して Create Computer ボタンをクリックします。

データは登録できたようですが、Introducedが表示されていません。

**登録結果画面**

.. image:: tutorial.Step3.png
    :width: 600px

次のステップで日付データを扱えるようにプログラムを修正していきましょう。

.. note::

  一旦登録したデータは、アプリケーションを再起動するとクリアされてしまいます。以下を参考に設定を変更しておいてください。
  
  `scaffoldによるアプリケーション作成 <getting_start/scaffold>`_
  ebean.propertiesのddl.run設定に関する注意事項

日付データを登録する
=============================================

次のコードをComputerControllerクラスに追加してください。Introducedに入力した内容を、Date型に変換して登録します。scaffoldで作成したcreateメソッドは削除しておいてください。

.. code-block:: java
    :linenos:
    
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    
    public Result create(Computer computer, @Bind("introduced") String _date) {
        try {
            computer.setIntroduced(formatter.parse(_date));
        } catch (ParseException ex) {}
        computer.save();
        return redirect(LIST_PATH);
    }

プログラムを再構築して実行します。

SimpleDateFormatで指定している通り、Introduced値にはyyyy-MM-dd形式で入力します。

データ入力後には、以下のように表示され、日付データが登録できます。

**登録結果画面**

.. image:: tutorial.Step7.png
    :width: 600px

次に日付の表示方法を変更してみましょう。

次のファイルはList.vm.fxmlファイルの一部で、Velocityの構文を使って一覧表示の部分を記述しています。

.. code-block:: xml
 :linenos:
 
 <!--%
 #set( $i = 1 )
 #foreach( $computer in $listOfComputer )
 <Label text="$!computer.name" GridPane.columnIndex="0" GridPane.rowIndex="$i" />
 <Label text="$!computer.company_name" GridPane.columnIndex="1" GridPane.rowIndex="$i" />
 <Label text="$!computer.introduced" GridPane.columnIndex="2" GridPane.rowIndex="$i" />

上記6行目を以下のように修正してください。

.. code-block:: xml
 :linenos:
 
 #if( $computer.introduced )
 <Label text="$formatter.format($!computer.introduced)" GridPane.columnIndex="2" GridPane.rowIndex="$i" />
 #else
 <Label text="" GridPane.columnIndex="2" GridPane.rowIndex="$i" />
 #end

検索機能を追加する
=============================================

データが登録できましたので、検索機能を追加してみましょう。

一覧画面に、検索条件入力フィールドと検索ボタンを追加します。

次は、List.vm.fxmlファイルの一部です。5行目〜19行目を追加してください。

.. code-block:: xml
  :linenos:
  
  <BorderPane id="root" prefHeight="600.0" prefWidth="800.0" xmlns:fx="http://javafx.com/fxml" fx:controller="views.computercontroller.List">
  <center>
    <VBox>
      <children>
        <CFHForm path="/ComputerController/search" prefHeight="-1.0" prefWidth="-1.0" spacing="5.0">
          <children>
            <Label text="Computer :"/>
            <CFTextField prefWidth="-1.0" name="computerName" />
             <Label text="Company :"/>
            <CFTextField prefWidth="-1.0" name="companyName" />
            <CFSubmitButton mnemonicParsing="false" text="Search" defaultButton="true" />
          </children>
          <padding>
            <Insets />
          </padding>
          <VBox.margin>
            <Insets bottom="10.0" top="10.0" />
          </VBox.margin>
        </CFHForm>
        <GridPane styleClass="grid-list">
          <children>
            <Label style="-fx-font-weight: bold;" text="Name" GridPane.columnIndex="0" GridPane.halignment="CENTER" GridPane.rowIndex="0" />

11行目にCFSubmitButtonが定義されています。このボタンをクリックすると、5行目のパスにリクエストを送りますが、CFHFormで囲まれたCFTextField要素のデータを引数として渡します。

これに対応するメソッドを、ComputerControllerクラスに追記します。

.. code-block:: java
    :linenos:
  
    public Result search(@Bind("computerName") String computerName, @Bind("companyName") String companyName) {
        
        computerName = computerName == null ? "" : computerName;
        companyName = companyName == null ? "" : companyName;
        listOfComputer = Computer.page(computerName, companyName).getList();
        return ok(views.computercontroller.List.class);
    }

では、検索を確認しましょう。プログラムを再構築して実行します。

**検索条件入力フィールド**

.. image:: tutorial.Step5.png
    :width: 600px

検索条件を入力してSearchボタンをクリックすると、検索結果が表示されます。

**検索結果表示**

.. image:: tutorial.Step6.png
    :width: 600px

