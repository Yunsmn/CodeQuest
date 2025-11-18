package CodeQuest.Main;

import CodeQuest.Entity.entity;

public class CollisionChecker {
    GamePanel gamePanel;

    public CollisionChecker(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
    public void checkTile(entity entity) {
        int EntityWorldXleft = entity.WorldX + entity.Solidarea.x;
        int EntityWorldXright = entity.WorldX + entity.Solidarea.x +  entity.Solidarea.width;
        int EntityWorldYup = entity.WorldY + entity.Solidarea.y;
        int EntityWorldYdown =  entity.WorldY + entity.Solidarea.y + entity.Solidarea.height;

        int EntityLeftCol = EntityWorldXleft/gamePanel.GameTileSize;
        int EntityRightCol = EntityWorldXright/gamePanel.GameTileSize;
        int EntityUpRow = EntityWorldYup/gamePanel.GameTileSize;
        int EntityDownRow = EntityWorldYdown/gamePanel.GameTileSize;

        int tile1, tile2;

        switch (entity.Direction) {
            case "up" :
                EntityUpRow = (EntityWorldYup - entity.speed) / gamePanel.GameTileSize;
                tile1 = gamePanel.TileM.MapTile[EntityLeftCol][EntityUpRow];
                tile2 = gamePanel.TileM.MapTile[EntityRightCol][EntityUpRow];
                if (gamePanel.TileM.Tiles[tile1].collison || gamePanel.TileM.Tiles[tile2].collison) {
                    entity.collisionON = true;
                }
                break;
            case "down" :
                EntityDownRow = (EntityWorldYdown - entity.speed) / gamePanel.GameTileSize;
                tile1 = gamePanel.TileM.MapTile[EntityLeftCol][EntityDownRow];
                tile2 = gamePanel.TileM.MapTile[EntityRightCol][EntityDownRow];
                if (gamePanel.TileM.Tiles[tile1].collison || gamePanel.TileM.Tiles[tile2].collison) {
                    entity.collisionON = true;
                }
                break;
            case "left" :
                EntityLeftCol = (EntityWorldXleft - entity.speed) / gamePanel.GameTileSize;
                tile1 = gamePanel.TileM.MapTile[EntityLeftCol][EntityUpRow];
                tile2 = gamePanel.TileM.MapTile[EntityLeftCol][EntityDownRow];
                if (gamePanel.TileM.Tiles[tile1].collison || gamePanel.TileM.Tiles[tile2].collison) {
                    entity.collisionON = true;
                }
                break;
            case "right" :
                EntityRightCol = (EntityWorldXright - entity.speed) / gamePanel.GameTileSize;
                tile1 = gamePanel.TileM.MapTile[EntityRightCol][EntityUpRow];
                tile2 = gamePanel.TileM.MapTile[EntityRightCol][EntityDownRow];
                if (gamePanel.TileM.Tiles[tile1].collison || gamePanel.TileM.Tiles[tile2].collison) {
                    entity.collisionON = true;
                }
                break;
        }



    }
}
