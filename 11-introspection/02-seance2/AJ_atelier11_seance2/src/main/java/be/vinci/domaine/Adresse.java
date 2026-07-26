package be.vinci.domaine;

import be.vinci.validation.LongueurMin;
import be.vinci.validation.NonNul;
import be.vinci.validation.Positif;

/**
 * L'adresse d'un étudiant, validée par annotations comme le reste du domaine.
 *
 * Elle sert aussi de champ objet à valider récursivement, via @Valide sur le champ qui la porte.
 */
public class Adresse {

    @NonNul
    @LongueurMin(valeur = 2)
    private final String rue;

    @Positif
    private final int numero;

    /**
     * Crée une adresse.
     *
     * @param rue    le nom de la rue
     * @param numero le numéro dans la rue
     */
    public Adresse(String rue, int numero) {
        this.rue = rue;
        this.numero = numero;
    }

    /**
     * Renvoie le nom de la rue.
     *
     * @return le nom de la rue
     */
    public String getRue() {
        return rue;
    }

    /**
     * Renvoie le numéro dans la rue.
     *
     * @return le numéro
     */
    public int getNumero() {
        return numero;
    }

}
