package compteur_thread;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

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

    // TODO Question 9 : utilisez la variable de classe ci-dessous permettant de retenir le gagnant : le
    //  CompteurThread qui a fini de compter le premier.
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

    /**
     * Compte de 1 au maximum en affichant chaque valeur, avec une pause entre deux nombres.
     */
    public void count() {
        // TODO Question 9 : modifier ce code pour déterminer le gagnant (le 1er qui a fini de compter)
        //  et lors de l'enregistrement du gagnant, veuillez attendre 10 ms avant de l'enregistrer et afficher
        //  le nom du gagnant sous cette forme : "Le compteur gagnant est XXX à 2024-10-25T15:20:16.109588".
        IntStream.rangeClosed(1, max).forEach(i -> {
            System.out.println(nom + " : " + i);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(nom + " a finit de compter jusqu'à " + max + " à " + LocalDateTime.now());
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
