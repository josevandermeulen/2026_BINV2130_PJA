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

    /**
     * Ajoute le résultat d'un test au rapport.
     *
     * @param resultat le résultat à consigner
     */
    public void ajouter(ResultatTest resultat) {
        resultats.add(resultat);
    }

    /**
     * Renvoie les résultats consignés, dans l'ordre d'exécution.
     *
     * @return la liste des résultats
     */
    public List<ResultatTest> getResultats() {
        return Collections.unmodifiableList(resultats);
    }

    /**
     * Compte les tests d'un statut donné.
     *
     * @param statut le statut recherché
     * @return le nombre de tests portant ce statut
     */
    public long compter(ResultatTest.Statut statut) {
        return resultats.stream().filter(r -> r.getStatut() == statut).count();
    }

    /**
     * Renvoie le détail des tests exécutés, suivi du total par statut.
     *
     * @return la représentation textuelle du rapport
     */
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
