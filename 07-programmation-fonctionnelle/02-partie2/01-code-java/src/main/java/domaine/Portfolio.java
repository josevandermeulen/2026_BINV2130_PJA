package domaine;

import java.util.List;

public class Portfolio {
    private final Trader trader;
    private final List<String> actions;

    public Portfolio(Trader trader, List<String> actions) {
        this.trader = trader;
        this.actions = actions;
    }

    public Trader getTrader() {
        return trader;
    }

    public List<String> getActions() {
        return actions;
    }

    @Override
    public String toString() {
        return "Portfolio{" +
                "trader=" + trader +
                ", actions=" + actions +
                '}';
    }
}
