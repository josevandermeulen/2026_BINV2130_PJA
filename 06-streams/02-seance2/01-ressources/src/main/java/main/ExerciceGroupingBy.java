package main;

import domaine.Trader;
import domaine.Transaction;

import java.util.List;
import java.util.Map;

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

    /**
     * Regroupe les transactions par trader.
     *
     * @return une map associant à chaque trader la liste de ses transactions
     */
    public Map<Trader, List<Transaction>> groupBy1() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Compte les transactions de chaque trader.
     *
     * @return une map associant à chaque trader son nombre de transactions
     */
    public Map<Trader, Long> groupBy2() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Répartit les transactions selon leur niveau de valeur.
     *
     * @return une map associant à chaque niveau la liste des transactions correspondantes
     */
    public Map<TransactionsLevel, List<Transaction>> groupBy3() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Cherche, pour chaque ville, le trader y ayant généré la plus grande valeur totale de transactions.
     *
     * @return une map associant à chaque ville le trader au total le plus élevé
     */
    public Map<String, Trader> villeTraderMaxTotal() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
