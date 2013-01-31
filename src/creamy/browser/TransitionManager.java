package creamy.browser;

import creamy.mvc.Response;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;

/**
 * ページ遷移を管理する
 * 当該クラスはObserbarパターンにおけるSubjectの役割を果たす
 * パスの変更、タイトルの変更、コンテンツの変更、履歴状態の変更を通知する
 * 
 * @author Taiji Miyabe (Professor Akiguchi's PBL 2012, AIIT)
 */
public class TransitionManager {
    /**
     * 現在表示しているコンテンツのタイトル
     */
    private StringProperty currentTitle;
    /**
     * 現在表示しているコンテンツのパス
     */
    private StringProperty currentPath;
    /**
     * 現在表示しているコンテンツ
     */
    private ObjectProperty<Node> currentScene;
    /**
     * 現在表示しているコンテンツを保有するレスポンスオブジェクト
     */
    private Response currentResponse;
    /**
     * BackButtonの履歴
     */
    private ObservableList<Response> backHistory;
    /**
     * ForwardButtonの履歴
     */
    private ObservableList<Response> forwardHistory;
    
    /**
     * TransitionManagerを生成する
     */
    public TransitionManager() {
        currentTitle = new SimpleStringProperty();
        currentPath = new SimpleStringProperty();
        currentScene = new SimpleObjectProperty<Node>();
        backHistory = FXCollections.observableArrayList();
        forwardHistory = FXCollections.observableArrayList();
    }
    
    /**
     * Responseオブジェクトに含まれるコンテンツを表示する
     * @param response Response
     */
    public synchronized void goTo(Response response) {
        // update histories
        if (currentResponse != null && !currentResponse.getPath().equals(response.getPath())) {
            backHistory.add(currentResponse);
        }
        forwardHistory.clear();

        // change view state
        isNewScene = true; 
        setViews(response);
    }

    /**
     * 1つ前のコンテンツを表示する
     */
    public synchronized void goBack() {
        if (!backHistory.isEmpty()) {
            forwardHistory.add(0, currentResponse);
            Response response = backHistory.remove(backHistory.size() - 1);
            
            isNewScene = false; 
            setViews(response);
        }
    }

    /**
     * 1つ先のコンテンツを表示する
     */
    public synchronized void goForward() {
        if (!forwardHistory.isEmpty()) {
            backHistory.add(0, currentResponse);
            Response response = forwardHistory.remove(0);
            
            isNewScene = false;
            setViews(response);
        }
    }
  
    private synchronized void setViews(Response response) {
        currentPath.set(response.getPath());
        String title = response.getActivity().getTitle();
        currentTitle.set(title);
        currentScene.set(response.getActivity().getScene());
        currentResponse = response;
    }

    /**
     * コンテンツの変更をウォッチするリスナーを追加する
     * @param listener ChangeListener<Node>
     */
    public void addSceneListener(ChangeListener<Node> listener) {
        currentScene.addListener(listener);
    }
    
    /**
     * コンテンツの変更をウォッチするリスナーを削除する
     * @param listener ChangeListener<Node>
     */
    public void removeSceneListener(ChangeListener<Node> listener) {
        currentScene.removeListener(listener);
    }
    
    /**
     * BackHistoryウォッチするリスナーを追加する
     * @param listener ListChangeListener<Response>
     */
    public void addBackListener(ListChangeListener<Response> listener) {
        backHistory.addListener(listener);
    }
    
    /**
     * BackHistoryウォッチするリスナーを削除する
     * @param listener ListChangeListener<Response>
     */
    public void removeBackListener(ListChangeListener<Response> listener) {
        backHistory.removeListener(listener);
    }
    
    /**
     * ForwardHistoryウォッチするリスナーを追加する
     * @param listener ListChangeListener<Response>
     */
    public void addForwardListener(ListChangeListener<Response> listener) {
        forwardHistory.addListener(listener);
    }
    
    /**
     * ForwardHistoryウォッチするリスナーを削除する
     * @param listener ListChangeListener<Response>
     */
    public void removeForwardListener(ListChangeListener<Response> listener) {
        forwardHistory.removeListener(listener);
    }

    /**
     * CurrentTitleProperyを取得する
     * @return CurrentTitleProperty
     */
    public StringProperty currentTitleProperty() { return currentTitle; }
    
    /**
     * CurrentPathProperyを取得する
     * @return CurrentPathProperty
     */
    public StringProperty currentPathProperty() { return currentPath; }
    
    /**
     * CurrentPathResponseを取得する
     * @return CurrentResponse
     */
    public Response getCurrentResponse() { return currentResponse; }
    
    private boolean isNewScene;
    boolean isNewScene(Node scene) {
        return isNewScene;
    }
    
    private boolean hasScene(ObservableList<Response> history, Node scene) {
        for (Response res : history) {
            if (res.getActivity().getScene() == scene)
                return true;
        }
        return false;
    }
    
    boolean backHistoryIsEmpty() {
        return backHistory.isEmpty();
    }
    
    boolean forwardHistoryIsEmpty() {
        return forwardHistory.isEmpty();
    }
}
