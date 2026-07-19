package domaine;

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
     * @return une chaine de caractère comprenant l'identification du trader (nom et ville)
     */
    public String toString() {
        return "Trader:" + this.name + " in " + this.city;
    }
}
