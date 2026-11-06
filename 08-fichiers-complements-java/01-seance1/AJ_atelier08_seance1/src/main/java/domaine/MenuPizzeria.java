package domaine;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe MenuPizzeria garde en constantes les pizzas composées de la pizzeria.
 */
public class MenuPizzeria {

    public static final PizzaComposee PIZZA_4SAISONS = new PizzaComposee("4 saisons",
            "4 goûts qui défilent selon les saisons",
            new ArrayList<>(List.of(Ingredients.TOMATE, Ingredients.ARTICHAUTS, Ingredients.JAMBON,
                    Ingredients.OLIVES, Ingredients.PARMESAN, Ingredients.MOZZARELLA)));
    public static final PizzaComposee PIZZA_4FROMAGES = new PizzaComposee("4 fromages",
            "le mélange généreux des formages italiens",
            new ArrayList<>(List.of(Ingredients.TOMATE, Ingredients.PARMESAN, Ingredients.GORGONZOLA,
                    Ingredients.PECORINO, Ingredients.MOZZARELLA)));
    public static final PizzaComposee PIZZA_MARGARITA = new PizzaComposee("margarita", "la simplissité culinaire",
            new ArrayList<>(List.of(Ingredients.TOMATE, Ingredients.MOZZARELLA)));
    public static final PizzaComposee PIZZA_DUCHEF = new PizzaComposee("du chef", "l'équilibre des saveurs du chef",
            new ArrayList<>(List.of(Ingredients.TOMATE, Ingredients.AUBERGINES,
                    Ingredients.JAMBON, Ingredients.EPINARDS,
                    Ingredients.MOZZARELLA, Ingredients.GORGONZOLA)));
    public static final PizzaComposee PIZZA_MARINIERE = new PizzaComposee("marinière", "les saveurs de l'océan",
            new ArrayList<>(List.of(Ingredients.TOMATE, Ingredients.SCAMPIS, Ingredients.MOZZARELLA)));
    public static final PizzaComposee PIZZA_DIAVOLA = new PizzaComposee("diavola", "le salami qui pique",
            new ArrayList<>(List.of(Ingredients.TOMATE, Ingredients.SALAMI, Ingredients.PIMENTS,
                    Ingredients.MOZZARELLA)));
    public static final PizzaComposee PIZZA_ORTOLANA = new PizzaComposee("ortolana", "le potager en fête",
            new ArrayList<>(List.of(Ingredients.TOMATE, Ingredients.AUBERGINES, Ingredients.EPINARDS,
                    Ingredients.ARTICHAUTS, Ingredients.MOZZARELLA)));
    public static final PizzaComposee PIZZA_TONNO = new PizzaComposee("tonno", "le thon et les olives",
            new ArrayList<>(List.of(Ingredients.TOMATE, Ingredients.THON, Ingredients.OLIVES,
                    Ingredients.MOZZARELLA)));
}
