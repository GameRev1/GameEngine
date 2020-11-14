/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Entity.Collision;

import Engine.Entity.EntityType.BaseEntity;
import Engine.Entity.EntityType.MovableEntity;
import Engine.Entity.Input.Direction;
import java.awt.Rectangle;

/**
 *
 * @author Alexandre Voghel
 */
public class Collision {
        private final MovableEntity entity;
    
    public Collision(MovableEntity entity) {
        this.entity = entity;
    } 
    
    public int getAllowedSpeed(Direction direction) {
        switch (direction) {
            case UP: return getAllowedUpSpeed();
            case DOWN: return getAllowedDownSpeed();
            case LEFT: return getAllowedLeftSpeed();
            case RIGHT: return getAllowedRightSpeed();
        }
        return 0;
    }
    
    private int getAllowedUpSpeed() {
        return distance((BaseEntity other) -> (int) (entity.getPosition().y - (other.getPosition().y + other.height)));
    }
    
    private int getAllowedDownSpeed() {
        return distance((BaseEntity other) -> (int) (other.getPosition().y - (entity.getPosition().y + entity.height)));
    }
    
    private int getAllowedLeftSpeed() {
        return distance((BaseEntity other) -> (int) (entity.getPosition().x - (other.getPosition().x + other.width)));
    }
    
    private int getAllowedRightSpeed() {
        return distance((BaseEntity other) -> (int) (other.getPosition().x - (entity.getPosition().x + entity.width)));
    }
    
    private int distance(AllowedDistanceCalculator calculator) {
        Rectangle collisionBound = entity.getHitbox();
        int allowedDistance = (int) entity.getVelocity().x;
        for (BaseEntity other : CollisionRegistery.getInstance()) {
            if (collisionBound.intersects(other.getHitbox())) {
                allowedDistance = Math.min(allowedDistance, 
                        calculator.calculateWith(other));
            }
        }
        return allowedDistance;
    }
    
    private interface AllowedDistanceCalculator {
        public int calculateWith(BaseEntity other);
    }
}
