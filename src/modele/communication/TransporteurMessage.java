package modele.communication;
/**
 * Classe qui implémente le protocol de communication entre le Rover
 * et le Centre d'opération.
 * 
 * Il se base sur une interprétation libre du concept de Nack:
 * 	https://webrtcglossary.com/nack/
 *  
 * Les messages envoyés sont mémorisé. À l'aide du compte unique
 * le transporteur de message peut identifier les Messages manquant
 * dans la séquence et demander le renvoi d'un Message à l'aide du Nack.
 * 
 * Pour contourner la situation ou le Nack lui-même est perdu, le Nack
 * est renvoyé periodiquement, tant que le Message manquant n'a pas été reçu.
 * 
 * C'est également cette classe qui gère les comptes unique.
 * 
 * Les messages reçu sont mis en file pour être traité.
 * 
 * La gestion des messages reçu s'effectue comme une tâche s'exécutant indépendamment (Thread)
 * 
 * Quelques détails:
 *  - Le traitement du Nack a priorité sur tout autre message.
 *  - Un message NoOp est envoyé périodiquement pour s'assurer de maintenir
 *    une communication active et identifier les messages manquants en bout de séquence.
 * 
 * Services offerts:
 *  - TransporteurMessage
 *  - receptionMessageDeSatellite
 *  - run
 * 
 * @author Frederic Simard, ETS
 * @version Hiver, 2024
 */

import java.util.PriorityQueue;
import java.util.concurrent.locks.ReentrantLock;

public abstract class TransporteurMessage extends Thread {
	

	// lock qui protège la liste de messages reçu
	private ReentrantLock lock = new ReentrantLock();
	// compteur de message
	protected CompteurMessage compteurMsg;
	public PriorityQueue<Message>messageRecu=new PriorityQueue<>();
	public PriorityQueue<Message>messageEnvoye=new PriorityQueue<>();

	
	/**
	 * Constructeur, initialise le compteur de messages unique
	 */
	public TransporteurMessage() {

		 compteurMsg = new CompteurMessage();
	}
	
	/**
	 * Méthode gérant les messages reçu du satellite. La gestion se limite
	 * à l'implémentation du Nack, les messages spécialisé sont envoyés
	 * aux classes dérivés
	 * @param msg, message reçu
	 */
	public void receptionMessageDeSatellite(Message msg) {
		lock.lock();
		
		try {
			
			/*
			 * (6.3.3) Insérer votre code ici 
			 */
			messageRecu.add(msg);

			
		}finally {
			lock.unlock();
		}
	}

	@Override
	/**
	 * Tâche effectuant la gestion des messages reçu
	 */
	public void run() {
		
		int compteCourant = 0;
		
		while(true) {
			
			lock.lock();
			
			try {

				/*
				 * (6.3.4) Insérer votre code ici 
				 */
				int compteManquant=0;
				Message messageAEnvoyer;
				boolean nackEnvoye=false;
				System.out.println("transporteur, debut");

				if (messageRecu.isEmpty()){
					System.out.println("transporteur = message reçu est vide");
					NoOp noOp=new NoOp(compteurMsg.getCompteActuel());
					envoyerMessage(noOp);
					messageEnvoye.add(noOp);
					System.out.println("transporteur NoOp cree");
				}

				while(!messageRecu.isEmpty() && !nackEnvoye){
					System.out.println("Transporteur message");

					Message premierMessage = messageRecu.peek();

					if (premierMessage instanceof Nack) {
						System.out.println("transporteur , message est nack");

						compteManquant = ((Nack)premierMessage).getCompte();

						int finalCompteManquant = compteManquant;

						messageEnvoye.removeIf(msg->msg.getCompte()< finalCompteManquant || msg instanceof Nack);

						messageAEnvoyer = messageEnvoye.peek();

						envoyerMessage(messageAEnvoyer);
						if(messageAEnvoyer!=null)
						{
							System.out.println("message est envoye");
							envoyerMessage(messageAEnvoyer);
						}
						messageRecu.remove(premierMessage);

					} else if (premierMessage.getCompte() == compteCourant) {
						System.out.println("Transporteur, compte = compte courant");

						envoyerMessage(new Nack(compteCourant));

						nackEnvoye = true;

					} else if (premierMessage.getCompte() < compteCourant) {
						System.out.println("Transporteur, compte < compte courant" );

						messageRecu.poll();

					}

					else {
						System.out.println("transporteur, else");

						gestionnaireMessage(premierMessage);

						messageRecu.poll();

						compteCourant++;
					}


				}





			
			}finally{

				lock.unlock();

			}
			
			// pause, cycle de traitement de messages
			try {

				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * méthode abstraite utilisé pour envoyer un message
	 * @param msg, le message à envoyer
	 */
	abstract protected void envoyerMessage(Message msg);

	/**
	 * méthode abstraite utilisé pour effectuer le traitement d'un message
	 * @param msg, le message à traiter
	 */
	abstract protected void gestionnaireMessage(Message msg);

	

}
