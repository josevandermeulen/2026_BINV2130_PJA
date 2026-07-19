package be.vinci.minijunit;

/**
 * Classe de tests factice pour @TestDesactive : la méthode désactivée lance
 * une exception si elle est invoquée — le runner ne doit jamais l'exécuter,
 * seulement la rapporter avec le statut IGNORE.
 */
public class ExempleTestsAvecDesactive {

    @TestDesactive
    @MonTest
    public void aEstDesactive() {
        throw new IllegalStateException("cette méthode ne doit jamais être exécutée");
    }

    @MonTest
    public void bResteExecute() {
        Verifications.verifierVrai(true, "ne peut pas échouer");
    }

}
