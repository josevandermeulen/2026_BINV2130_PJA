package compteur_thread;

public class TestCompteurThreadWithRaceCondition {
    public static void main(String[] args) {
        CompteurThreadWithRaceCondition[] compteurs = {new CompteurThreadWithRaceCondition("Bolt", 10), new CompteurThreadWithRaceCondition("Jakson", 10), new CompteurThreadWithRaceCondition("Robert", 10), new CompteurThreadWithRaceCondition("Stéphanie", 10)};
        long start = System.currentTimeMillis();

        // TODO Question 10 : lancer les compteurs
        for (int i = 0; i < compteurs.length; i++) {
        }

        // TODO Question 10 : attendre la fin de l'exécution de tous les compteurs
        //  pour attendre un thread t, utiliser t.join();
        for (int i = 0; i < compteurs.length; i++) {
        }

        System.out.println("Le(la) gagnant(e) est " + CompteurThreadWithRaceCondition.getGagnant().getNom());
        long end = System.currentTimeMillis();
        long duration = end - start;
        System.out.println("Durée avant d'atteindre cette instruction de fin du programme principal : " + duration + " ms");
    }
}
