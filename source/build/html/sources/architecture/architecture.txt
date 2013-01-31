=============================================
アーキテクチャ
=============================================
ここでは、

* パッケージレベルでのJavaFXライブラリ、外部ライブラリとの関連
* 主要クラスの構成

を説明します。

パッケージ構成(主要パッケージ)
=============================================
まず、パッケージ構成とJavaFXライブラリ、外部ライブラリとの関連をパッケージ図で見てみましょう。

Creamy パッケージ図
*******************************
 
.. image:: package-diagram.png

javafxとの関連
-----------------------

JavaFXにもCreamyにもscene.control、scene.layoutパッケージがあるのが分かりますね？

これは、CreamyがJavaFXのscene.control、scene.layoutをラップしているということを表しています。

実際にcreamy.scene.cntrol、creamy.scene.layoutに含まれるクラスは、ページ遷移に関連する以下のクラスです。

**creamy.scene.cntrol**

* CFHpyerlink ・・・ HTML <a> 相当
* CFTextField ・・・ HTML <input type="text"> 相当
* CFSubmittButton ・・・ HTML <input type="submit">相当
* CFChoicebox ・・・ HTML <select> 相当
* ...

**creamy.scene.layout**

* CFGridForm ・・・ HTML <form> 相当。Grid形式
* CFVForm ・・・ HTML <form> 相当。VBox形式
* CFHForm ・・・ HTML <form> 相当。HBox形式
* ...

つまり、creamy.scene.cntrolには、Formに関連するInputコントロールと、Hyperlink等のページ遷移に関連するコントロールが配置されているということです。

また、creamy.scene.layoutには、レイアウト形式に応じたFormが配置されているとうことです。

これらのコントロールは、JavaFXのプリミティブコントロール(Hyperlink,TextField,Button,ChoiceBox.../GridPane,VBox,HBox...)を拡張(継承)して作成されています。

com.avaje.ebeanとの関連
-------------------------------------------
creamy.dbからcom.avaje.ebeanへの使用依存があるのが分かります。

creamy.dbにはModelクラスが配置されており、Ebean ORマッパのAPIをラップしているのです。

org.apache.velocityとの関連
--------------------------------------------
creamy.activityからorg.apache.velocityへの使用依存があるのが分かります。

これは、Activityの生成/FXMテンプレートのレンダリングにおいて、Velocityを使用しているためです。

クラス構成(主要クラス)
=============================================
つぎに、Creamyを構成する主要クラスを見ていきましょう。


.. image:: class-diagram.png

まず、ユーザアプリケーションの基底となるクラス群です。

これは分かりやすいですね。Model, View, Controllerごとに基底となるクラスがあります。

* Model

  * 基底となるのはcreamy.db.Modelクラスです
  * このクラスはEbeanのAPIをラップすることにより、CRUD操作に関する、簡易なAPIを提供します

* View

  * UserFXMLがPaneを継承した関係になっています。これは、FXMLのルートノードが通常Paneとなるためです
  * UserActivityはcreamy.activity.AvairableAcitivtyを継承します。AvairableAcitivtyは親ノードへの参照、子ノードへの参照を保持しており、自由に親、子にアクセスすることができます。また、AvairableActivityには、以下の種類APIが用意されています。
      * Form生成のヘルパーメソッド：GridFormの生成、InputTextコントロールの生成など
      * Activity内からRequestを発行するたのめAPI：requestActivity(), requestData()の２つがある
      * アニメーションのヘルパーメソッド：スライドアニメーションなど
  
* Controller

  * UserControllerはcreamy.mvc.controllerクラスを継承します
  * creamy.mvc.controllerを継承したクラスは、ルーティングの対象になります

