package main;

import domaine.Categorie;
import domaine.Client;
import domaine.Commande;
import domaine.LigneDeCommande;
import domaine.Pizza;
import domaine.Pizzeria;
import util.LecteurCommandes;
import util.Util;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * L'analyse des fichiers de commandes : lecture, conversion en Commande et statistiques.
 *
 * La lecture proprement dite est déléguée au LecteurCommandes reçu à la construction, ce qui
 * permet de tester l'analyse sans toucher au disque. Les clients et les pizzas cités dans les
 * fichiers sont retrouvés dans la Pizzeria, qui enregistre aussi les commandes chargées. Les lignes
 * malformées et les fichiers introuvables sont signalés puis ignorés : un fichier abîmé ne doit pas
 * interrompre le chargement.
 */
public class AnalyseCommandes {

    private Pizzeria pizzeria;

    private LecteurCommandes lecteur;

    /**
     * Crée une analyse des commandes de la pizzeria, s'appuyant sur le lecteur donné.
     *
     * @param pizzeria la pizzeria dont on charge les commandes
     * @param lecteur  le lecteur qui fournit les lignes des fichiers
     * @throws IllegalArgumentException si la pizzeria ou le lecteur est null
     */
    public AnalyseCommandes(Pizzeria pizzeria, LecteurCommandes lecteur) {
        Util.checkObject(pizzeria);
        Util.checkObject(lecteur);

        this.pizzeria = pizzeria;
        this.lecteur = lecteur;
    }

    // Question 3
    /**
     * Convertit une ligne de fichier en commande clôturée.
     *
     * La ligne compte quatre champs séparés par des points-virgules : la date et l'heure, le
     * téléphone du client, le titre de la pizza et la quantité. Tous les champs sont vérifiés avant
     * de créer la commande : une commande créée puis abandonnée resterait la commande en cours du
     * client, et bloquerait toutes ses commandes suivantes.
     *
     * @param ligne la ligne à convertir
     * @return la commande correspondante, déjà clôturée
     * @throws IllegalArgumentException si la ligne est null ou vide, si elle ne compte pas quatre
     *                                  champs, si la date est mal formée, si le client ou la pizza
     *                                  est inconnu, ou si la quantité n'est pas un entier
     *                                  strictement positif
     */
    public Commande parserLigne(String ligne) {
        Util.checkString(ligne);
        String[] champs = ligne.split(";");
        if (champs.length != 4) {
            throw new IllegalArgumentException("Nombre de champs incorrect : " + ligne);
        }

        LocalDateTime date;
        try {
            date = LocalDateTime.parse(champs[0]);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Date mal formée : " + champs[0]);
        }

        Client client = pizzeria.rechercherClient(champs[1]);
        if (client == null) {
            throw new IllegalArgumentException("Client inconnu : " + champs[1]);
        }

        Pizza pizza = pizzeria.rechercherPizza(champs[2]);
        if (pizza == null) {
            throw new IllegalArgumentException("Pizza inconnue : " + champs[2]);
        }

        // NumberFormatException est une sous-classe d'IllegalArgumentException
        int quantite = Integer.parseInt(champs[3]);
        Util.checkStrictlyPositive(quantite);

        Commande commande = new Commande(client, date);
        commande.ajouter(pizza, quantite);
        client.cloturerCommandeEnCours();
        return commande;
    }

    // Question 4
    /**
     * Convertit un ensemble de lignes en commandes, en ignorant celles qui sont malformées.
     *
     * Une ligne rejetée est signalée sur la sortie standard, mais n'interrompt pas la conversion
     * des suivantes.
     *
     * @param lignes les lignes à convertir
     * @return les commandes obtenues, sans les lignes rejetées
     */
    public List<Commande> parserLignes(List<String> lignes) {
        List<Commande> commandes = new ArrayList<>();
        for (String ligne : lignes) {
            try {
                commandes.add(parserLigne(ligne));
            } catch (IllegalArgumentException e) {
                System.out.println("Ligne ignorée : " + ligne);
            }
        }
        return commandes;
    }

    // Question 5
    /**
     * Charge les commandes de tous les fichiers référencés par l'index du dossier, et les
     * enregistre dans la pizzeria.
     *
     * Un fichier annoncé par l'index mais introuvable est signalé puis ignoré ; en revanche, un
     * index illisible interrompt le chargement.
     *
     * @param dossier le dossier contenant l'index et les fichiers de commandes
     * @return les commandes de tous les fichiers lisibles, dans l'ordre de l'index
     * @throws IOException si le fichier d'index ne peut pas être lu
     */
    public List<Commande> chargerToutesLesCommandes(String dossier) throws IOException {
        List<Commande> commandes = new ArrayList<>();
        for (String nomFichier : lecteur.lireIndex(dossier + "/index.txt")) {
            try {
                commandes.addAll(parserLignes(lecteur.lireLignes(dossier + "/" + nomFichier)));
            } catch (FileNotFoundException e) {
                System.out.println("Fichier introuvable : " + nomFichier);
            }
        }

        for (Commande commande : commandes) {
            pizzeria.ajouterCommande(commande);
        }
        return commandes;
    }

    // Question 6
    /**
     * Calcule le chiffre d'affaires de chaque jour, à partir des commandes de la pizzeria.
     *
     * @return une table associant chaque jour au montant total de ses commandes, triée par date
     */
    public Map<LocalDate, Double> chiffreAffairesParJour() {
        return pizzeria.getCommandes().stream()
                .collect(Collectors.groupingBy(commande -> commande.getDate().toLocalDate(),
                        TreeMap::new,
                        Collectors.summingDouble(Commande::calculerMontantTotal)));
    }

    // Question 7
    /**
     * Compte les pizzas vendues dans chaque catégorie, à partir des commandes de la pizzeria.
     *
     * @return une table associant chaque catégorie vendue au nombre de pizzas vendues
     */
    public Map<Categorie, Integer> pizzasVenduesParCategorie() {
        return pizzeria.getCommandes().stream()
                .flatMap(commande -> commande.getLignes().stream())
                .collect(Collectors.groupingBy(ligne -> ligne.getPizza().getCategorie(),
                        Collectors.summingInt(LigneDeCommande::getQuantite)));
    }

    // Question 8
    /**
     * Écrit dans un fichier le chiffre d'affaires de chaque jour, par date croissante, suivi du
     * chiffre d'affaires total.
     *
     * @param chemin le chemin du fichier à écrire
     * @throws IOException si le fichier ne peut pas être écrit
     */
    public void ecrireRapport(String chemin) throws IOException {
        Map<LocalDate, Double> parJour = chiffreAffairesParJour();
        double total = 0;
        try (BufferedWriter redacteur = new BufferedWriter(new FileWriter(chemin))) {
            for (Map.Entry<LocalDate, Double> jour : parJour.entrySet()) {
                redacteur.write(jour.getKey() + " : " + jour.getValue() + " euros");
                redacteur.newLine();
                total += jour.getValue();
            }
            redacteur.write("Chiffre d'affaires total : " + total + " euros");
            redacteur.newLine();
        }
    }

    // Question 9 (optionnelle)
    /**
     * Renvoie le client qui a dépensé le plus, toutes commandes de la pizzeria confondues.
     *
     * @return le meilleur client, ou un Optional vide si la pizzeria n'a aucune commande
     */
    public Optional<Client> meilleurClient() {
        return pizzeria.getCommandes().stream()
                .collect(Collectors.groupingBy(Commande::getClient,
                        Collectors.summingDouble(Commande::calculerMontantTotal)))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
    }

}
