package domaine;

import util.Util;

/**
 * Un ingrédient associé à la quantité et à l'unité dans lesquelles un plat l'emploie.
 * <p>
 * Le même ingrédient peut donc figurer en quantités différentes dans deux plats : c'est
 * l'association, pas l'ingrédient, qui porte la quantité.
 */
public class IngredientQuantifie {
    private Ingredient ingredient;

    private int quantite;

    private Unite unite;

    /**
     * Crée un ingrédient quantifié.
     *
     * @param ingredient l'ingrédient employé
     * @param quantite   la quantité employée, strictement positive
     * @param unite      l'unité dans laquelle la quantité est exprimée
     * @throws IllegalArgumentException si l'ingrédient ou l'unité est null, ou si la quantité n'est
     *                                  pas strictement positive
     */
    public IngredientQuantifie(Ingredient ingredient, int quantite, Unite unite) {
        Util.checkObject(ingredient);
        Util.checkStrictlyPositive(quantite);
        Util.checkObject(unite);

        this.ingredient = ingredient;
        this.quantite = quantite;
        this.unite = unite;
    }

    /**
     * Renvoie l'ingrédient employé.
     *
     * @return l'ingrédient
     */
    public Ingredient getIngredient() {
        return ingredient;
    }

    /**
     * Renvoie la quantité employée.
     *
     * @return la quantité, exprimée dans l'unité de cet ingrédient quantifié
     */
    public int getQuantite() {
        return quantite;
    }

    /**
     * Modifie la quantité employée.
     *
     * @param quantite la nouvelle quantité, strictement positive
     * @throws IllegalArgumentException si la quantité n'est pas strictement positive
     */
    public void setQuantite(int quantite) {
        Util.checkStrictlyPositive(quantite);

        this.quantite = quantite;
    }

    /**
     * Renvoie l'unité dans laquelle la quantité est exprimée.
     *
     * @return l'unité de mesure
     */
    public Unite getUnite() {
        return unite;
    }

    /**
     * Modifie l'unité dans laquelle la quantité est exprimée.
     *
     * @param unite la nouvelle unité
     * @throws IllegalArgumentException si l'unité est null
     */
    public void setUnite(Unite unite) {
        Util.checkObject(unite);

        this.unite = unite;
    }

    /**
     * Renvoie la quantité, l'unité et le nom de l'ingrédient.
     *
     * @return la représentation textuelle de l'ingrédient quantifié
     */
    @Override
    public String toString() {
        return quantite + " " + unite + " " + ingredient;
    }

}
