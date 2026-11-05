package main;

import domaine.Categorie;
import domaine.Client;
import domaine.Commande;
import domaine.Pizzeria;
import util.LecteurCommandes;
import util.Util;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        // TODO Question 3
        throw new UnsupportedOperationException("À implémenter");
    }

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
        // TODO Question 4
        throw new UnsupportedOperationException("À implémenter");
    }

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
        // TODO Question 5
        throw new UnsupportedOperationException("À implémenter");
    }

    /**
     * Calcule le chiffre d'affaires de chaque jour, à partir des commandes de la pizzeria.
     *
     * @return une table associant chaque jour au montant total de ses commandes, triée par date
     */
    public Map<LocalDate, Double> chiffreAffairesParJour() {
        // TODO Question 6
        throw new UnsupportedOperationException("À implémenter");
    }

    /**
     * Compte les pizzas vendues dans chaque catégorie, à partir des commandes de la pizzeria.
     *
     * @return une table associant chaque catégorie vendue au nombre de pizzas vendues
     */
    public Map<Categorie, Integer> pizzasVenduesParCategorie() {
        // TODO Question 7
        throw new UnsupportedOperationException("À implémenter");
    }

    /**
     * Écrit dans un fichier le chiffre d'affaires de chaque jour, par date croissante, suivi du
     * chiffre d'affaires total.
     *
     * @param chemin le chemin du fichier à écrire
     * @throws IOException si le fichier ne peut pas être écrit
     */
    public void ecrireRapport(String chemin) throws IOException {
        // TODO Question 8
        throw new UnsupportedOperationException("À implémenter");
    }

    /**
     * Renvoie le client qui a dépensé le plus, toutes commandes de la pizzeria confondues.
     *
     * @return le meilleur client, ou un Optional vide si la pizzeria n'a aucune commande
     */
    public Optional<Client> meilleurClient() {
        // TODO Question 9
        throw new UnsupportedOperationException("À implémenter");
    }

}
