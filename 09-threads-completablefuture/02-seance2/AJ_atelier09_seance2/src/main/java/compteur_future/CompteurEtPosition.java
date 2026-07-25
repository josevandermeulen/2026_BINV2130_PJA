package compteur_future;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Un compteur qui retient, à l'arrivée, son rang dans la course.
 * <p>
 * Le rang vient d'un compteur d'arrivées partagé par toutes les instances : c'est ce partage qui
 * donne son sens à la course, et qui impose de le remettre à zéro entre deux exécutions.
 */
public class CompteurEtPosition {
    private final String nom;
    private final int max;

    private static AtomicInteger ordreArrivee = new AtomicInteger(0);

    /**
     * Crée un compteur.
     *
     * @param nom le nom affiché devant chaque nombre
     * @param max le nombre jusqu'auquel compter
     */
    public CompteurEtPosition(String nom, int max) {
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
     * Compte jusqu'au maximum, puis renvoie le rang d'arrivée obtenu. Bloque jusqu'à la fin.
     *
     * @return le rang d'arrivée, 1 pour le premier compteur terminé
     */
    public Integer countAndGetPosition() {
        for (int i = 1; i <= max; i++) {
            System.out.println(nom + " : " + i);
            try {
                Thread.sleep(10);
                if (i == max) {
                    Thread.sleep(10);
                    return ordreArrivee.incrementAndGet();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Cette méthode permet de compter et de déterminer la position de manière asynchrone
     *
     * @return un CompletableFuture contenant la position
     */
    /**
     * Lance le comptage en tâche de fond et rend la main aussitôt.
     *
     * @return une future portant le rang d'arrivée
     */
    public CompletableFuture<Integer> countAndGetPositionAsync() {
        return CompletableFuture.supplyAsync(this::countAndGetPosition);
    }


    /**
     * Remet le compteur d'arrivées à zéro, avant une nouvelle course.
     */
    public static void resetOrdreArrivee() {
        ordreArrivee.set(0);
    }

    /**
     * Renvoie le nombre de compteurs déjà arrivés.
     *
     * @return le nombre d'arrivées enregistrées
     */
    public static int getOrdreArrivee() {
        return ordreArrivee.get();
    }
}
