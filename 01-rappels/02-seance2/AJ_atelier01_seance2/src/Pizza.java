import java.util.ArrayList;
import java.util.Iterator;

// Question 3 : la classe abstraite Pizza — constructeurs (le second invoque le premier)
/**
 * Une pizza, c'est-à-dire un titre, une description et les ingrédients qui la composent.
 *
 * La classe est abstraite : elle porte le comportement commun aux pizzas de la carte
 * (PizzaComposee) et à celles que le client compose lui-même (PizzaComposable).
 * Elle est itérable, de sorte qu'une boucle `for` parcoure directement ses ingrédients.
 */
public abstract class Pizza implements Iterable<Ingredient> {
    /**
     * Le prix de départ d'une pizza, en euros, avant l'ajout du moindre ingrédient.
     */
    public static final double PRIX_BASE = 5;

    private String titre;

    private String description;

    private ArrayList<Ingredient> ingredients = new ArrayList<>();

    /**
     * Crée une pizza sans aucun ingrédient.
     *
     * @param titre       le titre de la pizza
     * @param description la description de la pizza
     */
    public Pizza(String titre, String description) {
        this.titre = titre;
        this.description = description;
    }

    /**
     * Crée une pizza garnie des ingrédients donnés.
     *
     * @param titre       le titre de la pizza
     * @param description la description de la pizza
     * @param ingredients les ingrédients composant la pizza, sans doublon
     * @throws IllegalArgumentException si le même ingrédient figure deux fois dans la liste
     */
    public Pizza(String titre, String description, ArrayList<Ingredient> ingredients) {
        this(titre, description);

        for (Ingredient i : ingredients) {
            if (this.ingredients.contains(i)) {
                throw new IllegalArgumentException(
                        "Il ne peut pas y avoir deux fois le même ingrédient dans une pizza.");
            }
            this.ingredients.add(i);
        }
    }
    // FinQuestion

    /**
     * Renvoie le titre de la pizza.
     *
     * @return le titre de la pizza
     */
    public String getTitre() {
        return titre;
    }

    /**
     * Renvoie la description de la pizza.
     *
     * @return la description de la pizza
     */
    public String getDescription() {
        return description;
    }

    // Question 3 : itérateur, ajouter (sans doublon), supprimer et calcul du prix
    /**
     * Renvoie un itérateur sur les ingrédients de la pizza.
     *
     * @return un itérateur sur les ingrédients
     */
    @Override
    public Iterator<Ingredient> iterator() {
        return ingredients.iterator();
    }

    /**
     * Ajoute un ingrédient à la pizza, sauf s'il s'y trouve déjà.
     *
     * @param ingredient l'ingrédient à ajouter
     * @return true si l'ingrédient a été ajouté, false s'il était déjà présent
     */
    public boolean ajouter(Ingredient ingredient) {
        if (ingredients.contains(ingredient)) {
            return false;
        }
        return ingredients.add(ingredient);
    }

    /**
     * Retire un ingrédient de la pizza.
     *
     * @param ingredient l'ingrédient à retirer
     * @return true si l'ingrédient a été retiré, false s'il n'était pas présent
     */
    public boolean supprimer(Ingredient ingredient) {
        return ingredients.remove(ingredient);
    }

    /**
     * Calcule le prix de la pizza : le prix de base augmenté du prix de chaque ingrédient.
     *
     * @return le prix de la pizza, en euros
     */
    public double calculerPrix() {
        double prix = PRIX_BASE;
        for (Ingredient i : ingredients) {
            prix += i.getPrix();
        }
        return prix;
    }
    // FinQuestion

    /**
     * Renvoie le titre, la description, la liste des ingrédients et le prix de la pizza.
     *
     * @return la description textuelle de la pizza
     */
    @Override
    public String toString() {
        String infos = titre + "\n" + description + "\nIngrédients : ";
        for (Ingredient ingredient : ingredients) {
            infos += "\n" + ingredient.getNom();
        }
        infos += "\nprix : " + calculerPrix() + " euros";
        return infos;
    }
}
