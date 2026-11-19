package compteur_thread;

/**
 * Programme de démonstration : lance plusieurs compteurs héritant de Thread et observe leur entrelacement.
 */
public class TestCompteurThread {

    // Question 3
    public static void main(String[] args) {
        CompteurThread[] compteurs = {
                new CompteurThread("Bolt", 10),
                new CompteurThread("Jakson", 10),
                new CompteurThread("Robert", 10),
                new CompteurThread("Stéphanie", 10)
        };

        long start = System.currentTimeMillis();
        for (int i = 0; i < compteurs.length; i++) {
            compteurs[i].start();
        }

        for (int i = 0; i < compteurs.length; i++) {
            try {
                compteurs[i].join();
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
