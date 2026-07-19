package be.vinci.minijunit;

/**
 * Classe de tests factice pour @Affichage : une méthode avec libellé,
 * une méthode sans — le rapport doit afficher le libellé quand il existe,
 * le nom de la méthode sinon.
 */
public class ExempleTestsAvecAffichage {

    @Affichage(valeur = "l'addition des entiers fonctionne")
    @MonTest
    public void aAvecLibelle() {
        Verifications.verifierEgalite(4, 2 + 2);
    }

    @MonTest
    public void bSansLibelle() {
        Verifications.verifierVrai(true, "ne peut pas échouer");
    }

}
