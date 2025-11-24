package CodeQuest.Main;

import CodeQuest.Entity.Player;
import CodeQuest.Entity.NPC;
import java.awt.Rectangle;
import java.util.*;

/**
 * CommandAdapter - Smooth movement version
 * Moves player gradually instead of instant teleportation
 */
public class CommandAdapter {
    private GamePanel gamePanel;
    private Player player;
    private Queue<Runnable> actionQueue;
    private boolean isExecuting;
    private int actionDelay = 0; // milliseconds - no delay between commands
    private long lastActionTime = 0;
    private long lastFrameTime = 0;
    private long frameDelay = 100_000_000; // 0.1 seconds - faster animation
    private boolean isMoving;
    private int targetX, targetY;
    private int moveSpeed = 3;
    
    public CommandAdapter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.player = gamePanel.player;
        this.actionQueue = new LinkedList<>();
        this.isExecuting = false;
        this.isMoving = false;
        this.targetX = player.worldX;
        this.targetY = player.worldY;
        this.targetY = player.worldY;
    }
    
    /**
     * Update method - processes smooth movement
     */
    public void update() {
        // If currently moving to target, continue smooth movement
        if (isMoving) {
            smoothMove();
            return;
        }
        
        // If done moving and queue is empty
        if (actionQueue.isEmpty()) {
            isExecuting = false;
            return;
        }
        
        // Check delay between commands
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastActionTime < actionDelay) {
            return;
        }
        
        // Execute next command
        isExecuting = true;
        Runnable action = actionQueue.poll();
        if (action != null) {
            action.run();
            lastActionTime = currentTime;
        }
    }
    
    /**
     * Smooth movement - move gradually toward target
     */
    private void smoothMove() {
        int dx = targetX - player.worldX;
        int dy = targetY - player.worldY;
        
        // Calculate distance
        double distance = Math.sqrt(dx * dx + dy * dy);
        
        if (distance <= moveSpeed) {
            // Close enough - snap to target
            player.worldX = targetX;
            player.worldY = targetY;
            isMoving = false;
            // Keep direction for next command
        } else {
            // Move toward target
            double ratio = moveSpeed / distance;
            int stepX = (int)(dx * ratio);
            int stepY = (int)(dy * ratio);

            // Try move
            int oldX = player.worldX;
            int oldY = player.worldY;
            player.worldX += stepX;
            player.worldY += stepY;

            // Check collision at new position
            player.collisionOn = false;
            gamePanel.collisionChecker.checkTile(player);
            // Check NPC collision
            Rectangle playerRect = new Rectangle(player.worldX + player.solidArea.x, player.worldY + player.solidArea.y, player.solidArea.width, player.solidArea.height);
            for (NPC npc : gamePanel.npcM.npcs) {
                Rectangle npcRect = new Rectangle(npc.worldX + npc.solidArea.x, npc.worldY + npc.solidArea.y, npc.solidArea.width, npc.solidArea.height);
                if (playerRect.intersects(npcRect)) {
                    player.collisionOn = true;
                    break;
                }
            }

            if (player.collisionOn) {
                // Revert move and stop
                player.worldX = oldX;
                player.worldY = oldY;
                isMoving = false;
                System.out.println("❌ Collision during movement - stopped");
                return;
            }

            // Update sprite animation with timing
            long now = System.nanoTime();
            if (now - lastFrameTime > frameDelay) {
                player.spriteNum++;
                if (player.spriteNum > 4) {
                    player.spriteNum = 1;
                }
                lastFrameTime = now;
            }
        }
    }
    
    /**
     * Start smooth movement to target position
     */
    private void startSmoothMove(int newTargetX, int newTargetY, String direction) {
        targetX = newTargetX;
        targetY = newTargetY;
        player.direction = direction;
        isMoving = true;
    }
    
    public boolean isExecuting() {
        return isExecuting || !actionQueue.isEmpty() || isMoving;
    }
    
    public int getQueueSize() {
        return actionQueue.size();
    }
    
    public void setActionDelay(int delayMs) {
        this.actionDelay = delayMs;
    }
    
    /**
     * Set movement speed (pixels per frame)
     * Lower = slower/smoother, Higher = faster
     */
    public void setMoveSpeed(int speed) {
        this.moveSpeed = speed;
    }
    
    public void clearQueue() {
        actionQueue.clear();
        isExecuting = false;
        isMoving = false;
        targetX = player.worldX;
        targetY = player.worldY;
        System.out.println("✓ Action queue cleared");
    }
    
    // ========== Movement Execution Methods ==========
    
    public void executeMoveUp() {
        actionQueue.add(() -> {
            // Check collision at target position (one tile)
            int newY = player.worldY - 64;

            player.direction = "up";
            player.collisionOn = false;

            // Temporarily set position to check collision
            int oldY = player.worldY;
            player.worldY = newY;
            gamePanel.collisionChecker.checkTile(player);
            player.worldY = oldY;

            if (!player.collisionOn) {
                startSmoothMove(player.worldX, newY, "up");
                System.out.println("✓ Moving up to (" + player.worldX + ", " + newY + ")");
            } else {
                System.out.println("❌ Cannot move up - collision detected");
            }
        });
    }
    
    public void executeMoveDown() {
        actionQueue.add(() -> {
            // Check collision at target position (one tile)
            int newY = player.worldY + 64;

            player.direction = "down";
            player.collisionOn = false;

            // Temporarily set position to check collision
            int oldY = player.worldY;
            player.worldY = newY;
            gamePanel.collisionChecker.checkTile(player);
            player.worldY = oldY;

            if (!player.collisionOn) {
                startSmoothMove(player.worldX, newY, "down");
                System.out.println("✓ Moving down to (" + player.worldX + ", " + newY + ")");
            } else {
                System.out.println("❌ Cannot move down - collision detected");
            }
        });
    }
    
    public void executeMoveLeft() {
        actionQueue.add(() -> {
            // Check collision at target position (one tile)
            int newX = player.worldX - 64;

            player.direction = "left";
            player.collisionOn = false;

            // Temporarily set position to check collision
            int oldX = player.worldX;
            player.worldX = newX;
            gamePanel.collisionChecker.checkTile(player);
            player.worldX = oldX;

            if (!player.collisionOn) {
                startSmoothMove(newX, player.worldY, "left");
                System.out.println("✓ Moving left to (" + newX + ", " + player.worldY + ")");
            } else {
                System.out.println("❌ Cannot move left - collision detected");
            }
        });
    }
    
    public void executeMoveRight() {
        actionQueue.add(() -> {
            // Check collision at target position (one tile)
            int newX = player.worldX + 64;

            player.direction = "right";
            player.collisionOn = false;

            // Temporarily set position to check collision
            int oldX = player.worldX;
            player.worldX = newX;
            gamePanel.collisionChecker.checkTile(player);
            player.worldX = oldX;

            if (!player.collisionOn) {
                startSmoothMove(newX, player.worldY, "right");
                System.out.println("✓ Moving right to (" + newX + ", " + player.worldY + ")");
            } else {
                System.out.println("❌ Cannot move right - collision detected");
            }
        });
    }
    
    public void executeTurn(String direction) {
        actionQueue.add(() -> {
            player.direction = direction;
            System.out.println("✓ Turned to face " + direction);
        });
    }
    
    public void executeWait(int milliseconds) {
        actionQueue.add(() -> {
            lastActionTime = System.currentTimeMillis() + milliseconds - actionDelay;
            System.out.println("✓ Waiting " + milliseconds + "ms");
        });
    }
    
    public void executePrint(String message) {
        actionQueue.add(() -> {
            System.out.println("🐍 " + message);
        });
    }
}
