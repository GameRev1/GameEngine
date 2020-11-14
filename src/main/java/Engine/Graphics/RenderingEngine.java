package Engine.Graphics;

import Engine.Entity.Input.MouseInput;
import Engine.Entity.Input.PlayerInput;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

public final class RenderingEngine {
    
    private final Screen screen;
    private JPanel panel;
    private BufferedImage bufferedImage;
    private boolean fullscreen = false;
    
    private static RenderingEngine instance;
    
    private int width = 800;
    private int height = 600;
    private String title = " --- Dradon engine : the return --- "; 
    
    public static RenderingEngine getInstance() {
        if(instance == null) {
            instance = new RenderingEngine();
        }
        return instance;
    }
    
    private RenderingEngine() {    
        screen = new Screen();
    }
    
    public Screen getScreen() {
        return screen;
    } 
    
    public boolean isFullScreen() {
        return fullscreen;
    }
    
    public void start() {
        initPanel();
        screen.start();
    }
    
    public void stop() {
        screen.end();
    }
    
    public void hide() {
        screen.end();
    }
    
    private void initPanel() {
        panel = new JPanel();
        panel.setBackground(Color.black);
        panel.setFocusable(true);
        panel.setDoubleBuffered(true);
        panel.addKeyListener(PlayerInput.getInstance());
        panel.addMouseListener(MouseInput.getInstance());
        screen.setPanel(panel);
        
    }
    
    public void drawBufferOnScreen() {
        Graphics2D graphics = (Graphics2D) panel.getGraphics();
        graphics.drawImage(bufferedImage, 0, 0, panel);
        Toolkit.getDefaultToolkit().sync();
        graphics.dispose();
    }
    
    public Buffer getRenderingBuffer() {
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        Graphics2D graphics = bufferedImage.createGraphics();
        graphics.setRenderingHints(getOptimalRenderingHint());
        return new Buffer(graphics);
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public void setViewport(int width, int height) {
        this.width = width;
        this.height = height;
        
    }

    public int getViewportWidth() {
        return width;
    }

    public int getViewportHeight() {
        return height;
    }
    
    public Dimension getScreenDimension() {
        return new Dimension(width, height);
    }
    
    public Toolkit getToolkit() {
        return screen.getToolkit();
    }
    
    private RenderingHints getOptimalRenderingHint() {
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        return rh;
    }
    
}
