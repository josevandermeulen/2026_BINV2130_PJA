# Atelier 2 - partie 2 : rappels de théorie

Ce document reprend les notions utiles pour réaliser l'exercice.

Les exemples ci-dessous sont volontairement différents de l'exercice. Ils servent à rappeler les mécanismes Java sans donner directement la structure de la solution.

## Table des matières

1. [Vidéos](#vidéos)
2. [L'interface Map](#linterface-map)
3. [Parcourir une Map](#parcourir-une-map)
4. [Les ensembles triés : SortedSet et TreeSet](#les-ensembles-triés--sortedset-et-treeset)
5. [Ordre naturel : l'interface Comparable](#ordre-naturel--linterface-comparable)
6. [L'interface Comparator](#linterface-comparator)
7. [Classes anonymes](#classes-anonymes)
8. [Égalité et ordre dans un TreeSet](#égalité-et-ordre-dans-un-treeset)
9. [Comparaison d'énumérés](#comparaison-dénumérés)
10. [Renvoyer une collection non modifiable](#renvoyer-une-collection-non-modifiable)
11. [StringBuilder](#stringbuilder)
12. [L'interface SortedMap](#linterface-sortedmap)
13. [Wrapper classes et autoboxing](#wrapper-classes-et-autoboxing)
14. [Clonage d'objets](#clonage-dobjets)
15. [Points d'attention pour l'exercice](#points-dattention-pour-lexercice)

## Vidéos

1. [Interface Comparable en Java](https://www.youtube.com/watch?v=PCjAg07fivY)
2. [Interface Comparator et classe anonyme en Java](https://www.youtube.com/watch?v=gLTAIkKdsK8)
3. [Clone, copie superficielle et profonde en Java](https://www.youtube.com/watch?v=la-CVGl3k7E)

## L'interface `Map`

L'interface `Map` ne fait pas partie des `Collection` à proprement parler. Il s'agit d'un type de structure de données qui stocke les données sous forme de dictionnaire : clé – valeur.

Pensez à un dictionnaire français-anglais. Vous y trouvez des couples de mots : un mot en français (la clé) et son équivalent en anglais (la valeur). Vous cherchez la traduction du mot « clavier », vous trouvez directement le mot « keyboard ».

On déclare la variable avec le type de l'interface et on instancie avec une implémentation, le plus souvent `HashMap` :

```java
Map<String, String> dictionnaire = new HashMap<>();
```

Les opérations de base :

```java
dictionnaire.put("clavier", "keyboard");   // ajoute (ou remplace) l'entrée de clé "clavier"
dictionnaire.get("clavier");               // renvoie "keyboard", ou null si la clé est absente
dictionnaire.remove("clavier");            // supprime l'entrée de clé "clavier"
dictionnaire.containsKey("souris");        // teste la présence d'une clé
dictionnaire.size();                       // nombre d'entrées
dictionnaire.isEmpty();                    // teste si la map est vide
```

Deux points d'attention :

1. Une clé est unique dans une `Map`. Un second `put` avec la même clé remplace la valeur précédente.
2. `get` renvoie `null` si la clé est absente. Il faut donc souvent tester si la clé existe (ou si `get` renvoie `null`) avant d'utiliser la valeur.

Un schéma d'utilisation courant : quand la valeur associée à une clé est elle-même une collection, il faut créer cette collection lors du premier ajout.

```java
public boolean inscrire(String cours, String etudiant) {
    Set<String> inscrits = inscriptions.get(cours);
    if (inscrits == null) {
        inscrits = new HashSet<>();
        inscriptions.put(cours, inscrits);
    }
    return inscrits.add(etudiant);
}
```

La valeur associée à la clé peut être n'importe quel type : un `String`, un objet métier, ou même une autre collection (`Map<String, Set<String>>` dans l'exemple ci-dessus).

## Parcourir une `Map`

Une `Map` ne se parcourt pas directement avec un foreach : il faut passer par une de ses trois vues.

1. `keySet()` renvoie un `Set` contenant toutes les clés.
2. `values()` renvoie une `Collection` contenant toutes les valeurs. C'est une `Collection` et non un `Set`, car les valeurs, contrairement aux clés, peuvent être dupliquées.
3. `entrySet()` renvoie un `Set` de `Map.Entry`, c'est-à-dire l'ensemble des couples clé-valeur.

Parcours par les clés :

```java
for (String mot : dictionnaire.keySet()) {
    System.out.println(mot + " -> " + dictionnaire.get(mot));
}
```

Parcours par les entrées, qui évite de rappeler `get` à chaque tour :

```java
for (Map.Entry<String, String> entry : dictionnaire.entrySet()) {
    System.out.println(entry.getKey() + " -> " + entry.getValue());
}
```

Parcours par les valeurs, quand les clés ne sont pas nécessaires :

```java
for (String traduction : dictionnaire.values()) {
    System.out.println(traduction);
}
```

Les vues sont des vues sur la `Map` et non des copies : une suppression faite via une vue supprime réellement dans la `Map`. En revanche, il n'est pas possible d'ajouter des éléments à une `Map` via une vue.

## Les ensembles triés : `SortedSet` et `TreeSet`

`SortedSet` est une extension de l'interface `Set` : pas de doublons, mais en plus les éléments sont gardés triés de façon croissante. L'itérateur (et donc le foreach) parcourt les éléments dans l'ordre croissant.

L'ordre utilisé est soit un ordre fourni à l'aide d'un `Comparator`, soit, à défaut, l'ordre naturel des éléments (voir plus bas). S'il n'y a ni `Comparator` fourni ni ordre naturel, une `ClassCastException` se produit lorsqu'on essaie d'ajouter un élément dans le `SortedSet`.

L'interface `SortedSet` est implémentée par la classe `TreeSet`, qui possède notamment ces constructeurs :

1. Un constructeur sans paramètre : il faut alors que les éléments ajoutés aient un ordre naturel.
2. Un constructeur qui prend en paramètre un `Comparator` indiquant l'ordre du tri.

```java
SortedSet<String> mots = new TreeSet<>();
mots.add("banane");
mots.add("abricot");
mots.add("cerise");
// le parcours donnera : abricot, banane, cerise
```

`SortedSet` ajoute aussi quelques méthodes, comme `first()` qui renvoie le plus petit élément de l'ensemble et `last()` qui renvoie le plus grand.

## Ordre naturel : l'interface `Comparable`

Pour qu'une classe possède un ordre naturel, il faut qu'elle implémente l'interface `Comparable<T>` où `T` correspond à la classe elle-même. Cette interface ne contient qu'une seule méthode :

```java
public int compareTo(T o)
```

Cette méthode doit renvoyer un entier négatif, nul ou positif, selon que l'objet courant est inférieur, égal ou supérieur à l'objet passé en paramètre.

Exemple : des boissons triées par nom, puis par contenance en cas de noms identiques.

```java
public class Boisson implements Comparable<Boisson> {
    private final String nom;

    private final int contenance;
    // …

    @Override
    public int compareTo(Boisson boisson) {
        int compare = this.nom.compareToIgnoreCase(boisson.nom);
        if (compare != 0) {
            return compare;
        }
        return Integer.compare(this.contenance, boisson.contenance);
    }
}
```

On compare d'abord les noms. Si le résultat est différent de 0, les noms sont différents et il n'est pas nécessaire de comparer les contenances. Sinon, on renvoie le résultat de la comparaison des contenances.

De nombreuses classes Java définissent un ordre naturel : les classes wrappers (`Integer`, `Double`, …), `String`, `LocalDate`, `LocalDateTime`, … et les énumérés (voir plus bas).

C'est la classe elle-même qui décide de la manière dont elle se compare. Toutes les structures triées qui reposent sur l'ordre naturel de cette classe seront affectées si on modifie son `compareTo`.

## L'interface `Comparator`

Lorsqu'il n'y a pas d'ordre naturel, ou si on veut trier dans un ordre qui ne correspond pas à l'ordre naturel, on passe un `Comparator<T>` lors de la construction du `TreeSet`. `Comparator<T>` est une interface qui ne contient qu'une seule méthode (abstraite) :

```java
public int compare(T o1, T o2)
```

Cette méthode doit renvoyer un entier négatif, nul ou positif, selon que le premier objet est inférieur, égal ou supérieur au deuxième.

Dans ce cas, c'est la structure de données qui reçoit la responsabilité de la comparaison, et plus la classe des éléments. On peut ainsi définir plusieurs ordres différents pour une même classe, sans toucher à cette classe.

```java
public class ComparatorBoisson implements Comparator<Boisson> {
    @Override
    public int compare(Boisson o1, Boisson o2) {
        int compare = Integer.compare(o1.getContenance(), o2.getContenance());
        if (compare != 0) {
            return compare;
        }
        return o1.getNom().compareToIgnoreCase(o2.getNom());
    }
}
```

```java
SortedSet<Boisson> boissons = new TreeSet<>(new ComparatorBoisson());
```

## Classes anonymes

Plutôt que de créer une classe à part entière pour un `Comparator` utilisé à un seul endroit, on peut l'instancier à l'aide d'une classe anonyme : on instancie l'interface en fournissant directement l'implémentation de sa méthode.

```java
SortedSet<Boisson> boissons = new TreeSet<>(new Comparator<Boisson>() {
    @Override
    public int compare(Boisson o1, Boisson o2) {
        return Integer.compare(o1.getContenance(), o2.getContenance());
    }
});
```

La classe créée n'a pas de nom : elle sert uniquement à fournir cette implémentation de `compare` à cet endroit du code.

## Égalité et ordre dans un `TreeSet`

Comme dans tout `Set`, il ne peut pas y avoir de doublons dans un `SortedSet`. Mais attention : dans un `TreeSet`, ce n'est pas la méthode `equals` qui détermine si deux éléments sont égaux, c'est la méthode `compareTo` (ou `compare` si un `Comparator` a été fourni). Si la méthode de comparaison renvoie 0, les éléments sont considérés comme égaux.

Cela vaut pour l'ajout, mais aussi pour la recherche (`contains`) et la suppression (`remove`).

Conséquence : si le `Comparator` renvoie 0 pour deux objets pourtant différents selon `equals`, les deux objets ne pourront pas se trouver en même temps dans le `TreeSet`. Exemple : avec un `Comparator` qui ne compare que la contenance, deux boissons différentes de 25 cl sont vues comme « égales » ; la seconde ne sera jamais ajoutée.

Il est donc fortement recommandé de définir la comparaison de façon cohérente avec `equals`, c'est-à-dire de sorte que :

```java
o1.equals(o2)  si et seulement si  compare(o1, o2) == 0
```

En pratique, quand on trie sur un critère qui peut être identique pour deux objets distincts (par exemple une contenance), il faut ajouter un ou plusieurs critères de comparaison supplémentaires (par exemple le nom) pour départager les objets distincts.

## Comparaison d'énumérés

Tout énuméré hérite de la classe `Enum`, et possède de ce fait un ordre naturel : la méthode `compareTo` repose sur l'ordre dans lequel les constantes ont été déclarées.

```java
public enum Taille {
    PETIT, MOYEN, GRAND;
}
```

`Taille.PETIT.compareTo(Taille.GRAND)` renvoie un négatif, car `PETIT` est déclaré avant `GRAND`. Cette méthode ne peut pas être redéfinie.

Cet ordre naturel est bien pratique : pour comparer deux objets selon un attribut de type énuméré, il suffit de déléguer au `compareTo` de l'énuméré.

```java
public int compare(Vetement v1, Vetement v2) {
    return v1.getTaille().compareTo(v2.getTaille());
}
```

Rappel : un énuméré peut aussi avoir des attributs, un constructeur (privé) et des getters. C'est utile quand chaque constante doit porter une information supplémentaire, comme un libellé d'affichage :

```java
public enum Taille {
    PETIT("Petit modèle"), MOYEN("Modèle standard"), GRAND("Grand modèle");

    private String libelle;

    Taille(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}
```

## Renvoyer une collection non modifiable

Rappel de la partie 1 : si un getter renvoie directement la collection interne, le code extérieur peut la modifier sans passer par les méthodes de la classe et l'encapsulation est cassée.

La classe `Collections` fournit une méthode wrapper pour chaque type de collection, dont une pour les ensembles triés :

```java
public SortedSet<Boisson> boissons() {
    return Collections.unmodifiableSortedSet(boissons);
}
```

Toute tentative de modification de l'ensemble renvoyé lance une `UnsupportedOperationException`, mais la lecture et le parcours restent possibles. Il existe aussi `Collections.unmodifiableSet`, `unmodifiableList`, `unmodifiableMap`, …

## StringBuilder

Concaténer des `String` dans une boucle avec `+=` crée un nouvel objet `String` à chaque tour, car un `String` est immuable. Pour construire une chaîne en plusieurs étapes, la classe `StringBuilder` est plus efficace : elle modifie un tampon interne au lieu de créer des objets intermédiaires.

```java
StringBuilder builder = new StringBuilder();
for (Boisson boisson : boissons) {
    builder.append(boisson).append("\n");
}
return builder.toString();
```

La méthode `append` accepte tous les types courants (`String`, `int`, objet…) et renvoie le `StringBuilder` lui-même, ce qui permet d'enchaîner les appels. On récupère le résultat final avec `toString()`.

## L'interface `SortedMap`

Comme il existe un `Set` trié (`SortedSet`), il existe une `Map` triée par ordre de clés : `SortedMap`, dont l'implémentation habituelle est `TreeMap`.

Les vues `keySet()`, `values()` et `entrySet()` d'une `SortedMap` se parcourent dans l'ordre des clés triées.

Comme pour `TreeSet`, les clés doivent pouvoir être triées : soit elles ont un ordre naturel, soit on passe un `Comparator` au constructeur de la `TreeMap`.

```java
SortedMap<Taille, Integer> stock = new TreeMap<>();
stock.put(Taille.GRAND, 4);
stock.put(Taille.PETIT, 10);
// le parcours des clés donnera : PETIT, GRAND (ordre de déclaration de l'énuméré)
```

Ici, les clés de type `Taille` sont triées selon l'ordre naturel de l'énuméré, donc selon l'ordre de déclaration des constantes.

## Wrapper classes et autoboxing

*Cette section et la suivante servent surtout à la Question 6 (partie optionnelle) des exercices ; le piège de l'unboxing d'un `null` (piège 2) reste utile à tous.*

Les collections Java (`List`, `Set`, `Map`, …) ne peuvent contenir que des objets, jamais des types primitifs (`int`, `double`, `boolean`, …). Pour stocker des valeurs primitives dans une collection, on utilise leur classe wrapper correspondante : `Integer` pour `int`, `Double` pour `double`, `Boolean` pour `boolean`, etc.

```java
Map<String, Integer> stock = new HashMap<>();
stock.put("pommes", 12);   // autoboxing : int 12 -> Integer.valueOf(12)
int quantite = stock.get("pommes");   // unboxing : Integer -> int
```

Le compilateur convertit automatiquement entre type primitif et wrapper : c'est l'autoboxing (primitif → wrapper) et l'unboxing (wrapper → primitif). Cette conversion est pratique, mais cache deux pièges.

### Piège 1 : `==` sur des wrappers

```java
Integer a = 127;
Integer b = 127;
System.out.println(a == b);   // true

Integer c = 200;
Integer d = 200;
System.out.println(c == d);   // false
```

`Integer a = 127` revient à écrire `Integer a = Integer.valueOf(127)`. Or `Integer.valueOf` garde en cache les instances pour les valeurs entre -128 et 127 : deux appels avec la même valeur dans cet intervalle renvoient la même instance, donc `==` (qui compare des références) renvoie `true`. En dehors de cet intervalle, chaque appel crée une nouvelle instance : `==` renvoie `false` même si les valeurs sont égales.

Conclusion : comme pour tout objet, il faut comparer des wrappers avec `equals()` (ou en les unboxant explicitement), jamais avec `==`.

### Piège 2 : unboxing d'un `null`

```java
Integer valeur = map.get(cle);   // null si la clé est absente
int total = valeur + 1;          // NullPointerException si valeur == null
```

`map.get(cle)` renvoie `null` quand la clé est absente. Le `+ 1` force l'unboxing de `valeur` en `int`, ce qui lance une `NullPointerException` si `valeur` est `null`. Il faut donc toujours tester si la valeur récupérée est `null` avant de faire une opération arithmétique dessus.

```java
Integer valeur = map.get(cle);
map.put(cle, (valeur == null) ? 1 : valeur + 1);
```

Alternative plus concise avec `getOrDefault(clé, valeurParDefaut)`, qui renvoie `valeurParDefaut` (jamais `null`) si la clé est absente :

```java
map.put(cle, map.getOrDefault(cle, 0) + 1);
```

## Clonage d'objets

Cloner un objet signifie en créer une copie indépendante : modifier le clone ne doit pas affecter l'original, et inversement.

Pour rendre une classe clonable, il faut :

1. La faire implémenter l'interface (marqueur, sans méthode) `Cloneable`.
2. Redéfinir la méthode `clone()` héritée de `Object` (visibilité `public`, alors qu'elle est `protected` dans `Object`).

```java
public class Boisson implements Cloneable {
    private String nom;

    @Override
    public Boisson clone() {
        try {
            return (Boisson) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
```

`Object.clone()` lance une `CloneNotSupportedException`, une exception vérifiée, si la classe n'implémente pas `Cloneable`. Comme la classe l'implémente ici, cette exception ne peut jamais se produire en pratique : on l'attrape et on la transforme en erreur non vérifiée (`AssertionError`) plutôt que de la propager inutilement.

### Clone superficiel (shallow copy)

`super.clone()` fait un clone superficiel : il crée un nouvel objet et copie la valeur de chaque champ tel quel. Pour un champ de type primitif ou un type immuable (`String`, `Duration`, `LocalDate`, …), c'est suffisant : la valeur copiée ne peut de toute façon pas être modifiée en place.

Pour un champ de type objet mutable, ou une collection, un clone superficiel copie uniquement la référence : l'original et le clone pointent alors vers le **même** objet interne.

```java
public class Commande implements Cloneable {
    private List<String> articles = new ArrayList<>();

    @Override
    public Commande clone() {
        try {
            return (Commande) super.clone();
            // ATTENTION : le clone et l'original partagent la même liste `articles` !
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
```

Ici, ajouter un article via le clone modifierait aussi la liste de l'original : ce n'est pas ce qu'on attend d'un clone.

### Clone profond (deep copy)

Pour éviter ce partage, il faut, après l'appel à `super.clone()`, remplacer chaque champ mutable (objet ou collection) par une copie indépendante.

```java
@Override
public Commande clone() {
    try {
        Commande copie = (Commande) super.clone();
        copie.articles = new ArrayList<>(this.articles);   // nouvelle liste, avec les mêmes éléments
        return copie;
    } catch (CloneNotSupportedException e) {
        throw new AssertionError(e);
    }
}
```

Si les éléments de la collection sont eux-mêmes mutables, il faut aussi les cloner un par un plutôt que de se contenter de copier la collection (`new ArrayList<>(this.articles)` copie la liste, mais pas les objets qu'elle contient).

```java
copie.articles = new ArrayList<>();
for (Article article : this.articles) {
    copie.articles.add(article.clone());
}
```

## Points d'attention pour l'exercice

Dans l'exercice, il faudra appliquer ces notions au domaine donné dans l'énoncé.

Avant de terminer, vérifiez que :

1. La structure de données choisie correspond au besoin : une `Map` quand on accède par clé, un `SortedSet` quand il faut un ensemble trié sans doublons.
2. Quand la valeur d'une `Map` est une collection, cette collection est créée lors du premier ajout pour cette clé.
3. Le `TreeSet` reçoit un `Comparator` (ou des éléments ayant un ordre naturel), sinon `ClassCastException` à l'ajout.
4. La méthode de comparaison départage les objets distincts : deux objets différents ne doivent pas être comparés à 0, sinon le `TreeSet` les considère comme des doublons.
5. Les getters de collections renvoient une vue non modifiable.
6. Les parcours de `Map` passent par les vues (`keySet()`, `values()`, `entrySet()`), en choisissant la vue adaptée au besoin.
7. Les énumérés portent leurs informations d'affichage dans un attribut avec getter, plutôt que de laisser l'affichage en majuscules du nom de la constante.
8. Les comparaisons de wrappers (`Integer`, …) utilisent `equals()`, jamais `==`.
9. Une valeur récupérée par `get()` sur une `Map` est vérifiée vis-à-vis de `null` avant tout unboxing/opération arithmétique.
10. `clone()` duplique bien chaque champ mutable (listes, ensembles, objets internes mutables) plutôt que de se contenter du `super.clone()` par défaut.
11. Le programme principal donne le même affichage que celui attendu.

---

*Passez aux [exercices](02B_2_exercices.md).*

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cheat sheet de cette semaine : [consultez-la en ligne](https://astounding-queijadas-0f428a.netlify.app/02-collections-enumeres-fr.html).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
