package profil.domaine;

/**
 * Profil utilisateur complet, regroupant les informations de base et les préférences.
 */
public class CompleteUserProfile {

    private final String userId;

    private final BasicInfo basicInfo;

    private final Preferences preferences;

    /**
     * Construit un profil complet à partir des données des deux services.
     *
     * @param userId      identifiant de l'utilisateur
     * @param basicInfo   informations de base de l'utilisateur
     * @param preferences préférences de l'utilisateur
     */
    public CompleteUserProfile(String userId, BasicInfo basicInfo, Preferences preferences) {
        this.userId = userId;
        this.basicInfo = basicInfo;
        this.preferences = preferences;
    }

    /**
     * Renvoie l'identifiant de l'utilisateur.
     *
     * @return identifiant de l'utilisateur
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Renvoie les informations de base de l'utilisateur.
     *
     * @return informations de base de l'utilisateur
     */
    public BasicInfo getBasicInfo() {
        return basicInfo;
    }

    /**
     * Renvoie les préférences de l'utilisateur.
     *
     * @return préférences de l'utilisateur
     */
    public Preferences getPreferences() {
        return preferences;
    }

    @Override
    public String toString() {
        return "\nProfil Complet pour " + userId + ":\n"
                + "  Info de base : " + basicInfo + "\n"
                + "  Préférences : " + preferences;
    }
}
