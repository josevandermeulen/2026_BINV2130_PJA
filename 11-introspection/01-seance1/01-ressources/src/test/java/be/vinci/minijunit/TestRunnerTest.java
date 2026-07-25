package be.vinci.minijunit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class TestRunnerTest {

    TestRunner runner;

    @BeforeEach
    void setUp() {
        runner = new TestRunner();
    }

    @Nested
    @DisplayName("Question 1 : l'annotation @MonTest")
    class Question1 {

        @Test
        void annotationConserveeALExecution() {
            Retention retention = MonTest.class.getAnnotation(Retention.class);

            assertNotNull(retention, "@MonTest doit porter @Retention(RetentionPolicy.RUNTIME)");
            assertEquals(RetentionPolicy.RUNTIME, retention.value());
        }

        @Test
        void annotationReserveeAuxMethodes() {
            Target target = MonTest.class.getAnnotation(Target.class);

            assertNotNull(target, "@MonTest doit porter @Target(ElementType.METHOD)");
            assertArrayEquals(new ElementType[]{ElementType.METHOD}, target.value());
        }
    }

    @Nested
    @DisplayName("Question 2 : trouver les méthodes de test")
    class Question2 {

        @Test
        void trouveExactementLesMethodesAnnotees() {
            List<String> noms = runner.trouverMethodesDeTest(ExempleTests.class).stream()
                    .map(Method::getName)
                    .collect(Collectors.toList());

            assertEquals(Arrays.asList("aReussitToujours", "bReussitAussi",
                    "cEchoueSurUneAssertion", "dLanceUneErreurInattendue"), noms);
        }

        @Test
        void aucuneMethodePourUneClasseSansTest() {
            assertTrue(runner.trouverMethodesDeTest(String.class).isEmpty());
        }
    }

    @Nested
    @DisplayName("Question 3 : exécuter les tests et produire un rapport")
    class Question3 {

        @Test
        void compteReussitesEchecsEtErreurs() {
            RapportExecution rapport = runner.executerTests(ExempleTests.class);

            assertAll(
                    () -> assertEquals(4, rapport.getResultats().size()),
                    () -> assertEquals(2, rapport.compter(ResultatTest.Statut.REUSSITE)),
                    () -> assertEquals(1, rapport.compter(ResultatTest.Statut.ECHEC)),
                    () -> assertEquals(1, rapport.compter(ResultatTest.Statut.ERREUR))
            );
        }

        @Test
        void classeCorrectementChaqueResultat() {
            RapportExecution rapport = runner.executerTests(ExempleTests.class);
            List<ResultatTest> resultats = rapport.getResultats();

            assertAll(
                    () -> assertEquals(ResultatTest.Statut.REUSSITE, resultats.get(0).getStatut()),
                    () -> assertEquals(ResultatTest.Statut.REUSSITE, resultats.get(1).getStatut()),
                    () -> assertEquals(ResultatTest.Statut.ECHEC, resultats.get(2).getStatut()),
                    () -> assertEquals(ResultatTest.Statut.ERREUR, resultats.get(3).getStatut())
            );
        }

        @Test
        void lEchecDAssertionConserveLeMessage() {
            RapportExecution rapport = runner.executerTests(ExempleTests.class);
            ResultatTest echec = rapport.getResultats().get(2);

            assertAll(
                    () -> assertEquals("cEchoueSurUneAssertion", echec.getNomMethode()),
                    () -> assertNotNull(echec.getMessage())
            );
        }
    }

    @Nested
    @DisplayName("Question 5 : @AvantChaqueTest")
    class Question5 {

        @Test
        void executeeAvantChaqueTestSurUneNouvelleInstance() {
            ExempleTestsAvecAvant.JOURNAL.clear();

            RapportExecution rapport = runner.executerTests(ExempleTestsAvecAvant.class);

            assertAll(
                    () -> assertEquals(2, rapport.compter(ResultatTest.Statut.REUSSITE)),
                    () -> assertEquals(Arrays.asList("avant", "testA", "avant", "testB"),
                            ExempleTestsAvecAvant.JOURNAL)
            );
        }
    }

    @Nested
    @DisplayName("Question 6 : @Affichage")
    class Question6 {

        @Test
        void libelleDeLAnnotationDansLeRapport() {
            RapportExecution rapport = runner.executerTests(ExempleTestsAvecAffichage.class);
            ResultatTest avecLibelle = rapport.getResultats().get(0);

            assertAll(
                    () -> assertEquals("aAvecLibelle", avecLibelle.getNomMethode()),
                    () -> assertEquals("l'addition des entiers fonctionne", avecLibelle.getLibelle()),
                    () -> assertEquals(ResultatTest.Statut.REUSSITE, avecLibelle.getStatut())
            );
        }

        @Test
        void nomDeMethodeSansAnnotation() {
            RapportExecution rapport = runner.executerTests(ExempleTestsAvecAffichage.class);
            ResultatTest sansLibelle = rapport.getResultats().get(1);

            assertAll(
                    () -> assertEquals("bSansLibelle", sansLibelle.getNomMethode()),
                    () -> assertEquals("bSansLibelle", sansLibelle.getLibelle())
            );
        }
    }

    @Nested
    @DisplayName("Question 7 : @Repeter")
    class Question7 {

        @Test
        void invoqueeAutantDeFoisQueDemande() {
            ExempleTestsAvecRepetition.JOURNAL.clear();

            RapportExecution rapport = runner.executerTests(ExempleTestsAvecRepetition.class);

            assertAll(
                    () -> assertEquals(3, rapport.getResultats().size()),
                    () -> assertEquals(3, rapport.compter(ResultatTest.Statut.REUSSITE))
            );
        }

        @Test
        void uneInstanceFraichePourChaqueRepetition() {
            ExempleTestsAvecRepetition.JOURNAL.clear();

            runner.executerTests(ExempleTestsAvecRepetition.class);

            assertEquals(Arrays.asList("compteur=1", "compteur=1", "compteur=1"),
                    ExempleTestsAvecRepetition.JOURNAL);
        }
    }

    @Nested
    @DisplayName("Question 10 (optionnelle) : @TestDesactive")
    class Question10 {

        @Test
        void rapporteeIgnoreeSansEtreInvoquee() {
            RapportExecution rapport = runner.executerTests(ExempleTestsAvecDesactive.class);
            ResultatTest ignore = rapport.getResultats().get(0);

            assertAll(
                    () -> assertEquals(2, rapport.getResultats().size()),
                    () -> assertEquals("aEstDesactive", ignore.getNomMethode()),
                    () -> assertEquals(ResultatTest.Statut.IGNORE, ignore.getStatut()),
                    () -> assertNull(ignore.getMessage()),
                    () -> assertEquals(1, rapport.compter(ResultatTest.Statut.REUSSITE))
            );
        }
    }

}
