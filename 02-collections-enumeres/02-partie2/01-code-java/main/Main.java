package main;

import domaine.Instruction;
import domaine.Plat;
import domaine.Plat.Cout;
import domaine.Plat.Difficulte;

import java.util.Iterator;

public class Main {

    public static void main(String[] args) {

        Plat plat = null;
        plat = new Plat("Waterzooi", 4, Difficulte.XX, Cout.$$$);

        Instruction instruction = new Instruction("Couper les légumes", 15);
        try {
            plat.insererInstruction(0, instruction);
        } catch (IllegalArgumentException iae) {}
        plat.ajouterInstruction(instruction);
        instruction = new Instruction("Faire revenir les légumes", 5);
        plat.ajouterInstruction(instruction);
        instruction = new Instruction("Laisser mijoter jusqu'à cuisson du poulet", 50);
        try {
            plat.insererInstruction(4, instruction);
        } catch (IllegalArgumentException iae) {}
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

        Iterator<Instruction> instructionIterator = plat.instructions().iterator();
        while (instructionIterator.hasNext()) {
            instruction = instructionIterator.next();
        }
        try {
            instructionIterator.remove();
        } catch (UnsupportedOperationException uoe) {}

        System.out.println(plat);
    }

}
