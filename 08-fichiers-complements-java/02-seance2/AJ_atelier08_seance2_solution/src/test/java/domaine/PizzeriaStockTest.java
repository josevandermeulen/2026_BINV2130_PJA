package domaine;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

/**
 * Tests de Pizzeria.ajouterCommandeSiDisponible, avec un stock remplacé d'abord par un stub, puis
 * par un mock Mockito.
 */
class PizzeriaStockTest {

    private Client emmeline;

    @BeforeEach
    void setUp() {
        Client.reinitialiserCompteur();
        Commande.reinitialiserCompteur();

        emmeline = new Client("Leconte", "Emmeline", "0488/98.23.85");
    }

    private Pizzeria pizzeriaAvec(Stock stock) {
        Pizzeria pizzeria = new Pizzeria(stock);
        pizzeria.ajouterClient(emmeline);
        pizzeria.ajouterPizza(MenuPizzeria.PIZZA_MARGARITA);
        pizzeria.ajouterPizza(MenuPizzeria.PIZZA_DIAVOLA);
        return pizzeria;
    }

    private Commande commandeCloturee(Pizza pizza) {
        Commande commande = new Commande(emmeline, LocalDateTime.of(2026, 11, 12, 12, 0));
        commande.ajouter(pizza, 1);
        emmeline.cloturerCommandeEnCours();
        return commande;
    }

    // Question 17
    @Nested
    @DisplayName("Question 17 : avec un stub écrit à la main")
    class Question17 {

        @Test
        void uneCommandeDontUnIngredientManqueEstRefusee() {
            Pizzeria pizzeria = pizzeriaAvec(new StockStub(Ingredients.SALAMI));
            Commande commande = commandeCloturee(MenuPizzeria.PIZZA_DIAVOLA);

            assertAll(
                    () -> assertFalse(pizzeria.ajouterCommandeSiDisponible(commande)),
                    () -> assertTrue(pizzeria.getCommandes().isEmpty())
            );
        }

        @Test
        void uneCommandeDontToutEstDisponibleEstEnregistree() {
            Pizzeria pizzeria = pizzeriaAvec(new StockStub(Ingredients.SALAMI));
            Commande commande = commandeCloturee(MenuPizzeria.PIZZA_MARGARITA);

            assertAll(
                    () -> assertTrue(pizzeria.ajouterCommandeSiDisponible(commande)),
                    () -> assertEquals(1, pizzeria.getCommandes().size())
            );
        }
    }

    // Question 18
    @Nested
    @DisplayName("Question 18 : avec un mock Mockito")
    class Question18 {

        @Test
        void uneCommandeDontUnIngredientManqueEstRefusee() {
            Stock stock = mock(Stock.class);
            when(stock.estDisponible(any(Ingredient.class))).thenReturn(true);
            when(stock.estDisponible(Ingredients.SALAMI)).thenReturn(false);
            Pizzeria pizzeria = pizzeriaAvec(stock);

            assertFalse(pizzeria.ajouterCommandeSiDisponible(commandeCloturee(MenuPizzeria.PIZZA_DIAVOLA)));
            verify(stock).estDisponible(Ingredients.SALAMI);
        }

        @Test
        void chaqueIngredientDeLaCommandeEstVerifie() {
            Stock stock = mock(Stock.class);
            when(stock.estDisponible(any(Ingredient.class))).thenReturn(true);
            Pizzeria pizzeria = pizzeriaAvec(stock);

            assertTrue(pizzeria.ajouterCommandeSiDisponible(commandeCloturee(MenuPizzeria.PIZZA_MARGARITA)));
            verify(stock).estDisponible(Ingredients.TOMATE);
            verify(stock).estDisponible(Ingredients.MOZZARELLA);
            verifyNoMoreInteractions(stock);
        }
    }

}
