package modele.environnement;
import java.util.Random;

/**
 * Classe qui définit un cratère lunaire
 * 
 * Services offerts:
 *  - Constructeur par paramètres
 *  - Accesseur informateur rayon et position
 * 
 * @author Frederic Simard, ETS
 * @version Hiver, 2019
 */
import utilitaires.Vect2D;

public class Cratere {

	private static final double MAX_RAYON = 10.0;
	private static final double MIN_RAYON = 1.0;
	
	private Random rand = new Random();
	
	private double rayon;
	private Vect2D position;
	
	/**
	 * Constructeur par paramètres
	 * @param rayon rayon du cratère
	 * @param position (Vect2D) position du cratère
	 */
	public Cratere (Vect2D position) {
		this.rayon = rand.nextDouble()*(MAX_RAYON-MIN_RAYON)+MIN_RAYON;
		this.position = position.clone();
	}
	
	/**
	 * Accesseur informateur, position
	 * @return double rayon du cratère
	 */
	public double getRayon() {
		return rayon;
	}

	/**
	 * Accesseur informateur, position
	 * @return Vect2D position du cratère
	 */
	public Vect2D getPosition() {
		return position;
	}
}
