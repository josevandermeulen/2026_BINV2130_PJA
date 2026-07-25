package usecase;

import domaine.Prix;
import domaine.Produit;
import exceptions.DateDejaPresenteException;
import exceptions.PrixNonDisponibleException;
import exceptions.ProduitNonPresentException;
import exceptions.QuantiteNonAutoriseeException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import static util.Util.checkObject;
import static util.Util.checkString;
import static util.Util.checkStrictlyPositive;

public class ListeProduits {


    //La classe produit n'implémentant pas Comparable, il faut passer un Comparator lors de la création du TreeSet
    // Ici, exemple via une classe anonyme;
    private SortedSet<Produit> produits = new TreeSet<>(Comparator.comparing(Produit::getNom)
                                                        .thenComparing(Produit::getMarque)
                                                        .thenComparing(Produit::getRayon));

    /**
     * Cette méthode renvoie vraie si le produit passé en paramètre est présent dans la liste
     *
     * @param p le produit recherché
     * @return true si le produit est dans la liste, false sinon
     * @throws IllegalArgumentException en cas de paramètre invalide
     */
    public boolean contient(Produit p) {
        checkObject(p);
        return produits.contains(p);
    }

    /**
     * Cette méthode ajoute le produit si celui-ci ne s'y trouve pas encore
     *
     * @param p le produit à ajouter
     * @return true si le produit a pu être ajouté, false sinon
     * @throws IllegalArgumentException en cas de paramètre invalide
     */
    public boolean ajouterProduit(Produit p) {
        checkObject(p);
        //Inutile de tester si le produit est présent avant de l'ajouter car c'est déjà fait au niveau du SortedSet
        return produits.add(p);
    }

    /**
     * Cette méthode supprime le produit si celui-ci est présent dans la liste
     *
     * @param p le produit à supprimer
     * @return true si le produit a pu être supprimé, false sinon
     * @throws IllegalArgumentException en cas de paramètre invalide
     */
    public boolean supprimerProduit(Produit p) {
        checkObject(p);
        return produits.remove(p);
    }


    /**
     * Cette méthode trouve le produit correspondant aux paramètres s'il existe et le renvoie
     *
     * @param nom    le nom du produit recherché
     * @param marque la marque du produit recherché
     * @param rayon  le rayon du produit recherché
     * @return le produit s'il existe, null sinon
     */
    public Produit trouverProduit(String nom, String marque, String rayon) {
        Produit produit = new Produit(nom, marque, rayon);
        for (Produit p : produits) {
            if (p.equals(produit)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Cette méthode renvoie un itérateur permettant de parcourir les produits
     * Si l'on tente d'appeler la méthode remove() sur l'itérateur renvoyé, cela lance une
     * UnsupportedOperationException (comportement par défaut de la méthode remove() d'un Iterator).
     * TIPS ? voir p.18 de la théorie "collections" (wrappers méthodes)
     *
     * @return un itérateur sur les produits
     */

    public Iterator<Produit> produits() {
         return Collections.unmodifiableSortedSet(produits).iterator();
    }

    /**
     * Cette méthode ajoute un prix à appliquer à partir d'une certaine date au produit
     *
     * @param prod le produit auquel s'applique le prix
     * @param date la date à partir de laquelle s'applique le prix
     * @param prix le prix à appliquer
     * @throws ProduitNonPresentException si le produit n'est pas dans la liste
     * @throws DateDejaPresenteException  s'il y a déjà un prix pour cette date pour ce produit
     * @throws IllegalArgumentException   en cas de paramètre invalide ou si le produit n'est pas présent
     */
    public void ajouterPrix(Produit prod, LocalDate date, Prix prix)
            throws DateDejaPresenteException, ProduitNonPresentException {
        checkObject(prix);
        checkObject(date);
        //Il faut récupérer le produit dans le SortedSet afin d'ajouter le prix au bon produit
        Produit produit = trouverProduit(prod);
        produit.ajouterPrix(date, prix);
    }

    /**
     * Cette méthode retrouve et renvoie le prix appliqué à un certain produit à une date donnée.
     *
     * @param prod le produit dont on cherche le prix
     * @param date la date pour laquelle on veut connaître le prix
     * @throws ProduitNonPresentException si le produit n'est pas dans la liste
     * @throws PrixNonDisponibleException s'il n'y a de prix pour cette date pour ce produit
     * @throws IllegalArgumentException   en cas de paramètre invalide ou si le produit n'est pas présent
     */
    public Prix trouverPrix(Produit prod, LocalDate date)
            throws ProduitNonPresentException, PrixNonDisponibleException {
        Produit produit = trouverProduit(prod);
        //Il faut récupérer le produit dans le SortedSet afin de rechercher dans le bon produit
        return produit.getPrix(date);
    }

    //Permet de retrouver le produit dans la liste correspondant au produit passé en paramètre
    // Cette méthode est utilisée dans les autres méthodes afin de récupérer le produit contenu
    // dans le SortedSet lorsqu'on veut lui ajouter un prix, ...
    // Cette méthode permet d'éviter de la duplication de code
    private Produit trouverProduit(Produit prod) throws ProduitNonPresentException {
        checkObject(prod);
        for (Produit p : produits) {
            if (p.equals(prod)) {
                return p;
            }
        }
        throw new ProduitNonPresentException("le produit ne se trouve pas dans la liste des produits.");
    }


    /**
     * Cette méthode renvoie les produits d'un rayon donné, triés par prix croissant (après application
     * de la promotion en cours) pour une date et une quantité données.
     * Un produit du rayon est exclu du résultat s'il n'a pas de prix disponible à cette date, ou si la
     * quantité demandée est inférieure à la quantité minimale autorisée pour son prix courant.
     * En cas d'égalité de prix, les produits sont ensuite triés par nom.
     *
     * @param rayon    le rayon recherché
     * @param date     la date pour laquelle il faut connaître le prix courant
     * @param quantite la quantité pour laquelle il faut calculer le prix
     * @return la liste des produits du rayon ayant un prix disponible, triés par prix croissant (liste vide si aucun)
     * @throws IllegalArgumentException en cas de paramètre invalide (rayon vide/null,
     *                                  date null, quantité négative ou nulle)
     */
    public List<Produit> produitsTriesParPrix(String rayon, LocalDate date, int quantite) {
        checkString(rayon);
        checkObject(date);
        checkStrictlyPositive(quantite);

        Map<Produit, Double> prixParProduit = new HashMap<>();
        for (Produit p : produits) {
            if (!p.getRayon().equals(rayon)) {
                continue;
            }
            try {
                prixParProduit.put(p, p.getPrix(date).getPrixPromo(quantite));
            } catch (PrixNonDisponibleException | QuantiteNonAutoriseeException e) {
                //produit exclu : pas de prix disponible à cette date, ou quantité non autorisée pour son prix courant
            }
        }

        List<Produit> resultat = new ArrayList<>(prixParProduit.keySet());
        resultat.sort(Comparator.<Produit>comparingDouble(prixParProduit::get).thenComparing(Produit::getNom));
        return resultat;
    }

    public String toString() {
        String detail = "";
        for (Produit p : produits) {
            detail += p + "\n----------------------------------------------------------------------\n";
        }
        return detail;
    }

}
