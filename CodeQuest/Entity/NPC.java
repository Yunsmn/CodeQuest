package CodeQuest.Entity;

import CodeQuest.Main.Drawable;
import CodeQuest.Main.GamePanel;
import CodeQuest.Tiles.AssetHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class NPC extends entity implements Drawable {
    GamePanel gamePanel;
    public String name;
    public String dialogue;
    public boolean hasDialogue = false;

    // NPC behavior
    public boolean stationary = true;
    public int actionCounter = 0;
    public int actionInterval = 120; // Change direction every 2 seconds at 60fps

    public NPC(GamePanel gamePanel, String name) {
        this.gamePanel = gamePanel;
        this.name = name;
        setDefaultValues();
        getNPCImage();
    }

    public void setDefaultValues() {
        speed = 2;
        direction = "down";

        // Default collision area - lower
        solidArea = new Rectangle(8, 32, gamePanel.gameTileSize / 2, gamePanel.gameTileSize / 2);
    }

    public void getNPCImage() {
        // Load directional and idle sprites
        this.up1 = AssetHandler.getInstance().getImage("NPC_up1");
        this.up2 = AssetHandler.getInstance().getImage("NPC_up2");
        this.up3 = AssetHandler.getInstance().getImage("NPC_up3");
        this.up4 = AssetHandler.getInstance().getImage("NPC_up4");
        this.down1 = AssetHandler.getInstance().getImage("NPC_down1");
        this.down2 = AssetHandler.getInstance().getImage("NPC_down2");
        this.down3 = AssetHandler.getInstance().getImage("NPC_down3");
        this.down4 = AssetHandler.getInstance().getImage("NPC_down4");
        this.left1 = AssetHandler.getInstance().getImage("NPC_left1");
        this.left2 = AssetHandler.getInstance().getImage("NPC_left2");
        this.left3 = AssetHandler.getInstance().getImage("NPC_left3");
        this.left4 = AssetHandler.getInstance().getImage("NPC_left4");
        this.right1 = AssetHandler.getInstance().getImage("NPC_right1");
        this.right2 = AssetHandler.getInstance().getImage("NPC_right2");
        this.right3 = AssetHandler.getInstance().getImage("NPC_right3");
        this.right4 = AssetHandler.getInstance().getImage("NPC_right4");
        this.idle1 = AssetHandler.getInstance().getImage("NPC_idle1");
        this.idle2 = AssetHandler.getInstance().getImage("NPC_idle2");
        this.idle3 = AssetHandler.getInstance().getImage("NPC_idle3");
        this.idle4 = AssetHandler.getInstance().getImage("NPC_idle4");
    }

    public void update() {
        if (!stationary) {
            // Random movement AI
            actionCounter++;
            if (actionCounter >= actionInterval) {
                // Random direction
                int rand = (int)(Math.random() * 100);
                if (rand < 25) direction = "up";
                else if (rand < 50) direction = "down";
                else if (rand < 75) direction = "left";
                else direction = "right";

                actionCounter = 0;
            }

            // Use existing collision system
            collisionOn = false;
            gamePanel.collisionChecker.checkTile(this);
            gamePanel.collisionChecker.checkNPCCollision(this); // Add this

            // Move if no collision
            if (!collisionOn) {
                switch (direction) {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }

            // Animate sprite
            long now = System.nanoTime();
            if (now - lastFrameTime > frameDelay) {
                spriteNum++;
                if (spriteNum > 4) spriteNum = 1;
                lastFrameTime = now;
            }
        } else {
            // Stationary NPC - just idle animation
            direction = "idle";
            long now = System.nanoTime();
            if (now - lastFrameTime > frameDelay) {
                spriteNum++;
                if (spriteNum > 4) spriteNum = 1;
                lastFrameTime = now;
            }
        }
    }

    @Override
    public int getSortY() {
        return worldY + solidArea.y + solidArea.height;
    }

    @Override
    public void draw(Graphics2D g2, int screenX, int screenY) {
        BufferedImage image = null;

        switch (direction) {
            case "up":
                if (spriteNum == 1) image = up1;
                else if (spriteNum == 2) image = up2;
                else if (spriteNum == 3) image = up3;
                else if (spriteNum == 4) image = up4;
                break;
            case "down":
                if (spriteNum == 1) image = down1;
                else if (spriteNum == 2) image = down2;
                else if (spriteNum == 3) image = down3;
                else if (spriteNum == 4) image = down4;
                break;
            case "left":
                if (spriteNum == 1) image = left1;
                else if (spriteNum == 2) image = left2;
                else if (spriteNum == 3) image = left3;
                else if (spriteNum == 4) image = left4;
                break;
            case "right":
                if (spriteNum == 1) image = right1;
                else if (spriteNum == 2) image = right2;
                else if (spriteNum == 3) image = right3;
                else if (spriteNum == 4) image = right4;
                break;
            case "idle":
                if (spriteNum == 1) image = idle1;
                else if (spriteNum == 2) image = idle2;
                else if (spriteNum == 3) image = idle3;
                else if (spriteNum == 4) image = idle4;
                break;
        }

        if (image != null) {
            g2.drawImage(image, screenX, screenY, 60, 90, null);
        } else {
            // Fallback - draw colored rectangle
            g2.setColor(Color.RED);
            g2.fillRect(screenX, screenY, 100, 100);
        }
    }
}
