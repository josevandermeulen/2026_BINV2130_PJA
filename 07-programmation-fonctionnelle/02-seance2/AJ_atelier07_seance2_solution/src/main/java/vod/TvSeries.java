package vod;

import java.util.List;

/**
 * Classe représentant une série télévisée.
 * Une série possède un genre et contient une liste d'épisodes.
 */
public class TvSeries {

    private final String title;

    private final String genre; // Ex: "Comedy", "Drama", "Sci-Fi"

    private final List<Episode> episodes;

    /**
     * Constructeur pour créer une nouvelle série TV.
     *
     * @param title    Le titre de la série.
     * @param genre    Le genre de la série (ex: "Comedy", "Drama", "Sci-Fi").
     * @param episodes La liste des épisodes de la série.
     */
    public TvSeries(String title, String genre, List<Episode> episodes) {
        this.title = title;
        this.genre = genre;
        this.episodes = episodes;
    }

    /**
     * Récupère le titre de la série.
     *
     * @return Le titre.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Récupère le genre de la série.
     *
     * @return Le genre.
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Récupère la liste des épisodes de la série.
     *
     * @return La liste des épisodes.
     */
    public List<Episode> getEpisodes() {
        return episodes;
    }
}
