# Atelier 7 : Programmation fonctionnelle – séance 1 : solutions

*Énoncé : [`07A_2_exercices.md`](../AJ_atelier07_seance1/07A_2_exercices.md) — théorie : [`07A_1_theorie.md`](../AJ_atelier07_seance1/07A_1_theorie.md).*

## Trouver le type

### Question 1

**Énoncé :** Quel est le type du paramètre de la méthode `filter` dans le code suivant ?

**paramètre de `filter`**

**Solution** : `Predicate<Employe>`

Le stream est un `Stream<Employe>`, donc la lambda reçoit un `Employe` et retourne un `boolean` : c'est la méthode [`test`](https://astounding-queijadas-0f428a.netlify.app/07-programmation-fonctionnelle-fr.html#predicate-test) de `Predicate<Employe>`.

### Question 2

**Énoncé :** Quel est le type du paramètre de la méthode `map` dans le code suivant ? Attention, la référence de méthode ne suffit pas : donnez l'interface fonctionnelle complète avec ses types génériques.

**paramètre de `map`**

**Solution** : `Function<Employe, Integer>`

La référence de méthode `Employe::getTaille` prend un `Employe` et retourne sa taille. `getTaille()` retourne un `int`, mais les génériques n'acceptent pas de type primitif : l'`int` est auto-boxé en `Integer`. C'est la méthode `apply` de `Function<Employe, Integer>`.

### Question 3

**Énoncé :** Quel est le type du paramètre de la méthode `Stream.generate` dans le code suivant ?

**paramètre de `Stream.generate`**

**Solution** : `Supplier<Employe>`

La lambda ne reçoit aucun paramètre et retourne un `Employe` : c'est la méthode `get` de `Supplier<Employe>`.

### Question 4

**Énoncé :** Quel est le type du second paramètre de la méthode `reduce` dans le code suivant ?

**second paramètre de `reduce`**

**Solution** : `BinaryOperator<Integer>`

Après le `map`, le stream est un `Stream<Integer>`. La lambda reçoit deux `Integer` (l'accumulateur et l'élément courant) et retourne un `Integer` : c'est la méthode `apply` de `BinaryOperator<Integer>` (un `BiFunction<Integer, Integer, Integer>` dont les trois types sont identiques).

### Question 5

**Énoncé :**

```java
var resultat = employes.stream()
        .map(Employe::getNom);
```

**type de retour de `map`**

**Solution** : `Stream<String>`

`map` transforme chaque élément sans terminer le stream : elle retourne un nouveau `Stream` dont le type d'élément est le type de retour de la fonction passée. `getNom()` retourne un `String`, donc `Stream<Employe>` devient `Stream<String>`.

### Question 6

**Énoncé :**

```java
var resultat = employes.stream()
        .filter(e -> e.getGenre() == Genre.FEMME);
```

**type de retour de `filter`**

**Solution** : `Stream<Employe>`

`filter` élimine des éléments mais ne les transforme jamais : le type d'élément du stream reste inchangé.

### Question 7

**Énoncé :**

```java
var resultat = employes.stream()
        .filter(e -> e.getTaille() > 200)
        .findFirst();
```

**type de retour de `findFirst`**

**Solution** : `Optional<Employe>`

`findFirst` est une opération terminale qui peut ne rien trouver (si le filtre élimine tout le monde) : elle retourne donc un `Optional<Employe>`, jamais un `Employe` nu ni `null`.

### Question 8

**Énoncé :**

```java
var resultat = employes.stream()
        .map(Employe::getNom)
        .count();
```

**type de retour de `count`**

**Solution** : `long`

`count` est une opération terminale qui retourne le type primitif `long` — pas un `Long`, ni un `int`.

## Paramètre de la méthode `map`

### Question 9

**Énoncé :** Ouvrez la classe `ExerciceFunctionalInterface` située dans le package `main` et dans laquelle se trouve la méthode `exMap` qui contient le code suivant :

**paramètre de `map` dans `exMap`**

**Solution** : `Function<Employe, String>`

Le stream est un `Stream<Employe>` après le `filter`/`sorted` (ces deux opérations ne changent pas le type d'élément). La lambda `e -> e.getNom()` prend un `Employe` et retourne un `String` : c'est la méthode `apply` de `Function<Employe, String>`.

### Question 10

**Énoncé :** Transformez le code de `exMap` en implémentant cette interface plutôt que de passer un lambda.

*implémente Function<Employe, String> au lieu du lambda (type trouvé en Question 9)* — [`src/main/java/main/ExerciceFunctionalInterface.java`](src/main/java/main/ExerciceFunctionalInterface.java) :

```java
public class ExerciceFunctionalInterface {
    private final List<Employe> employes;
    // ...

    /**
     * Renvoie les noms des employés hommes triés par taille décroissante, en implémentant
     * la Function&lt;Employe, String&gt; trouvée en Question 9 (voir EmployeNomFunction).
     *
     * @return les noms des employés hommes, du plus grand au plus petit
     */
    public List<String> exMap() {
        return employes.stream()
                .filter(e -> e.getGenre() == Genre.HOMME)
                .sorted(Comparator.comparingInt(Employe::getTaille).reversed())
                .map(new EmployeNomFunction())
                .toList();
    }
    // ...
}
```

*Vérifié par : `ExerciceFunctionalInterfaceTest` (classe `Question10`).*

## Paramètre de la méthode `forEach`

### Question 11

**Énoncé :** Toujours dans `ExerciceFunctionalInterface`, la méthode `exForEach` contient le code suivant :

**paramètre de `forEach` dans `exForEach`**

**Solution** : `Consumer<Employe>`

`employes` est une `List<Employe>` : la lambda reçoit un `Employe`. Elle ne retourne rien (`noms.add(...)` a un effet de bord mais ne produit pas de valeur utilisée) : c'est la méthode [`accept`](https://astounding-queijadas-0f428a.netlify.app/07-programmation-fonctionnelle-fr.html#consumer-accept) de `Consumer<Employe>`.

### Question 12

**Énoncé :** Faites le même raisonnement qu'au point précédent et transformez le code en implémentant cette interface plutôt que de passer un lambda.

*implémente Consumer<Employe> au lieu du lambda (type trouvé en Question 11)* — [`src/main/java/main/ExerciceFunctionalInterface.java`](src/main/java/main/ExerciceFunctionalInterface.java) :

```java
public class ExerciceFunctionalInterface {
    private final List<Employe> employes;
    // ...

    /**
     * Renvoie les noms de tous les employés dans leur ordre d'origine, en implémentant
     * le Consumer&lt;Employe&gt; trouvé en Question 11 (voir EmployeConsumer).
     *
     * @return les noms de tous les employés, dans leur ordre d'origine
     */
    public List<String> exForEach() {
        List<String> noms = new ArrayList<>();
        employes.forEach(new EmployeConsumer(noms));
        return noms;
    }
    // ...
}
```

*Vérifié par : `ExerciceFunctionalInterfaceTest` (classe `Question12`).*

## Des lambdas dans un autre contexte

### Question 13

**Énoncé :** Dans la classe `ExerciceFunctionalInterface`, prenez la méthode `exComparator`. Celle-ci utilise un `Comparator` en créant une classe et en l'instanciant. Modifiez le code pour remplacer le comparator par une lambda expression. Lisez d'abord […]

[`src/main/java/main/ExerciceFunctionalInterface.java`](src/main/java/main/ExerciceFunctionalInterface.java) :

```java
public class ExerciceFunctionalInterface {
    private final List<Employe> employes;
    // ...

    /**
     * Remplace l'instanciation de la classe EmployeComparator par un lambda.
     *
     * @return les employés triés par taille décroissante (puis par nom en cas d'égalité)
     */
    public List<Employe> exComparator() {
        List<Employe> tries = new ArrayList<>(employes);
        tries.sort((e1, e2) -> {
            if (e1.getTaille() == e2.getTaille()) {
                return e1.getNom().compareTo(e2.getNom());
            }
            return e2.getTaille() - e1.getTaille();
        });
        return tries;
    }
}
```

*Vérifié par : `ExerciceFunctionalInterfaceTest` (classe `Question13`).*

## Quelques `Predicate` et `Function`

### Question 14

**Énoncé :** Dans la classe `Lambda` du package `lambda`, implémentez la méthode `allMatches` en respectant la javadoc fournie. Testez-la via la classe `TestLambda` en fournissant des expressions lambda qui correspondent aux indications en commentaires.

*allMatches (Predicate) — version générique de la Question 16* — [`src/main/java/lambda/Lambda.java`](src/main/java/lambda/Lambda.java) :

```java
public class Lambda {
    // ...
    /**
     * Retourne une liste contenant uniquement les éléments qui correspondent
     * au predicat match
     * @param list La liste originale
     * @param match le predicat à respecter
     * @return une liste contenant les éléments qui respectent match
     */
    public static <T> List<T> allMatches(List<T> list, Predicate<T> match) {
        List<T> result = new ArrayList<>();
        list.forEach(i -> {
            if (match.test(i)) {
                result.add(i);
            }
        });
        return result;
    }
    // ...
}
```

*Vérifié par : `LambdaTest` (classe `Question14`).*

### Question 15

**Énoncé :** Implémentez de la même façon la méthode `transformAll` et testez-la via `TestLambda`.

*transformAll (Function) — version générique de la Question 16* — [`src/main/java/lambda/Lambda.java`](src/main/java/lambda/Lambda.java) :

```java
public class Lambda {
    // ...
    /**
     * Retourne une liste contenant tous les éléments de la liste originale, transformés
     * par la fonction transform
     * @param list La liste originale
     * @param transform la fonction à appliquer aux éléments
     * @return une liste contenant les éléments transformés par transform
     */
    public static <P, R> List<R> transformAll(List<P> list, Function<P, R> transform) {
        List<R> result = new ArrayList<>();
        list.forEach(i -> result.add(transform.apply(i)));
        return result;
    }
    // ...
}
```

*Vérifié par : `LambdaTest` (classe `Question15`).*

## Parties optionnelles

### Généricité

#### Question 16

**Énoncé :** Les signatures fournies de `allMatches` et `transformAll` sont déjà génériques (`<T>` pour la première, `<P, R>` pour la seconde) : vérifiez que vos implémentations acceptent bien des `List` de n'importe quel type, et pas seulement des `Integer`. Dans la […]

**rendre allMatches et transformAll génériques**

Le passage aux génériques se lit dans les signatures des extraits des
Questions 14 et 15 (`src/main/java/lambda/Lambda.java`) :

- `allMatches` : `List<Integer>` et `Predicate<Integer>` deviennent
  `List<T>` et `Predicate<T>`, avec le paramètre de type `<T>` déclaré avant
  le type de retour ;
- `transformAll` demande **deux** paramètres de type — `<P, R>` — car le type
  d'entrée de la `Function<P, R>` (celui des éléments de la liste) et son
  type de sortie (celui des éléments du résultat) sont indépendants.

Le corps des méthodes, lui, ne change pas : c'est tout l'intérêt — seules les
signatures se généralisent, les lambdas passées par `TestLambda` fixent les
types concrets à l'appel.

*Vérifié par : `LambdaTest` (classe `Question16`).*

### Les mêmes traitements avec l'API Stream

#### Question 17

**Énoncé :** La classe `Lambda` contient déjà la signature de la méthode `filter` : implémentez-la pour qu'elle fasse exactement la même chose que `allMatches`, mais en utilisant l'API Stream. Dans la classe `TestLambda`, effectuez les mêmes tests que pour `allMatches`.

*filter (API Stream)* — [`src/main/java/lambda/Lambda.java`](src/main/java/lambda/Lambda.java) :

```java
public class Lambda {
    // ...
    /**
     * Retourne une liste contenant uniquement les Integer qui correspondent
     * au predicat match
     * @param list La liste d'Integer originale
     * @param match le predicat à respecter
     * @return une liste contenant les integer qui respectent match
     */
    public static <T> List<T> filter(List<T> list, Predicate<T> match) {
        return list.stream().filter(match).toList();
    }
    // ...
}
```

*Vérifié par : `LambdaTest` (classe `Question17`).*

#### Question 18

**Énoncé :** Implémentez de même la méthode `map`, qui fait exactement la même chose que `transformAll`, mais en utilisant l'API Stream. Testez-la comme `transformAll`.

*map (API Stream)* — [`src/main/java/lambda/Lambda.java`](src/main/java/lambda/Lambda.java) :

```java
public class Lambda {
    // ...
    /**
     * Retourne une liste contenant tous les éléments de la liste originale, transformés
     * par la fonction transform
     * @param list La liste d'Integer originale
     * @param transform la fonction à appliquer aux éléments
     * @return une liste contenant les integer transformés par transform
     */
    public static <P, R> List<R> map(List<P> list, Function<P, R> transform) {
        return list.stream().map(transform).toList();
    }

}
```

*Vérifié par : `LambdaTest` (classe `Question18`).*

---

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
