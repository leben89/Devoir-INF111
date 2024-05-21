package vue;

import modele.CentreControle.CentreControle;
import modele.monObserver.MonObserver;
import utilitaires.Photos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GestionPhoto extends JPanel implements MonObserver {

    JPanel panneauGestionPhotos = new JPanel();
    JButton prendrePhoto = new JButton("Prendre Photo");
    JProgressBar chargePhoto = new JProgressBar(0,100);
    JList<String> listePhoto = new JList<>();

    Photos photos;

    /**
     * Creates a new <code>JPanel</code> with a double buffer
     * and a flow layout.
     */
    public GestionPhoto() {
        CentreControle.getInstance().getObservable().attacherObserver(this);
        photos=new Photos();
    }

    public Component initGestionPhotos () {

        panneauGestionPhotos.setLayout(new GridBagLayout());
        panneauGestionPhotos.setBackground(Color.DARK_GRAY);
        panneauGestionPhotos.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        CentreControle.getInstance().getObservable().attacherObserver(this);

        GridBagConstraints gbc=new GridBagConstraints();

        gbc.gridx=0;
        gbc.gridy=0;

        gbc.anchor=GridBagConstraints.NORTH;



        prendrePhoto.setPreferredSize(new Dimension(300,50));
        chargePhoto.setPreferredSize(new Dimension(300,50));
        chargePhoto.setStringPainted(true);
        listePhoto.setPreferredSize(new Dimension(500,500));

        prendrePhoto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CentreControle.getInstance().prendrePhoto();

            }
        });


        panneauGestionPhotos.add(prendrePhoto,gbc);
        gbc.gridy++;
        gbc.insets=new Insets(10,0,0,0);
        panneauGestionPhotos.add(chargePhoto,gbc);
        gbc.gridy++;
        panneauGestionPhotos.add(listePhoto,gbc);
        gbc.gridy++;

        return panneauGestionPhotos;
    }

    @Override
    public void avertir() {
        chargePhoto.setValue(CentreControle.getInstance().getProgresFichier());
        try{
            System.out.println(chargePhoto.getValue());
        }catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        listePhoto.setListData(photos.getRecu());
    }
}
