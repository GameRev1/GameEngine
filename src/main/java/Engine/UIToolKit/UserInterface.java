/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.UIToolKit;

import Engine.Entity.Input.MouseInput;
import Engine.Entity.Input.PlayerInput;
import Engine.Graphics.Buffer;
import Engine.Graphics.RenderingEngine;
import Engine.UIToolKit.Element.UIState;
import Engine.Utils.Action;
import com.sun.glass.events.KeyEvent;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.event.MouseEvent;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author 1635598
 */
public class UserInterface {

    private UIState currentState;
    private MouseInput mouse;
    private PlayerInput input;
    private JPanel parent;

    public UserInterface() {
        try {
            Field f = RenderingEngine.class.getDeclaredField("panel");
            f.setAccessible(true);
            parent = (JPanel) f.get(RenderingEngine.getInstance());
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(UserInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
        mouse = MouseInput.getInstance();
        input = PlayerInput.getInstance();
    }
    
    public void setUIState(UIState state) {
        this.currentState = state;
        currentState.setInterface(this);
    }

    public void update() {
        try {
            if (currentState.isVisible()) {
                currentState.update();
                if (mouse.getKeyState(MouseEvent.BUTTON1)) {
                    currentState.executeLeftMouseClick(mouse);
                }
                if (mouse.getKeyState(MouseEvent.BUTTON3)) {
                    currentState.executeRightMouseClick(mouse);
                }
                if (PlayerInput.getInstance().getKeyState(KeyEvent.VK_SPACE)) {
                    currentState.executeSpacebarEvent();
                }
                if (PlayerInput.getInstance().getKeyState(KeyEvent.VK_ENTER)) {
                    currentState.executeEnterEvent();
                }
            }
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(UserInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void draw(Buffer buffer) {
        if (currentState != null && currentState.isVisible()) {
            currentState.Draw(buffer);
        }
    }
    
    public void setVisible() {
        currentState.setVisible();
    }
    
    public boolean isVisible() {
        return currentState.isVisible();
    }

}
