package be.vinci.validation;

import static org.junit.jupiter.api.Assertions.*;

import be.vinci.domaine.Adresse;
import be.vinci.domaine.Etudiant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class ValidateurTest {

    Validateur validateur;

    Adresse adresseValide;

    @BeforeEach
    void setUp() {
        validateur = new Validateur();
        adresseValide = new Adresse("rue de la Paix", 12);
    }

    List<String> champs(List<Violation> violations) {
        return violations.stream().map(Violation::getChamp).collect(Collectors.toList());
    }

    @Nested
    @DisplayName("Question 1 : l'annotation @NonNul")
    class Question1 {

        @Test
        void annotationConserveeALExecution() {
            Retention retention = NonNul.class.getAnnotation(Retention.class);

            assertNotNull(retention, "@NonNul doit porter @Retention(RetentionPolicy.RUNTIME)");
            assertEquals(RetentionPolicy.RUNTIME, retention.value());
        }

        @Test
        void annotationReserveeAuxChamps() {
            Target target = NonNul.class.getAnnotation(Target.class);

            assertNotNull(target, "@NonNul doit porter @Target(ElementType.FIELD)");
            assertArrayEquals(new ElementType[]{ElementType.FIELD}, target.value());
        }
    }

    @Nested
    @DisplayName("Question 2 : trouver les champs annotés")
    class Question2 {

        @Test
        void trouveExactementLesChampsAnnotes() {
            List<String> noms = validateur.trouverChampsAnnotes(Etudiant.class, NonNul.class)
                    .stream()
                    .map(Field::getName)
                    .collect(Collectors.toList());

            assertEquals(Arrays.asList("adresse", "matricule", "nom"), noms);
        }

        @Test
        void distingueLesAnnotations() {
            List<String> noms = validateur.trouverChampsAnnotes(Etudiant.class, LongueurMin.class)
                    .stream()
                    .map(Field::getName)
                    .collect(Collectors.toList());

            assertEquals(Arrays.asList("nom"), noms);
        }

        @Test
        void aucunChampPourUneClasseSansAnnotation() {
            assertTrue(validateur.trouverChampsAnnotes(Object.class, NonNul.class).isEmpty());
        }
    }

    @Nested
    @DisplayName("Question 3 : valider @NonNul")
    class Question3 {

        @Test
        void violationParChampNul() {
            Etudiant etudiant = new Etudiant(null, null, 60, adresseValide);

            List<Violation> violations = validateur.validerNonNul(etudiant);

            assertEquals(Arrays.asList("matricule", "nom"), champs(violations));
        }

        @Test
        void aucuneViolationQuandRienNEstNul() {
            Etudiant etudiant = new Etudiant("Alice", "20240001", 60, adresseValide);

            assertTrue(validateur.validerNonNul(etudiant).isEmpty());
        }
    }

    @Nested
    @DisplayName("Question 4 : valider @LongueurMin")
    class Question4 {

        @Test
        void violationQuandLaChaineEstTropCourte() {
            Etudiant etudiant = new Etudiant("Al", "20240001", 60, adresseValide);

            List<Violation> violations = validateur.validerLongueurMin(etudiant);

            assertEquals(Arrays.asList("nom"), champs(violations));
        }

        @Test
        void aucuneViolationQuandLaLongueurSuffit() {
            Etudiant etudiant = new Etudiant("Ali", "20240001", 60, adresseValide);

            assertTrue(validateur.validerLongueurMin(etudiant).isEmpty());
        }

        @Test
        void unChampNulNEstPasUneViolationDeLongueur() {
            Etudiant etudiant = new Etudiant(null, "20240001", 60, adresseValide);

            assertTrue(validateur.validerLongueurMin(etudiant).isEmpty());
        }
    }

    @Nested
    @DisplayName("Question 5 : valider un objet complet")
    class Question5 {

        @Test
        void agregeToutesLesViolations() {
            Etudiant etudiant = new Etudiant("Al", null, 60, adresseValide);

            RapportValidation rapport = validateur.valider(etudiant);

            assertAll(
                    () -> assertFalse(rapport.estValide()),
                    () -> assertEquals(2, rapport.getViolations().size()),
                    () -> assertTrue(champs(rapport.getViolations()).contains("matricule")),
                    () -> assertTrue(champs(rapport.getViolations()).contains("nom"))
            );
        }

        @Test
        void rapportVidePourUnObjetValide() {
            Etudiant etudiant = new Etudiant("Alice", "20240001", 60, adresseValide);

            RapportValidation rapport = validateur.valider(etudiant);

            assertAll(
                    () -> assertTrue(rapport.estValide()),
                    () -> assertTrue(rapport.getViolations().isEmpty())
            );
        }
    }

    @Nested
    @DisplayName("Question 8 (optionnelle) : @Positif")
    class Question8 {

        @Test
        void violationQuandLaValeurEstNegative() {
            Etudiant etudiant = new Etudiant("Alice", "20240001", -5, adresseValide);

            List<Violation> violations = validateur.validerPositif(etudiant);

            assertEquals(Arrays.asList("nombreDeCredits"), champs(violations));
        }

        @Test
        void violationQuandLaValeurEstNulle() {
            Etudiant etudiant = new Etudiant("Alice", "20240001", 0, adresseValide);

            assertEquals(1, validateur.validerPositif(etudiant).size());
        }

        @Test
        void aucuneViolationQuandLaValeurEstPositive() {
            Etudiant etudiant = new Etudiant("Alice", "20240001", 60, adresseValide);

            assertTrue(validateur.validerPositif(etudiant).isEmpty());
        }
    }

    @Nested
    @DisplayName("Question 9 (optionnelle) : @Valide")
    class Question9 {

        @Test
        void violationsDuChampObjetPrefixeesDeSonNom() {
            Etudiant etudiant = new Etudiant("Alice", "20240001", 60,
                    new Adresse(null, 12));

            RapportValidation rapport = validateur.valider(etudiant);

            assertEquals(Arrays.asList("adresse.rue"), champs(rapport.getViolations()));
        }

        @Test
        void unChampObjetNulNEstPasValideRecursivement() {
            Etudiant etudiant = new Etudiant("Alice", "20240001", 60, null);

            RapportValidation rapport = validateur.valider(etudiant);

            assertEquals(Arrays.asList("adresse"), champs(rapport.getViolations()));
        }
    }

}
