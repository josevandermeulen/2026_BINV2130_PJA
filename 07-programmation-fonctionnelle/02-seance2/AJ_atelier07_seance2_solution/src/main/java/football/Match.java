package football;

import java.util.List;

/**
 * Classe représentant un match de football.
 * Un match contient les buts de chaque équipe et la liste des buteurs.
 */
public class Match {

    private final String name;

    private final int goalsHome;

    private final int goalsAway;

    private final List<Player> scorers;

    /**
     * Constructeur pour créer un nouveau match.
     *
     * @param name      Le nom du match.
     * @param goalsHome Le nombre de buts de l'équipe à domicile.
     * @param goalsAway Le nombre de buts de l'équipe à l'extérieur.
     * @param scorers   La liste des buteurs du match.
     */
    public Match(String name, int goalsHome, int goalsAway, List<Player> scorers) {
        this.name = name;
        this.goalsHome = goalsHome;
        this.goalsAway = goalsAway;
        this.scorers = scorers;
    }

    /**
     * Récupère le nom du match.
     *
     * @return Le nom.
     */
    public String getName() {
        return name;
    }

    /**
     * Récupère le nombre de buts de l'équipe à domicile.
     *
     * @return Le nombre de buts à domicile.
     */
    public int getGoalsHome() {
        return goalsHome;
    }

    /**
     * Récupère le nombre de buts de l'équipe à l'extérieur.
     *
     * @return Le nombre de buts à l'extérieur.
     */
    public int getGoalsAway() {
        return goalsAway;
    }

    /**
     * Récupère la liste des buteurs du match.
     *
     * @return La liste des buteurs.
     */
    public List<Player> getScorers() {
        return scorers;
    }
}
