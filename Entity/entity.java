package CodeQuest.Entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class entity {
    public int worldX,worldY;
    public int speed;
    public BufferedImage up1, up2, up3, up4, down1, down2, down3, down4, left1,
            left2, left3, left4, right1, right2, right3, right4, idle1, idle2, idle3, idle4;
    public String direction;
    public int spriteNum = 1;
    public long lastFrameTime = 0;
    public long frameDelay = 200_000_000;
    public Rectangle solidArea;
    public boolean collisionOn = false;

}
