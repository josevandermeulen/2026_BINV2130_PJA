package domaine;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Tests du socle fourni : la Pizzeria, le constructeur daté de Commande et les collections en
 * lecture seule.
 */
class PizzeriaTest {

    private Pizzeria pizzeria;

    private Client emmeline;

    @BeforeEach
    void setUp() {
        Client.reinitialiserCompteur();
        Commande.reinitialiserCompteur();

        pizzeria = new Pizzeria(ingredient -> true);
        emmeline = new Client("Leconte", "Emmeline", "0488/98.23.85");
        pizzeria.ajouterClient(emmeline);
        pizzeria.ajouterPizza(MenuPizzeria.PIZZA_MARGARITA);
        pizzeria.ajouterPizza(MenuPizzeria.PIZZA_4FROMAGES);
        pizzeria.ajouterPizza(MenuPizzeria.PIZZA_DIAVOLA);
    }

    private Commande commandeCloturee(Client client) {
        Commande commande = new Commande(client, LocalDateTime.of(2026, 11, 9, 12, 0));
        commande.ajouter(MenuPizzeria.PIZZA_MARGARITA);
        client.cloturerCommandeEnCours();
        return commande;
    }

    @Nested
    @DisplayName("Clients")
    class Clients {

        @Test
        void unClientEstRetrouveParSonTelephone() {
            assertSame(emmeline, pizzeria.rechercherClient("0488/98.23.85"));
        }

        @Test
        void unTelephoneInconnuDonneNull() {
            assertNull(pizzeria.rechercherClient("0499/00.00.00"));
        }

        @Test
        void deuxClientsNePeuventPasPartagerUnTelephone() {
            assertFalse(pizzeria.ajouterClient(new Client("Leconte", "Paul", "0488/98.23.85")));
        }

        @Test
        void lesClientsNeSontPasModifiablesDeLExterieur() {
            assertThrows(UnsupportedOperationException.class, () -> pizzeria.getClients().clear());
        }
    }

    @Nested
    @DisplayName("Menu")
    class Menu {

        @Test
        void unePizzaEstRetrouveeParSonTitre() {
            assertSame(MenuPizzeria.PIZZA_DIAVOLA, pizzeria.rechercherPizza("diavola"));
        }

        @Test
        void unTitreDejaAuMenuEstRefuse() {
            assertFalse(pizzeria.ajouterPizza(MenuPizzeria.PIZZA_MARGARITA));
        }

        @Test
        void leMenuEstTrieParTitre() {
            assertEquals(List.of(MenuPizzeria.PIZZA_4FROMAGES, MenuPizzeria.PIZZA_DIAVOLA,
                    MenuPizzeria.PIZZA_MARGARITA), pizzeria.getMenu());
        }

        @Test
        void leMenuTrieParPrixVaDuMoinsCherAuPlusCher() {
            assertEquals(List.of(MenuPizzeria.PIZZA_MARGARITA, MenuPizzeria.PIZZA_DIAVOLA,
                    MenuPizzeria.PIZZA_4FROMAGES), pizzeria.menuTrieParPrix());
        }
    }

    @Nested
    @DisplayName("Commandes")
    class Commandes {

        @Test
        void uneCommandeDateeGardeSaDate() {
            Commande commande = commandeCloturee(emmeline);

            assertEquals(LocalDateTime.of(2026, 11, 9, 12, 0), commande.getDate());
        }

        @Test
        void lesNumerosRepartentDeUnApresReinitialisation() {
            assertAll(
                    () -> assertEquals(1, emmeline.getNumero()),
                    () -> assertEquals(1, commandeCloturee(emmeline).getNumero())
            );
        }

        @Test
        void uneCommandeClotureeEstEnregistree() {
            Commande commande = commandeCloturee(emmeline);

            assertAll(
                    () -> assertTrue(pizzeria.ajouterCommande(commande)),
                    () -> assertEquals(List.of(commande), pizzeria.getCommandes())
            );
        }

        @Test
        void uneCommandeEnCoursEstRefusee() {
            Commande commande = new Commande(emmeline, LocalDateTime.of(2026, 11, 9, 12, 0));

            assertFalse(pizzeria.ajouterCommande(commande));
        }

        @Test
        void uneCommandeDUnClientInconnuEstRefusee() {
            Commande commande = commandeCloturee(new Client("Ferneeuw", "Stéphanie", "0475/51.30.80"));

            assertFalse(pizzeria.ajouterCommande(commande));
        }

        @Test
        void uneCommandeNEstEnregistreeQuUneFois() {
            Commande commande = commandeCloturee(emmeline);
            pizzeria.ajouterCommande(commande);

            assertFalse(pizzeria.ajouterCommande(commande));
        }

        @Test
        void lesLignesEtLHistoriqueNeSontPasModifiablesDeLExterieur() {
            Commande commande = commandeCloturee(emmeline);

            assertAll(
                    () -> assertThrows(UnsupportedOperationException.class, () -> commande.getLignes().clear()),
                    () -> assertThrows(UnsupportedOperationException.class,
                            () -> emmeline.getCommandesPassees().clear()),
                    () -> assertThrows(UnsupportedOperationException.class, () -> pizzeria.getCommandes().clear())
            );
        }
    }

}
