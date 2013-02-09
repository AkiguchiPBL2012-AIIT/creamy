package controllers;

import creamy.mvc.Controller;
import creamy.mvc.Result;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import models.Company;
import models.Computer;

/**
 * 小画面のコントローラ.
 * 全画面書き換えによる画面遷移方法で小画面を作成する。
 *
 * @author ahayama
 */
public class DialogController extends Controller {
    // 確認画面で表示するための値を保持する
    private String computerName;
    private String introDate;
    private String discDate;
    private String companyName;
    
    /**
     * 新コンピュータ登録画面へ遷移.
     * @return 
     */
    public Result create() {
        return ok(this);
    }
    /**
     * メーカ選択画面へ遷移.
     * 次画面へ入力データを渡すため、ContinualDataへ入力値をセットする。
     * @return 
     */
    public Result select() {
        // 前画面パラメータを保持
        getContinualData().put("name", params("name"));
        getContinualData().put("introduced", params("introduced"));
        getContinualData().put("discontinued", params("discontinued"));
        
        return ok(this);
    }
    /**
     * 確認画面へ遷移.
     * 確認画面で表示するデータをContinualDataから取得してセット
     * @return 
     */
    public Result confirm() {
        // 確認画面で表示するデータをセット
        computerName = (String)getContinualData().get("name");
        introDate = (String)getContinualData().get("introduced");
        discDate = (String)getContinualData().get("discontinued");
        companyName = (String)getContinualData().get("companyName");
        return ok(this);
    }
    
    private CheckeDateFormat formatter = new CheckeDateFormat("yyyy-MM-dd");
    
    public class CheckeDateFormat extends SimpleDateFormat {
        private CheckeDateFormat(String format) { super(format); }
        public String formatWithSpace(Date date) {
            try {
                return super.format(date);
            } catch (Exception ex) {
                return "";
            }
        }
    }

    /**
     * 新規ダイアログの入力内容に従って、データを登録.
     * ContinualDataに保持している前画面の入力データを Computerオブジェクトにセットする。<br>
     * メーカ名は当画面でセットされているので、確認画面で表示するためContinualDataにセットしておく。
     */
    public Result createComputer() {
        String name = (String)getContinualData().get("name");
        String intr = (String)getContinualData().get("introduced");
        String disc = (String)getContinualData().get("discontinued");
        Integer id = (Integer)params("companyList");
        
        // 新規コンピュータを生成
        Computer computer = new Computer();
        computer.setName(name);
        try {
            computer.setIntroduced(formatter.parse(intr));
        } catch (ParseException ex) {}
        try {
            computer.setDiscontinued(formatter.parse(disc));
        } catch (ParseException ex) {}
        
        // Companyを取得
        Company company = Company.find.byId(id.longValue());
        computer.setCompany(company);
        
        // 新しいコンピュータを保存
        computer.save();
        
        // 確認画面で表示する企業名
        getContinualData().put("companyName", company.getName());
        
        // 遷移先の指定
        return redirect("/DialogController/confirm");
    }
    
}
