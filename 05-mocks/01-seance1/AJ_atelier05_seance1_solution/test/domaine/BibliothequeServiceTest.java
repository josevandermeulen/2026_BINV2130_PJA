package domaine;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import source.SourceIndisponibleException;

import java.util.ArrayList;
import java.util.List;

class BibliothequeServiceTest {

    // Question 3

    private Bibliotheque reseauNamurois;

    private Bibliotheque baieDesTecks;

    private Bibliotheque bomel;

    private BibliothequeService service;

    @BeforeEach
    void setUp() {
        reseauNamurois = new Bibliotheque("Réseau Namurois de Lecture publique", "Namur", "5000", "Namur",
                "réseau de bibliothèques", "Arts du spectacle, Bandes dessinées", "Fonds Cinéma, Mangas",
                50.4652, 4.8666);
        baieDesTecks = new Bibliotheque("Baie des tecks", "Namur", "5000", "Namur",
                "bibliothèque locale", null, null, 50.4728, 4.8628);
        bomel = new Bibliotheque("Bibliothèque libre de Bomel", "Namur", "5000", "Namur",
                "bibliothèque locale", "Fonds d'histoire locale", null, 50.4717, 4.8669);

        service = new BibliothequeService(new SourceStub(List.of(reseauNamurois, baieDesTecks, bomel)));
    }
    // FinQuestion

    @Nested
    @DisplayName("Recherche par code postal")
    class ParCodePostal {

        // Question 4 : TC1 — les bibliothèques renvoyées sont triées par dénomination

        @Test
        void testServiceTC1() {
            assertEquals(List.of(baieDesTecks, bomel, reseauNamurois), service.parCodePostal("5000"));
        }

        // Question 5 : TC2 — aucune bibliothèque pour ce code postal

        @Test
        void testServiceTC2() {
            service = new BibliothequeService(new SourceStub(new ArrayList<>()));

            assertTrue(service.parCodePostal("5000").isEmpty());
        }

        // Question 8 : TC5 — code postal invalide

        @Test
        void testServiceTC5() {
            assertThrows(IllegalArgumentException.class, () -> service.parCodePostal("50A0"));
        }
        // FinQuestion
    }

    @Nested
    @DisplayName("Source indisponible")
    class SourceIndisponible {

        // Question 6 : TC3 — la panne de la source remonte jusqu'à l'appelant

        @Test
        void testServiceTC3() {
            service = new BibliothequeService(new SourceStub(new SourceIndisponibleException("réseau coupé")));

            assertThrows(SourceIndisponibleException.class, () -> service.parCodePostal("5000"));
        }
        // FinQuestion
    }

    @Nested
    @DisplayName("Bibliothèque la plus proche")
    class PlusProche {

        // Question 7 : TC4 — la plus proche d'un point situé à côté de Bomel

        @Test
        void testServiceTC4() {
            assertEquals(bomel, service.plusProche("5000", 50.4718, 4.8670));
        }

        // Question 9 : TC6 — deux bibliothèques à égale distance : la première par ordre alphabétique

        @Test
        void testServiceTC6() {
            Bibliotheque jumelle = new Bibliotheque("Annexe de Bomel", "Namur", "5000", "Namur",
                    "bibliothèque locale", null, null, 50.4717, 4.8669);
            service = new BibliothequeService(new SourceStub(List.of(bomel, jumelle)));

            assertEquals(jumelle, service.plusProche("5000", 50.4718, 4.8670));
        }

        // Question 10 : TC7 — aucune bibliothèque pour ce code postal

        @Test
        void testServiceTC7() {
            service = new BibliothequeService(new SourceStub(new ArrayList<>()));

            assertNull(service.plusProche("5000", 50.4686, 4.8620));
        }
        // FinQuestion
    }

    @Nested
    @DisplayName("Recherche par thématique")
    class ParThematique {

        // Question 11 : TC8 — la recherche ignore les majuscules et minuscules

        @Test
        void testServiceTC8() {
            assertEquals(List.of(reseauNamurois), service.parThematique("5000", "BANDES DESSINÉES"));
        }

        // Question 12 : TC9 — aucun résultat

        @Test
        void testServiceTC9() {
            assertTrue(service.parThematique("5000", "astronomie").isEmpty());
        }
        // FinQuestion
    }

    @Nested
    @DisplayName("Données incomplètes")
    class DonneesIncompletes {

        // Question 14 : TC10 — une bibliothèque sans thématique ni fonds est simplement ignorée

        @Test
        void testServiceTC10() {
            assertEquals(List.of(bomel), service.parThematique("5000", "histoire"));
        }
    }
}
