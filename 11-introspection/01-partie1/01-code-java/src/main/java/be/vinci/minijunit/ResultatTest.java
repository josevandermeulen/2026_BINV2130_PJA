package be.vinci.minijunit;

/**
 * Résultat de l'exécution d'une méthode de test : son nom, le libellé à
 * afficher dans le rapport (le nom de la méthode par défaut, ou la valeur
 * de @Affichage), son statut et, en cas d'échec ou d'erreur, le message
 * de l'exception.
 */
public class ResultatTest {

    public enum Statut {
        REUSSITE, ECHEC, ERREUR, IGNORE
    }

    private final String nomMethode;

    private final String libelle;

    private final Statut statut;

    private final String message;

    public ResultatTest(String nomMethode, Statut statut, String message) {
        this(nomMethode, nomMethode, statut, message);
    }

    public ResultatTest(String nomMethode, String libelle, Statut statut, String message) {
        this.nomMethode = nomMethode;
        this.libelle = libelle;
        this.statut = statut;
        this.message = message;
    }

    public String getNomMethode() {
        return nomMethode;
    }

    public String getLibelle() {
        return libelle;
    }

    public Statut getStatut() {
        return statut;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        String base = libelle + " : " + statut;
        return message == null ? base : base + " (" + message + ")";
    }

}
