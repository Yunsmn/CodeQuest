package CodeQuest.Main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class GUI implements UIComponent {
    BufferedImage background;
    GamePanel gamePanel;
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

    @Override
    public void draw(Graphics2D g2) {

    }

    @Override
    public void update(int mouseX, int mouseY) {

    }

    @Override
    public boolean handleMouseInput(int mouseX, int mouseY) {
        return false;
    }
}
