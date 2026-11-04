package main;

import domaine.Employe;

import java.util.function.Function;

/**
 * Function extrayant le nom d'un employé : l'équivalent objet du lambda e -> e.getNom().
 */
public class EmployeNomFunction implements Function<Employe, String> {

    /**
     * Extrait le nom de l'employé.
     *
     * @param employe l'employé traité
     * @return le nom de l'employé
     */
    @Override
    public String apply(Employe employe) {
        return employe.getNom();
    }
}
