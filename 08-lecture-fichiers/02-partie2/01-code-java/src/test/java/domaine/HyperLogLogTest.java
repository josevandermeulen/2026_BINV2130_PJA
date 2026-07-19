package domaine;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class HyperLogLogTest {

    @Nested
    @DisplayName("Question 6 : ajouter met à jour le bon registre")
    class Question6 {

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

        @Test
        void estimationProcheDuNombreReelDeValeursDistinctes() {
            // TODO Question 8 : un vrai DefaultHasher (4 bits d'index), 500 valeurs distinctes (avec
            //  répétitions), puis vérifiez que l'estimation reste à ±30 % de 500.
            fail("À compléter");
        }
    }

}
