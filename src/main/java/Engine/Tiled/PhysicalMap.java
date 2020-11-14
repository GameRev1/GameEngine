/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Tiled;

import Engine.Entity.Collision.CollisionRegistery;
import Engine.Entity.EntityType.PlayerEntity;
import Engine.Graphics.Buffer;
import Engine.Tiled.Parser.Chunk;
import Engine.Tiled.Parser.Layer;
import Engine.Utils.Vector2D;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
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
public abstract class PhysicalMap {

    protected final HashMap<Vector2D, BufferedImage> chunk;
    protected final HashMap<Vector2D, Boolean> permissionMap;
    protected final ArrayList<Layer> layerList;
    protected final HashMap<Integer, BufferedImage> textureBuffer;
    protected final Dimension mapDimension;
    protected final ArrayList<WarpPoint> warpPoint;
    protected EventMap eventMap;
    protected BufferedImage image;
    public boolean followPlayer;
    public boolean loaded = false;
    public boolean isUnloading = false;
    public boolean fixedCamera = false;
    
    public abstract void load();
    public abstract void drawLayer(Buffer buffer);
    public abstract void postDrawLayer(Buffer buffer);
    public abstract void preGenerateMap();
    public abstract void specialUpdate(PlayerEntity player);

    

    public PhysicalMap() {
        chunk = new HashMap<>();
        permissionMap = new HashMap<>();
        layerList = new ArrayList<>();
        textureBuffer = new HashMap<>();
        mapDimension = new Dimension();
        warpPoint = new ArrayList<>();
        internalLoad();
    }

    public Dimension getMapDimension() {
        Dimension tempDimension = new Dimension(mapDimension);
        return tempDimension;
    }
    
    public void update(PlayerEntity player) {
        for(WarpPoint warp : warpPoint) {
            if(warp.getHitbox().intersects(player.getHitbox())) {
                PhysicalMap oldMap = warp.warp(player);
                if(!oldMap.equals(this)) {
                    isUnloading = true;
                    oldMap.unload();
                }
            }
        }
        specialUpdate(player);
        if(isUnloading) {
            unload();
        }
    }
    
    public int parseInt(Node nodeName) {
        return Integer.parseInt(nodeName.getTextContent());
    }

    
    
    private void parseXML() {
        try {
            File file = new File(MapLoader.TEXTURE_PATH + this.getClass().getSimpleName() + ".tmx");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = factory.newDocumentBuilder();
            Document mapFile = db.parse(file);
            parseChunkData(mapFile);
            parseTilesetData(mapFile);
            parseMapData(mapFile);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void parseMapData(Document mapFile) throws DOMException {
        NodeList map = mapFile.getElementsByTagName("map");
        for(int i = 0; i < map.getLength(); i++) {
            Node node = map.item(i);
            if(node == null) {
                continue;
            }
            int tileHeight = Integer.parseInt(node.getAttributes().getNamedItem("tileheight").getTextContent());
            int tileWidth = Integer.parseInt(node.getAttributes().getNamedItem("tilewidth").getTextContent());
            int height = Integer.parseInt(node.getAttributes().getNamedItem("height").getTextContent()) * tileHeight;
            int width = Integer.parseInt(node.getAttributes().getNamedItem("width").getTextContent())  * tileWidth;
            
            mapDimension.setSize(width, height);
        }
    }

    private void parseChunkData(Document mapFile) throws DOMException {
        NodeList map = mapFile.getElementsByTagName("layer");
        for (int i = 0; i < map.getLength(); i++) {
            Node node = map.item(i);
            String name = node.getAttributes().getNamedItem("name").getTextContent();
            Layer layer = new Layer(name);
            Node dataNode = null;
            NodeList layerNodeList = node.getChildNodes();
            dataNode = getDataNode(layerNodeList, dataNode);
            if (dataNode != null) {
                processDataNode(dataNode, layer);
            }
            this.layerList.add(layer);
        }
    }

    private Node getDataNode(NodeList layerNodeList, Node dataNode) {
        for (int j = 0; j < layerNodeList.getLength(); j++) {
            Node data = layerNodeList.item(j);
            if (!data.getNodeName().equals("data")) {
                continue;
            }
            dataNode = data;
            break;
        }
        return dataNode;
    }

    private void processDataNode(Node dataNode, Layer layer) throws DOMException {
        NodeList chunkList = dataNode.getChildNodes();
        int lowestX = 0; 
        int lowestY = 0;
        for (int j = 0; j < chunkList.getLength(); j++) {
            Node chunkNode = chunkList.item(j);
            if (!chunkNode.getNodeName().equals("chunk")) {
                continue;
            }
            NamedNodeMap chunkNodeAttribute = chunkNode.getAttributes();
            int x = parseInt(chunkNodeAttribute.getNamedItem("x"));
            int y = parseInt(chunkNodeAttribute.getNamedItem("y"));
            if(x < lowestX) {
                System.out.println("BEFORE : [x : " + x + ", lowestX : " + lowestX + "]");
                lowestX = x;
                System.out.println("AFTER  : [x : " + x + ", lowestX : " + lowestX + "]");
            }
            if(y < lowestY) {
                System.out.println("BEFORE : [y : " + y + ", lowestY : " + lowestY + "]");
                lowestY = y;
                System.out.println("AFTER  : [y : " + y + ", lowestY : " + lowestY + "]");
            }
        }
        
        for (int j = 0; j < chunkList.getLength(); j++) {
            Node chunkNode = chunkList.item(j);
            if (!chunkNode.getNodeName().equals("chunk")) {
                continue;
            }
            NamedNodeMap chunkNodeAttribute = chunkNode.getAttributes();
            int x = parseInt(chunkNodeAttribute.getNamedItem("x")) + Math.abs(lowestX);
            int y = parseInt(chunkNodeAttribute.getNamedItem("y")) + Math.abs(lowestY);
            System.out.println(new Vector2D(x, y).toString());
            int width = parseInt(chunkNodeAttribute.getNamedItem("width"));
            int height = parseInt(chunkNodeAttribute.getNamedItem("height"));
            Chunk chunkXY = new Chunk(x, y, width, height, chunkNode.getTextContent());
            layer.addChunk(chunkXY);
        }
    }

    private void parseTilesetData(Document mapFile) throws DOMException {
        NodeList map = mapFile.getElementsByTagName("tileset");
        
        for (int i = 0; i < map.getLength(); i++) {
            Node node = map.item(i);
            String source = node.getAttributes().getNamedItem("source").getTextContent();
            int firstTileID = Integer.parseInt(node.getAttributes().getNamedItem("firstgid").getTextContent());

           
            textureBuffer.putAll(MapLoader.getInstance().textureFile.get(source.replace(".tsx", ".png").toLowerCase()).prepareID(firstTileID));
        }
    }
    
    protected int getSpecificTextureID(String textureName) {
        try{
            File file = new File(MapLoader.TEXTURE_PATH + this.getClass().getSimpleName() + ".tmx");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = factory.newDocumentBuilder();
            Document mapFile = db.parse(file);
            NodeList map = mapFile.getElementsByTagName("tileset");
            for (int i = 0; i < map.getLength(); i++) {
                Node node = map.item(i);
                String source = node.getAttributes().getNamedItem("source").getTextContent();
                if(source.equals(textureName)) {
                    return Integer.parseInt(node.getAttributes().getNamedItem("firstgid").getTextContent());
                } 
            }
        }catch(Exception e) {} 
        return 0;
    }
    
    private void internalLoad() {
        parseXML();
    }
    
    public final void unload() {
        /*for(Layer chunkLayer : layerList) {
            chunkLayer.unload();
        }*/
        chunk.clear();
        layerList.clear();
        permissionMap.clear();
        textureBuffer.clear();
        warpPoint.clear();
    }   
}
