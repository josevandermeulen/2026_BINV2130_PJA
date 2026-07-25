package compteur_thread;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

// TODO Question 5 : faire implémenter l'interface Runnable à cette classe
//  et implémenter la méthode run

/**
 * Un compteur exécutable en parallèle, écrit en implémentant {@link Runnable}.
 * <p>
 * Variante à préférer à {@link CompteurThread} : implémenter Runnable laisse la classe libre
 * d'hériter d'autre chose, et sépare ce qui est exécuté du fil qui l'exécute.
 */
public class CompteurRunnable {

    private final String nom;
    private final int max;

    /**
     * Crée un compteur.
     *
     * @param nom le nom affiché devant chaque nombre
     * @param max le nombre jusqu'auquel compter
     */
    public CompteurRunnable(String nom, int max) {
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
     * Compte de 1 au maximum en affichant chaque valeur, avec une pause entre deux nombres.
     */
    public void count() {
        IntStream.rangeClosed(1, max).forEach(i -> {
            System.out.println(nom + " : " + i);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException("Compteur interrompu pendant l'attente", e);
            }
        });
        System.out.println(nom + " a finit de compter jusqu'à " + max + " à " + LocalDateTime.now());
    }

}
