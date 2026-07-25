package main;

import domaine.Acces;
import domaine.DefaultHasher;
import domaine.HyperLogLog;
import util.LogReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

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

        long debutExact = System.currentTimeMillis();
        long nombreExact = AnalyseIp.compterIpUniques(acces);
        long dureeExact = System.currentTimeMillis() - debutExact;

        HyperLogLog hyperLogLog = new HyperLogLog(new DefaultHasher(), 4);
        long debutEstimation = System.currentTimeMillis();
        long estimation = AnalyseIp.estimerIpUniques(acces, hyperLogLog);
        long dureeEstimation = System.currentTimeMillis() - debutEstimation;

        System.out.println();
        System.out.println("Nombre d'accès chargés : " + acces.size() + " (" + dureeChargement + " ms)");
        System.out.println("Comptage exact (Set<String>, une entrée par IP unique) : "
                + nombreExact + " en " + dureeExact + " ms");
        System.out.println("Estimation HyperLogLog (16 registres, 64 octets fixes) : "
                + estimation + " en " + dureeEstimation + " ms");
    }

}
