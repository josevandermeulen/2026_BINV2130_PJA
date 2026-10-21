package main;

import domaine.Employe;
import domaine.Genre;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Réécriture d'une boucle explicite sur des employés à l'aide de l'API Stream.
 */
public class ExercicesEmployes {

    // Question 4 : boucle implicite
    /**
     * Filtre les employés de genre masculin.
     *
     * @param employes la liste des employés à filtrer
     * @return la liste des employés dont le genre est HOMME
     */
    public List<Employe> listeDesHommes(List<Employe> employes) {
        return employes.stream()
                .filter(e -> e.getGenre() == Genre.HOMME)
                .collect(Collectors.toList());
    }
}
