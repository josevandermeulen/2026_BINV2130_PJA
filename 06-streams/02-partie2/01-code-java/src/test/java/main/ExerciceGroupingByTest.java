package main;

import domaine.Trader;
import domaine.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExerciceGroupingByTest {

    private Trader raoul, mario, alan, brian;
    private Transaction t1, t2, t3, t4, t5, t6;
    private ExerciceGroupingBy exercice;

    @BeforeEach
    void setUp() {
        raoul = new Trader("Raoul", "Cambridge");
        mario = new Trader("Mario", "Milan");
        alan = new Trader("Alan", "Cambridge");
        brian = new Trader("Brian", "Cambridge");

        t1 = new Transaction(brian, 2011, 300);
        t2 = new Transaction(raoul, 2012, 1000);
        t3 = new Transaction(raoul, 2011, 400);
        t4 = new Transaction(mario, 2012, 710);
        t5 = new Transaction(mario, 2012, 700);
        t6 = new Transaction(alan, 2012, 950);

        exercice = new ExerciceGroupingBy(Arrays.asList(t1, t2, t3, t4, t5, t6));
    }

    @Nested
    @DisplayName("Question 3 : transactions par trader")
    class Question3 {

        @Test
        void groupBy1_groupsTransactionsByTrader() {
            Map<Trader, List<Transaction>> expected = new HashMap<>();
            expected.put(raoul, Arrays.asList(t2, t3));
            expected.put(mario, Arrays.asList(t4, t5));
            expected.put(alan, Arrays.asList(t6));
            expected.put(brian, Arrays.asList(t1));

            assertEquals(expected, exercice.groupBy1());
        }
    }

    @Nested
    @DisplayName("Question 4 : comptage par trader (counting)")
    class Question4 {

        @Test
        void groupBy2_countsTransactionsByTrader() {
            Map<Trader, Long> expected = new HashMap<>();
            expected.put(raoul, 2L);
            expected.put(mario, 2L);
            expected.put(alan, 1L);
            expected.put(brian, 1L);

            assertEquals(expected, exercice.groupBy2());
        }
    }

    @Nested
    @DisplayName("Question 5 : regroupement par niveau")
    class Question5 {

        @Test
        void groupBy3_groupsTransactionsByLevel() {
            Map<ExerciceGroupingBy.TransactionsLevel, List<Transaction>> expected = new HashMap<>();
            expected.put(ExerciceGroupingBy.TransactionsLevel.VERY_HI, Arrays.asList(t2));
            expected.put(ExerciceGroupingBy.TransactionsLevel.HI, Arrays.asList(t6));
            expected.put(ExerciceGroupingBy.TransactionsLevel.ME, Arrays.asList(t4, t5));
            expected.put(ExerciceGroupingBy.TransactionsLevel.LO, Arrays.asList(t1, t3));

            assertEquals(expected, exercice.groupBy3());
        }
    }

    @Nested
    @DisplayName("Question 12 : trader à la plus grande valeur totale par ville")
    class Question12 {

        @Test
        void villeTraderMaxTotal_returnsTraderWithHighestTotalValuePerCity() {
            Map<String, Trader> expected = new HashMap<>();
            expected.put("Cambridge", raoul);
            expected.put("Milan", mario);

            assertEquals(expected, exercice.villeTraderMaxTotal());
        }
    }
}
