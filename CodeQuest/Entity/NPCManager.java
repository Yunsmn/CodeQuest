package CodeQuest.Entity;

import CodeQuest.Main.GamePanel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class NPCManager {
    GamePanel gamePanel;
    public List<NPC> npcs = new ArrayList<>();

    public NPCManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        loadNPCs("/CodeQuest/res/Maps/NPCs.txt");
    }

    public void loadNPCs(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            if (is == null) return;

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;

            while ((line = br.readLine()) != null) {
                // Format: npcName x y stationary dialogue
                // Example: villager1 5 10 true Hello traveler!
                String[] parts = line.split(" ", 5);
                if (parts.length >= 4) {
                    String name = parts[0];
                    int x = Integer.parseInt(parts[1]);
                    int y = Integer.parseInt(parts[2]);
                    boolean stationary = Boolean.parseBoolean(parts[3]);
                    String dialogue = parts.length > 4 ? parts[4] : "";

                    NPC npc = new NPC(gamePanel, name);
                    npc.worldX = x * gamePanel.gameTileSize;
                    npc.worldY = y * gamePanel.gameTileSize;
                    npc.stationary = stationary;
                    npc.dialogue = dialogue;
                    npc.hasDialogue = !dialogue.isEmpty();

                    npcs.add(npc);
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        for (NPC npc : npcs) {
            npc.update();
        }
    }
}