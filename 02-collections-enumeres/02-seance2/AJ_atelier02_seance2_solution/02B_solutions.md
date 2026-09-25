# Atelier 2 : énumérés, collections – séance 2 : solutions

*Énoncé : [`02B_2_exercices.md`](../AJ_atelier02_seance2/02B_2_exercices.md) — théorie : [`02B_1_theorie.md`](../AJ_atelier02_seance2/02B_1_theorie.md).*

## L'énuméré `Type`

### Question 1

**Énoncé :** Pour pouvoir trier les plats par type (entrée, plat, dessert) dans notre livre, il va falloir créer cette notion de « type de plat » dans notre application. Comme les types sont bien définis et a priori invariables, on pense immédiatement à un énuméré. […]

*énuméré interne Type — l'attribut nom (avec getter) est ajouté à la Question 11* — [`src/domaine/Plat.java`](src/domaine/Plat.java) :

```java
public class Plat implements Cloneable {
    private final String nom;
    private Type type;
    // ...
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
    // ...
}
```

*attribut type (avec getter, sans setter)* — [`src/domaine/Plat.java`](src/domaine/Plat.java) :

```java
public class Plat implements Cloneable {
    // ...
    private Type type;
    // ...
}
```

*ajout du paramètre type au constructeur* — [`src/domaine/Plat.java`](src/domaine/Plat.java) :

```java
public class Plat implements Cloneable {
    private final String nom;
    private int nbPersonnes;
    private Difficulte niveauDeDifficulte;
    private Cout cout;
    private Type type;
    // ...
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
    // ...
}
```

*getter getType (pas de setter)* — [`src/domaine/Plat.java`](src/domaine/Plat.java) :

```java
public class Plat implements Cloneable {
    private Type type;
    // ...
    /**
     * Renvoie le moment du repas auquel le plat se sert.
     *
     * @return le type du plat
     */
    public Type getType() {
        return type;
    }
    // ...
}
```

*passage du type de plat au constructeur* — [`src/main/Main.java`](src/main/Main.java) :

```java
public class Main {
    // ...
    public static void main(String[] args) {
        // ...
        Plat plat = new Plat("Waterzooi", 4, Difficulte.XX, Cout.$$$, Plat.Type.PLAT);
        // ...
    }
}
```

## La classe `Livre`

### Question 2

**Énoncé :** Créez une classe `Livre` dans le package `domaine`. Elle reste vide pour l'instant, on va réfléchir à ce qu'on met dedans ensuite.

**la classe Livre créée vide**

À ce stade, `Livre` est une simple classe vide dans le package `domaine` — on
lui ajoute sa structure de données et ses méthodes à partir de la Question 5.
La solution ne conserve que l'état final : voir `Livre.java`.

### Question 3

**Énoncé :** Relisez bien l'introduction… Quelle est la structure de données qui nous conviendrait ici ? Justifiez votre choix.

**quelle structure de données pour le livre ?**

L'introduction donne deux contraintes. D'abord « les recettes seront triées par ordre de difficulté » : on parle de tri, il faut donc retenir un ordre. Une simple liste conserverait l'ordre d'insertion, mais elle accepte les doublons — or un livre de recettes n'en a pas. Un `Set` interdit les doublons mais n'est pas trié. La bonne base est donc un `SortedSet` : un ensemble trié, sans doublons.

Ensuite « pouvoir récupérer facilement tous les plats d'un certain type » : un unique `SortedSet` ne permet pas de retrouver rapidement tous les plats d'un type donné. C'est là qu'une `Map` aide : une `Map<Type, SortedSet<Plat>>`, dont la clé est le type de plat et la valeur l'ensemble trié des plats de ce type. Avec 3 types, la `Map` contient 3 `SortedSet<Plat>`.

### Question 4

**Énoncé :** Une fois la structure choisie, pourquoi ne pas faire juste 3 attributs `SortedSet<Plat>` plutôt qu'une `Map` ?

**pourquoi une Map plutôt que 3 attributs SortedSet ?**

Avec trois attributs `SortedSet<Plat>` (un par type), chaque méthode du livre (`ajouterPlat`, `supprimerPlat`, `toString`, …) devrait dupliquer sa logique trois fois — ou enchaîner des `if`/`switch` sur le type. Et si un quatrième type de plat apparaît un jour (une soupe ? un amuse-bouche ?), il faudrait ajouter un attribut **et** modifier toutes ces méthodes. Avec la `Map<Plat.Type, SortedSet<Plat>>`, le type est une simple clé : les méthodes s'écrivent une seule fois (`plats.get(type)`), et un nouveau type de plat ne demande aucun changement dans `Livre`.

## Construire le livre

### Question 5

**Énoncé :** Créez l'attribut `plats` dans la classe `Livre` : une `Map` dont la clé est le type de plat, et la valeur un ensemble trié (`SortedSet<Plat>`) des plats de ce type. Si on a 3 types de plat, la `Map` contiendra donc 3 `SortedSet<Plat>`.

*la classe Livre — le SortedMap règle aussi la Question 21 (ordre de* — [`src/domaine/Livre.java`](src/domaine/Livre.java) :

```java
package domaine;

/**
 * Un livre de cuisine : les plats, rangés par type puis triés au sein de chaque type.
 *
 * Les types se suivent dans l'ordre de service (Plat.Type), et les plats d'un même type
 * sont classés par difficulté croissante puis par nom. Un plat ne peut y figurer deux fois : deux
 * plats de même type, même difficulté et même nom sont considérés comme le même.
 */
public class Livre {

    private SortedMap<Plat.Type, SortedSet<Plat>> plats = new TreeMap<>();
    // ...
}
```

*ajouterPlat (le Comparator interne répond aux Questions 6 à 9)* — [`src/domaine/Livre.java`](src/domaine/Livre.java) :

```java
public class Livre {
    // ...
    /**
     * Ajoute un plat dans le livre, s'il n'existe pas déjà dedans.
     * Il faut ajouter correctement le plat en fonction de son type.
     * @param plat le plat à ajouter
     * @return true si le plat a été ajouté, false sinon.
     */
    public boolean ajouterPlat(Plat plat) {
        Util.checkObject(plat);

        SortedSet<Plat> plats = this.plats.get(plat.getType());
        // Si c'est le premier plat de ce type, on crée le SortedSet pour ce type dans la Map.
        if (plats == null) {
            // Comparator trié par difficulté puis nom (corrige le ClassCastException
            // et le faux-doublon détecté quand le tri ne portait que sur la difficulté)
            plats = new TreeSet<>(new Comparator<Plat>() {
                @Override
                public int compare(Plat o1, Plat o2) {
                    int comp = o1.getNiveauDeDifficulte().compareTo(o2.getNiveauDeDifficulte());
                    if (comp == 0) {
                        return o1.getNom().compareTo(o2.getNom());
                    }
                    return comp;
                }
            });
            this.plats.put(plat.getType(), plats);
        }

        // On ajoute dans le SortedSet
        return plats.add(plat);
    }
    // ...
}
```

*supprimerPlat* — [`src/domaine/Livre.java`](src/domaine/Livre.java) :

```java
public class Livre {
    // ...
    /**
     * Supprime un plat du livre, s'il est dedans.
     * Si le plat supprimé est le dernier de ce type de plat, il faut supprimer ce type de
     * plat de la Map.
     * @param plat le plat à supprimer
     * @return true si le plat a été supprimé, false sinon.
     */
    public boolean supprimerPlat(Plat plat) {
        Util.checkObject(plat);

        SortedSet<Plat> plats = this.plats.get(plat.getType());
        if (plats == null) {
            return false;
        }

        boolean deleted = plats.remove(plat);
        if (deleted && plats.isEmpty()) {
            this.plats.remove(plat.getType());
        }
        return deleted;
    }
    // ...
}
```

*affichage de la table des matières (toString), avec un StringBuilder* — [`src/domaine/Livre.java`](src/domaine/Livre.java) :

```java
public class Livre {
    // ...
    /**
     * Renvoie la table des matières du livre : chaque type, souligné, suivi du nom de ses plats.
     *
     * @return la table des matières du livre
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Map.Entry<Plat.Type, SortedSet<Plat>> entry : this.plats.entrySet()) {
            str.append(entry.getKey().getNom() + "\n=====\n");
            for (Plat plat : entry.getValue()) {
                str.append(plat.getNom() + "\n");
            }
        }
        return str.toString();
    }

}
```

## Trier les plats du livre

### Question 6

**Énoncé :** Quelle solution vous semble la plus adaptée ici ?

**Comparable ou Comparator ?**

Ici, le `Comparator` est préférable. Si on laissait `Plat` implémenter `Comparable`, la classe `Plat` déciderait une fois pour toutes de son ordre naturel (par difficulté). Mais imaginons un second type de livre trié par coût : on ne pourrait plus, car `compareTo` est unique par classe. De plus, changer ce `compareTo` influencerait toutes les structures qui reposent sur l'ordre naturel de `Plat`. En donnant un `Comparator` au `TreeSet`, c'est la classe `Livre` qui porte la responsabilité de l'ordre : on peut définir plusieurs ordres différents sans toucher à `Plat`, et personne ne risque de casser ce tri en modifiant `Plat`.

### Question 7

**Énoncé :** Adaptez le `main` pour tester `toString`, `ajouterPlat` et `supprimerPlat`. Ajoutez ceci à la fin de la méthode `main` :

**le Comparator passé au TreeSet**

Le `ClassCastException` vient du `TreeSet` : sans `Comparator`, il exige que
ses éléments soient `Comparable`, ce que `Plat` n'est pas. On fournit donc un
`Comparator<Plat>` (classe anonyme) à la construction du `TreeSet`, dans
`ajouterPlat` (extrait à la Question 5).

*test du Livre (ajout/suppression de plats, tri par difficulté puis nom)* — [`src/main/Main.java`](src/main/Main.java) :

```java
public class Main {
    // ...
    public static void main(String[] args) {
        // ...
        Livre livre = new Livre();
        livre.ajouterPlat(plat);
        // ...
    }
}
```

### Question 8

**Énoncé :** Modifiez votre méthode `main` avec la création du livre, pour y mettre ce code-ci :

**que va afficher le bout de code ?**

Le code ajoute deux entrées (« Waterzooi » n'est pas une entrée : il finit dans `PLAT` ; « Croquettes au fromage » est une `ENTREE`), puis tente de supprimer un plat qui n'a jamais été ajouté (« Toasts aux champignons »). On s'attend à ce que ce `supprimerPlat` ne fasse rien. Or, avec un `compare` qui ne trie que sur la difficulté, « Toasts aux champignons » (difficulté `XXX`, type `ENTREE`) est vu comme **identique** aux « Croquettes au fromage » (même difficulté `XXX`, même type `ENTREE`) : les croquettes disparaissent alors du second affichage, ce qui surprend.

### Question 9

**Énoncé :** Une fois que vous avez une idée du résultat attendu, exécutez-le. Si vous vous êtes trompés, pourquoi est-ce que ça affiche ce résultat-là ?

**pourquoi ce résultat ?**

Dans un `TreeSet`, ce n'est pas `equals` qui décide de l'identité de deux éléments, mais la méthode de comparaison : si `compare(a, b)` renvoie 0, les deux éléments sont considérés comme égaux (pour l'ajout, mais aussi pour `contains` et `remove`). En ne triant que sur la difficulté, deux plats de même difficulté sont déclarés égaux, donc `supprimerPlat` retire le mauvais plat. La correction consiste à départager les plats distincts : trier d'abord par difficulté, puis par nom. Deux plats ne sont alors identiques que s'ils ont la même difficulté **et** le même nom.

## Améliorer le `Livre`

### Question 10

**Énoncé :** Ajoutez les méthodes suivantes dans la classe `Livre` :

*opérations avancées du Livre (getPlatsParType, contient)* — [`src/domaine/Livre.java`](src/domaine/Livre.java) :

```java
public class Livre {
    // ...
    /**
     * Renvoie un ensemble contenant tous les plats d'un certain type.
     * L'ensemble n'est pas modifiable.
     * @param type le type de plats souhaité
     * @return l'ensemble des plats
     */
    public SortedSet<Plat> getPlatsParType(Plat.Type type) {
        // L'ensemble renvoyé ne doit pas être modifiable !
        Util.checkObject(type);

        SortedSet<Plat> platsDuType = this.plats.get(type);
        if (platsDuType == null) {
            return null;
        }

        return Collections.unmodifiableSortedSet(platsDuType);
    }

    /**
     * Renvoie true si le livre contient le plat passé en paramètre. False sinon.
     * Pour cette recherche, un plat est identique à un autre si son type, son niveau de
     * difficulté et son nom sont identiques.
     * @param plat le plat à rechercher
     * @return true si le livre contient le plat, false sinon.
     */
    public boolean contient(Plat plat) {
        // Ne pas utiliser 2 fois la méthode get() de la map, et ne pas déclarer de variable locale !
        Util.checkObject(plat);

        if (this.plats.containsKey(plat.getType())) {
            return this.plats.get(plat.getType()).contains(plat);
        }
        return false;
    }
    // ...
}
```

*tousLesPlats — la version fournie trie déjà (Type, Difficulté, Nom),* — [`src/domaine/Livre.java`](src/domaine/Livre.java) :

```java
public class Livre {
    // ...
    /**
     * Renvoie un ensemble contenant tous les plats du livre, triés par type, puis par niveau de
     * difficulté, et enfin par nom.
     * @return l'ensemble de tous les plats du livre.
     */
    public Set<Plat> tousLesPlats() {
        Set<Plat> plats = new TreeSet<>(new Comparator<Plat>() {
            @Override
            public int compare(Plat o1, Plat o2) {
                int comp = o1.getType().compareTo(o2.getType());
                if (comp == 0) {
                    comp = o1.getNiveauDeDifficulte().compareTo(o2.getNiveauDeDifficulte());
                }
                if (comp == 0) {
                    comp = o1.getNom().compareTo(o2.getNom());
                }
                return comp;
            }
        });
        // Ne pas utiliser la méthode keySet() ou entrySet() ici !
        for (Set<Plat> platsDunType : this.plats.values()) {
            plats.addAll(platsDunType);
        }
        return plats;
    }
    // ...
}
```

## Améliorer l'énuméré `Type` de plat

### Question 11

**Énoncé :** Lors de l'affichage du livre, on affiche directement le nom de l'énuméré. Il est donc affiché en majuscules.

**nom d'affichage de l'énuméré Type**

L'énuméré `Type` reçoit un attribut `nom` (avec getter, sans setter), initialisé
pour chaque constante avec son libellé lisible (`Entrée`, `Plat`, `Dessert`).
L'énuméré complet est montré à la Question 1 ; le `toString` de `Livre` l'exploite
via `getNom()` (extrait à la Question 5) pour afficher `Entrée` au lieu de `ENTREE`.

### Question 12

**Énoncé :** Exécutez la classe `Main` (package `main`). La sortie attendue se trouve dans `AJ_atelier02_seance2/affichage_Main.txt`, à l'ordre des ingrédients près (il dépend du `HashSet` utilisé). Votre sortie doit correspondre jusqu'à la ligne `Usages des […]

**exécution du Main fourni**

Rien à coder ici : exécutez la classe `Main` (package `main`) et comparez sa
sortie à `AJ_atelier02_seance2/affichage_Main.txt`, jusqu'à la ligne `Usages des
ingrédients :` exclue (l'ordre des ingrédients dépend du `HashSet` et peut
varier ; tout ce qui suit provient de la Question 14, optionnelle).

## Parties optionnelles

### Wrapper, autoboxing et clonage

#### Question 13

**Énoncé :** Avant de continuer, testez ce petit bout de code dans un `main` (peu importe où) :

**pourquoi les deux affichages == sont-ils différents ?**

Un `Integer` est un objet (wrapper autour d'un `int`). Écrire `Integer a = 127;` déclenche de l'autoboxing : Java appelle en réalité `Integer.valueOf(127)`. Or `Integer.valueOf` garde en cache les instances pour les valeurs entre -128 et 127 et renvoie toujours la même instance pour une même valeur dans cet intervalle. C'est pour ça que `a == b` est vrai : `a` et `b` pointent vers le même objet en cache.

En dehors de cet intervalle (comme 200), `valueOf` crée une nouvelle instance à chaque appel. `c` et `d` sont donc deux objets distincts, et `==` compare des références différentes : le résultat est `false`.

Conclusion : ne jamais comparer des wrappers (`Integer`, `Long`, …) avec `==`. Il faut utiliser `equals` (ou `.intValue()` pour comparer les valeurs primitives).

#### Question 14

**Énoncé :** Ajoutez d'abord dans la classe `Plat` le getter suivant :

**vue non modifiable des ingrédients quantifiés**

On ajoute d'abord, dans `Plat`, le getter qui expose les ingrédients quantifiés sans laisser modifier la collection interne :

```java
public Set<IngredientQuantifie> ingredientsQuantifies() {
    return Collections.unmodifiableSet(ingredients);
}
```

C'est ce getter que `compterUsagesIngredients` (dans `Livre`, ci-dessous) parcourt pour chaque plat.

À ce stade, `ingredients` est encore le `Set<IngredientQuantifie>` de la séance 1. Après le passage
à la `Map` (Question 18), `values()` renvoie une `Collection` et non un `Set` : le corps devient
`return Collections.unmodifiableSet(new HashSet<>(ingredients.values()));` — c'est cette version-là
que porte [`src/domaine/Plat.java`](src/domaine/Plat.java). La signature, elle, ne bouge pas : le
`Livre` qui l'appelle ignore comment le plat range ses ingrédients.

*comptage des usages d'un ingrédient dans le livre* — [`src/domaine/Livre.java`](src/domaine/Livre.java) :

```java
public class Livre {
    // ...
    /**
     * Compte, pour chaque ingrédient utilisé dans au moins un plat du livre,
     * dans combien de plats il apparaît.
     * @return une Map associant chaque ingrédient utilisé à son nombre d'usages.
     */
    public Map<Ingredient, Integer> compterUsagesIngredients() {
        Map<Ingredient, Integer> compteur = new HashMap<>();
        for (Plat plat : tousLesPlats()) {
            for (IngredientQuantifie iq : plat.ingredientsQuantifies()) {
                compteur.put(iq.getIngredient(), compteur.getOrDefault(iq.getIngredient(), 0) + 1);
            }
        }
        return compteur;
    }
    // ...
}
```

*ingrédient (sel) partagé entre plats, pour tester compterUsagesIngredients* — [`src/main/Main.java`](src/main/Main.java) :

```java
public class Main {
    // ...
    public static void main(String[] args) {
        // ...
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
        // ...
    }
}
```

#### Question 15

**Énoncé :** Faites implémenter `Cloneable` à la classe `Instruction`, et redéfinissez la méthode :

***clonage superficiel** d'Instruction (implements Cloneable + clone())* — [`src/domaine/Instruction.java`](src/domaine/Instruction.java) :

```java
public class Instruction implements Cloneable {
    // ...
    /**
     * Renvoie une copie superficielle de l'instruction.
     *
     * La copie suffit ici : les deux attributs, String et Duration, sont
     * immuables — les partager entre l'original et sa copie est sans risque.
     *
     * @return une copie de l'instruction
     */
    @Override
    public Instruction clone() {
        try {
            return (Instruction) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

}
```

#### Question 16

**Énoncé :** Faites implémenter `Cloneable` à la classe `Plat`, et redéfinissez `clone` pour renvoyer une **copie profonde** :

*clonage profond de Plat (implements Cloneable + clone())* — [`src/domaine/Plat.java`](src/domaine/Plat.java) :

> ⚠️ **Extrait montré dans son état à ce stade** : `ingredients` est encore le `Set<IngredientQuantifie>` de la séance 1. Le fichier lié, lui, porte l'état final, atteint à la **Question 18** — où il est repris.

```java
public class Plat implements Cloneable {
    // ...
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
            copie.ingredients = new HashSet<>();
            for (IngredientQuantifie iq : this.ingredients) {
                copie.ingredients.add(new IngredientQuantifie(iq.getIngredient(),
                        iq.getQuantite(), iq.getUnite()));
            }
            return copie;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
    // ...
}
```

*test du clonage profond de Plat (et superficiel d'Instruction)* — [`src/main/Main.java`](src/main/Main.java) :

```java
public class Main {
    // ...
    public static void main(String[] args) {
        // ...
        Plat copiePlat = plat.clone();
        copiePlat.ajouterInstruction(new Instruction("Étape ajoutée sur la copie", 2));
        copiePlat.modifierIngredient(new Ingredient("Sel"), 999, Unite.PINCEE);

        System.out.println("\nPlat original (ne doit pas avoir changé) :");
        System.out.println(plat);
        System.out.println("Plat copié (doit contenir la nouvelle étape et le sel modifié) :");
        System.out.println(copiePlat);
    }

}
```

#### Question 17

**Énoncé :** Si vous n'aviez pas redéfini `clone` dans `Plat` pour dupliquer la liste et l'ensemble (c.-à-d. si vous vous étiez contenté du `clone` par défaut de `Object`), qu'aurait affiché ce test ?

**sans redéfinition de clone dans Plat, qu'aurait affiché le test ?**

`Object.clone` fait une copie superficielle : il copie la valeur de chaque champ, y compris les références. La `List<Instruction>` et le `Set<IngredientQuantifie>` de la copie pointeraient donc vers exactement les **mêmes** objets que l'original. Ajouter une instruction ou modifier un ingrédient sur la copie aurait donc aussi modifié l'original — silencieusement, puisqu'aucune exception ne se produit. C'est le piège classique du clone superficiel appliqué à des champs mutables.

### Ingrédients : changement de structure de données

#### Question 18

**Énoncé :** Modifiez votre implémentation en utilisant une collection `Map<Ingredient, IngredientQuantifie>` dans la classe `Plat` au lieu de la collection `Set<IngredientQuantifie>` pour stocker les ingrédients. Après ce changement, l'ordre d'affichage des […]

*Map à la place du Set pour stocker les ingrédients (optionnelle)* — [`src/domaine/Plat.java`](src/domaine/Plat.java) :

```java
public class Plat implements Cloneable {
    // ...
    private Map<Ingredient, IngredientQuantifie> ingredients = new HashMap<>();
    // ...
}
```

*ajouterIngredient via la Map (modifierIngredient et supprimerIngredient sont analogues)* — [`src/domaine/Plat.java`](src/domaine/Plat.java) :

```java
public class Plat implements Cloneable {
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
        Util.checkObject(unite);
        Util.checkStrictlyPositive(quantite);
        if (trouverIngredientQuantifie(ingredient) != null) {
            return false;
        }

        ingredients.put(ingredient, new IngredientQuantifie(ingredient, quantite, unite));
        return true;
    }
    // ...
}
```

*la recherche devient un simple get sur la Map* — [`src/domaine/Plat.java`](src/domaine/Plat.java) :

```java
public class Plat implements Cloneable {
    // ...
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
    // ...
}
```

*clonage profond de Plat, mis à jour pour la `Map`* — [`src/domaine/Plat.java`](src/domaine/Plat.java) :

```java
public class Plat implements Cloneable {
    // ...
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
    // ...
}
```

### `StringBuilder`

#### Question 19

**Énoncé :** Renseignez-vous sur la classe `StringBuilder`, et utilisez-la à bon escient dans la méthode `toString` de la classe `Livre`.

**StringBuilder dans le toString de Livre**

Concaténer des `String` dans une boucle crée un nouvel objet `String` à
chaque `+=` (les `String` sont immuables) : le coût devient quadratique.
`StringBuilder` accumule dans un tampon mutable (`append`) et ne construit la
`String` finale qu'une fois, au `toString()`. La solution l'utilisait dès le
départ : voir le `toString` de `Livre.java` (extrait à la Question 5).

### Trier tous les plats

#### Question 20

**Énoncé :** Dans l'énoncé, on vous demande de ne pas trier les plats de la méthode `tousLesPlats`. Pour cet exercice, on vous demande de quand même trier cet ensemble, en faisant en sorte qu'ils soient triés par Type, puis par Niveau de difficulté, et enfin par Nom.

**tousLesPlats trié**

La méthode `tousLesPlats` fournie trie déjà l'ensemble renvoyé (par Type, puis
Difficulté, puis Nom) au moyen d'un `TreeSet` avec `Comparator`, plutôt que le
simple `Set` non trié demandé à la Question 10 : c'est exactement le tri
demandé ici.

### `Map` triée ?

#### Question 21

**Énoncé :** On souhaiterait faire en sorte que lorsqu'on appelle la méthode `keySet` de la `Map` des plats de la classe `Livre`, on obtienne les clefs dans un ordre bien précis. À savoir, par ordre de service dans un restaurant. D'abord les entrées, puis les plats, […]

**keySet dans l'ordre de service**

Deux ingrédients de solution (voir la déclaration de l'attribut `plats` dans
`Livre.java`) :

1. remplacer la `Map` par une `SortedMap` (implémentée par `TreeMap`) : son
   `keySet` renvoie les clés triées ;
2. les clés sont des `Plat.Type`, et tout énuméré est naturellement
   `Comparable` dans l'ordre de déclaration de ses constantes — il suffit
   donc de déclarer `ENTREE, PLAT, DESSERT` dans l'ordre de service, sans
   écrire de `Comparator`.

---

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
