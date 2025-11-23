package CodeQuest.Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    GamePanel gamePanel;
    public boolean UpPressed,  DownPressed, LeftPressed, RightPressed;

    public KeyHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
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
