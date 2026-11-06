package domaine;

/**
 * Le stock d'ingrédients de la pizzeria, isolé derrière une interface.
 *
 * La Pizzeria reçoit son stock à la construction : elle ne sait pas comment il est tenu (en
 * mémoire, dans un fichier, par un service externe), seulement s'il reste d'un ingrédient. C'est
 * ce qui permet de la tester avec un stub ou un mock, sans vrai stock.
 */
public interface Stock {

    /**
     * Indique s'il reste de l'ingrédient donné en stock.
     *
     * @param ingredient l'ingrédient demandé
     * @return true s'il en reste, false sinon
     */
    boolean estDisponible(Ingredient ingredient);

}
