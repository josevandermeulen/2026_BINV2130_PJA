package domaine;

import java.util.Objects;

import util.Util;

/**
 * Un ingrédient pouvant composer une pizza, avec le prix qu'il ajoute à celle-ci et sa catégorie
 * (végétarien, viande ou poisson), dont se déduit celle de la pizza.
 *
 * Deux ingrédients sont considérés comme identiques dès qu'ils portent le même nom : c'est ce qui
 * empêche une pizza de contenir deux fois le même ingrédient, quel qu'en soit le prix.
 */
public class Ingredient {
    private String nom;

    private double prix;

    private Categorie categorie;

    /**
     * Crée un ingrédient végétarien.
     *
     * @param nom  le nom de l'ingrédient
     * @param prix le prix que l'ingrédient ajoute à la pizza, en euros ; positif ou nul
     * @throws IllegalArgumentException si le nom est null ou vide, ou si le prix est négatif
     */
    public Ingredient(String nom, double prix) {
        this(nom, prix, Categorie.VEGETARIENNE);
    }

    /**
     * Crée un ingrédient de la catégorie donnée.
     *
     * @param nom       le nom de l'ingrédient
     * @param prix      le prix que l'ingrédient ajoute à la pizza, en euros ; positif ou nul
     * @param categorie la catégorie de l'ingrédient
     * @throws IllegalArgumentException si le nom est null ou vide, si le prix est négatif, ou si la
     *                                  catégorie est null
     */
    public Ingredient(String nom, double prix, Categorie categorie) {
        Util.checkString(nom);
        Util.checkObject(categorie);

        this.nom = nom;
        setPrix(prix);
        this.categorie = categorie;
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
     * Renvoie la catégorie de l'ingrédient.
     *
     * @return la catégorie de l'ingrédient
     */
    public Categorie getCategorie() {
        return categorie;
    }

    /**
     * Modifie le prix de l'ingrédient.
     *
     * @param prix le nouveau prix, en euros ; positif ou nul
     * @throws IllegalArgumentException si le prix est négatif
     */
    public void setPrix(double prix) {
        Util.checkStrictlyPositive(prix);

        this.prix = prix;
    }

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
