package CodeQuest.Tiles;

import CodeQuest.Main.GamePanel;
import CodeQuest.Tiles.AssetHandler;

import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ObjectManager {
    GamePanel gamePanel;
    public List<MapObject> objects = new ArrayList<>();

    public ObjectManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        loadObjects("/CodeQuest/res/Maps/Objects.txt");
    }

    public void loadObjects(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            if (is == null) return; // no objects file
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length >= 3) {
                    String name = parts[0];
                    int x = Integer.parseInt(parts[1]);
                    int y = Integer.parseInt(parts[2]);
                    MapObject obj = createObject(name);
                    obj.name = name;
                    obj.worldX = x * gamePanel.gameTileSize;
                    obj.worldY = y * gamePanel.gameTileSize;
                    // Assign correct image and solidArea for walls
                    if (name.equals("wall")) {
                        String tileKey;
                        if (x == 0 && y == 0) tileKey = "beach_top_left";
                        else if (x == 24 && y == 0) tileKey = "beach_top_right";
                        else if (x == 0 && y == 24) tileKey = "beach_bottom_left";
                        else if (x == 24 && y == 24) tileKey = "beach_bottom_right";
                        else if (y == 0) tileKey = "beach_up";
                        else if (y == 24) tileKey = "beach_down";
                        else if (x == 0) tileKey = "beach_left";
                        else if (x == 24) tileKey = "beach_right";
                        else tileKey = "beach_down";  // Fallback
                        obj.image = AssetHandler.getInstance().getImage(tileKey);
                        // Set solidArea based on tileKey, half +10 pixels
                        if (tileKey.equals("beach_up")) {
                            obj.solidArea = new Rectangle(0, -6, 64, 42);
                        } else if (tileKey.equals("beach_down")) {
                            obj.solidArea = new Rectangle(0, 0, 64, 64);  // Full 64x64
                        } else if (tileKey.equals("beach_left")) {
                            obj.solidArea = new Rectangle(0, 0, 64, 64);  // Full 64x64
                        } else if (tileKey.equals("beach_right")) {
                            obj.solidArea = new Rectangle(0, 0, 64, 64);  // Full 64x64
                        } else if (tileKey.equals("beach_top_left")) {
                            obj.solidArea = new Rectangle(-6, -6, 60, 60);  // 60x60
                        } else if (tileKey.equals("beach_top_right")) {
                            obj.solidArea = new Rectangle(10, -6, 60, 60);
                        } else if (tileKey.equals("beach_bottom_left")) {
                            obj.solidArea = new Rectangle(0, 0, 64, 64);  // Full 64x64
                        } else if (tileKey.equals("beach_bottom_right")) {
                            obj.solidArea = new Rectangle(0, 0, 64, 64);  // Full 64x64
                        }
                        obj.solidAreaDefaultX = obj.solidArea.x;
                        obj.solidAreaDefaultY = obj.solidArea.y;
                    }
                    objects.add(obj);
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private MapObject createObject(String name) {
        MapObject obj = new MapObject();
        if (name.equals("wall")) {
            obj.collision = true;
            obj.solidArea = new Rectangle(0, 0, 64, 64);  // Full tile collision
            obj.solidAreaDefaultX = obj.solidArea.x;
            obj.solidAreaDefaultY = obj.solidArea.y;
            // Image assigned later based on position
        } else if (name.equals("tree")) {
            obj.collision = true;
            obj.solidArea = new Rectangle(53, 133, 32, 59);  // Trunk collision shifted +64 x/y
            obj.solidAreaDefaultX = obj.solidArea.x;
            obj.solidAreaDefaultY = obj.solidArea.y;
            // Randomly choose tree or tree1
            String treeKey = (Math.random() < 0.5) ? "tree" : "tree1";
            obj.image = AssetHandler.getInstance().getImage(treeKey);
        }
        // Add other objects
        return obj; // Default
    }
}