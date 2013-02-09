/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package creamy.mvc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ATakahashi
 */
public class MvcUtil {
    
    
    /**
     * MapのKeyとClassインスタンスのSetterの名前を突き合わせて値をセットし、
     * 値をセット済みのclsのインスタンスを返す
     * @param <T>
     * @param map
     * @param cls
     * @return 
     */
    static public <T> BindingResult bindMapModel(Map<String, Object> map, T obj) {
        try{ 
            Class<? extends Object> cls =  obj.getClass();
            for (Method m : cls.getMethods()) {
                if(m.getName().indexOf("set") == 0){
                    String key = m.getName().replace("set", "").toLowerCase();
                    if (! map.containsKey(key)) continue;
                    
                    Class[] types = m.getParameterTypes();
                    if (types.length != 1) continue; 
                    if (map.get(key) == null) continue;

                    //セッターの引数の型を変更する
                    Object value;
                    if (!types[0].equals(map.get(key).getClass())) {
                        value = getWrapper(types[0].getName(), map.get(key));
                    } else {
                        value = map.get(key);
                    }
                    m.invoke(obj, new Object[]{value});
                }
            }
            for (String key : map.keySet()){
                if(key.indexOf(".") >= 1) {
                    String[] sepKey = key.split("\\.");
                    String name = sepKey[0];
                    String fieldName = sepKey[1];
                    Method setter = getMethodIgnoreCase(cls, "set" + name);
                    Class rType = setter.getParameterTypes()[0];
                    
                    Method rSetter = getMethodIgnoreCase(rType, "set" + fieldName);
                    Object rObj = rType.newInstance(); 
                    rSetter.invoke(rObj, new Object[]{map.get(key)});
                    
                    setter.invoke(obj, rObj);
                }
            }
            
            return new BindingResult(false, null);
        } catch (InstantiationException |
                 IllegalAccessException |
                 InvocationTargetException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            return new BindingResult(true, ex.getMessage());
        }
    }
    
        private static Object getWrapper(String className, Object value) {
        // return class of wrapper class type if primitive
        if (isTypeEquals(className, "int", Integer.class)) {
            return Integer.valueOf((String)value);
        } else if (isTypeEquals(className, "short", Short.class)) {
            return Short.valueOf((String)value);
        } else if (isTypeEquals(className, "long", Long.class)) {
            return Long.valueOf((String)value);
        } else if (isTypeEquals(className, "byte", Byte.class)) {
            return Byte.valueOf((String)value);
        //} else if (isTypeEquals(className, "char", Character.class)) {
            //return Character.valueOf((String)value);
        } else if (isTypeEquals(className, "float", Float.class)) {
            return Float.valueOf((String)value);
        } else if (isTypeEquals(className, "double", Double.class)) {
            return Double.valueOf((String)value);
        } else if (isTypeEquals(className, "boolean", Boolean.class)) {
            return Boolean.valueOf((String)value);
        } else if (className.equals(String.class.getName())) {
            return String.valueOf((String)value);
        } else {
            return null;
        }
    }
    
    private static boolean isTypeEquals(String className, String pClassName, Class clazz){
        return className.equals(pClassName) || className.equals(clazz.getName());
    }
        
    private static Method getMethodIgnoreCase(Class cls, String name) {
        for (Method m : cls.getMethods()) {
            if (m.getName().equalsIgnoreCase(name)) return m;
        }
        return null;
    }
    
}
