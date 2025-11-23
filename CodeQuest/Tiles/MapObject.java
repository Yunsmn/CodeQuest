package CodeQuest.Tiles;

import CodeQuest.Main.Drawable;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MapObject implements Drawable {
    public BufferedImage image;
    public Rectangle solidArea;
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;
    public boolean collision = false;
    public int worldX, worldY;
    public String name;

    public MapObject() {
        solidArea = new Rectangle(0, 0, 48, 48);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    @Override
    public int getSortY() {
        return worldY + solidArea.y;
    }

    @Override
    public void draw(Graphics2D g2, int screenX, int screenY) {
        if (name.equals("tree")) {
            if (image != null) {
                g2.drawImage(image, screenX - 64, screenY - 64, 256, 256, null);
            } else {
                g2.setColor(Color.BLUE);
                g2.fillRect(screenX - 64, screenY - 64, 256, 256);
            }
        } else {
            if (image != null) {
                g2.drawImage(image, screenX, screenY, 64, 64, null);
            } else {
                g2.setColor(Color.RED);
                g2.fillRect(screenX, screenY, 64, 64);
            }
        }
    }
}