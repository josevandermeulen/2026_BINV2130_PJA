package profil.domaine;

/**
 * Préférences d'un utilisateur.
 */
public class Preferences {

    private final String theme;

    private final String language;

    /**
     * Construit les préférences d'un utilisateur.
     *
     * @param theme    thème de l'interface
     * @param language langue de l'interface
     */
    public Preferences(String theme, String language) {
        this.theme = theme;
        this.language = language;
    }

    /**
     * Renvoie le thème de l'interface.
     *
     * @return thème de l'interface
     */
    public String getTheme() {
        return theme;
    }

    /**
     * Renvoie la langue de l'interface.
     *
     * @return langue de l'interface
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Renvoie une description lisible des préférences : le thème puis la langue.
     *
     * @return description lisible des préférences
     */
    @Override
    public String toString() {
        return "Thème=" + theme + ", Langue=" + language;
    }
}
