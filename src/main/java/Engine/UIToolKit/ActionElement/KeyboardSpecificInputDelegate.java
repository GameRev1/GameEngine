/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.UIToolKit.ActionElement;

import Engine.Entity.Input.PlayerInput;
import Engine.UIToolKit.BaseElement;
import Engine.Utils.Action;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alexandre Voghel
 */
public class KeyboardSpecificInputDelegate implements Action<Object> {
    public List<Method> keyboardActionList = new ArrayList<>(); 
    
    @Override
    public void invoke(Object[] args) {
        for(Method run : keyboardActionList) {
            try {
                run.invoke(this, args);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            }
        }
    }

    @Override
    public void add(String methodName, Class<?> original) {
        try {
            
            Method method = original.getMethod(methodName, new Class[]{BaseElement.class});
            if(method != null) {
                keyboardActionList.add(method);
            }
        } catch (NoSuchMethodException | SecurityException ex) {
            Logger.getLogger(MouseDelegate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
