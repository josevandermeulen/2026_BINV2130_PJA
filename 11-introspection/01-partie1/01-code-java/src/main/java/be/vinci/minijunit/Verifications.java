package be.vinci.minijunit;

/**
 * Assertions maison utilisées par les méthodes de test annotées @MonTest.
 * Une vérification qui échoue lance une AssertionError : c'est ainsi que le
 * TestRunner distingue un échec d'assertion d'une erreur inattendue.
 */
public interface Verifications {

    static void verifierVrai(boolean condition, String message) {
        if (!condition)
            throw new AssertionError(message);
    }

    static void verifierEgalite(Object attendu, Object obtenu) {
        if (attendu == null ? obtenu != null : !attendu.equals(obtenu))
            throw new AssertionError("Attendu <" + attendu + "> mais obtenu <" + obtenu + ">");
    }

}
