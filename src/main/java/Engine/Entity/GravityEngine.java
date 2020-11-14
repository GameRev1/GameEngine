/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Entity;

import Engine.Entity.EntityType.BaseEntity;
import static Engine.Utils.ObjectCaster.as;
/**
 *
 * @author 1635598
 */
public class GravityEngine {
    private static GravityEngine instance;
    
    public static GravityEngine getInstance() {
        if(instance == null) {instance = new GravityEngine();}
        return instance;
    } 
    
    private GravityEngine() {
        
    }
    
    public void processGravity(BaseEntity entity, boolean collisionDetected) {
        
        
        processVelocity(entity);
    }
    
    private void processVelocity(BaseEntity entity) {
        
    }
    
    private void invertGravity(BaseEntity entity) {
        
    }
    
    private void correctPosition(BaseEntity entity) {
        
    }
}
