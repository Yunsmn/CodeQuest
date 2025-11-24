package CodeQuest.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main {
    public static void main(String[] args) {

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle("CodeQuest");

        GamePanel gamePanel = new GamePanel();
        
        // CREATE COMMAND INPUT PANEL
        CommandInputPanel commandPanel = new CommandInputPanel(gamePanel.commandParser);
        
        // Use BorderLayout to put command panel on the RIGHT
        JPanel container = new JPanel(new BorderLayout());
        container.add(gamePanel, BorderLayout.CENTER);
        container.add(commandPanel, BorderLayout.EAST);
        
        frame.add(container);
        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // ⭐ GLOBAL ESC KEY LISTENER - Works from anywhere in the window!
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(
            new KeyEventDispatcher() {
                @Override
                public boolean dispatchKeyEvent(KeyEvent e) {
                    // Only handle key press events
                    if (e.getID() == KeyEvent.KEY_PRESSED) {
                        
                        // ESC key for pause/unpause
                        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                            if (gamePanel.gameState == gamePanel.playState) {
                                gamePanel.gameState = gamePanel.pauseState;
                                System.out.println("⏸️  GAME PAUSED - Press ESC to resume");
                            } else if (gamePanel.gameState == gamePanel.pauseState) {
                                gamePanel.gameState = gamePanel.playState;
                                System.out.println("▶️  GAME RESUMED");
                            }
                            return true; // Event consumed
                        }
                        

                    }
                    
                    return false; // Let other keys pass through
                }
            }
        );

        gamePanel.startGameThread();
        
        // Start a timer to update the command queue label
        Timer updateTimer = new Timer(100, e -> commandPanel.updateQueueLabel());
        updateTimer.start();
        
        // Request focus on game panel at start
        SwingUtilities.invokeLater(() -> gamePanel.requestFocusInWindow());
    }
}
