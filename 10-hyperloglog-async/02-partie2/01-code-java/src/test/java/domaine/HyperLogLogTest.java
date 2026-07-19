package domaine;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class HyperLogLogTest {

    @Nested
    @DisplayName("Question 1 : fusionner deux HyperLogLog")
    class Question1 {

        @Test
        void fusionPrendLeMaxRegistreParRegistre() {
            HyperLogLog premier = new HyperLogLog(new HasherStub(0), 4);
            HyperLogLog second = new HyperLogLog(new HasherStub(0), 4);
            premier.registres[0] = 3;
            premier.registres[5] = 1;
            second.registres[0] = 2;
            second.registres[5] = 4;

            premier.fusionner(second);

            assertAll(
                    () -> assertEquals(3, premier.registres[0]),
                    () -> assertEquals(4, premier.registres[5]),
                    () -> assertEquals(0, premier.registres[1])
            );
        }

        @Test
        void fusionNeModifiePasLAutreEstimateur() {
            HyperLogLog premier = new HyperLogLog(new HasherStub(0), 4);
            HyperLogLog second = new HyperLogLog(new HasherStub(0), 4);
            premier.registres[0] = 3;
            second.registres[0] = 2;

            premier.fusionner(second);

            assertEquals(2, second.registres[0]);
        }

        @Test
        void fusionEstCommutative() {
            HyperLogLog gauche = hllAvecValeurs("10.0.0.1", "10.0.0.2");
            HyperLogLog droite = hllAvecValeurs("10.0.0.2", "10.0.0.3");
            HyperLogLog gaucheBis = hllAvecValeurs("10.0.0.1", "10.0.0.2");
            HyperLogLog droiteBis = hllAvecValeurs("10.0.0.2", "10.0.0.3");

            gauche.fusionner(droite);
            droiteBis.fusionner(gaucheBis);

            assertEquals(gauche.estimerCardinalite(), droiteBis.estimerCardinalite());
        }

        @Test
        void fusionEquivauteAToutAjouterDansUnSeulEstimateur() {
            HyperLogLog premier = hllAvecValeurs("10.0.0.1", "10.0.0.2", "10.0.0.3");
            HyperLogLog second = hllAvecValeurs("10.0.0.3", "10.0.0.4");
            HyperLogLog reference = hllAvecValeurs("10.0.0.1", "10.0.0.2", "10.0.0.3", "10.0.0.4");

            premier.fusionner(second);

            assertEquals(reference.estimerCardinalite(), premier.estimerCardinalite());
        }

        @Test
        void fusionAvecUnNombreDeRegistresDifferentLanceException() {
            HyperLogLog seizeRegistres = new HyperLogLog(new HasherStub(0), 4);
            HyperLogLog trenteDeuxRegistres = new HyperLogLog(new HasherStub(0), 5);

            assertThrows(IllegalArgumentException.class,
                    () -> seizeRegistres.fusionner(trenteDeuxRegistres));
        }

        @Test
        void fusionAvecNullLanceException() {
            HyperLogLog hyperLogLog = new HyperLogLog(new HasherStub(0), 4);

            assertThrows(IllegalArgumentException.class, () -> hyperLogLog.fusionner(null));
        }

        private HyperLogLog hllAvecValeurs(String... valeurs) {
            HyperLogLog hyperLogLog = new HyperLogLog(new DefaultHasher(), 4);
            for (String valeur : valeurs) {
                hyperLogLog.ajouter(valeur);
            }
            return hyperLogLog;
        }
    }

}
