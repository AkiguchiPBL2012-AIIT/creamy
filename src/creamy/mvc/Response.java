package creamy.mvc;

import creamy.activity.Activity;
import creamy.validation.ValidationResult;

public class Response {
    private Status status;
    private String path;
    private String redirectPath;
    private Activity activity ;
    private Object data;
    private ValidationResult validationResult;

    public Response() {}

    public Response(Status status, String path) {
        this.path = path;
        this.status = status;
    }
    
    public Response(Status status, String path, Activity activity) {
        this(status, path);
        this.activity = activity;
    }
    
    public Response(Status status, String path, Object data) {
        this(status, path);
        this.data = data;
    }
    
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getRedirectPath() {
        return redirectPath;
    }

    public void setRedirectPath(String redirectPath) {
        this.redirectPath = redirectPath;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
    
    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
    
    public ValidationResult getValidationResult() {
        return this.validationResult;
    }
    
    void setValidationResult(ValidationResult validationResult) {
        this.validationResult = validationResult;
    }
}
