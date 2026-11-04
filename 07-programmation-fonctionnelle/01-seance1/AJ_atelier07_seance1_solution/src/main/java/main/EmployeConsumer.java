package main;

import domaine.Employe;

import java.util.List;
import java.util.function.Consumer;

/**
 * Consumer ajoutant le nom d'un employé à une liste : l'équivalent objet du lambda passé à forEach.
 */
public class EmployeConsumer implements Consumer<Employe> {

    private final List<String> noms;

    /**
     * Crée le consumer.
     *
     * @param noms la liste que le consumer remplit à chaque employé reçu
     */
    public EmployeConsumer(List<String> noms) {
        this.noms = noms;
    }

    /**
     * Ajoute le nom de l'employé à la liste.
     *
     * @param employe l'employé traité
     */
    @Override
    public void accept(Employe employe) {
        noms.add(employe.getNom());
    }
}
