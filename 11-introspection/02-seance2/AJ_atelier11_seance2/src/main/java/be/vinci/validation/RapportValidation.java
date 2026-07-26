package be.vinci.validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Le bilan d'une validation : toutes les violations relevées sur un objet.
 *
 * Le validateur ne s'arrête pas à la première faute : le rapport les rassemble toutes, pour que
 * l'appelant les voie d'un coup.
 */
public class RapportValidation {

    private final List<Violation> violations = new ArrayList<>();

    /**
     * Ajoute une violation au rapport.
     *
     * @param violation la violation relevée
     */
    public void ajouter(Violation violation) {
        violations.add(violation);
    }

    /**
     * Ajoute plusieurs violations au rapport.
     *
     * @param nouvelles les violations relevées
     */
    public void ajouterToutes(List<Violation> nouvelles) {
        violations.addAll(nouvelles);
    }

    /**
     * Indique si l'objet validé ne présente aucune violation.
     *
     * @return true si le rapport est vide
     */
    public boolean estValide() {
        return violations.isEmpty();
    }

    /**
     * Renvoie les violations relevées.
     *
     * @return la liste des violations
     */
    public List<Violation> getViolations() {
        return Collections.unmodifiableList(violations);
    }

    /**
     * Renvoie « valide », ou la liste des violations relevées.
     *
     * @return la représentation textuelle du rapport
     */
    @Override
    public String toString() {
        if (estValide()) {
            return "Aucune violation : objet valide.";
        }
        StringBuilder builder = new StringBuilder();
        for (Violation violation : violations) {
            builder.append(violation).append(System.lineSeparator());
        }
        builder.append("Violations : ").append(violations.size());
        return builder.toString();
    }

}
