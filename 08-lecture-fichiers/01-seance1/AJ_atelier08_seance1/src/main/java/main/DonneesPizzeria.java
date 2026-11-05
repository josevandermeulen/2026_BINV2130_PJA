package main;

import domaine.Client;
import domaine.MenuPizzeria;
import domaine.Pizzeria;
import domaine.Stock;

/**
 * Le jeu de données fixe de la pizzeria : vingt clients et les huit pizzas de la carte.
 *
 * Les fichiers de commandes du dossier commandes/ désignent les clients par ces numéros de
 * téléphone et les pizzas par ces titres.
 */
public class DonneesPizzeria {

    private static final String[][] CLIENTS = {
            {"Leconte", "Emmeline", "0488/98.23.85"},
            {"Ferneeuw", "Stéphanie", "0475/51.30.80"},
            {"Dubois", "Arthur", "0471/12.34.56"},
            {"Janssens", "Lina", "0472/23.45.67"},
            {"Peeters", "Noah", "0473/34.56.78"},
            {"Lambert", "Louise", "0474/45.67.89"},
            {"Maes", "Jules", "0476/56.78.90"},
            {"Dupont", "Olivia", "0477/67.89.01"},
            {"Willems", "Victor", "0478/78.90.12"},
            {"Claes", "Alice", "0479/89.01.23"},
            {"Goossens", "Louis", "0484/90.12.34"},
            {"Wouters", "Mila", "0485/01.23.45"},
            {"Mertens", "Adam", "0486/11.22.33"},
            {"Martin", "Emma", "0487/22.33.44"},
            {"Jacobs", "Gabriel", "0489/33.44.55"},
            {"Lemaire", "Chloé", "0490/44.55.66"},
            {"Simon", "Hugo", "0491/55.66.77"},
            {"Hermans", "Zoé", "0492/66.77.88"},
            {"Dumont", "Nathan", "0493/77.88.99"},
            {"Renard", "Léa", "0494/88.99.00"},
    };

    /**
     * Crée la pizzeria du jeu de données, avec un stock où tout est toujours disponible.
     *
     * @return la pizzeria, avec ses clients et son menu, sans aucune commande
     */
    public static Pizzeria creerPizzeria() {
        return creerPizzeria(ingredient -> true);
    }

    /**
     * Crée la pizzeria du jeu de données, adossée au stock donné.
     *
     * @param stock le stock d'ingrédients de la pizzeria
     * @return la pizzeria, avec ses clients et son menu, sans aucune commande
     */
    public static Pizzeria creerPizzeria(Stock stock) {
        Pizzeria pizzeria = new Pizzeria(stock);

        for (String[] client : CLIENTS) {
            pizzeria.ajouterClient(new Client(client[0], client[1], client[2]));
        }

        pizzeria.ajouterPizza(MenuPizzeria.PIZZA_4SAISONS);
        pizzeria.ajouterPizza(MenuPizzeria.PIZZA_4FROMAGES);
        pizzeria.ajouterPizza(MenuPizzeria.PIZZA_MARGARITA);
        pizzeria.ajouterPizza(MenuPizzeria.PIZZA_DUCHEF);
        pizzeria.ajouterPizza(MenuPizzeria.PIZZA_MARINIERE);
        pizzeria.ajouterPizza(MenuPizzeria.PIZZA_DIAVOLA);
        pizzeria.ajouterPizza(MenuPizzeria.PIZZA_ORTOLANA);
        pizzeria.ajouterPizza(MenuPizzeria.PIZZA_TONNO);
        return pizzeria;
    }

    /**
     * Renvoie les numéros de téléphone des clients du jeu de données, dans l'ordre de leur
     * déclaration.
     *
     * @return les numéros de téléphone des vingt clients
     */
    public static String[] telephones() {
        String[] telephones = new String[CLIENTS.length];
        for (int i = 0; i < CLIENTS.length; i++) {
            telephones[i] = CLIENTS[i][2];
        }
        return telephones;
    }

}
