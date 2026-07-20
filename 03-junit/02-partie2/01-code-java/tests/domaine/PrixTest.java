package domaine;

import exceptions.QuantiteNonAutoriseeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class PrixTest {

    // (test repris de la partie 1, Question 1) : Préparation du test
    private Prix prixAucune;

    private Prix prixPub;

    private Prix prixSolde;

    @BeforeEach
    void setUp() {
        prixAucune = new Prix();
        prixAucune.definirPrix(1, 20);
        prixAucune.definirPrix(10, 10);

        prixPub = new Prix(TypePromo.PUB, 10);
        prixPub.definirPrix(3, 15);
        prixPub.definirPrix(10, 8);

        prixSolde = new Prix(TypePromo.SOLDE, 30);
        prixSolde.definirPrix(2, 18);
        prixSolde.definirPrix(10, 9);
    }

    // (test repris de la partie 1, Question 2) : Tests des getters

    @Test
    @DisplayName("getValeurPromo sur prixAucune renvoie 0")
    void testGetValeurPromoAucune() {
        assertEquals(0, prixAucune.getValeurPromo());
    }

    @Test
    @DisplayName("getTypePromo sur prixAucune renvoie null")
    void testGetTypePromoAucune() {
        assertNull(prixAucune.getTypePromo());
    }

    // (test repris de la partie 1, Question 3) : Test du constructeur avec TypePromo null

    @Test
    @DisplayName("Test du constructeur avec paramètre Promo null")
    void testPrix1() {
        assertThrows(IllegalArgumentException.class, () -> new Prix(null, 15));
    }

    // (test repris de la partie 1, Question 4) : Regroupement d'assertions (assertAll)

    @Test
    @DisplayName("getValeurPromo et getTypePromo sur prixSolde renvoient les valeurs du constructeur")
    void testGettersSolde() {
        assertAll(
                () -> assertEquals(30, prixSolde.getValeurPromo()),
                () -> assertSame(TypePromo.SOLDE, prixSolde.getTypePromo())
        );
    }

    // (test repris de la partie 1, Question 5) : getPrixPromo sans promo

    @Test
    @DisplayName("getPrixPromo sur prixAucune renvoie le même résultat que getPrix")
    void testGetPrixPromoAucune() {
        assertEquals(prixAucune.getPrix(1), prixAucune.getPrixPromo(1));
    }

    // (test repris de la partie 1, Question 6) : getPrixPromo avec promo PUB

    @Test
    @DisplayName("getPrixPromo sur prixPub soustrait le montant fixe de valeurPromo")
    void testGetPrixPromoPub() {
        assertEquals(prixPub.getPrix(3) - prixPub.getValeurPromo(), prixPub.getPrixPromo(3));
    }

    // (test repris de la partie 1, Question 7) : getPrixPromo avec promo SOLDE

    @Test
    @DisplayName("getPrixPromo sur prixSolde applique le pourcentage de valeurPromo")
    void testGetPrixPromoSolde() {
        assertEquals(prixSolde.getPrix(2) * (1 - prixSolde.getValeurPromo() / 100), prixSolde.getPrixPromo(2));
    }

    // (test repris de la partie 1, Question 8) : getPrixPromo et le plancher de DESTOCKAGE

    @Test
    @DisplayName("getPrixPromo avec une promo DESTOCKAGE ne descend jamais sous 1 euro")
    void testGetPrixPromoDestockagePlancher() {
        Prix prixDestockage = new Prix(TypePromo.DESTOCKAGE, 95);
        prixDestockage.definirPrix(1, 5);
        assertEquals(1, prixDestockage.getPrixPromo(1));
    }

    // (test repris de la partie 1, Question 9) : getPrixPromo avec une quantité invalide

    @ParameterizedTest
    @ValueSource(ints = {-1, 0})
    @DisplayName("Test de la méthode getPrixPromo avec une quantité < ou = à 0")
    void testGetPrixPromo1(int quantite) {
        assertThrows(IllegalArgumentException.class, () -> prixPub.getPrixPromo(quantite));
    }

    // (test repris de la partie 1, Question 10) : Constructeur — valeur de promo invalide

    @ParameterizedTest
    @DisplayName("Test du constructeur avec une valeur de promo < ou = à 0")
    @ValueSource(doubles = {-7, -4, 0})
    void testPrix2(double valeur) {
        assertThrows(IllegalArgumentException.class, () -> new Prix(TypePromo.SOLDE, valeur));
    }

    // (test repris de la partie 1, Question 11) : definirPrix — quantité invalide

    @ParameterizedTest
    @ValueSource(ints = {-1, 0})
    @DisplayName("Test de la méthode definirPrix avec une quantité < ou = à 0")
    void definirPrix1(int quantite) {
        assertThrows(IllegalArgumentException.class, () -> prixPub.definirPrix(quantite, 15));
    }

    // (test repris de la partie 1, Question 12) : definirPrix — prix unitaire invalide

    @ParameterizedTest
    @ValueSource(doubles = {-3, 0})
    @DisplayName("Test de la méthode definirPrix avec une valeur < ou = à 0")
    void definirPrix2(double valeur) {
        assertThrows(IllegalArgumentException.class, () -> prixPub.definirPrix(15, valeur));
    }

    // (test repris de la partie 1, Question 13) : definirPrix — remplacement d'un palier existant

    @Test
    @DisplayName("Test du remplacement de la valeur pour une quantité déjà existante")
    void definirPrix3() {
        prixAucune.definirPrix(10, 6);
        assertEquals(6, prixAucune.getPrix(10));
    }

    // (test repris de la partie 1, Question 14) : getPrix — quantité invalide

    @ParameterizedTest
    @ValueSource(ints = {-1, 0})
    @DisplayName("Test de la méthode getPrix avec une quantité < ou = à 0")
    void testGetPrix1(int quantite) {
        assertThrows(IllegalArgumentException.class, () -> prixPub.getPrix(quantite));
    }

    // (test repris de la partie 1, Question 15) : getPrix — balayage des paliers

    @ParameterizedTest
    @CsvFileSource(resources = "paliers.csv", numLinesToSkip = 1)
    @DisplayName("Test de la méthode getPrix pour prixAucune sur les paliers de la question 1")
    void testGetPrix2(int quantite, double prixAttendu) throws QuantiteNonAutoriseeException {
        assertEquals(prixAttendu, prixAucune.getPrix(quantite));
    }

    // (test repris de la partie 1, Question 16) : getPrix — seuil minimal d'une promo

    @Test
    @DisplayName("Test de getPrix avec une quantité sous la plus petite quantité de prixPub")
    void testGetPrix3() {
        assertThrows(QuantiteNonAutoriseeException.class, () -> prixPub.getPrix(2));
    }

    // (test repris de la partie 1, Question 17) : getPrix — seuil minimal, deuxième cas

    @Test
    @DisplayName("Test de getPrix avec une quantité sous la plus petite quantité de prixSolde")
    void testGetPrix4() {
        assertThrows(QuantiteNonAutoriseeException.class, () -> prixSolde.getPrix(1));
    }

    // (test repris de la partie 1, Question 18) : Test de scénario sur plusieurs paliers

    @Test
    @DisplayName("Test de scénario complet sur plusieurs paliers")
    void testGetPrixScenarioPlusieursPaliers() {
        Prix prix = new Prix();
        prix.definirPrix(3, 20);
        prix.definirPrix(5, 18);
        prix.definirPrix(10, 15);

        assertAll(
                () -> assertEquals(20, prix.getPrix(3)),
                () -> assertEquals(18, prix.getPrix(5)),
                () -> assertEquals(15, prix.getPrix(10)),
                () -> assertEquals(20, prix.getPrix(4)),
                () -> assertEquals(18, prix.getPrix(9)),
                () -> assertThrows(QuantiteNonAutoriseeException.class, () -> prix.getPrix(2))
        );

        prix.definirPrix(5, 16);

        assertAll(
                () -> assertEquals(16, prix.getPrix(5)),
                () -> assertEquals(16, prix.getPrix(9)),
                () -> assertEquals(15, prix.getPrix(10))
        );
    }
}
