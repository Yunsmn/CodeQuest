package CodeQuest.Main;

import CodeQuest.Entity.Player;
import CodeQuest.Main.Drawable;
import CodeQuest.Tiles.MapObject;
import CodeQuest.Tiles.ObjectManager;
import CodeQuest.Tiles.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GamePanel extends JPanel implements Runnable {

    final int gameTiles = 32;
    final int scale = 2;
    public final int gameTileSize = gameTiles * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 10;
    public final int gameScreenRow = 11;
    final int parserRow = 3;
    public final int screenWidth = gameTileSize * maxScreenCol;
    public final int screenHeight = gameTileSize * maxScreenRow;
    int fps = 60;
    public KeyHandler keyH = new KeyHandler(this);
    Thread gameThread;
    public Player player = new Player(this, keyH);

    TileManager tileM = new TileManager(this);
    ObjectManager objM = new ObjectManager(this);

    public CollisionChecker collisionChecker = new CollisionChecker(this);

    public final int maxWorldCol = 25;
    public final int maxWorldRow = 25;
    public final int worldWidth = maxWorldCol * gameTileSize;
    public final int worldHeight = maxWorldRow * gameTileSize;




    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(new Color(0x156c99));
        this.setLayout(null);
        this.setFocusable(false);
        this.setVisible(true);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = (double) 1000000000 / fps;
        double deltaTime = 0;
        long lastTime = System.nanoTime();
        long now;
        long timer = 0;
        int frames = 0;

        while (gameThread != null) {

            now = System.nanoTime();
            deltaTime += (now - lastTime) / drawInterval;
            timer += now - lastTime;
            lastTime = now;
            if (deltaTime >= 1) {
                update();
                repaint();

                deltaTime--;
                frames++;
            }
            if (timer >= 1000000000) {
                frames = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        player.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        tileM.draw(g2);

        // Collect drawables for Y-sorting
        List<Drawable> drawables = new ArrayList<>();

        // Add objects
        for (MapObject obj : objM.objects) {
            if (obj.worldX + gameTileSize * 2 > player.worldX - player.screenX &&
                obj.worldX - gameTileSize * 2 < player.worldX + player.screenX &&
                obj.worldY + gameTileSize * 2 > player.worldY - player.screenY &&
                obj.worldY - gameTileSize * 2 < player.worldY + player.screenY) {
                drawables.add(obj);
            }
        }

        // Add player
        drawables.add(player);

        drawables.sort(Comparator.comparingInt(Drawable::getSortY));

        // Draw sorted
        for (Drawable d : drawables) {
            int screenX, screenY;
            if (d instanceof Player) {
                screenX = player.screenX;
                screenY = player.screenY;
            } else {
                MapObject obj = (MapObject) d;
                screenX = obj.worldX - player.worldX + player.screenX;
                screenY = obj.worldY - player.worldY + player.screenY;
            }
            d.draw(g2, screenX, screenY);
        }

        // Debug: Draw collision rects
        g2.setColor(Color.RED);
        for (MapObject obj : objM.objects) {
            if (obj.collision) {
                int screenX = obj.worldX - player.worldX + player.screenX;
                int screenY = obj.worldY - player.worldY + player.screenY;
                if (obj.worldX + gameTileSize > player.worldX - player.screenX &&
                    obj.worldX - gameTileSize < player.worldX + player.screenX) {
                    g2.drawRect(screenX + obj.solidArea.x, screenY + obj.solidArea.y, obj.solidArea.width, obj.solidArea.height);
                }
            }
        }
        // Draw player collision rect
        g2.setColor(Color.BLUE);
        g2.drawRect(player.screenX + player.solidArea.x, player.screenY + player.solidArea.y, player.solidArea.width, player.solidArea.height);

        g2.dispose();
    }
}
