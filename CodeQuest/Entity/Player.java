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

    public void GetPlayerImage() {
        try {
            this.Up1 = ImageIO.read(getClass().getResourceAsStream("/CodeQuest/res/player/boy_up_1.png"));
            this.Down1 = ImageIO.read(getClass().getResourceAsStream("/CodeQuest/res/player/boy_down_1.png"));
            this.Left1 = ImageIO.read(getClass().getResourceAsStream("/CodeQuest/res/player/boy_left_1.png"));
            this.Right1 = ImageIO.read(getClass().getResourceAsStream("/CodeQuest/res/player/boy_right_1.png"));
            this.Up2 = ImageIO.read(getClass().getResourceAsStream("/CodeQuest/res/player/boy_up_2.png"));
            this.Down2 = ImageIO.read(getClass().getResourceAsStream("/CodeQuest/res/player/boy_down_2.png"));
            this.Left2 = ImageIO.read(getClass().getResourceAsStream("/CodeQuest/res/player/boy_left_2.png"));
            this.Right2 = ImageIO.read(getClass().getResourceAsStream("/CodeQuest/res/player/boy_right_2.png"));


        }catch (IOException e){
            e.printStackTrace();
        }
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
