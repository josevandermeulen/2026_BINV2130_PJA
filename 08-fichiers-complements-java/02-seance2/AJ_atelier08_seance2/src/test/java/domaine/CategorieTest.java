package domaine;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CategorieTest {

    @Nested
    @DisplayName("Question 1 : estVegetarienne")
    class Question1 {

        @Test
        void seuleLaCategorieVegetarienneEstVegetarienne() {
            assertAll(
                    () -> assertTrue(Categorie.VEGETARIENNE.estVegetarienne()),
                    () -> assertFalse(Categorie.VIANDE.estVegetarienne()),
                    () -> assertFalse(Categorie.POISSON.estVegetarienne())
            );
        }

        @Test
        void uneMargaritaEstVegetarienne() {
            assertTrue(MenuPizzeria.PIZZA_MARGARITA.getCategorie().estVegetarienne());
        }
    }

    @Nested
    @DisplayName("Question 2 : l'abréviation")
    class Question2 {

        @Test
        void chaqueCategorieASonAbreviation() {
            assertAll(
                    () -> assertEquals("V", Categorie.VEGETARIENNE.getAbreviation()),
                    () -> assertEquals("VI", Categorie.VIANDE.getAbreviation()),
                    () -> assertEquals("P", Categorie.POISSON.getAbreviation())
            );
        }

        @Test
        void leLibelleNEstPasModifie() {
            assertEquals("végétarienne", Categorie.VEGETARIENNE.getLibelle());
        }
    }

}
