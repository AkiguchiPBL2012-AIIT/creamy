===========================================================
コンピュータ一覧画面を作る
===========================================================

`Creamyの基本概念 <basic_concept.html>`_ で説明しているように、画面はviewだぞ。という訳で、

Step1 : FXMLファイルを作る
=============================================
List.vm.fxml を作ります。

画面レイアウトを作成するにはScene Builderを使うのが便利だけど、残念ながら List.vm.fxml は、Creamy独自の要素を使っているので開けません。

新たにFXMLファイルを作成する場合はScene Builderを使ってレイアウトを決め、その後にCreamy独自の要素をテキストエディタなどのツールを使って組み込むのが良いでしょう。

List.vm.fxml のソースコードの一部を切り取って、貼る。

**実装例**

* fx:controllerのところ・・・JavaFXのコントローラクラスとして、List.javaがある。

*  <TableView fx:id="computerTable" ・・・一覧表示のところ

Step2 :  Activityクラスを実装する
=============================================
ListクラスをActivityクラスのサブクラスとして定義します。

`Creamyによる開発 - Views <development/views.html>`_ あたりにも書きました。

initializeメソッドは、画面が表示されたときに実行されるメソッドで、初期状態を記述する。

**実装例**

最初のパスは"/Application/list/"・・・で、Applicationコントロールクラスのlistメソッドを実行するということだよ。

引数の説明も。

次に、List.vm.fxml でソースコートを載せた場所に対応する部分のソースコードを貼る。

**実装例 @FXMLでつながるところ**

Step3 : Controllerクラスを実装する
=============================================
Applicationコントローラクラスを、Controllerクラスのサブクラスとして定義する。

`Creamyによる開発 - Controllers <development/controllers.html>`_ あたりを見て下さい。

List.javaのinitializeメソッドで"/Application/list/"・・・を指定したので、listメソッドを実装します。

**実装例 listメソッドのあたり**

引数がある場合は、型を合わせる。

@Bindが使えるが、詳細は＊＊＊を参照して下さい。

このようにパスを指定した場合は、それに対応するメソッドを実装しなければだめ。

Step4 : Modelクラスを実装する
=============================================
Computerクラス、Companyクラスは、
Scaffoldでどこまで出来るんんでしたっけ？

`Creamyによる開発 - Models <development/models.html>`_ あたりを見て下さい。

Step5 : レイアウトテンプレートを実装する
=============================================
画面の黒いバーから下の部分は、レイアウトテンプレートを使っています。

Main.java とMain.vm.fxmlが、Listアクティビティを囲んでいるイメージ

`レイアウトテンプレート <development/layout_template.html>`_ を見てくれ！

