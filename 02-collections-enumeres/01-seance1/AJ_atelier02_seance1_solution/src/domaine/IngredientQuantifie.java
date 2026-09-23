package domaine;

import util.Util;

import java.util.Objects;

/**
 * Un ingrédient associé à la quantité et à l'unité dans lesquelles un plat l'emploie.
 *
 * Le même ingrédient peut donc figurer en quantités différentes dans deux plats : c'est
 * l'association, pas l'ingrédient, qui porte la quantité.
 *
 * L'égalité porte sur le seul ingrédient : au sein d'un plat, un ingrédient n'est employé qu'une
 * fois, c'est donc lui qui identifie l'association. La quantité et l'unité, elles, changent au
 * cours de la vie de l'objet (setQuantite, setUnite) : les inclure rendrait le hashCode instable,
 * et un ingrédient quantifié déjà rangé dans un HashSet deviendrait introuvable dès qu'on le
 * modifie. Conséquence à connaître : deux ingrédients quantifiés du même ingrédient sont égaux
 * même si leurs quantités diffèrent — ne mettez pas dans un même ensemble ceux de deux plats
 * différents.
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
     * Compare deux ingrédients quantifiés sur leur seul ingrédient.
     *
     * @param o l'objet à comparer à cet ingrédient quantifié
     * @return true si o est un ingrédient quantifié portant le même ingrédient
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IngredientQuantifie that = (IngredientQuantifie) o;
        return ingredient.equals(that.ingredient);
    }

    /**
     * Renvoie un code de hachage calculé sur l'ingrédient, cohérent avec equals(Object).
     *
     * @return le code de hachage de l'ingrédient quantifié
     */
    @Override
    public int hashCode() {
        return Objects.hash(ingredient);
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
