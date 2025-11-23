package CodeQuest.Tiles;

import CodeQuest.Main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    public Tile[] tiles;
    GamePanel gamePanel;
    public int[][] mapTile;

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tiles = new Tile[10]; // grass variants
        getTileImage();
        mapTile = new int[gamePanel.maxWorldCol][gamePanel.maxWorldRow];
        loadMap("/CodeQuest/res/Maps/WorldMap2.txt");
    }

    public void loadMap(String name) {
        try {
            InputStream input = getClass().getResourceAsStream(name);
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            int col =0;
            int row =0;
            while (col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow) {
                String line = reader.readLine();
                while (col < gamePanel.maxWorldCol) {
                    String[] Nums = line.split(" ");
                    int num =  Integer.parseInt(Nums[col]);
                    mapTile[col][row] = num;
                    col++;
                }
                if (col == gamePanel.maxWorldCol) {
                    row++;
                    col = 0;
                }
            }
            reader.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getTileImage() {
        tiles[0] = new Tile();
        tiles[0].image = AssetHandler.getInstance().getImage("terrain1");

        tiles[1] = new Tile();
        tiles[1].image = AssetHandler.getInstance().getImage("terrain2");

        tiles[2] = new Tile();
        tiles[2].image = AssetHandler.getInstance().getImage("terrain3");
    }

    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gamePanel.maxWorldCol && worldRow < gamePanel.maxWorldRow) {

            int worldX = gamePanel.gameTileSize * worldCol;
            int worldY = gamePanel.gameTileSize * worldRow;
            int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
            int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

            if (worldX + gamePanel.gameTileSize > gamePanel.player.worldX - gamePanel.player.screenX &&
                    worldY + gamePanel.gameTileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
                    worldX - gamePanel.gameTileSize < gamePanel.player.worldX + gamePanel.player.screenX &&
                    worldY - 4*gamePanel.gameTileSize < gamePanel.player.worldY + gamePanel.player.screenY) {
                int tileNum = mapTile[worldCol][worldRow];
                if ( 0 <= tileNum && tileNum <= 2 ) {
                    BufferedImage img = tiles[tileNum].image;
                    if (img != null) {
                        g2.drawImage(img, screenX, screenY, gamePanel.gameTileSize, gamePanel.gameTileSize, null);
                    } else {
                        g2.setColor(Color.GREEN);
                        g2.fillRect(screenX, screenY, gamePanel.gameTileSize, gamePanel.gameTileSize);
                    }
                }
            }

            worldCol++;

            if (worldCol == gamePanel.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
