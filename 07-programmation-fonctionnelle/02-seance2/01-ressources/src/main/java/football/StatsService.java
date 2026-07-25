package football;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Module de statistiques destiné à une plateforme d'analyse de tournois de football.
 * Implémentez les six méthodes ci-dessous en utilisant exclusivement l'API Stream (aucune boucle).
 */
public class StatsService {

    /**
     * Récupère tous les tournois dont le nom contient le mot "Cup" (sensible à la casse).
     *
     * @param database La liste complète des tournois.
     * @return Une liste filtrée de tournois.
     */
    public List<Tournament> findTournamentsWithName(List<Tournament> database) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * À partir d'un tournoi, retourne la liste du nombre total de buts de chaque match
     * (`goalsHome` + `goalsAway`).
     *
     * @param tournament Le tournoi à analyser.
     * @return Une liste d'entiers (nombre total de buts par match).
     */
    public List<Integer> getMatchGoals(Tournament tournament) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Récupère la liste de tous les buteurs ayant joué dans un tournoi donné.
     * Note : les doublons sont autorisés (si un joueur a marqué dans 2 matchs, il apparaît 2 fois).
     *
     * @param tournament Le tournoi source.
     * @return La liste complète des buteurs (avec doublons éventuels).
     */
    public List<Player> getAllScorersInTournament(Tournament tournament) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * À partir d'une liste de joueurs, récupère la liste des nationalités présentes.
     * La liste retournée ne doit pas contenir de doublons.
     *
     * @param players Une liste de joueurs.
     * @return La liste des nationalités uniques (dans l'ordre d'apparition).
     */
    public List<String> getDistinctNationalities(List<Player> players) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Trouve le nombre total de buts (`goalsHome` + `goalsAway`) du match le plus prolifique.
     *
     * @param matches Une liste plate de matchs.
     * @return Un `Optional` contenant le nombre de buts maximal (ou vide si la liste est vide).
     */
    public Optional<Integer> findHighestGoalCount(List<Match> matches) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Groupe les tournois par leur type de compétition et compte le nombre de tournois par type.
     *
     * @param database La liste complète des tournois.
     * @return Une `Map` où la clé est le type de compétition et la valeur le nombre de tournois de ce type.
     */
    public Map<String, Long> countTournamentsByCompetition(List<Tournament> database) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
