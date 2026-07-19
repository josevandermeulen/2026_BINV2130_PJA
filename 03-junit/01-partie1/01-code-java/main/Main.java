package main;

import domaine.Prix;
import domaine.Produit;
import domaine.TypePromo;
import exceptions.DateDejaPresenteException;
import exceptions.PrixNonDisponibleException;
import exceptions.ProduitNonPresentException;
import usecase.ListeProduits;

import java.time.LocalDate;

public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {
        ListeProduits listeProduits = new ListeProduits();
        Produit jeansVert = new Produit("Jeans Vert", "One Green Elephant", "F4");
        Produit jeansRose = new Produit("Jeans Rose", "One Green Elephant", "Y8");
        listeProduits.ajouterProduit(jeansVert);
        listeProduits.ajouterProduit(jeansRose);
        LocalDate date = null;
        try {
            Produit jeansVertC = new Produit("Jeans Vert", "One Green Elephant", "F4");

            Prix prix = new Prix();
            prix.definirPrix(1, 79.9);
            prix.definirPrix(3, 69.9);
            prix.definirPrix(5, 59.9);
            listeProduits.ajouterPrix(jeansVertC, date =LocalDate.of(2018, 12, 3), prix);
            listeProduits.ajouterPrix(jeansRose, date, prix);

            prix = new Prix(TypePromo.SOLDE, 30);
            prix.definirPrix(1, 55.9);
            prix.definirPrix(3, 48.9);
            prix.definirPrix(5, 41.9);
            listeProduits.ajouterPrix(jeansVertC, date = LocalDate.of(2019, 1, 3), prix);
            listeProduits.ajouterPrix(jeansRose, date, prix);

            prix = new Prix(TypePromo.SOLDE, 50);
            prix.definirPrix(1, 39.9);
            prix.definirPrix(3, 34.9);
            prix.definirPrix(5, 29.9);
            listeProduits.ajouterPrix(jeansVertC, date = LocalDate.of(2019, 1, 15), prix);
            listeProduits.ajouterPrix(jeansRose, date, prix);

            prix = new Prix(TypePromo.DESTOCKAGE, 50);
            prix.definirPrix(1, 39.9);
            prix.definirPrix(3, 34.9);
            prix.definirPrix(5, 29.9);
            listeProduits.ajouterPrix(jeansRose, LocalDate.of(2019, 2, 2), prix);
            prix.definirPrix(9, 20);
            listeProduits.ajouterPrix(jeansRose, LocalDate.of(2019, 2, 3), prix);

            System.out.println(listeProduits);

            System.out.println("Le prix du jeans rose le 3 janvier 2019 :\n"+listeProduits.trouverPrix(new Produit("Jeans Rose", "One Green Elephant", "Y8"), LocalDate.of(2019, 1, 3)));

        } catch (DateDejaPresenteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (PrixNonDisponibleException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ProduitNonPresentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
