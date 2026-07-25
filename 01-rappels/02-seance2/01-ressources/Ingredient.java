import java.util.Objects;

public class Ingredient {
    // Question 1
    private String nom;

    private double prix;

    public Ingredient(String nom, double prix) {
        this.nom = nom;
        setPrix(prix);
    }

    public String getNom() {
        return nom;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    // Question 2 : equals/hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ingredient that = (Ingredient) o;
        return nom.equals(that.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom);
    }
}
