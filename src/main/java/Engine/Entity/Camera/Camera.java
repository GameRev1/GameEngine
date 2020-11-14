/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Entity.Camera;

import Engine.Entity.EntityType.PlayerEntity;
import Engine.Tiled.PhysicalMap;
import Engine.Utils.Vector2D;

/**
 *
 * @author 1635598
 */
public abstract  class Camera {
    protected final PlayerEntity player;
    protected Vector2D position;
    
    public abstract void update();
    
    public Camera(PlayerEntity player) {
        this.player = player;
        position = player.getPosition();
    }
    
    public int getPositionX() {
        return (int)position.x;
    }
    
    public int getPositionY() {
        return (int)position.y;
    }
}
