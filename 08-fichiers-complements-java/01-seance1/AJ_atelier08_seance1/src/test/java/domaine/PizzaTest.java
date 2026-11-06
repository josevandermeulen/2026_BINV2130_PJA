package domaine;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Tests du socle fourni : catégorie, ordre naturel et ingrédients en lecture seule des pizzas.
 */
class PizzaTest {

    @Nested
    @DisplayName("Catégorie déduite des ingrédients")
    class CategorieDeduite {

        @Test
        void unePizzaAvecDeLaViandeEstDeLaCategorieViande() {
            assertEquals(Categorie.VIANDE, MenuPizzeria.PIZZA_DIAVOLA.getCategorie());
        }

        @Test
        void unePizzaAvecDuPoissonSansViandeEstDeLaCategoriePoisson() {
            assertEquals(Categorie.POISSON, MenuPizzeria.PIZZA_MARINIERE.getCategorie());
        }

        @Test
        void unePizzaSansViandeNiPoissonEstVegetarienne() {
            assertEquals(Categorie.VEGETARIENNE, MenuPizzeria.PIZZA_MARGARITA.getCategorie());
        }

        @Test
        void laViandeLEmporteSurLePoisson() {
            PizzaComposable pizza = new PizzaComposable(new Client("Leconte", "Emmeline", "0488/98.23.85"));
            pizza.ajouter(Ingredients.SCAMPIS);
            pizza.ajouter(Ingredients.JAMBON);

            assertEquals(Categorie.VIANDE, pizza.getCategorie());
        }

        @Test
        void unePizzaComposableSansIngredientEstVegetarienne() {
            PizzaComposable pizza = new PizzaComposable(new Client("Leconte", "Emmeline", "0488/98.23.85"));

            assertEquals(Categorie.VEGETARIENNE, pizza.getCategorie());
        }
    }

    @Nested
    @DisplayName("Ordre naturel et lecture seule")
    class OrdreEtLectureSeule {

        @Test
        void lOrdreNaturelEstCeluiDesTitres() {
            assertAll(
                    () -> assertTrue(MenuPizzeria.PIZZA_DIAVOLA.compareTo(MenuPizzeria.PIZZA_MARGARITA) < 0),
                    () -> assertTrue(MenuPizzeria.PIZZA_TONNO.compareTo(MenuPizzeria.PIZZA_MARGARITA) > 0),
                    () -> assertEquals(0, MenuPizzeria.PIZZA_TONNO.compareTo(MenuPizzeria.PIZZA_TONNO))
            );
        }

        @Test
        void lesIngredientsNeSontPasModifiablesDeLExterieur() {
            assertThrows(UnsupportedOperationException.class,
                    () -> MenuPizzeria.PIZZA_MARGARITA.getIngredients().add(Ingredients.JAMBON));
        }
    }

}
