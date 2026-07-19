package compteur_thread;

public class TestCompteurRunnable {

    public static void main(String[] args) {
        CompteurRunnable[] compteurs = {new CompteurRunnable("Bolt", 10), new CompteurRunnable("Jakson", 10), new CompteurRunnable("Robert", 10), new CompteurRunnable("Stéphanie", 10)};

        long start = System.currentTimeMillis();

        // TODO Question 6 : lancer chaque compteur dans un Thread construit à partir
        //  du Runnable (new Thread(compteur))
        for (int i = 0; i < compteurs.length; i++) {
        }

        // TODO Question 6 : attendre la fin de l'exécution de tous les threads (join)
        for (int i = 0; i < compteurs.length; i++) {
        }

        long end = System.currentTimeMillis();
        long duration = end - start;
        System.out.println("Tout le monde a fini de compter !");
        System.out.println("Durée avant d'atteindre cette instruction de fin du programme principal : " + duration + " ms");
    }

}
