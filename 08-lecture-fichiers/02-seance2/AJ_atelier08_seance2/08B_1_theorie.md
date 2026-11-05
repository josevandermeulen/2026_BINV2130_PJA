# Atelier 8 : Lecture de fichiers, try-with-resources – séance 2 – Concepts

Les exercices associés à ces concepts se trouvent dans [`08B_2_exercices.md`](08B_2_exercices.md).

Cette séance reprend la pizzeria de la séance 1 pour revenir sur sept points du langage restés fragiles depuis l'atelier 2, ou jamais vus : l'énuméré vu comme une classe, la `Map` dont la valeur est une collection, `Comparable` et `Comparator`, les varargs, le type de retour covariant, le clonage et les classes internes. Elle se termine par un rappel des stubs et des mocks de l'atelier 5.

## Vidéos

1. [Apprendre Java #15 Fichier (File / Reader / Writer / Buffer)](https://www.youtube.com/watch?v=iCobhKfvf3M)
2. [Java File Input/Output - It's Way Easier Than You Think](https://www.youtube.com/watch?v=ScUJx4aWRi0)
3. [Exception Handling in Java Tutorial](https://www.youtube.com/watch?v=1XAfapkBQjk)
4. [Checked vs. Unchecked Exceptions in Java Tutorial - What's The Difference?](https://www.youtube.com/watch?v=bCPClyGsVhc)
5. [#79 Exception Hierarchy in Java](https://www.youtube.com/watch?v=IBttEEFkH_Y)
6. [#84 try with resources in Java](https://www.youtube.com/watch?v=hVQR0ZHFk-o)
7. [Java Enums Explained in 6 Minutes](https://www.youtube.com/watch?v=wq9SJb8VeyM)
8. [How to use computeIfAbsent method to prepare multi-value HashMap?](https://www.youtube.com/watch?v=S_sniYjlbLA)
9. [Inner Class Java Tutorial - Creating and Using Inner Classes](https://www.youtube.com/watch?v=BwkmIXjWWJc)
10. [Java Anonymous Inner Classes Explained in 6 Minutes](https://www.youtube.com/watch?v=DwtPWZn6T1A)
11. [Learn VARARGS in 6 minutes!](https://www.youtube.com/watch?v=2knxgAFqwKk)

## Un énuméré est une classe

Un énuméré n'est pas une simple liste de noms : c'est une **classe**, dont les constantes sont les seules instances. Chaque constante est créée une fois pour toutes, au chargement de la classe, par le constructeur de l'énuméré, avec les arguments écrits entre parenthèses :

```java
public enum Categorie {

    VEGETARIENNE("végétarienne"),

    VIANDE("viande"),

    POISSON("poisson");

    private final String libelle;

    Categorie(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }

}
```

`Categorie.VIANDE` est donc un objet, qui a ses propres attributs et sur lequel on appelle des méthodes comme sur n'importe quel objet : `Categorie.VIANDE.getLibelle()` renvoie `"viande"`. Une méthode de l'énuméré peut utiliser `this`, qui désigne la constante sur laquelle elle est appelée :

```java
public boolean estPoisson() {
    return this == POISSON;
}
```

Deux différences avec une classe ordinaire : le constructeur est toujours privé (on ne peut pas écrire `new Categorie(...)`), et les constantes se comparent avec `==`, puisqu'il n'en existe qu'un exemplaire chacune.

Tout énuméré reçoit en plus, sans rien écrire, quelques méthodes utiles :

- `Categorie.values()` renvoie un tableau de toutes les constantes, dans l'ordre de déclaration, ce qui permet de les parcourir avec un `for` ;
- `Categorie.valueOf("VIANDE")` renvoie la constante de ce nom, et lève une `IllegalArgumentException` si aucune ne porte ce nom ;
- `name()` renvoie le nom de la constante tel qu'il est écrit dans le code (`"VIANDE"`), à ne pas confondre avec un libellé choisi par le programmeur comme `getLibelle`.

Un énuméré implémente aussi `Comparable` : son ordre naturel est l'**ordre de déclaration** des constantes. Ici, `VEGETARIENNE` se place avant `VIANDE`, qui se place avant `POISSON`, et `VEGETARIENNE.compareTo(POISSON)` est négatif. Changer l'ordre des constantes dans le fichier change donc l'ordre des tris.

## Une `Map` dont la valeur est une collection

Pour ranger les pizzas par catégorie, on associe à chaque catégorie **un ensemble** de pizzas : `Map<Categorie, SortedSet<Pizza>>`. La valeur de la `Map` n'est pas une pizza, mais une collection, qu'il faut créer avant d'y ajouter quoi que ce soit.

Écrit à la main, l'ajout d'une pizza vérifie d'abord si l'ensemble de sa catégorie existe :

```java
SortedSet<Pizza> pizzasDeLaCategorie = carte.get(pizza.getCategorie());
if (pizzasDeLaCategorie == null) {
    pizzasDeLaCategorie = new TreeSet<>();
    carte.put(pizza.getCategorie(), pizzasDeLaCategorie);
}
pizzasDeLaCategorie.add(pizza);
```

La méthode `computeIfAbsent` fait la même chose en une ligne : elle renvoie la valeur associée à la clé, et, s'il n'y en a pas encore, la crée avec la fonction reçue et l'enregistre d'abord :

```java
carte.computeIfAbsent(pizza.getCategorie(), categorie -> new TreeSet<>()).add(pizza);
```

À l'inverse, quand on retire un élément, l'ensemble peut devenir vide. On retire alors aussi la clé, pour que la `Map` ne garde pas de catégorie sans pizza.

### Rendre une collection en lecture seule

Un getter qui renvoie directement la collection rangée dans la `Map` donne à l'appelant de quoi la modifier dans le dos de l'objet : il pourrait y ajouter une pizza sans passer par `ajouterPizza`, et donc sans aucune vérification. On renvoie plutôt une **vue non modifiable**, que la classe `Collections` fournit pour chaque type de collection :

```java
public List<Commande> getHistorique(Client client) {
    List<Commande> commandes = historique.get(client);
    if (commandes == null) {
        return Collections.emptyList();
    }
    return Collections.unmodifiableList(commandes);
}
```

Une vue n'est pas une copie : elle lit la collection d'origine, et reflète donc les ajouts faits ensuite par l'objet lui-même ; seules les modifications tentées **à travers elle** lèvent une `UnsupportedOperationException`. `List.copyOf` ou `Set.copyOf`, à l'inverse, prennent une copie figée à l'instant de l'appel. Les deux protègent l'objet ; seule la copie isole aussi l'appelant des changements ultérieurs.

Pour une clé absente, `get` renvoie `null`. Plutôt que de propager ce `null`, on renvoie une collection vide (`Collections.emptyList()`, `emptySet()`, `emptySortedSet()`…) : l'appelant peut la parcourir sans test préalable.

## `Comparable` et `Comparator`

Une classe qui implémente `Comparable<T>` a un **ordre naturel**, défini par sa méthode `compareTo` : c'est celui qu'utilisent `Collections.sort(liste)` et un `TreeSet`. `Pizza` implémente `Comparable<Pizza>` : son ordre naturel est l'ordre alphabétique des titres. Une classe n'a qu'un ordre naturel.

Un `Comparator<T>` est un ordre **extérieur** aux objets comparés, défini par sa méthode `compare(T o1, T o2)` : on peut en écrire autant qu'on veut, et le passer à `sort`. Les deux méthodes suivent la même convention : un résultat négatif si le premier objet se place avant le second, nul s'ils sont au même rang, positif s'il se place après.

Un `Comparator` peut s'écrire de trois façons, du plus long au plus court. Les trois exemples ci-dessous trient le menu par prix.

En **classe nommée**, dans son propre fichier :

```java
public class ComparateurPrix implements Comparator<Pizza> {

    @Override
    public int compare(Pizza pizza1, Pizza pizza2) {
        return Double.compare(pizza1.calculerPrix(), pizza2.calculerPrix());
    }

}

menu.sort(new ComparateurPrix());
```

En **classe anonyme**, déclarée et instanciée à l'endroit même où on l'utilise, sans lui donner de nom :

```java
menu.sort(new Comparator<Pizza>() {
    @Override
    public int compare(Pizza pizza1, Pizza pizza2) {
        return Double.compare(pizza1.calculerPrix(), pizza2.calculerPrix());
    }
});
```

En **lambda** (atelier 7), puisque `Comparator` est une interface fonctionnelle :

```java
menu.sort((pizza1, pizza2) -> Double.compare(pizza1.calculerPrix(), pizza2.calculerPrix()));
```

Pour comparer deux entiers ou deux réels, utilisez `Integer.compare` et `Double.compare` plutôt qu'une soustraction : `a - b` peut déborder, et une différence de réels arrondie à l'entier perd les écarts de moins de 1. Pour départager deux objets à égalité, comparez sur un second critère, par exemple l'ordre naturel (`pizza1.compareTo(pizza2)`).

Pour un ordre **décroissant**, il suffit d'inverser les arguments de la comparaison : `Double.compare(pizza2.calculerPrix(), pizza1.calculerPrix())` range la pizza la plus chère en premier. Avec un second critère, chacun garde son propre sens :

```java
menu.sort((pizza1, pizza2) -> {
    int parPrix = Double.compare(pizza2.calculerPrix(), pizza1.calculerPrix());   // décroissant
    if (parPrix != 0) {
        return parPrix;
    }
    return pizza1.compareTo(pizza2);                                              // puis par titre
});
```

Les méthodes `Comparator.comparing`, `thenComparing` et `reversed`, vues à l'atelier 6, construisent les mêmes ordres sans écrire `compare` à la main. Elles sont pratiques au quotidien, mais elles cachent la mécanique : dans les exercices de cette séance, écrivez la comparaison vous-même, sous la forme demandée.

## Les varargs

Un paramètre **varargs** (*variable arguments*) accepte un nombre quelconque d'arguments du même type, y compris aucun. Il s'écrit avec trois points après le type, et doit être le dernier paramètre de la méthode :

```java
public double prixTotal(Pizza... pizzas) {
    double total = 0;
    for (Pizza pizza : pizzas) {
        total += pizza.calculerPrix();
    }
    return total;
}
```

Dans le corps de la méthode, `pizzas` est un tableau, `Pizza[]`. À l'appel, on passe les arguments séparés par des virgules, ou directement un tableau :

```java
prixTotal(MenuPizzeria.PIZZA_MARGARITA, MenuPizzeria.PIZZA_TONNO);
prixTotal();
prixTotal(new Pizza[] {MenuPizzeria.PIZZA_DIAVOLA});
```

`List.of(...)` et `String.format(...)`, que vous utilisez depuis le début de l'année, prennent un varargs. Si une méthode est surchargée, une version avec un paramètre unique et une version varargs, l'appel avec un seul argument choisit la version à paramètre unique : le compilateur ne se rabat sur le varargs que si aucune autre version ne convient.

Puisque le varargs est un tableau, on peut le transmettre tel quel à une autre méthode varargs, par exemple pour le convertir en collection :

```java
public Menu(Pizza... pizzas) {
    this.pizzas = Set.of(pizzas);
}
```

`Set.of` refuse les doublons (`IllegalArgumentException`) et les `null` (`NullPointerException`), et renvoie un ensemble non modifiable. Si l'on doit accepter des doublons, ou modifier l'ensemble ensuite, on passe par `new HashSet<>(Arrays.asList(pizzas))`.

## Le type de retour covariant

Une méthode qui en redéfinit une autre (`@Override`) doit garder la même signature, mais peut **restreindre son type de retour** à un sous-type : c'est le type de retour covariant.

```java
public abstract class Pizza {

    public abstract Pizza copier();

}

public class PizzaComposable extends Pizza {

    @Override
    public PizzaComposable copier() {
        // ...
    }

}
```

La redéfinition renvoie une `PizzaComposable`, qui est une `Pizza` : le contrat de `Pizza.copier` est respecté. L'intérêt est du côté de l'appelant. Celui qui manipule une `PizzaComposable` récupère une `PizzaComposable`, sans transtypage, et peut continuer à lui ajouter des ingrédients ; celui qui ne connaît qu'une `Pizza` reçoit une `Pizza`, comme prévu.

```java
PizzaComposable copie = maPizza.copier();   // maPizza est une PizzaComposable : pas de (PizzaComposable)
```

Seul le type de retour peut ainsi varier, et seulement vers un sous-type : les paramètres, eux, doivent rester identiques, sans quoi on écrit une surcharge, et non une redéfinition.

## Le clonage

Le clonage a été vu à l'atelier 2 ([`02B_1_theorie.md`](../../../02-collections-enumeres/02-seance2/AJ_atelier02_seance2/02B_1_theorie.md), section *Clonage d'objets*, et la vidéo [Clone, copie superficielle et profonde en Java](https://www.youtube.com/watch?v=la-CVGl3k7E)). On y revient ici pour la pizzeria.

Une classe clonable implémente l'interface `Cloneable` (une interface marqueur, sans méthode) et redéfinit `clone`, `protected` dans `Object`, en `public`. `super.clone()` lance une `CloneNotSupportedException` (vérifiée) quand la classe n'implémente pas `Cloneable` ; comme elle l'implémente, l'exception ne peut pas se produire, et on la transforme en `AssertionError` :

```java
@Override
public Boisson clone() {
    try {
        return (Boisson) super.clone();
    } catch (CloneNotSupportedException e) {
        throw new AssertionError(e);
    }
}
```

### Copie superficielle, copie profonde

`super.clone()` crée un nouvel objet **de la même classe réelle** que l'original et recopie chaque attribut tel quel, sans appeler aucun constructeur. Pour un attribut qui référence une collection, c'est la **référence** qui est recopiée : l'original et le clone partagent alors la même liste.

```java
Pizza copie = (Pizza) super.clone();   // copie.ingredients et ingredients : la MÊME ArrayList
copie.ajouter(Ingredients.JAMBON);     // ... et le jambon apparaît aussi sur l'original
```

Pour une copie **profonde**, on remplace, après `super.clone()`, chaque attribut mutable du clone par une copie indépendante. Ici, une nouvelle liste contenant les mêmes ingrédients suffit. Inutile, en revanche, de cloner chaque `Ingredient` : ce sont les ingrédients du catalogue (`Ingredients.TOMATE`, …), partagés par toutes les pizzas, et si le prix de la tomate change, il doit changer partout. Ce qu'il faut dupliquer, c'est ce qui appartient en propre à l'objet : sa liste, pas les ingrédients qu'elle désigne.

### `clone` et le type de retour covariant

`Object.clone` renvoie un `Object`. Une redéfinition peut restreindre ce type (section précédente) : `Pizza.clone` renvoie une `Pizza`, et `PizzaComposable` peut redéfinir à nouveau `clone` pour renvoyer une `PizzaComposable`. Le transtypage du résultat de `super.clone()` est sûr, puisque l'objet créé est de la classe réelle de l'original.

### `clone` ou `copier` ?

`copier` passe par un constructeur : une `PizzaComposable` copiée est une **nouvelle** pizza, datée de l'instant de la copie. `clone` n'appelle aucun constructeur : le clone garde la date et le créateur de l'original — c'est la même pizza, en un second exemplaire.

## Les classes internes

Une classe peut être déclarée **à l'intérieur** d'une autre. On le fait quand elle n'a de sens qu'avec la classe qui l'entoure.

Une classe interne **statique** (`static class`) est une classe ordinaire, simplement rangée dans une autre : elle n'est rattachée à aucune instance de la classe englobante et n'a pas accès à ses attributs d'instance. De l'extérieur, on la nomme et on l'instancie par le nom de la classe englobante :

```java
public class Pizzeria {

    public static class Bilan {

        private int nombreCommandes;

        public Bilan(int nombreCommandes) {
            this.nombreCommandes = nombreCommandes;
        }

    }

}

Pizzeria.Bilan bilan = new Pizzeria.Bilan(12);
```

Une classe interne **non statique** (sans `static`) est au contraire rattachée à **une** instance de la classe englobante, celle qui l'a créée. Elle accède directement à tous ses attributs, même privés : `commandes` y désigne `Pizzeria.this.commandes`. Elle s'instancie donc à partir d'une instance, le plus souvent par une méthode de la classe englobante :

```java
public class Pizzeria {

    private Set<Commande> commandes = new LinkedHashSet<>();

    public Fidelite fidelite(int seuil) {
        return new Fidelite(seuil);
    }

    public class Fidelite {

        private int seuil;

        public Fidelite(int seuil) {
            this.seuil = seuil;
        }

        public boolean aDesCommandes() {
            return !commandes.isEmpty();   // l'attribut de la Pizzeria englobante
        }

    }

}
```

Si un attribut de la classe interne porte le même nom qu'un attribut de la classe englobante, le nom seul désigne celui de la classe interne ; on atteint l'autre en écrivant `Pizzeria.this.nom`. De l'extérieur, on peut aussi instancier une classe interne non statique sans passer par une méthode, avec une syntaxe peu courante qui montre bien qu'il faut une instance englobante : `pizzeria.new Fidelite(3)`.

La règle de choix : si la classe interne n'a pas besoin des attributs de l'objet englobant, déclarez-la `static`. Une classe interne non statique garde une référence vers son objet englobant, ce qui ne se justifie que si elle s'en sert.

Les classes anonymes vues plus haut avec `Comparator` sont elles aussi des classes internes, déclarées dans une méthode. Comme les lambdas, elles peuvent lire les variables locales de la méthode qui les entoure, à condition que celles-ci ne soient jamais réaffectées (*effectivement finales*).

## Stubs et mocks : rappel

La `Pizzeria` reçoit à sa construction un `Stock`, une interface. Pour tester une méthode qui l'interroge sans dépendre d'un vrai stock, on lui passe une fausse implémentation, comme à l'atelier 5.

Un **stub** est une classe écrite à la main, qui implémente l'interface et dont on fixe les réponses à la construction. Le test regarde ensuite le **résultat** : la méthode testée a-t-elle renvoyé la bonne valeur, l'objet est-il dans le bon état ?

Un **mock** Mockito est créé sans écrire de classe. On le configure, puis on peut vérifier en plus les **appels** qu'il a reçus. Rappel de la syntaxe, sur l'exemple de l'atelier 5 :

```java
Stage stage = Mockito.mock(Stage.class);

Mockito.when(stage.enregistrerMoniteur(Mockito.any(Moniteur.class))).thenReturn(true);   // règle générale
Mockito.when(stage.enregistrerMoniteur(moniteur)).thenReturn(false);                     // cas particulier

// ... appel de la méthode testée ...

Mockito.verify(stage).enregistrerMoniteur(moniteur);              // appelée une fois avec moniteur
Mockito.verify(stage, Mockito.never()).enregistrerMoniteur(autre); // jamais appelée avec autre
Mockito.verifyNoMoreInteractions(stage);                          // et aucun autre appel
```

Trois points à garder en tête :

- quand deux règles `when` s'appliquent à un même appel, c'est **la dernière écrite** qui l'emporte : on écrit donc la règle générale (`any`) d'abord, les cas particuliers ensuite ;
- un mock non configuré renvoie la valeur par défaut du type : `false`, `0` ou `null`. Un oubli de `when` ne fait pas planter le test, il le fausse ;
- `verifyNoMoreInteractions` échoue si le mock a reçu un appel qu'aucun `verify` n'a vérifié.

Avec `import static org.mockito.Mockito.*;`, on écrit simplement `mock`, `when`, `any`, `verify`… sans le préfixe `Mockito.`.

Enfin, un test qui ne peut pas échouer ne prouve rien : une fois un test écrit, cassez volontairement la méthode testée et vérifiez qu'il devient rouge.

Le projet embarque ses dépendances dans un dossier `repo/`, déclaré dans le `pom.xml` : JUnit 5 et Mockito y sont résolus sans connexion internet, comme ce sera le cas à l'examen.

## Petit résumé

1. Un énuméré est une classe : ses constantes sont des objets, avec leurs attributs et leurs méthodes. Son ordre naturel est l'ordre de déclaration.
2. Une `Map` dont la valeur est une collection se remplit avec `computeIfAbsent`, et perd sa clé quand la collection devient vide. Ses collections se montrent en lecture seule (`Collections.unmodifiable…`, ou une collection vide pour une clé absente).
3. `Comparable` donne l'ordre naturel (un seul), `Comparator` un ordre extérieur (autant qu'on veut), en classe nommée, anonyme ou en lambda. Inverser les arguments inverse l'ordre.
4. Un varargs (`Type... nom`) est un tableau dans la méthode, et accepte de zéro à n arguments à l'appel.
5. Une redéfinition peut restreindre son type de retour à un sous-type.
6. `super.clone()` fait une copie superficielle, sans constructeur ; une copie profonde remplace ensuite chaque attribut mutable propre à l'objet (ici, la liste d'ingrédients) par une copie.
7. Une classe interne statique n'est liée à aucune instance ; une classe interne non statique accède aux attributs de l'instance englobante.
8. Un stub fixe les réponses à la main et le test vérifie le résultat ; un mock se configure avec `when` et permet en plus de vérifier les appels avec `verify`.

---

*Passez aux [exercices](08B_2_exercices.md).*

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cheat sheet de cette semaine : [consultez-la en ligne](https://astounding-queijadas-0f428a.netlify.app/08-lecture-fichiers-fr.html).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
