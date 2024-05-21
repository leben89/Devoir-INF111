package vue.Composantes;
import utilitaires.Vect2D;

import javax.swing.*;
import java.awt.*;

public class DessinLigne extends JComponent {
    //Constante couleur
    private static final Color COLOR = Color.LIGHT_GRAY;

    // pour définir le type de ligne voulu
    private static Stroke Stroke = new BasicStroke(2,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL, 0,new float[]{9},0);

    // Constructeur par défaut
    public DessinLigne(){
        // appel du constructeur de la methode mère JComponent
        super();
    }
    // graphic veut dire pinceau
    public void draw(Graphics g, Vect2D x, Vect2D y) {
        // convertir le graphic en graphic 2D
        Graphics2D g2d = (Graphics2D) g.create();
        // définir quel type de couleur je veux
        g2d.setColor(COLOR);
        // définir quel type de ligne à tracer
        g2d.setStroke(Stroke);
        //tracer les lignes
        g2d.drawLine((int) x.getX(), (int) x.getY(), (int) y.getX(), (int) y.getY());
        // Range le pinceau
        g2d.dispose();
    }

    }

