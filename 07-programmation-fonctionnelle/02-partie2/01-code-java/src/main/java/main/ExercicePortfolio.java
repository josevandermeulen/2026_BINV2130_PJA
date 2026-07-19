package main;

import domaine.Portfolio;
import domaine.Trader;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExercicePortfolio {

    // ====== Table des prix (exemple pédagogique) ======
    static final Map<String, Double> PRICES = new HashMap<>();

    static {
        PRICES.put("AAPL", 226.12); PRICES.put("GOOGL", 181.40); PRICES.put("GOOG", 180.95);
        PRICES.put("TSLA", 245.70); PRICES.put("NVDA", 118.35);  PRICES.put("MSFT", 412.55);
        PRICES.put("AMZN", 181.90); PRICES.put("SAP", 189.20);   PRICES.put("NFLX", 647.80);
        PRICES.put("ORCL", 143.10); PRICES.put("META", 512.30);  PRICES.put("AMD", 157.40);
        PRICES.put("INTC", 32.85);  PRICES.put("AVGO", 1705.00); PRICES.put("ASML", 980.00);
        PRICES.put("TSM", 164.50);  PRICES.put("CRM", 289.60);   PRICES.put("NOW", 776.20);
        PRICES.put("SNOW", 156.40); PRICES.put("MDB", 338.90);   PRICES.put("UBER", 76.10);
        PRICES.put("ABNB", 151.30); PRICES.put("LYFT", 13.40);   PRICES.put("DASH", 123.80);
        PRICES.put("PANW", 320.50); PRICES.put("CRWD", 395.60);  PRICES.put("ZS", 192.20);
        PRICES.put("FTNT", 73.40);  PRICES.put("SHOP", 85.10);   PRICES.put("SQ", 65.70);
        PRICES.put("PYPL", 64.20);  PRICES.put("MELI", 1705.00); PRICES.put("COST", 909.00);
        PRICES.put("WMT", 67.30);   PRICES.put("TGT", 161.40);   PRICES.put("HD", 369.20);
        PRICES.put("DIS", 93.50);   PRICES.put("SONY", 84.60);   PRICES.put("RBLX", 38.90);
        PRICES.put("EA", 140.70);   PRICES.put("ADBE", 554.10);  PRICES.put("INTU", 652.30);
        PRICES.put("ADSK", 241.00); PRICES.put("ANSS", 323.40);  PRICES.put("IBM", 185.20);
        PRICES.put("INFY", 19.10);  PRICES.put("RIVN", 17.30);   PRICES.put("LCID", 3.20);
        PRICES.put("NIO", 4.80);
        // Actions jamais détenues par aucun trader, volontairement absentes des portefeuilles
        PRICES.put("DUMMY1", 1.0); PRICES.put("DUMMY2", 2.0); PRICES.put("DUMMY3", 3.0);
    }

    public static void main(String[] args) {
        // ====== Données d'exemple (doublons voulus) ======
        List<Portfolio> portfolios = Arrays.asList(
                new Portfolio(new Trader("Raoul", "Cambridge"), Arrays.asList("AAPL", "GOOGL", "TSLA", "NVDA")),
                new Portfolio(new Trader("Mario", "Milan"),      Arrays.asList("MSFT", "TSLA", "AMZN", "SAP")),
                new Portfolio(new Trader("Alan", "Cambridge"),   Arrays.asList("NFLX", "AMZN", "ORCL", "META")),
                new Portfolio(new Trader("Brian", "London"),     Arrays.asList("AMD", "INTC", "NVDA", "AVGO")),
                new Portfolio(new Trader("Sophia", "Oxford"),    Arrays.asList("ASML", "TSM", "NVDA", "AMD")),
                new Portfolio(new Trader("Luca", "Rome"),        Arrays.asList("CRM", "NOW", "SNOW", "MDB")),
                new Portfolio(new Trader("Emma", "Cambridge"),   Arrays.asList("UBER", "ABNB", "LYFT", "DASH")),
                new Portfolio(new Trader("Zoé", "Milan"),        Arrays.asList("PANW", "CRWD", "ZS", "FTNT")),
                new Portfolio(new Trader("Noah", "Brussels"),    Arrays.asList("SHOP", "SQ", "PYPL", "MELI")),
                new Portfolio(new Trader("Léo", "Paris"),        Arrays.asList("COST", "WMT", "TGT", "HD")),
                new Portfolio(new Trader("Mia", "Zurich"),       Arrays.asList("DIS", "SONY", "RBLX", "EA")),
                new Portfolio(new Trader("Ava", "Amsterdam"),    Arrays.asList("ADBE", "INTU", "ADSK", "ANSS")),
                new Portfolio(new Trader("Ivan", "Berlin"),      Arrays.asList("IBM", "ORCL", "SAP", "INFY")),
                new Portfolio(new Trader("Omar", "Madrid"),      Arrays.asList("GOOG", "META", "AAPL", "MSFT")),
                new Portfolio(new Trader("Nora", "Lisbon"),      Arrays.asList("TSLA", "RIVN", "LCID", "NIO"))
        );

        // Exercice 5.1
        System.out.println("===== Exercice 5.1 =====");
        System.out.println("Actions de tous les portfolios (avec doublons):");

        List<String> actions = actions(portfolios);
        System.out.println("Il y a " + actions.size() + " actions au total dans tous les portfolios.");
        actions.forEach(System.out::println);

        // Exercice 5.2
        System.out.println("\n===== Exercice 5.2 =====");
        System.out.println("\nActions uniques avec leur prix, triées par prix croissant:");
        List<Map.Entry<String, Double>> entries = portfolioToAction(portfolios, PRICES);
        System.out.println("Il y a " + entries.size() + " actions uniques au total.");
        entries.forEach(System.out::println);

        // Exercice 7.1
        System.out.println("\n===== Exercice 7.1 =====");
        System.out.println("\nCouples (action, trader):");
        actionTraderPairs(portfolios).forEach(System.out::println);

        // Exercice 7.2
        System.out.println("\n===== Exercice 7.2 =====");
        System.out.println("\nMap associant à chaque action la liste des traders qui la possèdent:");
        Map<String, List<Trader>> actionsMap = tradersByAction(portfolios);
        actionsMap.forEach((action, traders) -> {
            System.out.println(action + " -> " + traders);
        });
        System.out.println("\nIl y a " + actionsMap.size() + " actions au total dans tous les portfolios.");
        double moyenne = actionsMap.values().stream()
                .mapToInt(List::size)
                .average()
                .orElse(0.0);

        System.out.printf("Chaque action est possédée en moyenne par %.2f traders.%n", moyenne);
    }

    /**
     * Renvoie la liste des actions de tous les portefeuilles (avec doublons).
     */
    static List<String> actions(List<Portfolio> portfolios) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Renvoie une liste des actions uniques avec leur prix, triée par prix croissant
     * (puis par symbole en cas d'égalité).
     *
     * Au lieu de créer une classe ActionPrice, on utilise ici la classe utilitaire
     * Map.entry fournie par Java. Map.entry(k, v) construit un couple clé/valeur,
     * pratique pour manipuler des paires simples sans créer une nouvelle classe.
     */
    static List<Map.Entry<String, Double>> portfolioToAction(List<Portfolio> portfolios, Map<String, Double> prices) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Renvoie une liste de couples (action, trader). Chaque couple indique qu'un
     * trader possède une action donnée.
     */
    static List<Map.Entry<String, Trader>> actionTraderPairs(List<Portfolio> portfolios) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Renvoie une map associant à chaque action la liste des traders qui la possèdent.
     */
    static Map<String, List<Trader>> tradersByAction(List<Portfolio> portfolios) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
