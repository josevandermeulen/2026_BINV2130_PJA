package main;

import domaine.Trader;
import domaine.Transaction;

import java.util.List;

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

    public List<Transaction> predicats1() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public List<Transaction> predicats2() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public List<Transaction> predicats3() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public List<String> map1() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public List<Trader> map2() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public String map3() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public List<Transaction> sort1() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public String sort2() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public Integer reduce1() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public Transaction reduce2() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public Trader traderValeurTotaleMax() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
