package CodeQuest.Main;

import CodeQuest.Entity.Player;
import CodeQuest.Tiles.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    final int GameTiles = 16;
    final int scale = 3;
    public final int GameTileSize = GameTiles * scale;
    public final int MaxScreenCol = 16;
    public final int MaxScreenRow = 14;
    public final int GameScreenRow = 11;
    final int ParserRow = 3;
    public final int ScreenWidth = GameTileSize * MaxScreenCol;
    public final int ScreenHeight = GameTileSize * MaxScreenRow;
    int FPS = 60;
    public KeyHandler keyH = new KeyHandler();
    Thread GameThread;
    public Player player = new Player(this, keyH);

    TileManager TileM = new TileManager(this);

    public CollisionChecker CheckeColl = new CollisionChecker(this);

    public final int MaxWorldCol = 50;
    public final int MaxWorldRow = 50;
    public final int WorldWidth = MaxWorldCol*GameTileSize;
    public final int WorldHeight = MaxWorldRow*GameTileSize;




    public GamePanel() {
        this.setPreferredSize(new Dimension(ScreenWidth, ScreenHeight));
        this.setBackground(Color.black);
        this.setLayout(null);
        this.setFocusable(false);
        this.setVisible(true);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    void StartGameThread() {
        GameThread = new Thread(this);
        GameThread.start();
    }

    @Override
    public void run() {

        double DrawInterval = (double) 1000000000 / FPS;
        double deltaTime = 0;
        long lastTime = System.nanoTime();
        long now;
        long timer = 0;
        int frames = 0;

        while (GameThread != null) {

            now = System.nanoTime();
            deltaTime += (now - lastTime) / DrawInterval;
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
        TileM.draw(g2);
        player.draw(g2);
        g2.dispose();
    }
}
