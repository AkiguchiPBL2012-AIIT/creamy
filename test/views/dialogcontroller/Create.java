package views.dialogcontroller;

import creamy.activity.AvailableActivity;
import creamy.annotation.Template;
import views.dialogcontroller.DialogBase.Status;

/**
 * コンピュータデータ登録画面.
 * @author ahayama
 */
@Template(DialogBase.class)
public class Create extends AvailableActivity {
    
    /**
     * 登録確認画面の初期設定を行う.
     * 第１ステップの画面であることを親アクティビティ(DialogBase)に通知する。<br>
     */
    @Override
    public void initialize() {
        // parent activityに現在のウィンドウの状態を通知する。
        ((DialogBase)this.getParent()).setStatus(Status.FIRST_WINDOW);
    }
}
