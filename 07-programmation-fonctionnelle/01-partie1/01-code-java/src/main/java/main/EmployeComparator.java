package main;

import domaine.Employe;

import java.util.Comparator;

public class EmployeComparator implements Comparator<Employe> {

    @Override
    /**
     * Compare sur la taille puis sur le nom en cas d'égalité
     */
    public int compare(Employe e1, Employe e2) {
        if (e1.getTaille() == e2.getTaille()){
            return e1.getNom().compareTo(e2.getNom());
        }
        return e2.getTaille() - e1.getTaille();
    }
}
