package be.vinci.mocks.domaine;

import be.vinci.mocks.util.Util;

/**
 * La classe domaine.Enfant représente un enfant. Elle connaît son nom et son numéro
 * de téléphone.
 * Cette classe est immuable.
 */
public class EnfantImpl implements Enfant {
    /**
     * Le nom de l'enfant
     */
    private final String nom;
    /**
     * Le téléphone de l'enfant
     */
    private final String telephone;

    /**
     * Crée un enfant
     *
     * @param nom       Le nom de l'enfant
     * @param telephone Le téléphone de l'enfant
     * @exception IllegalArgumentException Exception lancée si l'un des paramètres
     *                                     n'est pas spécifié ou vide.
     *
     */
    public EnfantImpl(String nom, String telephone) {
        Util.checkString(nom);
        Util.checkString(telephone);
        this.nom = nom;
        this.telephone = telephone;
    }

    /**
     * renvoie le nom de l'enfant
     */
    @Override
    public String getNom() {
        return nom;
    }

    /**
     * renvoie le téléphone de l'enfant
     */
    @Override
    public String getTelephone() {
        return telephone;
    }
}
