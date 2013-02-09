============================================================
コンピュータ登録画面を作る
============================================================

Add a new computerボタンをクリックすると、Add a computer画面に遷移する。

**実装例 List.vm.fxmlのボタンがあるところ辺り**

<CFLinkButton>を使って、path="/Application/create"を指定しているので、createメソッドが呼ばれるんですよね。

このCF***は、`Formコントロール <development/form_control.html>`_ に書きました。

Step1 : FXMLファイルを作る
=============================================
Create.vm.fxml を作ります。

**実装例 <StackPane id="stackPane1" fx:id="createForm"あたり**

ここに、List.javaとは違う書き方をしていますよ。


Step2 :  Activityクラスを実装する
=============================================
早速実装例を見て下さい。

**Createクラスのinitiallizaメソッド**

`Creamyによる開発 - Activity記述例 <development/views.html#id4>`_ を見て欲しい。

Step3 : Controllerクラスを実装する
=============================================
Applicationコントローラクラスに、createメソッドを実装する。

**実装例 Applicationクラスのcreateメソッド**

ちょっと説明。

こんな感じで作れます。
