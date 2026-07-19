package be.vinci.minitest;

/**
 * Encapsule le résultat d'un seul cas de test.
 * Fourni - aucune modification nécessaire.
 */
public class TestResult {

    private final String methodName;
    private final String description;
    private final boolean passed;
    private final String errorMessage; // null si le test a réussi

    public TestResult(String methodName, String description, boolean passed, String errorMessage) {
        this.methodName = methodName;
        this.description = description;
        this.passed = passed;
        this.errorMessage = errorMessage;
    }

    public String getMethodName()  { return methodName; }
    public String getDescription() { return description; }
    public boolean isPassed()      { return passed; }
    public String getErrorMessage(){ return errorMessage; }
}
