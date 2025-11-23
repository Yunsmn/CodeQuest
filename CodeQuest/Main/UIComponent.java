package CodeQuest.Main;

import java.awt.*;

public interface UIComponent {

    public void draw(Graphics2D g2);
    public void update(int mouseX, int mouseY);
    public boolean handleMouseInput(int mouseX, int mouseY);

}
