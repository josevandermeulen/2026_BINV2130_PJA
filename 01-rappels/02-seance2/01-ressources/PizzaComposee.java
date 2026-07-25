import java.util.ArrayList;

public class PizzaComposee extends Pizza {

    // Question 4
    private static final int REMISE = 15;

    public PizzaComposee(String titre, String description, ArrayList<Ingredient> ingredients) {
        super(titre, description, ingredients);
    }

    @Override
    public double calculerPrix() {
        return Math.ceil(super.calculerPrix() * (1 - REMISE / 100.0));
    }

    @Override
    public boolean ajouter(Ingredient ingredient) {
        throw new UnsupportedOperationException("Les ingrédients d'une pizza composée ne peuvent pas être modifiés");
    }

    @Override
    public boolean supprimer(Ingredient ingredient) {
        throw new UnsupportedOperationException("Les ingrédients d'une pizza composée ne peuvent pas être modifiés");
    }
}
