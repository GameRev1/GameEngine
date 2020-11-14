/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Entity.EntityType;

import Engine.Graphics.Buffer;
import Engine.Networking.ArrayListNetwork;
import java.util.Objects;

/**
 *
 * @author Alexandre Voghel
 */
public class TileEntity extends BaseEntity {

    public TileEntity(int x, int y, int width, int height) {
        super.setDefaultDimension(width, height);
        super.teleport(x, y);
    }
    
    @Override
    public void draw(Buffer buffer) {
        
    }

    @Override
    public void syncData(ArrayListNetwork data) {
        
    }

    @Override
    public void receiveData(ArrayListNetwork data) {
        
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.position);
        hash = 97 * hash + this.width;
        hash = 97 * hash + this.height;
        return hash;
    }
    
}
