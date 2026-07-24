package football;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Permet de vérifier votre implémentation de {@link StatsService} : lancez le `main`
 * et comparez la sortie avec celle donnée dans l'énoncé.
 */
public class ExamRunner {

    private static final StatsService SERVICE = new StatsService();

    private static final List<Tournament> DATABASE = initDatabase();

    public static void main(String[] args) {
        System.out.println("--- Question 11: Tournaments with 'Cup' ---");
        SERVICE.findTournamentsWithName(DATABASE).forEach(t -> System.out.println(t.getName()));

        // Champions League est à l'index 3
        System.out.println("\n--- Question 12: Goals per match (Champions League) ---");
        SERVICE.getMatchGoals(DATABASE.get(3)).forEach(g -> System.out.println(g + " goals"));

        // Mbappe marque dans le match 1 ET le match 2 -> il doit apparaître 2 fois ici
        System.out.println("\n--- Question 13: Scorers (Champions League) ---");
        List<Player> scorersCl = SERVICE.getAllScorersInTournament(DATABASE.get(3));
        scorersCl.forEach(p -> System.out.println(p.getName() + " (" + p.getNationality() + ")"));

        // Malgré le doublon de Mbappe, on ne veut que les nationalités uniques
        System.out.println("\n--- Question 14: Distinct Nationalities ---");
        SERVICE.getDistinctNationalities(scorersCl).forEach(System.out::println);

        System.out.println("\n--- Question 15: Highest Goal Count ---");
        List<Match> testMatches = new ArrayList<>();
        testMatches.addAll(DATABASE.get(3).getMatches()); // Champions League
        testMatches.addAll(DATABASE.get(6).getMatches()); // World Cup
        Optional<Integer> max = SERVICE.findHighestGoalCount(testMatches);
        System.out.println("Max goals: " + max.orElse(0));

        System.out.println("\n--- Question 16: Count by Competition ---");
        Map<String, Long> groups = SERVICE.countTournamentsByCompetition(DATABASE);
        groups.forEach((competition, count) -> System.out.println(competition + " : " + count));
    }

    private static List<Tournament> initDatabase() {
        Player mbappe = new Player("Mbappe", "France");
        Player haaland = new Player("Haaland", "Norway");
        Player salah = new Player("Salah", "Egypt");
        Player bellingham = new Player("Bellingham", "England");

        // Match 1 : Mbappe + Haaland + Bellingham (2-1)
        Match match1 = new Match("Semifinal", 2, 1, Arrays.asList(mbappe, haaland, bellingham));
        // Match 2 : Mbappe (doublon ici !) + Salah (1-1)
        Match match2 = new Match("Final", 1, 1, Arrays.asList(mbappe, salah));
        Match matchWc = new Match("World Cup Final", 3, 2, Arrays.asList(mbappe, haaland, mbappe, haaland, salah));

        // Répartition League:4 Continental:2 Cup:3 Friendly:1
        Tournament ligue1 = new Tournament("Ligue 1", "League", new ArrayList<>());
        Tournament pl = new Tournament("Premier League", "League", new ArrayList<>());
        Tournament bundes = new Tournament("Bundesliga", "League", new ArrayList<>());
        Tournament serieA = new Tournament("Serie A", "League", new ArrayList<>());
        // Champions League (index 3)
        Tournament cl = new Tournament("Champions League", "Continental", Arrays.asList(match1, match2));
        Tournament europa = new Tournament("Europa League", "Continental", new ArrayList<>());
        // World Cup (index 6)
        Tournament wc = new Tournament("World Cup", "Cup", Arrays.asList(matchWc));
        Tournament faCup = new Tournament("FA Cup", "Cup", new ArrayList<>());
        Tournament coupe = new Tournament("French Cup", "Cup", new ArrayList<>());
        Tournament friendly = new Tournament("International Friendly", "Friendly", new ArrayList<>());

        return Arrays.asList(ligue1, pl, bundes, cl, europa, serieA, wc, faCup, coupe, friendly);
    }
}
