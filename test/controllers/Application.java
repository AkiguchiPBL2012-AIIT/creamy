/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import creamy.mvc.Controller;
import models.*;
import com.avaje.ebean.*;
import creamy.annotation.Bind;
import creamy.mvc.Result;
import java.util.Date;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author TadaoIsobe
 */
public class Application extends Controller {

    // home path
    private final String HOME = "/Application/list/0/name/asc";
    
    /*
     * private final int pageSize; private final long totalRowCount; private
     * final int pageIndex; private final List<Computer> list;
     */
    private Page<Computer> listComputer;
    private Computer computer;
    private StringProperty order = new SimpleStringProperty();
    private StringProperty sortBy = new SimpleStringProperty();
    private StringProperty query = new SimpleStringProperty();

    //GET Aplication/index 
    public Result index() {
        return redirect(HOME);
    }

    //GET Aplication/list/:page/:sortBy/:order
    public Result list(int page, String sortBy, String order, @Bind("filter") String filter) {
        //this.currentPage.set(String.valueOf(page));
        this.sortBy.set(sortBy);
        this.order.set(order);
        this.query.set(filter == null ? "" : filter);
        listComputer = Computer.page(page, 10, sortBy, order, query.get());
        return ok(this);
    }

    //GET  Aplication/edit/:id
    public Result edit(Integer id) {
        computer = Computer.find.byId(id);
        return ok(this);
    }

    //GET Aplication/create
    public Result create() {
        computer = new Computer();
        return ok(this);
    }

    //POST Aplication/update/:id
    public Result update(Integer id) {
        Computer comp = new Computer();
        bind(comp);
        comp.update(id);
        return redirect(HOME);
    }

    //POST Aplication/save
    public Result save() {
        Computer comp = new Computer();
        bind(comp);
        comp.save();
        return redirect(HOME);
    }

    //POST Aplication/delete/:id
    public Result delete(Integer id) {
        Computer.find.ref(id).delete();
        return redirect(HOME);
    }
}