package CodeQuest.Main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class GUI {
    BufferedImage background;
    GamePanel gamePanel;
    Graphics2D g2;
    List<ButtonGUI> buttons;
    Rectangle bounds;
    boolean fixed = true;

    public GUI(GamePanel gamePanel, BufferedImage background, List<ButtonGUI> buttons, int x, int y ) {
        this.gamePanel = gamePanel;
        this.buttons = buttons;
        this.background = background;
        if (background != null) {
            this.bounds = new Rectangle(x, y, background.getWidth(), background.getHeight());
        }
        else  {
            this.bounds = new Rectangle(x, y);
        }
    }
    public GUI(GamePanel gamePanel, List<ButtonGUI> buttons, int x, int y) {
        this(gamePanel,null,buttons, x, y);
    }

    public GUI(GamePanel gamePanel) {
        this(gamePanel,null,null,0,0);
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(new Font("Arial", Font.BOLD, 20));

        if (gamePanel.gameState == gamePanel.playState) {

        } else if (gamePanel.gameState == gamePanel.pauseState) {
            drawPauseScreen();
        }

    }

    public void drawPauseScreen() {
        String text = "GAME PAUSE";
        int x;
        int length = (int)(g2.getFontMetrics().getStringBounds(text, g2).getWidth());
        x = gamePanel.screenWidth / 2 - length / 2;
        int y = gamePanel.screenHeight / 2 ;
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        g2.setColor(Color.BLACK); g2.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);
    }
}
