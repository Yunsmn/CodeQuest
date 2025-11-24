package CodeQuest.Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    GamePanel gamePanel;
    public boolean UpPressed, DownPressed, LeftPressed, RightPressed;

    public KeyHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        
        // Arrow keys for movement
        if (code == KeyEvent.VK_UP) {
            UpPressed = true;
        }
        if (code == KeyEvent.VK_DOWN) {
            DownPressed = true;
        }
        if (code == KeyEvent.VK_LEFT) {
            LeftPressed = true;
        }
        if (code == KeyEvent.VK_RIGHT) {
            RightPressed = true;
        }
        
        // ESC key for pause/unpause
        if (code == KeyEvent.VK_ESCAPE) {
            if (gamePanel.gameState == gamePanel.playState) {
                gamePanel.gameState = gamePanel.pauseState;
                System.out.println("⏸️  GAME PAUSED - Press ESC to resume");
            } else if (gamePanel.gameState == gamePanel.pauseState) {
                gamePanel.gameState = gamePanel.playState;
                System.out.println("▶️  GAME RESUMED");
            }
        }
        

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_UP) {
            UpPressed = false;
        }
        if (code == KeyEvent.VK_DOWN) {
            DownPressed = false;
        }
        if (code == KeyEvent.VK_LEFT) {
            LeftPressed = false;
        }
        if (code == KeyEvent.VK_RIGHT) {
            RightPressed = false;
        }
    }
}
