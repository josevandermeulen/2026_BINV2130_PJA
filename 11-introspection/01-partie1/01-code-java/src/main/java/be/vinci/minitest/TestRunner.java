package be.vinci.minitest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Mini framework de tests inspiré de JUnit.
 *
 * Question 9 (examen de septembre) : compléter les 6 blocs TODO dans la méthode run().
 */
public class TestRunner {

    /**
     * Découvre et exécute tous les tests d'une classe.
     *
     * @param testClass la classe contenant les méthodes annotées @Test
     * @return la liste des résultats, un par méthode @Test trouvée
     */
    public List<TestResult> run(Class<?> testClass) throws Exception {
        List<TestResult> results = new ArrayList<>();

        // TODO Question 9 (étape 1) — Créer une instance de testClass via son constructeur sans argument.
        //           Remplacer null par l'instance.
        Object instance = null;

        // TODO Question 9 (étape 2) — Récupérer toutes les méthodes déclarées de testClass.
        //           Remplacez null par un tableau contenant les méthodes déclarées
        Method[] methods = null;

        for (Method method : methods) {

            // TODO Question 9 (étape 3) — Vérifiez la présence de l'annotation @Test sur method.
            //            Remplacez false par un boolean indiquant si l'annotation est présente.
            if (false) {

                // TODO Question 9 (étape 4) — Lire le champ description de l'annotation @Test.
                //            Remplacez "" par la description lue depuis l'annotation.
                String description = "";

                try {
                    // TODO Question 9 (étape 5) ci-dessous.
                    invoque(method, instance);

                    // Si on arrive ici sans exception, le test a réussi.
                    results.add(new TestResult(method.getName(), description, true, null));

                } catch (InvocationTargetException e) {
                    // TODO Question 9 (étape 6) — Le corps du test a levé une exception (test échoué).
                    //           e.getCause() contient l'exception originale du test.
                    //           Ajoutez ici un TestResult avec passed=false et le message de la cause.


                } catch (IllegalAccessException e) {
                    results.add(new TestResult(
                            method.getName(), description, false,
                            "Méthode inaccessible : " + e.getMessage()
                    ));
                }
            }
        }

        return results;
    }

    /**
     * TODO Question 9 (étape 5) — Invoquer method sur instance via la Reflection.
     *           Remplacez le corps de cette méthode par une seule ligne :
     *           method.invoke(instance)
     *
     * Note : la déclaration "throws" ici rend les catch de run() valides.
     *        method.invoke() emballe toute exception du test dans InvocationTargetException.
     */
    private void invoque(Method method, Object instance)
            throws InvocationTargetException, IllegalAccessException {
        throw new UnsupportedOperationException("TODO Question 9 (étape 5) : implémenter l'invocation Reflection");
    }

    // -------------------------------------------------------------------------
    // Fourni — affiche le rapport de tests. Aucune modification nécessaire.
    // -------------------------------------------------------------------------
    public void printReport(List<TestResult> results, String className) {
        System.out.println("=== Rapport de Tests : " + className + " ===\n");

        int passed = 0;
        int failed = 0;

        for (TestResult r : results) {
            String status = r.isPassed() ? "[PASS]" : "[FAIL]";
            String desc = r.getDescription().isEmpty() ? "(aucune description)" : r.getDescription();
            System.out.printf("%-6s %-30s - %s%n", status, r.getMethodName(), desc);

            if (!r.isPassed() && r.getErrorMessage() != null) {
                System.out.println("       Erreur : " + r.getErrorMessage());
            }

            if (r.isPassed()) passed++; else failed++;
        }

        System.out.printf("%nRésultat : %d réussi(s), %d échoué(s), %d au total%n",
                passed, failed, results.size());
    }
}
