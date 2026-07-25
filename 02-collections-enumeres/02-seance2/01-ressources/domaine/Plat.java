package domaine;

import util.Util;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Plat {

    public enum Difficulte {
        X, XX, XXX, XXXX, XXXXX;
        @Override
        public String toString() {
            return super.toString().replace("X", "*");
        }
    }

    public enum Cout {
        $, $$, $$$, $$$$, $$$$$;
        @Override
        public String toString() {
            return super.toString().replace("$", "€");
        }
    }

    private final String nom;

    private int nbPersonnes;

    private Difficulte niveauDeDifficulte;

    private Cout cout;

    private Duration dureeEnMinutes = Duration.ofMinutes(0);

    private List<Instruction> recette = new ArrayList<>();

    private Set<IngredientQuantifie> ingredients = new HashSet<>();

    public Plat(String nom, int nbPersonnes, Difficulte niveauDeDifficulte, Cout cout) {
        Util.checkString(nom);
        Util.checkStrictlyPositive(nbPersonnes);
        Util.checkObject(niveauDeDifficulte);
        Util.checkObject(cout);

        this.nom = nom;
        this.nbPersonnes = nbPersonnes;
        this.niveauDeDifficulte = niveauDeDifficulte;
        this.cout = cout;
    }

    public String getNom() {
        return nom;
    }

    public int getNbPersonnes() {
        return nbPersonnes;
    }

    public Difficulte getNiveauDeDifficulte() {
        return niveauDeDifficulte;
    }

    public Cout getCout() {
        return cout;
    }

    public Duration getDureeEnMinutes() {
        return dureeEnMinutes;
    }

    // gestion de la recette et de la dureeEnMinutes

    /**
     * Cette méthode insère l'instruction à la position précisée (la position commence à 1).
     * @param position la position à laquelle l'instruction doit être insérée
     * @param instruction l'instruction à insérer
     * @throws IllegalArgumentException en cas de position invalide ou d'instruction null
     */
    public void insererInstruction(int position, Instruction instruction) {
        Util.checkStrictlyPositive(position);
        Util.checkObject(instruction);
        if (position > recette.size() + 1) {
            throw new IllegalArgumentException();
        }

        recette.add(position - 1, instruction);
        dureeEnMinutes = dureeEnMinutes.plus(instruction.getDureeEnMinutes());
    }

    /**
     * Cette méthode ajoute l'instruction en fin de la liste.
     * @param instruction l'instruction à ajouter
     * @throws IllegalArgumentException en cas d'instruction null
     */
    public void ajouterInstruction(Instruction instruction) {
        Util.checkObject(instruction);

        recette.add(instruction);
        dureeEnMinutes = dureeEnMinutes.plus(instruction.getDureeEnMinutes());
    }

    /**
     * Cette méthode remplace l’instruction de la position précisée par celle en paramètre (la position commence à 1).
     * @param position la position de l'instruction à remplacer
     * @param instruction la nouvelle instruction
     * @return l'instruction remplacée
     * @throws IllegalArgumentException en cas de position invalide ou d'instruction null
     */
    public Instruction remplacerInstruction(int position, Instruction instruction) {
        Util.checkStrictlyPositive(position);
        Util.checkObject(instruction);
        if (position > recette.size()) {
            throw new IllegalArgumentException();
        }

        Instruction instructionRemplacee = recette.set(position - 1, instruction);
        dureeEnMinutes = dureeEnMinutes.minus(instructionRemplacee.getDureeEnMinutes());
        dureeEnMinutes = dureeEnMinutes.plus(instruction.getDureeEnMinutes());
        return instructionRemplacee;
    }

    /**
     * Cette méthode supprime l’instruction qui se trouve à la position précisée en
     * paramètre (la position commence à 1).
     * @param position la position de l'instruction à supprimer
     * @return l'instruction supprimée
     * @throws IllegalArgumentException en cas de position invalide
     */
    public Instruction supprimerInstruction(int position) {
        Util.checkStrictlyPositive(position);
        if (position > recette.size()) {
            throw new IllegalArgumentException();
        }

        Instruction instructionSupprimee = recette.remove(position - 1);
        dureeEnMinutes = dureeEnMinutes.minus(instructionSupprimee.getDureeEnMinutes());
        return instructionSupprimee;
    }

    public List<Instruction> instructions() {
        return Collections.unmodifiableList(recette);
    }

    /**
     * Dans le cas où l'ingrédient n'est pas encore présent, cette méthode crée et ajoute un ingrédient quantifié
     * avec comme quantité et comme unité les valeurs passées en paramètre.
     * @param ingredient l'ingrédient à ajouter
     * @param quantite la quantité désirée
     * @param unite l'unité de mesure
     * @return true si un ingrédient quantifié a été ajouté, false sinon.
     * @throws IllegalArgumentException en cas de paramètres invalides
     */
    public boolean ajouterIngredient(Ingredient ingredient, int quantite, Unite unite) {
        Util.checkObject(unite);
        Util.checkStrictlyPositive(quantite);
        if (trouverIngredientQuantifie(ingredient) != null) {
            return false;
        }

        ingredients.add(new IngredientQuantifie(ingredient, quantite, unite));
        return true;
    }

    /**
     * Dans le cas où l'ingrédient n'est pas encore présent, cette méthode crée et
     * ajoute un ingrédient quantifié avec la quantité passée en paramètre
     * et l'unité NEANT.
     * @param ingredient l'ingrédient à ajouter
     * @param quantite la quantité désirée
     * @return true si un ingrédient quantifié a été ajouté, false sinon.
     * @throws IllegalArgumentException en cas de paramètres invalides
     */
    public boolean ajouterIngredient(Ingredient ingredient, int quantite) {
        return ajouterIngredient(ingredient, quantite, Unite.NEANT);
    }

    /**
     * Cette méthode modifie la quantité et l'unité de l'ingrédient passé en paramètre si celui-ci est déjà présent.
     * @param ingredient l'ingrédient dont il faut modifier la quantité et l'unité
     * @param quantite la nouvelle quantité
     * @param unite la nouvelle unité
     * @return true si l'ingrédient est présent, false sinon
     * @throws IllegalArgumentException en cas de paramètres invalides
     */
    public boolean modifierIngredient(Ingredient ingredient, int quantite, Unite unite) {
        Util.checkObject(unite);
        Util.checkStrictlyPositive(quantite);
        IngredientQuantifie ingredientQuantifie = trouverIngredientQuantifie(ingredient);
        if (ingredientQuantifie == null) {
            return false;
        }

        ingredientQuantifie.setQuantite(quantite);
        ingredientQuantifie.setUnite(unite);
        return true;
    }

    /**
     * Cette méthode supprime, s'il existe, l'ingrédient quantifié correspondant à l'ingrédient passé en paramètre.
     * @param ingredient l'ingrédient à supprimer
     * @return true si une suppression a été effectuée, false sinon
     * @throws IllegalArgumentException en cas de paramètre invalide
     */
    public boolean supprimerIngredient(Ingredient ingredient) {
        IngredientQuantifie ingredientQuantifie = trouverIngredientQuantifie(ingredient);
        if (ingredientQuantifie == null) {
            return false;
        }

        return ingredients.remove(ingredientQuantifie);
    }

    /**
     * Cette méthode recherche et renvoie une copie de l'ingrédient quantifié
     * correspondant à l'ingrédient passé en paramètre.
     * @param ingredient l'ingrédient recherché
     * @return une copie de l'ingrédient quantifié correspondant s'il existe, null sinon
     * @throws IllegalArgumentException en cas de paramètre null
     */
    public IngredientQuantifie trouverIngredientQuantifie(Ingredient ingredient) {
        Util.checkObject(ingredient);

        for (IngredientQuantifie ingredientQuantifie : ingredients) {
            if (ingredientQuantifie.getIngredient().equals(ingredient)) {
                return ingredientQuantifie;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        String hms = String.format("%d h %02d m", dureeEnMinutes.toHours(), dureeEnMinutes.toMinutesPart());
        String res = this.nom + "\n\n";
        res += "Pour " + this.nbPersonnes + " personnes\n";
        res += "Difficulté : " + this.niveauDeDifficulte + "\n";
        res += "Coût : " + this.cout + "\n";
        res += "Durée : " + hms + "\n\n";
        res += "Ingrédients :\n";
        for (IngredientQuantifie ing : this.ingredients) {
            res += ing + "\n";
        }
        res += "\n";
        res += "Instructions :\n";
        int i = 1;
        for (Instruction instruction : this.recette) {
            res += i + ". " + instruction + "\n";
            i++;
        }
        return res;
    }
}
