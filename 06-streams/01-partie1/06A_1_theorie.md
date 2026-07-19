# Atelier 6 : Streams – les bases – partie 1 – Concepts

Les exercices associés à ces concepts se trouvent dans `06A_2_exercices.md`.

## Table des matières

1. [Vidéos](#vidéos)
2. [Concepts de base](#concepts-de-base)

## Vidéos

1. [Les Streams Java - Introduction](https://www.youtube.com/watch?v=q0zMS0_XRSs)
2. [Les Streams - les lambdas expressions en Java](https://www.youtube.com/watch?v=k3kLeB7dd48)
3. [Introduction à la programmation fonctionnelle](https://www.youtube.com/watch?v=rU_O2mrT8SM)
4. [Streams - Reduce](https://www.youtube.com/watch?v=LiZTza_KjnI)
5. [Les Streams - Boucles implicites](https://www.youtube.com/watch?v=jZeB6BKyzTM)
6. [Streams - Gestion du vide](https://www.youtube.com/watch?v=Oof31l4Raiw)
7. [Les Streams - GroupingBy](https://www.youtube.com/watch?v=AjMDMp0Dw84)
8. [Les Streams - debugger les Streams](https://www.youtube.com/watch?v=4hCoZ7HwZsU)
9. [Les Streams un peu de tout](https://www.youtube.com/watch?v=n3P4iniDhOA)

## Concepts de base

### Introduction à la programmation fonctionnelle

« En programmation fonctionnelle, on décrit le résultat souhaité mais pas comment on obtient le résultat. Ce sont les fonctionnalités sous-jacentes qui se chargent de réaliser les traitements requis en tentant de les exécuter de manière optimisée.

Ce mode de fonctionnement est similaire à SQL : le langage SQL permet d'exprimer une requête mais c'est le moteur de la base de données qui choisit la meilleure manière d'obtenir le résultat décrit. Comme avec SQL, la manière dont on exprime le résultat peut influencer la manière dont le résultat va être obtenu notamment en termes de performance.

L'API Stream de Java 8 propose une approche fonctionnelle dans les développements avec Java. Elle permet de décrire de manière concise et expressive un ensemble d'opérations dont le but est de réaliser des traitements sur les éléments d'une source de données. Cette façon de faire est complètement différente de l'approche itérative utilisée dans les traitements d'un ensemble de données avant Java 8. »

Pour avoir une idée de la puissance de l'approche, regardons le code écrit en style fonctionnel ci-dessous qui calcule la taille moyenne des employés masculins à partir d'une collection :

```java
double tailleMoyenneDesHommes = employes
        .stream()
        .filter(e -> e.getGenre() == Genre.HOMME)
        .mapToInt(e -> e.getTaille())
        .average()
        .orElse(0);
```

Nous voyons qu'il enchaîne une série d'opérations. Les trois premières opérations produisent un nouveau stream sur lequel on applique un nouveau traitement :

1. `.stream()` → transforme la collection en stream ;
2. `.filter(e -> e.getGenre() == Genre.HOMME)` → filtre le stream en ne gardant que les objets dont le genre est HOMME ;
3. `.mapToInt(e -> e.getTaille())` → convertit (map) le stream d'`Employe` en stream d'`Integer` ;
4. `.average()` → calcule la moyenne (ce n'est plus un stream) ;
5. `.orElse(0)` → renvoie 0 si le stream après filtrage est vide (dans le cas où il n'y avait aucun homme parmi les employés).

En programmation « normale », chacune de ces opérations aurait nécessité l'écriture d'une boucle explicite. Ici, implicitement, l'ensemble du stream est traité pour produire un nouveau résultat.

On peut résumer les streams par les points suivants :

1. Stream prend une collection, un tableau ou des E/S.
2. Les Streams ne modifient pas la structure de données d'origine. Ils fournissent uniquement le résultat selon les méthodes enchaînées.
3. Chaque opération intermédiaire est exécutée et renvoie un stream en conséquence, de sorte que diverses opérations intermédiaires peuvent être enchaînées.
4. Les opérations terminales marquent la fin du flux (stream) et renvoient le résultat.

**Observations & questions** : imaginez l'écriture du traitement des employés en programmation « classique ». Laquelle est la plus compacte/lisible selon vous ?

### Lambda expression et filtrage

La ligne `.filter(e -> e.getGenre() == Genre.HOMME)` passe en paramètre de la méthode `filter()` une condition de filtrage booléenne.

Nous pourrions écrire la méthode suivante représentant cette condition :

```java
boolean isHomme(Employe e) {
    return e.getGenre() == Genre.HOMME;
}
```

Lorsqu'on utilise des streams, plutôt que de définir une méthode pour chaque traitement, on utilise ce que l'on appelle des Lambda Expressions. Une Lambda expression est ce qu'on pourrait appeler une méthode anonyme.

`e -> e.getGenre() == Genre.HOMME` est une lambda expression qui prend un paramètre `e` qui sera de type `Employe` et renvoie une valeur booléenne. Une Lambda Expression qui renvoie un booléen est aussi appelée **prédicat**. Lors du filtrage par `filter`, cette Lambda Expression est appliquée à chaque élément contenu dans le stream.

Le type du paramètre `e` est déduit selon le contexte. Ici le stream contient des `Employe` ; `e` sera donc de type `Employe`.

Nous verrons lors de la seconde fiche de cet atelier que les lambda expressions peuvent être utilisées dans d'autres contextes que les streams, comme par exemple pour définir des comparateurs.

### Collecter les résultats

L'exemple donné en introduction nous renvoie un entier après traitement. Souvent on voudrait retrouver une collection telle une liste.

La méthode `collect()` nous permet de transformer notre stream en autre chose.

Pour reprendre notre exemple, si nous voulons uniquement une liste des employés hommes, nous pourrions faire ceci :

```java
List<Employe> listDesHommes = employes
        .stream()
        .filter(e -> e.getGenre() == Genre.HOMME)
        .collect(Collectors.toList());
```

On passe en paramètre à `collect()` un `Collector` qui indique vers quoi on veut aller ; ici une liste.

### Inférence de type à la compilation (var)

On peut déclarer un stream de deux façons :

```java
Stream<Transaction> s = transactions.stream();
var s = transactions.stream();
```

Le mot-clé `var` permet de ne pas devoir spécifier le type de la variable `s`. Il est déduit (inféré) lors de la compilation. Puisque la variable `s` prend lors de son initialisation `transactions.stream()`, le compilateur est capable de déduire que son type est `Stream<Transaction>`.

Ceci fonctionne pour la plupart des initialisations comme par exemple :

```java
var nom = "Gregory";
```

Attention, l'inférence de type à la compilation n'est pas la même chose que le typage dynamique comme en JavaScript ou Python. Ici le type doit être déterminable à l'initialisation. Une fois déduit, celui-ci ne pourra plus changer.

### Itérer

Afficher simplement le stream comme une collection (`System.out.println("..." + s)`) n'affiche que le type et la référence du stream.

Pour pousser la logique fonctionnelle jusqu'au bout, on utilise plutôt la méthode `forEach` qui va parcourir le stream et appliquer une méthode passée en paramètre à chaque élément de celui-ci :

```java
s.forEach(System.out::println);
```

`System.out::println` est un raccourci pour la lambda expression `e -> System.out.println(e)`. C'est ce qu'on appelle une **référence de méthode**. Elle peut être utilisée quand la lambda expression se contente d'appeler une seule méthode.

### Transformer (map)

Attention : l'opération `map` des streams n'a rien à voir avec la structure de données `Map` !

En programmation, il est courant de devoir sélectionner des informations de certains objets comme en SQL lorsque l'on sélectionne une colonne d'une table. Les méthodes `map` et `flatMap` de l'API Stream offrent ces possibilités.

La méthode `map()` prend en argument une fonction. Cette fonction est alors appliquée à chaque élément pour créer un nouvel élément. `map` renvoie un autre stream dont le type des éléments correspond à celui du renvoi de la fonction en paramètre :

```java
Stream<String> listeNom = employes.stream()
        .filter(e -> e.getGenre() == Genre.HOMME)
        .map(Employe::getNom);
```

Ici nous aurons donc un Stream de `String`.

### Trier

La méthode `sorted()` permet de trier les éléments d'un flux. Si l'on veut utiliser l'ordre « naturel », c'est-à-dire celui défini par le `compareTo`, on l'utilise sans paramètre comme dans cet exemple :

```java
List<Integer> list = Arrays.asList(6, 2, 9, 1, 7);
list.stream().sorted().forEach(System.out::println);
```

Si on veut trier selon un autre ordre, il va falloir fournir un `Comparator` à cette méthode. On peut facilement créer un comparateur à l'aide de la méthode `comparing()` comme dans l'exemple suivant :

```java
var employeTries =
        employes.stream()
                .sorted(Comparator.comparingInt(Employe::getTaille));
```

La méthode `comparing()` va créer un comparateur sur base de la valeur retournée par la méthode passée en paramètre.

On peut aussi inverser l'ordre de tri :

```java
var employeTries =
        employes.stream()
                .sorted(Comparator.comparingInt(Employe::getTaille)
                .reversed());
```

### Des lambda expressions plus complexes

#### Corps plus complexe

Dans les exemples précédents, nous avions toujours utilisé une forme simple des lambda expressions avec un seul argument et dont le corps était constitué d'une expression simple comme `e -> e.getGenre() == Genre.HOMME` (le corps de cette lambda est une expression booléenne).

Nous pourrions écrire celle-ci de façon moins compacte mais tout aussi correcte :

```java
e -> {
    if (e.getGenre() == Genre.HOMME)
        return true;
    else
        return false;
}
```

Nous voyons que nous pouvons écrire le corps de l'expression lambda comme le corps d'une méthode « normale » pour peu qu'elle renvoie une valeur.

#### Lambda avec plusieurs paramètres

De même, tout comme les méthodes, il est possible d'avoir une lambda qui prend plusieurs paramètres :

```java
(a, b) -> a + b
```

Ce code est équivalent à une méthode prenant deux paramètres et renvoyant leur somme. Les lambdas à plusieurs paramètres sont utilisées dans l'opération de réduction (`reduce`).

### Réduire (reduce)

Réduire un stream consiste à le… réduire à une seule valeur. Pour cela, les éléments contenus dans le stream vont être combinés deux à deux à l'aide d'un opérateur binaire, c'est-à-dire une expression lambda prenant deux paramètres.

Par exemple si l'on veut faire la somme des éléments contenus dans notre stream, on peut utiliser la lambda expression `(a, b) -> a + b` comme dans le code suivant :

```java
List<Integer> numbers = Arrays.asList(3, 4, 5, 1, 2, -5);
int sum = numbers.stream().reduce(0, (a, b) -> a + b);
```

Ce code correspond à la boucle suivante en vieux Java :

```java
int sum = 0;
for (int i : numbers) {
    sum += i;
}
```

Nous voyons ici que la méthode `reduce()` prend deux paramètres : la lambda expression et une valeur neutre (ici 0). Cette valeur neutre permet d'éviter les erreurs si le stream est vide !

Un autre exemple classique est la prise du maximum :

```java
Integer max2 = Stream.of(1, 2, 3, 4, 5)
        .reduce(Integer.MIN_VALUE, Integer::max);
System.out.println(max2);
```

### Petit résumé

Un stream est une séquence d'éléments provenant d'une source qui supporte des traitements de données :

1. il ne possède pas les données qu'il traite ;
2. il conserve l'ordre des données ;
3. il s'interdit de modifier les données qu'il traite ;
4. il traite les données en une passe.

Il y a deux grands types d'opérations :

1. Les opérations dites **intermédiaires** : elles renvoient un autre stream en résultat, ce qui permet de les connecter pour former une requête. Il s'agit des opérations `filter`, `map`, `sorted`, `distinct` ou `limit`.
2. Les opérations **terminales** : elles produisent un résultat à partir d'un pipeline. Ce résultat n'est pas un stream, il peut s'agir d'un nombre, d'une liste ou même `void`. Il s'agit des opérations comme `collect`, `forEach` ou `count`.

---

*Passez aux [exercices](06A_2_exercices.md).*

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cheat sheet de cette semaine : [consultez-la en ligne](https://astounding-queijadas-0f428a.netlify.app/06-streams-fr.html).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
