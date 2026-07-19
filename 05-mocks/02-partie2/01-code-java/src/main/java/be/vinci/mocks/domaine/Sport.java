package be.vinci.mocks.domaine;

import java.util.Set;

public interface Sport {

    /**
     * renvoie l'intitulé du sport
     *
     * @return L'intitulé du sport
     */

    String getIntitule();

    /**
     * ajoute un moniteur à l'ensemble des moniteurs compétents dans le sport
     *
     * @param moniteur Le moniteur à ajouter à ceux compétents dans le sport.
     * @return true si le moniteur a été ajouté
     * @throws IllegalArgumentException Exception lancée si l'un des paramètres n'est pas spécifié ou
     *                                  vide.
     */
    boolean ajouterMoniteur(Moniteur moniteur);

    /**
     * supprime le moniteur de l'ensemble des moniteurs compétents dans ce sport
     *
     * @param moniteur le moniteur à supprimer de la liste des compétents dans ce sport.
     * @return true si le moniteur a bien été supprimé
     * @throws IllegalArgumentException Exception lancée si l'un des paramètres n'est pas spécifié ou
     *                                  vide.
     */
    boolean supprimerMoniteur(Moniteur moniteur);

    /**
     * vérifie si le moniteur est compétents dans ce sport.
     *
     * @param moniteur le moniteur à tester.
     * @return true si le moniteur est compétent.
     * @throws IllegalArgumentException Exception lancée si l'un des paramètres n'est pas spécifié ou
     *                                  vide.
     */
    boolean contientMoniteur(Moniteur moniteur);

    /**
     * renvoie les moniteurs compétents dans le sport
     *
     * @return Les moniteurs compétents dans le sport
     */
    Set<Moniteur> moniteurs();
}
