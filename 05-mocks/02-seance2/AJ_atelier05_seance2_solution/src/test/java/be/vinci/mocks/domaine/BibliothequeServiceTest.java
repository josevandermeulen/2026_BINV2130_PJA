package be.vinci.mocks.domaine;

import be.vinci.mocks.source.SourceBibliotheques;
import be.vinci.mocks.source.SourceIndisponibleException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BibliothequeServiceTest {

    // Question 1 : setUp — service réel, source mockée

    private static final String FILTRE_NAMUR = "cp=\"5000\"";

    private static final String FILTRE_BRUXELLES = "cp=\"1000\"";

    private Bibliotheque baieDesTecks;

    private Bibliotheque bomel;

    private SourceBibliotheques source;

    private BibliothequeService service;

    @BeforeEach
    void setUp() {
        baieDesTecks = new Bibliotheque("Baie des tecks", "Namur", "5000", "Namur",
                "bibliothèque locale", null, null, 50.4728, 4.8628);
        bomel = new Bibliotheque("Bibliothèque libre de Bomel", "Namur", "5000", "Namur",
                "bibliothèque locale", "Fonds d'histoire locale", null, 50.4717, 4.8669);

        source = Mockito.mock(SourceBibliotheques.class);
        Mockito.when(source.rechercher(FILTRE_NAMUR)).thenReturn(List.of(bomel, baieDesTecks));

        service = new BibliothequeService(source);
    }
    // FinQuestion

    @Nested
    @DisplayName("Appels à la source")
    class AppelsALaSource {

        // Question 2 : TC1 — une première recherche interroge la source une fois, avec le bon filtre

        @Test
        void testServiceTC1() {
            assertEquals(List.of(baieDesTecks, bomel), service.parCodePostal("5000"));
            Mockito.verify(source, Mockito.times(1)).rechercher(FILTRE_NAMUR);
        }

        // Question 5 : TC4 — deux codes postaux différents : un appel pour chacun

        @Test
        void testServiceTC4() {
            Mockito.when(source.rechercher(FILTRE_BRUXELLES)).thenReturn(List.of());

            service.parCodePostal("5000");
            service.parCodePostal("1000");

            Mockito.verify(source, Mockito.times(1)).rechercher(FILTRE_NAMUR);
            Mockito.verify(source, Mockito.times(1)).rechercher(FILTRE_BRUXELLES);
        }

        // Question 6 : TC5 — code postal invalide : la source n'est jamais interrogée

        @Test
        void testServiceTC5() {
            assertThrows(IllegalArgumentException.class, () -> service.parCodePostal("50A0"));
            Mockito.verify(source, Mockito.never()).rechercher(Mockito.anyString());
        }
        // FinQuestion
    }

    @Nested
    @DisplayName("Cache")
    class Cache {

        // Question 3 : TC2 — la même recherche deux fois : un seul appel à la source

        @Test
        void testServiceTC2() {
            service.parCodePostal("5000");

            assertEquals(List.of(baieDesTecks, bomel), service.parCodePostal("5000"));
            Mockito.verify(source, Mockito.times(1)).rechercher(FILTRE_NAMUR);
        }

        // Question 7 : TC6 — le cache est partagé par toutes les recherches d'un même code postal

        @Test
        void testServiceTC6() {
            service.parCodePostal("5000");
            service.plusProche("5000", 50.4718, 4.8670);
            service.parThematique("5000", "histoire");

            Mockito.verify(source, Mockito.times(1)).rechercher(FILTRE_NAMUR);
        }
        // FinQuestion
    }

    @Nested
    @DisplayName("Source en panne")
    class Panne {

        // Question 4 : TC3 — une panne n'est pas mise en cache : la recherche suivante réinterroge la source

        @Test
        void testServiceTC3() {
            Mockito.when(source.rechercher(FILTRE_NAMUR))
                    .thenThrow(new SourceIndisponibleException("réseau coupé"))
                    .thenReturn(List.of(bomel, baieDesTecks));

            assertThrows(SourceIndisponibleException.class, () -> service.parCodePostal("5000"));
            assertEquals(List.of(baieDesTecks, bomel), service.parCodePostal("5000"));
            Mockito.verify(source, Mockito.times(2)).rechercher(FILTRE_NAMUR);
        }

        // Question 8 : TC7 — la panne d'une autre recherche ne vide pas le cache

        @Test
        void testServiceTC7() {
            Mockito.when(source.rechercher(FILTRE_BRUXELLES))
                    .thenThrow(new SourceIndisponibleException("réseau coupé"));

            service.parCodePostal("5000");
            assertThrows(SourceIndisponibleException.class, () -> service.parCodePostal("1000"));

            assertEquals(List.of(baieDesTecks, bomel), service.parCodePostal("5000"));
            Mockito.verify(source, Mockito.times(1)).rechercher(FILTRE_NAMUR);
        }
    }
}
