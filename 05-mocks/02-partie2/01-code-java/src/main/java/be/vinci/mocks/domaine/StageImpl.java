package be.vinci.mocks.domaine;

import be.vinci.mocks.util.Util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * La classe domaine.Stage représente un stage. Elle connaît son intitulé,
 * le lieu de déroulement du stage, le numéro de semaine du stage,
 * son moniteur et les enfants qui y sont inscrits
 */
public class StageImpl implements Stage {
    /**
     * L'intitulé du stage
     */
    private final String intitule;
    /**
     * Le lieu du stage
     */
    private final String lieu;
    /**
     * Le numéro de la semaine du stage (entre 1 et 8)
     */
    private final int numeroDeSemaine;
    /**
     * Le sport concerné par le stage.
     */
    private final Sport sport;
    /**
     * Le moniteur qui assure le stage
     */
    private Moniteur moniteur;
    /**
     *
     * Les enfants inscrits au stage.
     */
    private final Set<Enfant> inscrits = new HashSet<>();

    /**
     * Crée un stage
     *
     * @param intitule        L'intitulé du stage
     * @param lieu            Le lieu du stage
     * @param numeroDeSemaine Le numéro de la semaine durant laquelle a lieu le
     *                        stage (entre 1 et 8)
     * @param sport           Le sport concerné par le stage
     * @exception IllegalArgumentException Exception lancée si l'un des paramètres
     *                                     n'est pas spécifié ou vide.
     */
    public StageImpl(String intitule, String lieu, int numeroDeSemaine, Sport sport) {
        Util.checkString(intitule);
        Util.checkString(lieu);
        if (numeroDeSemaine < 1 || numeroDeSemaine > 8) {
            throw new IllegalArgumentException();
        }
        Util.checkObject(sport);
        this.intitule = intitule;
        this.lieu = lieu;
        this.numeroDeSemaine = numeroDeSemaine;
        this.sport = sport;
    }

    /**
     * Renvoie l'intitulé du stage
     *
     * @return L'intitulé du stage
     */
    @Override
    public String getIntitule() {
        return intitule;
    }

    /**
     * Renvoie le lieu du stage
     *
     * @return Le lieu du stage
     */
    @Override
    public String getLieu() {
        return lieu;
    }

    /**
     * Renvoie le numéro de la semaine durant laquelle le stage a lieu
     *
     * @return le numéro de la semaine durant laquelle le stage a lieu
     */
    @Override
    public int getNumeroDeSemaine() {
        return numeroDeSemaine;
    }

    /**
     * Renvoie le sport concerné par le stage
     *
     * @return Le sport concerné par le stage
     */
    @Override
    public Sport getSport() {
        return sport;
    }

    /**
     * Enregistre le moniteur qui assure le stage s'il n'y a pas encore de moniteur
     * spécifié Il faut que le moniteur soit libre durant cette semaine-là (pas
     * d'autre stage).Il faut aussi que le moiteur soit compétent dans ce sport.
     * Attention, il faut s'assurer que le moniteur ajoute le stage.
     *
     * @return true si le moniteur a bien été enregistré
     * @param moniteur le moniteur qui va assurer le stage.
     * @exception IllegalArgumentException Exception lancée si l'un des paramètres
     *                                     n'est pas spécifié ou vide.
     *
     */
    @Override
    public boolean enregistrerMoniteur(Moniteur moniteur) {
        Util.checkObject(moniteur);
        if (this.moniteur != null)
            return false;
        if (!moniteur.estLibre(numeroDeSemaine) && !moniteur.contientStage(this))
            return false;
        if(!getSport().contientMoniteur(moniteur))
            return false;
        this.moniteur = moniteur;
        moniteur.ajouterStage(this);
        return true;
    }

    /**
     * Supprime le moniteur qui assure le stage.
     *
     * Attention il faut s'assurer que le moniteur supprime également le stage
     * de sa liste des stages.
     *
     * @return true si le moniteur a pu être supprimé
     *
     */
    @Override
    public boolean supprimerMoniteur() {
        if (moniteur == null)
            return false;
        Moniteur ex = this.moniteur;
        this.moniteur = null;
        ex.supprimerStage(this);
        return true;
    }

    /**
     * Renvoie le moniteur qui assure le stage
     *
     * @return moniteur qui assure le stage
     */
    @Override
    public Moniteur getMoniteur() {
        return moniteur;
    }

    /**
     * Ajoute l'inscription d'un enfant au stage. L'enfant est ajouté seulement s'il
     * n'était pas déjà inscrit au stage.
     *
     * @return true si l'enfant a pu être inscrit au stage.
     * @param enfant l'enfant qui veut s'inscrire au stage.
     * @exception IllegalArgumentException Exception lancée si l'un des paramètres
     *                                     n'est pas spécifié ou vide.
     */
    @Override
    public boolean ajouterEnfant(Enfant enfant) {
        if (contientEnfant(enfant))
            return false;
        inscrits.add(enfant);
        return true;
    }

    /**
     * Supprime l'inscription d'un enfant au stage. L'inscription de l'enfant est
     * supprimée seulement s'il était inscrit au stage.
     *
     * @return true si l'enfant a pu être desinscrit au stage.
     * @param enfant l'enfant qui veut se desinscrire au stage.
     * @exception IllegalArgumentException Exception lancée si l'un des paramètres
     *                                     n'est pas spécifié ou vide.
     */
    @Override
    public boolean supprimerEnfant(Enfant enfant) {
        Util.checkObject(enfant);
        return inscrits.remove(enfant);
    }

    /**
     * Vérifie si un enfant est inscrit au stage.
     *
     * @return true si l'enfant est inscrit au stage.
     * @param enfant l'enfant qu'il faut tester.
     * @exception IllegalArgumentException Exception lancée si l'un des paramètres
     *                                     n'est pas spécifié ou vide.
     */
    @Override
    public boolean contientEnfant(Enfant enfant) {
        Util.checkObject(enfant);
        return  inscrits.contains(enfant);
    }

    /**
     * Renvoie le nombe d'enfants inscrits au stage.
     *
     * @return le nombre d'enfants inscrits au stage.
     */
    @Override
    public int nombreDEnfants() {
        return inscrits.size();
    }

    /**
     * Renvoie l'ensemble des enfants inscrits au stage.
     *
     * @return le Set des enfants inscrits au stage.
     */
    @Override
    public Set<Enfant> enfants() {
        return Collections.unmodifiableSet(inscrits);
    }

}
