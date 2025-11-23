package CodeQuest.Tiles;

import CodeQuest.Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    public Tile[] Tiles;
    GamePanel gamePanel;
    public int[][] MapTile;

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
            while (col < gamePanel.MaxWorldCol && row < gamePanel.MaxWorldRow) {
                String line = reader.readLine();
                while (col < gamePanel.MaxWorldCol) {
                    String[] Nums = line.split(" ");
                    int num =  Integer.parseInt(Nums[col]);
                    MapTile[col][row] = num;
                    col++;
                }
                if (col == gamePanel.MaxWorldCol) {
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
        try {
            Tiles[0] = new Tile();
            Tiles[0].image = ImageIO.read(getClass().getResourceAsStream("/CodeQuest/res/tiles/grass.png"));

            Tiles[1] = new Tile();
            Tiles[1].image = ImageIO.read(getClass().getResourceAsStream("/CodeQuest/res/tiles/wall.png"));
            Tiles[1].collison = true;

            Tiles[2] = new Tile();
            Tiles[2].image = ImageIO.read(getClass().getResourceAsStream("/CodeQuest/res/tiles/water.png"));
            Tiles[2].collison = true;


        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        int Worldcol = 0;
        int Worldrow = 0;

        while (Worldcol < gamePanel.MaxWorldCol && Worldrow < gamePanel.MaxWorldRow) {

            int WorldX = gamePanel.GameTileSize * Worldcol;
            int WorldY = gamePanel.GameTileSize * Worldrow;
            int ScreenX = WorldX - gamePanel.player.WorldX + gamePanel.player.ScreenX;
            int ScreenY = WorldY - gamePanel.player.WorldY + gamePanel.player.ScreenY;

            if (WorldX + gamePanel.GameTileSize > gamePanel.player.WorldX - gamePanel.player.ScreenX &&
                    WorldY + gamePanel.GameTileSize > gamePanel.player.WorldY - gamePanel.player.ScreenY &&
                    WorldX - gamePanel.GameTileSize < gamePanel.player.WorldX + gamePanel.player.ScreenX &&
                    WorldY - 4*gamePanel.GameTileSize < gamePanel.player.WorldY + gamePanel.player.ScreenY) {

                g2.drawImage(Tiles[MapTile[Worldcol][Worldrow]].image, ScreenX, ScreenY, gamePanel.GameTileSize, gamePanel.GameTileSize, null);
            }

            Worldcol++;

            if (Worldcol == gamePanel.MaxWorldCol) {
                Worldcol = 0;
                Worldrow++;
            }
        }
    }
}
