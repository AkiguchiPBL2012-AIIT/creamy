=============================================
Computer Database
=============================================

OverView
=============================================

Computer Databaseは、コンピュータ販売会社、その販売会社が販売しているコンピュータを管理するデスクトップアプリケーションです。
アプリケーションのイメージは以下のようになります。

.. image:: ../images/computer-database-overview.jpg


JavaFX開発環境をセットアップする
=============================================

以下の３つのものをインストールしてください。

#. JavaFX SDK をインストールする。
#. NetBeansをインストールする。
#. JavaFX Scene Builderをインストールする


くわしい方法は :doc:`/getting_start/set_up` に書かれています。

Creamyプロジェクトの作成
=============================================

#. NetBeansプロジェクトの新規作成。
    新規プロジェクトダイアログにて、プロジェクトにJavaFXアプリケーションを選択します。
#. Creamyのインストール
    ダウンロードした圧縮ファイルを、任意のディレクトリに解凍して配置します。
#. 環境変数(Path)への追加
    Creamyを配置した、場所へのPATHを通します。
#. コマンドライン操作(bat、shell)によるCreamyプロジェクトに必要なファイルの生成

.. code-block:: shell

	creamy_tools new


くわしい方法は :doc:`/getting_start/create_creamy_project` に書かれています。

ユーザーのエントリークラス
=============================================

このクラスは、ユーザがNetBeansでJavaFXプロジェクトを作成した際に作成されるクラスを、
creamy.entrypoint.CreamyAppクラスを継承して作成します。

ComputerDatabase.java
----------------------------

.. code-block:: java
	:linenos:

	package computerdatabase;

	import creamy.browser.TabBrowser;
	import creamy.browser.control.DefaultBrowserMenuBar;
	import creamy.browser.control.DefaultHeader;
	import creamy.entrypoint.CreamyApp;
	import javafx.stage.Stage;

	public class ComputerDatabase extends CreamyApp {

	    public static void main(String[] args) {
	        launch(args);
	    }
	    
	    @Override
	    public void start(Stage primaryStage) {
	        TabBrowser browser = new TabBrowser("/Application/index");
	        browser.setMenuBar(new DefaultBrowserMenuBar());
	        browser.setHeader(new DefaultHeader());
	        primaryStage.setScene(browser);
	        primaryStage.show();
	    }
	}

Creamyでは、BrowserというCreamy独自のクラスを利用しています。

Browserは、Webアプリケーションのように、Requestを送り、Responseを受け取り、その結果を表示させるためのCreamyにとっては重要なクラスです。

上記コードの17行目から19行目で、Browserのファミリーである、TabBrowserを作成しています。

詳しくは、:doc:`/basic_concept/browser` を参照してください。 


Controllerの書き方
=============================================

CreamyのControllerのクラスはcreamy.mvc.Controllerクラスを継承する必要があります。

Application.java
---------------------

.. code-block:: java

	public class Application extends Controller {


Controller内の各メソッドの戻り値は、creamy.mvc.Resultにする必要があります。

Creamyでは、パスで実行されるメソッドが決定されるので、それに即したメソッド名、引数で実装される必要があります。

.. code-block:: java

    //リクエストのパスは　/Aplication/list/:page/:sortBy/:order/:filter
    public Result list(int page, String sortBy, String order, @Bind(key="filter") String filter) {
        this.sortBy.set(sortBy);
        this.order.set(order);
        this.query.set(filter == null ? "" : filter);
        listComputer = Computer.page(page, 10, sortBy, order, query.get());
        return ok(this);
    }

詳しくは、:doc:`/development/controllers` を参照してください。



.. code-block:: java

    public Result list(int page, String sortBy, String order, @Bind(key="filter") String filter) {

listメソッドの引数にある「@Bind(key="filter")」は、ビューからコントローラを呼び出す際にForm等を利用して、引き渡されたパラメータを取得する為のものです。

ここでは、「filter」というパラメータを取得する事ができます。

詳しくは、:doc:`/development/parameter_binding` を参照してください。


Viewの書き方
=============================================

CreamyのViewでは、javaクラスとそのクラス名と同じfxmlファイルが必要です。

Creamyでは、クラス名.vm.fxmlという名前で作成します。

理由は、Creamyでは、fxmlのコード解析だけではなく、Velocityでの構文も解析しているため、独自にvm.fxmlをいう拡張子を使用します。

Viewのクラスでは、creamy.activity.AvailableActivityクラスを継承する必要があります。

これは、Webでいう、javaScriptのような機能を提供しています。

詳しくは、:doc:`/development/views` を参照してください。

Main.java
-----------------

このMainクラスは、各Viewの大枠となるViewを作り出すクラスです。
この大枠のクラスを元に、Viewを作成したい場合は、クラス名にアノテーションで@Template(クラス名)とする事で、指定したクラスを大枠としたViewを作成します。

.. code-block:: java
	:linenos:

	@Template(Main.class)
	public class Create extends AvailableActivity {


.. code-block:: java
	:linenos:

	package views.application;

	import creamy.activity.AvailableActivity;

	public class Main extends AvailableActivity {

	}

Main.vm.fxml
------------------

Main.vm.fxmlの35行目の「<!--% #body -->」の部分に、@Template(Main.class)のアノテーションをつけたクラスが挿入されます。

.. code-block:: xml
	:linenos:

	<?xml version="1.0" encoding="UTF-8"?>

	<?import java.lang.*?>
	<?import java.net.*?>
	<?import java.util.*?>
	<?import javafx.geometry.*?>
	<?import javafx.scene.*?>
	<?import javafx.scene.control.*?>
	<?import javafx.scene.layout.*?>
	<?import creamy.scene.control.*?>
	<?import creamy.scene.layout.*?>

	<AnchorPane prefHeight="704.0" prefWidth="1024.0" xmlns:fx="http://javafx.com/fxml" fx:controller="views.application.Main">
	  <fx:define>
	    <String fx:id="title" fx:value="Creamy" />
	  </fx:define>
	  <children>
	    <BorderPane id="borderPane" prefHeight="704.0" prefWidth="1024.0" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0">
	      <top>
	        <AnchorPane id="header" prefHeight="45.0" prefWidth="1024.0" BorderPane.alignment="CENTER">
	          <children>
	            <CFHyperlink id="home" layoutX="15.0" layoutY="15.0" text="Creamy Framework sample application - Computer database" path="/Application/list/0/name/asc/" />
	          </children>
	          <padding>
	            <Insets bottom="10.0" />
	          </padding>
	          <BorderPane.margin>
	            <Insets />
	          </BorderPane.margin>
	        </AnchorPane>
	      </top>
	      <center>
	        <StackPane>
	          <children>
	            <!--% #body -->　//この部分に@Templateを指定したクラスが描画されます。
	          </children>
	          <padding>
	            <Insets left="30.0" />
	          </padding>
	        </StackPane>
	      </center>
	    </BorderPane>
	  </children>
	</AnchorPane>



Create.java
--------------------

Viewでは、大枠にするクラス以外は、「initialize()」メソッド内に表示すべきロジックを記述してください。

.. code-block:: java

	@Template(Main.class)
	public class Create extends AvailableActivity {

	    @FXML private StackPane createForm;
	    
	    // date formatter
	    private static final String DATE_FORMAT = "yyyy-MM-dd";
	    private SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);

	    @Override
	    public void initialize() {
	        createForm.getChildren().add(
	            gridForm("/Application/save").method(Request.POST).styleClass("grid-form")
	                .row(   label("Computer Name:"),
	                        text("name"),
	                        label("Required").styleClass(this.validationResult.hasError() ? "err-text" : "guide-text"))
	                


List.java
-----------------

クラス名の最初に、CFとついているクラスは、JavaFxが提供しているクラスをより使いやすいようにCreamy FrameWorkでWrapしたものです。

.. code-block:: java
	:linenos:

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

List.vm.fxml
-----------------

.. code-block:: xml
	:linenos:

	<?xml version="1.0" encoding="UTF-8"?>

	<?import java.lang.*?>
	<?import javafx.geometry.*?>
	<?import javafx.scene.*?>
	<?import javafx.scene.control.*?>
	<?import javafx.scene.layout.*?>
	<?import creamy.scene.control.*?>
	<?import creamy.scene.layout.*?>

	<AnchorPane id="AnchorPane" maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="600.0" prefWidth="1000.0" xmlns:fx="http://javafx.com/fxml" fx:controller="views.application.List">
	  <fx:define>
	    <String fx:id="title" fx:value="Computer-Database" />
	  </fx:define>
	  <children>
	    <GridPane id="gridPane1" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0">
	      <children>
	        <AnchorPane id="anchorPane1" prefHeight="60.0" prefWidth="200.0" GridPane.columnIndex="0" GridPane.halignment="LEFT" GridPane.rowIndex="0" GridPane.valignment="CENTER">
	          <children>
	            <HBox id="hBox2" alignment="CENTER_LEFT" prefHeight="60.0" prefWidth="970.0" spacing="10.0" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0">
	              <children>
	                <Label id="label1" text="$listComputer.getTotalRowCount()" styleClass="subtitle" />
	                <Label id="label2" text="computers found" styleClass="subtitle" />
	              </children>
	            </HBox>
	          </children>
	          <GridPane.margin>
	            <Insets left="30.0" top="20" />
	          </GridPane.margin>
	        </AnchorPane>
	        <AnchorPane id="anchorPane2" prefHeight="60.0" prefWidth="200.0" GridPane.columnIndex="0" GridPane.rowIndex="1">
	          <children>
	            <GridPane id="gridPane2" prefHeight="60.0" prefWidth="960.0" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0">
	              <children>
	                <HBox id="hBox3" prefHeight="60.0" prefWidth="500.0" spacing="10.0" GridPane.columnIndex="0" GridPane.rowIndex="0">
	                  <children>
	                    <CFHForm fx:id="searchForm" method="GET" spacing="10.0" alignment="CENTER_LEFT" >
	                      <children>
	                        <CFTextField fx:id="filter" name="filter" promptText="Filter by computer name..." prefWidth="200.0"/>
	                        <CFSubmitButton fx:id="filterButton" styleClass="btn-primary" text="Filter by name" />
	                      </children>
	                    </CFHForm>
	                  </children>
	                  <GridPane.margin>
	                    <Insets bottom="30.0" top="30.0" />
	                  </GridPane.margin>
	                </HBox>
	                <CFLinkButton fx:id="createButton" styleClass="btn-success" path="/Application/create" alignment="CENTER_RIGHT" text="Add a new computer" GridPane.columnIndex="1" GridPane.rowIndex="0" >
	                </CFLinkButton>
	              </children>
	              <columnConstraints>
	                <ColumnConstraints hgrow="SOMETIMES" maxWidth="805.0" minWidth="10.0" prefWidth="767.0" />
	                <ColumnConstraints halignment="CENTER" hgrow="SOMETIMES" maxWidth="478.0" minWidth="10.0" prefWidth="193.0" />
	              </columnConstraints>
	              <rowConstraints>
	                <RowConstraints minHeight="10.0" vgrow="SOMETIMES" />
	              </rowConstraints>
	            </GridPane>
	          </children>
	          <GridPane.margin>
	            <Insets left="40.0" />
	          </GridPane.margin>
	        </AnchorPane>
	        <AnchorPane id="anchorPane3" prefHeight="300.0" prefWidth="200.0" GridPane.columnIndex="0" GridPane.rowIndex="2">
	          <children>
	            <TableView fx:id="computerTable" styleClass="tbl" prefHeight="300.0" prefWidth="960.0" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="40.0" AnchorPane.topAnchor="0.0">
	              <columns>
	                <TableColumn fx:id="computerName" text="Computer name" prefWidth="338.0" />
	                <TableColumn fx:id="introduced"   text="Introduced"    prefWidth="180.0" />
	                <TableColumn fx:id="discontinued" text="Discontinued"  prefWidth="180.0" />
	                <TableColumn fx:id="company"      text="Company"       prefWidth="220.0" />
	              </columns>
	            </TableView>
	          </children>
	          <GridPane.margin>
	            <Insets left="40.0" />
	          </GridPane.margin>
	        </AnchorPane>
	        <AnchorPane id="anchorPane4" prefHeight="60.0" prefWidth="200.0" GridPane.columnIndex="0" GridPane.rowIndex="3">
	          <children>
	            <GridPane id="gridPane3" prefHeight="60.0" prefWidth="353.0" AnchorPane.bottomAnchor="0.0" AnchorPane.rightAnchor="40.0" AnchorPane.topAnchor="0.0">
	              <children>
	                <CFHyperlink fx:id="prevLink" text="←Previous" styleClass="link-regurar" GridPane.columnIndex="0" GridPane.rowIndex="0" />
	                <CFHyperlink fx:id="nextLink" text="Next→" styleClass="link-regurar" GridPane.columnIndex="2" GridPane.rowIndex="0" />
	                <HBox id="hBox1" alignment="CENTER" prefHeight="50.0" prefWidth="187.0" spacing="5.0" GridPane.columnIndex="1" GridPane.rowIndex="0">
	                  <children>
	                    <Label text="Displaying" />
	                    <CFLabel fx:id="fromRow" />
	                    <Label text="to" />
	                    <CFLabel fx:id="toRow" />
	                    <Label text="of" />
	                    <Label text="$listComputer.getTotalRowCount()" />
	                  </children>
	                </HBox>
	              </children>
	              <columnConstraints>
	                <ColumnConstraints halignment="RIGHT" hgrow="SOMETIMES" maxWidth="130.0" minWidth="10.0" prefWidth="79.0" />
	                <ColumnConstraints hgrow="SOMETIMES" maxWidth="248.0" minWidth="10.0" prefWidth="219.0" />
	                <ColumnConstraints halignment="LEFT" hgrow="SOMETIMES" maxWidth="50.0" minWidth="10.0" prefWidth="50.0" />
	              </columnConstraints>
	              <rowConstraints>
	                <RowConstraints minHeight="10.0" vgrow="SOMETIMES" />
	              </rowConstraints>
	            </GridPane>
	          </children>
	        </AnchorPane>
	      </children>
	      <columnConstraints>
	        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" />
	      </columnConstraints>
	      <rowConstraints>
	        <RowConstraints maxHeight="60.0"  minHeight="10.0" prefHeight="60.0"  vgrow="SOMETIMES" />
	        <RowConstraints maxHeight="60.0"  minHeight="10.0" prefHeight="60.0"  valignment="TOP" vgrow="SOMETIMES" />
	        <RowConstraints maxHeight="345.0" minHeight="10.0" prefHeight="345.0" valignment="TOP" vgrow="SOMETIMES" />
	        <RowConstraints maxHeight="60.0"  minHeight="10.0" prefHeight="60.0"  valignment="TOP" vgrow="SOMETIMES" />
	      </rowConstraints>
	    </GridPane>
	  </children>
	</AnchorPane>


Modelの書き方
=============================================

Modelは、ebeanをCreamy用にWrapした、creamy.db.Modelクラスを継承してください。
そうする事で、ebeanで利用できる、O/Rマッパーの機能を利用する事ができます。

アノテーションも、利用できます。

Computer.java
---------------------

.. code-block:: java
	:linenos:
	
	package models;

	import com.avaje.ebean.Page;
	import creamy.db.Model;
	import java.text.ParseException;
	import java.text.SimpleDateFormat;
	import java.util.Date;
	import javax.persistence.Entity;
	import javax.persistence.Id;
	import javax.persistence.ManyToOne;
	import javax.persistence.Temporal;
	import javax.validation.constraints.NotNull;
	import javax.validation.constraints.Pattern;

	/**
	 * Computer entity managed by Ebean
	 */

	@Entity 
	public class Computer extends Model {

	    @Id
	    private Integer id;
	    
	    @Pattern(regexp = "[.]+")
	    private String name;
	    
	    @NotNull
	    @Temporal(javax.persistence.TemporalType.DATE)
	    private Date introduced;
	    
	    @NotNull
	    @Temporal(javax.persistence.TemporalType.DATE)
	    private Date discontinued;
	    
	    @ManyToOne
	    private Company company;
	    
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
	    
	}

Download
=============================================

こちらに、ComputerDabaseのサンプルプログラムが、NetBeansプロジェクトとともに置いてあります。
解凍して、そのままご利用できます。
http://example.com/download/computer_database.zip


