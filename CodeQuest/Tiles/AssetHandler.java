package CodeQuest.Tiles;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class AssetHandler {
    private static AssetHandler instance;
    private java.util.HashMap<String, BufferedImage> assets;

    private AssetHandler() {
        assets = new java.util.HashMap<>();
        loadAssets();
    }

    public static AssetHandler getInstance() {
        if (instance == null) {
            instance = new AssetHandler();
        }
        return instance;
    }

    private void loadAssets() {
        // Player sprites
        loadImage("player_up1", "/CodeQuest/res/player/run_up/0.png");
        loadImage("player_down1", "/CodeQuest/res/player/run_down/0.png");
        loadImage("player_left1", "/CodeQuest/res/player/run_left/0.png");
        loadImage("player_right1", "/CodeQuest/res/player/run_right/0.png");
        loadImage("player_up2", "/CodeQuest/res/player/run_up/2.png");
        loadImage("player_down2", "/CodeQuest/res/player/run_down/2.png");
        loadImage("player_left2", "/CodeQuest/res/player/run_left/2.png");
        loadImage("player_right2", "/CodeQuest/res/player/run_right/2.png");
        loadImage("player_up3", "/CodeQuest/res/player/run_up/4.png");
        loadImage("player_down3", "/CodeQuest/res/player/run_down/4.png");
        loadImage("player_left3", "/CodeQuest/res/player/run_left/4.png");
        loadImage("player_right3", "/CodeQuest/res/player/run_right/4.png");
        loadImage("player_up4", "/CodeQuest/res/player/run_up/6.png");
        loadImage("player_down4", "/CodeQuest/res/player/run_down/6.png");
        loadImage("player_left4", "/CodeQuest/res/player/run_left/6.png");
        loadImage("player_right4", "/CodeQuest/res/player/run_right/6.png");
        loadImage("player_idle1", "/CodeQuest/res/player/idle_down/0.png");
        loadImage("player_idle2", "/CodeQuest/res/player/idle_down/2.png");
        loadImage("player_idle3", "/CodeQuest/res/player/idle_down/4.png");
        loadImage("player_idle4", "/CodeQuest/res/player/idle_down/6.png");

        // Tiles
        loadImage("terrain", "/CodeQuest/res/tiles/terrain.png");

        // Objects
        loadImage("beach_up", "/CodeQuest/res/tiles/Beach/beach_up.png");
        loadImage("beach_down", "/CodeQuest/res/tiles/Beach/beach_down.png");
        loadImage("beach_left", "/CodeQuest/res/tiles/Beach/beach_left.png");
        loadImage("beach_right", "/CodeQuest/res/tiles/Beach/beach_right.png");
        loadImage("beach_top_left", "/CodeQuest/res/tiles/Beach/beach_top_left.png");
        loadImage("beach_top_right", "/CodeQuest/res/tiles/Beach/beach_top_right.png");
        loadImage("beach_bottom_left", "/CodeQuest/res/tiles/Beach/beach_bottom_left.png");
        loadImage("beach_bottom_right", "/CodeQuest/res/tiles/Beach/beach_bottom_right.png");
        loadImage("tree", "/CodeQuest/res/tiles/tree2.png");
        loadImage("terrain1", "/CodeQuest/res/tiles/terrain1.png");
        loadImage("tree1", "/CodeQuest/res/tiles/tree1.png");
        loadImage("terrain2", "/CodeQuest/res/tiles/terrain2.png");
        loadImage("terrain3", "/CodeQuest/res/tiles/terrain3.png");
        loadImage("bush1", "/CodeQuest/res/tiles/bush1.png");
        loadImage("bush2", "/CodeQuest/res/tiles/bush2.png");

        loadImage("NPC_idle1", "/CodeQuest/res/tiles/NPC_idle1.png");
        loadImage("NPC_idle2", "/CodeQuest/res/tiles/NPC_Idle2.png");
        loadImage("NPC_idle3", "/CodeQuest/res/tiles/NPC_Idle3.png");
        loadImage("NPC_idle4", "/CodeQuest/res/tiles/NPC_Idle4.png");

    }

    private void loadImage(String key, String path) {
        try {
            InputStream input = getClass().getResourceAsStream(path);
            if (input != null) {
                BufferedImage img = ImageIO.read(input);
                assets.put(key, img);
            } else {
                assets.put(key, null); // Placeholder
            }
        } catch (IOException e) {
            e.printStackTrace();
            assets.put(key, null); // Placeholder
        }
    }

    public BufferedImage getImage(String key) {
        return assets.get(key);
    }
}