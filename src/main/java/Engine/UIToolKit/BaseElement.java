package Engine.UIToolKit;

import Engine.Entity.Input.MouseInput;
import Engine.Entity.Input.PlayerInput;
import Engine.Graphics.Buffer;
import Engine.UIToolKit.ActionElement.KeyboardDelegate;
import Engine.UIToolKit.ActionElement.KeyboardSpecificInputDelegate;
import Engine.UIToolKit.ActionElement.MouseDelegate;
import Engine.Utils.Action;
import Engine.Utils.ObjectCaster;
import Engine.Utils.Vector2D;
import com.sun.javafx.embed.AbstractEvents;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;

public abstract class BaseElement {
    private ArrayList<BaseElement> elements = new ArrayList<>();
    public final MouseDelegate leftClickEvent = new MouseDelegate();
    public final MouseDelegate rightClickEvent = new MouseDelegate();
    public final KeyboardSpecificInputDelegate enterEvent = new KeyboardSpecificInputDelegate();
    public final KeyboardSpecificInputDelegate spaceEvent = new KeyboardSpecificInputDelegate();
    public final List<Method> keyboardEvent = new ArrayList<>();
    protected Dimension dimension = new Dimension();
    protected Vector2D position = Vector2D.ZERO;
    
    protected abstract void DrawSelf(Buffer buffer);
    protected abstract void onInitialize();
    
    
    public BaseElement() {
        onInitialize();
    }
    
    public void setPosition(Vector2D position) {
        this.position = position;
    } 
    
    public void Draw(Buffer buffer) {
        DrawSelf(buffer);
        drawChildren(buffer);
    }
    
    public final void append(BaseElement element) {
        elements.add(element);
    }
    
    public void update() {
    }
    
    public boolean isMouseHovering() {
        Rectangle rectangle = new Rectangle(new Point((int)position.x, (int)position.y), dimension);
        return rectangle.contains(MouseInput.getInstance().getMousePosition());
    }
    
    protected void drawChildren(Buffer buffer) {
        for(BaseElement element : elements) {
            element.Draw(buffer);
        }
    }
    
    public final void executeLeftMouseClick(MouseInput input) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        for(Method method : leftClickEvent.mouseEvent) {
            if(isMouseHovering()) {
                method.invoke(this, new Object[]{ObjectCaster.as(this, BaseElement.class)});
            }
        }
        for(BaseElement element : elements) {
            element.executeLeftMouseClick(input);
        }
        
    }
    
    public final void executeRightMouseClick(MouseInput input) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        for(Method method : rightClickEvent.mouseEvent) {
            method.invoke(this, new Object[]{ObjectCaster.as(this, BaseElement.class)});
        }
        for(BaseElement element : elements) {
            element.executeRightMouseClick(input);
        }
    }
    
    public final void executeSpacebarEvent() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        for(Method method : spaceEvent.keyboardActionList) {
            method.invoke(this, new Object[]{this});
        }
        for(BaseElement element : elements) {
            element.spaceEvent.invoke(new Object[]{});
        }
    }
    
    public final void executeEnterEvent() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        for(Method method : enterEvent.keyboardActionList) {
            method.invoke(this, new Object[]{this});
        }
        for(BaseElement element : elements) {
            element.enterEvent.invoke(new Object[]{this});
        }
    }
}
