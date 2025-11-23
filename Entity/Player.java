package CodeQuest.Entity;

import CodeQuest.Main.GamePanel;
import CodeQuest.Main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends entity {
    GamePanel gamePanel;
    KeyHandler keyH;
    public final int ScreenX;
    public final int ScreenY;


    public Player(GamePanel gamePanel, KeyHandler keyH) {
        this.gamePanel = gamePanel;
        this.keyH = keyH;
        setdefault();
        ScreenX = (int) (gamePanel.GameTileSize * 7.5);
        ScreenY = (int) (gamePanel.GameTileSize*5);
        Solidarea = new Rectangle();
        Solidarea.x = 8;
        Solidarea.y = 16;
        Solidarea.width = gamePanel.GameTileSize/2;
        Solidarea.height = gamePanel.GameTileSize/2;

    }

    public void setdefault() {
        WorldX = 100;
        WorldY = 100;
        speed = 4;
        Direction = "down";
        GetPlayerImage();
    }

    public void getPlayerImage() {
        // Load from AssetHandler
        this.up1 = AssetHandler.getInstance().getImage("player_up1");
        this.down1 = AssetHandler.getInstance().getImage("player_down1");
        this.left1 = AssetHandler.getInstance().getImage("player_left1");
        this.right1 = AssetHandler.getInstance().getImage("player_right1");
        this.up2 = AssetHandler.getInstance().getImage("player_up2");
        this.down2 = AssetHandler.getInstance().getImage("player_down2");
        this.left2 = AssetHandler.getInstance().getImage("player_left2");
        this.right2 = AssetHandler.getInstance().getImage("player_right2");
        this.up3 = AssetHandler.getInstance().getImage("player_up3");
        this.down3 = AssetHandler.getInstance().getImage("player_down3");
        this.left3 = AssetHandler.getInstance().getImage("player_left3");
        this.right3 = AssetHandler.getInstance().getImage("player_right3");
        this.up4 = AssetHandler.getInstance().getImage("player_up4");
        this.down4 = AssetHandler.getInstance().getImage("player_down4");
        this.left4 = AssetHandler.getInstance().getImage("player_left4");
        this.right4 = AssetHandler.getInstance().getImage("player_right4");
        this.idle1 = AssetHandler.getInstance().getImage("player_idle1");
        this.idle2 = AssetHandler.getInstance().getImage("player_idle2");
        this.idle3 = AssetHandler.getInstance().getImage("player_idle3");
        this.idle4 = AssetHandler.getInstance().getImage("player_idle4");
    }

    public void update() {
        if (keyH.UpPressed || keyH.DownPressed || keyH.LeftPressed || keyH.RightPressed) {
            if (keyH.UpPressed) {
                Direction = "up";
            }
            if (keyH.DownPressed) {
                Direction = "down";
            }
            if (keyH.LeftPressed) {
                Direction = "left";
            }
            if (keyH.RightPressed) {
                Direction = "right";
            }

            collisionON = false;
            gamePanel.CheckeColl.checkTile(this);

            if (!collisionON) {
                switch (Direction) {
                    case "up":
                        WorldY = WorldY - speed;
                        break;
                    case "down":
                        WorldY = WorldY + speed;
                        break;
                    case "left":
                        WorldX = WorldX - speed;
                        break;
                    case  "right":
                        WorldX = WorldX + speed;
                        break;
                }
            }

            SpriteCounter++;
            if (SpriteCounter > 11) {
                if (SpriteNum == 1) {
                    SpriteNum = 2;
                } else if  (SpriteNum == 2) {
                    SpriteNum = 1;
                }
                SpriteCounter = 0;
            }
        }
        else {
            Direction = "down";
        }

    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch (Direction) {
            case "up":
                if (SpriteNum == 1) {
                    image = Up1;
                }
                if  (SpriteNum == 2) {
                    image = Up2;
                }
                break;
            case "down":
                if (SpriteNum == 1) {
                    image = Down1;
                }
                if  (SpriteNum == 2) {
                    image = Down2;
                }
                break;
            case "left":
                if (SpriteNum == 1) {
                    image = Left1;
                }
                if  (SpriteNum == 2) {
                    image = Left2;
                }
                break;
            case "right":
                if (SpriteNum == 1) {
                    image = Right1;
                }
                if  (SpriteNum == 2) {
                    image = Right2;
                }
                break;
         }
        g2.drawImage(image, ScreenX, ScreenY,gamePanel.GameTileSize,gamePanel.GameTileSize, null);

    }
}
