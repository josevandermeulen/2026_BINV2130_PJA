# Atelier 7 : Programmation fonctionnelle – partie 1 – Concepts

Les exercices associés à ces concepts se trouvent dans `07A_2_exercices.md`.

## Table des matières

1. [Vidéos](#vidéos)
2. [Derrière les fagots](#derrière-les-fagots)

## Vidéos

1. [Comment aplatir les collections imbriquées en Java avec flatMap](https://www.youtube.com/watch?v=aZHDUchqjyk)
2. [Immuabilité Java](https://www.youtube.com/watch?v=u0j64SmIoc4)
3. [Mise en pratique du design fonctionnel en Java partie 1 - L'immuabilité](https://www.youtube.com/watch?v=s6Zwdeg3NdI)
4. [Programmation fonctionnelle cas pratique en Java partie 2 - Les exceptions](https://www.youtube.com/watch?v=g0EVQYClhtA)

## Derrière les fagots

### Et le typage dans tout ça ?

Les Streams viennent de la programmation fonctionnelle. Or Java est à la base un langage orienté objets. Dans cette section nous allons voir un peu plus en profondeur les extensions apportées à Java pour permettre aux Streams de s'intégrer harmonieusement dans Java malgré cette différence de paradigme.

### Les méthodes default

Les collections Java se transforment en stream comme par magie. Notre liste de choses devient en un seul appel de méthode un fameux stream…

Reprenons justement cet exemple : avant Java 8, `List<T>` ne contenait pas la méthode `stream()`.

Ajouter une méthode abstraite `stream()` dans `Collection` et une méthode concrète dans `ArrayList` aurait pu convenir pour notre cas, mais quel cauchemar pour toutes les autres classes qui étendent `Collection` qu'il aurait alors fallu modifier. Il devenait impossible de faire évoluer les interfaces sans interrompre le fonctionnement des implémentations de celles-ci, quel dilemme ! C'est pour cela que Java 8 permet désormais d'avoir des méthodes `default` dans les interfaces ; les classes ne doivent pas fournir d'implémentation pour celles-ci. Il n'est donc plus obligatoire d'ajouter des implémentations dans toutes les classes qui implémentent des interfaces évoluant. Abracadabra !

Autrement dit, les interfaces permettent de faire comme si on ajoutait l'implémentation d'une méthode à une classe sans modifier celle-ci.

Prenons un exemple concret. Il est possible d'invoquer la méthode `sort()` sur une `ArrayList`, pourtant, cette dernière n'en possède aucune implémentation directe. L'interface `List` propose une implémentation par défaut de celle-ci.

### Petit détour par les Generics

En allant voir dans l'interface `List` la méthode `sort`, vous avez dû remarquer que sa signature comportait des éléments relatifs aux types génériques assez complexes :

```java
default void sort(Comparator<? super E> c)
```

Jusqu'à présent, nous avons principalement utilisé les génériques, mais nous n'avons pas abordé en détail la déclaration de méthodes ou de classes avec des génériques.

Observons cette signature plus en détail, en commençant par le type du paramètre `c` : le type `Comparator<? super E>` indique que le paramètre `c` est un `Comparator` destiné à comparer des objets de type `E` ou de ses superclasses. `E` est le type générique de l'élément de la `List`. Par exemple, si nous avons une `List<String>`, `E` serait de type `String`.

La raison pour laquelle on utilise `? super E` est pour offrir plus de flexibilité. Imaginez avoir une classe `Animal` et une sous-classe `Dog`. Si vous avez une `List<Dog>`, vous pourriez vouloir la trier en utilisant un `Comparator<Animal>`, car tous les `Dog` sont aussi des `Animal`.

La méthode `sort` de l'interface `List` utilise ensuite la méthode `sort` de la classe `Collections` pour effectivement trier la liste, en utilisant le comparateur fourni.

Ce principe vaut aussi pour la déclaration des interfaces et classes. Prenons par exemple l'interface `List` ; nous y voyons le code suivant :

```java
public interface List<E> extends Collection<E> {
```

Nous voyons ici que `List` est déclarée générique par rapport au type `E`, sur lequel il n'y a aucune contrainte. Nous pouvons donc avoir une `List` de n'importe quoi.

Si nous avons une `List<String>`, `E` représente le type `String` quand il est utilisé dans le code de l'interface. Ici `List<String>` extends donc `Collection<String>`.

Plus loin, nous voyons la méthode `add` :

```java
boolean add(E e);
```

Encore une fois, si nous avons une `List<String>`, la signature de la méthode `add` doit être comprise comme :

```java
boolean add(String e)
```

Pour une explication encore plus détaillée, voir https://docs.oracle.com/javase/tutorial/extra/generics/methods.html

### Les interfaces fonctionnelles et lambda expressions

Une expression lambda est en fait une fonction ; ce n'est pas une classe mais plutôt une méthode que l'on passe en paramètre. Une lambda expression est donc une fonction manipulée comme une valeur !

Or Java est un langage fortement typé et les lambdas doivent respecter cela.

Essayons de comprendre ce qui se cache derrière la magie. Reprenons notre classe `Employe` de la semaine passée :

```java
List<Employe> listDesHommes = employes
        .stream()
        .filter(e -> e.getGenre() == Genre.HOMME)
```

La méthode `filter` reçoit en paramètre une lambda. Regardez avec IntelliJ la signature de cette méthode (clic droit → Go to → Declaration or Usages) :

```java
Stream<T> filter(Predicate<? super T> predicate)
```

Nous voyons qu'elle prend en paramètre un `Predicate`. La lambda passée en paramètre est donc considérée comme étant de type `Predicate`.

Regardez maintenant la définition de `Predicate` avec IntelliJ :

```java
@FunctionalInterface
public interface Predicate<T> {

    /**
     * Evaluates this predicate on the given argument.
     *
     * @param t the input argument
     * @return {@code true} if the input argument matches the predicate,
     * otherwise {@code false}
     */
    boolean test(T t);
```

Nous voyons :

1. qu'il s'agit d'une interface ;
2. qu'elle possède une méthode abstraite `test` qui prend un paramètre d'un type quelconque et renvoie un booléen ;
3. qu'elle est annotée `@FunctionalInterface`, ce qui l'oblige à n'avoir qu'une seule méthode abstraite (donc non implémentée par défaut).

Notre lambda `e -> e.getGenre() == Genre.HOMME` est en fait l'implémentation implicite de la méthode `test` contenue dans l'interface `Predicate`.

On pourrait écrire une classe implémentant l'interface `Predicate` :

```java
public class PredicatGenreHomme implements Predicate<Employe> {
    @Override
    public boolean test(Employe e) {
        return e.getGenre() == Genre.HOMME;
    }
}
```

et l'utiliser ensuite dans notre filter :

```java
List<Employe> listDesHommesBis = employes
        .stream()
        .filter(new PredicatGenreHomme())
```

La lambda expression dans ce contexte n'est autre qu'un raccourci pour l'implémentation et l'instanciation de l'interface `Predicate` ! La méthode `filter` va appeler `test()` sur chacun des employés contenus dans le stream.

L'interface `Predicate` est dite **interface fonctionnelle** car elle ne possède qu'une seule méthode abstraite. Elle représente donc la définition d'une fonction.

### Function

La méthode `map` suit exactement le même principe. Regardez sa signature avec IntelliJ :

```java
<R> Stream<R> map(Function<? super T, ? extends R> mapper)
```

Elle prend en paramètre une `Function`. En allant voir la définition de cette interface, nous retrouvons la même structure que `Predicate` : une interface annotée `@FunctionalInterface` avec une seule méthode abstraite :

```java
R apply(T t);
```

Une `Function<T, R>` représente une fonction qui reçoit un objet de type `T` et renvoie un objet de type `R`. La lambda `e -> e.getNom()` passée à `map` est donc une `Function<Employe, String>` : elle reçoit un `Employe` et renvoie un `String`. Comme pour `Predicate`, on pourrait écrire une classe implémentant l'interface :

```java
public class EmployeNomFunction implements Function<Employe, String> {
    @Override
    public String apply(Employe employe) {
        return employe.getNom();
    }
}
```

et la passer à `map` : `.map(new EmployeNomFunction())`.

### Consumer

Même raisonnement avec la méthode `forEach`, qui prend en paramètre un `Consumer`. Sa méthode abstraite est :

```java
void accept(T t);
```

Un `Consumer<T>` reçoit un objet de type `T` et ne renvoie rien : il « consomme » l'élément pour produire un effet — afficher, ajouter à une liste, écrire dans un fichier… La lambda `e -> System.out.println(e.getNom())` passée à un `forEach` sur des employés est un `Consumer<Employe>`.

### Supplier

Une autre interface fonctionnelle très utile est l'interface `Supplier`. Celle-ci possède une méthode `get()` qui ne prend pas de paramètre et renvoie un élément d'un type donné. Comme son nom l'indique, l'interface `Supplier` sert à fournir des données comme, par exemple, lorsqu'on lit un fichier.

### Les lambdas dans un autre contexte

Les lambdas ne sont pas seulement utiles dans le contexte de l'API Stream : toute interface fonctionnelle peut être implémentée par une lambda. C'est le cas des `Comparator` que nous avons écrits lors du travail sur les collections ; leur unique méthode abstraite est `int compare(T o1, T o2)`. Au lieu de déclarer une classe :

```java
public class EmployeComparator implements Comparator<Employe> {
    @Override
    public int compare(Employe e1, Employe e2) {
        return e2.getTaille() - e1.getTaille();
    }
}
```

et de l'instancier au moment du tri, on écrit directement :

```java
employes.sort((e1, e2) -> e2.getTaille() - e1.getTaille());
```

### Les références de méthode

Vous avez croisé la notation `Employe::getTaille` dans les exercices :

```java
.sorted(Comparator.comparingInt(Employe::getTaille).reversed())
```

Une référence de méthode est un raccourci d'écriture pour une lambda qui ne fait qu'appeler une méthode existante : `Employe::getTaille` équivaut à `e -> e.getTaille()`. Quand une lambda a cette forme, IntelliJ propose d'ailleurs la conversion automatiquement.

Quelques équivalences courantes :

| Référence de méthode | Lambda équivalente |
|---|---|
| `Employe::getNom` | `e -> e.getNom()` |
| `String::toUpperCase` | `s -> s.toUpperCase()` |
| `System.out::println` | `s -> System.out.println(s)` |
| `Employe::new` | `() -> new Employe()` |

---

*Passez aux [exercices](07A_2_exercices.md).*

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cheat sheet de cette semaine : [consultez-la en ligne](https://astounding-queijadas-0f428a.netlify.app/07-programmation-fonctionnelle-fr.html).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
