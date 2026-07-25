package be.vinci.mocks.domaine;

import java.util.Set;

public interface Stage {

    /**
     * Renvoie l'intitulé du stage
     *
     * @return L'intitulé du stage
     */
    String getIntitule();

    /**
     * Renvoie le lieu du stage
     *
     * @return Le lieu du stage
     */
    String getLieu();

    /**
     * Renvoie le numéro de la semaine durant laquelle le stage a lieu
     *
     * @return le numéro de la semaine durant laquelle le stage a lieu
     */
    int getNumeroDeSemaine();

    /**
     * Renvoie le sport concerné par le stage
     *
     * @return Le sport concerné par le stage
     */
    Sport getSport();

    /**
     * Enregistre le moniteur qui assure le stage s'il n'y a pas encore de moniteur spécifié Il faut
     * que le moniteur soit libre durant cette semaine-là (pas d'autre stage).Il faut aussi que le
     * moiteur soit compétent dans ce sport. Attention, il faut s'assurer que le moniteur ajoute le
     * stage.
     *
     * @param moniteur le moniteur qui va assurer le stage.
     * @return true si le moniteur a bien été enregistré
     * @throws IllegalArgumentException Exception lancée si l'un des paramètres n'est pas spécifié ou
     *                                  vide.
     */
    boolean enregistrerMoniteur(Moniteur moniteur);

    /**
     * Supprime le moniteur qui assure le stage.
     * <p>
     * Attention il faut s'assurer que le moniteur supprime également le stage de sa liste des
     * stages.
     *
     * @return true si le moniteur a pu être supprimé
     */
    boolean supprimerMoniteur();

    /**
     * Renvoie le moniteur qui assure le stage
     *
     * @return moniteur qui assure le stage
     */
    Moniteur getMoniteur();

    /**
     * Ajoute l'inscription d'un enfant au stage. L'enfant est ajouté seulement s'il n'était pas déjà
     * inscrit au stage.
     *
     * @param enfant l'enfant qui veut s'inscrire au stage.
     * @return true si l'enfant a pu être inscrit au stage.
     * @throws IllegalArgumentException Exception lancée si l'un des paramètres n'est pas spécifié ou
     *                                  vide.
     */
    boolean ajouterEnfant(Enfant enfant);

    /**
     * Supprime l'inscription d'un enfant au stage. L'inscription de l'enfant est supprimée seulement
     * s'il était inscrit au stage.
     *
     * @param enfant l'enfant qui veut se desinscrire au stage.
     * @return true si l'enfant a pu être desinscrit au stage.
     * @throws IllegalArgumentException Exception lancée si l'un des paramètres n'est pas spécifié ou
     *                                  vide.
     */
    boolean supprimerEnfant(Enfant enfant);

    /**
     * Vérifie si un enfant est inscrit au stage.
     *
     * @param enfant l'enfant qu'il faut tester.
     * @return true si l'enfant est inscrit au stage.
     * @throws IllegalArgumentException Exception lancée si l'un des paramètres n'est pas spécifié ou
     *                                  vide.
     */
    boolean contientEnfant(Enfant enfant);

    /**
     * Renvoie le nombe d'enfants inscrits au stage.
     *
     * @return le nombre d'enfants inscrits au stage.
     */
    int nombreDEnfants();

    /**
     * Renvoie l'ensemble des enfants inscrits au stage.
     *
     * @return le Set des enfants inscrits au stage.
     */
    Set<Enfant> enfants();
}
