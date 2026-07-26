package be.vinci.minitest;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe de tests servant de cobaye au runner : elle mêle à dessein des tests qui passent, un qui
 * échoue et une méthode ordinaire, que le runner doit ignorer.
 * Fournie — aucune modification nécessaire.
 *
 * Comportement attendu du runner sur cette classe :
 *   - 3 tests réussissent
 *   - 1 test échoue intentionnellement
 *   - helperMethod() ne doit PAS être exécutée (pas d'annotation @Test)
 */
public class SampleTests {

    /**
     * Test réussi : vérifie une addition.
     */
    @Test(description = "Addition simple : 1 + 1 doit valoir 2")
    public void testAddition() {
        int result = 1 + 1;
        if (result != 2) {
            throw new AssertionError("Attendu 2, obtenu " + result);
        }
    }

    /**
     * Test réussi : vérifie la longueur d'une chaîne.
     */
    @Test(description = "Longueur de la chaîne \"hello\" vaut 5")
    public void testStringLength() {
        String s = "hello";
        if (s.length() != 5) {
            throw new AssertionError("Longueur attendue 5, obtenu " + s.length());
        }
    }

    /**
     * Test réussi : vérifie qu'une liste n'est pas nulle.
     */
    @Test(description = "Une ArrayList vide n'est pas nulle")
    public void testListNotNull() {
        List<String> list = new ArrayList<>();
        if (list == null) {
            throw new AssertionError("La liste ne devrait pas être nulle");
        }
    }

    /**
     * Test qui échoue à dessein, pour vérifier que le runner rapporte bien les échecs.
     */
    @Test
    public void testIntentionalFailure() {
        // Ce test échoue intentionnellement - votre runner doit le signaler [FAIL]
        throw new RuntimeException("Ce test échoue intentionnellement");
    }

    // Méthode utilitaire sans @Test — ne doit PAS être découverte par le runner
    /**
     * Méthode ordinaire, sans annotation : le runner ne doit pas l'exécuter.
     *
     * @return une chaîne quelconque
     */
    public String helperMethod() {
        return "Je ne suis pas un test !";
    }
}
