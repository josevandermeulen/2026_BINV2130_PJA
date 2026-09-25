package domaine;

import util.Util;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Un plat de cuisine : sa fiche d'identité, ses ingrédients et sa recette.
 *
 * La durée du plat n'est jamais fixée directement : elle est recalculée à chaque ajout, retrait ou
 * remplacement d'instruction, et vaut toujours la somme des durées de la recette. Un ingrédient ne
 * peut figurer qu'une fois dans un plat, quelle que soit sa quantité.
 */
public class Plat implements Cloneable {

    /**
     * Le niveau de difficulté d'un plat, de `X` (le plus simple) à `XXXXX`, affiché
     * sous forme d'étoiles.
     */
    public enum Difficulte {
        X, XX, XXX, XXXX, XXXXX;
        /**
         * Renvoie le niveau sous forme d'étoiles.
         *
         * @return le niveau de difficulté en étoiles
         */
        @Override
        public String toString() {
            return super.toString().replace("X", "*");
        }
    }

    /**
     * Le coût d'un plat, de `$` (le plus économique) à `$$$$$`, affiché en euros.
     */
    public enum Cout {
        $, $$, $$$, $$$$, $$$$$;
        /**
         * Renvoie le coût sous forme de symboles euro.
         *
         * @return le coût en symboles euro
         */
        @Override
        public String toString() {
            return super.toString().replace("$", "€");
        }
    }

    // Question 1 : énuméré interne Type — l'attribut nom (avec getter) est ajouté à la Question 11
    /**
     * Le moment du repas auquel un plat se sert.
     *
     * L'ordre de déclaration est l'ordre de service : c'est lui que suit le classement des plats
     * dans un Livre.
     */
    public enum Type {
        ENTREE("Entrée"), PLAT("Plat"), DESSERT("Dessert");

        private String nom;

        /**
         * Crée un type de plat.
         *
         * @param nom le libellé affiché du type
         */
        Type(String nom) {
            this.nom = nom;
        }

        /**
         * Renvoie le libellé du type.
         *
         * @return le libellé du type
         */
        public String getNom() {
            return this.nom;
        }
    }
    // FinQuestion

    private final String nom;

    private int nbPersonnes;

    private Difficulte niveauDeDifficulte;

    private Cout cout;

    // Question 1 : attribut type (avec getter, sans setter)
    private Type type;
    // FinQuestion

    private Duration dureeEnMinutes = Duration.ofMinutes(0);

    private List<Instruction> recette = new ArrayList<>();

    // Question 18 : Map à la place du Set pour stocker les ingrédients (optionnelle)
    // Map<Ingredient, IngredientQuantifie> au lieu du Set<IngredientQuantifie> de la partie 1
    private Map<Ingredient, IngredientQuantifie> ingredients = new HashMap<>();
    // FinQuestion

    // Question 1 : ajout du paramètre type au constructeur
    /**
     * Crée un plat sans ingrédient ni instruction.
     *
     * @param nom                le nom du plat
     * @param nbPersonnes        le nombre de personnes servies, strictement positif
     * @param niveauDeDifficulte le niveau de difficulté du plat
     * @param cout               le coût du plat
     * @param type               le moment du repas auquel le plat se sert
     * @throws IllegalArgumentException si un paramètre est null ou vide, ou si le nombre de
     *                                  personnes n'est pas strictement positif
     */
    public Plat(String nom, int nbPersonnes, Difficulte niveauDeDifficulte, Cout cout, Type type) {
        Util.checkString(nom);
        Util.checkStrictlyPositive(nbPersonnes);
        Util.checkObject(niveauDeDifficulte);
        Util.checkObject(cout);
        Util.checkObject(type);

        this.nom = nom;
        this.nbPersonnes = nbPersonnes;
        this.niveauDeDifficulte = niveauDeDifficulte;
        this.cout = cout;
        this.type = type;
    }
    // FinQuestion

    /**
     * Renvoie le nom du plat.
     *
     * @return le nom du plat
     */
    public String getNom() {
        return nom;
    }

    /**
     * Renvoie le nombre de personnes que le plat sert.
     *
     * @return le nombre de personnes servies
     */
    public int getNbPersonnes() {
        return nbPersonnes;
    }

    /**
     * Renvoie le niveau de difficulté du plat.
     *
     * @return le niveau de difficulté
     */
    public Difficulte getNiveauDeDifficulte() {
        return niveauDeDifficulte;
    }

    /**
     * Renvoie le coût du plat.
     *
     * @return le coût du plat
     */
    public Cout getCout() {
        return cout;
    }

    // Question 1 : getter getType (pas de setter)
    /**
     * Renvoie le moment du repas auquel le plat se sert.
     *
     * @return le type du plat
     */
    public Type getType() {
        return type;
    }
    // FinQuestion

    /**
     * Renvoie la durée totale du plat, soit la somme des durées de ses instructions.
     *
     * @return la durée de préparation du plat
     */
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

    /**
     * Renvoie la recette du plat, dans l'ordre d'exécution.
     *
     * @return la liste non modifiable des instructions
     */
    public List<Instruction> instructions() {
        return Collections.unmodifiableList(recette);
    }

    // Question 16 (partie optionnelle) : clonage profond de Plat (implements Cloneable + clone())
    /**
     * Renvoie une copie profonde du plat.
     *
     * La recette et les ingrédients quantifiés sont dupliqués, et non partagés : modifier la
     * quantité d'un ingrédient de la copie laisse l'original intact. Les Ingredient
     * eux-mêmes restent partagés, étant immuables.
     *
     * @return une copie indépendante du plat
     */
    @Override
    public Plat clone() {
        try {
            Plat copie = (Plat) super.clone();
            copie.recette = new ArrayList<>();
            for (Instruction instruction : this.recette) {
                copie.recette.add(instruction.clone());
            }
            copie.ingredients = new HashMap<>();
            for (IngredientQuantifie iq : this.ingredients.values()) {
                copie.ingredients.put(iq.getIngredient(),
                        new IngredientQuantifie(iq.getIngredient(), iq.getQuantite(),
                                iq.getUnite()));
            }
            return copie;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
    // FinQuestion

    // (Question 14) vue non modifiable des ingrédients quantifiés — montrée en prose
    // (fiche) pour apparaître avant compterUsagesIngredients
    /**
     * Renvoie les ingrédients du plat avec leur quantité.
     *
     * @return l'ensemble non modifiable des ingrédients quantifiés
     */
    public Set<IngredientQuantifie> ingredientsQuantifies() {
        return Collections.unmodifiableSet(new HashSet<>(ingredients.values()));
    }

    // gestion des ingrédients

    // Question 18 : ajouterIngredient via la Map (modifierIngredient et supprimerIngredient sont analogues)
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

        ingredients.put(ingredient, new IngredientQuantifie(ingredient, quantite, unite));
        return true;
    }
    // FinQuestion

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
        if (trouverIngredientQuantifie(ingredient) == null) {
            return false;
        }

        return ingredients.remove(ingredient) != null;
    }

    // Question 18 : la recherche devient un simple get sur la Map
    /**
     * Cette méthode recherche et renvoie une copie de l'ingrédient quantifié
     * correspondant à l'ingrédient passé en paramètre.
     * @param ingredient l'ingrédient recherché
     * @return une copie de l'ingrédient quantifié correspondant s'il existe, null sinon
     * @throws IllegalArgumentException en cas de paramètre null
     */
    public IngredientQuantifie trouverIngredientQuantifie(Ingredient ingredient) {
        Util.checkObject(ingredient);

        return ingredients.get(ingredient);
    }
    // FinQuestion

    /**
     * Renvoie la fiche complète du plat : identité, durée, ingrédients et recette numérotée.
     *
     * @return la représentation textuelle du plat
     */
    @Override
    public String toString() {
        String hms = String.format("%d h %02d m", dureeEnMinutes.toHours(), dureeEnMinutes.toMinutesPart());
        String res = this.nom + "\n\n";
        res += "Pour " + this.nbPersonnes + " personnes\n";
        res += "Difficulté : " + this.niveauDeDifficulte + "\n";
        res += "Coût : " + this.cout + "\n";
        res += "Durée : " + hms + "\n\n";
        res += "Ingrédients :\n";
        for (IngredientQuantifie ing : this.ingredients.values()) {
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
