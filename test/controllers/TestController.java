package controllers;

import creamy.mvc.Controller;

/**
 *
 * @author ryu
 */
public class TestController extends Controller{
    
    private String message;
    
    public void testActivity(String message, double number){
        this.message = message + " " +  Double.valueOf(number);
        System.out.println(this.message);
    }
    
    public void testContinualData(){
        getContinualData().put("testKey", "testValue");
    }
}
