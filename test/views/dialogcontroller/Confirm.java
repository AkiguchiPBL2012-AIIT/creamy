package views.dialogcontroller;

import creamy.activity.AvailableActivity;
import creamy.annotation.Template;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * 登録確認画面.
 * 登録結果を表示する。
 * @author ahayama
 */
@Template(DialogBase.class)
public class Confirm extends AvailableActivity {
    
    @FXML private Label name;
    @FXML private Label introduced;
    @FXML private Label discontinued;
    @FXML private Label company;
    
    // 前画面の登録内容を保持
    private String computerName;
    private String introDate;
    private String discDate;
    private String companyName;

    /**
     * 登録確認画面の初期設定を行う.
     * 第３ステップの画面であることを親アクティビティ(DialogBase)に通知する。<br>
     * 登録内容を画面に表示する。
     */
    @Override
    public void initialize() {
        ((DialogBase)this.getParent()).setStatus(DialogBase.Status.THIRD_WINDOW);
        
        // 登録した値を表示
        this.name.setText(computerName);
        this.introduced.setText(introDate);
        this.discontinued.setText(discDate);
        this.company.setText(companyName);
    }
}
