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

    /**
     * Ajoute une pizza au menu, sauf si une pizza porte déjà le même titre.
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
        return true;
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
     * Renvoie les commandes enregistrées, dans l'ordre de leur enregistrement.
     *
     * @return les commandes, dans une liste non modifiable
     */
    public List<Commande> getCommandes() {
        return List.copyOf(commandes);
    }

}
