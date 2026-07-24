package main;

import domaine.Trader;
import domaine.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExercicesDeBaseTest {

    private Trader raoul;

    private Trader mario;

    private Trader alan;

    private Trader brian;

    private Transaction t1;

    private Transaction t2;

    private Transaction t3;

    private Transaction t4;

    private Transaction t5;

    private Transaction t6;

    private ExercicesDeBase exercices;

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

        List<Transaction> transactions = Arrays.asList(t1, t2, t3, t4, t5, t6);
        exercices = new ExercicesDeBase(transactions);
    }

    @Nested
    @DisplayName("Question 1 : transactions de 2011")
    class Question1 {

        @Test
        void predicats1_returnsTransactionsOf2011() {
            assertEquals(Arrays.asList(t1, t3), exercices.predicats1());
        }
    }

    @Nested
    @DisplayName("Question 2 : transactions de valeur > 600")
    class Question2 {

        @Test
        void predicats2_returnsTransactionsAbove600() {
            assertEquals(Arrays.asList(t2, t4, t5, t6), exercices.predicats2());
        }
    }

    @Nested
    @DisplayName("Question 3 : transactions de Raoul")
    class Question3 {

        @Test
        void predicats3_returnsRaoulsTransactions() {
            assertEquals(Arrays.asList(t2, t3), exercices.predicats3());
        }
    }

    @Nested
    @DisplayName("Question 5 : villes sans doublon")
    class Question5 {

        @Test
        void map1_returnsDistinctCities() {
            assertEquals(Arrays.asList("Cambridge", "Milan"), exercices.map1());
        }
    }

    @Nested
    @DisplayName("Question 6 : courtiers de Cambridge")
    class Question6 {

        @Test
        void map2_returnsDistinctTradersFromCambridge() {
            assertEquals(Arrays.asList(brian, raoul, alan), exercices.map2());
        }
    }

    @Nested
    @DisplayName("Question 7 : noms des traders joints")
    class Question7 {

        @Test
        void map3_returnsJoinedTraderNames() {
            assertEquals("Brian, Raoul, Mario, Alan", exercices.map3());
        }
    }

    @Nested
    @DisplayName("Question 8 : transactions triées par valeur")
    class Question8 {

        @Test
        void sort1_returnsTransactionsSortedByValue() {
            assertEquals(Arrays.asList(t1, t3, t5, t4, t6, t2), exercices.sort1());
        }
    }

    @Nested
    @DisplayName("Question 9 : noms triés alphabétiquement")
    class Question9 {

        @Test
        void sort2_returnsTraderNamesSortedAlphabetically() {
            assertEquals("Alan, Brian, Mario, Raoul", exercices.sort2());
        }
    }

    @Nested
    @DisplayName("Question 10 : valeur max (reduce)")
    class Question10 {

        @Test
        void reduce1_returnsMaxValue() {
            assertEquals(1000, exercices.reduce1());
        }
    }

    @Nested
    @DisplayName("Question 11 : transaction de valeur minimale (reduce)")
    class Question11 {

        @Test
        void reduce2_returnsMinValueTransaction() {
            assertEquals(t1, exercices.reduce2());
        }
    }

    @Nested
    @DisplayName("Question 12 : trader à la plus grande valeur totale de transactions")
    class Question12 {

        @Test
        void traderValeurTotaleMax_returnsTraderWithHighestTotalValue() {
            assertEquals(mario, exercices.traderValeurTotaleMax());
        }
    }
}
