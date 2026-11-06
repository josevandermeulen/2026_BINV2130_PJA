package vod;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Module de statistiques destiné à une plateforme de VOD.
 */
public class AnalyticsService {

    // Question 3 : findSeriesWithTitle
    /**
     * Récupère toutes les séries dont le titre contient le mot "Day" (sensible à la casse).
     *
     * @param database La liste complète des séries.
     * @return Une liste filtrée de séries.
     */
    public List<TvSeries> findSeriesWithTitle(List<TvSeries> database) {
        return database.stream()
                .filter(series -> series.getTitle().contains("Day"))
                .toList();
    }

    // Question 4 : getEpisodeDurations
    /**
     * À partir d'une série, retourne la liste des durées de chaque épisode.
     *
     * @param series La série à analyser.
     * @return Une liste d'entiers (minutes).
     */
    public List<Integer> getEpisodeDurations(TvSeries series) {
        return series.getEpisodes().stream()
                .map(Episode::getDuration)
                .toList();
    }

    // Question 5 : getAllActorsInSeries
    /**
     * Récupère la liste de tous les acteurs ayant joué dans une série donnée.
     * Note : les doublons sont autorisés (si un acteur joue dans 2 épisodes, il apparaît 2 fois).
     *
     * @param series La série source.
     * @return La liste complète des acteurs.
     */
    public List<Actor> getAllActorsInSeries(TvSeries series) {
        return series.getEpisodes().stream()
                .flatMap(episode -> episode.getCast().stream())
                .toList();
    }

    // Question 6 : getDistinctNationalities
    /**
     * À partir d'une liste d'acteurs, récupère la liste des nationalités présentes.
     * La liste retournée ne doit pas contenir de doublons.
     *
     * @param actors Une liste d'acteurs.
     * @return La liste des nationalités uniques.
     */
    public List<String> getDistinctNationalities(List<Actor> actors) {
        return actors.stream()
                .map(Actor::getNationality)
                .distinct()
                .toList();
    }

    // Question 7 : findLongestEpisodeDuration
    /**
     * Trouve la durée (en minutes) de l'épisode le plus long de la liste fournie.
     *
     * @param episodes Une liste plate d'épisodes.
     * @return Un `Optional` contenant la durée maximale (ou vide si la liste est vide).
     */
    public Optional<Integer> findLongestEpisodeDuration(List<Episode> episodes) {
        return episodes.stream()
                .map(Episode::getDuration)
                .max(Integer::compareTo);
    }

    // Question 8 : countSeriesByGenre
    /**
     * Groupe les séries par leur genre et compte le nombre de séries par genre.
     *
     * @param database La liste complète des séries.
     * @return Une `Map` où la clé est le genre et la valeur le nombre de séries de ce genre.
     */
    public Map<String, Long> countSeriesByGenre(List<TvSeries> database) {
        return database.stream()
                .collect(Collectors.groupingBy(TvSeries::getGenre, Collectors.counting()));
    }
}
