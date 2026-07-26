package main;

import domaine.Trader;
import domaine.Transaction;

import java.util.List;

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

    /**
     * Filtre les transactions effectuées en 2011.
     *
     * @return la liste des transactions dont l'année vaut 2011
     */
    public List<Transaction> predicats1() {
        // TODO Question 1
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Filtre les transactions de valeur strictement supérieure à 600.
     *
     * @return la liste des transactions dont la valeur est > 600
     */
    public List<Transaction> predicats2() {
        // TODO Question 2
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Filtre les transactions effectuées par le trader Raoul.
     *
     * @return la liste des transactions dont le trader se nomme Raoul
     */
    public List<Transaction> predicats3() {
        // TODO Question 3
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Extrait les villes où travaillent les courtiers, sans doublon.
     *
     * @return la liste des villes distinctes
     */
    public List<String> map1() {
        // TODO Question 5
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Extrait les courtiers travaillant à Cambridge, sans doublon.
     *
     * @return la liste des traders distincts dont la ville est Cambridge
     */
    public List<Trader> map2() {
        // TODO Question 6
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Assemble les noms des traders, sans doublon, en une seule chaîne.
     *
     * @return les noms distincts séparés par une virgule et une espace
     */
    public String map3() {
        // TODO Question 7
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Trie les transactions par valeur croissante.
     *
     * @return la liste des transactions triées par ordre croissant de valeur
     */
    public List<Transaction> sort1() {
        // TODO Question 8
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Assemble les noms des traders, sans doublon, triés par ordre alphabétique.
     *
     * @return les noms distincts triés, séparés par une virgule et une espace
     */
    public String sort2() {
        // TODO Question 9
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Cherche la valeur de transaction la plus élevée, par réduction.
     *
     * @return la valeur maximale, ou 0 si la liste est vide
     */
    public Integer reduce1() {
        // TODO Question 10
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Cherche la transaction de valeur la plus faible, par réduction.
     *
     * @return la transaction de valeur minimale, ou la transaction neutre si la liste est vide
     */
    public Transaction reduce2() {
        // TODO Question 11
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Cherche le trader dont la somme des valeurs de transactions est la plus élevée.
     *
     * @return le trader au total le plus élevé, ou null si la liste est vide
     */
    public Trader traderValeurTotaleMax() {
        // TODO Question 12
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
