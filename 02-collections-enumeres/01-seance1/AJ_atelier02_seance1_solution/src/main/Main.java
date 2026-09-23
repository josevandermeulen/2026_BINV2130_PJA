package main;

import domaine.Ingredient;
import domaine.IngredientQuantifie;
import domaine.Instruction;
import domaine.Plat;
import domaine.Plat.Cout;
import domaine.Plat.Difficulte;
import domaine.Unite;

import java.util.Iterator;

/**
 * Programme de démonstration : construit un plat, exerce la gestion de sa recette et de ses
 * ingrédients, puis affiche sa fiche.
 *
 * Trois opérations sont volontairement invalides. Chacune affiche le refus qu'elle provoque :
 * c'est ainsi que se vérifie la validation des paramètres et l'encapsulation des collections.
 */
public class Main {

    public static void main(String[] args) {

        Plat plat = new Plat("Waterzooi", 4, Difficulte.XX, Cout.$$$);

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
    }

}
