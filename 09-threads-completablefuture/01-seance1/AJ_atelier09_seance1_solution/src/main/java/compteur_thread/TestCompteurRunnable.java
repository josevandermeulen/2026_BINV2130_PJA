package compteur_thread;

/**
 * Programme de démonstration : lance plusieurs compteurs écrits en Runnable et observe leur entrelacement.
 */
public class TestCompteurRunnable {

    // Question 6
    public static void main(String[] args) {
        CompteurRunnable[] compteurs = {
                new CompteurRunnable("Bolt", 10),
                new CompteurRunnable("Jakson", 10),
                new CompteurRunnable("Robert", 10),
                new CompteurRunnable("Stéphanie", 10)
        };

        long start = System.currentTimeMillis();

        Thread[] threads = new Thread[compteurs.length];
        for (int i = 0; i < compteurs.length; i++) {
            threads[i] = new Thread(compteurs[i]);
            threads[i].start();
        }

        for (int i = 0; i < threads.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        long end = System.currentTimeMillis();
        long duration = end - start;
        System.out.println("Tout le monde a fini de compter !");
        System.out.println("Durée avant d'atteindre cette instruction de fin du programme principal : "
                + duration + " ms");
    }

}
