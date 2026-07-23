package domaine;

import java.util.Objects;

/**
 * Représente un Trader.
 *
 * Simple structure de donnée formée d'un nom et d'une ville.
 * Un Trader est un objet immutable.
 *
 * @author systho
 */
public class Trader {

    /**
     * Le nom du trader
     **/
    private String name;

    /**
     * Le nom de la ville de travail du trader
     */
    private String city;

    /**
     * Crée un Trader.
     *
     * @param name le nom du trader.
     * @param city la ville du trader.
     */
    public Trader(String name, String city) {
        this.name = name;
        this.city = city;
    }

    /**
     * @return le nom du trader
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return le nom de la ville de travail du trader
     */
    public String getCity() {
        return this.city;
    }

    /**
     * Deux traders sont considérés comme égaux s'ils ont le même nom et la même ville.
     *
     * @param o l'objet à comparer
     * @return true si o est un Trader de même nom et de même ville
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Trader)) {
            return false;
        }

        Trader autre = (Trader) o;
        return Objects.equals(name, autre.name) && Objects.equals(city, autre.city);
    }

    /**
     * @return un code de hachage cohérent avec equals (basé sur le nom et la ville)
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, city);
    }

    /**
     * @return une chaine de caractère comprenant l'identification du trader (nom et ville)
     */
    @Override
    public String toString() {
        return "Trader:" + this.name + " in " + this.city;
    }
}
