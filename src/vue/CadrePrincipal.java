package vue;


import modele.CentreControle.CentreControle;
import modele.communication.CmdDeplacerRover;
import modele.communication.Message;
import modele.communication.Status;
import modele.monObserver.MonObserver;
import utilitaires.Vect2D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CadrePrincipal extends JFrame implements Runnable, MonObserver {
    JFrame cadrePrincipal = new JFrame();

    JPanel panneauPrincipal = new JPanel();
    VisuelRover visuelRover=new VisuelRover();
    JPanel panneauConsole = new JPanel();
    JPanel panneauCmdDeplacement = new JPanel();
    PositionCourante positionCourante = new PositionCourante();
    GestionPhoto gestionPhoto = new GestionPhoto();



    public CadrePrincipal () {

        initFenetre();
        initContenu();
        setVisible(true);


    }



    private class PositionCourante extends JPanel {
        JLabel positionCouranteX = new JLabel();
        JLabel positionCouranteY = new JLabel();


        public PositionCourante () {
            setLayout( new GridLayout(2,1));

            if(CentreControle.getInstance().getPositionRover()!=null)
            {
                setCoordonnee(CentreControle.getInstance().getPositionRover().getX(),CentreControle.getInstance().getPositionRover().getY());
            }
            else{

                positionCouranteX.setText("Pos courante X: xxx");
                positionCouranteX.setHorizontalAlignment(SwingConstants.CENTER);
                positionCouranteY.setText("Pos courante Y: yyy");
                positionCouranteY.setHorizontalAlignment(SwingConstants.CENTER);

                for(Message msg:CentreControle.getInstance().messageRecu)
                {
                    if(msg instanceof Status)
                    {
                        setCoordonnee(((Status) msg).getPosition().getX(),((Status) msg).getPosition().getY());
                    }
                }
            }

            setPreferredSize(new Dimension(300,100));

            add(positionCouranteX);
            add(positionCouranteY);
        }
        public void setCoordonnee(double x, double y)
        {
            positionCouranteX.setText("Pos courante X: " +x);
            positionCouranteX.setHorizontalAlignment(SwingConstants.CENTER);
            positionCouranteY.setText("Pos courante Y: "+y);
            positionCouranteY.setHorizontalAlignment(SwingConstants.CENTER);
        }

    }
    private class NouvellePositionRover extends JPanel {

        JLabel nouvellePositionRover = new JLabel();
        JTextField nouvellePositionRoverValeur = new JTextField();

        public NouvellePositionRover(String texte) {
            setLayout(new GridLayout(1,2));

            nouvellePositionRover.setText(texte);
            nouvellePositionRover.setHorizontalAlignment(SwingConstants.RIGHT);
            nouvellePositionRoverValeur.setColumns(5);
            nouvellePositionRoverValeur.setHorizontalAlignment(SwingConstants.LEFT);

            setPreferredSize(new Dimension(400,100));

            add(nouvellePositionRover);
            add(nouvellePositionRoverValeur);


        }
        public boolean estValide()
        {
            try{
                Integer.parseInt(nouvellePositionRoverValeur.getText());
                return true;
            }
            catch (NumberFormatException e)
            {
                return false;
            }
        }


    }

    private void initFenetre() {

        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
    private void initContenu(){

            setContentPane(panneauPrincipal);
            panneauPrincipal.setLayout(new BorderLayout());
            panneauPrincipal.setBackground(Color.BLACK);

            panneauPrincipal.add(visuelRover.initPanneauRover(), BorderLayout.CENTER);
            panneauPrincipal.add(initConsole(), BorderLayout.LINE_END);
    }




    private Component initConsole(){

        panneauConsole.setLayout(new GridLayout(2,1));
        panneauConsole.setBackground(Color.PINK);
        panneauConsole.setPreferredSize(new Dimension(800,0));


        panneauConsole.add(initCmdDeplacement());
        panneauConsole.add(gestionPhoto.initGestionPhotos());

        return panneauConsole;

    }

    public Component initCmdDeplacement () {

        panneauCmdDeplacement.setLayout(new GridBagLayout());
        panneauCmdDeplacement.setBackground(Color.darkGray);
        panneauCmdDeplacement.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        CentreControle.getInstance().getObservable().attacherObserver(this);

        GridBagConstraints gbc=new GridBagConstraints();

        gbc.gridx=0;
        gbc.gridy=0;
        CentreControle.getInstance().getObservable().attacherObserver(this);

        gbc.anchor=GridBagConstraints.NORTH;


        NouvellePositionRover nouvellePositionRoverX = new NouvellePositionRover("Position Cible X:");
        NouvellePositionRover nouvellePositionRoverY = new NouvellePositionRover("Position Cible Y:");

        JButton deplacerRover = new JButton("Deplacer Rover");
        deplacerRover.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!nouvellePositionRoverX.nouvellePositionRoverValeur.getText().isEmpty()&&!nouvellePositionRoverY.nouvellePositionRoverValeur.getText().isEmpty())
                {
                    if(nouvellePositionRoverX.estValide()&&nouvellePositionRoverY.estValide())
                    {
                        int x=Integer.parseInt(nouvellePositionRoverX.nouvellePositionRoverValeur.getText());
                        int y= Integer.parseInt(nouvellePositionRoverY.nouvellePositionRoverValeur.getText());
                       CentreControle.getInstance().deplacerRover(x,y);
//                        positionCourante.setCoordonnee(x,y);

                    }
                    else {
                        JOptionPane.showMessageDialog(cadrePrincipal,"Valeur invalide");
                    }

                }
                else{
                    JOptionPane.showMessageDialog(cadrePrincipal, "Valeur vide");
                }

            }
        });

        deplacerRover.setPreferredSize(new Dimension(500,100));
        panneauCmdDeplacement.add(positionCourante,gbc);
        gbc.gridy++;
        gbc.insets = new Insets(10,0,0,0);
        panneauCmdDeplacement.add(nouvellePositionRoverX,gbc);
        gbc.gridy++;
        panneauCmdDeplacement.add(nouvellePositionRoverY,gbc);
        gbc.gridy++;
        panneauCmdDeplacement.add(deplacerRover,gbc);


        return panneauCmdDeplacement;
    }




    @Override

    public void run() {

        setVisible(true);

    }
    @Override
    public void avertir() {
        positionCourante.setCoordonnee(CentreControle.getInstance().getPositionRover().getX(),CentreControle.getInstance().getPositionRover().getY());

    }
}
