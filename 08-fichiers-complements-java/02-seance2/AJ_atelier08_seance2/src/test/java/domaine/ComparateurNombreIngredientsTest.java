package domaine;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ComparateurNombreIngredientsTest {

    @Nested
    @DisplayName("Question 6 : un Comparator en classe nommée")
    class Question6 {

        private final ComparateurNombreIngredients comparateur = new ComparateurNombreIngredients();

        @Test
        void laPizzaLaPlusGarnieVientDAbord() {
            // du chef : 6 ingrédients ; margarita : 2
            assertTrue(comparateur.compare(MenuPizzeria.PIZZA_DUCHEF, MenuPizzeria.PIZZA_MARGARITA) < 0);
        }

        @Test
        void laPizzaLaMoinsGarnieVientEnsuite() {
            assertTrue(comparateur.compare(MenuPizzeria.PIZZA_MARGARITA, MenuPizzeria.PIZZA_DUCHEF) > 0);
        }

        @Test
        void aNombreEgalLOrdreEstCeluiDesTitres() {
            // diavola et tonno : 4 ingrédients chacune
            assertTrue(comparateur.compare(MenuPizzeria.PIZZA_DIAVOLA, MenuPizzeria.PIZZA_TONNO) < 0);
        }

        @Test
        void unePizzaEstEgaleAElleMeme() {
            assertEquals(0, comparateur.compare(MenuPizzeria.PIZZA_TONNO, MenuPizzeria.PIZZA_TONNO));
        }
    }

}
