package modele.satelliteRelai;

/**
 * Classe simulant le satellite relai
 * 
 * Le satellite ne se contente que de transferer les messages du Rover vers le centre de contrôle
 * et vice-versa.
 * 
 * Le satellite exécute des cycles à intervale de TEMPS_CYCLE_MS. Période à
 * laquelle tous les messages en attente sont transmis. Ceci est implémenté à
 * l'aide d'une tâche (Thread).
 * 
 * Le relai satellite simule également les interférence dans l'envoi des messages.
 * 
 * Services offerts:
 *  - lierCentrOp
 *  - lierRover
 *  - envoyerMessageVersCentrOp
 *  - envoyerMessageVersRover
 * 
 * @author Frederic Simard, ETS
 * @version Hiver, 2024
 */

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

import Rover.Rover;
import modele.CentreControle.CentreControle;
import modele.communication.Message;
import utilitaires.File;

public class SatelliteRelai extends Thread{
	
	static final int TEMPS_CYCLE_MS = 500;
	static final double PROBABILITE_PERTE_MESSAGE = 0.15;
	
	ReentrantLock lock = new ReentrantLock();
	
	private Random rand = new Random();
	public File<Message> centreOp=new File<>();
	public File<Message> rover =new File<>();
	public CentreControle lierCentrOp;
	public Rover lierRover;

	public void LierCentrOP (CentreControle centreControle){

		this.lierCentrOp = centreControle;
	}

	public void LierRover (Rover rover){

		this.lierRover = rover;

	}
	/**
	 * Méthode permettant d'envoyer un message vers le centre d'opération
	 * @param msg, message à envoyer
	 */
	public void envoyerMessageVersCentrOp(Message msg) {


		
		lock.lock();
		
		try {

			/*
			 * (5.1) Insérer votre code ici 
			 */
			if (rand.nextDouble()>PROBABILITE_PERTE_MESSAGE){
				centreOp.ajouterElement(msg);
			}


			
		}finally {
			lock.unlock();
		}
	}
	
	/**
	 * Méthode permettant d'envoyer un message vers le rover
	 * @param msg, message à envoyer
	 */
	public void envoyerMessageVersRover(Message msg) {
		lock.lock();
		
		try {

			/*
			 * (5.2) Insérer votre code ici 
			 */
			if (rand.nextDouble()>PROBABILITE_PERTE_MESSAGE){
				rover.ajouterElement(msg);
			}
			
		}finally {
			lock.unlock();
		}
	}

	@Override
	public void run() {
		
		while(true) {
			
			/*
			 * (5.3) Insérer votre code ici 
			 */
			try{
				while(!centreOp.estVide())
				{
					lierCentrOp.gestionnaireMessage(centreOp.enleverElement());

				}
				while (!rover.estVide()){
					lierRover.gestionnaireMessage(rover.enleverElement());
				}
			}catch (Exception e)
			{
				e.getMessage();
			}


			// attend le prochain cycle
			try {
				Thread.sleep(TEMPS_CYCLE_MS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}



}
