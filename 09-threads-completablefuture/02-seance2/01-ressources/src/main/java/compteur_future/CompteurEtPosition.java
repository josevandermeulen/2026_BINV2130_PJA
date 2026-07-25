package compteur_future;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

public class CompteurEtPosition {
    private final String nom;
    private final int max;

    private static AtomicInteger ordreArrivee = new AtomicInteger(0);

    public CompteurEtPosition(String nom, int max) {
        this.nom = nom;
        this.max = max;
    }

    public String getNom() {
        return nom;
    }

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
    public CompletableFuture<Integer> countAndGetPositionAsync() {
        return CompletableFuture.supplyAsync(this::countAndGetPosition);
    }


    public static void resetOrdreArrivee() {
        ordreArrivee.set(0);
    }

    public static int getOrdreArrivee() {
        return ordreArrivee.get();
    }
}
