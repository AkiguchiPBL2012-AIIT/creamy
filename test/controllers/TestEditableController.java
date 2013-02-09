package controllers;

import creamy.annotation.Bind;
import creamy.mvc.Controller;
import creamy.mvc.Result;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import models.Company;
import models.Computer;

/**
 *
 * @author miyabetaiji
 */
public class TestEditableController extends Controller {

    public Result testEditableIndex() {
        // /TestEditableController/testEditableList/0/name/asc
        // return testEditableList(0, "name", "asc", null);
        computers = Computer.page(0, 5, "name", "asc", "").getList();
        return ok(this);
    }
                
    private List<Computer> computers;
    private CheckeDateFormat formatter = new CheckeDateFormat("yyyy-MM-dd");
    
    public Result testEditableList(int page, String sortBy, String order, @Bind("filter") String filter) {
        String checkedFilter = filter == null ? "" : filter;
        computers = Computer.page(page, 5, sortBy, order, checkedFilter).getList();
        return ok(this);
    }
    
    /*
    private Computer computer;
    
    public Result testEditableItem(Integer computerId) {
        computer = Computer.find.byId(computerId);
        return ok(this);
    }
    */
    
    public Result testCompanyName(Integer computerId) {
        Company company = Computer.find.byId(computerId).getCompany();
        return ok(company == null ? "" : company.getName());
    }
    
    public Result updateComputer(@Bind("id") Integer id,
                                 @Bind("name") String name,
                                 @Bind("introduced") String introducedStr,
                                 @Bind("discontinued") String discontinuedStr) {
        Computer comp = Computer.find.byId(id);
        // nullケース？
        comp.setName(name);
        try {
            comp.setIntroduced(formatter.parse(introducedStr));
        } catch (ParseException ex) {}
        try {
            comp.setDiscontinued(formatter.parse(discontinuedStr));
        } catch (ParseException ex) {}
        comp.update();
        return ok();
    }
    
    public Result deleteComputer(Integer computerId) {
        Computer.find.byId(computerId).delete();
        return ok();
    }
    
    public Result dataRequest() {
        return ok("testing");
    }
    
    public Result badRequestTest() {
        return badRequest();
    }
    
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
     * 検索ダイアログで入力した条件で検索.
     * 検索条件が入力されないことも考慮してパラメータをセットする。
     * @param page 表示ページ
     * @param sortBy ソートキー
     * @param order asc:昇順、des:降順
     * @param computerName コンピュータ名
     * @param companyName メーカー名
     * @param introducedFrom 検索範囲開始
     * @param introducedTo 検索範囲終了
     * @return 
     */
    public Result testEditableList(int page, String sortBy, String order,
                                 @Bind("computer") String computerName,
                                 @Bind("company") String companyName,
                                 @Bind("from") String introducedFrom,
                                 @Bind("to") String introducedTo) {
        
        computerName = computerName == null ? "" : computerName;
        companyName = companyName == null ? "" : companyName;
        
        if (introducedFrom == null || introducedTo == null) {
            computers = Computer.page(page, 5, sortBy, order, 
                    computerName).getList();
        }
        else if (introducedFrom.isEmpty() && introducedTo.isEmpty()) {
            if (companyName.isEmpty()) {
                computers = Computer.page(page, 5, sortBy, order, 
                        computerName).getList();
            }
            else {
                computers = Computer.page(page, 5, sortBy, order, 
                        computerName, companyName).getList();
            }
        }
        else {
            // 検索ダイアログで日付が指定されていないときは、検索範囲を広げてセットする。
            introducedFrom = introducedFrom.isEmpty() ? "1970-01-01" : introducedFrom;
            introducedTo = introducedTo.isEmpty() ? "2050-12-31" : introducedTo;

            // 日付範囲が時間に制限されないように、時分秒ミリ秒をセットする。
            java.sql.Timestamp from, to;
            Calendar cal = Calendar.getInstance();

            cal.set(Integer.valueOf(introducedFrom.substring(0, 4)),
                    Integer.valueOf(introducedFrom.substring(5, 7)) - 1,
                    Integer.valueOf(introducedFrom.substring(8, 10)),
                    0, 0, 0);
            cal.set(Calendar.MILLISECOND, 0);
            from = new java.sql.Timestamp(cal.getTimeInMillis());

            cal.set(Integer.valueOf(introducedTo.substring(0, 4)),
                    Integer.valueOf(introducedTo.substring(5, 7)) - 1,
                    Integer.valueOf(introducedTo.substring(8, 10)),
                    23, 59, 59);
            cal.set(Calendar.MILLISECOND, 999);
            to = new java.sql.Timestamp(cal.getTimeInMillis());

            // 検索実行
            computers = Computer.page(page, 5, sortBy, order, 
                    computerName, companyName, from, to).getList();
        }
        return ok(this);
    }
    
    public Result search() {
        return ok(this);
    }
    
    public Result confirm() {
        return ok(this);
    }
}
