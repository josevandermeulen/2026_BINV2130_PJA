package domaine;

public class Acces {

    private final String horodatage;

    private final String utilisateur;

    private final String ip;

    public Acces(String horodatage, String utilisateur, String ip) {
        // TODO Question 3 : valider les paramètres via Util avant de les affecter
        this.horodatage = horodatage;
        this.utilisateur = utilisateur;
        this.ip = ip;
    }

    public String getHorodatage() {
        return horodatage;
    }

    public String getUtilisateur() {
        return utilisateur;
    }

    public String getIp() {
        return ip;
    }

    @Override
    public String toString() {
        return horodatage + " : " + utilisateur + " (" + ip + ")";
    }

}
