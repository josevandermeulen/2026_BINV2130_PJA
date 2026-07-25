package be.vinci.domaine;

import be.vinci.validation.LongueurMin;
import be.vinci.validation.NonNul;
import be.vinci.validation.Positif;
import be.vinci.validation.Valide;

/**
 * Un étudiant dont les contraintes sont déclarées par annotations sur les
 * champs — c'est le Validateur qui les fait respecter, pas le constructeur.
 */
public class Etudiant {

    @NonNul
    @LongueurMin(valeur = 3)
    private final String nom;

    @NonNul
    private final String matricule;

    @Positif
    private final int nombreDeCredits;

    @NonNul
    @Valide
    private final Adresse adresse;

    public Etudiant(String nom, String matricule, int nombreDeCredits, Adresse adresse) {
        this.nom = nom;
        this.matricule = matricule;
        this.nombreDeCredits = nombreDeCredits;
        this.adresse = adresse;
    }

    public String getNom() {
        return nom;
    }

    public String getMatricule() {
        return matricule;
    }

    public int getNombreDeCredits() {
        return nombreDeCredits;
    }

    public Adresse getAdresse() {
        return adresse;
    }

}
