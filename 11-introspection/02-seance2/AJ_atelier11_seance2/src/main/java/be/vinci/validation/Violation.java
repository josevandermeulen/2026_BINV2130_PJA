package be.vinci.validation;

/**
 * Une contrainte non respectée : le champ fautif et ce qu'on lui reproche.
 */
public class Violation {

    private final String champ;

    private final String message;

    /**
     * Crée une violation.
     *
     * @param champ   le nom du champ en faute
     * @param message la contrainte non respectée
     */
    public Violation(String champ, String message) {
        this.champ = champ;
        this.message = message;
    }

    /**
     * Renvoie le nom du champ en faute.
     *
     * @return le nom du champ
     */
    public String getChamp() {
        return champ;
    }

    /**
     * Renvoie la contrainte non respectée.
     *
     * @return le message de la violation
     */
    public String getMessage() {
        return message;
    }

    /**
     * Renvoie le champ fautif suivi de son message.
     *
     * @return la représentation textuelle de la violation
     */
    @Override
    public String toString() {
        return champ + " : " + message;
    }

}
