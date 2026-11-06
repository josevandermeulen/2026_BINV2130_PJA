package domaine;

import static org.junit.jupiter.api.Assertions.*;

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

    @Nested
    @DisplayName("Question 17 : avec un stub écrit à la main")
    class Question17 {

        @Test
        void uneCommandeDontUnIngredientManqueEstRefusee() {
            // TODO Question 17 : une pizzeria dont le stock (un StockStub) manque de salami refuse une
            //  commande de diavola, et ne l'enregistre pas.
            fail("À compléter");
        }

        @Test
        void uneCommandeDontToutEstDisponibleEstEnregistree() {
            // TODO Question 17 : avec le même stock, une commande de margarita est acceptée et
            //  enregistrée.
            fail("À compléter");
        }
    }

    @Nested
    @DisplayName("Question 18 : avec un mock Mockito")
    class Question18 {

        @Test
        void uneCommandeDontUnIngredientManqueEstRefusee() {
            // TODO Question 18 : le même test qu'à la Question 17, avec un mock de Stock (when, any),
            //  en vérifiant (verify) que le stock a été interrogé sur le salami.
            fail("À compléter");
        }

        @Test
        void chaqueIngredientDeLaCommandeEstVerifie() {
            // TODO Question 18 : une commande de margarita, un stock qui a tout ; vérifiez que la
            //  tomate et la mozzarella sont demandées, et rien d'autre (verifyNoMoreInteractions).
            fail("À compléter");
        }
    }

}
