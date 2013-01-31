/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package views.application;

import com.avaje.ebean.Page;
import creamy.activity.*;
import creamy.annotation.Template;
import creamy.scene.control.CFHyperlink;
import creamy.scene.control.CFLabel;
import creamy.scene.control.CFLinkButton;
import creamy.scene.control.CFTextField;
import creamy.scene.layout.CFHForm;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.LabelBuilder;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import models.Company;
import models.Computer;

/**
 *
 * @author TadaoIsobe
 */
@Template(Main.class)
public class List extends AvailableActivity {

    // Set page title
    public String title() { return "Computer-Database"; }
    
    // Table view and columns
    @FXML private TableView<DispComputer> computerTable;
    @FXML private TableColumn computerName;
    @FXML private TableColumn introduced;
    @FXML private TableColumn discontinued;
    @FXML private TableColumn company;
    
    // Hyper links (prev & next)
    @FXML private CFHyperlink prevLink;
    @FXML private CFHyperlink nextLink;
    
    // To create page
    @FXML private CFLinkButton createButton;
    
    // Search form
    @FXML private CFHForm searchForm;
    @FXML private CFTextField filter;
    @FXML private CFLabel fromRow;
    
    @FXML private CFLabel toRow;
    
    // Lists of computers
    private Page<Computer> listComputer;
    
    // request parameteers
    private StringProperty order;
    private StringProperty sortBy;
    private StringProperty query;
    
    // path for next page
    private StringProperty currentPage;
    private StringExpression path;

    public void initialize() {
        // Initialize contorller path
        currentPage = new SimpleStringProperty("0");
        path = Bindings.concat("/Application/list/", currentPage, "/", sortBy, "/", order);
        
        // build table view
        buildTableView();

        // build search form
        searchForm.addEventFilter(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                searchForm.setPath(path.getValue());
            }
        });
        
        // build link
        buildLink(prevLink, listComputer.hasPrev(), listComputer.getPageIndex() - 1);
        buildLink(nextLink, listComputer.hasNext(), listComputer.getPageIndex() + 1);

        // build labels
        //Integer from = (Integer.valueOf(;
        fromRow.setText(String.valueOf(listComputer.getPageIndex() * 10 + 1));
        toRow.setText(String.valueOf(listComputer.getPageIndex() * 10 + 10));
    }

    private void buildTableView() {
        // data copy from model to display model
        ObservableList<DispComputer> entries = FXCollections.observableArrayList();
        for (Computer compm : listComputer.getList()) {
            entries.add(new DispComputer(compm));
        }

        // Set event handler to tableView
        // (CLick event of table headers)
        final Map<TableColumn, String> columnsMap = buildColumns();
        computerTable.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent e) {
                ObservableList<TableColumn<DispComputer, ?>> sortCols = computerTable.getSortOrder();
                if (sortCols.isEmpty()) {
                    return;
                }
                TableColumn sortCol = sortCols.get(0);
                sortBy.set(columnsMap.get(sortCol));
                order.set(sortCol.getSortType() == TableColumn.SortType.ASCENDING ? "asc" : "desc");
            }
        });

        // Bind display mode to computer table view
        computerTable.setItems(entries);
    }

    // set setCellValueFactory to all column
    private Map<TableColumn, String> buildColumns() {
        final Map<TableColumn, String> columnsMap = new HashMap<TableColumn, String>() {
            {
                put(computerName, "name");
                put(introduced, "introduced");
                put(discontinued, "discontinued");
                put(company, "company");
            }
        };
        for (TableColumn column : columnsMap.keySet()) {
            column.setCellValueFactory(new PropertyValueFactory(columnsMap.get(column)));
        }
        computerName.setComparator(new CFHyperlink.Comprator());
        return columnsMap;
    }
    
    private void buildLink(final CFHyperlink link, Boolean enabled, final int pageNo) {
        // set enaabled
        link.setDisable(!enabled);
        
        // set event filter (for dynamic path)
        if (!enabled) return;
        link.addEventFilter(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                currentPage.set(String.valueOf(pageNo));
                link.setPath(path.getValue() + "/" + query.get());            }
        });
    }
        
    protected class DispComputer {

        private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        public CFHyperlink name;
        public String introduced;
        public String discontinued;
        public Label company;

        protected DispComputer(Computer compm) {
            setName(compm);
            setIntroduced(compm.getIntroduced());
            setDiscontinued(compm.getDiscontinued());
            setCompany(compm.getCompany());
        }

        public String getDiscontinued() {
            return discontinued;
        }

        public void setDiscontinued(String discontinued) {
            this.discontinued = discontinued;
        }

        public String getIntroduced() {
            return introduced;
        }

        public void setIntroduced(String introduced) {
            this.introduced = introduced;
        }

        public CFHyperlink getName() {
            return name;
        }

        public void setName(CFHyperlink name) {
            this.name = name;
        }

        private void setName(Computer compm) {
            this.name = hyperlink("/Application/edit/" + compm.getId().toString())
                        .text(compm.getName()).styleClass("link-regurar").build();
        }

        private void setIntroduced(Date introduced) {
            if (introduced == null) {
                this.introduced = "-";
                return;
            }
            this.introduced = formatter.format(introduced);
        }

        private void setDiscontinued(Date discontinued) {
            if (discontinued == null) {
                this.discontinued = "-";
                return;
            }
            this.discontinued = formatter.format(discontinued);
        }

        public Label getCompany() {
            return company;
        }

        private void setCompany(Company company) {
            this.company = LabelBuilder.create().prefHeight(25).build();
            if (company == null)
                this.company.setText("-");
            else
                this.company.setText(company.getName());
        }
    }
}
