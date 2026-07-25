package be.vinci.minitest;

import java.util.List;

/**
 * Point d'entrée. Lance TestRunner sur SampleTests et affiche le rapport.
 * Fourni - aucune modification nécessaire.
 *
 * Sortie attendue (ordre des tests peut varier) :
 *
 *   === Rapport de Tests : SampleTests ===
 *
 *   [PASS] testAddition                    - Addition simple : 1 + 1 doit valoir 2
 *   [PASS] testStringLength                - Longueur de la chaîne "hello" vaut 5
 *   [PASS] testListNotNull                 - Une ArrayList vide n'est pas nulle
 *   [FAIL] testIntentionalFailure          - (aucune description)
 *          Erreur : Ce test échoue intentionnellement
 *
 *   Résultat : 3 réussi(s), 1 échoué(s), 4 au total
 */
public class Main {

    public static void main(String[] args) throws Exception {
        TestRunner runner = new TestRunner();
        List<TestResult> results = runner.run(SampleTests.class);
        runner.printReport(results, SampleTests.class.getSimpleName());
    }
}
