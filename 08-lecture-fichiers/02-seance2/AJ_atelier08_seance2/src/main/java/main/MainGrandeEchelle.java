package main;

import domaine.Commande;
import domaine.Pizzeria;
import util.LecteurFichiers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Programme de démonstration à grande échelle : génère un gros volume de commandes, puis mesure ce
 * que l'analyse en tire — et ce qu'elle coûte.
 */
public class MainGrandeEchelle {

    private static final String DOSSIER = "commandes-grand-volume";

    private static final int NB_FICHIERS = 100;

    private static final int NB_LIGNES_PAR_FICHIER = 2_000;

    public static void main(String[] args) throws IOException {
        if (!Files.exists(Path.of(DOSSIER))) {
            System.out.println("Génération de " + NB_FICHIERS + " fichiers de " + NB_LIGNES_PAR_FICHIER
                    + " commandes dans " + DOSSIER + "... (une seule fois)");
            GenerateurCommandes.genererGrandVolume(DOSSIER, NB_FICHIERS, NB_LIGNES_PAR_FICHIER);
        }

        Pizzeria pizzeria = DonneesPizzeria.creerPizzeria();
        AnalyseCommandes analyse = new AnalyseCommandes(pizzeria, new LecteurFichiers());

        long debutChargement = System.currentTimeMillis();
        List<Commande> commandes = analyse.chargerToutesLesCommandes(DOSSIER);
        long dureeChargement = System.currentTimeMillis() - debutChargement;

        long debutStats = System.currentTimeMillis();
        int nbJours = analyse.chiffreAffairesParJour().size();
        analyse.pizzasVenduesParCategorie();
        long dureeStats = System.currentTimeMillis() - debutStats;

        long debutRapport = System.currentTimeMillis();
        analyse.ecrireRapport(DOSSIER + "-rapport.txt");
        long dureeRapport = System.currentTimeMillis() - debutRapport;

        System.out.println();
        System.out.println("Nombre de commandes chargées : " + commandes.size() + " (" + dureeChargement + " ms)");
        System.out.println("Nombre de jours : " + nbJours + " (statistiques calculées en " + dureeStats + " ms)");
        System.out.println("Rapport écrit dans " + DOSSIER + "-rapport.txt (" + dureeRapport + " ms)");
    }

}
