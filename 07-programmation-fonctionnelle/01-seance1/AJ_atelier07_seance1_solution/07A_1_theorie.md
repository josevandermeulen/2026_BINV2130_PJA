# Atelier 7 : Programmation fonctionnelle – séance 1 – Concepts

Les exercices associés à ces concepts se trouvent dans [`07A_2_exercices.md`](07A_2_exercices.md).

## À rendre cette semaine

- **Le QCM de la semaine sur mooVin** — ferme le mardi 03/11/2026 à 20h.

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

Reprenons justement cet exemple : avant Java 8, `List<T>` ne contenait pas la méthode `stream`.

Ajouter une méthode abstraite `stream` dans `Collection` et une méthode concrète dans `ArrayList` aurait pu convenir pour notre cas, mais quel cauchemar pour toutes les autres classes qui étendent `Collection` qu'il aurait alors fallu modifier. Il devenait impossible de faire évoluer les interfaces sans interrompre le fonctionnement des implémentations de celles-ci, quel dilemme ! C'est pour cela que Java 8 permet désormais d'avoir des méthodes `default` dans les interfaces ; les classes ne doivent pas fournir d'implémentation pour celles-ci. Il n'est donc plus obligatoire d'ajouter des implémentations dans toutes les classes qui implémentent des interfaces évoluant. Abracadabra !

Autrement dit, les interfaces permettent de faire comme si on ajoutait l'implémentation d'une méthode à une classe sans modifier celle-ci.

Prenons un exemple concret. Il est possible d'invoquer la méthode `sort` sur une `ArrayList`, pourtant, cette dernière n'en possède aucune implémentation directe. L'interface `List` propose une implémentation par défaut de celle-ci. Voici, à peu de chose près, ce qu'on trouve dans le JDK :

```java
public interface List<E> extends Collection<E> {
    // ...
    default void sort(Comparator<? super E> c) {
        Object[] a = this.toArray();
        Arrays.sort(a, (Comparator) c);
        ListIterator<E> i = this.listIterator();
        for (Object e : a) {
            i.next();
            i.set((E) e);
        }
    }
}
```

Le mot-clé `default` permet d'écrire le corps d'une méthode directement dans l'interface. `ArrayList`, `LinkedList` et toutes les classes écrites avant Java 8 qui implémentent `List` reçoivent cette méthode sans avoir été modifiées. Remarquez que ce code n'utilise que d'autres méthodes de l'interface (`toArray`, `listIterator`) : il ne sait rien de la façon dont la liste est stockée.

Le même mécanisme fonctionne dans vos propres interfaces :

```java
public interface Forme {
    double aire();                      // abstraite : chaque classe doit l'implémenter

    default String decrire() {          // default : implémentation fournie
        return "Forme d'aire " + aire();
    }
}

public class Carre implements Forme {
    private final double cote;

    public Carre(double cote) {
        this.cote = cote;
    }

    @Override
    public double aire() {
        return cote * cote;
    }
    // pas besoin d'écrire decrire()
}
```

`new Carre(2).decrire()` renvoie `"Forme d'aire 4.0"`. Une classe peut aussi redéfinir `decrire` (avec `@Override`) si l'implémentation par défaut ne lui convient pas.

#### Les contraintes à respecter

1. **Pas d'état.** Une interface n'a pas d'attributs d'instance (seulement des constantes `static final`). Une méthode `default` ne peut donc travailler qu'avec ses paramètres et les autres méthodes de l'interface, comme `decrire` qui appelle `aire`.
2. **Toujours `public`.** Une méthode `default` est implicitement publique ; elle ne peut être ni `private`, ni `protected`, ni `final`. Une classe qui la redéfinit doit donc aussi la déclarer `public`.
3. **Pas de `default` pour `equals`, `hashCode` ou `toString`.** Ces méthodes existent déjà dans `Object`, dont hérite toute classe : le compilateur refuse `default String toString() { ... }` dans une interface (*default method toString in interface … overrides a member of java.lang.Object*).
4. **La classe gagne.** Si la classe (ou une de ses superclasses) définit une méthode de même signature, c'est elle qui est utilisée, jamais la version `default`.
5. **Conflit entre deux interfaces.** Si une classe implémente deux interfaces qui proposent chacune une méthode `default` de même signature, le code ne compile pas tant que la classe ne la redéfinit pas. Elle peut alors choisir (ou combiner) les versions avec la syntaxe `Interface.super.methode()` :

   ```java
   public interface Volant {
       default String seDeplacer() { return "Je vole"; }
   }

   public interface Nageant {
       default String seDeplacer() { return "Je nage"; }
   }

   public class Canard implements Volant, Nageant {
       @Override
       public String seDeplacer() {   // obligatoire, sinon erreur de compilation
           return Volant.super.seDeplacer() + " et " + Nageant.super.seDeplacer();
       }
   }
   ```

6. **Une méthode `default` n'est pas abstraite.** Une interface fonctionnelle (voir plus bas) peut donc en contenir autant qu'elle veut, tant qu'elle n'a qu'**une seule** méthode abstraite. `Comparator` en est un bon exemple : `reversed`, que vous avez utilisée dans les exercices, est une méthode `default` de `Comparator`.

### Petit détour par les Generics

En allant voir dans l'interface `List` la méthode `sort`, vous avez dû remarquer que sa signature comportait des éléments relatifs aux types génériques assez complexes :

```java
default void sort(Comparator<? super E> c)
```

Jusqu'à présent, nous avons principalement utilisé les génériques, mais nous n'avons pas abordé en détail la déclaration de méthodes ou de classes avec des génériques.

Observons cette signature plus en détail, en commençant par le type du paramètre `c` : le type `Comparator<? super E>` indique que le paramètre `c` est un `Comparator` destiné à comparer des objets de type `E` ou de ses superclasses. `E` est le type générique de l'élément de la `List`. Par exemple, si nous avons une `List<String>`, `E` serait de type `String`.

La raison pour laquelle on utilise `? super E` est pour offrir plus de flexibilité. Imaginez avoir une classe `Animal` et une sous-classe `Dog`. Si vous avez une `List<Dog>`, vous pourriez vouloir la trier en utilisant un `Comparator<Animal>`, car tous les `Dog` sont aussi des `Animal`.

Comme on l'a vu plus haut, la méthode `sort` de l'interface `List` copie ensuite les éléments dans un tableau, le trie avec `Arrays.sort` en utilisant le comparateur fourni, puis réécrit les éléments dans la liste. (C'est d'ailleurs l'inverse de ce qu'on pourrait croire : depuis Java 8, `Collections.sort(liste, c)` se contente d'appeler `liste.sort(c)`.)

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

La lambda expression dans ce contexte n'est autre qu'un raccourci pour l'implémentation et l'instanciation de l'interface `Predicate` ! La méthode `filter` va appeler `test` sur chacun des employés contenus dans le stream.

L'interface `Predicate` est dite **interface fonctionnelle** car elle ne possède qu'une seule méthode abstraite. Elle représente donc la définition d'une fonction.

### `Function`

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

### `Consumer`

Même raisonnement avec la méthode `forEach`, qui prend en paramètre un `Consumer`. Sa méthode abstraite est :

```java
void accept(T t);
```

Un `Consumer<T>` reçoit un objet de type `T` et ne renvoie rien : il « consomme » l'élément pour produire un effet — afficher, ajouter à une liste, écrire dans un fichier… La lambda `e -> System.out.println(e.getNom())` passée à un `forEach` sur des employés est un `Consumer<Employe>`.

### `Supplier`

Une autre interface fonctionnelle très utile est l'interface `Supplier`. Celle-ci possède une méthode `get` qui ne prend pas de paramètre et renvoie un élément d'un type donné. Comme son nom l'indique, l'interface `Supplier` sert à fournir des données comme, par exemple, lorsqu'on lit un fichier.

### Les lambdas dans un autre contexte

Les lambdas ne sont pas seulement utiles dans le contexte de l'API Stream : toute interface fonctionnelle peut être implémentée par une lambda. C'est le cas des `Comparator` que nous avons écrits lors du travail sur les collections ; leur unique méthode abstraite est `int compare(T o1, T o2)`. Au lieu de déclarer une classe :

```java
public class TailleComparator implements Comparator<Employe> {
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

Ce comparateur ne trie que sur la taille : deux employés de même taille restent dans un ordre quelconque. Quand la comparaison demande plusieurs instructions — un `if` pour départager les ex æquo, par exemple —, le corps de la lambda devient un **bloc** entre accolades, avec un `return` explicite, exactement comme le corps de la méthode `compare` :

```java
employes.sort((e1, e2) -> {
    int ecart = e2.getTaille() - e1.getTaille();
    // ... d'autres instructions si nécessaire
    return ecart;
});
```

### Les références de méthode

Vous avez croisé la notation `Employe::getTaille` dans les exercices :

```java
.sorted(Comparator.comparingInt(Employe::getTaille).reversed())
```

Une référence de méthode est un raccourci d'écriture pour une lambda qui ne fait qu'appeler une méthode existante : `Employe::getTaille` équivaut à `e -> e.getTaille()`. Quand une lambda a cette forme, IntelliJ propose d'ailleurs la conversion automatiquement.

Une référence de méthode n'est donc rien d'autre qu'une lambda écrite plus court : elle implémente elle aussi la méthode abstraite d'une interface fonctionnelle.

#### Les quatre formes

| Forme | Exemple | Lambda équivalente |
|---|---|---|
| Méthode `static` : `Classe::methode` | `Integer::parseInt` | `s -> Integer.parseInt(s)` |
| Méthode d'**un objet précis** : `objet::methode` | `System.out::println` | `x -> System.out.println(x)` |
| Méthode d'instance appelée **sur le paramètre** : `Classe::methode` | `Employe::getNom` | `e -> e.getNom()` |
| Constructeur : `Classe::new` | `ArrayList::new` | `() -> new ArrayList<>()` |

La troisième forme est la plus subtile. On écrit le nom de la classe, mais la méthode n'est pas `static` : le **premier paramètre** de la lambda devient l'objet sur lequel la méthode est appelée, et les paramètres suivants deviennent ses arguments. Avec deux paramètres :

```java
Comparator<String> c = String::compareToIgnoreCase;
// équivaut à (s1, s2) -> s1.compareToIgnoreCase(s2)
```

#### Le type dépend du contexte

Comme une lambda, une référence de méthode n'a pas de type par elle-même : c'est l'interface fonctionnelle attendue à cet endroit qui le fixe. C'est particulièrement visible avec les constructeurs, où Java choisit le constructeur qui correspond aux paramètres de la méthode abstraite :

```java
Supplier<StringBuilder> s1 = StringBuilder::new;          // () -> new StringBuilder()
Function<String, StringBuilder> s2 = StringBuilder::new;  // texte -> new StringBuilder(texte)
```

Notre classe `Employe` n'a qu'un constructeur à trois paramètres (genre, taille, nom). `Employe::new` ne peut donc pas être un `Supplier<Employe>`, car il faudrait un constructeur sans paramètre : il faudrait une interface fonctionnelle dont la méthode abstraite reçoit trois paramètres, ce que le JDK ne fournit pas.

#### En pratique dans un stream

```java
employes.stream()
        .filter(Main::estGrand)                    // méthode static de notre classe Main
        .sorted(Comparator.comparing(Employe::getNom))
        .map(Employe::getNom)
        .forEach(System.out::println);
```

avec, dans la classe `Main` :

```java
private static boolean estGrand(Employe e) {
    return e.getTaille() > 180;
}
```

Cette astuce est utile quand une lambda devient longue : on la déplace dans une méthode bien nommée et on passe une référence vers celle-ci. Le stream se lit alors comme une phrase.

#### Quand ce n'est pas possible

Une référence de méthode ne fonctionne que si la lambda se contente d'appeler **une** méthode en lui transmettant ses paramètres tels quels, dans le même ordre. Ces lambdas-ci ne peuvent pas être converties :

```java
e -> e.getTaille() > 180        // un calcul en plus de l'appel
e -> e.getNom().toUpperCase()   // deux appels enchaînés
e -> e.getTaille() * 2          // un calcul en plus de l'appel
```

Pour les écrire avec une référence de méthode, il faut d'abord en faire une méthode nommée, comme `estGrand` ci-dessus.

---

*Passez aux [exercices](07A_2_exercices.md).*

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cheat sheet de cette semaine : [consultez-la en ligne](https://astounding-queijadas-0f428a.netlify.app/07-programmation-fonctionnelle-fr.html).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
