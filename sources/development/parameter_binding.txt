=============================================
パラメータバインディング
=============================================

Creamyでは、ビューからコントローラを呼び出す際にForm等を利用してパラメータを
ポストすることができます。
ポストされたパラメータはコントローラで以下のように、param変数から参照できます。

.. code-block:: java
	:linenos:
	
	public Result update() {
		//全て個別に値をセットする
		Computer cp= new Computer();
		cp.setName(param("Name"));
		cp.setMakerName(param("MakerName"));
		cp.update(param("Id"));
		return ok(this);
	}


このままでも十分パラメータの利用はできますが、ここでパラメータバインディングの機構を利用すると、より簡潔にモデル等のオブジェクトや引数にパラメータを適用することができます。

オブジェクトへのパラメータ適用
------------------------------
オブジェクトへのパラメータの適用には、bindメソッドを利用します。
bindメソッドを利用することで、引数に取ったオブジェクトにパラメータが適用され、
BindingResultオブジェクトが戻り値として得られます。

利用例を以下に示します。

.. code-block:: java
	:linenos:
	
	public Result update() {
		//bindメソッドで一括で値をセットする
		Computer cp= new Computer();
		bind(cp);
		cp.update(param("Id"));
		return ok(this);
	}
	
bindメソッドは引数のオブジェクトが持つsetterに対応するパラメータが
存在する場合、そのsetterメソッドを利用して、オブジェクトにパラメータを適用します。
パラメータのキーがsetterのsetXXXと大文字小文字を区別せず一致する場合に対応するものとなっています。


引数へのパラメータ適用
------------------------------
コントローラメソッドの引数にパラメータを適用するには、
@Bind("Key")アノテーションを利用します。
利用例を以下に示します。

.. code-block:: java
	:linenos:
	
	//Idパラメータを引数として受け取る
	public Result update(@Bind("Id") Integer Id) {
		Computer cp= new Computer();
		bind(cp);
		cp.update(Id);
		return ok(this);
	}

ユーザクラス型の引数へのパラメータ適用
-------------------------------------------------------
bindメソッドを利用しなくても、引数としてパラメータの適用済みオブジェクトを
取得することも可能です。
コントローラメソッドが以下の型以外の引数を取る場合に、bindメソッド適用済みのオブジェクトが取得出来ます。

- int, Integer
- short, Short
- long, Long
- byte, Byte
- char, Character
- float, Frolat
- double, Double
- Boolean, boolean
- String

ただし、bindメソッドを利用した場合と異なり、BindingResultは取得できません。
パラメータ適用時にエラーが発生した場合は、コントローラメソッドは呼び出されません。
利用例を以下に示します。

.. code-block:: java
	:linenos:
	
	//IdとComputerを引数として受け取る
	public Result update(@Bind("Id") Integer Id, Computer cp) {
		cp.update(Id);
		return ok(this);
	}
