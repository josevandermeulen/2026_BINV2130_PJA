package domaine;

import java.util.Set;

// Question 16 : le stub
/**
 * Un faux stock, écrit à la main pour les tests : les ingrédients manquants sont fixés à la
 * construction, tous les autres sont disponibles.
 */
public class StockStub implements Stock {

    private Set<Ingredient> manquants;

    /**
     * Crée un stock où seuls les ingrédients donnés manquent.
     *
     * @param manquants les ingrédients épuisés
     */
    public StockStub(Ingredient... manquants) {
        this.manquants = Set.of(manquants);
    }

    /**
     * Indique si l'ingrédient fait partie des ingrédients disponibles.
     *
     * @param ingredient l'ingrédient demandé
     * @return false si l'ingrédient fait partie des manquants, true sinon
     */
    @Override
    public boolean estDisponible(Ingredient ingredient) {
        return !manquants.contains(ingredient);
    }

}
