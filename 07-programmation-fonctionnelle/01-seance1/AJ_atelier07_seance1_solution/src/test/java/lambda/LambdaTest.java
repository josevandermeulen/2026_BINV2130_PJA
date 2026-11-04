package lambda;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LambdaTest {

    private final List<Integer> numbers = Arrays.asList(153, 22, 4567, 50, 209, 34, 1040);
    private final List<String> words = Arrays.asList("hello", "bonjour", "goeiedag", "hallo", "hej");

    @Nested
    @DisplayName("Question 14 : allMatches (Predicate)")
    class Question14 {

        @Test
        void allMatches_findsIntegersAbove200() {
            assertEquals(Arrays.asList(4567, 209, 1040), Lambda.allMatches(numbers, i -> i > 200));
        }

        @Test
        void allMatches_findsEvenIntegers() {
            assertEquals(Arrays.asList(22, 50, 34, 1040), Lambda.allMatches(numbers, i -> i % 2 == 0));
        }
    }

    @Nested
    @DisplayName("Question 15 : transformAll (Function)")
    class Question15 {

        @Test
        void transformAll_multipliesIntegersByTwo() {
            assertEquals(Arrays.asList(306, 44, 9134, 100, 418, 68, 2080), Lambda.transformAll(numbers, i -> i * 2));
        }
    }

    @Nested
    @DisplayName("Question 16 : généricité")
    class Question16 {

        @Test
        void allMatches_findsWordsStartingWithH() {
            assertEquals(Arrays.asList("hello", "hallo", "hej"), Lambda.allMatches(words, s -> s.startsWith("h")));
        }

        @Test
        void transformAll_returnsLengthOfEachWord() {
            assertEquals(Arrays.asList(5, 7, 8, 5, 3), Lambda.transformAll(words, String::length));
        }
    }

    @Nested
    @DisplayName("Question 17 : filter (API Stream)")
    class Question17 {

        @Test
        void filter_findsIntegersAbove200() {
            assertEquals(Arrays.asList(4567, 209, 1040), Lambda.filter(numbers, i -> i > 200));
        }
    }

    @Nested
    @DisplayName("Question 18 : map (API Stream)")
    class Question18 {

        @Test
        void map_returnsLengthOfEachWord() {
            assertEquals(Arrays.asList(5, 7, 8, 5, 3), Lambda.map(words, String::length));
        }
    }
}
