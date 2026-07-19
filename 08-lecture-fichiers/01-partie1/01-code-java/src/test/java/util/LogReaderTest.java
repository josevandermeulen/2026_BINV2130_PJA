package util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

class LogReaderTest {

    private LogReader lecteur;

    @BeforeEach
    void setUp() {
        lecteur = new LogReader();
    }

    @Nested
    @DisplayName("Question 1 : lireIndex")
    class Question1 {

        @Test
        void lireIndexRenvoieTousLesNomsDeFichiersDansLOrdre() throws IOException {
            List<String> noms = lecteur.lireIndex("logs/index.txt");

            assertEquals(List.of("2026-11-09.log", "2026-11-10.log", "2026-11-11.log", "2026-11-12.log"), noms);
        }

        @Test
        void lireIndexLeveIOExceptionSiLeFichierNExistePas() {
            assertThrows(IOException.class, () -> lecteur.lireIndex("logs/inexistant.txt"));
        }
    }

    @Nested
    @DisplayName("Question 2 : lireLignes")
    class Question2 {

        @Test
        void lireLignesRenvoieToutesLesLignesDansLOrdre() throws IOException {
            List<String> lignes = lecteur.lireLignes("logs/2026-11-09.log");

            assertAll(
                    () -> assertEquals(12, lignes.size()),
                    () -> assertEquals("2026-11-09T08:12:44;alice;192.168.0.12", lignes.get(0)),
                    () -> assertEquals("2026-11-09T16:03:32;emma;192.168.0.44", lignes.get(11))
            );
        }

        @Test
        void lireLignesLeveIOExceptionSiLeFichierNExistePas() {
            assertThrows(IOException.class, () -> lecteur.lireLignes("logs/2026-11-12.log"));
        }
    }

}
