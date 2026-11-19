package sync;

import java.time.LocalDateTime;

/**
 * Un compteur qui affiche les nombres de 1 à son maximum, en marquant une pause à chaque pas.
 *
 * Cette pause est ce qui rend l'exécution séquentielle sensible : lancer plusieurs compteurs en
 * parallèle ne divise pas le travail, il recouvre les attentes.
 */
public class Compteur {

    private final String nom;
    private final int max;

    /**
     * Crée un compteur.
     *
     * @param nom le nom affiché devant chaque nombre
     * @param max le nombre jusqu'auquel compter
     */
    public Compteur(String nom, int max) {
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
     * Renvoie le nombre jusqu'auquel ce compteur compte.
     *
     * @return le maximum du compteur
     */
    public int getMax() {
        return max;
    }

    /**
     * Compte de 1 au maximum en affichant chaque valeur, avec une pause entre deux nombres.
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
    }

    /* Optional method to measure the total sleep time
    public void count() {
        long sleepTime = 0;
        for (int i = 1; i <= max; i++) {
            long start = System.nanoTime();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException("Compteur interrompu pendant l'attente", e);
            }
            long end = System.nanoTime();
            sleepTime += end - start;
        }
        System.out.println(nom + " total sleep time: " + sleepTime / 1_000_000 + " ms");
    }
    */

}
