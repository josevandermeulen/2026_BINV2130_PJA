package be.vinci.main;

import be.vinci.domaine.Adresse;
import be.vinci.domaine.Etudiant;
import be.vinci.validation.Validateur;

public class Main {

    public static void main(String[] args) {
        Validateur validateur = new Validateur();

        Etudiant valide = new Etudiant("Alice", "20240001", 60,
                new Adresse("rue de la Paix", 12));
        System.out.println("Étudiant valide :");
        System.out.println(validateur.valider(valide));

        Etudiant fautif = new Etudiant("Al", null, -5,
                new Adresse(null, 3));
        System.out.println();
        System.out.println("Étudiant fautif :");
        System.out.println(validateur.valider(fautif));
    }

}
