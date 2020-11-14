/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Tiled.Parser;

import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 *
 * @author Alexandre Voghel
 */
public class Texture {

    private final BufferedImage texture;
    private final int column;
    private final int width;
    private final int height;
    private final int tileWidth;
    private final int tileHeight;
    private String source;

    public HashMap<Integer, BufferedImage> prepareID(int startingID) {
        HashMap<Integer, BufferedImage> mapTexture = new HashMap<>();

        for (int j = 0; j < height / tileHeight; j++) {
            for (int i = 0; i < width / tileWidth; i++) {
                mapTexture.put(startingID, texture.getSubimage(i * tileWidth, j * tileHeight, tileWidth, tileHeight));
                startingID++;
            }
        }
        return mapTexture;
    }

    public Texture(BufferedImage texture, int column, int tileWidth, int tileHeight, String source) {
        this.texture = texture;
        this.column = column;
        this.height = texture.getHeight();
        this.width = texture.getWidth();
        this.tileHeight = tileHeight;
        this.tileWidth = tileWidth;
        this.source = source;
    }
}
