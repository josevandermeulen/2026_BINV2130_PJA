package be.vinci.domaine;

import be.vinci.validation.LongueurMin;
import be.vinci.validation.NonNul;
import be.vinci.validation.Positif;

/**
 * Adresse d'un étudiant — sert de champ objet à valider récursivement (@Valide).
 */
public class Adresse {

    @NonNul
    @LongueurMin(valeur = 2)
    private final String rue;

    @Positif
    private final int numero;

    public Adresse(String rue, int numero) {
        this.rue = rue;
        this.numero = numero;
    }

    public String getRue() {
        return rue;
    }

    public int getNumero() {
        return numero;
    }

}
