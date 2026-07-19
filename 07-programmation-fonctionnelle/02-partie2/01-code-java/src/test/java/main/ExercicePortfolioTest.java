package main;

import domaine.Portfolio;
import domaine.Trader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExercicePortfolioTest {

    private Trader raoul;
    private Trader mario;
    private Map<String, Double> prices;
    private List<Portfolio> portfolios;

    @BeforeEach
    void setUp() {
        raoul = new Trader("Raoul", "Cambridge");
        mario = new Trader("Mario", "Milan");

        prices = new HashMap<>();
        prices.put("AAPL", 100.0);
        prices.put("GOOGL", 50.0);
        prices.put("MSFT", 200.0);

        portfolios = Arrays.asList(
                new Portfolio(raoul, Arrays.asList("AAPL", "GOOGL")),
                new Portfolio(mario, Arrays.asList("MSFT", "AAPL"))
        );
    }

    @Nested
    @DisplayName("Question 1 : actions (flatMap)")
    class Question1 {

        @Test
        void actions_returnsAllActionsWithDuplicates() {
            assertEquals(Arrays.asList("AAPL", "GOOGL", "MSFT", "AAPL"), ExercicePortfolio.actions(portfolios));
        }
    }

    @Nested
    @DisplayName("Question 2 : portfolioToAction")
    class Question2 {

        @Test
        void portfolioToAction_returnsUniqueActionsSortedByPriceAsc() {
            List<Map.Entry<String, Double>> expected = Arrays.asList(
                    Map.entry("GOOGL", 50.0),
                    Map.entry("AAPL", 100.0),
                    Map.entry("MSFT", 200.0)
            );
            assertEquals(expected, ExercicePortfolio.portfolioToAction(portfolios, prices));
        }
    }

    @Nested
    @DisplayName("Question 9 (optionnelle) : actionTraderPairs")
    class Question9 {

        @Test
        void actionTraderPairs_returnsOnePairPerActionHeld() {
            List<Map.Entry<String, Trader>> expected = Arrays.asList(
                    Map.entry("AAPL", raoul),
                    Map.entry("GOOGL", raoul),
                    Map.entry("MSFT", mario),
                    Map.entry("AAPL", mario)
            );
            assertEquals(expected, ExercicePortfolio.actionTraderPairs(portfolios));
        }
    }

    @Nested
    @DisplayName("Question 10 (optionnelle) : tradersByAction")
    class Question10 {

        @Test
        void tradersByAction_groupsTradersByAction() {
            Map<String, List<Trader>> expected = new HashMap<>();
            expected.put("AAPL", Arrays.asList(raoul, mario));
            expected.put("GOOGL", Arrays.asList(raoul));
            expected.put("MSFT", Arrays.asList(mario));

            assertEquals(expected, ExercicePortfolio.tradersByAction(portfolios));
        }
    }
}
