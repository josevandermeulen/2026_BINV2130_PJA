package main;

import domaine.Bibliotheque;
import domaine.BibliothequeService;
import source.OdwbClient;
import source.SourceIndisponibleException;

/**
 * Petite démonstration de BibliothequeService sur les bibliothèques de Namur (5000), avec les
 * vraies données d'ODWB.
 *
 * Elle a besoin d'une connexion Internet : sans réseau, elle affiche « Source indisponible ». Et
 * son affichage peut changer d'un jour à l'autre, quand le jeu de données est mis à jour.
 *
 * Classe fournie : vous n'avez pas à la modifier.
 */
public class Main {

    private static final String NAMUR = "5000";

    public static void main(String[] args) {
        BibliothequeService service = new BibliothequeService(new OdwbClient());

        try {
            System.out.println("Bibliothèques de " + NAMUR + " :");
            for (Bibliotheque bibliotheque : service.parCodePostal(NAMUR)) {
                System.out.println("  " + bibliotheque);
            }

            System.out.println();
            System.out.println("La plus proche de la gare de Namur :");
            System.out.println("  " + service.plusProche(NAMUR, 50.4686, 4.8620));

            System.out.println();
            System.out.println("Thématique « bande » :");
            for (Bibliotheque bibliotheque : service.parThematique(NAMUR, "bande")) {
                System.out.println("  " + bibliotheque);
            }
        } catch (SourceIndisponibleException e) {
            System.out.println("Source indisponible : " + e.getMessage());
        }
    }
}
