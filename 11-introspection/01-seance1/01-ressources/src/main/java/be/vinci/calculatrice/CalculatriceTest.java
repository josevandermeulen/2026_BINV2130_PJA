package be.vinci.calculatrice;

import be.vinci.minijunit.Affichage;
import be.vinci.minijunit.AvantChaqueTest;
import be.vinci.minijunit.MonTest;
import be.vinci.minijunit.Verifications;

/**
 * Classe de tests écrite pour le mini-JUnit : ses méthodes sont annotées
 * avec nos annotations maison, pas avec celles du vrai JUnit.
 */
public class CalculatriceTest {

    private Calculatrice calculatrice;

    @AvantChaqueTest
    public void creerLaCalculatrice() {
        calculatrice = new Calculatrice();
    }

    @Affichage(valeur = "3 + 4 vaut 7")
    @MonTest
    public void additionnerDeuxNombres() {
        Verifications.verifierEgalite(7, calculatrice.additionner(3, 4));
    }

    @Affichage(valeur = "12 divisé par 3 vaut 4")
    @MonTest
    public void diviserSansReste() {
        Verifications.verifierEgalite(4, calculatrice.diviser(12, 3));
    }

    @MonTest
    public void diviserParZeroLanceUneException() {
        try {
            calculatrice.diviser(1, 0);
            Verifications.verifierVrai(false, "Une IllegalArgumentException était attendue");
        } catch (IllegalArgumentException attendue) {
            // comportement attendu
        }
    }

}
