package be.vinci.minijunit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Rapport d'exécution d'une classe de tests : la liste des résultats,
 * dans l'ordre d'exécution des méthodes.
 */
public class RapportExecution {

    private final List<ResultatTest> resultats = new ArrayList<>();

    public void ajouter(ResultatTest resultat) {
        resultats.add(resultat);
    }

    public List<ResultatTest> getResultats() {
        return Collections.unmodifiableList(resultats);
    }

    public long compter(ResultatTest.Statut statut) {
        return resultats.stream().filter(r -> r.getStatut() == statut).count();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (ResultatTest resultat : resultats) {
            builder.append(resultat).append(System.lineSeparator());
        }
        builder.append("Réussites : ").append(compter(ResultatTest.Statut.REUSSITE))
                .append(", échecs : ").append(compter(ResultatTest.Statut.ECHEC))
                .append(", erreurs : ").append(compter(ResultatTest.Statut.ERREUR))
                .append(", ignorés : ").append(compter(ResultatTest.Statut.IGNORE));
        return builder.toString();
    }

}
