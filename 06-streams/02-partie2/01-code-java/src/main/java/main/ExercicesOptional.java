package main;

import domaine.Transaction;

import java.util.List;
import java.util.Optional;

/**
 * Exercices de réduction sans valeur neutre, où l'absence de résultat est portée par un Optional.
 */
public class ExercicesOptional {

    /**
     * Cherche la valeur de transaction la plus élevée, par réduction sans valeur neutre.
     *
     * @param transactions la liste des transactions à réduire
     * @return la valeur maximale, ou -1 si la liste est vide
     */
    public Integer optional1(List<Transaction> transactions) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Cherche la transaction de valeur la plus faible, par réduction sans valeur neutre.
     *
     * @param transactions la liste des transactions à réduire
     * @return la transaction de valeur minimale, ou un Optional vide si la liste est vide
     */
    public Optional<Transaction> optional2(List<Transaction> transactions) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
