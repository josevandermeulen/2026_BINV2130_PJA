package be.vinci.mocks.util;

/**
 * Boîte à outils de validation des paramètres, partagée par les classes du domaine.
 *
 * Chaque méthode lève une IllegalArgumentException dès que la valeur reçue ne respecte pas
 * la contrainte annoncée, ce qui évite de répéter le même test dans chaque constructeur.
 */
public interface Util {

    static void checkObject(Object o) {
        if (o == null) {
            throw new IllegalArgumentException("L'objet ne peut pas être null");
        }
    }

    static void checkString(String s) {
        checkObject(s);
        if (s.matches("\\s*")) {
            throw new IllegalArgumentException("La chaîne ne peut pas être vide");
        }
    }

    /**
     * Vérifie qu'une chaîne est un code postal belge : exactement quatre chiffres.
     *
     * @param codePostal la chaîne à vérifier
     * @throws IllegalArgumentException si la chaîne est `null` ou n'est pas formée de quatre chiffres
     */
    static void checkCodePostal(String codePostal) {
        checkObject(codePostal);
        if (!codePostal.matches("\\d{4}")) {
            throw new IllegalArgumentException("Le code postal doit comporter quatre chiffres");
        }
    }
}
