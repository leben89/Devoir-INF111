package vue;

import modele.CentreControle.CentreControle;
import modele.environnement.Cratere;
import modele.environnement.Lune;
import modele.monObserver.MonObserver;
import utilitaires.Vect2D;
import vue.Composantes.DessinCratere;
import vue.Composantes.DessinLigne;
import vue.Composantes.DessinRover;
import vue.Composantes.DessinTexte;

import javax.swing.*;
import java.awt.*;

public class VisuelRover extends JPanel implements MonObserver {

    private DessinCratere dessinCratere = new DessinCratere();
    private DessinLigne dessinLigne = new DessinLigne();
    private DessinRover dessinRover = new DessinRover();
    private DessinTexte dessinTexte = new DessinTexte();


    public VisuelRover() {}

    private Vect2D convertirPositionToPixel (Vect2D vect2D){

        double x = (vect2D.getX()*this.getSize().getWidth())/ Lune.DIM_SITE.getX();
        double y = (vect2D.getY()*this.getSize().getHeight())/Lune.DIM_SITE.getY();

        return new Vect2D(x,y);
    }

    private double convertirLongueurToPixel (double longueur) {

        return ((longueur*this.getSize().getWidth()/Lune.DIM_SITE.getX())+(this.getSize().getHeight()/Lune.DIM_SITE.getY()))/2;
    }

    public Component initPanneauRover() {

        setLayout(new BorderLayout());
        setBackground (Color.WHITE);
        CentreControle.getInstance().getObservable().attacherObserver(this);
        setPreferredSize( new Dimension(600,400));

        return this;

    }

    @Override
    protected void paintComponent(Graphics g) {

        double largeur = getSize().getWidth()/Lune.LIGNES;
        double hauteur =  getSize().getHeight()/Lune.LIGNES;
        System.out.println("création des variables largeur et hauteur");

        for(int i=0; i<Lune.LIGNES+1; i++){

            dessinLigne.draw(g, new Vect2D(largeur*i, 0), new Vect2D(largeur*i,getSize().getHeight()));
            dessinTexte.setTexte("");
            dessinTexte.draw(g, new Vect2D(largeur*i,20), ""+i*20);
            System.out.println("Dessin des lignes verticales");
        }

        for(int i=0; i<Lune.LIGNES+1; i++) {

            dessinLigne.draw(g, new Vect2D(getSize().getWidth(), hauteur*i), new Vect2D(0,hauteur*i));
            dessinTexte.draw(g, new Vect2D(0,hauteur*i - 5), "" +i*20);

            System.out.println("Dessin des lignes horizontales");

            }

        for(Cratere cratere:Lune.getInstance().getCrateres()){

            dessinCratere.draw(g, convertirPositionToPixel(cratere.getPosition()), (int) convertirLongueurToPixel(cratere.getRayon()));

        }

        dessinRover.draw(g, convertirPositionToPixel(CentreControle.getInstance().getPositionRover()));


    }

    @Override
    public void avertir() {
        dessinRover.repaint((int)convertirLongueurToPixel(CentreControle.getInstance().getPositionRover().getX()),(int) convertirLongueurToPixel (CentreControle.getInstance().getPositionRover().getY()), (int) convertirLongueurToPixel(DessinRover.RAYON), (int)convertirLongueurToPixel(DessinRover.RAYON));

    }
}
