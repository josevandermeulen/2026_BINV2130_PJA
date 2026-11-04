package main;

import domaine.Employe;
import domaine.Genre;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Exercices sur les interfaces fonctionnelles qui se cachent derrière les lambdas des streams.
 */
public class ExerciceFunctionalInterface {

    private final List<Employe> employes;

    /**
     * Crée l'exercice sur une liste d'employés.
     *
     * @param employes les employés sur lesquels portent les traitements
     */
    public ExerciceFunctionalInterface(List<Employe> employes) {
        this.employes = employes;
    }

    // Question 10 : implémente Function<Employe, String> au lieu du lambda (type trouvé en Question 9)

    /**
     * Renvoie les noms des employés hommes triés par taille décroissante, en implémentant
     * la Function&lt;Employe, String&gt; trouvée en Question 9 (voir EmployeNomFunction).
     *
     * @return les noms des employés hommes, du plus grand au plus petit
     */
    public List<String> exMap() {
        return employes.stream()
                .filter(e -> e.getGenre() == Genre.HOMME)
                .sorted(Comparator.comparingInt(Employe::getTaille).reversed())
                .map(new EmployeNomFunction())
                .toList();
    }

    // Question 12 : implémente Consumer<Employe> au lieu du lambda (type trouvé en Question 11)

    /**
     * Renvoie les noms de tous les employés dans leur ordre d'origine, en implémentant
     * le Consumer&lt;Employe&gt; trouvé en Question 11 (voir EmployeConsumer).
     *
     * @return les noms de tous les employés, dans leur ordre d'origine
     */
    public List<String> exForEach() {
        List<String> noms = new ArrayList<>();
        employes.forEach(new EmployeConsumer(noms));
        return noms;
    }

    // Question 13

    /**
     * Remplace l'instanciation de la classe EmployeComparator par un lambda.
     *
     * @return les employés triés par taille décroissante (puis par nom en cas d'égalité)
     */
    public List<Employe> exComparator() {
        List<Employe> tries = new ArrayList<>(employes);
        tries.sort((e1, e2) -> {
            if (e1.getTaille() == e2.getTaille()) {
                return e1.getNom().compareTo(e2.getNom());
            }
            return e2.getTaille() - e1.getTaille();
        });
        return tries;
    }
}
