package CodeQuest.Entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class entity {
    public int WorldX,WorldY;
    public int speed;
    public BufferedImage Up1, Up2, Down1, Down2, Left1, Left2, Right1, Right2;
    public String Direction;
    public int SpriteCounter = 0;
    public int SpriteNum = 1;

    public Rectangle Solidarea;
    public boolean collisionON = false;

}
