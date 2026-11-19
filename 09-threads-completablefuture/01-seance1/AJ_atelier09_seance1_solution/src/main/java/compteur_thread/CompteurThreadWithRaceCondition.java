package compteur_thread;

import java.time.LocalDateTime;

/**
 * Le même compteur, doté d'une variable de classe retenant qui a fini le premier.
 *
 * Cette variable partagée est le siège de la situation de compétition étudiée : sans
 * synchronisation, deux threads peuvent la lire vide au même instant et se déclarer tous deux
 * gagnants.
 */
public class CompteurThreadWithRaceCondition extends Thread {

    private final String nom;
    private final int max;

    //Cette variable de classe permet de retenir quel CompteurThread
    //a fini de compter le premier.
    private static CompteurThreadWithRaceCondition gagnant;

    /**
     * Crée un compteur.
     *
     * @param nom le nom affiché devant chaque nombre
     * @param max le nombre jusqu'auquel compter
     */
    public CompteurThreadWithRaceCondition(String nom, int max) {
        this.nom = nom;
        this.max = max;
    }

    /**
     * Renvoie le nom du compteur.
     *
     * @return le nom du compteur
     */
    public String getNom() {
        return nom;
    }

    /**
     * Lance le comptage dans le thread courant.
     */
    @Override
    public void run() {
        count();
    }

    // Question 9
    /**
     * Compte de 1 au maximum avec une pause entre deux nombres, puis s'enregistre comme gagnant
     * si aucun autre compteur ne l'a fait avant lui.
     */
    public void count() {
        for (int i = 1; i <= max; i++) {
            System.out.println(nom + " : " + i);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException("Compteur interrompu pendant l'attente", e);
            }
        }
        System.out.println(nom + " a fini de compter jusqu'à " + max + " à " + LocalDateTime.now());

        // Correction de la race condition : le test « personne n'a encore gagné ? » et
        // l'enregistrement du gagnant forment un tout (atomique). Sans ce bloc synchronized,
        // plusieurs compteurs lisent gagnant à null pendant la pause et se déclarent tous gagnants.
        synchronized (CompteurThreadWithRaceCondition.class) {
            if (gagnant == null) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException("Compteur interrompu pendant l'attente", e);
                }
                gagnant = this;
                System.out.println("Le compteur gagnant est " + nom + " à " + LocalDateTime.now());
            }
        }
    }

    /**
     * Renvoie le compteur qui a fini le premier.
     *
     * Sans synchronisation autour de son affectation, deux compteurs peuvent se croire gagnants :
     * c'est précisément le défaut que la question fait observer.
     *
     * @return le compteur gagnant, ou null si aucun n'est encore arrivé
     */
    public static CompteurThreadWithRaceCondition getGagnant() {
        return gagnant;
    }
}
