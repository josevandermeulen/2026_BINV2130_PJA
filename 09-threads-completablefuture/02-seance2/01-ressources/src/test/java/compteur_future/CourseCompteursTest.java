package compteur_future;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

class CourseCompteursTest {

    CourseCompteurs course;

    CompteurEtPosition[] compteurs;

    @BeforeEach
    void setUp() {
        course = new CourseCompteurs();
        compteurs = new CompteurEtPosition[]{
                new CompteurEtPosition("Bolt", 2), new CompteurEtPosition("Jakson", 2),
                new CompteurEtPosition("Robert", 2), new CompteurEtPosition("Stéphanie", 2)
        };
        CompteurEtPosition.resetOrdreArrivee();
    }

    @Nested
    @DisplayName("Question 1 : attendre les résultats de countAndGetPositionAsync")
    class Question1 {

        @Test
        @Timeout(value = 5, unit = TimeUnit.SECONDS)
        void attendTousLesCompteursAvantDeTerminer() {
            course.attendreResultatsAsync(compteurs).join();

            assertEquals(compteurs.length, CompteurEtPosition.getOrdreArrivee());
        }
    }

    @Nested
    @DisplayName("Question 2 : créer des futures à partir de countAndGetPosition")
    class Question2 {

        @Test
        @Timeout(value = 5, unit = TimeUnit.SECONDS)
        void attendTousLesCompteursAvantDeTerminer() {
            course.creerFuturesDepuisCountAndGetPosition(compteurs).join();

            assertEquals(compteurs.length, CompteurEtPosition.getOrdreArrivee());
        }
    }

}
