package domaine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import util.Util;

/**
 * Une pizza, c'est-à-dire un titre, une description et les ingrédients qui la composent.
 *
 * La classe est abstraite : elle porte le comportement commun aux pizzas de la carte
 * (PizzaComposee) et à celles que le client compose lui-même (PizzaComposable).
 * Elle est itérable, de sorte qu'une boucle `for` parcoure directement ses ingrédients.
 * Deux pizzas sont considérées comme identiques dès qu'elles portent le même titre, et leur ordre
 * naturel (Comparable) est celui de leur titre. Sa catégorie n'est pas stockée : elle se déduit de
 * ses ingrédients.
 */
public abstract class Pizza implements Iterable<Ingredient>, Comparable<Pizza>, Cloneable {
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
     * @throws IllegalArgumentException si le titre ou la description est null ou vide
     */
    public Pizza(String titre, String description) {
        Util.checkString(titre);
        Util.checkString(description);

        this.titre = titre;
        this.description = description;
    }

    /**
     * Crée une pizza garnie des ingrédients donnés.
     *
     * @param titre       le titre de la pizza
     * @param description la description de la pizza
     * @param ingredients les ingrédients composant la pizza : au moins un, et sans doublon
     * @throws IllegalArgumentException si un paramètre est null ou vide, si la liste d'ingrédients
     *                                  est vide, ou si le même ingrédient y figure deux fois
     */
    public Pizza(String titre, String description, ArrayList<Ingredient> ingredients) {
        this(titre, description);
        Util.checkObject(ingredients);
        if (ingredients.isEmpty()) {
            throw new IllegalArgumentException("Il faut au minimum un ingrédient pour une pizza");
        }

        for (Ingredient i : ingredients) {
            if (this.ingredients.contains(i)) {
                throw new IllegalArgumentException(
                        "Il ne peut pas y avoir deux fois le même ingrédient dans une pizza.");
            }
            this.ingredients.add(i);
        }
    }

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
     * Renvoie les ingrédients de la pizza, dans une liste non modifiable.
     *
     * @return les ingrédients de la pizza, en lecture seule
     */
    public List<Ingredient> getIngredients() {
        return Collections.unmodifiableList(ingredients);
    }

    /**
     * Renvoie la catégorie de la pizza, déduite de ses ingrédients : VIANDE dès qu'un ingrédient en
     * est, sinon POISSON dès qu'un ingrédient en est, sinon VEGETARIENNE (y compris pour une pizza
     * encore sans ingrédient).
     *
     * @return la catégorie de la pizza
     */
    public Categorie getCategorie() {
        boolean poisson = false;
        for (Ingredient ingredient : ingredients) {
            if (ingredient.getCategorie() == Categorie.VIANDE) {
                return Categorie.VIANDE;
            }
            if (ingredient.getCategorie() == Categorie.POISSON) {
                poisson = true;
            }
        }

        if (poisson) {
            return Categorie.POISSON;
        }
        return Categorie.VEGETARIENNE;
    }

    /**
     * Ajoute un ingrédient à la pizza, sauf s'il s'y trouve déjà.
     *
     * @param ingredient l'ingrédient à ajouter
     * @return true si l'ingrédient a été ajouté, false s'il était déjà présent
     * @throws IllegalArgumentException si l'ingrédient est null
     */
    public boolean ajouter(Ingredient ingredient) {
        Util.checkObject(ingredient);
        if (ingredients.contains(ingredient)) {
            return false;
        }
        return ingredients.add(ingredient);
    }

    // Question 9
    /**
     * Ajoute plusieurs ingrédients à la pizza, en ignorant ceux qui s'y trouvent déjà. Le
     * paramètre est un varargs : on peut passer autant d'ingrédients qu'on veut, séparés par des
     * virgules, ou un tableau.
     *
     * @param ingredients les ingrédients à ajouter
     * @return le nombre d'ingrédients effectivement ajoutés
     * @throws IllegalArgumentException si le tableau ou l'un des ingrédients est null
     */
    public int ajouter(Ingredient... ingredients) {
        Util.checkObject(ingredients);

        int nombre = 0;
        for (Ingredient ingredient : ingredients) {
            if (ajouter(ingredient)) {
                nombre++;
            }
        }
        return nombre;
    }

    // FinQuestion

    /**
     * Retire un ingrédient de la pizza.
     *
     * @param ingredient l'ingrédient à retirer
     * @return true si l'ingrédient a été retiré, false s'il n'était pas présent
     * @throws IllegalArgumentException si l'ingrédient est null
     */
    public boolean supprimer(Ingredient ingredient) {
        Util.checkObject(ingredient);
        return ingredients.remove(ingredient);
    }

    /**
     * Renvoie une copie de la pizza, avec ses propres ingrédients. Chaque sous-classe la redéfinit
     * avec son propre type de retour (type de retour covariant).
     *
     * @return une copie de la pizza
     */
    public abstract Pizza copier();

    // Question 12
    /**
     * Renvoie un clone de la pizza : même titre, même description, mêmes ingrédients.
     *
     * Contrairement à copier, aucun constructeur n'est appelé : chaque attribut est recopié tel
     * quel. La liste des ingrédients, elle, n'est pas partagée : ajouter ou retirer un ingrédient
     * du clone ne change pas l'original, et inversement. Les ingrédients eux-mêmes restent communs :
     * ce sont ceux du catalogue.
     *
     * @return le clone de la pizza
     */
    @Override
    public Pizza clone() {
        try {
            Pizza copie = (Pizza) super.clone();
            copie.ingredients = new ArrayList<>(ingredients);
            return copie;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    // FinQuestion

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

    /**
     * Compare deux pizzas selon l'ordre alphabétique de leur titre : c'est l'ordre de la carte.
     *
     * @param autre la pizza à comparer à celle-ci
     * @return un entier négatif, nul ou positif selon que cette pizza se place avant, au même rang
     *         ou après l'autre
     */
    @Override
    public int compareTo(Pizza autre) {
        return titre.compareTo(autre.titre);
    }

    /**
     * Compare deux pizzas sur leur seul titre.
     *
     * @param o l'objet à comparer à cette pizza
     * @return true si o est une pizza portant le même titre
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pizza that = (Pizza) o;
        return titre.equals(that.titre);
    }

    /**
     * Renvoie un code de hachage calculé sur le titre, cohérent avec equals(Object).
     *
     * @return le code de hachage de la pizza
     */
    @Override
    public int hashCode() {
        return Objects.hash(titre);
    }

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
