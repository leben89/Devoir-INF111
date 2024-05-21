package modele.monObserver;

/**
 * Classe abstraite du patron Observable
 * @author Fred Simard | ETS
 * @version ETE 2018 - TP2
 */

import java.util.ArrayList;

public abstract class MonObservable {

	// liste des observers
	ArrayList<MonObserver> observers = new ArrayList<MonObserver>();
	
	/**
	 * méthode pour attacher un Observer
	 * @param observer
	 */
	public void attacherObserver(MonObserver observer){
		observers.add(observer);
	}
	
	/**
	 * méthode pour avertir tous les observers
	 */
	public void avertirLesObservers(){
		try {
			for(MonObserver observer:observers){
				observer.avertir();
			}
		}
		catch(Exception e) {
			return;
		}
	}
		
}
