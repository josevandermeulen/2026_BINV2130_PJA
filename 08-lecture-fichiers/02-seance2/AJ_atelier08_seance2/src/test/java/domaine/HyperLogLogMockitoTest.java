package domaine;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Les mêmes vérifications que HyperLogLogTest, le stub écrit à la main étant remplacé
 * par un mock Mockito.
 */
class HyperLogLogMockitoTest {

    @Nested
    @DisplayName("Question 9 (optionnelle) : le stub remplacé par un mock Mockito")
    class Question9 {

        /**
         * Reprend la vérification de la Question 6 avec un mock : le hash est programmé par
         * when(...).thenReturn(...), et verify contrôle qu'il est bien appelé une seule fois.
         */
        @Test
        void ajouterMetAJourLeBonRegistreAvecLeBonNombreDeZeros() {
            // TODO Question 9 (optionnelle) : reprenez le test de la Question 6 en remplaçant le
            //  HasherStub par un mock Mockito (mock, when...thenReturn), et vérifiez
            //  avec verify que hash est appelé exactement une fois.
            fail("À compléter");
        }
    }

}
