package be.vinci.calculatrice;

public class Calculatrice {

    public int additionner(int gauche, int droite) {
        return gauche + droite;
    }

    public int diviser(int numerateur, int denominateur) {
        if (denominateur == 0)
            throw new IllegalArgumentException("Division par zéro");
        return numerateur / denominateur;
    }

}
