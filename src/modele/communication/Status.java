package modele.communication;
import utilitaires.Vect2D;

public class Status extends Message {

	private Vect2D position;
	
	/**
	 * Constructeur permettant de transmettre le status
	 * @param compte, compte unique
	 * @param position, position actuelle du Rover
	 */
	public Status(int compte, Vect2D position) {
		super(compte);
		this.position = position;
	}

	/**
	 * Accesseur informateur, pour obtenir la position du rover
	 * @return position
	 */
	public Vect2D getPosition() {
		return position;
	}

}
