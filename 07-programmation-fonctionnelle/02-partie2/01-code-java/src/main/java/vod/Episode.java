package vod;

import java.util.List;

/**
 * Classe représentant un épisode d'une série TV.
 * Un épisode contient une liste d'acteurs (le casting).
 */
public class Episode {

    private final String title;

    private final int duration;

    private final List<Actor> cast;

    /**
     * Constructeur pour créer un nouvel épisode.
     *
     * @param title    Le titre de l'épisode.
     * @param duration La durée de l'épisode en minutes.
     * @param cast     La liste des acteurs jouant dans cet épisode.
     */
    public Episode(String title, int duration, List<Actor> cast) {
        this.title = title;
        this.duration = duration;
        this.cast = cast;
    }

    /**
     * Récupère le titre de l'épisode.
     *
     * @return Le titre.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Récupère la durée de l'épisode.
     *
     * @return La durée en minutes.
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Récupère la liste des acteurs de l'épisode.
     *
     * @return La liste des acteurs (casting).
     */
    public List<Actor> getCast() {
        return cast;
    }
}
