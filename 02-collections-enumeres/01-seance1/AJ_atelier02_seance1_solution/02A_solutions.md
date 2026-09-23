# Atelier 2 : énumérés, collections – séance 1 : solutions

*Énoncé : [`02A_2_exercices.md`](../AJ_atelier02_seance1/02A_2_exercices.md) — théorie : [`02A_1_theorie.md`](../AJ_atelier02_seance1/02A_1_theorie.md).*

## L'énuméré `Unite`

### Question 1

**Énoncé :** Commencez par créer, dans le package `domaine`, l'énuméré `Unite`.

*l'énuméré Unite* — [`src/domaine/Unite.java`](src/domaine/Unite.java) :

```java
package domaine;

/**
 * Les unités de mesure utilisables pour quantifier un ingrédient.
 *
 * Chaque unité connaît son abréviation, seule forme affichée dans une recette. NEANT est
 * l'unité vide, pour les ingrédients qui se comptent sans unité (« 3 œufs »).
 */
public enum Unite {
    GRAMME("gr"), KILOGRAMME("kg"), LITRE("l"), MILLILITRE("ml"),
    CENTILITRE("cl"), DECILITRE("dl"), CUILLER_A_CAFE("cc"), CUILLER_A_THE("ct"),
    CUILLER_A_DESSERT("cd"), CUILLER_A_SOUPE("cs"), PINCEE("pincée"), UN_PEU("peu"),
    NEANT("");

    private String abreviation;

    /**
     * Crée une unité.
     *
     * @param abreviation l'abréviation affichée dans les recettes
     */
    Unite(String abreviation) {
        this.abreviation = abreviation;
    }

    /**
     * Renvoie l'abréviation de l'unité.
     *
     * @return l'abréviation de l'unité
     */
    @Override
    public String toString() {
        return abreviation;
    }
}
```

## La classe `Instruction`

### Question 2

**Énoncé :** Créez, dans le package `domaine`, la classe `Instruction`.

**pourquoi le constructeur ne passe pas par les setters**

Question classique en séance, héritée d'APOO : puisque `setDescription` refait exactement le `Util.checkString(description)` du constructeur, pourquoi ne pas écrire `setDescription(description)` et n'avoir la validation qu'à un seul endroit ? Pour la durée le gain serait même double, le constructeur recevant un `int` et le setter une `Duration` : `setDureeEnMinutes(Duration.ofMinutes(duree))` compile et apporte en prime le contrôle « minutes entières » que le constructeur ne fait pas.

La réponse est qu'appeler depuis un constructeur une méthode publique — donc redéfinissable — est un piège Java connu : si une sous-classe redéfinit `setDescription`, c'est **sa** version qui s'exécute, avant que ses propres attributs soient initialisés ; elle les verrait à leur valeur par défaut (`null`, `0`). Le problème est réel et pas théorique ici : `Instruction` n'est pas `final`, et le cours enseigne l'héritage.

Deux sorties propres existent, si l'on tient à supprimer la duplication : déclarer les setters `final`, ou extraire la validation dans une méthode `private` que le constructeur et le setter appellent tous les deux. Les deux sont correctes ; aucune ne vaut son poids sur une classe de deux attributs. Répéter une ligne de `Util.check*` reste la moins chère des trois options tant que la classe reste petite.

*la classe Instruction — attributs et constructeur (Duration.ofMinutes)* — [`src/domaine/Instruction.java`](src/domaine/Instruction.java) :

```java
package domaine;

/**
 * Une étape de la recette d'un plat : ce qu'il faut faire, et le temps que cela prend.
 *
 * La durée est exprimée en minutes entières ; c'est en additionnant celles de ses instructions
 * qu'un Plat obtient sa durée totale.
 */
public class Instruction {
    private String description;

    private Duration dureeEnMinutes;

    /**
     * Crée une instruction.
     *
     * @param description ce qu'il faut faire
     * @param duree       la durée de l'étape, en minutes ; positive ou nulle
     * @throws IllegalArgumentException si la description est null ou vide, ou si la durée est
     *                                  négative
     */
    public Instruction(String description, int duree) {
        Util.checkString(description);
        Util.checkPositiveOrNul(duree);

        this.description = description;
        this.dureeEnMinutes = Duration.ofMinutes(duree);
    }
    // ...
}
```

*setter avec validation d'une Duration en minutes entières* — [`src/domaine/Instruction.java`](src/domaine/Instruction.java) :

```java
public class Instruction {
    private Duration dureeEnMinutes;
    // ...
    /**
     * Modifie la durée de l'étape.
     *
     * @param dureeEnMinutes la nouvelle durée : positive ou nulle, et exprimée en minutes entières
     * @throws IllegalArgumentException si la durée est null, négative, ou comporte des secondes
     */
    public void setDureeEnMinutes(Duration dureeEnMinutes) {
        Util.checkObject(dureeEnMinutes);
        Util.checkPositiveOrNul(dureeEnMinutes.toMinutes());
        if (!dureeEnMinutes.equals(Duration.ofMinutes(dureeEnMinutes.toMinutes()))) {
            throw new IllegalArgumentException();
        }

        this.dureeEnMinutes = dureeEnMinutes;
    }
    // ...
}
```

*redéfinition de toString (le format HH:mm sur deux chiffres est peaufiné en Question 8)* — [`src/domaine/Instruction.java`](src/domaine/Instruction.java) :

```java
public class Instruction {
    private String description;
    private Duration dureeEnMinutes;
    // ...
    /**
     * Renvoie la durée au format `(hh:mm)` suivie de la description.
     *
     * @return la représentation textuelle de l'instruction
     */
    @Override
    public String toString() {
        return "(" + String.format("%02d:%02d", dureeEnMinutes.toHours(),
                dureeEnMinutes.toMinutesPart()) + ") " + description;
    }

}
```

## La classe `Plat`

### Question 3

**Énoncé :** Créez la classe `Plat` dans le package `domaine`. La classe `Plat` définit en interne deux énumérés : `Difficulte` et `Cout`.

*la classe Plat — déclaration et énumérés internes Difficulte et Cout* — [`src/domaine/Plat.java`](src/domaine/Plat.java) :

```java
package domaine;

/**
 * Un plat de cuisine : sa fiche d'identité, ses ingrédients et sa recette.
 *
 * La durée du plat n'est jamais fixée directement : elle est recalculée à chaque ajout, retrait ou
 * remplacement d'instruction, et vaut toujours la somme des durées de la recette. Un ingrédient ne
 * peut figurer qu'une fois dans un plat, quelle que soit sa quantité.
 */
public class Plat {

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
    // ...
}
```

*attributs, collections et constructeur* — [`src/domaine/Plat.java`](src/domaine/Plat.java) :

```java
public class Plat {
    // ...
    private final String nom;

    private int nbPersonnes;

    private Difficulte niveauDeDifficulte;

    private Cout cout;

    private Duration dureeEnMinutes = Duration.ofMinutes(0);

    private List<Instruction> recette = new ArrayList<>();

    private Set<IngredientQuantifie> ingredients = new HashSet<>();

    /**
     * Crée un plat sans ingrédient ni instruction.
     *
     * @param nom                le nom du plat
     * @param nbPersonnes        le nombre de personnes servies, strictement positif
     * @param niveauDeDifficulte le niveau de difficulté du plat
     * @param cout               le coût du plat
     * @throws IllegalArgumentException si un paramètre est null ou vide, ou si le nombre de
     *                                  personnes n'est pas strictement positif
     */
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
    // ...
}
```

*gestion des instructions (insérer, ajouter, remplacer, supprimer) et consultation non modifiable* — [`src/domaine/Plat.java`](src/domaine/Plat.java) :

```java
public class Plat {
    // ...
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
    // ...
}
```

## La méthode `toString` de `Plat`

### Question 4

**Énoncé :** Ajoutez la méthode `toString` dans la classe `Plat` en vous basant sur l'exemple de sortie attendu (`affichage_Main.txt`), en laissant de côté la partie relative aux ingrédients : elle sera traitée à la question sur la gestion des ingrédients. À ce stade, […]

*toString de Plat (à ce stade, sans la partie ingrédients — traitée à la Question 6)* — [`src/domaine/Plat.java`](src/domaine/Plat.java) :

```java
public class Plat {
    private final String nom;
    private int nbPersonnes;
    private Difficulte niveauDeDifficulte;
    private Cout cout;
    // ...
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
```

## Le programme de démonstration

### Question 5

**Énoncé :** Copiez la classe `Main` fournie dans votre projet (à vous de voir dans quel package la mettre). Elle exerce toute l'API du plat, gestion des ingrédients comprise : elle ne compilera donc qu'une fois la question suivante terminée. Son exécution et la […]

**exécution du Main fourni**

Rien à coder ici : copiez la classe `Main` fournie dans le package `main`. Elle exerce toute l'API du plat, gestion des ingrédients comprise, et ne compile donc qu'une fois la Question 6 terminée — son exécution et la comparaison à `affichage_Main.txt` sont l'objet de la section `Test`, en fin d'énoncé. Tant que la Question 8 n'est pas faite, le format des durées (`1 h 27 m` au lieu de `HH:mm`) diffère de l'attendu — c'est normal.

## Gestion des ingrédients dans `Plat`

### Question 6

**Énoncé :** Ajoutons maintenant des fonctionnalités de gestion des ingrédients à la classe `Plat`.

**l'unicité est portée par le `HashSet`, pas par un parcours**

`IngredientQuantifie` (fournie) redéfinit `equals` et `hashCode` sur son **seul** ingrédient. Le `HashSet<IngredientQuantifie>` du plat refuse donc lui-même un ingrédient déjà présent, et `ajouterIngredient` se réduit à une ligne, `add` renvoyant exactement le booléen attendu :

```java
return ingredients.add(new IngredientQuantifie(ingredient, quantite, unite));
```

Pourquoi sur l'ingrédient seul, et pas sur les trois attributs ? Parce que `quantite` et `unite` ont des setters, et que `modifierIngredient` s'en sert sur un élément **déjà rangé** dans le `HashSet`. Un `hashCode` calculé sur eux changerait après le rangement : l'objet resterait dans le seau choisi à l'insertion, mais serait cherché dans un autre, et deviendrait introuvable — `contains` et `remove` échoueraient sur un élément pourtant présent. C'est le piège classique du champ mutable dans une clé de hachage. `ingredient`, lui, n'a pas de setter : l'égalité repose sur la seule partie stable de l'objet.

La contrepartie à connaître : deux ingrédients quantifiés du même ingrédient sont égaux même si leurs quantités diffèrent. C'est voulu — au sein d'un plat, un ingrédient n'apparaît qu'une fois, c'est donc lui qui identifie l'association —, mais ça n'a de sens que dans ce cadre : réunir dans un même ensemble les ingrédients quantifiés de deux plats différents ferait disparaître l'un des deux.

Même principe qu'à la semaine 1, où `Etudiant.equals` porte sur le seul matricule : l'égalité se définit sur ce qui identifie l'objet, pas sur tout ce qu'il contient.

*gestion des ingrédients (ajouter x2, modifier, supprimer, trouver)* — [`src/domaine/Plat.java`](src/domaine/Plat.java) :

```java
public class Plat {
    // ...
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
        // IngredientQuantifie compare sur son seul ingrédient : le Set refuse donc lui-même un
        // ingrédient déjà présent, et add renvoie exactement le booléen attendu.
        return ingredients.add(new IngredientQuantifie(ingredient, quantite, unite));
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
    // ...
}
```

## Parties optionnelles

### Création d'un `SortedSet` en utilisant un `Comparator`

#### Question 7

**Énoncé :** Ajoutez, dans la classe `Plat`, la méthode :

*ensemble trié des ingrédients via un Comparator anonyme* — [`src/domaine/Plat.java`](src/domaine/Plat.java) :

```java
public class Plat {
    private final String nom;
    // ...
    /**
     * Renvoie les ingrédients du plat, sans leur quantité, triés par nom.
     *
     * @return l'ensemble trié des ingrédients du plat
     */
    public SortedSet<Ingredient> ingredients() {
        SortedSet<Ingredient> ingredientsTries = new TreeSet<>(new Comparator<Ingredient>() {
            @Override
            public int compare(Ingredient ingredient1, Ingredient ingredient2) {
                return ingredient1.getNom().compareTo(ingredient2.getNom());
            }
        });
        for (IngredientQuantifie ingredientQuantifie : ingredients) {
            ingredientsTries.add(ingredientQuantifie.getIngredient());
        }
        return ingredientsTries;
    }
    // ...
}
```

### Formatage de l'affichage des durées

#### Question 8

**Énoncé :** Dans la méthode `toString` de la classe `Instruction`, utilisez `String.format` afin d'avoir la durée au format `HH:mm`.

**format HH:mm de la durée**

La solution fournie applique déjà ce format dans le `toString` d'`Instruction` (extrait à la Question 2) : `String.format("%02d:%02d", dureeEnMinutes.toHours(), dureeEnMinutes.toMinutesPart())`. Les `%02d` forcent l'affichage de chaque champ sur deux chiffres — `01:07` plutôt que `1:7`. C'est le seul changement demandé par la Question 8 par rapport au `toString` simple écrit à la Question 2.

---

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
