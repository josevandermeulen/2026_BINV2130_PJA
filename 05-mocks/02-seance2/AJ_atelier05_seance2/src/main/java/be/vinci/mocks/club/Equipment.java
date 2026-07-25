package be.vinci.mocks.club;

/**
 * Un équipement sportif du club, et son état d'emprunt.
 * <p>
 * Le code identifie l'équipement, le drapeau d'emprunt dit s'il est actuellement sorti du stock.
 */
public class Equipment {
    private final String code;

    private final String name;

    private boolean borrowed;

    /**
     * Crée un équipement, disponible à l'emprunt.
     *
     * @param code le code identifiant l'équipement
     * @param name le nom de l'équipement
     */
    public Equipment(String code, String name) {
        this.code = code;
        this.name = name;
        this.borrowed = false;
    }

    /**
     * Renvoie le code de l'équipement.
     *
     * @return le code de l'équipement
     */
    public String getCode() {
        return this.code;
    }

    /**
     * Renvoie le nom de l'équipement.
     *
     * @return le nom de l'équipement
     */
    public String getName() {
        return this.name;
    }

    /**
     * Indique si l'équipement est actuellement emprunté.
     *
     * @return true si l'équipement est emprunté
     */
    public boolean isBorrowed() {
        return this.borrowed;
    }

    /**
     * Marque l'équipement comme emprunté ou rendu.
     *
     * @param borrowed true pour le marquer emprunté, false pour le rendre disponible
     */
    public void setBorrowed(boolean borrowed) {
        this.borrowed = borrowed;
    }

    /**
     * Renvoie le code, le nom et l'état d'emprunt de l'équipement.
     *
     * @return la représentation textuelle de l'équipement
     */
    public String toString() {
        return "Equipment{code='" + this.code + "', name='" + this.name + "', borrowed=" + this.borrowed + "}";
    }
}
