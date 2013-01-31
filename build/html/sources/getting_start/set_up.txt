=============================================
JavaFX開発環境をセットアップする
=============================================

JavaFX SDK をインストールする
=============================================

JavaFX SDKは、JavaFXを利用するためのライブラリ・ツール群です。

WindowsとMac OS X向けのJavaFX SDKは、JDK 7にバンドルされています。JDKをインストールすると、JDK本体、JRE、JavaFX SDKのインストールが行われます。
Creamyを使う場合は、JDK1.7以上のバージョンをインストールしてください。

開発用のPCに、OracleのサイトからJavaFXをダウンロードしてインストールします。

ダウンロードは `こちら
<http://www.oracle.com/technetwork/java/javafx/downloads/index.html>`_ のページからできます。

* Oracleのダウンロードサイト

JavaFX for Java7 SEをインストールします。

.. image:: JavaFXSDK01.PNG

ダウンロードの前に、OTN Early Adopter License Agreementに同意する必要があります。また、インストールする際にはOracleのアカウントが必要になります。ない場合は、取得をしてからインストールします。


* JavaFXのダウンロードサイト

JavaFXのリンクをクリックします。この画面では、JavaFX2.2.4の例となっています。

.. image:: JavaFXSDK02.PNG


Accept License Agreement をチェックします。
Windows、Mac、64bit環境など、自分の環境に合ったインストーラを選んでダウンロードします。

.. image:: JavaFXSDK03.PNG


* インストーラの実行

ダウンロードしたインストーラを実行します。インストール先の変更などをしない限り、デフォルトの設定で進めて問題ありません。

.. image:: JavaFXSDK04.PNG


NetBeansをインストールする
=============================================

]Creamyを利用して、アプリケーションを作成するには、NetBeans7.1以上が必要となります。

NetBeansは、いわゆるIDE（統合開発環境）です。

Javaの開発においては、IDEとしてはEclipseが利用されることが多いですが、JavaFXの標準の開発環境としては、デフォルトでJavaFXのプラグインがインストールされているNetBeansが推奨されています。

また、JavaFX Scene Builderとのシームレスな連携も実現されているので、JavaFXの開発をする際には、NetBeansを用いるのがお勧めです。

NetBeansを使ったことがない方も、JavaFX、そしてCreamyの利用を機に、NetBeansを使ってみましょう。

使い勝手はEclipseとそれほど大きな差はなく、UIも軽快に動作します。

また、Creamyのプロジェクト新規作成やScaffoldのツールは、NetBeans上のプロジェクトを対象として動作します。

ダウンロードは `こちら
<http://ja.netbeans.org/>`_ のページからできます。

Creamyを使う場合は、7.1以上のバージョンをインストールしてください。


* NetBeansのダウンロードサイト

自分の環境にあったプラットフォームを選択し、Java SE など、サポートテクノロジーにJavaFXが含まれるものをダウンロードします。

.. image:: NetBeans01.PNG

* NetBeansのインストーラの実行

ダウンロードしたインストーラを実行します。

.. image:: NetBeans02.PNG


* JUnitのインストール

途中、JUnitのインストールの選択ダイアログが表示されます。単体テスト実行に便利なので、インストールしておきましょう。その他はデフォルトの設定でインストールして問題ありません。

.. image:: NetBeans03.PNG


JavaFX Scene Builderをインストールする
=============================================

JavaFX Scene Builderは、JavaFXを用いたプログラム開発をする際に、GUIデザインを支援するツールです。

NetBeansから起動することができ、シームレスに連携します。

Creamyを利用する上での必須要件ではありませんが、JavaFXを用いた開発での定番ツールなので、インストールしておきましょう。

JavaFX SDKと同様に、OracleのサイトからJavaFX Scene Builderをダウンロードします。

ダウンロードは `こちら
<http://www.oracle.com/technetwork/java/javafx/tools/index.html>`_ のページからできます。

* JavaFX Scene Builderのダウンロードサイト

Windows、Macなど、自分の環境に合ったインストーラを選んでダウンロードします。

この画面では、Developer PreviewのJavaFX Scene Builder1.1の例となっています。

.. image:: SceneBuilder01.PNG


* インストーラの実行

ダウンロードしたインストーラを実行します。インストール先の変更などをしない限り、デフォルトの設定で進めて問題ありません。

.. image:: SceneBuilder02.PNG

以上で、JavaFX開発環境の準備は完了です。
