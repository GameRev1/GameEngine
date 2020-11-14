/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Entity.EntityType;

import Engine.Entity.Collision.Collision;
import Engine.Entity.Input.Direction;
import Engine.Networking.ArrayListNetwork;
import Engine.Networking.SocketHandler;
import Engine.Utils.Vector2D;
import java.awt.Rectangle;

/**
 *
 * @author 1635598
 */
public abstract class MovableEntity extends BaseEntity {
    protected Vector2D velocity;
    private Direction direction = Direction.UP;
    public boolean gravityAffected;
    public boolean bounce;
    public float bouncingYGravity;
    public boolean collided;
    private final Collision collision;
    public boolean isMoving;
    
    public MovableEntity() {
        cutSprite();
        this.collision = new Collision(this);
    }
    
    public abstract void update();
    public abstract Rectangle getBound();
    public abstract void cutSprite();
    
    public Vector2D getVelocity() {
        return velocity;
    }
    
    public void move(Direction direction) {
        this.direction = direction;
        int allowedSpeed = collision.getAllowedSpeed(direction);
        position.x += direction.getVelocityX(allowedSpeed);
        position.y += direction.getVelocityY(allowedSpeed);
        isMoving = true;
    }
    
    public void moveLeft() {
        move(Direction.LEFT);
    }
    
    public void moveRight() {
        move(Direction.RIGHT);
    }
    
    public void moveUp() {
        move(Direction.UP);
    }
    
    public void moveDown() {
        move(Direction.DOWN);
    }
    
    public Direction getDirection() {
        return direction;
    }
    
    protected boolean collisionBoundIntersectWith(BaseEntity other) {
        if (other == null) {
            return false;
        }
        return getCollisionBound().intersects(other.getHitbox());
    }
    
    protected Rectangle getCollisionBound() {
        switch (direction) {
            case UP: return getCollisionUpperBound();
            case DOWN: return getCollisionLowerBound();
            case LEFT: return getCollisionLeftBound();
            case RIGHT: return getCollisionRightBound();
            default: return getHitbox();
        }
    }
    
    private Rectangle getCollisionUpperBound() {
        return new Rectangle((int)position.x, (int)position.y - 1, width, (int) velocity.y);
    }
    
    private Rectangle getCollisionLowerBound() {
        return new Rectangle((int)position.x, (int)position.y + height, width, (int) velocity.y);
    }
    
    private Rectangle getCollisionLeftBound() {
        return new Rectangle((int)position.x - 1, (int)position.y, (int) velocity.x, height);
    }
    
    private Rectangle getCollisionRightBound() {
        return new Rectangle((int)position.x + width, (int)position.y, (int) velocity.x, height);
    }
    
    
    @Override
    public void netUpdate() {
        baseSendSync();
        update();
    }
    
    
    
    private void baseSendSync() {
        ArrayListNetwork list = new ArrayListNetwork();
        list.add(position.x);
        list.add(position.y);
        list.add(position.x);
        list.add(position.y);
        syncData(list);
        SocketHandler.getInstance().sendData(list);
    }
    
    private void baseReceiveData(ArrayListNetwork list) {
        position.x = list.readFloat();
        position.y = list.readFloat();
        velocity.x = list.readFloat();
        velocity.y = list.readFloat();
        receiveData(list);
    }
}
