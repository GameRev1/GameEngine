package Engine;

import Engine.Entity.EntityType.BaseEntity;
import Engine.Entity.Input.MouseInput;
import Engine.Entity.Input.PlayerInput;
import Engine.Graphics.Buffer;
import Engine.Graphics.RenderingEngine;
import Engine.Tiled.MapLoader;
import Engine.Networking.Client;
import Engine.Networking.SocketHandler;
import Engine.Tiled.PhysicalMap;
import java.util.HashMap;

public abstract class Game {
    public static boolean playing = true;
    private RenderingEngine engine;
    public static int netMode = 0;
    private GameTime gameTime;
    public static final HashMap<Integer, BaseEntity> ENTITY_LIST = new HashMap<>();
    
    public abstract void update();
    public abstract void drawOnBuffer(Buffer buffer);
    public abstract void initialize();
    public abstract void conclude();
    
    public Game() {
        if(netMode != 2) {
            engine = RenderingEngine.getInstance();
        }
    }
    
    public void start() {
        initialize();
        run();
        conclude();
    }
    
    public void stop() {
        playing = false;
    }
    
    private void run() {
        if(netMode != 2) {
            engine.start();   
        }
        gameTime = GameTime.getInstance();
        startServer();
        while (playing) {
            update();
            if(Game.netMode != 2) {
                drawOnBuffer(engine.getRenderingBuffer());
                engine.drawBufferOnScreen();
                MouseInput.getInstance().resetInput();
            }
            gameTime.synchronize();
        }
        engine.stop();
    }
    
    private void startServer() {
        if(netMode == 2) {
            SocketHandler.getInstance().start();
        } else if(netMode == 1) {
            Client.getInstance().start();
        }
    }
}
