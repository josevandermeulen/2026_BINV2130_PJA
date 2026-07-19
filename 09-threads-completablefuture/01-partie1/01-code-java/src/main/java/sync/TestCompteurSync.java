package sync;

import sync.Compteur;

public class TestCompteurSync {
    public static void main(String[] args) {

        Compteur[] compteurs = {new Compteur("Bolt", 10), new Compteur("Jakson", 10),
                new Compteur("Robert", 10), new Compteur("Stéphanie", 10)};
        long start = System.currentTimeMillis();

        for (int i = 0; i < compteurs.length; i++) {
            compteurs[i].count();
        }

        long end = System.currentTimeMillis();
        long duration = end - start;
        System.out.println("Tout le monde a fini de compter !");
        System.out.println("Durée avant d'atteindre cette instruction de fin du programme principal : " + duration + " ms");
    }


}
