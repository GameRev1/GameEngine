/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.UIToolKit.Element;

import Engine.Entity.Input.KeyCombination;
import Engine.Graphics.Buffer;
import Engine.UIToolKit.BaseElement;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;

/**
 *
 * @author 1635598
 */
public class UIControlConfig extends BaseElement {

    private final KeyCombination controlName;
    
    public UIControlConfig(KeyCombination controlName) {
        this.controlName = controlName;
    }
    
    @Override
    protected void DrawSelf(Buffer buffer) {
        buffer.drawRectangle((int) position.x, (int)position.y, dimension.width, dimension.height, Color.BLACK);
        buffer.drawText(controlName.getKeyName() + " : " + KeyEvent.getKeyText(controlName.getKeyCode()), (int) position.x + 5, (int)position.y + 12, Color.white);
    }

    @Override
    protected void onInitialize() {
        dimension.width = 200;
        dimension.height = 20;
    }
    
    
}