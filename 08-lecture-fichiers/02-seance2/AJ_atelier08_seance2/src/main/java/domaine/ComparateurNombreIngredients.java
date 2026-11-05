package domaine;

import java.util.Comparator;

/**
 * Range les pizzas de la plus garnie à la moins garnie ; à nombre d'ingrédients égal, dans l'ordre
 * naturel des pizzas (l'ordre des titres).
 *
 * Un Comparator est un ordre extérieur aux objets comparés : on peut en écrire autant qu'on veut,
 * alors qu'une classe n'a qu'un ordre naturel (Comparable).
 */
public class ComparateurNombreIngredients implements Comparator<Pizza> {

    /**
     * Compare deux pizzas sur leur nombre d'ingrédients, du plus grand au plus petit.
     *
     * @param pizza1 la première pizza
     * @param pizza2 la seconde pizza
     * @return un entier négatif si pizza1 a plus d'ingrédients que pizza2, positif si elle en a
     *         moins ; à égalité, le résultat de pizza1.compareTo(pizza2)
     */
    @Override
    public int compare(Pizza pizza1, Pizza pizza2) {
        // TODO Question 6
        throw new UnsupportedOperationException("À implémenter");
    }

}
