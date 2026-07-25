package main;

import domaine.Trader;
import domaine.Transaction;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Exercices panachés combinant filtrage, réduction, regroupement et gestion du vide.
 */
public class ExercicesPanaches {

    /**
     * La liste de base de toutes les transactions.
     */
    private final List<Transaction> transactions;

    /**
     * Crée un objet comprenant toutes les transactions afin de faciliter leur usage pour chaque point de l'énoncé
     *
     * @param transactions la liste des transactions
     */
    public ExercicesPanaches(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    /**
     * Cherche la plus grande valeur parmi les transactions effectuées à Cambridge.
     *
     * @return la valeur maximale, ou un Optional vide s'il n'y a aucune transaction à Cambridge
     */
    public Optional<Integer> exercice1() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Compte les transactions de chaque trader basé à Cambridge.
     *
     * @return une map associant à chaque trader de Cambridge son nombre de transactions
     */
    public Map<Trader, Long> exercice2() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Cherche, parmi les transactions de valeur supérieure à 500, le nom de trader le plus long.
     *
     * @return le nom le plus long, ou un Optional vide si aucune transaction ne dépasse 500
     */
    public Optional<String> exercice3() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Calcule la moyenne des valeurs de transactions pour chaque ville où sont basés les traders.
     *
     * @return une map associant à chaque ville la moyenne des valeurs de ses transactions
     */
    public Map<String, Double> exercice4() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Cherche la transaction de plus faible valeur parmi celles effectuées à Milan.
     *
     * @return la transaction minimale, ou un Optional vide s'il n'y a aucune transaction à Milan
     */
    public Optional<Transaction> exercice5() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Regroupe les transactions par année.
     *
     * @return une map associant à chaque année la liste de ses transactions
     */
    public Map<Integer, List<Transaction>> exercice6() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
