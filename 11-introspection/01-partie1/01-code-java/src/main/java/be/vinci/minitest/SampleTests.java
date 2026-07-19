package be.vinci.minitest;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe de tests factices utilisée pour valider votre TestRunner.
 * Fournie - aucune modification nécessaire.
 *
 * Comportement attendu du runner sur cette classe :
 *   - 3 tests réussissent
 *   - 1 test échoue intentionnellement
 *   - helperMethod() ne doit PAS être exécutée (pas d'annotation @Test)
 */
public class SampleTests {

    @Test(description = "Addition simple : 1 + 1 doit valoir 2")
    public void testAddition() {
        int result = 1 + 1;
        if (result != 2) {
            throw new AssertionError("Attendu 2, obtenu " + result);
        }
    }

    @Test(description = "Longueur de la chaîne \"hello\" vaut 5")
    public void testStringLength() {
        String s = "hello";
        if (s.length() != 5) {
            throw new AssertionError("Longueur attendue 5, obtenu " + s.length());
        }
    }

    @Test(description = "Une ArrayList vide n'est pas nulle")
    public void testListNotNull() {
        List<String> list = new ArrayList<>();
        if (list == null) {
            throw new AssertionError("La liste ne devrait pas être nulle");
        }
    }

    @Test
    public void testIntentionalFailure() {
        // Ce test échoue intentionnellement - votre runner doit le signaler [FAIL]
        throw new RuntimeException("Ce test échoue intentionnellement");
    }

    // Méthode utilitaire sans @Test — ne doit PAS être découverte par le runner
    public String helperMethod() {
        return "Je ne suis pas un test !";
    }
}
