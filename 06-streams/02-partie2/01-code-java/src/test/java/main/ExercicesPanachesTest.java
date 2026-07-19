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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExercicesPanachesTest {

    private Trader raoul, mario, alan, brian;
    private Transaction t1, t2, t3, t4, t5, t6;
    private ExercicesPanaches exercices;

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

        exercices = new ExercicesPanaches(Arrays.asList(t1, t2, t3, t4, t5, t6));
    }

    @Nested
    @DisplayName("Question 6 : valeur max à Cambridge")
    class Question6 {

        @Test
        void exercice1_returnsMaxValueInCambridge() {
            assertEquals(Optional.of(1000), exercices.exercice1());
        }
    }

    @Nested
    @DisplayName("Question 7 : comptage par trader de Cambridge")
    class Question7 {

        @Test
        void exercice2_countsTransactionsPerCambridgeTrader() {
            Map<Trader, Long> expected = new HashMap<>();
            expected.put(raoul, 2L);
            expected.put(alan, 1L);
            expected.put(brian, 1L);

            assertEquals(expected, exercices.exercice2());
        }
    }

    @Nested
    @DisplayName("Question 8 : nom le plus long (valeur > 500)")
    class Question8 {

        @Test
        void exercice3_returnsLongestTraderNameAboveThreshold() {
            assertEquals(Optional.of("Raoul"), exercices.exercice3());
        }
    }

    @Nested
    @DisplayName("Question 9 : moyenne des valeurs par ville")
    class Question9 {

        @Test
        void exercice4_returnsAverageValueByCity() {
            Map<String, Double> expected = new HashMap<>();
            expected.put("Cambridge", 662.5);
            expected.put("Milan", 705.0);

            assertEquals(expected, exercices.exercice4());
        }
    }

    @Nested
    @DisplayName("Question 10 : transaction minimale à Milan")
    class Question10 {

        @Test
        void exercice5_returnsMinValueTransactionInMilan() {
            assertEquals(Optional.of(t5), exercices.exercice5());
        }
    }

    @Nested
    @DisplayName("Question 11 : transactions par année")
    class Question11 {

        @Test
        void exercice6_groupsTransactionsByYear() {
            Map<Integer, List<Transaction>> expected = new HashMap<>();
            expected.put(2011, Arrays.asList(t1, t3));
            expected.put(2012, Arrays.asList(t2, t4, t5, t6));

            assertEquals(expected, exercices.exercice6());
        }
    }
}
