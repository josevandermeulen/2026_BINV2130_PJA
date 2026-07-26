package domaine;

import exceptions.DateDejaPresenteException;
import exceptions.PrixNonDisponibleException;
import util.Util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Comparator;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Un produit du magasin et l'historique de ses prix successifs.
 *
 * L'historique est daté : un prix vaut à partir de sa date et jusqu'à la suivante, de sorte que
 * getPrix(LocalDate) rend le prix en vigueur à un jour donné. Deux produits sont
 * identiques s'ils ont même nom, même marque et même rayon.
 */
public class Produit  {

    private final String nom;
    private final String rayon;
    private final String marque;

    private SortedMap<LocalDate, Prix> historiquePrix = new TreeMap<LocalDate, Prix>(Comparator.reverseOrder());

    /**
     * Crée un produit sans aucun prix dans son historique.
     *
     * @param nom    le nom du produit
     * @param marque la marque du produit
     * @param rayon  le rayon où le produit est vendu
     * @throws IllegalArgumentException si l'un des paramètres est null ou vide
     */
    public Produit(String nom, String marque, String rayon) {
        Util.checkString(nom);
        Util.checkString(marque);
        Util.checkString(rayon);
        this.nom = nom;
        this.marque = marque;
        this.rayon = rayon;
    }

    /**
     * Renvoie la marque du produit.
     *
     * @return la marque du produit
     */
    public String getMarque() {
        return marque;
    }

    /**
     * Renvoie le nom du produit.
     *
     * @return le nom du produit
     */
    public String getNom() {
        return nom;
    }

    /**
     * Renvoie le rayon où le produit est vendu.
     *
     * @return le rayon du produit
     */
    public String getRayon() {
        return rayon;
    }

    /**
     * ajoute un prix dans l'historique si la date n'est pas déjà présente
     *
     * @param date la date à partir duquel les prix s'appliquent
     * @param prix le prix qui s'applique à partir de cette date
     * @throws DateDejaPresenteException si la date est déjà présente dans l'historique
     * @throws IllegalArgumentException  en cas de paramètre invalide
     */
    public void ajouterPrix(LocalDate date, Prix prix) throws DateDejaPresenteException {
        Util.checkObject(date);
        Util.checkObject(prix);
        if (historiquePrix.containsKey(date)) {
            throw new DateDejaPresenteException();
        }
        historiquePrix.put(date, prix);
    }

    /**
     * Renvoie le prix appliqué à une date donnée
     *
     * @param date la date pour laquelle il faut retrouver le prix
     * @return le prix
     * @throws PrixNonDisponibleException s'il n'y a pas de prix disponible pour cette date
     * @throws IllegalArgumentException   en cas de paramètre invalide
     */
    public Prix getPrix(LocalDate date) throws PrixNonDisponibleException {
        Util.checkObject(date);
        for (Entry<LocalDate, Prix> entry : historiquePrix.entrySet()) {
            if (!entry.getKey().isAfter(date)) {
                return entry.getValue();
            }
        }
        throw new PrixNonDisponibleException();
    }

    /**
     * Renvoie l'identité du produit suivie de son historique de prix, du plus récent au plus ancien.
     *
     * @return la représentation textuelle du produit
     */
    @Override
    public String toString() {
        String detail = "Nom : " + nom + "\nMarque : " + marque + "\nRayon : " + rayon + "\nHistorique des prix :";
        DateTimeFormatter formater = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG);
        for (Entry<LocalDate, Prix> entry : historiquePrix.entrySet()) {
            detail += "\n" + formater.format(entry.getKey()) + " :\n" + entry.getValue();
        }

        return detail;
    }

    /**
     * Compare deux produits sur leur nom, leur rayon et leur marque — pas sur leur historique de
     * prix.
     *
     * @param o l'objet à comparer à ce produit
     * @return true si o est un produit de même nom, même rayon et même marque
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Produit produit = (Produit) o;

        if (!nom.equals(produit.nom)) {
            return false;
        }
        if (!rayon.equals(produit.rayon)) {
            return false;
        }
        return marque.equals(produit.marque);
    }

    /**
     * Renvoie un code de hachage calculé sur le nom, le rayon et la marque, cohérent avec
     * equals(Object).
     *
     * @return le code de hachage du produit
     */
    @Override
    public int hashCode() {
        int result = nom.hashCode();
        result = 31 * result + rayon.hashCode();
        result = 31 * result + marque.hashCode();
        return result;
    }

}
