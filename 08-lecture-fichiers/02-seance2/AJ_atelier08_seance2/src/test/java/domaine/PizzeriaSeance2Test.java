package domaine;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

class PizzeriaSeance2Test {

    private Pizzeria pizzeria;

    private Client emmeline;

    private Client stephanie;

    @BeforeEach
    void setUp() {
        Client.reinitialiserCompteur();
        Commande.reinitialiserCompteur();

        pizzeria = new Pizzeria(ingredient -> true);
        emmeline = new Client("Leconte", "Emmeline", "0488/98.23.85");
        stephanie = new Client("Ferneeuw", "Stéphanie", "0475/51.30.80");
        pizzeria.ajouterClient(emmeline);
        pizzeria.ajouterClient(stephanie);
        pizzeria.ajouterPizza(MenuPizzeria.PIZZA_MARGARITA);
        pizzeria.ajouterPizza(MenuPizzeria.PIZZA_4FROMAGES);
        pizzeria.ajouterPizza(MenuPizzeria.PIZZA_DIAVOLA);
        pizzeria.ajouterPizza(MenuPizzeria.PIZZA_DUCHEF);
        pizzeria.ajouterPizza(MenuPizzeria.PIZZA_MARINIERE);
    }

    private void commander(Client client, Pizza pizza, int quantite) {
        Commande commande = new Commande(client, LocalDateTime.of(2026, 11, 12, 12, 0));
        commande.ajouter(pizza, quantite);
        client.cloturerCommandeEnCours();
        pizzeria.ajouterCommande(commande);
    }

    @Nested
    @DisplayName("Question 3 : ranger la pizza sur la carte")
    class Question3 {

        @Test
        void chaqueCategorieDuMenuEstSurLaCarte() {
            assertAll(
                    () -> assertTrue(pizzeria.aDesPizzas(Categorie.VEGETARIENNE)),
                    () -> assertTrue(pizzeria.aDesPizzas(Categorie.VIANDE)),
                    () -> assertTrue(pizzeria.aDesPizzas(Categorie.POISSON))
            );
        }

        @Test
        void uneCategorieSansPizzaNEstPasSurLaCarte() {
            Pizzeria pizzeriaVegetarienne = new Pizzeria(ingredient -> true);
            pizzeriaVegetarienne.ajouterPizza(MenuPizzeria.PIZZA_MARGARITA);

            assertAll(
                    () -> assertTrue(pizzeriaVegetarienne.aDesPizzas(Categorie.VEGETARIENNE)),
                    () -> assertFalse(pizzeriaVegetarienne.aDesPizzas(Categorie.POISSON))
            );
        }
    }

    @Nested
    @DisplayName("Question 4 : getPizzas")
    class Question4 {

        @Test
        void lesPizzasDUneCategorieSontTrieesParTitre() {
            assertEquals(List.of(MenuPizzeria.PIZZA_4FROMAGES, MenuPizzeria.PIZZA_MARGARITA),
                    List.copyOf(pizzeria.getPizzas(Categorie.VEGETARIENNE)));
        }

        @Test
        void chaquePizzaEstDansSaCategorie() {
            assertAll(
                    () -> assertEquals(List.of(MenuPizzeria.PIZZA_DIAVOLA, MenuPizzeria.PIZZA_DUCHEF),
                            List.copyOf(pizzeria.getPizzas(Categorie.VIANDE))),
                    () -> assertEquals(List.of(MenuPizzeria.PIZZA_MARINIERE),
                            List.copyOf(pizzeria.getPizzas(Categorie.POISSON)))
            );
        }

        @Test
        void uneCategorieSansPizzaDonneUnEnsembleVide() {
            Pizzeria vide = new Pizzeria(ingredient -> true);

            assertTrue(vide.getPizzas(Categorie.POISSON).isEmpty());
        }

        @Test
        void unePizzaRefuseeNEntrePasDeuxFoisSurLaCarte() {
            pizzeria.ajouterPizza(MenuPizzeria.PIZZA_MARGARITA);

            assertEquals(2, pizzeria.getPizzas(Categorie.VEGETARIENNE).size());
        }

        @Test
        void laCarteNEstPasModifiableDeLExterieur() {
            assertThrows(UnsupportedOperationException.class,
                    () -> pizzeria.getPizzas(Categorie.VIANDE).clear());
        }
    }

    @Nested
    @DisplayName("Question 5 : retirerPizza")
    class Question5 {

        @Test
        void unePizzaRetireeQuitteLeMenuEtLaCarte() {
            assertAll(
                    () -> assertTrue(pizzeria.retirerPizza("diavola")),
                    () -> assertNull(pizzeria.rechercherPizza("diavola")),
                    () -> assertEquals(List.of(MenuPizzeria.PIZZA_DUCHEF),
                            List.copyOf(pizzeria.getPizzas(Categorie.VIANDE)))
            );
        }

        @Test
        void laCategorieDisparaitAvecSaDernierePizza() {
            pizzeria.retirerPizza("marinière");

            assertFalse(pizzeria.aDesPizzas(Categorie.POISSON));
        }

        @Test
        void unTitreInconnuNeRetireRien() {
            assertAll(
                    () -> assertFalse(pizzeria.retirerPizza("hawaïenne")),
                    () -> assertEquals(5, pizzeria.getMenu().size())
            );
        }
    }

    @Nested
    @DisplayName("Question 7 : un Comparator en classe anonyme")
    class Question7 {

        @Test
        void leMenuVaDeLaPlusGarnieALaMoinsGarnie() {
            assertEquals(List.of(MenuPizzeria.PIZZA_DUCHEF, MenuPizzeria.PIZZA_4FROMAGES,
                    MenuPizzeria.PIZZA_DIAVOLA, MenuPizzeria.PIZZA_MARINIERE, MenuPizzeria.PIZZA_MARGARITA),
                    pizzeria.menuTrieParNombreIngredients());
        }
    }

    @Nested
    @DisplayName("Question 8 : un Comparator en lambda")
    class Question8 {

        @Test
        void leMenuEstTrieParCategoriePuisParTitre() {
            assertEquals(List.of(MenuPizzeria.PIZZA_4FROMAGES, MenuPizzeria.PIZZA_MARGARITA,
                    MenuPizzeria.PIZZA_DIAVOLA, MenuPizzeria.PIZZA_DUCHEF, MenuPizzeria.PIZZA_MARINIERE),
                    pizzeria.menuTrieParCategorie());
        }
    }

    @Nested
    @DisplayName("Question 14 : le bilan, une classe interne statique")
    class Question14 {

        @Test
        void leBilanCompteCommandesPizzasEtChiffreAffaires() {
            commander(emmeline, MenuPizzeria.PIZZA_MARGARITA, 2);
            commander(stephanie, MenuPizzeria.PIZZA_DIAVOLA, 3);

            Pizzeria.Bilan bilan = pizzeria.bilan();

            double attendu = 2 * MenuPizzeria.PIZZA_MARGARITA.calculerPrix()
                    + 3 * MenuPizzeria.PIZZA_DIAVOLA.calculerPrix();
            assertAll(
                    () -> assertEquals(2, bilan.getNombreCommandes()),
                    () -> assertEquals(5, bilan.getNombrePizzas()),
                    () -> assertEquals(attendu, bilan.getChiffreAffaires())
            );
        }

        @Test
        void leBilanDUnePizzeriaSansCommandeEstNul() {
            Pizzeria.Bilan bilan = pizzeria.bilan();

            assertAll(
                    () -> assertEquals(0, bilan.getNombreCommandes()),
                    () -> assertEquals(0, bilan.getNombrePizzas()),
                    () -> assertEquals(0, bilan.getChiffreAffaires())
            );
        }
    }

    @Nested
    @DisplayName("Question 15 : la fidélité, une classe interne non statique")
    class Question15 {

        @Test
        void unClientEstFideleAPartirDuSeuil() {
            commander(emmeline, MenuPizzeria.PIZZA_MARGARITA, 1);
            commander(emmeline, MenuPizzeria.PIZZA_DIAVOLA, 1);
            commander(stephanie, MenuPizzeria.PIZZA_DIAVOLA, 1);

            Pizzeria.Fidelite fidelite = pizzeria.fidelite(2);

            assertAll(
                    () -> assertTrue(fidelite.estFidele(emmeline)),
                    () -> assertFalse(fidelite.estFidele(stephanie))
            );
        }

        @Test
        void laFideliteSuitLesCommandesEnregistreesApresSaCreation() {
            Pizzeria.Fidelite fidelite = pizzeria.fidelite(1);
            assertFalse(fidelite.estFidele(stephanie));

            commander(stephanie, MenuPizzeria.PIZZA_MARINIERE, 1);

            assertTrue(fidelite.estFidele(stephanie));
        }
    }

}
