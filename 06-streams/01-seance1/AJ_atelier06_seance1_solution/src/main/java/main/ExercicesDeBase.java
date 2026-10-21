package main;

import domaine.Trader;
import domaine.Transaction;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Exercices de base sur les streams : filtrage, transformation, tri et réduction de transactions.
 */
public class ExercicesDeBase {

    /**
     * La liste de base de toutes les transactions.
     */
    private final List<Transaction> transactions;

    /**
     * Crée un objet comprenant toutes les transactions afin de faciliter leur usage pour chaque point de l'énoncé
     *
     * @param transactions la liste des transactions
     */
    public ExercicesDeBase(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    // Question 1 : transactions de 2011
    /**
     * Filtre les transactions effectuées en 2011.
     *
     * @return la liste des transactions dont l'année vaut 2011
     */
    public List<Transaction> predicats1() {
        return transactions.stream()
                .filter(t -> t.getYear() == 2011)
                .collect(Collectors.toList());
    }

    // Question 2 : transactions de valeur > 600
    /**
     * Filtre les transactions de valeur strictement supérieure à 600.
     *
     * @return la liste des transactions dont la valeur est > 600
     */
    public List<Transaction> predicats2() {
        return transactions.stream()
                .filter(t -> t.getValue() > 600)
                .collect(Collectors.toList());
    }

    // Question 3 : transactions de Raoul
    /**
     * Filtre les transactions effectuées par le trader Raoul.
     *
     * @return la liste des transactions dont le trader se nomme Raoul
     */
    public List<Transaction> predicats3() {
        return transactions.stream()
                .filter(t -> t.getTrader().getName().equals("Raoul"))
                .collect(Collectors.toList());
    }

    // Question 5 : villes sans doublon
    /**
     * Extrait les villes où travaillent les courtiers, sans doublon.
     *
     * @return la liste des villes distinctes
     */
    public List<String> map1() {
        return transactions.stream()
                .map(t -> t.getTrader().getCity())
                .distinct()
                .collect(Collectors.toList());
    }

    // Question 6 : courtiers de Cambridge
    /**
     * Extrait les courtiers travaillant à Cambridge, sans doublon.
     *
     * @return la liste des traders distincts dont la ville est Cambridge
     */
    public List<Trader> map2() {
        return transactions.stream()
                .map(Transaction::getTrader)
                .distinct()
                .filter(trader -> trader.getCity().equals("Cambridge"))
                .collect(Collectors.toList());
    }

    // Question 7 : noms des traders joints
    /**
     * Assemble les noms des traders, sans doublon, en une seule chaîne.
     *
     * @return les noms distincts séparés par une virgule et une espace
     */
    public String map3() {
        return transactions.stream()
                .map(t -> t.getTrader().getName())
                .distinct()
                .collect(Collectors.joining(", "));
    }

    // Question 8 : transactions triées par valeur
    /**
     * Trie les transactions par valeur croissante.
     *
     * @return la liste des transactions triées par ordre croissant de valeur
     */
    public List<Transaction> sort1() {
        return transactions.stream()
                .sorted(Comparator.comparing(Transaction::getValue))
                .collect(Collectors.toList());
    }

    // Question 9 : noms triés alphabétiquement
    /**
     * Assemble les noms des traders, sans doublon, triés par ordre alphabétique.
     *
     * @return les noms distincts triés, séparés par une virgule et une espace
     */
    public String sort2() {
        return transactions.stream()
                .map(t -> t.getTrader().getName())
                .distinct()
                .sorted()
                .collect(Collectors.joining(", "));
    }

    // Question 10 : valeur max (reduce)
    /**
     * Cherche la valeur de transaction la plus élevée, par réduction.
     *
     * @return la valeur maximale, ou 0 si la liste est vide
     */
    public Integer reduce1() {
        return transactions.stream()
                .map(Transaction::getValue)
                .reduce(0, Integer::max);
    }

    // Question 11 : transaction de valeur minimale (reduce)
    /**
     * Cherche la transaction de valeur la plus faible, par réduction.
     *
     * @return la transaction de valeur minimale, ou la transaction neutre si la liste est vide
     */
    public Transaction reduce2() {
        Transaction transNeutre = new Transaction(null, 20000, Integer.MAX_VALUE);
        return transactions.stream()
                .reduce(transNeutre, (t1, t2) -> t1.getValue() < t2.getValue() ? t1 : t2);
    }

}
