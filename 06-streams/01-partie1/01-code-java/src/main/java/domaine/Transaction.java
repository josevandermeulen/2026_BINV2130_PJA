package domaine;

/**
 * Représente une transaction financière.
 * <p>
 * Simple structure de donnée liant un Trader, une année et une valeur.
 * Une transaction est un objet immutable.
 */
public class Transaction {

    /**
     * référence vers le Trader ayant réalisé la transaction
     */
    private Trader trader;
    /**
     * année de réalisation de la transaction.
     */
    private int year;
    /**
     * valeur numérique de la transaction
     */
    private int value;

    /**
     * Crée une nouvelle transaction.
     *
     * @param trader référence du Trader
     * @param year   année de la transaction
     * @param value  valeur de la transaction
     */
    public Transaction(Trader trader, int year, int value) {
        this.trader = trader;
        this.year = year;
        this.value = value;
    }

    /**
     * @return la référence du Trader reçue par le constructeur.
     */
    public Trader getTrader() {
        return this.trader;
    }

    /**
     * @return l'année de la transaction
     */
    public int getYear() {
        return this.year;
    }

    /**
     * @return la valeur de la transaction
     */
    public int getValue() {
        return this.value;
    }

    /**
     * @return Une chaine de caractère incluant les détails de la transaction.
     */
    public String toString() {
        return "{" + this.trader + ", " +
                "year: " + this.year + ", " +
                "value:" + this.value + "}";
    }
}
