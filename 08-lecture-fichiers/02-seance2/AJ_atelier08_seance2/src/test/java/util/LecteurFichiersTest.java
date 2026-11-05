package util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

class LecteurFichiersTest {

    private LecteurFichiers lecteur;

    @BeforeEach
    void setUp() {
        lecteur = new LecteurFichiers();
    }

    @Nested
    @DisplayName("Séance 1, question 1 : lireIndex")
    class Seance1Question1 {

        @Test
        void lireIndexRenvoieTousLesNomsDeFichiersDansLOrdre() throws IOException {
            List<String> noms = lecteur.lireIndex("commandes/index.txt");

            assertEquals(List.of("2026-11-09.csv", "2026-11-10.csv", "2026-11-11.csv", "2026-11-12.csv"), noms);
        }

        @Test
        void lireIndexLeveIOExceptionSiLeFichierNExistePas() {
            assertThrows(IOException.class, () -> lecteur.lireIndex("commandes/inexistant.txt"));
        }
    }

    @Nested
    @DisplayName("Séance 1, question 2 : lireLignes")
    class Seance1Question2 {

        @Test
        void lireLignesRenvoieToutesLesLignesDansLOrdre() throws IOException {
            List<String> lignes = lecteur.lireLignes("commandes/2026-11-09.csv");

            assertAll(
                    () -> assertEquals(36, lignes.size()),
                    () -> assertEquals("2026-11-09T11:31:00;0486/11.22.33;4 fromages;3", lignes.get(0)),
                    () -> assertEquals("2026-11-09T12:31:00;0471/12.34.56;margarita", lignes.get(5))
            );
        }

        @Test
        void lireLignesLeveIOExceptionSiLeFichierNExistePas() {
            assertThrows(IOException.class, () -> lecteur.lireLignes("commandes/2026-11-12.csv"));
        }
    }

}
