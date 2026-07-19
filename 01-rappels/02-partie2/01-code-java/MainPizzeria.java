public class MainPizzeria {

    public static void main(String[] args) {
        Client emmeline = new Client("Leconte", "Emmeline", "0488/98.23.85");
        Client stephanie = new Client("Ferneeuw", "Stéphanie", "0475/51.30.80");
        Commande commandeEmmeline = new Commande(emmeline);
        System.out.println("Commande en cours d'Emmeline : " + emmeline.getCommandeEnCours());
        System.out.println();
        try {
            new Commande(emmeline);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();
        commandeEmmeline.ajouter(MenuPizzeria.PIZZA_DUCHEF, 2);
        PizzaComposable pizzaComposable = new PizzaComposable(emmeline);
        pizzaComposable.ajouter(Ingredients.AUBERGINES);
        pizzaComposable.ajouter(Ingredients.TOMATE);
        pizzaComposable.ajouter(Ingredients.GORGONZOLA);
        emmeline.cloturerCommandeEnCours();
        System.out.println(commandeEmmeline);
        System.out.println(commandeEmmeline.detailler());
        System.out.println();
        System.out.println("Commande en cours d'Emmeline : " + emmeline.getCommandeEnCours());
        System.out.println();
        System.out.println("ajout d'une pizza à une commande clôturée : " + commandeEmmeline.ajouter(MenuPizzeria.PIZZA_4FROMAGES));
        System.out.println();
        Commande commandeStephanie = new Commande(stephanie);
        commandeStephanie.ajouter(MenuPizzeria.PIZZA_MARGARITA, 1);
        commandeStephanie.ajouter(MenuPizzeria.PIZZA_MARGARITA);
        commandeStephanie.ajouter(MenuPizzeria.PIZZA_MARINIERE, 3);
        System.out.println(commandeStephanie);
        System.out.println(commandeStephanie.detailler());
        System.out.println("Montant de la commande de Stéphanie : " + commandeStephanie.calculerMontantTotal());
        System.out.println();
        Commande commandeEmmeline2 = new Commande(emmeline);
        pizzaComposable = new PizzaComposable(emmeline);
        pizzaComposable.ajouter(Ingredients.JAMBON);
        pizzaComposable.ajouter(Ingredients.TOMATE);
        pizzaComposable.ajouter(Ingredients.OLIVES);
        pizzaComposable.ajouter(Ingredients.MOZZARELLA);
        System.out.println(commandeEmmeline2);
    }

}
