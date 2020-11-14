/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Entity.Input;

/**
 *
 * @author 1635598
 */
public class KeyCombination {
    private final String keyName;
    private int key;
    
    public KeyCombination(String keyName, int key) {
        this.keyName = keyName;
        this.key = key;
    }
    
    public void setKey(int key) {
        this.key = key;
    }
    
    public String getKeyName() {
        return keyName;
    }
    
    public int getKeyCode() {
        return key;
    }
}
