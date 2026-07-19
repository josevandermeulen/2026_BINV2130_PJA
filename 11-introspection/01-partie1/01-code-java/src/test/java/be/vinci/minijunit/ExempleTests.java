package be.vinci.minijunit;

/**
 * Classe de tests factice utilisée par TestRunnerTest pour vérifier le mini-JUnit :
 * deux réussites, un échec d'assertion, une erreur inattendue, et une méthode
 * non annotée qui ne doit jamais être exécutée.
 */
public class ExempleTests {

    @MonTest
    public void aReussitToujours() {
        Verifications.verifierVrai(true, "ne peut pas échouer");
    }

    @MonTest
    public void bReussitAussi() {
        Verifications.verifierEgalite("ok", "ok");
    }

    @MonTest
    public void cEchoueSurUneAssertion() {
        Verifications.verifierEgalite(1, 2);
    }

    @MonTest
    public void dLanceUneErreurInattendue() {
        throw new IllegalStateException("boum");
    }

    public void pasUnTest() {
        throw new IllegalStateException("cette méthode ne doit jamais être exécutée");
    }

}
