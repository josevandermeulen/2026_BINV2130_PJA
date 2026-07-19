package vod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Permet de vérifier votre implémentation de {@link AnalyticsService} : lancez le `main`
 * et comparez la sortie avec celle donnée dans l'énoncé.
 */
public class ExamRunner {

    private static final AnalyticsService SERVICE = new AnalyticsService();

    private static final List<TvSeries> DATABASE = initDatabase();

    public static void main(String[] args) {
        System.out.println("--- Q1: Series with 'Day' ---");
        SERVICE.findSeriesWithTitle(DATABASE).forEach(s -> System.out.println(s.getTitle()));

        // Breaking Bad est à l'index 3
        System.out.println("\n--- Q2: Durations (Breaking Bad) ---");
        SERVICE.getEpisodeDurations(DATABASE.get(3)).forEach(d -> System.out.println(d + " min"));

        // Bryan joue dans l'épisode 1 ET l'épisode 2 -> il doit apparaître 2 fois ici
        System.out.println("\n--- Q3: Actors (Breaking Bad) ---");
        List<Actor> actorsBb = SERVICE.getAllActorsInSeries(DATABASE.get(3));
        actorsBb.forEach(a -> System.out.println(a.getName() + " (" + a.getNationality() + ")"));

        // Malgré le doublon de Bryan, on ne veut que les nationalités uniques
        System.out.println("\n--- Q4: Distinct Nationalities ---");
        SERVICE.getDistinctNationalities(actorsBb).forEach(System.out::println);

        System.out.println("\n--- Q5: Longest Episode Duration ---");
        List<Episode> testEpisodes = new ArrayList<>();
        testEpisodes.addAll(DATABASE.get(3).getEpisodes());
        testEpisodes.addAll(DATABASE.get(6).getEpisodes());
        Optional<Integer> max = SERVICE.findLongestEpisodeDuration(testEpisodes);
        System.out.println("Max duration: " + max.orElse(0));

        System.out.println("\n--- Q6: Group by Genre ---");
        Map<String, Long> groups = SERVICE.countSeriesByGenre(DATABASE);
        groups.forEach((genre, count) -> System.out.println(genre + " : " + count));
    }

    private static List<TvSeries> initDatabase() {
        Actor bryan = new Actor("Bryan Cranston", "USA");
        Actor cillian = new Actor("Cillian Murphy", "Ireland");
        Actor omar = new Actor("Omar Sy", "France");
        Actor will = new Actor("Will Smith", "USA");

        // Ep 1 : Bryan + Cillian
        Episode ep1 = new Episode("Pilot", 58, Arrays.asList(bryan, cillian));
        // Ep 2 : Bryan (doublon ici !) + Omar
        Episode ep2 = new Episode("Ozymandias", 48, Arrays.asList(bryan, omar));
        Episode epMovie = new Episode("The Movie", 145, Arrays.asList(will));

        // Répartition Comedy:1 Fantasy:2 Drama:3 Sci-Fi:4
        TvSeries b99 = new TvSeries("Brooklyn Nine-Nine", "Comedy", new ArrayList<>());
        TvSeries wed = new TvSeries("Wednesday", "Fantasy", new ArrayList<>());
        TvSeries got = new TvSeries("Game of Thrones", "Fantasy", new ArrayList<>());
        // Breaking Bad (index 3)
        TvSeries bb = new TvSeries("Breaking Bad", "Drama", Arrays.asList(ep1, ep2));
        TvSeries bcs = new TvSeries("Better Call Saul", "Drama", new ArrayList<>());
        TvSeries wire = new TvSeries("The Wire", "Drama", new ArrayList<>());
        TvSeries id = new TvSeries("Independence Day", "Sci-Fi", Arrays.asList(epMovie));
        TvSeries blackM = new TvSeries("Black Mirror", "Sci-Fi", new ArrayList<>());
        TvSeries drWho = new TvSeries("Doctor Who", "Sci-Fi", new ArrayList<>());
        TvSeries expanse = new TvSeries("The Expanse", "Sci-Fi", new ArrayList<>());

        return Arrays.asList(b99, wed, got, bb, bcs, wire, id, blackM, drWho, expanse);
    }
}
