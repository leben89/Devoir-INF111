package modele.CentreControle;

import modele.communication.*;
import modele.monObserver.MonObservable;
import modele.monObserver.MonObserver;
import modele.satelliteRelai.SatelliteRelai;
import utilitaires.Vect2D;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CentreControle extends TransporteurMessage {

    // référence au fichier et stream permettant d'enregistrer les photos
    // reçu dans un fichier
    private File photo = null;
    FileOutputStream streamSortie;
    int compteurPhoto = 0;

    Vect2D positionRover = null;



    double progresFichier = 0.0;
    double tailleCourante = 0.0;
    // référence sur le centre de contrôle (LazySingleton)
    private static CentreControle instance = new CentreControle();

    SatelliteRelai satelliteRelai;
    MonObservable observable=new MonObservable() {
        /**
         * méthode pour attacher un Observer
         *
         * @param observer
         */
        @Override
        public void attacherObserver(MonObserver observer) {
            super.attacherObserver(observer);
        }

        /**
         * méthode pour avertir tous les observers
         */
        @Override
        public void avertirLesObservers() {

            super.avertirLesObservers();
        }
    };

    private CentreControle(){

        super();
    }

    /**
     * Méthode permettant d'obtenir une référence sur le centre de contrôle
     * @return une référence sur le centre de contrôle
     */
    public static CentreControle getInstance() {

        return instance;
    }

    /**
     *
     * @param satellite, reference au satellite
     */
    public void attacherSatellite(SatelliteRelai satellite) {

        this.satelliteRelai = satellite;
    }

    public Vect2D getPositionRover() {
        return positionRover;
    }

    public MonObservable getObservable() {
        return observable;
    }
    public int getProgresFichier() {
        return (int) progresFichier;
    }


    /**
     * Méthode envoyant la commande de prendre une photo
     */
    public void prendrePhoto() {

        System.out.println("Prendre Photo");
        Commande msgCmd = new Commande(compteurMsg.getCompteActuel(), eCommande.PRENDRE_PHOTOS);
        satelliteRelai.envoyerMessageVersRover(msgCmd);
        messageEnvoye.add(msgCmd);

        progresFichier = 0;
        observable.avertirLesObservers();
    }

    /**
     * Méthode envoyant la commande de déplacer le Rover
     * @param posX position en X
     * @param posY position en Y
     */
    public void deplacerRover(double posX,double posY) {
        Commande msgCmd = new CmdDeplacerRover(compteurMsg.getCompteActuel(),new Vect2D(posX,posY));
        satelliteRelai.envoyerMessageVersRover(msgCmd);
        messageEnvoye.add(msgCmd);
    }

    /**
     * méthode abstraite utilisé pour envoyer un message
     *
     * @param msg
     */
    @Override
    public void envoyerMessage(Message msg) {
        System.out.println("centre controle, envoyer message");

        satelliteRelai.envoyerMessageVersRover(msg);
        TransporteurMessage transporteurMessage=this;
        transporteurMessage.messageEnvoye.add(msg);

    }

    /**
     * méthode abstraite utilisé pour effectuer le traitement d'un message
     *
     * @param msg
     */
    @Override
   public void gestionnaireMessage(Message msg) {
        // vérifie s'il s'agit d'un message contenant un morceau d'image
        if(msg instanceof MorceauImage) {

            // oui, on cast le message en MorceauImage
            MorceauImage morceauIm = (MorceauImage)msg;

            // on écrit le morceau dans un fichier
            try {

                // s'il ne s'agit pas de la fin
                if(morceauIm.getFin()==false) {


                    // si aucun fichier n'est ouvert
                    if(photo==null) {

                        System.out.println("Reception d'une photo, début");
                        tailleCourante = 0.0;

                        // on ouvre un fichier
                        photo = new File("photos/image"+compteurPhoto+".jpg");
                        streamSortie = new FileOutputStream(photo);
                    }
                    // on écrit le morceau dans un fichier ouvert
                    streamSortie.write(morceauIm.getMorceau());


                    tailleCourante += (double)morceauIm.getMorceau().length;
                    progresFichier = (tailleCourante)/morceauIm.getTailleTotale()*100;
                    observable.avertirLesObservers();

                }else {
                    // ferme le fichier
                    streamSortie.close();
                    photo = null;
                    compteurPhoto++;

                    System.out.println("Reception d'une photo, terminé");

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(msg instanceof Status) {
            Status msgStatus = (Status)msg;
            System.out.println("Status reçu");
            System.out.println("    position du Rover: " + msgStatus.getPosition());
            positionRover = msgStatus.getPosition();
            observable.avertirLesObservers();
        }

    }
}
