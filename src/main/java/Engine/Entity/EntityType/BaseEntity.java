/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Entity.EntityType;

import Engine.Graphics.Buffer;
import Engine.Networking.ArrayListNetwork;
import Engine.Networking.SocketHandler;
import Engine.Utils.Vector2D;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author 1635598
 */
public abstract class BaseEntity {

    
    protected Vector2D position;
    public int width;
    public int height;
    
    public abstract void draw(Buffer buffer);
    public abstract void syncData(ArrayListNetwork data);
    public abstract void receiveData(ArrayListNetwork data);
    
    public void teleport(float x, float y) {
        this.position = new Vector2D(x, y);
    }
    
    public Vector2D getPosition() {
        return new Vector2D(position.x - width / 2, position.y - height / 2);
    }
    
    public void setPosition(Vector2D newPosition) {
        position = newPosition;
    }

    public void netUpdate() {
        baseSendSync();
    }
    
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    public void setDefaultDimension(int width, int height) {
        this.width = width;
        this.height = height;
    }
    
    public Rectangle getHitbox() {
        return new Rectangle((int)position.x, (int)position.y, width, height);
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.position);
        hash = 97 * hash + this.width;
        hash = 97 * hash + this.height;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BaseEntity other = (BaseEntity) obj;
        return true;
    }
    
    private void baseSendSync() {
        ArrayListNetwork list = new ArrayListNetwork();
        list.add(position.x);
        list.add(position.y);
        syncData(list);
        SocketHandler.getInstance().sendData(list);
    }
    
    private void baseReceiveData(ArrayListNetwork list) {
        position.x = list.readFloat();
        position.y = list.readFloat();
        receiveData(list);
    }
}
