package modele.communication;
/**
 * Classe spécialisant la Commande, qui hérite de Messaage.
 * 
 * Ce message contient la commande pour déplacer le Rover
 * 
 * Services offerts:
 *  - CmdDeplacerRover
 *  - getDestination
 * 
 * @author Frederic Simard, ETS
 * @version Hiver, 2024
 */

import utilitaires.Vect2D;

public class CmdDeplacerRover extends Commande {

	// membre contenant la position vers où effectuer le déplacement
	private Vect2D destionation;

	/**
	 * Constructeur, construit un message contenant une destination pour le
	 * déplacement
	 * @param compte, compte unique
	 * @param nouvellePosition, destination du déplacement
	 */
	public CmdDeplacerRover(int compte, Vect2D nouvellePosition) {
		super(compte, eCommande.DEPLACER_ROVER);
		this.destionation = nouvellePosition;
	}
	
	/**
	 * Accesseur informatrice pour obtenir la destination
	 * @return Vect2D, destination
	 */
	public Vect2D getDestination() {
		return destionation;
	}
	
}
