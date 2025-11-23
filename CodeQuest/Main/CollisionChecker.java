package CodeQuest.Main;

import CodeQuest.Entity.NPC;
import CodeQuest.Entity.entity;
import CodeQuest.Tiles.MapObject;

import java.awt.Rectangle;

public class CollisionChecker {
    GamePanel gamePanel;

    public CollisionChecker(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
    public void checkTile(entity entity) {
        int EntityWorldXleft = entity.worldX + entity.solidArea.x;
        int EntityWorldXright = entity.worldX + entity.solidArea.x +  entity.solidArea.width;
        int EntityWorldYup = entity.worldY + entity.solidArea.y;
        int EntityWorldYdown =  entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int EntityLeftCol = EntityWorldXleft/gamePanel.gameTileSize;
        int EntityRightCol = EntityWorldXright/gamePanel.gameTileSize;
        int EntityUpRow = EntityWorldYup/gamePanel.gameTileSize;
        int EntityDownRow = EntityWorldYdown/gamePanel.gameTileSize;

        int tile1, tile2;

        switch (entity.direction) {
            case "up" :
                // Check objects only (no tile collision since map is all floor)
                if (checkObjectCollision(entity, "up")) {
                    entity.collisionOn = true;
                }
                break;
            case "down" :
                // Check objects only
                if (checkObjectCollision(entity, "down")) {
                    entity.collisionOn = true;
                }
                break;
            case "left" :
                // Check objects only
                if (checkObjectCollision(entity, "left")) {
                    entity.collisionOn = true;
                }
                break;
            case "right" :
                // Check objects only
                if (checkObjectCollision(entity, "right")) {
                    entity.collisionOn = true;
                }
                break;
        }



    }

    private boolean checkObjectCollision(entity entity, String direction) {
        int futureX = entity.worldX;
        int futureY = entity.worldY;
        switch (direction) {
            case "up":
                futureY -= entity.speed;
                break;
            case "down":
                futureY += entity.speed;
                break;
            case "left":
                futureX -= entity.speed;
                break;
            case "right":
                futureX += entity.speed;
                break;
        }
        Rectangle futureSolid = new Rectangle(futureX + entity.solidArea.x, futureY + entity.solidArea.y, entity.solidArea.width, entity.solidArea.height);
        for (MapObject obj : gamePanel.objM.objects) {
            if (obj.collision) {
                // Temporarily move to world coordinates
                obj.solidArea.x = obj.worldX + obj.solidAreaDefaultX;
                obj.solidArea.y = obj.worldY + obj.solidAreaDefaultY;
                boolean intersects = futureSolid.intersects(obj.solidArea);
                // Reset to relative
                obj.solidArea.x = obj.solidAreaDefaultX;
                obj.solidArea.y = obj.solidAreaDefaultY;
                if (intersects) {
                    return true;
                }
            }
        }
        return false;
    }
    public void checkNPCCollision(entity entity) {
        int futureX = entity.worldX;
        int futureY = entity.worldY;

        switch (entity.direction) {
            case "up": futureY -= entity.speed; break;
            case "down": futureY += entity.speed; break;
            case "left": futureX -= entity.speed; break;
            case "right": futureX += entity.speed; break;
        }

        Rectangle futureSolid = new Rectangle(
                futureX + entity.solidArea.x,
                futureY + entity.solidArea.y,
                entity.solidArea.width,
                entity.solidArea.height
        );

        // Check collision with player
        Rectangle playerRect = new Rectangle(
                gamePanel.player.worldX + gamePanel.player.solidArea.x,
                gamePanel.player.worldY + gamePanel.player.solidArea.y,
                gamePanel.player.solidArea.width,
                gamePanel.player.solidArea.height
        );

        if (futureSolid.intersects(playerRect)) {
            entity.collisionOn = true;
            return;
        }

        // Check collision with other NPCs
        for (NPC npc : gamePanel.npcM.npcs) {
            if (npc != entity) { // Don't check collision with self
                Rectangle npcRect = new Rectangle(
                        npc.worldX + npc.solidArea.x,
                        npc.worldY + npc.solidArea.y,
                        npc.solidArea.width,
                        npc.solidArea.height
                );

                if (futureSolid.intersects(npcRect)) {
                    entity.collisionOn = true;
                    return;
                }
            }
        }

        // Check collision with objects
        for (MapObject obj : gamePanel.objM.objects) {
            if (obj.collision) {
                obj.solidArea.x = obj.worldX + obj.solidAreaDefaultX;
                obj.solidArea.y = obj.worldY + obj.solidAreaDefaultY;
                boolean intersects = futureSolid.intersects(obj.solidArea);
                obj.solidArea.x = obj.solidAreaDefaultX;
                obj.solidArea.y = obj.solidAreaDefaultY;
                if (intersects) {
                    entity.collisionOn = true;
                    return;
                }
            }
        }
    }
}
