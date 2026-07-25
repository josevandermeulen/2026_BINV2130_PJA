package domaine;

import util.Util;

/**
 * La classe Enfant représente un enfant. Elle connaît son nom et son numéro
 * de téléphone.
 * Cette classe est immuable.
 */
public class Enfant {
    /**
     * Le nom de l'enfant
     */
    private String nom;
    /**
     * Le téléphone de l'enfant
     */
    private String telephone;

    /**
     * Crée un enfant
     *
     * @param nom       Le nom de l'enfant
     * @param telephone Le téléphone de l'enfant
     * @exception IllegalArgumentException Exception lancée si l'un des paramètres
     *                                     n'est pas spécifié ou vide.
     *
     */
    public Enfant(String nom, String telephone) {
        Util.checkString(nom);
        Util.checkString(telephone);
        this.nom = nom;
        this.telephone = telephone;
    }

    /**
     * renvoie le nom de l'enfant
     */
    public String getNom() {
        return nom;
    }

    /**
     * renvoie le téléphone de l'enfant
     */
    public String getTelephone() {
        return telephone;
    }
}
