package vod;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Module de statistiques destiné à une plateforme de VOD.
 * Implémentez les six méthodes ci-dessous en utilisant exclusivement l'API Stream (aucune boucle).
 */
public class AnalyticsService {

    /**
     * Récupère toutes les séries dont le titre contient le mot "Day" (sensible à la casse).
     *
     * @param database La liste complète des séries.
     * @return Une liste filtrée de séries.
     */
    public List<TvSeries> findSeriesWithTitle(List<TvSeries> database) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * À partir d'une série, retourne la liste des durées de chaque épisode.
     *
     * @param series La série à analyser.
     * @return Une liste d'entiers (minutes).
     */
    public List<Integer> getEpisodeDurations(TvSeries series) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Récupère la liste de tous les acteurs ayant joué dans une série donnée.
     * Note : les doublons sont autorisés (si un acteur joue dans 2 épisodes, il apparaît 2 fois).
     *
     * @param series La série source.
     * @return La liste complète des acteurs.
     */
    public List<Actor> getAllActorsInSeries(TvSeries series) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * À partir d'une liste d'acteurs, récupère la liste des nationalités présentes.
     * La liste retournée ne doit pas contenir de doublons.
     *
     * @param actors Une liste d'acteurs.
     * @return La liste des nationalités uniques.
     */
    public List<String> getDistinctNationalities(List<Actor> actors) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Trouve la durée (en minutes) de l'épisode le plus long de la liste fournie.
     *
     * @param episodes Une liste plate d'épisodes.
     * @return Un `Optional` contenant la durée maximale (ou vide si la liste est vide).
     */
    public Optional<Integer> findLongestEpisodeDuration(List<Episode> episodes) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Groupe les séries par leur genre et compte le nombre de séries par genre.
     *
     * @param database La liste complète des séries.
     * @return Une `Map` où la clé est le genre et la valeur le nombre de séries de ce genre.
     */
    public Map<String, Long> countSeriesByGenre(List<TvSeries> database) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
