package vue.Composantes;

import utilitaires.Vect2D;

import javax.swing.*;
import java.awt.*;

public class DessinTexte extends JComponent {

    private static final Font selectedFont = new Font("Times New Roman", Font.PLAIN, 25);

    private static final Color COLOR = Color.GRAY;

    private String texte;

    public DessinTexte(){
        super();
        setTexte("");
    }

    public DessinTexte(String texte){
        super();
        this.texte = texte;
    }

    public void draw(Graphics g, Vect2D position, String text) {
        g.setColor(COLOR);
        g.setFont(selectedFont);
        g.drawString(text, (int)position.getX(), (int) position.getY());
    }

    public void setTexte(String text){
        this.texte = text;
    }
}
