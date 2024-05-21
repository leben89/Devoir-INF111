package modele.environnement;
/**
 * Classe simulant la Lune.
 * 
 * Cette classe répond au demandes du Rover concernant l'information sur la Lune.
 * Présentement, il est possible de prendre des photos de la surface lunaire, la photo
 * prise dépend de la position du Rover.
 * 
 * Les photos doivent être lues par chunk.
 * 
 * La Lune est implémenté en LazySingleton
 * 
 * Services offerts:
 *  - getInstance
 *  - ouvrirFichierPhoto
 *  - lireChunkPhoto
 * 
 * @author Frederic Simard, ETS
 * @version Hiver, 2024
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import utilitaires.Vect2D;

public class Lune {

	private final int NB_CRATERES = 20;
	ArrayList<Cratere> crateres = new ArrayList<Cratere>();

	public static final int LIGNES = 10;
	File fichierPhoto = null;
	FileInputStream streamEntree; 
	
	Random rand = new Random();
	
	public static final Vect2D DIM_SITE = new Vect2D(200,200);
	public static final Vect2D CENTRE = new Vect2D(100,100);
	int DIVISION_GRILLE = 4;
	String[][] IMAGES_SURFACE= {{"lunar-surface-1.jpg","lunar-surface-2.jpg","lunar-surface-3.jpg","lunar-surface-4.jpg"},
					 			{"lunar-surface-5.jpg","lunar-surface-6.jpg","lunar-surface-7.jpg","lunar-surface-8.jpg"},
					 			{"lunar-surface-9.jpg","lunar-surface-8.jpg","lunar-surface-11.jpg","lunar-surface-12.jpg"},
					 			{"lunar-surface-13.jpg","lunar-surface-14.jpg","lunar-surface-15.jpg","lunar-surface-16.jpg"}};
	
	private static Lune instance = new Lune();
	
	/**
	 * Constructeur par défaut
	 */
	private Lune() {
	
		// popule la liste de cratères
		for(int i=0;i<NB_CRATERES;i++) {
			// initialise avec position aléatoire
			Cratere ceCratere = new Cratere(new Vect2D(rand.nextDouble()*DIM_SITE.getX(),rand.nextDouble()*DIM_SITE.getY()));
			// ajoute à la liste
			crateres.add(ceCratere);
		}
	}
	
	/**
	 * méthode pour obtenir la référence sur la Lune (LazySingleton)
	 * @return référence à la Lune
	 */
	public static Lune getInstance() {
		return instance;
	}
	
	/**
	 * Méthode permettant d'ouvrir une fichier photo, la photo prise
	 * dépend de la position du Rover.
	 * @param position, position du Rover
	 * @throws Exception, exception généré si l'on tente de prendre
	 * 					  une nouvelle photo alors que la précédente n'est
	 * 					  pas complétement transféré.
	 */
	public long ouvrirFichierPhoto(Vect2D position) throws Exception{
		
		// s'assure qu'aucune photo n'est déjà ouverte
		if(fichierPhoto != null) {
			throw new Exception("fichier photos déjà ouvert");
		}
		
		// calcule position du rover dans la grille
		int posXGrille = (int)Math.floor((position.getX())/(DIM_SITE.getX()/DIVISION_GRILLE));
		int posYGrille = (int)Math.floor((position.getY())/(DIM_SITE.getY()/DIVISION_GRILLE));		
		
		// ouvre la photo correspondante à la position du Rover
		fichierPhoto  = new File("images/" + IMAGES_SURFACE[posYGrille][posXGrille]);		
		streamEntree = new FileInputStream(fichierPhoto);
		return fichierPhoto.length();
	}

	/**
	 * méthode permettant de lire un chunk de la photo
	 * @param chunk (sortie), contenant du chunk
	 * @return longeur du chunk lu -1 si photo terminé
	 * @throws Exception, exception lancé si aucune photo en cours
	 */
	public int lireChunkPhoto(byte[] chunk) throws Exception{
		int chunkLength = 0;

		// s'assure qu'une photo est ouverte
		if(fichierPhoto == null) {
			throw new Exception("aucun fichier photos d'ouvert");
		}
		
		try {
			// lit un chunk de la photo
			chunkLength = streamEntree.read(chunk);
			
			// vérifie si la photo est terminé
			if(chunkLength == -1) {
				streamEntree.close();
				fichierPhoto = null;
			}
		} catch (IOException e1) {
			System.out.println("echec lecture photo");
		}
		return chunkLength;
	}	
	
	

	/**
	 * Méthode permettant d'obtenir une position aléatoire sur la lune
	 * @return Vect2 position aléatoire sur la lune
	 */
	public Vect2D obtenirPositionAlea(){
		Vect2D position = new Vect2D(rand.nextInt((int)DIM_SITE.getX()),
				                     rand.nextInt((int)DIM_SITE.getX()));
		
		return position;
	}
	
	/**
	 * Permet d'obtenir la liste de cratères
	 * @return ArrayList<Cratere> liste de cratères
	 */
	public ArrayList<Cratere> getCrateres() {
		return crateres;
	}
}
