package CodeQuest.Main;

import CodeQuest.Entity.Player;
import CodeQuest.Tiles.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    final int gameTiles = 16;
    final int scale = 3;
    public final int gameTileSize = gameTiles * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 14;
    public final int gameScreenRow = 11;
    final int parserRow = 3;
    public final int screenWidth = gameTileSize * maxScreenCol;
    public final int screenHeight = gameTileSize * maxScreenRow;
    int fps = 60;
    public KeyHandler keyH = new KeyHandler(this);
    Thread gameThread;
    public Player player = new Player(this, keyH);

    TileManager tileM = new TileManager(this);

    public CollisionChecker collisionChecker = new CollisionChecker(this);

    public final int maxWorldCol = 25;
    public final int maxWorldRow = 25;
    public final int worldWidth = maxWorldCol * gameTileSize;
    public final int worldHeight = maxWorldRow * gameTileSize;




    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
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
        player.draw(g2);
        g2.dispose();
    }
}
