import java.util.ArrayList;

// Question 4 : la sous-classe PizzaComposee
/**
 * Une pizza de la carte, dont la composition est fixée par la pizzeria.
 * <p>
 * Ses ingrédients sont donnés à la construction et ne peuvent plus changer : {@link #ajouter} et
 * {@link #supprimer} lèvent une exception plutôt que de modifier la recette. En contrepartie de
 * cette composition imposée, son prix bénéficie d'une remise.
 */
public class PizzaComposee extends Pizza {

    private static final int REMISE = 15;

    /**
     * Crée une pizza de la carte.
     *
     * @param titre       le titre de la pizza
     * @param description la description de la pizza
     * @param ingredients les ingrédients composant la pizza, sans doublon
     * @throws IllegalArgumentException si le même ingrédient figure deux fois dans la liste
     */
    public PizzaComposee(String titre, String description, ArrayList<Ingredient> ingredients) {
        super(titre, description, ingredients);
    }

    /**
     * Calcule le prix de la pizza, remise appliquée puis arrondi à l'euro supérieur.
     *
     * @return le prix remisé de la pizza, en euros
     */
    @Override
    public double calculerPrix() {
        return Math.ceil(super.calculerPrix() * (1 - REMISE / 100.0));
    }

    /**
     * Refuse l'ajout d'un ingrédient : la composition d'une pizza de la carte est figée.
     *
     * @param ingredient l'ingrédient dont l'ajout est demandé
     * @return jamais : la méthode lève toujours une exception
     * @throws UnsupportedOperationException systématiquement
     */
    @Override
    public boolean ajouter(Ingredient ingredient) {
        throw new UnsupportedOperationException("Les ingrédients d'une pizza composée ne peuvent pas être modifiés");
    }

    /**
     * Refuse le retrait d'un ingrédient : la composition d'une pizza de la carte est figée.
     *
     * @param ingredient l'ingrédient dont le retrait est demandé
     * @return jamais : la méthode lève toujours une exception
     * @throws UnsupportedOperationException systématiquement
     */
    @Override
    public boolean supprimer(Ingredient ingredient) {
        throw new UnsupportedOperationException("Les ingrédients d'une pizza composée ne peuvent pas être modifiés");
    }
}
