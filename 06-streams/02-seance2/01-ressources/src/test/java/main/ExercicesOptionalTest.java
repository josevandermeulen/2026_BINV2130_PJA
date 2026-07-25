package main;

import domaine.Trader;
import domaine.Transaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExercicesOptionalTest {

    private final Trader brian = new Trader("Brian", "Cambridge");

    private final Transaction t1 = new Transaction(brian, 2011, 300);

    private final List<Transaction> transactions = Arrays.asList(
            t1,
            new Transaction(new Trader("Raoul", "Cambridge"), 2012, 1000)
    );

    private final ExercicesOptional exercices = new ExercicesOptional();

    @Nested
    @DisplayName("Question 1 : valeur max avec orElse")
    class Question1 {

        @Test
        void optional1_returnsMaxValue() {
            assertEquals(1000, exercices.optional1(transactions));
        }

        @Test
        void optional1_returnsMinusOneWhenEmpty() {
            assertEquals(-1, exercices.optional1(new ArrayList<>()));
        }
    }

    @Nested
    @DisplayName("Question 2 : transaction minimale en Optional")
    class Question2 {

        @Test
        void optional2_returnsMinValueTransaction() {
            assertEquals(Optional.of(t1), exercices.optional2(transactions));
        }

        @Test
        void optional2_returnsEmptyWhenNoTransaction() {
            assertTrue(exercices.optional2(new ArrayList<>()).isEmpty());
        }
    }
}
