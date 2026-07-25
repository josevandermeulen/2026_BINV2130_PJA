import java.util.ArrayList;

public class MainPizza {

    public static void main(String[] args) {
        ArrayList<Ingredient> ingredients;
        ingredients = new ArrayList<>();
        ingredients.add(Ingredients.TOMATE);
        ingredients.add(Ingredients.PARMESAN);
        ingredients.add(Ingredients.GORGONZOLA);
        ingredients.add(Ingredients.PECORINO);
        ingredients.add(Ingredients.MOZZARELLA);
        ingredients.add(Ingredients.PARMESAN);
        try {
            new PizzaComposee("4 fromages", "le mélange généreux des fromages italiens", ingredients);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();
        ingredients.remove(Ingredients.PARMESAN);
        Pizza p_4fromages = new PizzaComposee("4 fromages", "le mélange généreux des fromages italiens", ingredients);
        System.out.println(p_4fromages);
        System.out.println();
        try {
            p_4fromages.ajouter(Ingredients.OLIVES);
        } catch (UnsupportedOperationException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();
        Client client = new Client("Leconte", "Emmeline", "0488/98.23.85");
        Pizza pizzaClient = new PizzaComposable(client);
        pizzaClient.ajouter(Ingredients.JAMBON);
        pizzaClient.ajouter(Ingredients.SALAMI);
        pizzaClient.ajouter(Ingredients.TOMATE);
        pizzaClient.ajouter(Ingredients.MOZZARELLA);
        pizzaClient.ajouter(Ingredients.OLIVES);
        System.out.println("Ajout d'une tomate déjà présente : " + pizzaClient.ajouter(Ingredients.TOMATE));
        System.out.println("Suppression des scampis absents : " + pizzaClient.supprimer(Ingredients.SCAMPIS));
        System.out.println();
        System.out.println(pizzaClient);
    }

}
