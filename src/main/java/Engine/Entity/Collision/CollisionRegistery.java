/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Entity.Collision;

import Engine.Entity.EntityType.BaseEntity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Alexandre Voghel
 */
public class CollisionRegistery implements Iterable<BaseEntity>{

    private static CollisionRegistery instance;
    private static ArrayList<BaseEntity> registeredCollidableEntities;
   
    public static CollisionRegistery getInstance() {
        if (instance == null) {
            instance = new CollisionRegistery();
        }
        return instance;
    }
    
    public static CollisionRegistery getInstance(boolean clear) {
        if (instance == null || clear) {
            instance = new CollisionRegistery();
            registeredCollidableEntities.clear();
            System.out.println("======== CLEAR =======");
        }
        return instance;
    }
    
    public void registerEntity(BaseEntity entity) {
        registeredCollidableEntities.add(entity);
    }
    
    public void registerEntities(Collection<BaseEntity> entities) {
        registeredCollidableEntities.addAll(entities);
    }
    
    public void unregisterEntity(BaseEntity entity) {
        registeredCollidableEntities.remove(entity);
    }
    
    public int count() {
        return registeredCollidableEntities.size();
    }
    
    public void clearList() {
        System.out.println(registeredCollidableEntities.removeAll(registeredCollidableEntities));
        System.out.println("============= CLEAR RESULT ==============");
    }
    

    @Override
    public Iterator<BaseEntity> iterator() {
        return registeredCollidableEntities.iterator();
    }
    
    private CollisionRegistery() {
        registeredCollidableEntities = new ArrayList<>();
    }
    
}
