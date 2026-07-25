package be.vinci.minijunit;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe de tests factice pour @Repeter : le test incrémente un compteur
 * d'instance et vérifie qu'il vaut 1 — il ne réussit ses répétitions que
 * si chacune s'exécute sur une instance fraîche.
 */
public class ExempleTestsAvecRepetition {

    public static final List<String> JOURNAL = new ArrayList<>();

    private int compteur;

    @Repeter(fois = 3)
    @MonTest
    public void compteurRepartDeZero() {
        compteur++;
        JOURNAL.add("compteur=" + compteur);
        Verifications.verifierEgalite(1, compteur);
    }

}
