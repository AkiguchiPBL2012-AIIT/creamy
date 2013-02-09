/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import creamy.db.Model;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
/**
 *
 * @author TadaoIsobe
 */
@Entity
@Table(name="company")  
public class Company extends Model{
    @Id
    @NotNull
    private Integer id;
    @NotNull
    private String name;
    
    
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
    /**
     * Generic query helper for entity Company with id Long
     */
    public static Model.Finder<Long,Company> find = new Model.Finder<>(Long.class, Company.class);

    public static Map<Integer,String> options() {
        LinkedHashMap<Integer,String> options = new LinkedHashMap<>();
        for(Company c: Company.find.orderBy("name").findList()) {
            //options.put(c.id.toString(), c.name);
            options.put(c.id, c.name);
        }
        return options;
    }
    
}
