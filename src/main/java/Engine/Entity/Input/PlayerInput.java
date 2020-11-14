/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Entity.Input;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author 1635598
 */
public class PlayerInput implements java.awt.event.KeyListener {
    private final int ESCAPE = KeyEvent.VK_ESCAPE;
    private final int ENTER = KeyEvent.VK_ENTER;
    private final int F11 = KeyEvent.VK_F;
    private final int SPACE = KeyEvent.VK_SPACE;
    
    private int[] basicControl = {KeyEvent.VK_DOWN, KeyEvent.VK_UP, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT};
    private final HashMap<Integer, Boolean> keyValidation;
    private ArrayList<KeyCombination> combination;
    
    
    private static PlayerInput instance;
    
    public static PlayerInput getInstance() {
        if(instance == null) {
            instance = new PlayerInput();
        }
        return instance;
    }
    
    private PlayerInput() {
        keyValidation = new HashMap<>();
        combination = new ArrayList<>();
        registerDefaultKey();
        registerKeyFunction();
    }

    private void registerKeyFunction() {
        combination.add(new KeyCombination("Down", basicControl[0]));
        combination.add(new KeyCombination("Up", basicControl[1]));
        combination.add(new KeyCombination("Left", basicControl[2]));
        combination.add(new KeyCombination("Right", basicControl[3]));
        combination.add(new KeyCombination("Exit", ESCAPE));
        combination.add(new KeyCombination("Fullscreen", F11));
    }

    private void registerDefaultKey() {  
        keyValidation.put(basicControl[0], false);
        keyValidation.put(basicControl[1], false);
        keyValidation.put(basicControl[2], false);
        keyValidation.put(basicControl[3], false);
        keyValidation.put(ESCAPE, false);
        keyValidation.put(F11, false);
        keyValidation.put(SPACE, false);
        keyValidation.put(ENTER, false);
    }
    
    public ArrayList<KeyCombination> getKeyList() {
        return (ArrayList<KeyCombination>) combination.clone();
    }
    
    public boolean getKeyState(char c) {
        int key = KeyEvent.getExtendedKeyCodeForChar(c);
        if(keyValidation.containsKey(key)) {
            return keyValidation.get(key);
        }
        return false;
    }
    
    public boolean getKeyState(int key) {
        if(keyValidation.containsKey(key)) {
            return keyValidation.get(key);
        }
        return false;
    }
    
    public void addInput(int key, String description) {
        keyValidation.put(key, false);
        combination.add(new KeyCombination(description, key));
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(keyValidation.containsKey(e.getKeyCode())) {
            keyValidation.replace(e.getKeyCode(), true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(keyValidation.containsKey(e.getKeyCode())) {
            keyValidation.replace(e.getKeyCode(), false);
        }
    }
}
