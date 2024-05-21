package vue.Composantes;

import utilitaires.Vect2D;

import  javax.swing.*;
import java.awt.*;

public class DessinRover extends JComponent {

    private static final Color COLOR = Color.GREEN;
    public static final int RAYON = 15;

    public void draw(Graphics g, Vect2D position){
        g.setColor(COLOR);
        g.fillOval((int) position.getX(), (int) position.getY(), RAYON,RAYON);
    }
}
