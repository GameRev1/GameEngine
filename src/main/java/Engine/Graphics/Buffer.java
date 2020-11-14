package Engine.Graphics;

import Engine.Utils.Vector2D;
import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public final class Buffer {
    private final Graphics2D graphics;
    
    public Buffer(Graphics2D graphics) {
        this.graphics = graphics;
    }
    
    public Graphics2D getGraphicsBuffer() {
        return graphics;
    }
    
    public void drawRectangle(int x, int y, int width, int height, Paint paint) {
        graphics.setPaint(paint);
        graphics.fillRect(x, y, width, height);
    }
     
    public void drawRectangle(Vector2D vector, int width, int height, Paint paint) {
        drawRectangle((int)vector.x, (int)vector.y, width, height, paint);
    }
    
    public void drawCircle(int x, int y, int radius, Paint paint) {
        graphics.setPaint(paint);
        graphics.fillOval(x, y, radius * 2, radius * 2);
    }
    
    public void drawCircle(Vector2D vector, int radius, Paint paint) {
        drawCircle((int)vector.x, (int)vector.y, radius, paint);
    }
    
    public void drawText(String text, int x, int y, Paint paint) {
        graphics.setPaint(paint);
        graphics.drawString(text, x, y);
    }
    
    public void drawText(String text, Vector2D vector, Paint paint) {
        drawText(text, (int)vector.x, (int)vector.y, paint);
    }
    
    public void drawScaledText(String text, int x, int y, Paint paint, float scale) {
        AffineTransform oldTransform = graphics.getTransform();
        graphics.scale(scale, scale);
        graphics.setPaint(paint);
        graphics.drawString(text, x, y);
        graphics.setTransform(oldTransform);
    }
    
    public void drawScaledText(String text, Vector2D vector, Paint paint, float scale) {;
        this.drawScaledText(text, (int)vector.x, (int)vector.y, paint, scale);
    }
    
    public void drawTextWithCustomFont(String text, int x, int y, Paint paint, Font font) {
        Font oldFont = graphics.getFont();
        graphics.setFont(font);
        graphics.setPaint(paint);
        graphics.drawString(text, x, y);
        graphics.setFont(oldFont);
    }
    
    public void drawTextWithCustomFont(String text, Vector2D vector, Paint paint, Font font) {
        drawText(text, (int)vector.x, (int)vector.y, paint);
    }
    
    public void drawScaledTextWithCustomFont(String text, int x, int y, Paint paint, Font font, float scale) {
        Font oldFont = graphics.getFont();
        graphics.setFont(font.deriveFont(scale));
        graphics.setPaint(paint);
        graphics.drawString(text, x, y);
        graphics.setFont(oldFont);
    }
    
    public void drawScaledTextWithCustomFont(String text, Vector2D vector, Paint paint, Font font, float scale) {;
        this.drawScaledTextWithCustomFont(text, (int)vector.x, (int)vector.y, paint, font, scale);
    }
    
    public void drawTexture(BufferedImage image, int x, int y) {
        graphics.drawImage(image, x, y, null);
    }
    
    public void drawTexture(BufferedImage image, Vector2D vector) {
        drawTexture(image, (int)vector.x, (int)vector.y);
    }
    
    public void drawTransparentTexture(BufferedImage image, int x, int y, float alpha) {
        int rule = AlphaComposite.SRC_OVER;
        Composite transparency = AlphaComposite.getInstance(rule, alpha);
        graphics.setComposite(transparency);
        graphics.drawImage(image, x, y, null);
        graphics.setComposite(AlphaComposite.SrcOver);
    }
    
    public void drawTransparentScaledTexture(BufferedImage image, int x, int y, float alpha, float scaleX, float scaleY) {
        AffineTransform oldTransform = graphics.getTransform();
        int rule = AlphaComposite.SRC_OVER;
        Composite transparency = AlphaComposite.getInstance(rule, alpha);
        graphics.scale(scaleY, scaleX);
        graphics.setComposite(transparency);
        graphics.drawImage(image, x, y, null);
        graphics.setComposite(AlphaComposite.SrcOver);
        graphics.setTransform(oldTransform);
    }
    
    public void drawScaledTexture(BufferedImage image, int x, int y, float scaleX, float scaleY) {
        AffineTransform oldTransform = graphics.getTransform();
        graphics.drawImage(image, x, y, (int) (image.getWidth() * scaleX), (int) (image.getHeight() * scaleY), null);
        graphics.setTransform(oldTransform);
    }
    
    public void drawScaledTexture(BufferedImage image, int x, int y, float scaleXY) {
        AffineTransform oldTransform = graphics.getTransform();
        graphics.drawImage(image, x, y, (int) (image.getWidth() * scaleXY), (int) (image.getHeight() * scaleXY), null);
        graphics.setTransform(oldTransform);
    }
    
    public void drawScaledTexture(BufferedImage image, Vector2D vector, float scaleXY) {
        Buffer.this.drawScaledTexture(image, (int)vector.x, (int)vector.y, scaleXY);
    }
    
    public int getScaleTextWidth(Font font, String text, float scale) {
        AffineTransform oldTransform = graphics.getTransform();
        FontMetrics metric = graphics.getFontMetrics(font.deriveFont(scale));
        graphics.setTransform(oldTransform);
        return metric.stringWidth(text);
    }
    
    public int getScaleTextWidth(String text, float scale) {
        AffineTransform oldTransform = graphics.getTransform();
        FontMetrics metric = graphics.getFontMetrics(graphics.getFont().deriveFont(scale));
        graphics.setTransform(oldTransform);
        return metric.stringWidth(text);
    }
}
