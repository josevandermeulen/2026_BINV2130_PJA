package be.vinci.domaine;

import be.vinci.validation.LongueurMin;
import be.vinci.validation.NonNul;
import be.vinci.validation.Positif;
import be.vinci.validation.Valide;

/**
 * Un étudiant dont les contraintes sont déclarées par annotations sur les
 * champs — c'est le Validateur qui les fait respecter, pas le constructeur.
 */
/**
 * Un étudiant, dont les contraintes de validité sont portées par des annotations sur ses champs
 * plutôt que par du code de contrôle.
 * <p>
 * C'est le {@link be.vinci.validation.Validateur} qui, par introspection, lit ces annotations et
 * en tire les violations : la classe elle-même ne valide rien.
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

    /**
     * Crée un étudiant, sans vérifier ses contraintes — c'est le rôle du validateur.
     *
     * @param nom             le nom de l'étudiant
     * @param matricule       son matricule
     * @param nombreDeCredits le nombre de crédits acquis
     * @param adresse         son adresse
     */
    public Etudiant(String nom, String matricule, int nombreDeCredits, Adresse adresse) {
        this.nom = nom;
        this.matricule = matricule;
        this.nombreDeCredits = nombreDeCredits;
        this.adresse = adresse;
    }

    /**
     * Renvoie le nom de l'étudiant.
     *
     * @return le nom de l'étudiant
     */
    public String getNom() {
        return nom;
    }

    /**
     * Renvoie le matricule de l'étudiant.
     *
     * @return le matricule
     */
    public String getMatricule() {
        return matricule;
    }

    /**
     * Renvoie le nombre de crédits acquis.
     *
     * @return le nombre de crédits
     */
    public int getNombreDeCredits() {
        return nombreDeCredits;
    }

    /**
     * Renvoie l'adresse de l'étudiant.
     *
     * @return l'adresse de l'étudiant
     */
    public Adresse getAdresse() {
        return adresse;
    }

}
