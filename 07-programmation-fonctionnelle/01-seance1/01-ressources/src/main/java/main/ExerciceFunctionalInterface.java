package main;

import domaine.Employe;
import domaine.Genre;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Exercices sur les interfaces fonctionnelles qui se cachent derrière les lambdas des streams.
 */
public class ExerciceFunctionalInterface {

    private final List<Employe> employes;

    public ExerciceFunctionalInterface(List<Employe> employes) {
        this.employes = employes;
    }

    /**
     * Remplacer l'instanciation de la classe EmployeComparator par un lambda.
     *
     * @return les employés triés par taille décroissante (puis par nom en cas d'égalité)
     */
    public List<Employe> exComparator() {
        List<Employe> tries = new ArrayList<>(employes);
        tries.sort(new EmployeComparator());
        return tries;
    }

    /**
     * Trouver le type du paramètre de la méthode map.
     * Ensuite créer une classe implémentant la functional interface correspondante pour
     * remplacer le lambda en paramètre par une instance de celle-ci.
     */
    public List<String> exMap() {
        return employes.stream()
                .filter(e -> e.getGenre() == Genre.HOMME)
                .sorted(Comparator.comparingInt(Employe::getTaille).reversed())
                .map(e -> e.getNom())
                .collect(Collectors.toList());
    }

    /**
     * Trouver le type du paramètre de la méthode forEach.
     * Ensuite créer une classe implémentant la functional interface correspondante pour
     * remplacer le lambda en paramètre par une instance de celle-ci.
     */
    public List<String> exForEach() {
        List<String> noms = new ArrayList<>();
        employes.forEach(e -> noms.add(e.getNom()));
        return noms;
    }
}
