package CodeQuest.Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// Handles keyboard input for player movement and game controls
public class KeyHandler implements KeyListener {
    GamePanel gamePanel; // Reference to game panel for state checks
    public boolean UpPressed, DownPressed, LeftPressed, RightPressed; // Arrow key states

    // Constructor
    public KeyHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode(); // Get key code

        // Handle game over state keys
        if (gamePanel.gameState == gamePanel.gameOverState) {
            if (code == KeyEvent.VK_R) {
                gamePanel.restartGame(); // Restart on R key
                return;
            }
            if (code == KeyEvent.VK_ESCAPE) {
                gamePanel.gameState = gamePanel.menuState; // Return to menu
                gamePanel.healthSystem.resetHealth();
                return;
            }
            return; // Ignore other keys in game over state
        }

        // Handle play state arrow keys for movement
        if (gamePanel.gameState == gamePanel.playState) {
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
        }

        // Handle ESC key - toggle pause
        if (code == KeyEvent.VK_ESCAPE) {
            if (gamePanel.gameState == gamePanel.playState) {
                gamePanel.gameState = gamePanel.pauseState; // Pause game
            } else if (gamePanel.gameState == gamePanel.pauseState) {
                gamePanel.gameState = gamePanel.playState; // Unpause game
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode(); // Get key code
        // Reset arrow key states when released
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
