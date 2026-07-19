package main;

import domaine.Trader;
import domaine.Transaction;

import java.util.List;
import java.util.Map;

public class ExerciceGroupingBy {

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

    public Map<Trader, List<Transaction>> groupBy1() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public Map<Trader, Long> groupBy2() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public Map<TransactionsLevel, List<Transaction>> groupBy3() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public Map<String, Trader> villeTraderMaxTotal() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
