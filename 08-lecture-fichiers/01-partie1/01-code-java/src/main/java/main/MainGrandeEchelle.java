package main;

import domaine.Acces;
import util.LogReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

public class MainGrandeEchelle {

    private static final String DOSSIER = "logs-grand-volume";
    private static final int NB_FICHIERS = 100;
    private static final int NB_LIGNES_PAR_FICHIER = 10_000;

    public static void main(String[] args) throws IOException {
        if (!Files.exists(Path.of(DOSSIER))) {
            System.out.println("Génération de " + NB_FICHIERS + " fichiers de " + NB_LIGNES_PAR_FICHIER
                    + " accès dans " + DOSSIER + "... (une seule fois)");
            GenerateurLogs.genererGrandVolume(DOSSIER, NB_FICHIERS, NB_LIGNES_PAR_FICHIER);
        }

        AnalyseLogs analyse = new AnalyseLogs(new LogReader());

        long debutChargement = System.currentTimeMillis();
        List<Acces> acces = analyse.chargerTousLesAcces(DOSSIER);
        long dureeChargement = System.currentTimeMillis() - debutChargement;

        long debutStats = System.currentTimeMillis();
        Set<String> ipUniques = analyse.ipUniques(acces);
        analyse.nombreAccesParUtilisateur(acces);
        long dureeStats = System.currentTimeMillis() - debutStats;

        long debutRapport = System.currentTimeMillis();
        analyse.ecrireRapport(DOSSIER + "-rapport.txt", acces);
        long dureeRapport = System.currentTimeMillis() - debutRapport;

        System.out.println();
        System.out.println("Nombre d'accès chargés : " + acces.size() + " (" + dureeChargement + " ms)");
        System.out.println("Adresses IP uniques : " + ipUniques.size() + " (statistiques calculées en " + dureeStats + " ms)");
        System.out.println("Rapport écrit dans " + DOSSIER + "-rapport.txt (" + dureeRapport + " ms)");
    }

}
