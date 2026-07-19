package main;

import domaine.Employe;
import domaine.Genre;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExerciceFunctionalInterfaceTest {

    private final Employe bob = new Employe(Genre.HOMME, 185, "Bob");
    private final Employe alice = new Employe(Genre.FEMME, 225, "Alice");
    private final Employe john = new Employe(Genre.HOMME, 155, "John");
    private final Employe carole = new Employe(Genre.FEMME, 165, "Carole");
    private final Employe alex = new Employe(Genre.HOMME, 185, "Alex");
    private final Employe bart = new Employe(Genre.HOMME, 185, "Bart");

    private final ExerciceFunctionalInterface exercice = new ExerciceFunctionalInterface(
            Arrays.asList(bob, alice, john, carole, alex, bart));

    @Nested
    @DisplayName("Question 10 : implémente Function au lieu du lambda (map)")
    class Question10 {

        @Test
        void exMap_returnsMenNamesSortedByHeightDesc() {
            assertEquals(Arrays.asList("Bob", "Alex", "Bart", "John"), exercice.exMap());
        }
    }

    @Nested
    @DisplayName("Question 12 : implémente Consumer au lieu du lambda (forEach)")
    class Question12 {

        @Test
        void exForEach_returnsAllNamesInOriginalOrder() {
            List<String> expected = Arrays.asList("Bob", "Alice", "John", "Carole", "Alex", "Bart");
            assertEquals(expected, exercice.exForEach());
        }
    }

    @Nested
    @DisplayName("Question 13 : Comparator en lambda")
    class Question13 {

        @Test
        void exComparator_returnsEmployeesSortedByHeightDescThenNameAsc() {
            List<Employe> expected = Arrays.asList(alice, alex, bart, bob, carole, john);
            assertEquals(expected, exercice.exComparator());
        }
    }
}
