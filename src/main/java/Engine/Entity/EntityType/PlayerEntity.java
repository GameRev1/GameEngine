/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Entity.EntityType;

import Engine.Entity.Camera.Camera;
import Engine.Entity.Input.MouseInput;
import Engine.Entity.Input.PlayerInput;

/**
 *
 * @author 1635598
 */
public abstract class PlayerEntity extends MovableEntity {
    protected abstract void movePlayer();
    
    
    protected PlayerInput keyboardInput = PlayerInput.getInstance();
    protected MouseInput mouseHandler = MouseInput.getInstance();
    
    public Camera camera;
}
