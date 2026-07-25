package be.vinci.calculatrice;

/**
 * Une calculatrice minimale, sujet des tests écrits pour le mini-framework de la séance.
 */
public class Calculatrice {

    /**
     * Additionne deux entiers.
     *
     * @param gauche  le premier terme
     * @param droite  le second terme
     * @return la somme des deux termes
     */
    public int additionner(int gauche, int droite) {
        return gauche + droite;
    }

    /**
     * Divise deux entiers.
     *
     * @param numerateur   le dividende
     * @param denominateur le diviseur
     * @return le quotient entier
     * @throws ArithmeticException si le diviseur est nul
     */
    public int diviser(int numerateur, int denominateur) {
        if (denominateur == 0) {
            throw new IllegalArgumentException("Division par zéro");
        }
        return numerateur / denominateur;
    }
}
