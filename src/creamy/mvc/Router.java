package creamy.mvc;

import creamy.activity.Activity;
import creamy.activity.ActivityFactory;
import creamy.annotation.Bind;
import creamy.annotation.Valid;
import creamy.browser.Browser;
import creamy.global.WindowData;
import creamy.validation.ValidationException;
import creamy.validation.ValidationResult;
import creamy.validation.Validator;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.ConstraintViolation;

/**
 * Router class of creamy framework This class does routing user's request to
 * specified constroller
 *
 * @author Ryusaburo Tanaka (Professor Akiguchi's PBL 2012, AIIT)
 */
public class Router {

    /**
     * constants of position of CLASS element in request path
     */
    private static final int CLASS = 1;
    /**
     * constants of position of METHOD element in request path
     */
    private static final int METHOD = 2;
    /**
     * start position of parameter in request path
     */
    private static final int START_PARAM = 3;
    /**
     * singleton object of this class
     */
    private static Router router;

    /**
     * private constructor for singleton pattern
     */
    private Router() {
    }

    /**
     * new and retrun object of this class
     *
     * @return singleton object of this class
     */
    public static Router getInstance() {
        if (router == null) {
            router = new Router();
            return router;
        } else {
            return router;
        }
    }

    /**
     * recieve user's action on GUI as Request and return Response
     *
     * @param request request from GUI
     * @return Response
     */
    public Response processRequest(Request request, ValidationResult inputValidResult) {
        // add parameters to the path as additional parameters, and split each elements
        String[] elements = getElementsFromPath(request.getPath(), request.getParams());
        Controller controller = getControllerInstance(elements[CLASS], request.getParams(), request.getBrowser());
        Method method = getControllerMethod(controller.getClass(), elements[METHOD]);
        // convert array that has only parameter data
        String[] pathParams = Arrays.copyOfRange(elements, START_PARAM, elements.length);
        Object[] invokeParameter = null;
        ValidationResult validResult = ValidationResult.getEmptyResult();
        try {
            invokeParameter = getInvokeParameter(method, pathParams, request.getParams());
        } catch (ValidationException ve) {
            validResult = ve.getValidationResult();
        }
        Result result = null;
        if (!validResult.hasError()) {
            result = invokeControllerMethod(controller, method, invokeParameter);
        } else if (!request.isDataRequest()) {
            //Validationでエラーが発生した場合は、前のページに戻り、検証結果を渡す
            //Activityにも検証結果を所持させるためprocessRequestにも検証結果を渡す
            Request tempRequest = new Request(request.getBrowser(), 
                                              request.getMethod(), 
                                              request.getSenderPath(), 
                                              request.getParams(),
                                              request.getSenderPath());
            Response res = processRequest(tempRequest, validResult);
            res.setValidationResult(validResult);
            return res;
        }
        // make response and set redirect path according to need
        // inputValidResultがエラーを持つ場合は、検証失敗したページから戻ってきたパターン
        // なので、最初に検証に失敗したものの結果を優先して渡す
        return createResponse(result, request.getPath(), elements[METHOD], 
                inputValidResult.hasError() ? inputValidResult : validResult,
                request.isDataRequest());
        /*
        if (controller.isRedirect()) {
            Response response = new Response();
            response.setRedirectPath(controller.getRedirectPath());
            return response;
        } else {
            // handle activity class and invoke render method
            return createNoRedirectResponse(elements[METHOD], request.getPath(), controller);
        }
        */
    }

    private Controller getControllerInstance(String className, Map<String, Object> requestParams, Browser browser) {
        // instance controller class
        Class<?> clazz = getControllerClassClass(className);
        Controller controller = getControllerClass(clazz);
        // invoke target method with parameter depends on the type
        controller.setRequestParams(requestParams);
        // set WindowData(continualData) to controller
        controller.setContinualData(WindowData.getInstance().getData(browser));
        return controller;
    }

    private Response createResponse(Result result, 
                                    String path, 
                                    String methodName,
                                    ValidationResult validResult, 
                                    boolean isDataRequest) {
        if (isDataRequest && validResult != null && validResult.hasError()) {
            return new Response (Status.VALIDATION_ERR, path, validResult);
        }
        
        if (result.isRedirectResponse()) {
            Response response = new Response(result.getStatus(), path);
            response.setRedirectPath(result.getRedirectPath());
            return response;
        } else if (result.isActivityResponse()) {
            if (result.getActivity() == null)
                return new Response(result.getStatus(), path, createActivity(methodName,
                        result.getController(), validResult));
            else
                return new Response(result.getStatus(), path, createActivity(result.getActivity(),
                        result.getController(), validResult));
        } else if (result.isDataResponse()) {
            return new Response(result.getStatus(), path, result.getData());
        } else {
            return new Response(result.getStatus(), path, validResult);
        }
    }
    
    @SuppressWarnings("unchecked")
    private Activity createActivity(String activityName, Controller controller, ValidationResult validResult) {
        Class<? extends Activity> activityClass = null;
        try {
            // head character have to be upper case because of class name
            String activityClassName =
                    activityName.substring(0, 1).toUpperCase() + activityName.substring(1);
            String packageName = "views." + controller.getClass().getSimpleName().toLowerCase() + ".";
            activityClass = (Class<? extends Activity>) Class.forName(packageName + activityClassName);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException("Activity:" + activityName + " not found", ex);
        }
        return createActivity(activityClass, controller, validResult);
    }

    private Activity createActivity(Class<? extends Activity> activityClass,
            Controller controller, ValidationResult validResult) {
        return ActivityFactory.getInstance().createActivity(activityClass, controller, validResult);
    }
    
    /*
    private Response createNoRedirectResponse(String methodName, String requestPath, Controller controller) {
        Class<? extends Activity> activityClass = null;
        try {
            // head character have to be upper case because of class name
            String activityClassName =
                    elementMethod.substring(0, 1).toUpperCase() + elementMethod.substring(1);
            activityClass =
                    (Class<? extends Activity>) Class.forName("views." + activityClassName);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Router.class.getName()).log(Level.SEVERE, null, ex);
        }
        Activity mainActivity = ActivityFactory.getInstance().createActivity(activityClass, controller);
        //BrowserScene scene = new BrowserScene((Activity) mainActivity, (Node) mainActivity.getScene());
        return new Response(requestPath, mainActivity);
    }
    */

    private Object[] getInvokeParameter(Method method, String[] pathParams, Map<String, Object> requestParams) throws ValidationException {
        // get types of parameters of invoking target methods and save them to array
        Class<?>[] types = method.getParameterTypes();
        Annotation[][] annotations = method.getParameterAnnotations();
        Object[] invokeParams = new Object[types.length];

        for (int i = 0; i < types.length; i++) {
            //パラメータの型を取得（組み込み型の場合はラップする）
            Class<?> type = types[i];
            Class<?> wrapperClass = getWrapperIfPrimitive(type.getName());
            Bind bindAnnotation;
            //組み込み型でもStringでもなかった場合、ユーザクラスと判断してバインドする
            //TODO:配列型は？
            try {
                if ((bindAnnotation = getBindAnnotation(annotations[i])) != null) {
                    //TODO:リクエストパラメータの中身はコントローラの引数型を意識しているか？
                    invokeParams[i] = requestParams.get(bindAnnotation.value());
                } else if (wrapperClass == null) {
                    Object instance = type.newInstance();
                    MvcUtil.bindMapModel(requestParams, instance);
                    // @Validの付与されてるパラメータの場合は検証を行う
                    if (isValid(annotations[i])) {
                        ValidationResult validResult 
                            = Validator.valid(type.cast(instance));
                        if (validResult.hasError()) {
                            //エラーの場合、検証結果オブジェクトを返したいが、引数の型と合わないので
                            //Exceptionで戻す
                            throw new ValidationException(validResult);
                        }
                    }
                    invokeParams[i] = instance;
                } else {
                    //TODO:Bindやユーザクラスの場合は？
                    invokeParams[i] = getValueOfMethod(wrapperClass).invoke(null, pathParams[i]);
                }
            } catch (IllegalAccessException |
                    IllegalArgumentException |
                    InvocationTargetException |
                    InstantiationException ex) {
                Logger.getLogger(Router.class.getName()).log(Level.SEVERE, null, ex);
                invokeParams[i] = null;
            }
        }
        // parameters add formParams
        /*
         * TODO:今まで動いていたか？ if (types.length != parameters.length) { String[]
         * newParams = addFormParameters(parameters, requestParams, types.length
         * - parameters.length); parameters = newParams;
        }
         */
        return invokeParams;
    }

    /**
     * get controller method from method names specified as parameters
     *
     * @param clazz target class
     * @param elements parameters
     * @return Method from method names specified as parameter
     */
    private Method getControllerMethod(Class clazz, String elementMethod) {
        Method[] methods = clazz.getMethods();
        for (Method methodTmp : methods) {
            if (methodTmp.getName().equals(elementMethod)) {
                return methodTmp;
            }
        }
        Logger.getLogger(Router.class.getName()).log(Level.SEVERE,
                "Method not found:" + elementMethod,
                new RuntimeException("Method not found:" + elementMethod));
        return null;
    }

    /**
     * split path by "/"
     *
     * @param path request path like URL
     * @param params params, this value is used only by form type request
     * @return String array of elements of the request path
     */
    private String[] getElementsFromPath(String path, Map params) {
        /*
         * if((params != null) && (params.size() != 0)){ for (Iterator itr =
         * params.keySet().iterator(); itr.hasNext(); ){ path = path + "/" +
         * (String)params.get((String)itr.next()); } }
         */
        return path.split("/", -1);
    }

    /**
     * get controller class class of the target form class name string
     *
     * @param className class name from request path
     * @return class class of controller
     */
    private Class<?> getControllerClassClass(String className) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName("controllers." + className);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Router.class.getName()).log(Level.SEVERE, null, ex);
        }
        return clazz;
    }

    /**
     * get controller class of the target form class class
     *
     * @param clazz class class of the controller
     * @return the controller
     */
    private Controller getControllerClass(Class<?> clazz) {
        Controller controller = null;
        try {
            controller = (Controller) clazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException |
                InvocationTargetException | NoSuchMethodException | SecurityException ex) {
            Logger.getLogger(Router.class.getName()).log(Level.SEVERE, null, ex);
        }
        return controller;
    }

    /**
     * get wrapper class if the parameter describes primitive type
     *
     * @param className class name of target
     * @return class class of primitive, if not pmirimite returns null
     */
    private Class<?> getWrapperIfPrimitive(String className) {
        // return class of wrapper class type if primitive
        if (isTypeEquals(className, "int", Integer.class)) {
            return Integer.class;
        } else if (isTypeEquals(className, "short", Short.class)) {
            return Short.class;
        } else if (isTypeEquals(className, "long", Long.class)) {
            return Long.class;
        } else if (isTypeEquals(className, "byte", Byte.class)) {
            return Byte.class;
        } else if (isTypeEquals(className, "char", Character.class)) {
            return Character.class;
        } else if (isTypeEquals(className, "float", Float.class)) {
            return Float.class;
        } else if (isTypeEquals(className, "double", Double.class)) {
            return Double.class;
        } else if (isTypeEquals(className, "boolean", Boolean.class)) {
            return Boolean.class;
        } else if (className.equals(String.class.getName())) {
            return String.class;
        } else {
            return null;
        }
    }
    
    private boolean isTypeEquals(String className, String pClassName, Class clazz){
     return className.equals(pClassName) || className.equals(clazz.getName());
    }

    /**
     * get valueOf(Object object) or valueOf(String string) method
     *
     * @param typeClass class class of the target
     * @return valueOf(Object object) or valueOf(String string)
     */
    private Method getValueOfMethod(Class<?> typeClass) {
        try {
            if (typeClass.getName().equals("java.lang.String")) {
                // valueOf(Object object) method because String type doesn't have valueOf(String s)
                return typeClass.getMethod("valueOf", Object.class);
            } else {
                // valueOf(String string) metod sush as Integer.valueOd(string), Short.valueOf(string) etc.
                return typeClass.getMethod("valueOf", String.class);
            }
        } catch (NoSuchMethodException | SecurityException ex) {
            Logger.getLogger(Router.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * recieve user's action on GUI as Request and return Response with no
     * screen transition
     *
     * @param request request from GUI
     * @return
     */
    public Controller processNoTransitionRequest(Request request) {
        // add parameters to the path as additional parameters, and split each elements
        String[] elements = getElementsFromPath(request.getPath(), request.getParams());

        // instance controller class
        Class<?> clazz = getControllerClassClass(elements[CLASS]);
        return getControllerClass(clazz);
    }

    /**
     * cast parameters appropriately by using valueOf method just before
     * invoking pass parameters as wrapper class type with boxing if definition
     * of parameter type is primitive type
     *
     * @param controller the controller class
     * @param method method of the controller
     * @param valueOfMethods valueOf method of parameters for cast
     * @param parameters parameters of the method
     */
    private Result invokeControllerMethod(Controller controller, Method method, Object[] parameters) {
        try {
            return (Result)method.invoke(controller, parameters);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(Router.class.getName()).log(Level.SEVERE, null, ex);
            return new Result(Status.INTERNAL_ERROR);
        }
    }

    /**
     * pathParams add formParams Method
     *
     */
    private String[] addFormParameters(String[] pathParams, Map<String, Object> formParams, int gap) {
        if (formParams.size() != gap) {
            throw new RuntimeException("parameter size missmatch. " + "gap=" + gap + " .fomParams.size =" + formParams.size());
        }
        ArrayList<String> tmpList = new ArrayList<>(Arrays.asList(pathParams));
        for (Object obj : formParams.values()) {
            tmpList.add(obj.toString());
        }
        return tmpList.toArray(new String[0]);
    }

    // Debug print
    private void showParameters(String[] parameters) {
        int i = 0;
        for (String param : parameters) {
            System.err.println("params[" + i + "]=" + param);
            i++;
        }
    }

    private Bind getBindAnnotation(Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            if (annotation.annotationType() == Bind.class) {
                return (Bind) annotation;
            }
        }
        return null;
    }
    
    private boolean isValid(Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            if (annotation.annotationType() == Valid.class) {
                return true;
            }
        }
        return false;
    }
}
