package controllers;

import creamy.annotation.Bind;
import creamy.mvc.Controller;
import creamy.mvc.Result;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import models.Company;
import models.Computer;

/**
 * 小画面のコントローラ.
 * 部分書き換えにより小画面を作成する。
 * 
 * @author ahayama
 */
public class NewDialogController extends Controller {
    
    public Result newDialog() {
        return ok(this);
    }
    
    public Result selectCompany() {
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

    // 新規ダイアログの入力内容に従って、データを登録する。
    public Result createComputer(@Bind("computer") String name,
                                 @Bind("companyId") Integer id,
                                 @Bind("introduced") String introducedStr,
                                 @Bind("discontinued") String discontinuedStr) {
        
        // 新規コンピュータを生成
        Computer computer = new Computer();
        computer.setName(name);
        try {
            computer.setIntroduced(formatter.parse(introducedStr));
        } catch (ParseException ex) {}
        try {
            computer.setDiscontinued(formatter.parse(discontinuedStr));
        } catch (ParseException ex) {}
        
        // Companyを取得
        Company company = Company.find.byId(id.longValue());
        computer.setCompany(company);
        
        // 新しいコンピュータを保存
        computer.save();
        
        return ok();
    }
    
}
