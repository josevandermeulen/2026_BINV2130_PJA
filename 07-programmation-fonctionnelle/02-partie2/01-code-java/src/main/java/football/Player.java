package football;

import java.util.Objects;

/**
 * Classe représentant un joueur de football.
 * C'est l'entité de niveau le plus bas dans notre modèle.
 */
public class Player {

    private final String name;

    private final String nationality;

    /**
     * Constructeur pour créer un nouveau joueur.
     *
     * @param name        Le nom du joueur.
     * @param nationality La nationalité du joueur.
     */
    public Player(String name, String nationality) {
        this.name = name;
        this.nationality = nationality;
    }

    /**
     * Récupère le nom du joueur.
     *
     * @return Le nom.
     */
    public String getName() {
        return name;
    }

    /**
     * Récupère la nationalité du joueur.
     *
     * @return La nationalité.
     */
    public String getNationality() {
        return nationality;
    }

    // equals() et hashCode() sont implémentés correctement pour permettre la comparaison et l'utilisation dans des collections (comme Set pour distinct()).

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(name, player.name) && Objects.equals(nationality, player.nationality);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, nationality);
    }
}
