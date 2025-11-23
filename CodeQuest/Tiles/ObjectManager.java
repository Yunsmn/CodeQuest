package CodeQuest.Tiles;

import CodeQuest.Main.GamePanel;

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
                    if (name.startsWith("npc")) continue; // Skip NPCs, handled by NPCManager
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
                        switch (tileKey) {
                            case "beach_up" :
                                obj.solidArea = new Rectangle(0, -6, 64, 42);
                                break;
                            case "beach_down" :
                                obj.solidArea = new Rectangle(0, 0, 64, 64);// Full 64x64
                                break;
                            case "beach_left" :
                                obj.solidArea = new Rectangle(0, 0, 64, 64);  // Full 64x64
                                break;
                            case "beach_right" :
                                obj.solidArea = new Rectangle(0, 0, 64, 64);  // Full 64x64
                                break;
                            case "beach_top_left" :
                                obj.solidArea = new Rectangle(-6, -6, 60, 60);// 60x60
                                break;
                            case "beach_top_right" :
                                obj.solidArea = new Rectangle(10, -6, 60, 60);
                                break;
                            case "beach_bottom_left" :
                                obj.solidArea = new Rectangle(0, 0, 64, 64);  // Full 64x64
                                break;
                            case "beach_bottom_right" :
                                obj.solidArea = new Rectangle(0, 0, 64, 64);  // Full 64x64
                                break;
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
            obj.solidArea = new Rectangle(50, 133, 32, 59);  // Trunk collision shifted +64 x/y
            obj.solidAreaDefaultX = obj.solidArea.x;
            obj.solidAreaDefaultY = obj.solidArea.y;
            // Randomly choose tree or tree1
            String treeKey = (Math.random() < 0.5) ? "tree" : "tree1";
            obj.image = AssetHandler.getInstance().getImage(treeKey);
        } else if (name.equals("bush")) {
            obj.collision = false;
            obj.solidArea = new Rectangle(8, 11, 47, 42);  // Full bush image area
            obj.solidAreaDefaultX = obj.solidArea.x;
            obj.solidAreaDefaultY = obj.solidArea.y;
            // Randomly choose bush1 or bush2
            String bushKey = (Math.random() < 0.5) ? "bush1" : "bush2";
            obj.image = AssetHandler.getInstance().getImage(bushKey);
        }

        return obj; // Default
    }
}