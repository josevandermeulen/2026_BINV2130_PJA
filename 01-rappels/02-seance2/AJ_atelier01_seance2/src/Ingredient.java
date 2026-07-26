import java.util.Objects;

// Question 1 : la classe Ingredient
/**
 * Un ingrédient pouvant composer une pizza, avec le prix qu'il ajoute à celle-ci.
 *
 * Deux ingrédients sont considérés comme identiques dès qu'ils portent le même nom : c'est ce qui
 * empêche une pizza de contenir deux fois le même ingrédient, quel qu'en soit le prix.
 */
public class Ingredient {
    private String nom;

    private double prix;

    /**
     * Crée un ingrédient.
     *
     * @param nom  le nom de l'ingrédient
     * @param prix le prix que l'ingrédient ajoute à la pizza, en euros
     */
    public Ingredient(String nom, double prix) {
        this.nom = nom;
        setPrix(prix);
    }

    /**
     * Renvoie le nom de l'ingrédient.
     *
     * @return le nom de l'ingrédient
     */
    public String getNom() {
        return nom;
    }

    /**
     * Renvoie le prix de l'ingrédient.
     *
     * @return le prix de l'ingrédient, en euros
     */
    public double getPrix() {
        return prix;
    }

    /**
     * Modifie le prix de l'ingrédient.
     *
     * @param prix le nouveau prix, en euros
     */
    public void setPrix(double prix) {
        this.prix = prix;
    }

    // Question 2 : equals/hashCode
    /**
     * Compare deux ingrédients sur leur seul nom.
     *
     * @param o l'objet à comparer à cet ingrédient
     * @return true si o est un ingrédient portant le même nom
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ingredient that = (Ingredient) o;
        return nom.equals(that.nom);
    }

    /**
     * Renvoie un code de hachage calculé sur le nom, cohérent avec equals(Object).
     *
     * @return le code de hachage de l'ingrédient
     */
    @Override
    public int hashCode() {
        return Objects.hash(nom);
    }
}
