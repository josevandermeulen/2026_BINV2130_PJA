package main;

import domaine.Trader;
import domaine.Transaction;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Exercices de regroupement de transactions à l'aide des collecteurs groupingBy.
 */
public class ExerciceGroupingBy {

    /**
     * Les niveaux de valeur permettant de classer une transaction.
     */
    public enum TransactionsLevel {
        VERY_HI, HI, LO, ME
    }

    /**
     * La liste de base de toutes les transactions.
     */
    private final List<Transaction> transactions;

    /**
     * Crée un objet comprenant toutes les transactions afin de faciliter leur usage pour chaque point de l'énoncé
     *
     * @param transactions la liste des transactions
     */
    public ExerciceGroupingBy(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    // Question 3 : transactions par trader
    /**
     * Regroupe les transactions par trader.
     *
     * @return une map associant à chaque trader la liste de ses transactions
     */
    public Map<Trader, List<Transaction>> groupBy1() {
        return transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getTrader));
    }

    // Question 4 : comptage par trader (counting)
    /**
     * Compte les transactions de chaque trader.
     *
     * @return une map associant à chaque trader son nombre de transactions
     */
    public Map<Trader, Long> groupBy2() {
        return transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getTrader, Collectors.counting()));
    }

    // Question 5 : regroupement par niveau
    /**
     * Répartit les transactions selon leur niveau de valeur.
     *
     * @return une map associant à chaque niveau la liste des transactions correspondantes
     */
    public Map<TransactionsLevel, List<Transaction>> groupBy3() {
        return transactions.stream()
                .collect(Collectors.groupingBy(tr -> {
                    if (tr.getValue() >= 1000) {
                        return TransactionsLevel.VERY_HI;
                    }

                    if (tr.getValue() >= 800) {
                        return TransactionsLevel.HI;
                    }

                    if (tr.getValue() >= 600) {
                        return TransactionsLevel.ME;
                    }

                    return TransactionsLevel.LO;
                }));
    }

    // Question 12 : trader à la plus grande valeur totale de transactions
    /**
     * Cherche le trader dont la somme des valeurs de transactions est la plus élevée.
     *
     * @return le trader au total le plus élevé, ou null si la liste est vide
     */
    public Trader traderValeurTotaleMax() {
        return transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getTrader, Collectors.summingInt(Transaction::getValue)))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    // Question 13 : trader à la plus grande valeur totale par ville
    /**
     * Cherche, pour chaque ville, le trader y ayant généré la plus grande valeur totale de transactions.
     *
     * @return une map associant à chaque ville le trader au total le plus élevé
     */
    public Map<String, Trader> villeTraderMaxTotal() {
        return transactions.stream()
                .collect(Collectors.groupingBy(t -> t.getTrader().getCity(),
                        Collectors.collectingAndThen(
                                Collectors.groupingBy(Transaction::getTrader,
                                        Collectors.summingInt(Transaction::getValue)),
                                totalParTrader -> totalParTrader.entrySet().stream()
                                        .max(Map.Entry.comparingByValue())
                                        .map(Map.Entry::getKey)
                                        .orElse(null))));
    }
}
