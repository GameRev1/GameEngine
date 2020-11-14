/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Entity.Input;

/**
 *
 * @author 1635598
 */
public enum Direction {
    LEFT(-1, 0), RIGHT(1, 0), UP(0, -1), DOWN(0, 1);
    
    private final int xMultiplier;
    private final int yMultiplier;
    Direction(int xMultiplier, int yMultiplier) {
        this.xMultiplier = xMultiplier;
        this.yMultiplier = yMultiplier;
    }
    
    
    public float getVelocityX(float speedX) {
        return xMultiplier * speedX;
    }
    
    public float getVelocityY(float speedY) {
        return yMultiplier * speedY;
    }
}
