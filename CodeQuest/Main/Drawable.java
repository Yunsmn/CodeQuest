package CodeQuest.Main;

import java.awt.Graphics2D;

public interface Drawable {
    int getSortY();
    void draw(Graphics2D g2, int screenX, int screenY);
}