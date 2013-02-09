=============================================
Viewの構成とActivity
=============================================

Activity
=============================================
Creamyではユーザアクションを処理する、あるいは、UIの表示要素を変更するオブジェクトをActivityと呼んでいます。

Webと比較して、Activityは何にあたるでしょうか？そう、JavaScriptです。
Webとの比較を簡単にまとめると以下となります。

======= ===============================
======= ===============================
Creamy  View = FXML + CSS + Acitivty
Web     View = HTML + CSS + JavaScript
======= ===============================

ただし、「Activity = JavaScript」ではありません。違いがあります。

イベントをハンドリングする、表示要素を変更する、リクエストの送信/ハンドリングをする という意味では同じですが構成が異なります。

* Activityは、Javaオブジェクト。JavaFXのクラスを使って記述する

* Activityは、1つのFXMLに対して必ず1つ定義される。(1対1の関係)

* Activityは階層構造を持つ。(FXMLの階層に対応した構造となる)

以下のように、FXMLが階層構造となっている場合、Activityも同じ構造となります。

.. image:: fxml-activity-figure.png

ActivityとFXMLの関係
=============================================

ActivityはJavaFXの「Controller」を拡張したものです。Creamyでは用語の統一のため「Activity」と呼んでいます。

したがって、ActivityからFXMLに記述されたNodeを参照することや、FXML内にActivity内のメソッドをハンドラとして記述することができます。以下に例を示します。

*Sample.vm.fxml*

.. code-block:: xml

  <?xml version="1.0" encoding="UTF-8"?>

  <?import java.lang.*?>
  <?import java.net.*?>
  <?import java.util.*?>
  <?import javafx.scene.control.*?>
  <?import javafx.scene.layout.*?>
  <?import javafx.scene.paint.*?>
  <?import javafx.scene.text.*?>

  <AnchorPane xmlns:fx="http://javafx.com/fxml" fx:controller="Sample"
    <children>
      <Button fx:id="button" onAction="#buttonHandler" />
      <TextField fx:id="textField" />
    </children>
  </AnchorPane>

*Sample.java*

.. code-block:: java

  public class Sample extends Activity {
    @FXML private Button button;
    @FXML private TextField textField;
  
    @FXML public void buttonHandler(ActionEvent evnent) {
      textField.setText("Hogehoge");
    }
  }

