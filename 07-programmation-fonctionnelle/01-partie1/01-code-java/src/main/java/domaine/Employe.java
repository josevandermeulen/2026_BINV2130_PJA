package domaine;

/**
 * Représente un employé : son genre, sa taille et son nom.
 */
public class Employe {

    /**
     * Le genre de l'employé
     */
    private Genre genre;

    /**
     * La taille de l'employé, en centimètres
     */
    private int taille;

    /**
     * Le nom de l'employé
     */
    private String nom;

    /**
     * Crée un employé.
     *
     * @param genre le genre de l'employé.
     * @param taille la taille de l'employé, en centimètres.
     * @param nom le nom de l'employé.
     */
    public Employe(Genre genre, int taille, String nom) {
        this.genre = genre;
        this.taille = taille;
        this.nom = nom;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public int getTaille() {
        return taille;
    }

    public void setTaille(int taille) {
        this.taille = taille;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "Employe{" +
                "genre=" + genre +
                ", taille=" + taille +
                ", nom='" + nom + '\'' +
                '}';
    }
}
