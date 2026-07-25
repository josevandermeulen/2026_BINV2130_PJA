package main;

import domaine.Employe;
import domaine.Genre;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExercicesEmployesTest {

    @Nested
    @DisplayName("Question 4 : boucle implicite")
    class Question4 {

        @Test
        void listeDesHommes_returnsOnlyMen() {
            Employe marie = new Employe(Genre.FEMME, 175, "Marie");
            Employe georges = new Employe(Genre.HOMME, 156, "Georges");
            Employe raphael = new Employe(Genre.HOMME, 187, "Raphaël");
            Employe antoinette = new Employe(Genre.FEMME, 120, "Antoinette");

            List<Employe> employes = Arrays.asList(marie, georges, raphael, antoinette);

            assertEquals(Arrays.asList(georges, raphael), new ExercicesEmployes().listeDesHommes(employes));
        }
    }
}
