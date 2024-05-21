package programme;

import Rover.Rover;
import modele.CentreControle.CentreControle;
import modele.communication.Message;
import modele.environnement.Lune;
import modele.satelliteRelai.SatelliteRelai;
import utilitaires.Vect2D;
import utilitaires.File;
import vue.CadrePrincipal;

import javax.swing.*;

public class ProgrammePrincipal {

	/**
	 * Programme principale, instancie les éléments de la simulation,
	 * les lie entre eux, puis lance la séquence de test.
	 *
	 * @param args, pas utilisé
	 */
	public static void main(String[] args) {
		Lune lune= Lune.getInstance();

		SatelliteRelai satellite = new SatelliteRelai();
		Rover rover=new Rover(satellite,lune,lune.obtenirPositionAlea());
		CentreControle centreControle= CentreControle.getInstance();
		centreControle.attacherSatellite(satellite);

		satellite.LierRover(rover);
		satellite.LierCentrOP(centreControle);

		Thread roverTache=new Thread(rover);
		Thread centreOP=new Thread(centreControle);
		satellite.start();
		roverTache.start();
		centreOP.start();
		SwingUtilities.invokeLater(new CadrePrincipal());
		//validationVect2D();
		//validationFile();

	}

//	public static void validationVect2D() {
//		Vect2D vect2D = new Vect2D();
//		vect2D.setX(14);
//		vect2D.setY(35);
//		System.out.printf(vect2D.toString());
//		System.out.println();
//		Vect2D vect2D1 = new Vect2D();
//		vect2D1.setX(4);
//		vect2D1.setY(5);
//		System.out.printf(vect2D1.toString());
//		System.out.println();
//		Vect2D vect0 = vect2D1.calculerDiff(vect2D);
//		System.out.printf(vect0.toString());
//		System.out.println();
//		System.out.println("Diviser");
//		vect2D.diviser(7);
//		System.out.printf(vect2D.toString());
//		System.out.println();
//		vect2D.ajouter(3, 1);
//		System.out.printf(vect2D.toString());
//		System.out.println();
//		double answer = vect2D.getAngle(vect2D.getX(), vect2D.getY());
//		System.out.println(answer);
//		double longueur = vect0.getLongueur(vect2D.getX(), vect2D.getY());
//		System.out.println(longueur);
//		boolean result = vect2D.equals(vect2D1);
//		System.out.println(result);
//	}
//
//	public static void validationFile() throws Exception {
//		System.out.println("File");
//		Vect2D vect = new Vect2D();
//		vect.setX(12);
//		vect.setY(13);
//		File file = new File();
//		file.ajouterElement(vect);
//		System.out.println("ajouter 1 vect");
//		System.out.println(file.getTaille());
//		Vect2D vect1 = new Vect2D();
//		vect1.setX(12);
//		vect1.setY(13);
//		file.ajouterElement(vect1);
//		System.out.println("ajouter 2 vect");
//		System.out.println(file.getTaille());
//		try {
//			System.out.println(file.enleverElement());
//		} catch (Exception e) {
//			System.out.println(" " + e.getMessage());
//		}
//		System.out.println(file.getTaille());
//
//
//	}
}
