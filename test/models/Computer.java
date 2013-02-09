package models;

import com.avaje.ebean.Page;
import creamy.db.Model;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
//import creamy.data.format.Formats;

/**
 * Computer entity managed by Ebean
 */
@Entity 
public class Computer extends Model {

    @Id
    private Integer id;
    
    //@Constraints.Required
    @Pattern(regexp = "[.]+")
    private String name;
    
    //@Formats.DateTime(pattern="yyyy-MM-dd")
    @NotNull
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date introduced;
    
    //@Formats.DateTime(pattern="yyyy-MM-dd")
    @NotNull
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date discontinued;
    
    @ManyToOne
    private Company company;
    
    // Date formatter (from String to Date)
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT);
    
    public void setId(Integer id){
        this.id = id;
    }
    public Integer getId(){
        return id;
    }
    
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
    
    public void setIntroduced(Date introduced){
        this.introduced = introduced;
    }
    
    // Convert from String to Date
    public void setIntroduced(String introduced) {
        try {
            this.introduced = dateFormatter.parse(introduced);
        } catch (ParseException ex) {
            this.introduced = null;
        }
    }
    
    public Date getIntroduced(){
        return introduced;
    }
    
    public void setDiscontinued(Date discontinued){
        this.discontinued = discontinued;
    }
    
    // Convert from String to Date
    public void setDiscontinued(String discontinued) {
        try {
            this.discontinued = dateFormatter.parse(discontinued);
        } catch (ParseException ex) {
            this.discontinued = null;
        }
    }
    
    public Date getDiscontinued(){
        return discontinued;
    }
    
    public void setCompany(Company company){
        this.company = company;
    }
    
    public Company getCompany(){
        return company;
    }
    
    /**
     * Generic query helper for entity Computer with id Long
     */
    public static Finder<Integer,Computer> find = new Finder<>(Integer.class, Computer.class); 
    
    /**
     * Return a page of computer
     *
     * @param page Page to display
     * @param pageSize Number of computers per page
     * @param sortBy Computer property used for sorting
     * @param order Sort order (either or asc or desc)
     * @param filter Filter applied on the name column
     */
    public static Page<Computer> page(int page, int pageSize, String sortBy, String order, String filter) {
        return 
            find.where()
                .ilike("name", "%" + filter + "%")
                .orderBy(sortBy + " " + order)
                .fetch("company")
                .findPagingList(pageSize)
                .getPage(page);
    }
    
    /**
     * 検索実行.
     * @param page 表示ページ
     * @param pageSize １ページあたりの表示件数
     * @param sortBy ソートキー
     * @param order asc:昇順、des:降順
     * @param computerName コンピュータ名
     * @param companyName メーカー名
     * @param introducedFrom 検索範囲開始
     * @param introducedTo 検索範囲終了
     * @return 
     */
    public static Page<Computer> page(int page, int pageSize, String sortBy, String order, 
            String computerName, String companyName, Timestamp introducedFrom, Timestamp introducedTo) {
        
        return 
            find.where()
                .ilike("name", "%" + computerName + "%")
                .ilike("company.name", "%" + companyName + "%")
                .ge("introduced", introducedFrom)
                .le("introduced", introducedTo)
                .orderBy(sortBy + " " + order)
                .fetch("company")
                .findPagingList(pageSize)
                .getPage(page);
    }
    
    /**
     * 検索実行.
     * @param page 表示ページ
     * @param pageSize １ページあたりの表示件数
     * @param sortBy ソートキー
     * @param order asc:昇順、des:降順
     * @param computerName コンピュータ名
     * @param companyName メーカー名
     * @return 
     */
    public static Page<Computer> page(int page, int pageSize, String sortBy, String order, 
            String computerName, String companyName) {
        
        return 
            find.where()
                .ilike("name", "%" + computerName + "%")
                .ilike("company.name", "%" + companyName + "%")
                .orderBy(sortBy + " " + order)
                .fetch("company")
                .findPagingList(pageSize)
                .getPage(page);
    }
    
}

