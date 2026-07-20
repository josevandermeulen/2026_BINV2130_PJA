# Atelier 2 : énumérés, collections – partie 2

## Objectif

L'objectif de cet atelier est d'étendre l'application de recettes en ajoutant un livre de recettes permettant de classer, trier et retrouver les plats selon leur type.

## Concepts

1. Collections associatives : `Map`
2. Ensembles triés : `SortedSet` et `TreeSet`
3. Comparaison d'objets : `Comparable` et `Comparator`
4. Classes anonymes
5. Énumérés internes avec attributs
6. Parcours de collections (`keySet`, `entrySet`, `values`)
7. Collections non modifiables
8. Wrapper classes, autoboxing/unboxing
9. Clonage superficiel et profond (`Cloneable`)
10. `StringBuilder` et `SortedMap` (parties optionnelles)

## Vidéos

1. [Interface Comparable en Java](https://www.youtube.com/watch?v=PCjAg07fivY)
2. [Interface Comparator et classe anonyme en Java](https://www.youtube.com/watch?v=gLTAIkKdsK8)
3. [Clone, copie superficielle et profonde en Java](https://www.youtube.com/watch?v=la-CVGl3k7E)

## Exercices

### Introduction

Dans cette partie, nous allons introduire la notion de livre de recettes.

Dans notre livre, on pourra y retrouver plusieurs plats, organisés par type (entrée, plat, dessert). Pour chaque type de plat, les recettes seront triées par ordre de difficulté.

On souhaiterait également pouvoir récupérer facilement tous les plats d'un certain type. Par exemple, toutes les entrées, ou tous les desserts.

### Consignes

Pour cette partie, nous allons repartir de la solution de la partie 1.

Dans IntelliJ, créez un projet intitulé `AJ_atelier02_partie2`. Ajoutez-y les classes de la solution de l'atelier 2 partie 1 (voir `../01-partie1/02-solution/`). Faites attention aux packages !

Vérifiez qu'il fonctionne correctement en démarrant le main.

Certaines questions ci-dessous sont des questions de réflexion (rien à coder) : leurs réponses se trouvent dans [`02-solution/02B_solutions-observations.md`](02-solution/02B_solutions-observations.md) — réfléchissez (et testez !) avant de les consulter.

### L'énuméré `Type`

**Question 1** :

✏️ *A corriger au tableau*

#### Créer l'énuméré

Pour pouvoir trier les plats par type (entrée, plat, dessert) dans notre livre, il va falloir créer cette notion de « type de plat » dans notre application. Comme les types sont bien définis et a priori invariables, on pense immédiatement à un énuméré. Comme ce type n'a de sens que pour un plat, on va le mettre en classe interne.

Créez cet énuméré `Type` dans la classe [`Plat`](01-code-java/domaine/Plat.java) (package `domaine`), avec 3 valeurs (`ENTREE`, `PLAT`, `DESSERT`).

#### Adapter la classe `Plat`

Ajoutez dans la classe `Plat` un attribut `type`, avec un getter, pas de setter, et ajoutez le type également dans le constructeur.

#### Adapter le main

Vous remarquerez en modifiant le constructeur qu'il signale un « related problem ». Cliquez dessus pour vous retrouver dans le main. Corrigez-y le problème.

Lancez votre projet pour vérifier que tout fonctionne correctement. S'il n'y a pas de message d'erreur, et que tout s'affiche comme au début, c'est sans doute OK.

### La classe `Livre`

**Question 2** :
Créez une classe `Livre` dans le package `domaine`. Elle reste vide pour l'instant, on va réfléchir à ce qu'on met dedans ensuite.

#### La structure de données

Un livre va être composé d'un ensemble d'objets `Plat`. Mais sous quelle forme retenir ces plats ? Une liste ? Un ensemble ? Autre chose ?

Pour pouvoir faire un choix, il faut réfléchir à la manière dont on va utiliser cette structure de données. Comment on va insérer des choses dedans, comment on va en supprimer, y accéder…

**Question 3** :

✏️ *A corriger au tableau*

Relisez bien l'introduction… Quelle est la structure de données qui nous conviendrait ici ? Justifiez votre choix.

**Question 4** :
Une fois la structure choisie, pourquoi ne pas faire juste 3 attributs `SortedSet<Plat>` plutôt qu'une `Map` ?

### Construire le livre

**Question 5** :
Créez l'attribut `plats` dans la classe `Livre` : une `Map` dont la clé est le type de plat, et la valeur un ensemble trié (`SortedSet<Plat>`) des plats de ce type. Si on a 3 types de plat, la `Map` contiendra donc 3 `SortedSet<Plat>`.

Ajoutez ensuite les méthodes suivantes dans la classe `Livre` :

```java
/**
 * Ajoute un plat dans le livre, s'il n'existe pas déjà dedans.
 * Il faut ajouter correctement le plat en fonction de son type.
 * @param plat le plat à ajouter
 * @return true si le plat a été ajouté, false sinon.
 */
public boolean ajouterPlat(Plat plat) {
    return false;
}

/**
 * Supprime un plat du livre, s'il est dedans.
 * Si le plat supprimé est le dernier de ce type de plat, il faut supprimer ce type de
 * plat de la Map.
 * @param plat le plat à supprimer
 * @return true si le plat a été supprimé, false sinon.
 */
public boolean supprimerPlat(Plat plat) {
    return false;
}
```

Complétez ces méthodes.

**ASTUCES** :

1. Lorsque vous souhaitez ajouter un plat, il faut d'abord récupérer le bon ensemble de plats en fonction du type. Au début, il n'y aura encore aucun ensemble dans la `Map`. Il faut donc à chaque fois vérifier si l'ensemble existe avant d'ajouter le plat.
2. Vous n'allez pas pouvoir tester tout de suite vos méthodes… C'est normal. Attendez un peu avant d'exécuter.

Enfin, écrivez la méthode `toString` de la classe `Livre`, pour pouvoir afficher la liste des plats et tester vos méthodes. Voici un exemple d'affichage :

```
ENTREE
=====
Croquettes au fromage

PLAT
=====
Waterzooi
```

### Trier les plats du livre

Pour garder les plats triés, on va les stocker dans un `TreeSet`. Mais un `TreeSet` a besoin de savoir comment ordonner ses éléments. Il y a 2 solutions :

1. Soit on laisse `Plat` implémenter l'interface `Comparable`, et donc la méthode `compareTo`. En faisant cela, on laisse la classe `Plat` décider de comment elle se juge comparable. Donc comment elle juge qu'un plat sera avant ou après un autre.
2. Soit on donne un `Comparator` à l'ensemble trié. C'est une méthode qui définit comment comparer les objets qui seront dans l'ensemble. Dans ce cas, c'est l'ensemble qui a la responsabilité de la comparaison, et plus la classe `Plat`.

**Question 6** :
Quelle solution vous semble la plus adaptée ici ?

**Question 7** :
Adaptez le `main` pour tester `toString`, `ajouterPlat` et `supprimerPlat`. Ajoutez ceci à la fin de la méthode `main` :

```java
Livre livre = new Livre();
livre.ajouterPlat(plat);
System.out.println(livre);
livre.supprimerPlat(plat);
System.out.println(livre);
```

Si vous exécutez tel quel, vous obtiendrez l'erreur suivante :

```
Exception in thread "main" java.lang.ClassCastException: class domaine.Plat cannot be cast to class java.lang.Comparable
```

Le `TreeSet` ne sait pas comment ordonner les `Plat` : on ne lui a jamais dit comment le faire. Modifiez la méthode `ajouterPlat` en passant en paramètre un `new Comparator<Plat>` lors de la création du `TreeSet`.

**ASTUCES** :

1. C'est possible de faire la méthode `compare` en une ligne.
2. N'hésitez pas à déléguer la comparaison à une méthode `compare` d'une autre classe.
3. Regardez ce qu'il existe comme méthodes pour un énuméré…

Exécutez votre main. Il devrait afficher, en plus de la recette du waterzooi, le livre de recettes :

```
PLAT
=====
Waterzooi
```

**Question 8** :
Modifiez votre méthode `main` avec la création du livre, pour y mettre ce code-ci :

```java
Livre livre = new Livre();
livre.ajouterPlat(plat);
livre.ajouterPlat(new Plat("Croquettes au fromage", 4, Difficulte.XXX, Cout.$$, Plat.Type.ENTREE));
System.out.println(livre);
livre.supprimerPlat(new Plat("Toasts aux champignons", 5, Difficulte.XXX, Cout.$$$, Plat.Type.ENTREE));
System.out.println(livre);
```

**NE L'EXÉCUTEZ PAS ENCORE !** Lisez le code et essayez d'imaginer le résultat attendu. Que va afficher ce bout de code ?

**Question 9** :
Une fois que vous avez une idée du résultat attendu, exécutez-le. Si vous vous êtes trompés, pourquoi est-ce que ça affiche ce résultat-là ?

Corrigez ensuite le tir : modifiez votre méthode `compare` pour trier par niveau de difficulté, puis par nom. Deux plats seront donc identiques si leur niveau de difficulté est identique, et si leur nom est identique. Une fois la modification effectuée, vérifiez que tout est rentré dans l'ordre.

### Améliorer le `Livre`

**Question 10** :
Ajoutez les méthodes suivantes dans la classe `Livre` :

```java
/**
 * Renvoie un ensemble contenant tous les plats d'un certain type.
 * L'ensemble n'est pas modifiable.
 * @param type le type de plats souhaité
 * @return l'ensemble des plats
 */
public SortedSet<Plat> getPlatsParType(Plat.Type type) {
    // L'ensemble renvoyé ne doit pas être modifiable !
    return null;
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
    return false;
}

/**
 * Renvoie un ensemble contenant tous les plats du livre. Ils ne doivent pas être triés.
 * @return l'ensemble de tous les plats du livre.
 */
public Set<Plat> tousLesPlats() {
    // Ne pas utiliser la méthode keySet() ou entrySet() ici !
    return null;
}
```

Complétez ces méthodes.

N'hésitez pas à modifier votre `main` pour les tester.

### Améliorer l'énuméré `Type` de plat

**Question 11** :
Lors de l'affichage du livre, on affiche directement le nom de l'énuméré. Il est donc affiché en majuscules.

Modifiez l'énuméré pour lui ajouter un attribut `nom`, avec son getter, mais pas de setter. Initialisez chaque énuméré avec son nom correct : Entrée, Plat, Dessert.

Adaptez la méthode `toString` de la classe `Livre` pour qu'elle affiche correctement le nom.

À ce stade, en ajoutant à la fin du `main` les ingrédients, le plat et le livre créés au fil des questions précédentes, la sortie du programme doit correspondre au début de celle attendue (voir `01-code-java/affichage_Main.txt`, jusqu'à la ligne `Usages des ingrédients :` exclue), à l'ordre des ingrédients près : cet ordre dépend du `HashSet` utilisé et peut varier d'une exécution à l'autre.

### Test

**Question 12** :
Exécutez la classe [`Main`](01-code-java/main/Main.java) (package `main`). La sortie attendue se trouve dans `01-code-java/affichage_Main.txt`, à l'ordre des ingrédients près (il dépend du `HashSet` utilisé). Votre sortie doit correspondre jusqu'à la ligne `Usages des ingrédients :` exclue — tout ce qui suit provient de la Question 14 (partie optionnelle ci-dessous).

## Parties optionnelles

### Wrapper, autoboxing et clonage

**Question 13** :

Avant de continuer, testez ce petit bout de code dans un `main` (peu importe où) :

```java
Integer a = 127;
Integer b = 127;
System.out.println(a == b);

Integer c = 200;
Integer d = 200;
System.out.println(c == d);
```

Pourquoi les deux affichages sont-ils différents ?

**Question 14** :

#### Compter les usages d'un ingrédient

Ajoutez d'abord dans la classe `Plat` le getter suivant :

```java
public Set<IngredientQuantifie> ingredientsQuantifies()
// renvoie une vue non modifiable de l'ensemble des ingrédients quantifiés du plat.
```

Ajoutez ensuite dans la classe `Livre` la méthode :

```java
/**
 * Compte, pour chaque ingrédient utilisé dans au moins un plat du livre,
 * dans combien de plats il apparaît.
 * @return une Map associant chaque ingrédient utilisé à son nombre d'usages.
 */
public Map<Ingredient, Integer> compterUsagesIngredients() {
    return null;
}
```

Complétez cette méthode en parcourant `tousLesPlats` et, pour chaque plat, ses `ingredientsQuantifies`.

**ASTUCE / PIÈGE** : `Map.get` renvoie `null` si la clé est absente. À cause de l'autoboxing, écrire directement `compteur.put(ingredient, compteur.get(ingredient) + 1)` lance une `NullPointerException` la première fois qu'on rencontre un ingrédient (Java doit unboxer le `null` en `int` pour faire le `+1`). Il faut donc tester si la valeur récupérée est `null` avant d'incrémenter, ou utiliser directement `Map.getOrDefault(clé, valeurParDefaut)`, qui renvoie `valeurParDefaut` (ici `0`) au lieu de `null` si la clé est absente : `compteur.put(ingredient, compteur.getOrDefault(ingredient, 0) + 1)`.

Testez avec ce code, à ajouter après la partie sur le `Livre` :

```java
Plat croquettes = new Plat("Croquettes au fromage", 4, Difficulte.XXX, Cout.$$, Plat.Type.ENTREE);
croquettes.ajouterIngredient(new Ingredient("Sel"), 1, Unite.PINCEE);
livre.ajouterPlat(croquettes);
// (remplacez la ligne "livre.ajouterPlat(new Plat("Croquettes au fromage", ...))" par les 3 lignes ci-dessus)

System.out.println("Usages des ingrédients :");
Map<Ingredient, Integer> usages = livre.compterUsagesIngredients();
for (Map.Entry<Ingredient, Integer> entry : usages.entrySet()) {
    System.out.println(entry.getKey() + " : " + entry.getValue());
}
```

`Sel` doit apparaître avec un compteur de 2 (il est utilisé à la fois dans le Waterzooi et dans les Croquettes), les autres ingrédients avec un compteur de 1.

#### Cloner une instruction (clone superficiel)

**Question 15** :
Faites implémenter `Cloneable` à la classe [`Instruction`](01-code-java/domaine/Instruction.java), et redéfinissez la méthode :

```java
@Override
public Instruction clone() {
    // ...
}
```

Comme `description` (`String`) et `dureeEnMinutes` (`Duration`) sont des types immuables, un clone superficiel (`super.clone()`) suffit ici : il n'y a aucun risque que le clone et l'original partagent un état mutable.

#### Cloner un plat (clone profond)

**Question 16** :
Faites implémenter `Cloneable` à la classe `Plat`, et redéfinissez `clone` pour renvoyer une **copie profonde** :

1. Les champs simples (`nom`, `nbPersonnes`, `niveauDeDifficulte`, `cout`, `type`, `dureeEnMinutes`) peuvent être copiés tels quels par `super.clone()` : ce sont des types immuables ou des énumérés (partagés sans risque).
2. La `List<Instruction>` doit être remplacée par une **nouvelle** liste contenant des **clones** de chaque instruction (sinon le clone et l'original partageraient les mêmes objets `Instruction`, qui sont mutables via leurs setters).
3. Le `Set<IngredientQuantifie>` doit être remplacé par un **nouvel** ensemble contenant de **nouvelles instances** d'[`IngredientQuantifie`](01-code-java/domaine/IngredientQuantifie.java) (mêmes valeurs, mais indépendantes) : `IngredientQuantifie` est mutable (`setQuantite`, `setUnite`), donc le réutiliser tel quel casserait l'indépendance entre original et clone.

**RAPPEL** : `Object.clone` lance une `CloneNotSupportedException` (exception vérifiée). Comme votre classe implémente `Cloneable`, cette exception ne se produira jamais en pratique : attrapez-la et relancez une `AssertionError`, ou toute autre erreur non vérifiée.

Testez avec ce code (à ajouter à la fin du `main`) :

```java
Plat copiePlat = plat.clone();
copiePlat.ajouterInstruction(new Instruction("Étape ajoutée sur la copie", 2));
copiePlat.modifierIngredient(new Ingredient("Sel"), 999, Unite.PINCEE);

System.out.println("\nPlat original (ne doit pas avoir changé) :");
System.out.println(plat);
System.out.println("Plat copié (doit contenir la nouvelle étape et le sel modifié) :");
System.out.println(copiePlat);
```

**Question 17** :
Si vous n'aviez pas redéfini `clone` dans `Plat` pour dupliquer la liste et l'ensemble (c.-à-d. si vous vous étiez contenté du `clone` par défaut de `Object`), qu'aurait affiché ce test ?

### Ingrédients : changement de structure de données

**Question 18** :
Modifiez votre implémentation en utilisant une collection `Map<Ingredient, IngredientQuantifie>` dans la classe `Plat` au lieu de la collection `Set<IngredientQuantifie>` pour stocker les ingrédients. Après ce changement, l'ordre d'affichage des ingrédients peut différer de celui que vous obteniez avec le `Set` : une `HashMap` ne garantit pas plus d'ordre qu'un `HashSet`. Ce n'est pas une régression — seul le contenu compte, comme indiqué à la Question 12.

### `StringBuilder`

**Question 19** :
Renseignez-vous sur la classe `StringBuilder`, et utilisez-la à bon escient dans la méthode `toString` de la classe `Livre`.

### Trier tous les plats

**Question 20** :
Dans l'énoncé, on vous demande de ne pas trier les plats de la méthode `tousLesPlats`. Pour cet exercice, on vous demande de quand même trier cet ensemble, en faisant en sorte qu'ils soient triés par Type, puis par Niveau de difficulté, et enfin par Nom.

Exemple : les entrées en premier, et les entrées sont triées par niveau de difficulté ascendant, et pour les plus simples par exemple, on les trie par ordre alphabétique de nom.

### Map triée ?

**Question 21** :
On souhaiterait faire en sorte que lorsqu'on appelle la méthode `keySet` de la `Map` des plats de la classe `Livre`, on obtienne les clefs dans un ordre bien précis. À savoir, par ordre de service dans un restaurant. D'abord les entrées, puis les plats, puis les desserts.

Modifiez votre code pour arriver à ce résultat.

Astuces :

1. Il faut modifier le type de l'attribut `plats` de la classe `Livre`.
2. Il faut trouver une solution pour que les `Type` puissent être triés.

---

*Passez à la [théorie suivante](../../03-junit/01-partie1/03A_1_theorie.md).*

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cheat sheet de cette semaine : [consultez-la en ligne](https://astounding-queijadas-0f428a.netlify.app/02-collections-enumeres-fr.html).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
