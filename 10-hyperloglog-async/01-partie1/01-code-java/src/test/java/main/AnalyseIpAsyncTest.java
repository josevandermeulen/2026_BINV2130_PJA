package main;

import static org.junit.jupiter.api.Assertions.*;

import domaine.Acces;
import domaine.DefaultHasher;
import domaine.Hasher;
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
import java.util.ArrayList;
import java.util.List;

class AnalyseIpAsyncTest {

    @TempDir
    Path dossier;

    @TempDir
    Path dossier2;

    AnalyseIpAsync analyseAsync;

    Hasher hasher = new DefaultHasher();

    List<Acces> acces;
    List<Acces> acces2;

    @BeforeEach
    void setUp() throws IOException {
        Files.writeString(dossier.resolve("index.txt"), "acces.log\n");
        Files.writeString(dossier.resolve("acces.log"),
                "2026-11-23T08:00:00;alice;192.168.0.12\n"
                        + "2026-11-23T08:00:05;bob;10.0.0.7\n"
                        + "2026-11-23T08:00:09;alice;192.168.0.12\n"
                        + "2026-11-23T08:00:12;claire;172.16.4.2\n");
        acces = List.of(
                new Acces("2026-11-23T08:00:00", "alice", "192.168.0.12"),
                new Acces("2026-11-23T08:00:05", "bob", "10.0.0.7"),
                new Acces("2026-11-23T08:00:09", "alice", "192.168.0.12"),
                new Acces("2026-11-23T08:00:12", "claire", "172.16.4.2")
        );

        Files.writeString(dossier2.resolve("index.txt"), "acces.log\n");
        Files.writeString(dossier2.resolve("acces.log"),
                "2026-11-24T09:00:00;dan;192.168.1.1\n"
                        + "2026-11-24T09:00:05;eve;192.168.1.2\n");
        acces2 = List.of(
                new Acces("2026-11-24T09:00:00", "dan", "192.168.1.1"),
                new Acces("2026-11-24T09:00:05", "eve", "192.168.1.2")
        );

        analyseAsync = new AnalyseIpAsync(new AnalyseLogs(new LogReader()));
    }

    private long estimationSynchrone(List<Acces> acces) {
        return AnalyseIp.estimerIpUniques(acces, new HyperLogLog(hasher, 4));
    }

    @Nested
    @DisplayName("Question 1 : estimer de manière asynchrone")
    class Question1 {

        @Test
        void memeEstimationQueLaVersionSynchrone() {
            long estimation = analyseAsync
                    .estimerIpUniquesAsync(acces, new HyperLogLog(hasher, 4))
                    .join();

            assertEquals(estimationSynchrone(acces), estimation);
        }
    }

    @Nested
    @DisplayName("Question 2 : charger les accès de manière asynchrone")
    class Question2 {

        @Test
        void chargeTousLesAccesDuDossier() {
            List<Acces> charges = analyseAsync.chargerAccesAsync(dossier.toString()).join();

            assertAll(
                    () -> assertEquals(4, charges.size()),
                    () -> assertEquals("192.168.0.12", charges.get(0).getIp()),
                    () -> assertEquals("claire", charges.get(3).getUtilisateur())
            );
        }
    }

    @Nested
    @DisplayName("Question 3 : chaîner chargement et estimation")
    class Question3 {

        @Test
        void memeEstimationQueLaChaineSynchrone() {
            long estimation = analyseAsync
                    .estimerDepuisDossierAsync(dossier.toString(), new HyperLogLog(hasher, 4))
                    .join();

            assertEquals(estimationSynchrone(acces), estimation);
        }
    }

    @Nested
    @DisplayName("Question 5 : valeur de repli en cas d'erreur")
    class Question5 {

        @Test
        void renvoieLaValeurDeRepliQuandLeDossierNExistePas() {
            long estimation = analyseAsync
                    .estimerDepuisDossierAsyncAvecRepli("dossier-inexistant", new HyperLogLog(hasher, 4), -1)
                    .join();

            assertEquals(-1, estimation);
        }

        @Test
        void renvoieLEstimationQuandToutSePasseBien() {
            long estimation = analyseAsync
                    .estimerDepuisDossierAsyncAvecRepli(dossier.toString(), new HyperLogLog(hasher, 4), -1)
                    .join();

            assertEquals(estimationSynchrone(acces), estimation);
        }
    }

    @Nested
    @DisplayName("Question 6 : paralléliser deux dossiers avec thenCombine")
    class Question6 {

        @Test
        void combineLesAccesDesDeuxDossiers() {
            List<Acces> tousLesAcces = new ArrayList<>(acces);
            tousLesAcces.addAll(acces2);

            long estimation = analyseAsync
                    .estimerDeuxDossiersAsync(dossier.toString(), dossier2.toString(), new HyperLogLog(hasher, 4))
                    .join();

            assertEquals(estimationSynchrone(tousLesAcces), estimation);
        }
    }

    @Nested
    @DisplayName("Question 7 : paralléliser plusieurs dossiers avec allOf")
    class Question7 {

        @Test
        void renvoieUneEstimationParDossierDansLOrdre() {
            List<Long> estimations = analyseAsync
                    .estimerPlusieursDossiersAsync(List.of(dossier.toString(), dossier2.toString()), hasher)
                    .join();

            assertEquals(List.of(estimationSynchrone(acces), estimationSynchrone(acces2)), estimations);
        }
    }

    @Nested
    @DisplayName("Question 8 : comparer le calcul séquentiel et parallèle")
    class Question8 {

        @Test
        void memesResultatsQueLaVersionParallele() {
            List<String> dossiers = List.of(dossier.toString(), dossier2.toString());

            List<Long> sequentiel = analyseAsync.estimerPlusieursDossiersSequentiellement(dossiers, hasher);
            List<Long> parallele = analyseAsync.estimerPlusieursDossiersAsync(dossiers, hasher).join();

            assertEquals(parallele, sequentiel);
        }
    }

}
