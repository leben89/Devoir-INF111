package vue.Composantes;

import utilitaires.Vect2D;

import javax.swing.*;
import java.awt.*;

public class DessinCratere extends JComponent {
    private static final Color BORDER_COLOR = Color.DARK_GRAY;

    private static final Color FILL_COLOR = Color.LIGHT_GRAY;

    private static final double STROKE_WIDTH = 0.9;

    public DessinCratere() { super();}


    public void draw(Graphics g, Vect2D position, double rayon) {
        g.setColor(BORDER_COLOR);

        g.fillOval((int) position.getX(), (int) position.getY(), (int) rayon, (int) rayon);
        rayon *= STROKE_WIDTH;

        g.setColor(FILL_COLOR);
        g.fillOval((int) position.getX(), (int) position.getY(), (int) rayon, (int) rayon);

    }
}
