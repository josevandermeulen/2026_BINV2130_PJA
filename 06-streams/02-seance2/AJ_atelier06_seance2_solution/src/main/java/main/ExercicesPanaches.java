package main;

import domaine.Trader;
import domaine.Transaction;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

    // Question 6 : valeur max à Cambridge
    /**
     * Cherche la plus grande valeur parmi les transactions effectuées à Cambridge.
     *
     * @return la valeur maximale, ou un Optional vide s'il n'y a aucune transaction à Cambridge
     */
    public Optional<Integer> exercice1() {
        return transactions.stream()
                .filter(t -> t.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getValue)
                .reduce(Integer::max);
    }

    // Question 7 : comptage par trader de Cambridge
    /**
     * Compte les transactions de chaque trader basé à Cambridge.
     *
     * @return une map associant à chaque trader de Cambridge son nombre de transactions
     */
    public Map<Trader, Long> exercice2() {
        return transactions.stream()
                .filter(t -> t.getTrader().getCity().equals("Cambridge"))
                .collect(Collectors.groupingBy(Transaction::getTrader, Collectors.counting()));
    }

    // Question 8 : nom le plus long (valeur > 500)
    /**
     * Cherche, parmi les transactions de valeur supérieure à 500, le nom de trader le plus long.
     *
     * @return le nom le plus long, ou un Optional vide si aucune transaction ne dépasse 500
     */
    public Optional<String> exercice3() {
        return transactions.stream()
                .filter(t -> t.getValue() > 500)
                .map(t -> t.getTrader().getName())
                .sorted((n1, n2) -> Integer.compare(n2.length(), n1.length()))
                .findFirst();
    }

    // Question 9 : moyenne des valeurs par ville
    /**
     * Calcule la moyenne des valeurs de transactions pour chaque ville où sont basés les traders.
     *
     * @return une map associant à chaque ville la moyenne des valeurs de ses transactions
     */
    public Map<String, Double> exercice4() {
        return transactions.stream()
                .collect(Collectors.groupingBy(
                        t -> t.getTrader().getCity(),
                        Collectors.averagingInt(Transaction::getValue)
                ));
    }

    // Question 10 : transaction minimale à Milan
    /**
     * Cherche la transaction de plus faible valeur parmi celles effectuées à Milan.
     *
     * @return la transaction minimale, ou un Optional vide s'il n'y a aucune transaction à Milan
     */
    public Optional<Transaction> exercice5() {
        return transactions.stream()
                .filter(t -> t.getTrader().getCity().equals("Milan"))
                .reduce((t1, t2) -> t1.getValue() < t2.getValue() ? t1 : t2);
    }

    // Question 11 : transactions par année
    /**
     * Regroupe les transactions par année.
     *
     * @return une map associant à chaque année la liste de ses transactions
     */
    public Map<Integer, List<Transaction>> exercice6() {
        return transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getYear));
    }

}
