package compteur_thread;

/**
 * Programme de démonstration : fait courir plusieurs compteurs pour faire
 * apparaître la situation de compétition sur le gagnant.
 */
public class TestCompteurThreadWithRaceCondition {
    // Question 10
    public static void main(String[] args) {
        CompteurThreadWithRaceCondition[] compteurs = {
                new CompteurThreadWithRaceCondition("Bolt", 10),
                new CompteurThreadWithRaceCondition("Jakson", 10),
                new CompteurThreadWithRaceCondition("Robert", 10),
                new CompteurThreadWithRaceCondition("Stéphanie", 10)
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

        System.out.println("Le(la) gagnant(e) est " + CompteurThreadWithRaceCondition.getGagnant().getNom());
        long end = System.currentTimeMillis();
        long duration = end - start;
        System.out.println("Durée avant d'atteindre cette instruction de fin du programme principal : "
                + duration + " ms");
    }
}
