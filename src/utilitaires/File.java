package utilitaires;

public class File<E> {


    private class Noeud {
        private Noeud suivant;
        private E element;

        public Noeud(E element) {
            this.element = element;
        }

        public Noeud(Noeud suivant, E element) {
            this.suivant = suivant;
            this.element = element;
        }
    }

    private Noeud tete;

    public File() {
    }
    int taille = 0;
    public int getTaille(){
        return taille;
    }

    public void ajouterElement(E element) {

        if(estVide())
        {
            tete=new Noeud(element);
            return;
        }
        Noeud courant = tete;
        while (courant.suivant != null) {
            courant = courant.suivant;

        }
        courant.suivant=new Noeud(element);
    }

    public E enleverElement()throws Exception {
        if (estVide()) {
            throw new Exception("la file est vide");
        }
        E retour = tete.element;
        tete = tete.suivant;
        taille--;

        return retour;
    }
    public boolean estVide(){
        return tete == null;
    }
    public int getNbElement(){
        int compteur = 0;
        Noeud courant = tete;

        while(courant != null){
            compteur++;
            courant = courant.suivant;
        }
        return compteur;
    }
}
