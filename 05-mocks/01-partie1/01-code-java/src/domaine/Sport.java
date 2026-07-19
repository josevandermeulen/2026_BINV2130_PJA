package domaine;

import util.Util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * La classe Sport représente un sport. Elle connaît son intitulé
 * et les moniteurs compétents dans ce sport.
 */
public class Sport {
    /**
     * L'intitulé du sport
     */
    private String intitule;
    /**
     *
     * Les moniteurs compétents dans la discipline sportive.
     */
    private Set<Moniteur> moniteurs;

    /**
     * Crée un sport
     *
     * @param intitule L'intitulé du sport
     * @exception IllegalArgumentException Exception lancée si l'un des paramètres
     *                                     n'est pas spécifié ou vide.
     */
    public Sport(String intitule) {
        Util.checkString(intitule);
        this.intitule = intitule;
        this.moniteurs = new HashSet<>();
    }

    /**
     * renvoie l'intitulé du sport
     *
     * @return L'intitulé du sport
     */

    public String getIntitule() {
        return intitule;
    }

    /**
     * ajoute un moniteur à l'ensemble des moniteurs compétents dans le sport
     *
     * @param moniteur Le moniteur à ajouter à ceux compétents dans le sport.
     * @return true si le moniteur a été ajouté
     * @exception IllegalArgumentException Exception lancée si l'un des paramètres
     *                                     n'est pas spécifié ou vide.
     */
    public boolean ajouterMoniteur(Moniteur moniteur) {
        if (this.contientMoniteur(moniteur))
            return false;
        moniteurs.add(moniteur);
        return true;
    }

    /**
     * supprime le moniteur de l'ensemble des moniteurs compétents dans ce sport
     *
     * @return true si le moniteur a bien été supprimé
     * @param moniteur le moniteur à supprimer de la liste des compétents dans ce sport.
     * @exception IllegalArgumentException Exception lancée si l'un des paramètres
     *                                     n'est pas spécifié ou vide.
     */
    public boolean supprimerMoniteur(Moniteur moniteur) {
        if (!this.contientMoniteur(moniteur))
            return false;
        moniteurs.remove(moniteur);
        return true;
    }

    /**
     * vérifie si le moniteur est compétents dans ce sport.
     *
     * @return true si le moniteur est compétent.
     * @param moniteur le moniteur à tester.
     * @exception IllegalArgumentException Exception lancée si l'un des paramètres
     *                                     n'est pas spécifié ou vide.
     */
    public boolean contientMoniteur(Moniteur moniteur) {
        Util.checkObject(moniteur);
        return this.moniteurs.contains(moniteur);
    }

    /**
     * renvoie les moniteurs compétents dans le sport
     *
     * @return Les moniteurs compétents dans le sport
     */
    public Set<Moniteur> moniteurs() {
        return Collections.unmodifiableSet(moniteurs);
    }

}
