/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Tiled.Parser;

import Engine.Entity.Collision.CollisionRegistery;
import Engine.Entity.EntityType.BaseEntity;
import Engine.Entity.EntityType.TileEntity;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Alexandre Voghel
 */
public final class Chunk {
    private int x;
    private int y;
    private int width;
    private int height;
    private int[][] tileData;
    private ArrayList<BaseEntity> collision;
    
    public Chunk(int x, int y, int width, int height, String data) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        tileData = new int[width][height];
        collision = new ArrayList<>();
        setData(data);
    }
    
    public void buildChunkFromData(Graphics2D graphics, HashMap<Integer, BufferedImage> texture) {
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                graphics.drawImage(texture.get(tileData[i][j]), x * 2 * width + (i * 32), y * 2 * height + (j * 32), null);
            }
        }
    }
    
    private void setData(String data) {
        data = data.replace("\n", "");
        String[] encodedData = data.split(",");
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                try {tileData[i][j] = Integer.parseInt(encodedData[i + j * width]);} catch(NumberFormatException idc) {}
                
            }
        }
    }

    void generateChunkCollision(int startingID) {
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                if(tileData[i][j] == startingID) {
                    TileEntity te = new TileEntity(x * 2 * width + (i * 32), y * 2 * height + (j * 32), 32, 32);
                    CollisionRegistery.getInstance().registerEntity(te);
                    collision.add(te);
                }
                //graphics.drawImage(texture.get(tileData[i][j]), x * 2 * width + (i * 32), y * 2 * height + (j * 32), null);
            }
        }
    }
    
    public void removeCollision() {
        for(BaseEntity entity : collision) {
            CollisionRegistery.getInstance().unregisterEntity(entity);
        }
        collision.clear();
    }

    @Override
    public String toString() {
        return "Chunk{" + "x=" + x + ", y=" + y + ", width=" + width + ", height=" + height + '}';
    }
    
    
}
