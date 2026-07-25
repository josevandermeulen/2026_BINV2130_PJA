package main;

import static org.junit.jupiter.api.Assertions.*;

import domaine.DefaultHasher;
import domaine.HyperLogLog;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import util.LogReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class AnalyseRouteursTest {

    @TempDir
    Path dossier;

    AnalyseRouteurs analyseRouteurs;

    @BeforeEach
    void setUp() throws IOException {
        Files.writeString(dossier.resolve("index.txt"), "routeur-00.log\nrouteur-01.log\n");
        Files.writeString(dossier.resolve("routeur-00.log"),
                "2026-11-23T08:00:00;alice;192.168.0.12\n"
                        + "2026-11-23T08:00:05;bob;10.0.0.7\n"
                        + "2026-11-23T08:00:09;alice;192.168.0.12\n");
        Files.writeString(dossier.resolve("routeur-01.log"),
                "2026-11-23T08:00:02;claire;10.0.0.7\n"
                        + "2026-11-23T08:00:11;denis;172.16.4.2\n");
        analyseRouteurs = new AnalyseRouteurs(new AnalyseLogs(new LogReader()), new DefaultHasher());
    }

    private HyperLogLog hllAvecValeurs(String... valeurs) {
        HyperLogLog hyperLogLog = new HyperLogLog(new DefaultHasher(), 4);
        for (String valeur : valeurs) {
            hyperLogLog.ajouter(valeur);
        }
        return hyperLogLog;
    }

    @Nested
    @DisplayName("Question 2 : un HyperLogLog local par fichier routeur")
    class Question2 {

        @Test
        void hllPourFichierEstimeCommeUnEstimateurConstruitALaMain() throws IOException {
            HyperLogLog hyperLogLog = analyseRouteurs.hllPourFichier(dossier.toString(), "routeur-00.log");

            assertEquals(hllAvecValeurs("192.168.0.12", "10.0.0.7").estimerCardinalite(),
                    hyperLogLog.estimerCardinalite());
        }
    }

    @Nested
    @DisplayName("Question 3 : fusion séquentielle des routeurs")
    class Question3 {

        @Test
        void fusionnerTousLesRouteursEstimeCommeUnEstimateurUnique() throws IOException {
            HyperLogLog fusion = analyseRouteurs.fusionnerTousLesRouteurs(dossier.toString());

            assertEquals(hllAvecValeurs("192.168.0.12", "10.0.0.7", "172.16.4.2").estimerCardinalite(),
                    fusion.estimerCardinalite());
        }
    }

    @Nested
    @DisplayName("Question 4 : un CompletableFuture par routeur")
    class Question4 {

        @Test
        void memeEstimationQueLaVersionSynchrone() throws IOException {
            HyperLogLog asynchrone = analyseRouteurs
                    .hllPourFichierAsync(dossier.toString(), "routeur-01.log").join();

            assertEquals(analyseRouteurs.hllPourFichier(dossier.toString(), "routeur-01.log").estimerCardinalite(),
                    asynchrone.estimerCardinalite());
        }
    }

    @Nested
    @DisplayName("Question 5 : fusion asynchrone de tous les routeurs")
    class Question5 {

        @Test
        void memeEstimationQueLaFusionSequentielle() throws IOException {
            HyperLogLog fusion = analyseRouteurs.fusionnerTousLesRouteursAsync(dossier.toString()).join();

            assertEquals(analyseRouteurs.fusionnerTousLesRouteurs(dossier.toString()).estimerCardinalite(),
                    fusion.estimerCardinalite());
        }

        @Test
        void resultatDeterministeSurPlusieursExecutions() throws IOException {
            long premiereExecution = analyseRouteurs
                    .fusionnerTousLesRouteursAsync(dossier.toString()).join().estimerCardinalite();
            long deuxiemeExecution = analyseRouteurs
                    .fusionnerTousLesRouteursAsync(dossier.toString()).join().estimerCardinalite();

            assertEquals(premiereExecution, deuxiemeExecution);
        }
    }

}
