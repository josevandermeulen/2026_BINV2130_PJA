package main;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import domaine.Acces;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import util.LecteurLogs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;

class AnalyseLogsTest {

    private LecteurLogs lecteur;

    private AnalyseLogs analyse;

    @BeforeEach
    void setUp() {
        lecteur = mock(LecteurLogs.class);
        analyse = new AnalyseLogs(lecteur);
    }

    private List<Acces> troisAccesDeDemo() {
        return List.of(
                new Acces("2026-11-09T08:12:44", "alice", "192.168.0.12"),
                new Acces("2026-11-09T08:15:03", "bob", "10.0.0.7"),
                new Acces("2026-11-09T08:20:51", "alice", "192.168.0.12")
        );
    }

    @Nested
    @DisplayName("Question 4 : parserLigne")
    class Question4 {

        @Test
        void parserLigneConstruitUnAcces() {
            Acces acces = analyse.parserLigne("2026-11-09T08:12:44;alice;192.168.0.12");

            assertAll(
                    () -> assertEquals("2026-11-09T08:12:44", acces.getHorodatage()),
                    () -> assertEquals("alice", acces.getUtilisateur()),
                    () -> assertEquals("192.168.0.12", acces.getIp())
            );
        }

        @Test
        void parserLigneRefuseUnChampManquant() {
            assertThrows(IllegalArgumentException.class,
                    () -> analyse.parserLigne("2026-11-09T09:14:02;bob"));
        }

        @Test
        void parserLigneRefuseUnChampEnTrop() {
            assertThrows(IllegalArgumentException.class,
                    () -> analyse.parserLigne("2026-11-10T09:47:03;bob;10.0.0.7;proxy"));
        }

        @Test
        void parserLigneRefuseUnUtilisateurVide() {
            assertThrows(IllegalArgumentException.class,
                    () -> analyse.parserLigne("2026-11-09T10:45:00;;192.168.0.44"));
        }
    }

    @Nested
    @DisplayName("Question 5 : parserLignes ignore les lignes invalides")
    class Question5 {

        @Test
        void parserLignesIgnoreLesLignesInvalidesEtGardeLesValides() {
            List<String> lignes = List.of(
                    "2026-11-09T08:12:44;alice;192.168.0.12",
                    "2026-11-09T09:14:02;bob",
                    "2026-11-09T09:30:27;carla;172.16.4.101"
            );

            List<Acces> acces = analyse.parserLignes(lignes);

            assertAll(
                    () -> assertEquals(2, acces.size()),
                    () -> assertEquals("alice", acces.get(0).getUtilisateur()),
                    () -> assertEquals("carla", acces.get(1).getUtilisateur())
            );
        }
    }

    @Nested
    @DisplayName("Question 6 : chargerTousLesAcces combine index et fichiers")
    class Question6 {

        @Test
        void chargerTousLesAccesLitTousLesFichiersDeLIndex() throws IOException {
            when(lecteur.lireIndex("logs/index.txt")).thenReturn(List.of("a.log", "b.log"));
            when(lecteur.lireLignes("logs/a.log")).thenReturn(List.of(
                    "2026-11-09T08:12:44;alice;192.168.0.12",
                    "2026-11-09T08:15:03;bob;10.0.0.7"
            ));
            when(lecteur.lireLignes("logs/b.log")).thenReturn(List.of(
                    "2026-11-10T07:59:12;david;10.0.0.8"
            ));

            List<Acces> acces = analyse.chargerTousLesAcces("logs");

            assertAll(
                    () -> assertEquals(3, acces.size()),
                    () -> assertEquals("alice", acces.get(0).getUtilisateur()),
                    () -> assertEquals("david", acces.get(2).getUtilisateur())
            );
        }
    }

    @Nested
    @DisplayName("Question 7 : un fichier introuvable n'interrompt pas le chargement")
    class Question7 {

        @Test
        void chargerTousLesAccesContinueApresUnFichierIntrouvable() throws IOException {
            when(lecteur.lireIndex("logs/index.txt")).thenReturn(List.of("absent.log", "b.log"));
            when(lecteur.lireLignes("logs/absent.log"))
                    .thenThrow(new FileNotFoundException("logs/absent.log"));
            when(lecteur.lireLignes("logs/b.log")).thenReturn(List.of(
                    "2026-11-10T07:59:12;david;10.0.0.8"
            ));

            List<Acces> acces = analyse.chargerTousLesAcces("logs");

            assertAll(
                    () -> assertEquals(1, acces.size()),
                    () -> assertEquals("david", acces.get(0).getUtilisateur())
            );
        }
    }

    @Nested
    @DisplayName("Question 8 : ipUniques")
    class Question8 {

        @Test
        void ipUniquesSupprimeLesDoublons() {
            Set<String> ips = analyse.ipUniques(troisAccesDeDemo());

            assertEquals(Set.of("192.168.0.12", "10.0.0.7"), ips);
        }
    }

    @Nested
    @DisplayName("Question 9 : nombreAccesParUtilisateur")
    class Question9 {

        @Test
        void nombreAccesParUtilisateurCompteChaqueUtilisateur() {
            Map<String, Long> compteurs = analyse.nombreAccesParUtilisateur(troisAccesDeDemo());

            assertEquals(Map.of("alice", 2L, "bob", 1L), compteurs);
        }
    }

    @Nested
    @DisplayName("Question 10 : ecrireRapport")
    class Question10 {

        @TempDir
        Path dossierTemporaire;

        @Test
        void ecrireRapportEcritLesIpTrieesPuisLeTotal() throws IOException {
            Path rapport = dossierTemporaire.resolve("rapport.txt");

            analyse.ecrireRapport(rapport.toString(), troisAccesDeDemo());

            List<String> lignes = Files.readAllLines(rapport);
            assertEquals(List.of(
                    "10.0.0.7",
                    "192.168.0.12",
                    "Nombre d'adresses IP uniques : 2"
            ), lignes);
        }
    }

    @Nested
    @DisplayName("Question 11 (optionnelle) : validation des adresses IP")
    class Question11 {

        @Test
        void ipValideAccepteUneAdresseCorrecte() {
            assertTrue(AnalyseLogs.ipValide("192.168.0.12"));
        }

        @Test
        void ipValideRefuseUnOctetHorsBornes() {
            assertFalse(AnalyseLogs.ipValide("300.0.0.1"));
        }

        @Test
        void ipValideRefuseUnMauvaisNombreDOctets() {
            assertFalse(AnalyseLogs.ipValide("192.168.0"));
        }

        @Test
        void ipValideRefuseUnOctetNonNumerique() {
            assertFalse(AnalyseLogs.ipValide("192.168.0.abc"));
        }

        @Test
        void accesIpInvalideRenvoieLesAccesFautifs() {
            List<Acces> acces = List.of(
                    new Acces("2026-11-10T15:44:50", "bob", "300.0.0.1"),
                    new Acces("2026-11-09T08:12:44", "alice", "192.168.0.12")
            );

            List<Acces> invalides = analyse.accesIpInvalide(acces);

            assertAll(
                    () -> assertEquals(1, invalides.size()),
                    () -> assertEquals("300.0.0.1", invalides.get(0).getIp())
            );
        }
    }

    @Nested
    @DisplayName("Question 12 (optionnelle) : utilisateurs par adresse IP")
    class Question12 {

        @Test
        void utilisateursParIpRegroupeSansDoublon() {
            List<Acces> acces = List.of(
                    new Acces("2026-11-09T08:15:03", "bob", "10.0.0.7"),
                    new Acces("2026-11-09T10:02:13", "david", "10.0.0.7"),
                    new Acces("2026-11-09T14:41:08", "bob", "10.0.0.7"),
                    new Acces("2026-11-09T08:12:44", "alice", "192.168.0.12")
            );

            Map<String, Set<String>> parIp = analyse.utilisateursParIp(acces);

            assertEquals(Map.of(
                    "10.0.0.7", Set.of("bob", "david"),
                    "192.168.0.12", Set.of("alice")
            ), parIp);
        }
    }

}
