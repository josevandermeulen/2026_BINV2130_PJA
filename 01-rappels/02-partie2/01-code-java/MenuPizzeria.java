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
    public final PizzaComposee p_4saisons, p_4fromages, p_margarita, p_duchef, p_mariniere;

    /**
     * crée les pizzas de la pizzeria.
     */
    public MenuPizzeria() {
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(Ingredients.TOMATE);
        ingredients.add(Ingredients.ARTICHAUTS);
        ingredients.add(Ingredients.JAMBON);
        ingredients.add(Ingredients.OLIVES);
        ingredients.add(Ingredients.PARMESAN);
        ingredients.add(Ingredients.MOZZARELLA);
        p_4saisons = new PizzaComposee("4 saisons", "4 goûts qui défilent selon les saisons", ingredients);
        ingredients = new ArrayList<>();
        ingredients.add(Ingredients.TOMATE);
        ingredients.add(Ingredients.PARMESAN);
        ingredients.add(Ingredients.GORGONZOLA);
        ingredients.add(Ingredients.PECORINO);
        ingredients.add(Ingredients.MOZZARELLA);
        p_4fromages = new PizzaComposee("4 fromages", "le mélange généreux des formages italiens", ingredients);
        ingredients = new ArrayList<>();
        ingredients.add(Ingredients.TOMATE);
        ingredients.add(Ingredients.MOZZARELLA);
        p_margarita = new PizzaComposee("margarita", "la simplissité culinaire", ingredients);
        ingredients = new ArrayList<>();
        ingredients.add(Ingredients.TOMATE);
        ingredients.add(Ingredients.AUBERGINES);
        ingredients.add(Ingredients.JAMBON);
        ingredients.add(Ingredients.EPINARDS);
        ingredients.add(Ingredients.MOZZARELLA);
        ingredients.add(Ingredients.GORGONZOLA);
        p_duchef = new PizzaComposee("du chef", "l'équilibre des saveurs du chef", ingredients);
        ingredients = new ArrayList<>();
        ingredients.add(Ingredients.TOMATE);
        ingredients.add(Ingredients.SCAMPIS);
        ingredients.add(Ingredients.MOZZARELLA);
        p_mariniere = new PizzaComposee("marinière", "les saveurs de l'océan", ingredients);
    }

}
