package football;

import java.util.List;

/**
 * Classe représentant un tournoi de football.
 * Un tournoi appartient à une compétition et contient une liste de matchs.
 */
public class Tournament {

    private final String name;

    private final String competition;

    private final List<Match> matches;

    /**
     * Constructeur pour créer un nouveau tournoi.
     *
     * @param name        Le nom du tournoi.
     * @param competition Le type de compétition (ex : "League", "Cup", "Continental", "Friendly").
     * @param matches     La liste des matchs du tournoi.
     */
    public Tournament(String name, String competition, List<Match> matches) {
        this.name = name;
        this.competition = competition;
        this.matches = matches;
    }

    /**
     * Récupère le nom du tournoi.
     *
     * @return Le nom.
     */
    public String getName() {
        return name;
    }

    /**
     * Récupère le type de compétition du tournoi.
     *
     * @return Le type de compétition.
     */
    public String getCompetition() {
        return competition;
    }

    /**
     * Récupère la liste des matchs du tournoi.
     *
     * @return La liste des matchs.
     */
    public List<Match> getMatches() {
        return matches;
    }
}
