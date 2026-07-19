package be.vinci.validation;

/**
 * Une violation de validation : le nom du champ fautif et le message expliquant
 * la règle enfreinte.
 */
public class Violation {

    private final String champ;

    private final String message;

    public Violation(String champ, String message) {
        this.champ = champ;
        this.message = message;
    }

    public String getChamp() {
        return champ;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return champ + " : " + message;
    }

}
