package be.vinci.minijunit;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe de tests factice pour la partie optionnelle : journalise l'ordre
 * des appels pour vérifier que la méthode @AvantChaqueTest est exécutée
 * avant chaque test, sur une nouvelle instance à chaque fois.
 */
public class ExempleTestsAvecAvant {

    public static final List<String> JOURNAL = new ArrayList<>();

    private String preparation;

    @AvantChaqueTest
    public void preparer() {
        preparation = "prêt";
        JOURNAL.add("avant");
    }

    @MonTest
    public void aVoitLaPreparation() {
        JOURNAL.add("testA");
        Verifications.verifierEgalite("prêt", preparation);
    }

    @MonTest
    public void bVoitAussiLaPreparation() {
        JOURNAL.add("testB");
        Verifications.verifierEgalite("prêt", preparation);
    }

}
