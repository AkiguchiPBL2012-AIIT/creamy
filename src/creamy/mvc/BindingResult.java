/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package creamy.mvc;

/**
 *
 * @author ATakahashi
 */
public class BindingResult {
    private boolean _isError;
    private String _message;
    
    protected BindingResult(boolean isError, String message){
        _isError = isError;
        _message = message;
    }
    
    public boolean isError() {
        return _isError;
    }
    
    public String getMessage() {
        return _message;
    }
    
}
