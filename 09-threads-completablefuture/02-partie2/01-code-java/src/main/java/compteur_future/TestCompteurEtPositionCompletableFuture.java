package compteur_future;

import java.util.Arrays;

public class TestCompteurEtPositionCompletableFuture {
    private static long start;
    private static long end;
    private static long duration;

    public static void main(String[] args) {
        CourseCompteurs course = new CourseCompteurs();

        CompteurEtPosition[] compteurs = {new CompteurEtPosition("Bolt", 10), new CompteurEtPosition("Jakson", 10),
                new CompteurEtPosition("Robert", 10), new CompteurEtPosition("Stéphanie", 10)};


        System.out.println("0. Exécuter tous les compteurs et déterminer la position de manière synchrone");
        start = System.currentTimeMillis();


        Arrays.asList(compteurs).forEach(compteur -> System.out.println("Compteur : " + compteur.getNom() + " - Position : " + compteur.countAndGetPosition()));


        end = System.currentTimeMillis();
        duration = end - start;
        System.out.println("0. Durée d'exécution pour tous les compteurs de manière synchrone : " + duration + " ms");


        System.out.println("1. Récupérer les valeurs de toutes les futures countAndGetPositionAsync");
        CompteurEtPosition.resetOrdreArrivee();
        start = System.currentTimeMillis();

        course.attendreResultatsAsync(compteurs).join();

        end = System.currentTimeMillis();
        duration = end - start;
        System.out.println("1. Durée d'exécution : " + duration + " ms");


        System.out.println("2. Créer des futures à partir de countAndGetPosition");
        CompteurEtPosition.resetOrdreArrivee();
        start = System.currentTimeMillis();

        course.creerFuturesDepuisCountAndGetPosition(compteurs).join();

        end = System.currentTimeMillis();
        duration = end - start;
        System.out.println("2. Durée d'exécution : " + duration + " ms");
    }

}
