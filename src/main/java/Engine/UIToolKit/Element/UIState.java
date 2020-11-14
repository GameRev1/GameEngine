/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.UIToolKit.Element;

import Engine.UIToolKit.BaseElement;
import Engine.UIToolKit.UserInterface;

/**
 *
 * @author 1635598
 */
public abstract class UIState extends BaseElement {
    private boolean isVisible;
    private UserInterface currentlyLinkedInterface;
    
    public void setInterface(UserInterface currentlyLinkedInterface) {
        this.currentlyLinkedInterface = currentlyLinkedInterface;
    }
    
    public boolean isVisible() {
        return isVisible;
    }
    
    public void setVisible() {
        isVisible = !isVisible;
    }
}
