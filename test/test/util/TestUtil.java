/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import static org.junit.Assert.fail;
/**
 *
 * @author ATakahashi
 */
public class TestUtil {

    
    public static <T> T invokePrivateMethod(Method method) {
        return invokePrivateMethod(null, method, null);        
    }
    
    public static <T> T invokePrivateMethod(Method method, Object[] parameters) {
        return invokePrivateMethod(null, method, parameters);        
    }
    
    public static <T> T invokePrivateMethod(Object instance, String methodName) {
        return invokePrivateMethod(instance, methodName, null);        
    }
    
    public static <T> T invokePrivateMethod(Object instance, String methodName, Object[] parameters) {
        Method method = null;
        for (Method m : instance.getClass().getDeclaredMethods()) {
            if (m.getName().equals(methodName)) {
                method = m;
            }
        }
        return invokePrivateMethod(instance, method, parameters);        
    }
   
    public static <T> T invokePrivateMethod(Object instance, Method method, Object[] parameters) {
        try{
            method.setAccessible(true);
            Object result;
            if(parameters == null || parameters.length == 0) {
                result = method.invoke(instance);
            } else { 
                result = method.invoke(instance, parameters);
            }
            return result == null ? null : (T)result;
        } catch (SecurityException | 
                 IllegalAccessException | 
                 IllegalArgumentException | 
                 InvocationTargetException e) {
            e.printStackTrace();
            fail(e.getMessage());
            return null;
        }
    }
    
}
