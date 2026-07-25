package domaine;

import util.Util;

public class IngredientQuantifie {
    private Ingredient ingredient;

    private int quantite;

    private Unite unite;

    public IngredientQuantifie(Ingredient ingredient, int quantite, Unite unite) {
        Util.checkObject(ingredient);
        Util.checkStrictlyPositive(quantite);
        Util.checkObject(unite);

        this.ingredient = ingredient;
        this.quantite = quantite;
        this.unite = unite;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        Util.checkStrictlyPositive(quantite);

        this.quantite = quantite;
    }

    public Unite getUnite() {
        return unite;
    }

    public void setUnite(Unite unite) {
        Util.checkObject(unite);

        this.unite = unite;
    }

    @Override
    public String toString() {
        return quantite + " " + unite + " " + ingredient;
    }

}
