package main;

import domaine.Commande;
import domaine.Pizzeria;
import util.LecteurFichiers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Programme de démonstration : charge les commandes des fichiers du dossier commandes/, puis
 * affiche les statistiques calculées à partir d'elles et écrit le rapport.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        Pizzeria pizzeria = DonneesPizzeria.creerPizzeria();
        AnalyseCommandes analyse = new AnalyseCommandes(pizzeria, new LecteurFichiers());
        List<Commande> commandes = analyse.chargerToutesLesCommandes("commandes");

        System.out.println();
        System.out.println("Nombre de commandes valides : " + commandes.size());

        System.out.println();
        System.out.println("Chiffre d'affaires par jour :");
        analyse.chiffreAffairesParJour()
                .forEach((jour, montant) -> System.out.println("  " + jour + " : " + montant + " euros"));

        System.out.println();
        System.out.println("Pizzas vendues par catégorie :");
        analyse.pizzasVenduesParCategorie().entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entree -> System.out.println("  " + entree.getKey().getLibelle() + " : " + entree.getValue()));

        analyse.ecrireRapport("rapport.txt");
        System.out.println();
        System.out.println("Rapport écrit dans rapport.txt");
    }

}
