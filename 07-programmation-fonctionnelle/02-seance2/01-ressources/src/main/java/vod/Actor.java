package vod;

import java.util.Objects;

/**
 * Classe représentant un acteur.
 * C'est l'entité de niveau le plus bas dans notre modèle.
 */
public class Actor {

    private final String name;

    private final String nationality;

    /**
     * Constructeur pour créer un nouvel acteur.
     *
     * @param name        Le nom de l'acteur.
     * @param nationality La nationalité de l'acteur.
     */
    public Actor(String name, String nationality) {
        this.name = name;
        this.nationality = nationality;
    }

    /**
     * Récupère le nom de l'acteur.
     *
     * @return Le nom.
     */
    public String getName() {
        return name;
    }

    /**
     * Récupère la nationalité de l'acteur.
     *
     * @return La nationalité.
     */
    public String getNationality() {
        return nationality;
    }

    // equals() et hashCode() sont implémentés correctement pour permettre la comparaison
    // et l'utilisation dans des collections (comme Set pour distinct()).

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Actor actor = (Actor) o;
        return Objects.equals(name, actor.name) && Objects.equals(nationality, actor.nationality);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, nationality);
    }
}
