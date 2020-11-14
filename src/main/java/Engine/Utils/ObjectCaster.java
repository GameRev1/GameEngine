/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Utils;

import java.awt.Rectangle;

/**
 *
 * @author 1635598
 */
public class ObjectCaster {
    public static <T extends Object> T as(Object a, Class<T> obj) {
        return obj.isInstance(a) ? obj.cast(a) : null;
    }
    
    public static Rectangle Rectangle(Vector2D position, int width, int height) {
        return new Rectangle((int)position.x, (int)position.y, width, height);
    }
}
