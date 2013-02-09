=============================================
データのバリデーション
=============================================
Creamyでは、モデルに対する検証機能を用意しています。
検証機能を利用することにより、個々のモデルに対して単純なデータの
検証を実装する必要がなくなるため、本来のビジネスロジックの実装に注力できるようになります。


検証は、creamy.validation.Validatorクラスを利用し、任意のタイミングで行うか、
コントローラメソッドの引数に@Validアノテーションを付与することで、
コントローラメソッド実行前に実施することができます。


Validatorクラスを利用した検証
=============================================
Validatorクラスを利用した検証は、任意のモデルに対して任意のタイミングで行うことができます。
また、検証結果はValidationResultオブジェクトを参照することで確認出来ます。

.. code-block:: java
	:linenos:
	
	//検証対象モデル
	public class TestBean {
		// アノテーションでNotNullであることを検証することをマークする
		@NotNull
		private String id;
		// アノテーションで正規表現にマッチするか検証することをマークする
		@Pattern(regexp="[0-9]+")
		private String number;

		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getNumber() {
			return number;
		}
		public void setNumber(String number) {
			this.number = number;
		}
	}
	
	//検証実施用クラス
	public class ValidationTest {
		public void manualValid() {
			TestBean bean = new TestBean(); //検証対象のモデル
			ValidationResult result;   // 検証結果
	
			bean.setId("NotNull");        //nullではないので問題無し
			bean.setNumber("string"); //正規表現にマッチしないので問題有り
		    
			result = Validator.valid(bean); //検証の実施
		    
			//検証結果にエラーが含まれるかを判定
			if (result.hasError()) {　
				//エラーの個数を取得　この場合１
				System.out.println(result.getViolations().size()));   
				//異常な入力値を取得　この場合 "string"
				System.out,println(result.getViolations().get(0).getInvalidValue()); 
			}
		}		
	}

@Validアノテーションを利用した検証
=============================================
@Validアノテーションを利用した検証ではコントローラメソッドの引数に、
アノテーションを付与することでコントローラメソッドが実行される前に、
パラメータ適用済みのモデルオブジェクトに対して検証を行うことができます。


検証に成功した場合のみコントローラメソッドが実行されるため、安全にロジックを記述できます。


また、検証に失敗した場合は、リクエストの発生元パスに戻ります。
検証結果は戻った先でレスポンス、Activityを通して参照することができます。
以下、サンプルコードとコメントを参照してください。


.. code-block:: java
	:linenos:

	
	//検証実施用クラス
	public class ValidationControllerTest  extends Controller {
		
		//パス /ValidationControllerTest/autoValid に 
		//Formで以下のパラメータが渡されている前提
		// Id : "NotNull", Number : "string" 
		public void autoValid(@Valid Bean bean) {
			// このメソッドが実行される場合は、
			//  bean.getId()はnullではないこと
			//  bean.getNumberは"[0-9]+"にマッチすることが保障される。
		}		
	}
	
	//リクエスト発生元Activity
	public class TestCreate extends AvailableActivity {
		// 検証失敗時に元のパスに戻ってきた場合、
		// 対応するActivityから検証結果を参照できる。
		public void initialize() {
			//検証結果にエラーが含まれるかを判定
			if (validationResult != null && validationResult.hasError()) {　
				//エラーの個数を取得　この場合１
				System.out.println(validationResult.getViolations().size()));   
				//異常な入力値を取得　この場合 "string"
				System.out,println(validationResult.getViolations().get(0).getInvalidValue()); 
			}
		}
	}


バリデーションの種類
=============================================

Creamyではjavax.validation.constraintsパッケージ以下の検証用アノテーションが利用出来ます。検証用アノテーションの詳細については `javax.validation.constraintsのドキュメント <http://docs.oracle.com/javaee/6/api/javax/validation/constraints/package-summary.html>`_ を参照してください。

以下に例をいくつか挙げます。

- @NotNull : nullではないことを検証する
- @Pattern : 正規表現にマッチすることを検証する
- @Max : 上限値に収まっていることを検証する
- @Min : 下限値に収まっていることを検証する
- @Size : サイズ内に収まっていることを検証する（文字数等）
