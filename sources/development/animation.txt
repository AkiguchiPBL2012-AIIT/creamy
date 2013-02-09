=============================================
アニメーション
=============================================
アニメーション機能を使うことで、Creamyアプリケーションの画面に表現力を持たせることが出来ます。

CreamyのEffect/Animation機能は、jQuery UIを参考に作成しています。

提供する機能
=============================================
Effects/Toggle/Hide/showの4種類があります。

.. list-table:: 
   :widths: 15 85
   :header-rows: 1
   
   * - 機能
     - 説明
   * - Effects
     - 対象のノードに指定したアニメーションを動作させる
   * - show
     - 指定したアニメーションを動作させながらノードを表示
   * - Hide
     - 指定したアニメーションを動作させながらノードを非表示
   * - Toggle
     - 指定したアニメーションを動作させながらノードを表示/非表示

**アニメーションの種類**

上記4種類の機能のそれぞれに、以下のアニメーションがあります。

.. list-table:: 
   :widths: 15 85
   :header-rows: 1
   
   * - 名称
     - 説明
   * - pulstate
     - 点滅させる
   * - blind
     - ブラインドを開閉するように、縦方向に伸縮しながら画面の一部を表示/非表示する
   * - fade
     - 徐々に透明(非透明)にする
   * - scale
     - 徐々に拡大/縮小する
   * - slide
     - 左からスライドしながら表示する/右にスライドしながら非表示する

アニメーション機能は、CFAnimationクラスを使います。次にAPI一覧を示します。

CFAnimation API
----------------

**effectPulstate**

.. code-block:: java
 
 public void effectPulstate(double interval, int times)
 public void effectPulstate(double interval, int times, EventHandler<ActionEvent> callback)

**effectFade**

.. code-block:: java
 
 public void effectFade(double interval, int times)
 public void effectFade(double interval, int times, EventHandler<ActionEvent> callback)

**effectScale**

.. code-block:: java
 
 public void effectScale(double interval, double scale)
 public void effectScale(double interval, double scale, EventHandler<ActionEvent> callback)

**showPulstate**

.. code-block:: java

 public void showPulstate(double interval, int times)
 public void showPulstate(double interval, int times, EventHandler<ActionEvent> callback)

**showBlind**

.. code-block:: java

 public void showBlind(double interval)
 public void showBlind(double interval, EventHandler<ActionEvent> callback)

**showFade**

.. code-block:: java

 public void showFade(double interval, int times)
 public void showFade(double interval, int times, EventHandler<ActionEvent> callback)

**showScale**

.. code-block:: java

 public void effectScale(double interval)
 public void effectScale(double interval, EventHandler<ActionEvent> callback)

**showSlide**

.. code-block:: java

 public void effectSlide(double interval)
 public void effectSlide(double interval, EventHandler<ActionEvent> callback)

**hidePulstate**

.. code-block:: java

 public void showPulstate(double interval, int times)
 public void showPulstate(double interval, int times, EventHandler<ActionEvent> callback)

**hideBlind**

.. code-block:: java

 public void showBlind(double interval)
 public void showBlind(double interval, EventHandler<ActionEvent> callback)

**hideFade**

.. code-block:: java

 public void showFade(double interval, int times)
 public void showFade(double interval, int times, EventHandler<ActionEvent> callback)

**hideScale**

.. code-block:: java

 public void effectScale(double interval)
 public void effectScale(double interval, EventHandler<ActionEvent> callback)

**hideSlide**

.. code-block:: java

 public void effectSlide(double interval)
 public void effectSlide(double interval, EventHandler<ActionEvent> callback)

**togglePulstate**

.. code-block:: java

 public void showPulstate(double interval, int times)
 public void showPulstate(double interval, int times, EventHandler<ActionEvent> callback)

**toggleBlind**

.. code-block:: java

 public void showBlind(double interval)
 public void showBlind(double interval, EventHandler<ActionEvent> callback)

**toggleFade**

.. code-block:: java

 public void showFade(double interval, int times)
 public void showFade(double interval, int times, EventHandler<ActionEvent> callback)

**toggleScale**

.. code-block:: java

 public void effectScale(double interval)
 public void effectScale(double interval, EventHandler<ActionEvent> callback)

**toggleSlide**

.. code-block:: java

 public void effectSlide(double interval)
 public void effectSlide(double interval, EventHandler<ActionEvent> callback)


記述方法
=============================================
