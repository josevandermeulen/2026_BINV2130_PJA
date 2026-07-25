package domaine;

/**
 * Représente un employé.
 *
 * Simple structure de donnée formée d'un genre, d'une taille et d'un nom.
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

    /**
     * Renvoie le genre de l'employé.
     *
     * @return le genre de l'employé
     */
    public Genre getGenre() {
        return genre;
    }

    /**
     * Modifie le genre de l'employé.
     *
     * @param genre le nouveau genre
     */
    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    /**
     * Renvoie la taille de l'employé.
     *
     * @return la taille de l'employé, en centimètres
     */
    public int getTaille() {
        return taille;
    }

    /**
     * Modifie la taille de l'employé.
     *
     * @param taille la nouvelle taille, en centimètres
     */
    public void setTaille(int taille) {
        this.taille = taille;
    }

    /**
     * Renvoie le nom de l'employé.
     *
     * @return le nom de l'employé
     */
    public String getNom() {
        return nom;
    }

    /**
     * Modifie le nom de l'employé.
     *
     * @param nom le nouveau nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Renvoie le genre, la taille et le nom de l'employé.
     *
     * @return la représentation textuelle de l'employé
     */
    @Override
    public String toString() {
        return "Employe{" +
                "genre=" + genre +
                ", taille=" + taille +
                ", nom='" + nom + '\'' +
                '}';
    }
}
