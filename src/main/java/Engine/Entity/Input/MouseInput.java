/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Entity.Input;

import Engine.Graphics.RenderingEngine;
import Engine.Graphics.Screen;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;

/**
 *
 * @author 1635598
 */
public class MouseInput implements java.awt.event.MouseListener {
    
    private int[] basicControl = {MouseEvent.BUTTON1, MouseEvent.BUTTON3};
    private HashMap<Integer, Boolean> keyValidation;
    
    private Point mousePosition;
    
    
    private static MouseInput instance;
    
    public static MouseInput getInstance() {
        if(instance == null) {
            instance = new MouseInput();
        }
        return instance;
    }
    
    private MouseInput() {
        keyValidation = new HashMap<>();
        keyValidation.put(basicControl[0], false);
        keyValidation.put(basicControl[1], false);
    }
    
    public boolean getKeyState(int key) {
        if(keyValidation.containsKey(key)) {
            return keyValidation.get(key);
        }
        return false;
    }
    
    public void resetInput() {
        keyValidation.replaceAll((v, k) -> false);
    }
    
    public Point getMousePosition() {
        Screen screen = RenderingEngine.getInstance().getScreen();
        Point p = MouseInfo.getPointerInfo().getLocation();;
        return new Point(p.x - screen.getFramePosition().x, p.y - screen.getFramePosition().y);
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if(keyValidation.containsKey(e.getButton())) {
            keyValidation.replace(e.getButton(), true);
        }
        mousePosition = new Point(e.getX(), e.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(keyValidation.containsKey(e.getButton())) {
            keyValidation.replace(e.getButton(), false);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
       
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
    
}
