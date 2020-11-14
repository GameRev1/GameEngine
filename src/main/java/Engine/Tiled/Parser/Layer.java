/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Tiled.Parser;

import Engine.Graphics.Buffer;
import Engine.Graphics.RenderingEngine;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Alexandre Voghel
 */
public class Layer {
    public final String layerName;
    public ArrayList<Chunk> textureChunk;
    private BufferedImage layerImage;
    
    
    public Layer(String layerName) {
        this.layerName = layerName;
        textureChunk = new ArrayList<>();
    }
    
    public void addChunk(Chunk chunk) {
        System.out.println(chunk.toString());
        textureChunk.add(chunk);
    }
    
    public void constructImage(HashMap<Integer, BufferedImage> texture) {
        
        layerImage = new BufferedImage(64*32, 64*32, BufferedImage.TYPE_INT_ARGB);
        Graphics2D imageGraphics = layerImage.createGraphics();
        for(Chunk chunk : textureChunk) {
            chunk.buildChunkFromData(imageGraphics, texture);
        }
    }
    
    public void draw(Buffer buffer) {

    }

    public void generateCollision(int startingID) {
        for(Chunk chunk : textureChunk) {
            chunk.generateChunkCollision(startingID);
        }
    }
    
    public void unload() {
        for(Chunk chunk : textureChunk) {
            chunk.removeCollision();
        }
        textureChunk.clear();
    }

    @Override
    public String toString() {
        return "Layer{" + "layerName=" + layerName + ", textureChunk=" + textureChunk + ", layerImage=" + layerImage + '}';
    }
    
    
}
