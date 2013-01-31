package creamy.activity;

import creamy.activity.requestor.Requestor;
import creamy.browser.Broker;
import creamy.browser.Browser;
import creamy.browser.BrowserPanel;
import creamy.global.ApplicationData;
import creamy.scene.layout.ChildPane;
import creamy.utils.FinderUtil;
import creamy.validation.ValidationResult;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Map.Entry;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.PopupBuilder;
import javafx.stage.Stage;

/**
 * Activityの基底クラス.
 * 生成時に自身を初期化する.また基本的なメソッドを提供する
 *
 * @author Taiji Miyabe
 */
public abstract class Activity extends Requestor implements Initializeble {
    // title
    private static final String DELIMITER = ":";
    @FXML protected String title;
    protected ValidationResult validationResult = ValidationResult.getEmptyResult();
    
    // 小画面作成時の Owner Activivy
    protected Activity ownerActivity;
    
    /**
     * Validationの結果を取得する
     * @return 
     */
    public ValidationResult getValidationResult() {
        return this.validationResult;
    }
    /**
     * Validationの結果を格納する
     * TODO:publicはまずいがValidationのタイミング的に他に手がない？
     * 　　　privateにしてリフレクションで無理矢理呼ぶか？
     * @param validResult 
     */
    public void setValidationResult(ValidationResult validResult) {
        this.validationResult = validResult;
    }
    
    /**
     * FXMLに記述されたタイトルを取得する
     * Child Activityが存在する場合は、Child Activityのタイトルを取得し、
     * 「:」区切りで結合して返す
     * @return title
     */
    public String getTitle() {
        String childTitle = null;
        for (List<Activity> children : childActivities.values()) {
            Activity child = children.get(0);
            childTitle = child.getTitle();
        }
        if (this.title == null) {
            if (childTitle == null) return "";
            else return childTitle;
        } else {
            if (childTitle == null) return title;
            else return title + DELIMITER + childTitle;
        }
    }
    
    /**
     * タイトルを設定する
     * @return title
     */
    public void setTitle(String title) { this.title = title; }
    
    // root node
    @FXML protected Pane scene;

    private Activity parentActivity;
    private ObservableMap<Class<? extends Activity>, List<Activity>>
            childActivities = FXCollections.observableHashMap();    
    
    public Pane getScene() { return scene; }
    
    public void setScene(Pane scene) { this.scene = scene; }
    
    protected void initialize(Activity parent, Map<Field,Object> fieldParams,
            Map<String,Object> takeoverParams) {
        setParent(parent);
        setFieldsValue(fieldParams);
        setTakeoverValues(takeoverParams);
        applyStyle();
        //initialize();
    };

    public void setParent(Activity parent) {this.parentActivity = parent;}

    public Activity getParent() {return parentActivity;}
    
    public <T extends Activity> List<T> getChildren(Class<T> clazz) {
        List<Activity> children = childActivities.get(clazz);
        if (children == null) return null;
        List<T> casted = new ArrayList<T>();
        for (Activity child : children) {
            casted.add(clazz.cast(child));
        }
        return casted;
    }
    
    public <T extends Activity> void addChild(T child) {
        List<Activity> children = childActivities.get(child.getClass());
        if (children == null) {
            children = new ArrayList<Activity>();
            childActivities.put(child.getClass(), children);
        }
        children.add(child);
    }
    
    private void setFieldsValue(Map<Field, Object> params) {
        if (params.isEmpty()) return;
        //TODO:スーパクラスのFieldコピー
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field source : params.keySet()) {
            for (Field destination : fields) {
                if (source.getName().equals(destination.getName()) &&
                    //params.get(name).getClass().isAssignableFrom(f.getType())) {
                    destination.getType().isAssignableFrom(source.getType()) ) {
                    try {
                        destination.setAccessible(true);
                        destination.set(this, params.get(source));
                        break;
                    } catch (IllegalAccessException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    private void setTakeoverValues(Map<String,Object> params) {
        if (params.isEmpty()) return;
        //TODO:スーパクラスのFieldコピー
        Field[] fields = this.getClass().getDeclaredFields();
        for (Entry<String,Object> source : params.entrySet()) {
            for (Field destination : fields) {
                if (source.getKey().equals(destination.getName()) &&
                    destination.getType().isInstance(source.getValue())) {
                    try {
                        destination.setAccessible(true);
                        destination.set(this, source.getValue());
                        break;
                    } catch (IllegalAccessException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }
    
    synchronized void createChildren(Map<Field,Object> fieldParams, RenderParameter renderParam, ValidationResult validResult) {
        List<Node> childNodes = getNodesByTag(ChildPane.class);
        for (Node childNode : childNodes) {
            try {
                ChildPane childPane = (ChildPane)childNode;
                if (childPane.getChild() == null) { continue; }
                // 子Activityを生成する
                String className;
                if (childPane.getChild().contains("."))
                    className = childPane.getChild();
                else
                    className = getPackageName() + "." + childPane.getChild();
                String fxId = childPane.getId();
                @SuppressWarnings("unchecked")
                Class<Activity> clazz = (Class<Activity>)Class.forName(className);
                Activity childActivity = ActivityFactory.getInstance().
                        createActivity(clazz, this, fieldParams, renderParam.getParams(fxId), validResult);
                // 子のrootノードをChildPaneに追加する
                childPane.getChildren().add(childActivity.getScene());
                childActivity.setScene(childPane);
                addChildActivity(childActivity);
            } catch (ClassNotFoundException ex) {
                throw new FXMLLoadException(ex);
            }
        }
    }

    protected List<Node> getNodesByTag(Class<? extends Node> clazz) {
        List<Node> nodes = new ArrayList<>();

        if (scene == null || !(scene instanceof Pane)) return nodes;

        Queue<Pane> queue = new LinkedList<>();
        queue.add((Pane)scene);

        while (!queue.isEmpty()) {
            Pane pane = queue.poll();

            for (Node node : pane.getChildren()) {
                if (clazz.isAssignableFrom(node.getClass()))
                    nodes.add(node);
                if (node instanceof Pane)
                    queue.add((Pane)node);
            }
        }
        return nodes;
    }

    private String getPackageName() {
        String className = getClass().getName();
        int idx = className.lastIndexOf('.');

        return className.substring(0, idx);
    }
    
    protected void addChildActivity(Activity childActivity) {
        Class<? extends Activity> clazz = childActivity.getClass();
        
        if (childActivities.get(clazz) == null) {
            List<Activity> children = new ArrayList<Activity>();
            children.add(childActivity);
            childActivities.put(clazz, children);   
        } else {
            List<Activity> children = childActivities.get(clazz);
            children.add(childActivity);
        }        
    }
    
    protected List<Activity> getChildActivities(Class<? extends Activity> clazz) {
        return childActivities.get(clazz);
    }
    
    private void applyStyle() {
        URL url = getClass().getResource(getCssName());
        if (url == null) return;
        
        scene.getStylesheets().add(url.toExternalForm());
    }
    
    private String getCssName() {
        String className = getClass().getName();
        int idx = className.lastIndexOf('.') + 1;

        return className.substring(idx, className.length()) + ".css";
    }
    
    /**
     * 動的リクエストで使用するBrokerを生成する
     * BrokerにはこのActivityが表示されているBrowserPanelのインスタンスをセットする
     * 
     * @return broker
     */
    @Override
    protected Broker createBroker() {
        //TODO:Browserのnewを廃止
        if (getBrowser() != null) return new Broker(getBrowser().getBrowserPanel());
        Browser browser = new Browser();
        return new Broker(browser.getBrowserPanel());
    }
    
    private Browser getBrowser() {
        try {
            return (Browser)scene.getScene();
        } catch (ClassCastException ex) {
            if (browser != null) return browser;
            throw new RuntimeException("Browser not found.", ex);
        }
    }
    
    /**
     * ApplicationData(グローバルオブジェクト)を取得する
     * @see ApplicationData
     */
    protected Map<String,Object> getApplicationData() {
        return ApplicationData.getInstance().getData();
    }

    private void showParams(Map<Field, Object> params) {
        StringBuilder builder = new StringBuilder();
        
        builder.append("----- params detail -----\n");
        for (Field key : params.keySet()) {
            builder.append(key.getName()).append("\t").append(params.get(key)).append("\n");
        }
        Logger.getLogger(getClass().getName()).log(Level.INFO, builder.toString());
    }
    /**
     * 小画面を作成、表示する.
     * Activityのコントロール（Buttonなど）からのアクションにより表示するときに使用する。
     * 
     * @param path 遷移先のパス
     * @param modality モーダリティ:APPLICATION_MODAL/NONE/WINDOW_MODAL
     * @return 小画面のアクティビティ
     */
    public Activity createWindow(String path, Modality modality) {
        
        Browser window = new Browser(path);
        Stage newStage = new Stage();
        newStage.initModality(modality);
        newStage.setScene(window);

        // activityを取得
        Activity activity = window.getBrowserPanel()
                .getTransitionManager().getCurrentResponse().getActivity();
        // owner activityをセット
        activity.setOwner(this);
        newStage.setTitle(activity.getTitle());

        // owner Windowセット
        newStage.initOwner(this.getScene().getParent().getScene().getWindow());
        
        // 新規ダイアログを表示
        newStage.show();
        
        return activity;
    }
    /**
     * 小画面を作成、表示する.
     * MenuItemなどのActivity以外からのアクションにりょり表示するときに使用する。
     * 
     * @param ownerBrowser 表示もとのブラウザ
     * @param path 遷移先のパス
     * @param modality モーダリティ:APPLICATION_MODAL/NONE/WINDOW_MODAL
     * @return 
     */
    public static Activity createWindow(Browser ownerBrowser, String path, Modality modality) {
        Browser window = new Browser(path);
        Stage newStage = new Stage();
        newStage.initModality(modality);
        newStage.setScene(window);

        // activityを取得
        Activity activity = window.getBrowserPanel()
                .getTransitionManager().getCurrentResponse().getActivity();
        // owner activityを取得
        Activity owner = ownerBrowser.getBrowserPanel()
                .getTransitionManager().getCurrentResponse().getActivity();
        // owner activityをセット
        activity.setOwner(owner);
        newStage.setTitle(activity.getTitle());

        // owner Windowセット
        newStage.initOwner(owner.getScene().getParent().getScene().getWindow());
        
        // 新規ダイアログを表示
        newStage.show();
        
        return activity;
    }
    
    /**
     * 小画面のオーナアクティビティを返す.
     * @return 小画面のオーナアクティビティ
     */
    protected Activity getOwner() {
        return ownerActivity;
    }
    
    /**
     * 小画面のオーナアクティビティを返す.
     * @return 小画面のオーナアクティビティ
     */
    protected void setOwner(Activity activity) {
        ownerActivity = activity;
    }
    
    private Browser browser;
    
    protected Popup createPopup(String path, boolean autoHide) {
       Browser child = new Browser(path);
       Activity root = child.getBrowserPanel().getTransitionManager().getCurrentResponse().getActivity();
       root.browser = child;
       return PopupBuilder.create()
                 .autoHide(autoHide)
                 .content(child.getBrowserPanel().getBody())
                 .build();
    }
    
    /**
     * このAcitivityに対応するノードツリーからノードを探索する
     * @param <T> 検索対象のクラス
     * @param target 検索対象のクラス
     * @return 一番最初に見つかったノード
     */
    protected <T extends Node> T lookup(Class<T> target) {
        return FinderUtil.lookup(scene, target);
    }

    /**
     * このAcitivityに対応するノードツリーからノードを探索する
     * @param <T> 検索対象のクラス
     * @param target 検索対象のクラス
     * @return 見つかったノードのセット
     */
    protected <T extends Node> Set<T> lookupAll(Class<T> target) {
        return FinderUtil.lookupAll(scene, target);
    }
    
    protected void moveTo(String path) {
        BrowserPanel panel = getBrowser().getBrowserPanel();
        panel.receiveResponse(panel.getBroker().sendRequest(path));
    }
}
