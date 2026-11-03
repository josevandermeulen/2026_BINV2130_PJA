package domaine;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Tests de HyperLogLog. Le hachage est neutralisé par un stub, de sorte que chaque test vise
 * un registre connu d'avance plutôt que de dépendre d'un vrai hachage.
 */
class HyperLogLogTest {

    @Nested
    @DisplayName("Question 6 : ajouter met à jour le bon registre")
    class Question6 {

        /**
         * Vérifie qu'ajouter une valeur incrémente le registre désigné par l'index du hash,
         * avec le nombre de zéros de tête attendu.
         */
        @Test
        void ajouterMetAJourLeBonRegistreAvecLeBonNombreDeZeros() {
            // TODO Question 6 : HyperLogLog (4 bits d'index) avec un HasherStub fixé à 0x12345678,
            //  un appel à ajouter, puis vérifiez registres[1] == 3 et registres[0] == 0.
            fail("À compléter");
        }
    }

    @Nested
    @DisplayName("Question 7 : ajouter ne diminue jamais un registre")
    class Question7 {

        /**
         * Vérifie qu'ajouter une valeur ne fait jamais baisser un registre déjà plus élevé :
         * HyperLogLog ne retient que le maximum.
         */
        @Test
        void ajouterNeDiminueJamaisUnRegistreDejaPlusGrand() {
            // TODO Question 7 : HasherStub fixé à 0x1FFFFFFF, registres[1] forcé à 5,
            //  un appel à ajouter, puis vérifiez que registres[1] vaut toujours 5.
            fail("À compléter");
        }
    }

    @Nested
    @DisplayName("Question 8 : estimation proche de la réalité")
    class Question8 {

        /**
         * Vérifie que l'estimation reste dans la marge d'erreur attendue face à un nombre
         * connu de valeurs distinctes.
         */
        @Test
        void estimationProcheDuNombreReelDeValeursDistinctes() {
            // TODO Question 8 : un vrai DefaultHasher (4 bits d'index), 500 valeurs distinctes (avec
            //  répétitions), puis vérifiez que l'estimation reste à ±30 % de 500.
            fail("À compléter");
        }
    }

    @Nested
    @DisplayName("Question 9 : clone renvoie une copie indépendante")
    class Question9 {

        /**
         * Vérifie que la copie part des mêmes registres que l'original, sans partager son tableau.
         */
        @Test
        void cloneCopieLesRegistresDansUnNouveauTableau() {
            HyperLogLog original = new HyperLogLog(new HasherStub(0x12345678), 4);
            original.ajouter("peu importe");

            HyperLogLog copie = original.clone();

            assertAll(
                    () -> assertNotSame(original, copie),
                    () -> assertNotSame(original.registres, copie.registres),
                    () -> assertArrayEquals(original.registres, copie.registres)
            );
        }

        /**
         * Vérifie qu'ajouter une valeur à l'original, après la copie, ne modifie pas la copie.
         */
        @Test
        void ajouterALOriginalNeModifiePasLaCopie() {
            HyperLogLog original = new HyperLogLog(new HasherStub(0x12345678), 4);
            HyperLogLog copie = original.clone();

            original.ajouter("peu importe");

            assertAll(
                    () -> assertEquals(3, original.registres[1]),
                    () -> assertEquals(0, copie.registres[1])
            );
        }

        /**
         * Vérifie qu'ajouter une valeur à la copie ne modifie pas l'original.
         */
        @Test
        void ajouterALaCopieNeModifiePasLOriginal() {
            HyperLogLog original = new HyperLogLog(new HasherStub(0x12345678), 4);
            HyperLogLog copie = original.clone();

            copie.ajouter("peu importe");

            assertAll(
                    () -> assertEquals(3, copie.registres[1]),
                    () -> assertEquals(0, original.registres[1])
            );
        }
    }

}
