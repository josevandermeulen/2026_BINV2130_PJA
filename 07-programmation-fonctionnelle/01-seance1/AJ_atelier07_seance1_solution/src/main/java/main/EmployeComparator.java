package main;

import domaine.Employe;

import java.util.Comparator;

/**
 * Comparateur d'employés : taille décroissante, puis nom en cas d'égalité.
 */
public class EmployeComparator implements Comparator<Employe> {

    /**
     * Compare sur la taille puis sur le nom en cas d'égalité
     */
    @Override
    public int compare(Employe e1, Employe e2) {
        if (e1.getTaille() == e2.getTaille()) {
            return e1.getNom().compareTo(e2.getNom());
        }
        return e2.getTaille() - e1.getTaille();
    }
}
