/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Tiled;

import Engine.Entity.EntityType.PlayerEntity;
import Engine.Utils.Action;
import Engine.Utils.Vector2D;
import java.util.HashMap;

/**
 *
 * @author Alexandre Voghel
 */
public abstract class EventMap {
    protected final HashMap<Vector2D, Action<Object>> eventMap;

    public abstract void load(); 
    
    public EventMap() {
        eventMap = new HashMap<>();
    }
    
    public final void checkIfPlayerPositionIsInAnEventCase(PlayerEntity player) {
        Vector2D realPlayerPosition = Vector2D.Divide(player.getPosition(), new Vector2D(16, 16));
        if(eventMap.containsKey(realPlayerPosition)) {
            eventMap.get(realPlayerPosition).invoke(new Object[] {realPlayerPosition});
        }
    }
}
