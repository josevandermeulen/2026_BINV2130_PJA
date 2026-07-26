package domaine;

import util.Util;

import java.util.Objects;

/**
 * Un ingrédient, identifié par son seul nom.
 *
 * L'égalité porte sur le nom : c'est ce qui permet à un plat de retrouver un ingrédient dans sa
 * liste, et d'en interdire deux exemplaires.
 */
public class Ingredient {
    private final String nom;

    /**
     * Crée un ingrédient.
     *
     * @param nom le nom de l'ingrédient
     * @throws IllegalArgumentException si le nom est null ou vide
     */
    public Ingredient(String nom) {
        Util.checkString(nom);

        this.nom = nom;
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
     * Renvoie le nom de l'ingrédient.
     *
     * @return le nom de l'ingrédient
     */
    @Override
    public String toString() {
        return nom;
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
