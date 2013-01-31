=============================================
Activityからのリクエスト
=============================================
Activityの中からリクエストを発行して、画面の一部を変更する、あるいは、データの取得、更新を行いたい場合があります。

これは、Webアプリケーションでの、Ajaxによく似ています。ここでは、Activity内からのリクエスト方法について説明します。

提供する機能
=============================================
サンプルを見ながら機能を見ていきましょう。以下にサンプルの画面イメージを示します。

.. image:: activity-request-figure.png
   :width: 800px

機能はいたってシンプルです。

* Refreshボタン

  * Computerリスト全体を再描画します

* Show Companyボタン

  * 該当のComputerのCompany(会社名)を、Company Name欄に表示します

* Updateボタン

  * 左のテキストフィールドで設定した値に、Computerを更新します。(データベース更新も行います)

* Deleteボタン

  * 該当のComputerを削除(Delete)します。(データベース更新も行います)
  * 削除されると、該当のComputerの行が表示されなくなります。

この画面は部分テンプレートを使って、3つのFXMLで構成されています。構造は以下のとおりです。

.. code-block:: text

  Index           : root. Refreshボタンを含む
  └── List        : ItemのParent
      ├── Item    : ひとつひとつのComputerを表示
      ├── Item    : 同上
      └── ...     : ...

これを行うには、以下3つのリクエストが必要となります。

#. Listを再描画するためのリクエスト
#. Computerに紐付くCompanyの名称を取得するリクエスト
#. Computerを削除(Delete)するリクエスト

上記はAcitivity内にJavaコードで記述します。では、次に記述方法を見て行きましょう。

記述方法
=============================================

Acitivtyリクエスト(Refleshボタン)
------------------------------------------------
こでは、IndexからListを取得するリクエストが必要となります。

**記述例 - Index.java**

.. code-block:: java
  :linenos:

  @Template(Main.class)
  public class Index extends AvailableActivity {
      @FXML private AnchorPane listArea;
      
      @FXML private void refresh(ActionEvent event) {
          requestActivity("/ComputerListController/list/0/name/asc")
              .onSuccess(new CallBack<Activity>() {
                  @Override
                  public void call(Activity activity, Status status) {
                      listArea.getChildren().clear();
                      listArea.getChildren().add(activity.getScene());
                  }
              })
              .execute();
      }
  }
  
refreshメソッドはRefreshボタンのHandlerになっています。(@FXMLアノテーションが付加されているのが分かります)

Activityをリクエストする場合は、reqestActivityメソッドを利用します。このメソッドはスーパクラスAcitivityで定義されています。

では、コードの中身を見て行きましょう。

* reqestActivityの引数にはリクエスト先のパスを指定します。(6行目)
* 次にリクエストが成功した場合のコールバックを記述します。リクエストが成功した場合にこのコードが評価されます。(7-10行目)

  * コールバックはCallBack<Acitivty>インターフェイスを実装する無名クラスで記述します。

  * CallBack<Acitivty>インターフェイスで実装すべきメソッドはひとつだけです。public void call(Activity activity, Status status)を実装します。

  * 引数activityにはレスポンスとしてActivityがセットされます。statusにはリクエストのステータス(OK, BAD_REQESET, ...)がセットされます。
  * この例ではリスト領域を書き換える処理をしています。Activityからrootノードを取得するにはgetScene()メソッドを利用します。(10-11行目)

* 最後にリクエストを実行するため、executeメソッドを呼出します。これでリクエストが送信されます。

少し変わった記述になっていますが、その理由は、requestActivityでは実際にはRequestオブジェクトを生成しているためです。
onSuccess()、execute()はRequestクラスのインスタンスメソッドです。これがメソッドチェーンによって繋がっています。


Dataリクエスト(Show Companyボタン/Updateボタン/Deleteボタン)
--------------------------------------------------------------------------------
こでは、以下のリクエストが必要となります。

=================== ==============================
Show Companyボタン  Computerの属するCompanyの名称をStringで取得するリクエスト
Updateボタン        Computerの属性を更新するリクエスト
Deleteボタン        Computerを削除するリクエスト
=================== ==============================

少し長くなりますが、全コードを見て行きましょう。

**記述例 - Item.java**

.. code-block:: java
  :linenos:

  public class Item extends AvailableActivity {
      @FXML private Integer computerId;
      @FXML private TextField computerName;
      @FXML private TextField introduced;
      @FXML private TextField discontinued;
      @FXML private Label companyName;
      @FXML private Label message;
      private List parent;
  
      @Override
      public void initialize() {
          parent = (List)getParent();
          messageAnimation.setNode(message);
      }
      
      @FXML private void showCompany(ActionEvent event) {
          String path = "/ComputerListController/testCompanyName/" + computerId;
          requestData(path)
                  .onSuccess(new CallBack<Object>() {
                      @Override
                      public void call(Object data, Status status) {
                          companyName.setText(data.toString());
                      }
                  })
                  .execute();
      }
      
      @FXML private void updateComputer(ActionEvent event) {
          Map<String,Object> params = new HashMap<String,Object>() {{
             put("id", computerId);
             put("name", computerName.getText());
             put("introduced", introduced.getText());
             put("discontinued", discontinued.getText());
          }};
          requestData("/ComputerListController/updateComputer")
                  .params(params)
                  .onSuccess(new CallBack<Object>() {
                      @Override
                      public void call(Object data, Status status) {
                          message.setText("updated");
                          messageAnimation.play();
                      }      
                  })
                  .onFail(new CallBack<Object>() {
                      @Override
                      public void call(Object data, Status status) {
                          message.setText("update failed");
                      }
                  })
                  .execute();
      }
  
      @FXML private void deleteComputer(ActionEvent event) {
          String path = "/ComputerListController/deleteComputer/" + computerId;
          requestData(path)
                  .onSuccess(new CallBack<Object>() {
                      @Override
                      public void call(Object data, Status status) {
                          messageAnimation.setOnFinished(new EventHandler<ActionEvent>() {
                              @Override
                              public void handle(ActionEvent t) {
                                  parent.removed(Item.this);
                              }
                          });
                          message.setText("deleted");
                          messageAnimation.play();
                      }
                  })
                  .execute();
      }
  }

Dataをリクエストする場合は、reqestDataメソッドを利用します。このメソッドはスーパクラスAcitivityで定義されています。

**showCompanyメソッド (16行目)**

このメソッドでComputerの属するCompanyの名称を取得して、Labelを書き換える処理を行っています。ではコードを見て行きます。

* リクエスト先のパスを設定しています。この例では、"/ComputerListController/companyName/<computerId>"がパスになっています。(17行目)
* reqestData()を利用して、データ取得リクエストを生成しています。(18行目)
* コールバック内に、Labelのテキストを設定する処理を記述します。(20-24行目)

  * requestDataの場合、Callback<Object>#callの第一引数はデータを表すObject型になります。(25行目)
  * 取得したデータを用いて、Labelのテキスト(Company名称)を更新します。(26行目)

* execute()を呼出してリクエストを送信します。(25行目)

**updateComputerメソッド (28行目)**

このメソッドでComputerの属性を更新します。

* リクエスト先のパスを設定しています。この例では、"/ComputerListController/updateComputer"がパスになっています。(35行目)
* 次に更新データをパラメータとしてセットしています。

  * パラメータの登録にはparams()を使用します。
  * パラメータはMap<String,Object>型でセットします。
  * 例では29-34行目がパラメータデータをセットしているコードです。テキストフィールドの各値を収集しています。

* コールバック内に、リクエストが成功した場合の処理を記述します。(37-43行目)

  * Computerの更新では、レポンスとなるデータはありません。したがってcallメソッドの第一引数にはnullがセットされます。(39行目)
  * メッセージLabelのテキストを更新し、アニメーションを使って点滅させます。(40-41行目)

* コールバック内に、リクエストが失敗した場合の処理を記述します。(44-49行目)

  * メッセージLabelのテキストを更新し、「update failed」を表示させます。(47行目)

* execute()を呼出してリクエストを送信します。


