/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Tiled;

import Engine.Tiled.Parser.Chunk;
import Engine.Tiled.Parser.Layer;
import Engine.Tiled.Parser.Texture;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Alexandre Voghel
 */
public class MapLoader {
    public HashMap<String, Texture> textureFile;
    public static final String TEXTURE_PATH = "./assets/Maps/";
    private static MapLoader instance;

    public static MapLoader getInstance() {
        if(instance == null) {
            instance = new MapLoader();
        }
        return instance;
    }
    
    private MapLoader() {
        textureFile = new HashMap<>();
        loadTexture();
    }
    
    private void loadTexture() {
        try{
            File mapFile = new File(TEXTURE_PATH);
            File[] mapTextures = mapFile.listFiles();
            for(File f : mapTextures) {
                if(f.getName().endsWith(".tsx")) {
                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder db = factory.newDocumentBuilder();
                    Document mapFileTSX = db.parse(f);
                    parseTSXFile(mapFileTSX);
                }
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }    
    
    private void parseTSXFile(Document mapFile) throws DOMException, IOException {
        NodeList tileset = mapFile.getElementsByTagName("tileset");
        NodeList image = mapFile.getElementsByTagName("image");
        
        String source = "";
        int tileWidth = 0, tileHeight = 0, column = 0;
        
        for (int i = 0; i < tileset.getLength(); i++) {
            Node node = tileset.item(i);
            if(node == null) {
                continue;
            }
            tileWidth = Integer.parseInt(node.getAttributes().getNamedItem("tilewidth").getTextContent());
            tileHeight = Integer.parseInt(node.getAttributes().getNamedItem("tileheight").getTextContent());
        }
        
        for (int i = 0; i < image.getLength(); i++) {
            Node node = image.item(i);
            if(node == null) {
                continue;
            }
            source = node.getAttributes().getNamedItem("source").getTextContent();
        }
        System.out.println(source);
        textureFile.put(source.toLowerCase(), new Texture(ImageIO.read(new File(TEXTURE_PATH + source)), column, tileWidth, tileHeight, source));
    }
}
