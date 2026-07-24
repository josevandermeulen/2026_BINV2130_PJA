# Atelier 7 – partie 1 : réponses aux questions d'observation

Ce document donne la réponse des Questions 1 à 9 et 11 : les Questions 1 à 4 portent sur le type des paramètres, les Questions 5 à 8 sur le type de retour, les Questions 9 et 11 sur le paramètre des méthodes `map` et `forEach` de `ExerciceFunctionalInterface`. Cherchez d'abord la réponse vous-même et vérifiez-la dans IntelliJ (`Ctrl+P`, `Ctrl+Maj+P`) avant de lire ce qui suit.

## Question 1 : paramètre de `filter`

Quel est le type du paramètre de la méthode `filter` dans le code suivant ?

```java
long nbGrands = employes.stream()
        .filter(e -> e.getTaille() > 180)
        .count();
```

**Solution** : `Predicate<Employe>`

Le stream est un `Stream<Employe>`, donc la lambda reçoit un `Employe` et retourne un `boolean` : c'est la méthode [`test`](https://astounding-queijadas-0f428a.netlify.app/07-programmation-fonctionnelle-fr.html#predicate-test) de `Predicate<Employe>`.

## Question 2 : paramètre de `map`

Quel est le type du paramètre de la méthode `map` dans le code suivant ?

```java
List<Integer> tailles = employes.stream()
        .map(Employe::getTaille)
        .toList();
```

**Solution** : `Function<Employe, Integer>`

La référence de méthode `Employe::getTaille` prend un `Employe` et retourne sa taille. `getTaille()` retourne un `int`, mais les génériques n'acceptent pas de type primitif : l'`int` est auto-boxé en `Integer`. C'est la méthode `apply` de `Function<Employe, Integer>`.

## Question 3 : paramètre de `Stream.generate`

Quel est le type du paramètre de la méthode `Stream.generate` dans le code suivant ?

```java
List<Employe> interimaires = Stream.generate(() -> new Employe(Genre.HOMME, 180, "Intérimaire"))
        .limit(3)
        .toList();
```

**Solution** : `Supplier<Employe>`

La lambda ne reçoit aucun paramètre et retourne un `Employe` : c'est la méthode `get` de `Supplier<Employe>`.

## Question 4 : second paramètre de `reduce`

Quel est le type du second paramètre de la méthode `reduce` dans le code suivant ?

```java
int sommeTailles = employes.stream()
        .map(Employe::getTaille)
        .reduce(0, (total, taille) -> total + taille);
```

**Solution** : `BinaryOperator<Integer>`

Après le `map`, le stream est un `Stream<Integer>`. La lambda reçoit deux `Integer` (l'accumulateur et l'élément courant) et retourne un `Integer` : c'est la méthode `apply` de `BinaryOperator<Integer>` (un `BiFunction<Integer, Integer, Integer>` dont les trois types sont identiques).

## Question 5 : type de retour de `map`

Quel est le type de la variable `resultat` ?

```java
var resultat = employes.stream()
        .map(Employe::getNom);
```

**Solution** : `Stream<String>`

`map` transforme chaque élément sans terminer le stream : elle retourne un nouveau `Stream` dont le type d'élément est le type de retour de la fonction passée. `getNom()` retourne un `String`, donc `Stream<Employe>` devient `Stream<String>`.

## Question 6 : type de retour de `filter`

Quel est le type de la variable `resultat` ?

```java
var resultat = employes.stream()
        .filter(e -> e.getGenre() == Genre.FEMME);
```

**Solution** : `Stream<Employe>`

`filter` élimine des éléments mais ne les transforme jamais : le type d'élément du stream reste inchangé.

## Question 7 : type de retour de `findFirst`

Quel est le type de la variable `resultat` ?

```java
var resultat = employes.stream()
        .filter(e -> e.getTaille() > 200)
        .findFirst();
```

**Solution** : `Optional<Employe>`

`findFirst` est une opération terminale qui peut ne rien trouver (si le filtre élimine tout le monde) : elle retourne donc un `Optional<Employe>`, jamais un `Employe` nu ni `null`.

## Question 8 : type de retour de `count`

Quel est le type de la variable `resultat` ?

```java
var resultat = employes.stream()
        .map(Employe::getNom)
        .count();
```

**Solution** : `long`

`count` est une opération terminale qui retourne le type primitif `long` — pas un `Long`, ni un `int`.

## Question 9 : paramètre de `map` dans `exMap`

Quel est le type du paramètre de la méthode `map` dans le code suivant ?

```java
return employes.stream()
        .filter(e -> e.getGenre() == Genre.HOMME)
        .sorted(Comparator.comparingInt(Employe::getTaille).reversed())
        .map(e -> e.getNom())
        .collect(Collectors.toList());
```

**Solution** : `Function<Employe, String>`

Le stream est un `Stream<Employe>` après le `filter`/`sorted` (ces deux opérations ne changent pas le type d'élément). La lambda `e -> e.getNom()` prend un `Employe` et retourne un `String` : c'est la méthode `apply` de `Function<Employe, String>`.

## Question 11 : paramètre de `forEach` dans `exForEach`

Quel est le type du paramètre de la méthode `forEach` dans le code suivant ?

```java
List<String> noms = new ArrayList<>();
employes.forEach(e -> noms.add(e.getNom()));
```

**Solution** : `Consumer<Employe>`

`employes` est une `List<Employe>` : la lambda reçoit un `Employe`. Elle ne retourne rien (`noms.add(...)` a un effet de bord mais ne produit pas de valeur utilisée) : c'est la méthode [`accept`](https://astounding-queijadas-0f428a.netlify.app/07-programmation-fonctionnelle-fr.html#consumer-accept) de `Consumer<Employe>`.

---

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
