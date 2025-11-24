package CodeQuest.Entity;

import CodeQuest.Main.Drawable;
import CodeQuest.Main.GamePanel;
import CodeQuest.Main.KeyHandler;
import CodeQuest.Entity.NPC;
import CodeQuest.Tiles.AssetHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends entity implements Drawable {
    GamePanel gamePanel;
    KeyHandler keyH;
    public final int screenX;
    public final int screenY;

    // Command movement
    public String commandDirection;
    public long commandMoveEndTime;
    public int commandMoveDuration;
    public int commandSpeed;
    public long commandFrameDelay;

    public Player(GamePanel gamePanel, KeyHandler keyH) {
        this.gamePanel = gamePanel;
        this.keyH = keyH;
        setDefault();
        screenX = (int) (gamePanel.gameTileSize * 7.5);
        screenY = (int) (gamePanel.gameTileSize * 5);
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = gamePanel.gameTileSize / 2;
        solidArea.height = gamePanel.gameTileSize / 2;

        // Command movement fields
        commandDirection = null;
        commandMoveEndTime = 0;
        commandMoveDuration = 500; // 0.5 seconds
        commandSpeed = 2; // Slower for smooth one-tile movement
        commandFrameDelay = 100_000_000; // Faster animation for commands

    }

    public void setDefault() {
        worldX = 100;
        worldY = 100;
        speed = 4;
        direction = "down";
        getPlayerImage();
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
        // Handle command movement
        if (commandDirection != null && System.currentTimeMillis() < commandMoveEndTime) {
            direction = commandDirection;
            collisionOn = false;
            gamePanel.collisionChecker.checkTile(this);

            // Check collision with NPCs
            if (!collisionOn) {
                Rectangle futureRect = new Rectangle(worldX + solidArea.x, worldY + solidArea.y, solidArea.width, solidArea.height);
                switch (direction) {
                    case "up": futureRect.y -= commandSpeed; break;
                    case "down": futureRect.y += commandSpeed; break;
                    case "left": futureRect.x -= commandSpeed; break;
                    case "right": futureRect.x += commandSpeed; break;
                }
                for (NPC npc : gamePanel.npcM.npcs) {
                    Rectangle npcRect = new Rectangle(npc.worldX + npc.solidArea.x, npc.worldY + npc.solidArea.y, npc.solidArea.width, npc.solidArea.height);
                    if (futureRect.intersects(npcRect)) {
                        collisionOn = true;
                        break;
                    }
                }
            }

            if (!collisionOn) {
                switch (direction) {
                    case "up": worldY -= commandSpeed; break;
                    case "down": worldY += commandSpeed; break;
                    case "left": worldX -= commandSpeed; break;
                    case "right": worldX += commandSpeed; break;
                }

                // Update animation with faster delay
                long now = System.nanoTime();
                if (now - lastFrameTime > commandFrameDelay) {
                    spriteNum++;
                    if (spriteNum > 4) spriteNum = 1;
                    lastFrameTime = now;
                }
            } else {
                // Stop command movement on collision
                commandDirection = null;
                commandMoveEndTime = 0;
            }
        } else if (commandDirection != null) {
            // Time expired, stop command movement
            commandDirection = null;
        }

        // Allow keyboard control only when command parser is not executing
        // This prevents conflicts between keyboard and command movement
        boolean commandsExecuting = (gamePanel.commandParser != null && gamePanel.commandParser.isExecuting());

        if (commandsExecuting && (keyH.UpPressed || keyH.DownPressed || keyH.LeftPressed || keyH.RightPressed)) {
            if (keyH.UpPressed) {
                direction = "up";
            }
            if (keyH.DownPressed) {
                direction = "down";
            }
            if (keyH.LeftPressed) {
                direction = "left";
            }
            if (keyH.RightPressed) {
                direction = "right";
            }

            collisionOn = false;
            gamePanel.collisionChecker.checkTile(this);

            // Check collision with NPCs at future position
            if (!collisionOn) {
                Rectangle futureRect = new Rectangle(worldX + solidArea.x, worldY + solidArea.y, solidArea.width, solidArea.height);
                switch (direction) {
                    case "up": futureRect.y -= speed; break;
                    case "down": futureRect.y += speed; break;
                    case "left": futureRect.x -= speed; break;
                    case "right": futureRect.x += speed; break;
                }
                for (NPC npc : gamePanel.npcM.npcs) {
                    Rectangle npcRect = new Rectangle(npc.worldX + npc.solidArea.x, npc.worldY + npc.solidArea.y, npc.solidArea.width, npc.solidArea.height);
                    if (futureRect.intersects(npcRect)) {
                        collisionOn = true;
                        break;
                    }
                }
            }

            if (!collisionOn) {
                switch (direction) {
                    case "up":
                        worldY = worldY - speed;
                        break;
                    case "down":
                        worldY = worldY + speed;
                        break;
                    case "left":
                        worldX = worldX - speed;
                        break;
                    case "right":
                        worldX = worldX + speed;
                        break;
                }
            }

            long now = System.nanoTime();
            if (now - lastFrameTime > frameDelay) {
                spriteNum++;
                if (spriteNum > 4) {
                    spriteNum = 1;
                }
                lastFrameTime = now;
            }
        } else if (!commandsExecuting) {
            // Only set to idle if not moving and commands aren't executing
            direction = "idle";
            long now = System.nanoTime();
            if (now - lastFrameTime > frameDelay) {
                spriteNum++;
                if (spriteNum > 4) {
                    spriteNum = 1;
                }
                lastFrameTime = now;
            }
        } else {
            // Commands are executing - just update animation
            long now = System.nanoTime();
            if (now - lastFrameTime > frameDelay) {
                spriteNum++;
                if (spriteNum > 4) {
                    spriteNum = 1;
                }
                lastFrameTime = now;
            }
        }

    }

    public void drawPlayer(Graphics2D g2) {
        BufferedImage image = null;

        switch (direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }
                if (spriteNum == 3) {
                    image = up3;
                }
                if (spriteNum == 4) {
                    image = up4;
                }
                break;
            case "down":
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }
                if (spriteNum == 3) {
                    image = down3;
                }
                if (spriteNum == 4) {
                    image = down4;
                }
                break;
            case "left":
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
                if (spriteNum == 3) {
                    image = left3;
                }
                if (spriteNum == 4) {
                    image = left4;
                }
                break;
            case "right":
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }
                if (spriteNum == 3) {
                    image = right3;
                }
                if (spriteNum == 4) {
                    image = right4;
                }
                break;
            case "idle":
                if (spriteNum == 1) {
                    image = idle1;
                }
                if (spriteNum == 2) {
                    image = idle2;
                }
                if (spriteNum == 3) {
                    image = idle3;
                }
                if (spriteNum == 4) {
                    image = idle4;
                }
                break;
        }
        if (image != null) {
            g2.drawImage(image, screenX, screenY, gamePanel.gameTileSize, gamePanel.gameTileSize, null);
        }
    }

    @Override
    public int getSortY() {
        return worldY + solidArea.y + solidArea.height;
    }

    @Override
    public void draw(Graphics2D g2, int screenX, int screenY) {
        drawPlayer(g2);
    }
}
