package main;

import domaine.Ingredient;
import domaine.IngredientQuantifie;
import domaine.Instruction;
import domaine.Livre;
import domaine.Plat;
import domaine.Plat.Cout;
import domaine.Plat.Difficulte;
import domaine.Unite;

import java.util.Iterator;
import java.util.Map;

/**
 * Programme de démonstration : construit des plats, les range dans un livre de cuisine et affiche
 * les fiches ainsi que la table des matières obtenues.
 *
 * Trois opérations sont volontairement invalides. Chacune affiche le refus qu'elle provoque :
 * c'est ainsi que se vérifie la validation des paramètres et l'encapsulation des collections.
 */
public class Main {

    public static void main(String[] args) {

        // Question 1 : passage du type de plat au constructeur
        Plat plat = new Plat("Waterzooi", 4, Difficulte.XX, Cout.$$$, Plat.Type.PLAT);
        // FinQuestion

        Instruction instruction = new Instruction("Couper les légumes", 15);

        // Les positions commencent à 1 : l'insertion en position 0 doit être refusée.
        try {
            plat.insererInstruction(0, instruction);
            System.out.println("ANOMALIE : insertion acceptée en position 0");
        } catch (IllegalArgumentException iae) {
            System.out.println("Insertion refusée en position 0 : les positions commencent à 1");
        }

        plat.ajouterInstruction(instruction);
        instruction = new Instruction("Faire revenir les légumes", 5);
        plat.ajouterInstruction(instruction);
        instruction = new Instruction("Laisser mijoter jusqu'à cuisson du poulet", 50);

        // La recette ne compte que 2 instructions : la position 4 est hors bornes.
        try {
            plat.insererInstruction(4, instruction);
            System.out.println("ANOMALIE : insertion acceptée en position 4");
        } catch (IllegalArgumentException iae) {
            System.out.println("Insertion refusée en position 4 : la recette ne compte que 2 instructions");
        }

        plat.ajouterInstruction(instruction);
        instruction = new Instruction("Laisser légèrement refroidir", 3);
        plat.ajouterInstruction(instruction);
        instruction = new Instruction("Ajouter la crème et servir", 0);
        plat.ajouterInstruction(instruction);
        instruction = new Instruction("Laisser mijoter jusqu'à cuisson du poulet", 67);
        plat.remplacerInstruction(3, instruction);
        instruction = new Instruction("Ajouter le poulet", 0);
        plat.insererInstruction(3, instruction);
        plat.supprimerInstruction(5);

        // Le parcours complet amène l'itérateur sur la dernière instruction : la suppression qui suit
        // est donc un appel bien formé, et n'échoue que parce que la liste renvoyée est en lecture seule.
        Iterator<Instruction> instructionIterator = plat.instructions().iterator();
        while (instructionIterator.hasNext()) {
            instructionIterator.next();
        }

        try {
            instructionIterator.remove();
            System.out.println("ANOMALIE : suppression acceptée sur la liste des instructions");
        } catch (UnsupportedOperationException uoe) {
            System.out.println("Suppression refusée (comportement attendu) : "
                    + "la liste des instructions est en lecture seule");
        }

        System.out.println();

        Ingredient ingredient = new Ingredient("Blanc de poulet");
        plat.ajouterIngredient(ingredient, 400, Unite.GRAMME);
        ingredient = new Ingredient("Céleri");
        plat.ajouterIngredient(ingredient, 200, Unite.GRAMME);
        ingredient = new Ingredient("Carottes");
        plat.ajouterIngredient(ingredient, 2);
        ingredient = new Ingredient("jus de citron");
        plat.ajouterIngredient(ingredient, 10, Unite.MILLILITRE);
        ingredient = new Ingredient("Sel");
        plat.ajouterIngredient(ingredient, 1, Unite.PINCEE);
        ingredient = new Ingredient("Crème fraiche");
        plat.ajouterIngredient(ingredient, 10, Unite.CENTILITRE);

        plat.modifierIngredient(new Ingredient("Blanc de poulet"), 600, Unite.GRAMME);
        plat.supprimerIngredient(new Ingredient("jus de citron"));

        IngredientQuantifie ingredientQuantifie = plat.trouverIngredientQuantifie(new Ingredient("Blanc de poulet"));
        System.out.println("Quantité de blanc de poulet nécessaire : "
                + ingredientQuantifie.getQuantite() + " " + ingredientQuantifie.getUnite() + "\n");

        System.out.println(plat);

        // Question 7 : test du Livre (ajout/suppression de plats, tri par difficulté puis nom)
        Livre livre = new Livre();
        livre.ajouterPlat(plat);

        // Question 14 (partie optionnelle) : ingrédient (sel) partagé entre plats, pour tester compterUsagesIngredients
        Plat croquettes = new Plat("Croquettes au fromage", 4, Difficulte.XXX, Cout.$$, Plat.Type.ENTREE);
        croquettes.ajouterIngredient(new Ingredient("Sel"), 1, Unite.PINCEE);
        livre.ajouterPlat(croquettes);
        System.out.println(livre);
        livre.supprimerPlat(new Plat("Toasts aux champignons", 5, Difficulte.XXX, Cout.$$$, Plat.Type.ENTREE));
        System.out.println(livre);

        System.out.println("Usages des ingrédients :");
        Map<Ingredient, Integer> usages = livre.compterUsagesIngredients();
        for (Map.Entry<Ingredient, Integer> entry : usages.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }

        // Question 16 (partie optionnelle) : test du clonage profond de Plat (et superficiel d'Instruction)
        Plat copiePlat = plat.clone();
        copiePlat.ajouterInstruction(new Instruction("Étape ajoutée sur la copie", 2));
        copiePlat.modifierIngredient(new Ingredient("Sel"), 999, Unite.PINCEE);

        System.out.println("\nPlat original (ne doit pas avoir changé) :");
        System.out.println(plat);
        System.out.println("Plat copié (doit contenir la nouvelle étape et le sel modifié) :");
        System.out.println(copiePlat);
    }

}
