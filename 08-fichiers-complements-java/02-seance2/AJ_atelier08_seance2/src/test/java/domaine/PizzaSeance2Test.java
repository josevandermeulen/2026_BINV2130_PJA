package domaine;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

class PizzaSeance2Test {

    private PizzaComposable pizza;

    @BeforeEach
    void setUp() {
        pizza = new PizzaComposable(new Client("Leconte", "Emmeline", "0488/98.23.85"));
    }

    @Nested
    @DisplayName("Question 9 : ajouter avec un varargs")
    class Question9 {

        @Test
        void ajouterPlusieursIngredientsEnUnAppel() {
            int nombre = pizza.ajouter(Ingredients.TOMATE, Ingredients.MOZZARELLA, Ingredients.JAMBON);

            assertAll(
                    () -> assertEquals(3, nombre),
                    () -> assertEquals(List.of(Ingredients.TOMATE, Ingredients.MOZZARELLA, Ingredients.JAMBON),
                            pizza.getIngredients())
            );
        }

        @Test
        void lesIngredientsDejaPresentsNeSontPasComptes() {
            pizza.ajouter(Ingredients.TOMATE);

            assertEquals(1, pizza.ajouter(Ingredients.TOMATE, Ingredients.OLIVES));
        }

        @Test
        void unTableauEstAccepte() {
            Ingredient[] ingredients = {Ingredients.TOMATE, Ingredients.THON};

            assertEquals(2, pizza.ajouter(ingredients));
        }

        @Test
        void aucunIngredientNAjouteRien() {
            assertEquals(0, pizza.ajouter());
        }

        @Test
        void unePizzaDeLaCarteRefuseToujours() {
            assertThrows(UnsupportedOperationException.class,
                    () -> MenuPizzeria.PIZZA_MARGARITA.ajouter(Ingredients.OLIVES, Ingredients.THON));
        }
    }

    @Nested
    @DisplayName("Question 10 : copier, avec un type de retour covariant")
    class Question10 {

        @Test
        void laCopieEstUnePizzaComposeeEquivalente() {
            PizzaComposee copie = MenuPizzeria.PIZZA_DIAVOLA.copier();

            assertAll(
                    () -> assertNotSame(MenuPizzeria.PIZZA_DIAVOLA, copie),
                    () -> assertEquals(MenuPizzeria.PIZZA_DIAVOLA, copie),
                    () -> assertEquals(MenuPizzeria.PIZZA_DIAVOLA.getDescription(), copie.getDescription()),
                    () -> assertEquals(MenuPizzeria.PIZZA_DIAVOLA.getIngredients(), copie.getIngredients()),
                    () -> assertEquals(MenuPizzeria.PIZZA_DIAVOLA.calculerPrix(), copie.calculerPrix())
            );
        }

        @Test
        void laCopieAppeleeSurUnePizzaRenvoieAussiUnePizzaComposee() {
            Pizza pizzaDeLaCarte = MenuPizzeria.PIZZA_TONNO;

            assertInstanceOf(PizzaComposee.class, pizzaDeLaCarte.copier());
        }
    }

    @Nested
    @DisplayName("Question 12 : clone, une copie profonde de la liste d'ingrédients")
    class Question12 {

        /**
         * Une pizza minimale, propre à ces tests : elle hérite de Pizza.clone sans le redéfinir.
         * Les tests de la Question 12 ne dépendent donc pas de PizzaComposable.clone (Question 13).
         */
        private static class PizzaDeTest extends Pizza {
            PizzaDeTest() {
                super("Pizza de test", "Une pizza pour tester Pizza.clone");
            }

            @Override
            public Pizza copier() {
                throw new UnsupportedOperationException("Inutile pour ces tests");
            }
        }

        private Pizza original;

        @BeforeEach
        void creerOriginal() {
            original = new PizzaDeTest();
            original.ajouter(Ingredients.TOMATE);
            original.ajouter(Ingredients.MOZZARELLA);
        }

        @Test
        void leCloneAUneListeDIngredientsAPart() {
            Pizza copie = original.clone();

            assertAll(
                    () -> assertNotSame(original, copie),
                    () -> assertEquals(original.getTitre(), copie.getTitre()),
                    () -> assertEquals(original.getIngredients(), copie.getIngredients())
            );
        }

        @Test
        void ajouterAuCloneNeModifiePasLOriginal() {
            Pizza copie = original.clone();

            copie.ajouter(Ingredients.JAMBON);

            assertAll(
                    () -> assertEquals(List.of(Ingredients.TOMATE, Ingredients.MOZZARELLA, Ingredients.JAMBON),
                            copie.getIngredients()),
                    () -> assertEquals(List.of(Ingredients.TOMATE, Ingredients.MOZZARELLA),
                            original.getIngredients())
            );
        }

        @Test
        void retirerDeLOriginalNeModifiePasLeClone() {
            Pizza copie = original.clone();

            original.supprimer(Ingredients.TOMATE);

            assertEquals(List.of(Ingredients.TOMATE, Ingredients.MOZZARELLA), copie.getIngredients());
        }

        @Test
        void leCloneDUnePizzaDeLaCarteEstUnePizzaComposee() {
            Pizza pizzaDeLaCarte = MenuPizzeria.PIZZA_DIAVOLA;

            Pizza copie = pizzaDeLaCarte.clone();

            assertAll(
                    () -> assertInstanceOf(PizzaComposee.class, copie),
                    () -> assertEquals(pizzaDeLaCarte.getIngredients(), copie.getIngredients())
            );
        }
    }

    @Nested
    @DisplayName("Question 13 : clone, avec un type de retour covariant")
    class Question13 {

        @Test
        void leCloneEstUnePizzaComposableDuMemeCreateurEtDeLaMemeDate() {
            pizza.ajouter(Ingredients.TOMATE);

            PizzaComposable copie = pizza.clone();

            assertAll(
                    () -> assertNotSame(pizza, copie),
                    () -> assertSame(pizza.getCreateur(), copie.getCreateur()),
                    () -> assertEquals(pizza.getDate(), copie.getDate()),
                    () -> assertEquals(pizza, copie)
            );
        }

        @Test
        void leCloneResteModifiableSansToucherALOriginal() {
            pizza.ajouter(Ingredients.TOMATE);
            PizzaComposable copie = pizza.clone();

            copie.ajouter(Ingredients.OLIVES);

            assertEquals(List.of(Ingredients.TOMATE), pizza.getIngredients());
        }
    }

}
