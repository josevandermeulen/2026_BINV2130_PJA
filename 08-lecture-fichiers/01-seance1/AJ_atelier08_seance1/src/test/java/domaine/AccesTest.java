package domaine;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class AccesTest {

    @Nested
    @DisplayName("Question 3 : validation du constructeur")
    class Question3 {

        @Test
        void constructeurValideConserveLesValeurs() {
            Acces acces = new Acces("2026-11-09T08:12:44", "alice", "192.168.0.12");

            assertAll(
                    () -> assertEquals("2026-11-09T08:12:44", acces.getHorodatage()),
                    () -> assertEquals("alice", acces.getUtilisateur()),
                    () -> assertEquals("192.168.0.12", acces.getIp())
            );
        }

        @Test
        void constructeurRefuseUtilisateurVide() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Acces("2026-11-09T08:12:44", "", "192.168.0.12"));
        }

        @Test
        void constructeurRefuseIpNull() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Acces("2026-11-09T08:12:44", "alice", null));
        }

        @Test
        void constructeurRefuseHorodatageVide() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Acces("  ", "alice", "192.168.0.12"));
        }
    }

}
