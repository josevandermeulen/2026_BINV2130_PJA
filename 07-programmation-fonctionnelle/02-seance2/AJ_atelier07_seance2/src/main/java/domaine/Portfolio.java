package domaine;

import java.util.List;

/**
 * Portefeuille d'un trader : le trader et les symboles des actions qu'il détient.
 */
public class Portfolio {
    private final Trader trader;
    private final List<String> actions;

    /**
     * Crée un portefeuille.
     *
     * @param trader  le trader détenteur du portefeuille
     * @param actions les symboles des actions détenues
     */
    public Portfolio(Trader trader, List<String> actions) {
        this.trader = trader;
        this.actions = actions;
    }

    /**
     * Renvoie le trader détenteur du portefeuille.
     *
     * @return le trader
     */
    public Trader getTrader() {
        return trader;
    }

    /**
     * Renvoie les symboles des actions détenues.
     *
     * @return la liste des symboles d'actions
     */
    public List<String> getActions() {
        return actions;
    }

    /**
     * Renvoie le trader et les actions du portefeuille.
     *
     * @return la représentation textuelle du portefeuille
     */
    @Override
    public String toString() {
        return "Portfolio{" +
                "trader=" + trader +
                ", actions=" + actions +
                '}';
    }
}
