package creamy.browser;

import creamy.activity.WebActivity;
import creamy.mvc.Request;
import creamy.mvc.Response;
import creamy.mvc.Router;
import creamy.mvc.Status;
import creamy.scene.control.FormRequest;
import creamy.scene.control.UnitRequest;
import creamy.scene.layout.Form;
import creamy.scene.layout.FormInput;
import creamy.validation.ValidationResult;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * Browser-Router/Controller間のリクエスト、レスポンスを仲介する
 * 
 * @author Taiji Miyabe (Professor Akiguchi's PBL 2012, AIIT)
 */
public class Broker {
    /**
     * BrowserPanel
     */
    private BrowserPanel panel;
    private HandlerConstructor handlerConstructor;
    /**
     * Brokerを生成する
     * @param panel BrowserPanel
     */
    public Broker(BrowserPanel panel) {
        this.panel = panel;
        this.handlerConstructor = new HandlerConstructor(this);
    }
    
    /**
     * 指定されたパスをGETメソッドでRouter/Contorllerにリクエストする
     * @param path パス
     * @return response レスポンス
     */
    public synchronized Response sendRequest(String path) {
        Request request = new Request(panel.getBrowser(), 
                                      Request.GET, path, 
                                      panel.transitionManager.currentPathProperty().get());
        Response response = sendRequest(request);
        
        return response;
    }
    
    /**
     * 指定されたパスをPOSTメソッドでRouter/Contorllerにリクエストする
     * @param path パス
     * @return response レスポンス
     */
    public synchronized Response sendRequest(String path, Map<String,Object> params) {
        if (params == null) return sendRequest(path);
        Request request = new Request(panel.getBrowser(),
                                      Request.POST, 
                                      path,
                                      params,
                                      panel.transitionManager.currentPathProperty().get());
        return sendRequest(request);
    }
    public synchronized Response sendDataRequest(String path, Map<String,Object> params) {
        if (params == null) return sendRequest(path);
        Request request = new Request(panel.getBrowser(),
                                      Request.POST, 
                                      path,
                                      params,
                                      panel.transitionManager.currentPathProperty().get(),
                                      true);
        return sendRequest(request);    
    }
    
    
    /**
     * 指定されたコントロールのパス、メソッド(GET/POST)を解析し、
     * Router/Contorllerにリクエストする
     * @param control リクエストコントロール
     */
    public synchronized void sendRequest(UnitRequest control) {
        if (isWebRequest(control.getPath())) {
            panel.receiveResponse(webResponse(control.getPath()));
            return;
        }
        Request request = new Request(panel.getBrowser(), 
                                      control.getMethod(), 
                                      control.getPath(),
                                      panel.transitionManager.currentPathProperty().get());
        Response response = sendRequest(request);

        panel.receiveResponse(response);
    }
    
    /**
     * フォームリクエストを処理する
     * フォーム内のパラメータを収集し、リクエストする
     * @param control リクエストコントロール
     */
    public synchronized <C extends Node & FormRequest, F extends Pane & Form>
            void sendRequest(C control) {
        F form = findForm(control);
        if (form == null) {
            Logger.getLogger(getClass().getName()).log(Level.WARNING, "Form not found");
            return;
        }
        LinkedHashMap<String, Object> params = collectFormParams(form);
        
        Request request = new Request(panel.getBrowser(), 
                                      form.getMethod(), 
                                      form.getPath(), 
                                      params,
                                      panel.transitionManager.currentPathProperty().get());
        Response response = sendRequest(request);

        panel.receiveResponse(response);
    }
    
    private Response sendRequest(Request request) {
        Router router = Router.getInstance();
        Response response = null;
        boolean isRedirect = true;
        int count = 0;
        
        while (isRedirect) {
            if (count++ > 100)
                throw new RuntimeException("Ileagal redirecting. check your application controller.");
            
            showRequest(request);
            response = router.processRequest(request, ValidationResult.getEmptyResult());
            showResponse(response);
            
            String rpath = response.getRedirectPath();
            if (rpath == null || rpath.length() == 0) {
                isRedirect = false;
            } else {
                request = new Request(panel.getBrowser(), 
                                      Request.GET, 
                                      response.getRedirectPath(),
                                      panel.transitionManager.currentPathProperty().get());
                showRequest(request);
            }
        }
        if (response.getActivity() != null && response.getActivity().getScene() != null)
            handlerConstructor.constructHandler(response.getActivity().getScene());
        return response;
    }
    
    // ToDo:FormとPaneの整理
    @SuppressWarnings("unchecked")
    private <C extends Node & FormRequest, F extends Pane & Form>
            F findForm(C control) {
        Node node = control;
        
        while (node != null) {
            node = node.getParent();
            if (node instanceof Form && Pane.class.isAssignableFrom(node.getClass())) {
                return (F)node;
            }
        }
        return null;
    }
    
    private LinkedHashMap<String, Object> collectFormParams(Pane form) {
        LinkedHashMap<String, Object> params = new LinkedHashMap<>();
        Queue<Pane> panes = new LinkedList<>();
        
        panes.add(form);
        while (!panes.isEmpty()) {
            Pane pane = panes.poll();
            
            for (Node node : pane.getChildren()) {
                if (node instanceof FormInput)
                    params.put(((FormInput)node).getCFName(), ((FormInput)node).getCFValue());
                if (node instanceof Pane)
                    panes.add((Pane)node);
            }
        }        
        return params;
    }
    
    private boolean isWebRequest(String path) {
        return path.toLowerCase().matches("^http(s)?:\\/\\/.+");
    }
    
    private Response webResponse(String path) {
        WebView webview = new WebView();
        WebEngine webEngine = webview.getEngine();
        webEngine.load(path);
        WebActivity webActivity = new WebActivity();
        Pane pane = new Pane();
        pane.getChildren().add(webview);
        webActivity.setScene(pane);
        //TODO: WebResponseはValidationなしでよい？
        return new Response(Status.OK, path, webActivity);
    }
    
    // for degbug
    private void showRequest(Request request) {
        StringBuilder builder = new StringBuilder();
        
        builder.append("----- Request detail -----\n");
        builder.append("path:\t").append(request.getPath()).append("\n");
        builder.append("method:\t").append(request.getMethod()).append("\n");
        for (String key : request.getParams().keySet())
            builder.append(key).append("\t").append(request.getParams().get(key)).append("\n");
              
        Logger.getLogger(getClass().getName()).log(Level.INFO, builder.toString());
    }
    
    private void showResponse(Response response) {
        StringBuilder builder = new StringBuilder();
        
        builder.append("----- Reponse detail -----\n");
        builder.append("path:\t").append(response.getPath()).append("\n");
        builder.append("redirectPath:\t").append(response.getRedirectPath()).append("\n"); 
        
        Logger.getLogger(getClass().getName()).log(Level.INFO, builder.toString());
    }
}
