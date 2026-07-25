package domaine;

/**
 * Une ligne de log analysée : qui s'est connecté, quand et depuis quelle adresse IP.
 * <p>
 * Les trois champs restent des chaînes brutes, telles que lues dans le fichier — l'horodatage
 * n'est pas converti en date, l'IP n'est pas validée à la construction.
 */
public class Acces {

    private final String horodatage;

    private final String utilisateur;

    private final String ip;

    /**
     * Crée un accès.
     *
     * @param horodatage l'horodatage brut de la ligne de log
     * @param utilisateur le nom de l'utilisateur connecté
     * @param ip l'adresse IP d'origine, telle qu'écrite dans le log
     * @throws IllegalArgumentException si l'un des paramètres est null ou vide
     */
    public Acces(String horodatage, String utilisateur, String ip) {
        // TODO Question 3 : valider les paramètres via Util avant de les affecter
        this.horodatage = horodatage;
        this.utilisateur = utilisateur;
        this.ip = ip;
    }

    /**
     * Renvoie l'horodatage de l'accès.
     *
     * @return l'horodatage brut
     */
    public String getHorodatage() {
        return horodatage;
    }

    /**
     * Renvoie l'utilisateur qui s'est connecté.
     *
     * @return le nom de l'utilisateur
     */
    public String getUtilisateur() {
        return utilisateur;
    }

    /**
     * Renvoie l'adresse IP d'origine.
     *
     * @return l'adresse IP, telle qu'écrite dans le log
     */
    public String getIp() {
        return ip;
    }

    /**
     * Renvoie l'horodatage, l'utilisateur et l'IP de l'accès.
     *
     * @return la représentation textuelle de l'accès
     */
    @Override
    public String toString() {
        return horodatage + " : " + utilisateur + " (" + ip + ")";
    }

}
