/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Utils;


import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 *
 * @author 1635598
 * @param <T>
 */
public interface Action<T> {
    public final ArrayList<Method> array = new ArrayList<>();
    
    public void invoke(T[] args);
    public void add(String methodName, Class<?> original);
}
