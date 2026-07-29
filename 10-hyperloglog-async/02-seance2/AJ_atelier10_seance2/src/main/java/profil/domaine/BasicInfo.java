package profil.domaine;

/**
 * Informations de base d'un utilisateur.
 */
public class BasicInfo {

    private final String name;

    private final String email;

    /**
     * Construit les informations de base d'un utilisateur.
     *
     * @param name  nom de l'utilisateur
     * @param email adresse e-mail de l'utilisateur
     */
    public BasicInfo(String name, String email) {
        this.name = name;
        this.email = email;
    }

    /**
     * Renvoie le nom de l'utilisateur.
     *
     * @return nom de l'utilisateur
     */
    public String getName() {
        return name;
    }

    /**
     * Renvoie l'adresse e-mail de l'utilisateur.
     *
     * @return adresse e-mail de l'utilisateur
     */
    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return name + " (" + email + ")";
    }
}
