package CodeQuest.Main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ButtonGUI implements UIComponent {
    BufferedImage image;
    Rectangle rect;
    Runnable onClick;
    public ButtonGUI(BufferedImage image, Runnable onClick) {
        this.image = image;
        this.onClick = onClick;
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
