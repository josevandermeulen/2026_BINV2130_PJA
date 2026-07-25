package be.vinci.minitest;

/**
 * Encapsule le résultat d'un seul cas de test.
 * Fourni - aucune modification nécessaire.
 */
/**
 * Le résultat d'une méthode de test exécutée par le mini-framework.
 */
public class TestResult {

    private final String methodName;
    private final String description;
    private final boolean passed;
    private final String errorMessage; // null si le test a réussi

    /**
     * Crée un résultat de test.
     *
     * @param methodName   le nom de la méthode exécutée
     * @param description  la description portée par son annotation
     * @param passed       true si le test a réussi
     * @param errorMessage le message d'échec, ou null si le test a réussi
     */
    public TestResult(String methodName, String description, boolean passed, String errorMessage) {
        this.methodName = methodName;
        this.description = description;
        this.passed = passed;
        this.errorMessage = errorMessage;
    }

    /**
     * Renvoie le nom de la méthode testée.
     *
     * @return le nom de la méthode
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * Renvoie la description du test.
     *
     * @return la description portée par l'annotation
     */
    public String getDescription() {
        return description;
    }

    /**
     * Indique si le test a réussi.
     *
     * @return true si le test a réussi
     */
    public boolean isPassed() {
        return passed;
    }

    /**
     * Renvoie le message d'échec.
     *
     * @return le message d'échec, ou null si le test a réussi
     */
    public String getErrorMessage() {
        return errorMessage;
    }
}
