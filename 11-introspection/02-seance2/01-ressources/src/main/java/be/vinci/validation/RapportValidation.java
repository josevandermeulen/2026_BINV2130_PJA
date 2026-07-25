package be.vinci.validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Rapport de validation d'un objet : la liste des violations relevées.
 * Un rapport sans violation signifie que l'objet est valide.
 */
public class RapportValidation {

    private final List<Violation> violations = new ArrayList<>();

    public void ajouter(Violation violation) {
        violations.add(violation);
    }

    public void ajouterToutes(List<Violation> nouvelles) {
        violations.addAll(nouvelles);
    }

    public boolean estValide() {
        return violations.isEmpty();
    }

    public List<Violation> getViolations() {
        return Collections.unmodifiableList(violations);
    }

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
