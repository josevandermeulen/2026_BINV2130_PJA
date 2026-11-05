package domaine;

/**
 * Un faux stock, écrit à la main pour les tests : les ingrédients manquants sont fixés à la
 * construction, tous les autres sont disponibles.
 */
public class StockStub implements Stock {

    // TODO Question 16 : un attribut, l'ensemble des ingrédients manquants

    /**
     * Crée un stock où seuls les ingrédients donnés manquent.
     *
     * @param manquants les ingrédients épuisés
     */
    public StockStub(Ingredient... manquants) {
        // TODO Question 16
    }

    /**
     * Indique si l'ingrédient fait partie des ingrédients disponibles.
     *
     * @param ingredient l'ingrédient demandé
     * @return false si l'ingrédient fait partie des manquants, true sinon
     */
    @Override
    public boolean estDisponible(Ingredient ingredient) {
        // TODO Question 16
        throw new UnsupportedOperationException("À implémenter");
    }

}
