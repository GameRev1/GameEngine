/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Tiled;

import Engine.Entity.Collision.CollisionRegistery;
import Engine.Entity.EntityType.BaseEntity;
import Engine.Entity.EntityType.PlayerEntity;
import Engine.Graphics.Buffer;
import Engine.Networking.ArrayListNetwork;
import Engine.Utils.ObjectCaster;
import Engine.Utils.Vector2D;
import java.awt.Color;
import java.awt.Rectangle;

/**
 *
 * @author Alexandre Voghel
 */
public class WarpPoint extends BaseEntity {
    private Vector2D position;
    private Vector2D positionToWarp;
    private int tileWidth;
    private int tileHeight;
    private PhysicalMap map;
    
    public WarpPoint(Vector2D position, Vector2D positionToWarp, int tileWidth, int tileHeight, PhysicalMap referenceMap) {
        this.position = position.Multiply(32);
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.map = referenceMap;
        this.positionToWarp = positionToWarp.Multiply(32);
    }
    
    /* NEED TO BE ADAPTED */
    public PhysicalMap warp(PlayerEntity player) {
        //PhysicalMap previousMap = LuigiMansion.getInstance().currentlyLoadedMap;
        if(map != null) {
            CollisionRegistery.getInstance().clearList();
            if(!map.loaded) {
                map.load();
            }
            //LuigiMansion.getInstance().currentlyLoadedMap = map;
        }       
        player.setPosition(positionToWarp);
        return null;
    }
    
    @Override
    public Rectangle getHitbox() {
        return ObjectCaster.Rectangle(position, tileWidth * 32, tileHeight * 32);
    }

    @Override
    public void draw(Buffer buffer) {
        System.out.println("draw");
        buffer.drawRectangle(position, tileWidth * 32, tileHeight * 32, Color.red);
    }

    @Override
    public void syncData(ArrayListNetwork data) {
        
    }

    @Override
    public void receiveData(ArrayListNetwork data) {
        
    }
            
}
