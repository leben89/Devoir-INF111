package modele.communication;

/**
 * Classe spécialisant le Message.
 * 
 * Ce message contient une commande générale.
 * 
 * Services offerts:
 *  - Commande
 *  - getCommande
 * 
 * @author Frederic Simard, ETS
 * @version Hiver, 2024
 */

public class Commande extends Message {

	private eCommande cmd = eCommande.NULLE;
	
	/**
	 * Constructeur requiert un compte unique et la commande à exécuter
	 * @param compte, compte unique
	 * @param cmd, commande à exécuter
	 */
	public Commande (int compte, eCommande cmd) {
		super(compte);
		this.cmd = cmd;
	}

	/**
	 * Accesseur informatrice pour obtenir la commande
	 * @return Vect2D, destination
	 */
	public eCommande getCommande() {
		return cmd;
	}
	
}
