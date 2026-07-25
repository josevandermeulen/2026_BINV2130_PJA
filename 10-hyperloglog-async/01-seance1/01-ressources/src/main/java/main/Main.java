package main;

import domaine.DefaultHasher;
import domaine.HyperLogLog;
import util.LogReader;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Main {

    public static void main(String[] args) {
        AnalyseIpAsync analyseAsync = new AnalyseIpAsync(new AnalyseLogs(new LogReader()));

        long debut = System.currentTimeMillis();
        CompletableFuture<Long> estimationFuture =
                analyseAsync.estimerDepuisDossierAsync("logs", new HyperLogLog(new DefaultHasher(), 4));

        System.out.println("Le programme principal continue pendant le chargement... ("
                + (System.currentTimeMillis() - debut) + " ms après le lancement)");

        System.out.println("Estimation HyperLogLog : " + estimationFuture.join()
                + " (obtenue après " + (System.currentTimeMillis() - debut) + " ms)");

        // "logs" réutilisé plusieurs fois pour la démonstration : dans un vrai projet, ce
        // seraient des dossiers différents (un par jour, un par serveur...)
        List<String> dossiers = List.of("logs", "logs", "logs");

        long debutSequentiel = System.currentTimeMillis();
        analyseAsync.estimerPlusieursDossiersSequentiellement(dossiers, new DefaultHasher());
        System.out.println("Temps séquentiel (" + dossiers.size() + " dossiers) : "
                + (System.currentTimeMillis() - debutSequentiel) + " ms");

        long debutParallele = System.currentTimeMillis();
        analyseAsync.estimerPlusieursDossiersAsync(dossiers, new DefaultHasher()).join();
        System.out.println("Temps parallèle (" + dossiers.size() + " dossiers) : "
                + (System.currentTimeMillis() - debutParallele) + " ms");
    }

}
