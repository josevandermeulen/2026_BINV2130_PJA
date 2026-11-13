package domaine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import util.Util;

/**
 * La pizzeria : ses clients, son menu, les commandes qu'elle a reçues et son stock d'ingrédients.
 *
 * Les clients sont retrouvés par leur numéro de téléphone et les pizzas du menu par leur titre,
 * d'où les deux Map. Le stock est reçu à la construction (injection de dépendance) : la pizzeria
 * ne sait pas comment il est tenu, seulement s'il reste d'un ingrédient.
 */
public class Pizzeria {

    private Map<String, Client> clients = new HashMap<>();

    private Map<String, Pizza> pizzas = new HashMap<>();

    // la carte : les pizzas du menu regroupées par catégorie, chacune triée dans son ordre naturel
    private Map<Categorie, SortedSet<Pizza>> carte = new HashMap<>();

    // un LinkedHashSet garde l'ordre d'enregistrement et refuse un doublon sans parcourir la collection
    private Set<Commande> commandes = new LinkedHashSet<>();

    private Stock stock;

    /**
     * Crée une pizzeria sans client, sans pizza ni commande, adossée au stock donné.
     *
     * @param stock le stock d'ingrédients de la pizzeria
     * @throws IllegalArgumentException si le stock est null
     */
    public Pizzeria(Stock stock) {
        Util.checkObject(stock);

        this.stock = stock;
    }

    /**
     * Ajoute un client à la pizzeria, sauf si un client porte déjà le même numéro de téléphone.
     *
     * @param client le client à ajouter
     * @return true si le client a été ajouté, false si son téléphone est déjà connu
     * @throws IllegalArgumentException si le client est null
     */
    public boolean ajouterClient(Client client) {
        Util.checkObject(client);

        if (clients.containsKey(client.getTelephone())) {
            return false;
        }
        clients.put(client.getTelephone(), client);
        return true;
    }

    /**
     * Recherche un client par son numéro de téléphone.
     *
     * @param telephone le numéro de téléphone du client
     * @return le client, ou null si aucun client ne porte ce numéro
     * @throws IllegalArgumentException si le téléphone est null ou vide
     */
    public Client rechercherClient(String telephone) {
        Util.checkString(telephone);

        return clients.get(telephone);
    }

    /**
     * Renvoie les clients de la pizzeria, dans une collection non modifiable.
     *
     * @return les clients, en lecture seule et sans ordre particulier
     */
    public Collection<Client> getClients() {
        return Collections.unmodifiableCollection(clients.values());
    }

    // Question 3 : ranger la pizza sur la carte
    /**
     * Ajoute une pizza au menu, sauf si une pizza porte déjà le même titre, et la range sur la
     * carte dans sa catégorie.
     *
     * @param pizza la pizza à ajouter
     * @return true si la pizza a été ajoutée, false si son titre est déjà au menu
     * @throws IllegalArgumentException si la pizza est null
     */
    public boolean ajouterPizza(Pizza pizza) {
        Util.checkObject(pizza);

        if (pizzas.containsKey(pizza.getTitre())) {
            return false;
        }
        pizzas.put(pizza.getTitre(), pizza);

        // l'ensemble de la catégorie n'est créé qu'au premier ajout d'une pizza de cette catégorie
        carte.computeIfAbsent(pizza.getCategorie(), categorie -> new TreeSet<>()).add(pizza);
        return true;
    }

    // Question 4
    /**
     * Renvoie les pizzas du menu d'une catégorie, dans l'ordre naturel des pizzas.
     *
     * @param categorie la catégorie demandée
     * @return les pizzas de la catégorie, en lecture seule ; un ensemble vide si le menu n'en
     *         compte aucune
     * @throws IllegalArgumentException si la catégorie est null
     */
    public SortedSet<Pizza> getPizzas(Categorie categorie) {
        Util.checkObject(categorie);

        SortedSet<Pizza> pizzasDeLaCategorie = carte.get(categorie);
        if (pizzasDeLaCategorie == null) {
            return Collections.emptySortedSet();
        }
        return Collections.unmodifiableSortedSet(pizzasDeLaCategorie);
    }

    // Question 5
    /**
     * Retire une pizza du menu et de la carte. Une catégorie dont la dernière pizza est retirée
     * disparaît de la carte.
     *
     * @param titre le titre de la pizza à retirer
     * @return true si la pizza a été retirée, false si aucune pizza du menu ne porte ce titre
     * @throws IllegalArgumentException si le titre est null ou vide
     */
    public boolean retirerPizza(String titre) {
        Util.checkString(titre);

        Pizza pizza = pizzas.remove(titre);
        if (pizza == null) {
            return false;
        }

        SortedSet<Pizza> pizzasDeLaCategorie = carte.get(pizza.getCategorie());
        pizzasDeLaCategorie.remove(pizza);
        if (pizzasDeLaCategorie.isEmpty()) {
            carte.remove(pizza.getCategorie());
        }
        return true;
    }

    // FinQuestion

    /**
     * Indique si la carte a une entrée pour la catégorie donnée.
     *
     * @param categorie la catégorie demandée
     * @return true si la carte a une entrée pour cette catégorie
     */
    public boolean aDesPizzas(Categorie categorie) {
        return carte.containsKey(categorie);
    }

    /**
     * Recherche une pizza du menu par son titre.
     *
     * @param titre le titre de la pizza
     * @return la pizza, ou null si aucune pizza du menu ne porte ce titre
     * @throws IllegalArgumentException si le titre est null ou vide
     */
    public Pizza rechercherPizza(String titre) {
        Util.checkString(titre);

        return pizzas.get(titre);
    }

    /**
     * Renvoie le menu, trié dans l'ordre naturel des pizzas (l'ordre alphabétique des titres).
     *
     * @return les pizzas du menu, triées, dans une liste non modifiable
     */
    public List<Pizza> getMenu() {
        List<Pizza> menu = new ArrayList<>(pizzas.values());
        Collections.sort(menu);
        return Collections.unmodifiableList(menu);
    }

    /**
     * Renvoie le menu trié par prix croissant ; à prix égal, dans l'ordre alphabétique des titres.
     *
     * @return les pizzas du menu, triées par prix, dans une liste non modifiable
     */
    public List<Pizza> menuTrieParPrix() {
        List<Pizza> menu = new ArrayList<>(pizzas.values());
        menu.sort(Comparator.comparingDouble(Pizza::calculerPrix).thenComparing(Comparator.naturalOrder()));
        return Collections.unmodifiableList(menu);
    }

    // Question 7
    /**
     * Renvoie le menu trié de la pizza la plus garnie à la moins garnie, à l'aide d'un Comparator
     * écrit en classe anonyme.
     *
     * @return les pizzas du menu, triées par nombre d'ingrédients décroissant, dans une liste non
     *         modifiable
     */
    public List<Pizza> menuTrieParNombreIngredients() {
        List<Pizza> menu = new ArrayList<>(pizzas.values());
        menu.sort(new Comparator<Pizza>() {
            @Override
            public int compare(Pizza pizza1, Pizza pizza2) {
                int parNombre = Integer.compare(pizza2.getIngredients().size(), pizza1.getIngredients().size());
                if (parNombre != 0) {
                    return parNombre;
                }
                return pizza1.compareTo(pizza2);
            }
        });
        return Collections.unmodifiableList(menu);
    }

    // Question 8
    /**
     * Renvoie le menu trié par catégorie (dans l'ordre de déclaration de l'énuméré), puis par titre,
     * à l'aide d'un Comparator écrit en lambda.
     *
     * @return les pizzas du menu, triées par catégorie puis par titre, dans une liste non modifiable
     */
    public List<Pizza> menuTrieParCategorie() {
        List<Pizza> menu = new ArrayList<>(pizzas.values());
        menu.sort((pizza1, pizza2) -> {
            int parCategorie = pizza1.getCategorie().compareTo(pizza2.getCategorie());
            if (parCategorie != 0) {
                return parCategorie;
            }
            return pizza1.compareTo(pizza2);
        });
        return Collections.unmodifiableList(menu);
    }

    // FinQuestion

    /**
     * Enregistre une commande clôturée d'un client de la pizzeria.
     *
     * @param commande la commande à enregistrer
     * @return true si la commande a été enregistrée, false si son client n'est pas un client de la
     *         pizzeria, si elle est encore en cours ou si elle est déjà enregistrée
     * @throws IllegalArgumentException si la commande est null
     */
    public boolean ajouterCommande(Commande commande) {
        Util.checkObject(commande);

        Client client = commande.getClient();
        if (clients.get(client.getTelephone()) != client) {
            return false;
        }

        if (client.getCommandeEnCours() == commande) {
            return false;
        }
        return commandes.add(commande);
    }

    /**
     * Enregistre une commande clôturée, à condition que le stock dispose de tous les ingrédients de
     * toutes ses pizzas.
     *
     * @param commande la commande à enregistrer
     * @return true si la commande a été enregistrée, false si un ingrédient manque ou si
     *         ajouterCommande la refuse
     * @throws IllegalArgumentException si la commande est null
     */
    public boolean ajouterCommandeSiDisponible(Commande commande) {
        Util.checkObject(commande);

        for (LigneDeCommande ligne : commande.getLignes()) {
            for (Ingredient ingredient : ligne.getPizza()) {
                if (!stock.estDisponible(ingredient)) {
                    return false;
                }
            }
        }
        return ajouterCommande(commande);
    }

    /**
     * Renvoie les commandes enregistrées, dans l'ordre de leur enregistrement.
     *
     * @return les commandes, dans une liste non modifiable
     */
    public List<Commande> getCommandes() {
        return List.copyOf(commandes);
    }

    // Question 14
    /**
     * Établit le bilan de la pizzeria : nombre de commandes, nombre de pizzas vendues et chiffre
     * d'affaires.
     *
     * @return le bilan des commandes enregistrées
     */
    public Bilan bilan() {
        int nombrePizzas = 0;
        double chiffreAffaires = 0;
        for (Commande commande : commandes) {
            for (LigneDeCommande ligne : commande.getLignes()) {
                nombrePizzas += ligne.getQuantite();
            }
            chiffreAffaires += commande.calculerMontantTotal();
        }
        return new Bilan(commandes.size(), nombrePizzas, chiffreAffaires);
    }

    // FinQuestion

    /**
     * Renvoie le programme de fidélité de la pizzeria pour le seuil donné.
     *
     * @param seuil le nombre de commandes à partir duquel un client est fidèle
     * @return le programme de fidélité, attaché à cette pizzeria
     * @throws IllegalArgumentException si le seuil n'est pas strictement positif
     */
    public Fidelite fidelite(int seuil) {
        return new Fidelite(seuil);
    }

    /**
     * Le bilan chiffré de la pizzeria à un instant donné.
     *
     * C'est une classe interne statique : elle n'est rattachée à aucune Pizzeria en particulier et
     * n'a pas accès à ses attributs. Elle reçoit donc toutes ses valeurs par son constructeur, et
     * s'instancie de l'extérieur par new Pizzeria.Bilan(...).
     */
    public static class Bilan {

        private int nombreCommandes;

        private int nombrePizzas;

        private double chiffreAffaires;

        /**
         * Crée un bilan.
         *
         * @param nombreCommandes le nombre de commandes
         * @param nombrePizzas    le nombre de pizzas vendues
         * @param chiffreAffaires le chiffre d'affaires, en euros
         */
        public Bilan(int nombreCommandes, int nombrePizzas, double chiffreAffaires) {
            this.nombreCommandes = nombreCommandes;
            this.nombrePizzas = nombrePizzas;
            this.chiffreAffaires = chiffreAffaires;
        }

        /**
         * Renvoie le nombre de commandes.
         *
         * @return le nombre de commandes
         */
        public int getNombreCommandes() {
            return nombreCommandes;
        }

        /**
         * Renvoie le nombre de pizzas vendues.
         *
         * @return le nombre de pizzas vendues
         */
        public int getNombrePizzas() {
            return nombrePizzas;
        }

        /**
         * Renvoie le chiffre d'affaires.
         *
         * @return le chiffre d'affaires, en euros
         */
        public double getChiffreAffaires() {
            return chiffreAffaires;
        }

    }

    /**
     * Le programme de fidélité de la pizzeria : un client est fidèle à partir d'un certain nombre
     * de commandes.
     *
     * C'est une classe interne non statique : chaque instance est rattachée à la Pizzeria qui l'a
     * créée (Pizzeria.this), dont elle lit directement les attributs privés.
     */
    public class Fidelite {

        private int seuil;

        /**
         * Crée un programme de fidélité pour la pizzeria englobante.
         *
         * @param seuil le nombre de commandes à partir duquel un client est fidèle
         * @throws IllegalArgumentException si le seuil n'est pas strictement positif
         */
        public Fidelite(int seuil) {
            Util.checkStrictlyPositive(seuil);

            this.seuil = seuil;
        }

        // Question 15
        /**
         * Indique si un client a passé au moins seuil commandes enregistrées dans la pizzeria.
         *
         * @param client le client à examiner
         * @return true si le client est fidèle
         * @throws IllegalArgumentException si le client est null
         */
        public boolean estFidele(Client client) {
            Util.checkObject(client);

            int nombre = 0;
            // commandes est l'attribut de la Pizzeria englobante (Pizzeria.this.commandes)
            for (Commande commande : commandes) {
                if (commande.getClient().equals(client)) {
                    nombre++;
                }
            }
            return nombre >= seuil;
        }

    }

}
