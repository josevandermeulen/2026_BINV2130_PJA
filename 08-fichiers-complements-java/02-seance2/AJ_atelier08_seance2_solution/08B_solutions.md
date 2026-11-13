# Atelier 8 : Lecture de fichiers et compléments Java – séance 2 : solutions

*Énoncé : [`08B_2_exercices.md`](../AJ_atelier08_seance2/08B_2_exercices.md) — théorie : [`08B_1_theorie.md`](../AJ_atelier08_seance2/08B_1_theorie.md).*

## L'énuméré `Categorie`

### Question 1

**Énoncé :** Ouvrez l'énuméré `Categorie` (package `domaine`). Complétez la méthode `estVegetarienne`, qui renvoie `true` pour la seule constante `VEGETARIENNE`. Une méthode d'un énuméré s'appelle sur une constante, comme sur n'importe quel objet : […]

[`src/main/java/domaine/Categorie.java`](src/main/java/domaine/Categorie.java) :

```java
public enum Categorie {
    // ...
    /**
     * Indique si la catégorie est végétarienne.
     *
     * @return true pour VEGETARIENNE, false pour les autres catégories
     */
    public boolean estVegetarienne() {
        return this == VEGETARIENNE;
    }

}
```

*Vérifié par : `CategorieTest` (classe `Question1`).*

### Question 2

**Énoncé :** Ajoutez à `Categorie` un deuxième attribut, l'abréviation imprimée sur la carte (`"V"`, `"VI"` et `"P"`), initialisé par le constructeur de l'énuméré à partir d'un second argument donné à chaque constante, puis complétez `getAbreviation`.

*un deuxième attribut* — [`src/main/java/domaine/Categorie.java`](src/main/java/domaine/Categorie.java) :

```java
public enum Categorie {
    // ...
    VEGETARIENNE("végétarienne", "V"),

    VIANDE("viande", "VI"),

    POISSON("poisson", "P");

    private final String libelle;

    private final String abreviation;

    Categorie(String libelle, String abreviation) {
        this.libelle = libelle;
        this.abreviation = abreviation;
    }

    /**
     * Renvoie l'abréviation de la catégorie, imprimée à côté du titre sur la carte.
     *
     * @return l'abréviation de la catégorie
     */
    public String getAbreviation() {
        return abreviation;
    }
    // ...
}
```

*Vérifié par : `CategorieTest` (classe `Question2`).*

## La carte par catégorie

### Question 3

**Énoncé :** La `Pizzeria` a un nouvel attribut, `carte`, une `Map<Categorie, SortedSet<Pizza>>` qui range les pizzas du menu par catégorie. Complétez `ajouterPizza` pour qu'elle range aussi la pizza ajoutée sur la carte, en créant l'ensemble de sa catégorie au premier […]

*ranger la pizza sur la carte* — [`src/main/java/domaine/Pizzeria.java`](src/main/java/domaine/Pizzeria.java) :

```java
public class Pizzeria {
    // ...
    /**
     * Ajoute une pizza au menu, sauf si une pizza porte déjà le même titre, et la range sur la
     * carte dans sa catégorie.
     *
     * @param pizza la pizza à ajouter
     * @return true si la pizza a été ajoutée, false si son titre est déjà au menu
     * @throws IllegalArgumentException si la pizza est null
     */
    public boolean ajouterPizza(Pizza pizza) {
        Util.checkObject(pizza);

        if (pizzas.containsKey(pizza.getTitre())) {
            return false;
        }
        pizzas.put(pizza.getTitre(), pizza);

        // l'ensemble de la catégorie n'est créé qu'au premier ajout d'une pizza de cette catégorie
        carte.computeIfAbsent(pizza.getCategorie(), categorie -> new TreeSet<>()).add(pizza);
        return true;
    }
    // ...
}
```

*Vérifié par : `PizzeriaSeance2Test` (classe `Question3`).*

### Question 4

**Énoncé :** Complétez `getPizzas(Categorie)`, qui renvoie l'ensemble des pizzas d'une catégorie en lecture seule (`Collections.unmodifiableSortedSet`), ou un ensemble vide (`Collections.emptySortedSet`) si la carte n'en a aucune.

[`src/main/java/domaine/Pizzeria.java`](src/main/java/domaine/Pizzeria.java) :

```java
public class Pizzeria {
    // ...
    /**
     * Renvoie les pizzas du menu d'une catégorie, dans l'ordre naturel des pizzas.
     *
     * @param categorie la catégorie demandée
     * @return les pizzas de la catégorie, en lecture seule ; un ensemble vide si le menu n'en
     *         compte aucune
     * @throws IllegalArgumentException si la catégorie est null
     */
    public SortedSet<Pizza> getPizzas(Categorie categorie) {
        Util.checkObject(categorie);

        SortedSet<Pizza> pizzasDeLaCategorie = carte.get(categorie);
        if (pizzasDeLaCategorie == null) {
            return Collections.emptySortedSet();
        }
        return Collections.unmodifiableSortedSet(pizzasDeLaCategorie);
    }
    // ...
}
```

*Vérifié par : `PizzeriaSeance2Test` (classe `Question4`).*

### Question 5

**Énoncé :** Complétez `retirerPizza(String titre)` : la pizza quitte le menu et la carte, et une catégorie dont la dernière pizza est retirée disparaît de la carte (la méthode fournie `aDesPizzas` renvoie alors `false`).

[`src/main/java/domaine/Pizzeria.java`](src/main/java/domaine/Pizzeria.java) :

```java
public class Pizzeria {
    // ...
    /**
     * Retire une pizza du menu et de la carte. Une catégorie dont la dernière pizza est retirée
     * disparaît de la carte.
     *
     * @param titre le titre de la pizza à retirer
     * @return true si la pizza a été retirée, false si aucune pizza du menu ne porte ce titre
     * @throws IllegalArgumentException si le titre est null ou vide
     */
    public boolean retirerPizza(String titre) {
        Util.checkString(titre);

        Pizza pizza = pizzas.remove(titre);
        if (pizza == null) {
            return false;
        }

        SortedSet<Pizza> pizzasDeLaCategorie = carte.get(pizza.getCategorie());
        pizzasDeLaCategorie.remove(pizza);
        if (pizzasDeLaCategorie.isEmpty()) {
            carte.remove(pizza.getCategorie());
        }
        return true;
    }
    // ...
}
```

*Vérifié par : `PizzeriaSeance2Test` (classe `Question5`).*

## Trier le menu

### Question 6

**Énoncé :** Complétez la méthode `compare` de la classe `ComparateurNombreIngredients` : elle range les pizzas de la plus garnie à la moins garnie et, à nombre d'ingrédients égal, dans leur ordre naturel. Utilisez `Integer.compare` plutôt qu'une soustraction.

[`src/main/java/domaine/ComparateurNombreIngredients.java`](src/main/java/domaine/ComparateurNombreIngredients.java) :

```java
package domaine;

/**
 * Range les pizzas de la plus garnie à la moins garnie ; à nombre d'ingrédients égal, dans l'ordre
 * naturel des pizzas (l'ordre des titres).
 *
 * Un Comparator est un ordre extérieur aux objets comparés : on peut en écrire autant qu'on veut,
 * alors qu'une classe n'a qu'un ordre naturel (Comparable).
 */
public class ComparateurNombreIngredients implements Comparator<Pizza> {

    /**
     * Compare deux pizzas sur leur nombre d'ingrédients, du plus grand au plus petit.
     *
     * @param pizza1 la première pizza
     * @param pizza2 la seconde pizza
     * @return un entier négatif si pizza1 a plus d'ingrédients que pizza2, positif si elle en a
     *         moins ; à égalité, le résultat de pizza1.compareTo(pizza2)
     */
    @Override
    public int compare(Pizza pizza1, Pizza pizza2) {
        int parNombre = Integer.compare(pizza2.getIngredients().size(), pizza1.getIngredients().size());
        if (parNombre != 0) {
            return parNombre;
        }
        return pizza1.compareTo(pizza2);
    }

}
```

*Vérifié par : `ComparateurNombreIngredientsTest` (classe `Question6`).*

### Question 7

**Énoncé :** Complétez `menuTrieParNombreIngredients` dans `Pizzeria`, qui trie le menu dans le même ordre, mais sans utiliser `ComparateurNombreIngredients` : passez à `sort` un `Comparator` écrit en **classe anonyme**.

[`src/main/java/domaine/Pizzeria.java`](src/main/java/domaine/Pizzeria.java) :

```java
public class Pizzeria {
    // ...
    /**
     * Renvoie le menu trié de la pizza la plus garnie à la moins garnie, à l'aide d'un Comparator
     * écrit en classe anonyme.
     *
     * @return les pizzas du menu, triées par nombre d'ingrédients décroissant, dans une liste non
     *         modifiable
     */
    public List<Pizza> menuTrieParNombreIngredients() {
        List<Pizza> menu = new ArrayList<>(pizzas.values());
        menu.sort(new Comparator<Pizza>() {
            @Override
            public int compare(Pizza pizza1, Pizza pizza2) {
                int parNombre = Integer.compare(pizza2.getIngredients().size(), pizza1.getIngredients().size());
                if (parNombre != 0) {
                    return parNombre;
                }
                return pizza1.compareTo(pizza2);
            }
        });
        return Collections.unmodifiableList(menu);
    }
    // ...
}
```

*Vérifié par : `PizzeriaSeance2Test` (classe `Question7`).*

### Question 8

**Énoncé :** Complétez `menuTrieParCategorie`, qui trie le menu par catégorie (dans l'ordre de déclaration de l'énuméré : les énumérés sont `Comparable`) puis par titre, avec un `Comparator` écrit en **lambda**.

[`src/main/java/domaine/Pizzeria.java`](src/main/java/domaine/Pizzeria.java) :

```java
public class Pizzeria {
    // ...
    /**
     * Renvoie le menu trié par catégorie (dans l'ordre de déclaration de l'énuméré), puis par titre,
     * à l'aide d'un Comparator écrit en lambda.
     *
     * @return les pizzas du menu, triées par catégorie puis par titre, dans une liste non modifiable
     */
    public List<Pizza> menuTrieParCategorie() {
        List<Pizza> menu = new ArrayList<>(pizzas.values());
        menu.sort((pizza1, pizza2) -> {
            int parCategorie = pizza1.getCategorie().compareTo(pizza2.getCategorie());
            if (parCategorie != 0) {
                return parCategorie;
            }
            return pizza1.compareTo(pizza2);
        });
        return Collections.unmodifiableList(menu);
    }
    // ...
}
```

*Vérifié par : `PizzeriaSeance2Test` (classe `Question8`).*

## Varargs et type de retour covariant

### Question 9

**Énoncé :** Complétez, dans `Pizza`, la méthode `ajouter(Ingredient... ingredients)`, qui ajoute en une fois tous les ingrédients reçus (en ignorant ceux déjà présents) et renvoie le nombre d'ingrédients effectivement ajoutés. Appuyez-vous sur la méthode […]

[`src/main/java/domaine/Pizza.java`](src/main/java/domaine/Pizza.java) :

```java
public abstract class Pizza implements Iterable<Ingredient>, Comparable<Pizza>, Cloneable {
    // ...
    /**
     * Ajoute plusieurs ingrédients à la pizza, en ignorant ceux qui s'y trouvent déjà. Le
     * paramètre est un varargs : on peut passer autant d'ingrédients qu'on veut, séparés par des
     * virgules, ou un tableau.
     *
     * @param ingredients les ingrédients à ajouter
     * @return le nombre d'ingrédients effectivement ajoutés
     * @throws IllegalArgumentException si le tableau ou l'un des ingrédients est null
     */
    public int ajouter(Ingredient... ingredients) {
        Util.checkObject(ingredients);

        int nombre = 0;
        for (Ingredient ingredient : ingredients) {
            if (ajouter(ingredient)) {
                nombre++;
            }
        }
        return nombre;
    }
    // ...
}
```

*Vérifié par : `PizzaSeance2Test` (classe `Question9`).*

### Question 10

**Énoncé :** `Pizza` déclare une méthode abstraite `Pizza copier()`, redéfinie dans `PizzaComposable` (fournie). Complétez sa redéfinition dans `PizzaComposee` : une nouvelle pizza de même titre, même description et mêmes ingrédients (dans une nouvelle liste).

[`src/main/java/domaine/PizzaComposee.java`](src/main/java/domaine/PizzaComposee.java) :

```java
public class PizzaComposee extends Pizza {
    // ...
    /**
     * Renvoie une nouvelle pizza de la carte, de même titre, même description et mêmes
     * ingrédients. Le type de retour est PizzaComposee : l'appelant n'a pas à transtyper.
     *
     * @return une copie de la pizza
     */
    @Override
    public PizzaComposee copier() {
        return new PizzaComposee(getTitre(), getDescription(), new ArrayList<>(getIngredients()));
    }
    // ...
}
```

*Vérifié par : `PizzaSeance2Test` (classe `Question10`).*

### Question 11

**Énoncé :** Les deux redéfinitions de `copier` ne renvoient pas une `Pizza`, mais une `PizzaComposable` et une `PizzaComposee`. Pourquoi le compilateur l'accepte-t-il, alors que la signature de `Pizza.copier` annonce une `Pizza` ? Qu'est-ce que cela change pour le […]

**le type de retour covariant**

Le compilateur l'accepte parce qu'une redéfinition peut **restreindre** son type de retour à un sous-type : c'est le type de retour covariant. Une `PizzaComposee` est une `Pizza`, si bien que le contrat de `Pizza.copier` (« je renvoie une `Pizza` ») reste respecté : celui qui appelle `copier` sur une variable de type `Pizza` reçoit bien une `Pizza`.

Pour l'appelant, cela évite le transtypage. Sur une variable de type `PizzaComposee`, `copier()` renvoie directement une `PizzaComposee` :

```java
PizzaComposee copie = MenuPizzeria.PIZZA_DIAVOLA.copier();
```

Si la redéfinition gardait `Pizza` comme type de retour, il faudrait écrire `(PizzaComposee) MenuPizzeria.PIZZA_DIAVOLA.copier()`, et le compilateur ne pourrait plus vérifier que la conversion est sûre.

Le changement inverse est refusé : une redéfinition qui annoncerait `Object` élargirait le type de retour, et celui qui attend une `Pizza` pourrait recevoir n'importe quel objet. Le compilateur signale alors un type de retour incompatible.

## Cloner une pizza

### Question 12

**Énoncé :** Rendez `Pizza` clonable et complétez sa méthode `clone` : le clone ne doit pas partager sa liste d'ingrédients avec l'original, de sorte qu'ajouter ou retirer un ingrédient de l'un ne change pas l'autre. Les ingrédients eux-mêmes, eux, restent partagés. […]

**Pizza.clone**

Deux gestes en plus du `try/catch` : `implements Cloneable` dans la déclaration de `Pizza` (sans quoi `super.clone()` lance une `CloneNotSupportedException`, transformée en `AssertionError`), et une nouvelle `ArrayList` pour le clone. L'affectation `copie.ingredients = …` est possible parce que `ingredients` n'est pas `final`, et parce que le code est dans `Pizza` : l'attribut est privé, mais l'accès est permis depuis la classe elle-même, sur n'importe quelle instance. Le piège classique est de s'arrêter à `return (Pizza) super.clone();` : ça compile et renvoie bien un nouvel objet, mais les deux pizzas partagent la même liste. Les tests `ajouterAuCloneNeModifiePasLOriginal` et `retirerDeLOriginalNeModifiePasLeClone` le détectent. On ne clone pas les `Ingredient` : ce sont les objets du catalogue, partagés volontairement. `PizzaComposee` hérite de ce `clone` sans rien redéfinir : `super.clone()` crée un objet de la classe réelle, donc une `PizzaComposee`.

[`src/main/java/domaine/Pizza.java`](src/main/java/domaine/Pizza.java) :

```java
public abstract class Pizza implements Iterable<Ingredient>, Comparable<Pizza>, Cloneable {
    private String titre;
    private String description;
    // ...
    /**
     * Renvoie un clone de la pizza : même titre, même description, mêmes ingrédients.
     *
     * Contrairement à copier, aucun constructeur n'est appelé : chaque attribut est recopié tel
     * quel. La liste des ingrédients, elle, n'est pas partagée : ajouter ou retirer un ingrédient
     * du clone ne change pas l'original, et inversement. Les ingrédients eux-mêmes restent communs :
     * ce sont ceux du catalogue.
     *
     * @return le clone de la pizza
     */
    @Override
    public Pizza clone() {
        try {
            Pizza copie = (Pizza) super.clone();
            copie.ingredients = new ArrayList<>(ingredients);
            return copie;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
    // ...
}
```

*Vérifié par : `PizzaSeance2Test` (classe `Question12`).*

### Question 13

**Énoncé :** Complétez `clone` dans `PizzaComposable` en vous appuyant sur `Pizza.clone` : elle renvoie une `PizzaComposable`, et non une `Pizza`, de sorte que `PizzaComposable copie = pizza.clone();` compile sans transtypage.

**PizzaComposable.clone**

Une ligne : `return (PizzaComposable) super.clone();`. Le transtypage est sûr, puisque `Pizza.clone` (via `Object.clone`) a créé un objet de la classe réelle, et la copie profonde de la liste est déjà faite par `Pizza.clone`. La date et le créateur sont recopiés tels quels : le clone est `equals` à l'original (même titre, même date), alors qu'une pizza obtenue par `copier` porte une nouvelle date. Recopier `createur` par référence est voulu : c'est le même client.

[`src/main/java/domaine/PizzaComposable.java`](src/main/java/domaine/PizzaComposable.java) :

```java
public class PizzaComposable extends Pizza {
    private LocalDateTime date;
    // ...
    /**
     * Renvoie un clone de la pizza composable : même créateur, même date, mêmes ingrédients (dans
     * une liste à part). Le type de retour est PizzaComposable, et non Pizza : l'appelant n'a pas à
     * transtyper.
     *
     * @return le clone de la pizza
     */
    @Override
    public PizzaComposable clone() {
        return (PizzaComposable) super.clone();
    }
    // ...
}
```

*Vérifié par : `PizzaSeance2Test` (classe `Question13`).*

## Les classes internes

### Question 14

**Énoncé :** La classe interne **statique** `Pizzeria.Bilan` est fournie. Complétez la méthode `bilan` de `Pizzeria`, qui compte les commandes enregistrées, les pizzas vendues (la somme des quantités des lignes) et le chiffre d'affaires, et renvoie le `Bilan` […]

[`src/main/java/domaine/Pizzeria.java`](src/main/java/domaine/Pizzeria.java) :

```java
public class Pizzeria {
    // ...
    /**
     * Établit le bilan de la pizzeria : nombre de commandes, nombre de pizzas vendues et chiffre
     * d'affaires.
     *
     * @return le bilan des commandes enregistrées
     */
    public Bilan bilan() {
        int nombrePizzas = 0;
        double chiffreAffaires = 0;
        for (Commande commande : commandes) {
            for (LigneDeCommande ligne : commande.getLignes()) {
                nombrePizzas += ligne.getQuantite();
            }
            chiffreAffaires += commande.calculerMontantTotal();
        }
        return new Bilan(commandes.size(), nombrePizzas, chiffreAffaires);
    }
    // ...
}
```

*Vérifié par : `PizzeriaSeance2Test` (classe `Question14`).*

### Question 15

**Énoncé :** La classe interne **non statique** `Pizzeria.Fidelite` est fournie, avec son constructeur ; on l'obtient par `pizzeria.fidelite(seuil)`. Complétez sa méthode `estFidele(Client)`, qui renvoie `true` si le client a passé au moins `seuil` commandes […]

[`src/main/java/domaine/Pizzeria.java`](src/main/java/domaine/Pizzeria.java) :

```java
public class Pizzeria {
    // ...
    public class Fidelite {
        private int seuil;
        // ...
        /**
         * Indique si un client a passé au moins seuil commandes enregistrées dans la pizzeria.
         *
         * @param client le client à examiner
         * @return true si le client est fidèle
         * @throws IllegalArgumentException si le client est null
         */
        public boolean estFidele(Client client) {
            Util.checkObject(client);

            int nombre = 0;
            // commandes est l'attribut de la Pizzeria englobante (Pizzeria.this.commandes)
            for (Commande commande : commandes) {
                if (commande.getClient().equals(client)) {
                    nombre++;
                }
            }
            return nombre >= seuil;
        }

    }

}
```

*Vérifié par : `PizzeriaSeance2Test` (classe `Question15`).*

## Tester `ajouterCommandeSiDisponible`

### Question 16

**Énoncé :** Complétez, dans `src/test/java/domaine/`, la classe `StockStub` : un attribut, l'ensemble des ingrédients manquants reçus par le constructeur (un varargs, à convertir avec `Set.of`), et `estDisponible`, qui renvoie `false` pour ces seuls ingrédients.

*le stub* — [`src/test/java/domaine/StockStub.java`](src/test/java/domaine/StockStub.java) :

```java
package domaine;

/**
 * Un faux stock, écrit à la main pour les tests : les ingrédients manquants sont fixés à la
 * construction, tous les autres sont disponibles.
 */
public class StockStub implements Stock {

    private Set<Ingredient> manquants;

    /**
     * Crée un stock où seuls les ingrédients donnés manquent.
     *
     * @param manquants les ingrédients épuisés
     */
    public StockStub(Ingredient... manquants) {
        this.manquants = Set.of(manquants);
    }

    /**
     * Indique si l'ingrédient fait partie des ingrédients disponibles.
     *
     * @param ingredient l'ingrédient demandé
     * @return false si l'ingrédient fait partie des manquants, true sinon
     */
    @Override
    public boolean estDisponible(Ingredient ingredient) {
        return !manquants.contains(ingredient);
    }

}
```

### Question 17

**Énoncé :** Complétez les deux tests de la classe `Question17` de `PizzeriaStockTest`, avec un `StockStub` : une commande de diavola refusée par une pizzeria qui manque de salami, et une commande de margarita acceptée par la même pizzeria.

[`src/test/java/domaine/PizzeriaStockTest.java`](src/test/java/domaine/PizzeriaStockTest.java) :

```java
class PizzeriaStockTest {
    // ...
    @Nested
    @DisplayName("Question 17 : avec un stub écrit à la main")
    class Question17 {

        @Test
        void uneCommandeDontUnIngredientManqueEstRefusee() {
            Pizzeria pizzeria = pizzeriaAvec(new StockStub(Ingredients.SALAMI));
            Commande commande = commandeCloturee(MenuPizzeria.PIZZA_DIAVOLA);

            assertAll(
                    () -> assertFalse(pizzeria.ajouterCommandeSiDisponible(commande)),
                    () -> assertTrue(pizzeria.getCommandes().isEmpty())
            );
        }

        @Test
        void uneCommandeDontToutEstDisponibleEstEnregistree() {
            Pizzeria pizzeria = pizzeriaAvec(new StockStub(Ingredients.SALAMI));
            Commande commande = commandeCloturee(MenuPizzeria.PIZZA_MARGARITA);

            assertAll(
                    () -> assertTrue(pizzeria.ajouterCommandeSiDisponible(commande)),
                    () -> assertEquals(1, pizzeria.getCommandes().size())
            );
        }
    }
    // ...
}
```

*Vérifié par : `PizzeriaStockTest` (classe `Question17`).*

### Question 18

**Énoncé :** Complétez les deux tests de la classe `Question18` de `PizzeriaStockTest`, en remplaçant le `StockStub` par un mock Mockito : configurez-le avec `when(...).thenReturn(...)` (et `any(Ingredient.class)` pour la règle générale), puis vérifiez avec `verify` […]

[`src/test/java/domaine/PizzeriaStockTest.java`](src/test/java/domaine/PizzeriaStockTest.java) :

```java
class PizzeriaStockTest {
    // ...
    @Nested
    @DisplayName("Question 18 : avec un mock Mockito")
    class Question18 {

        @Test
        void uneCommandeDontUnIngredientManqueEstRefusee() {
            Stock stock = mock(Stock.class);
            when(stock.estDisponible(any(Ingredient.class))).thenReturn(true);
            when(stock.estDisponible(Ingredients.SALAMI)).thenReturn(false);
            Pizzeria pizzeria = pizzeriaAvec(stock);

            assertFalse(pizzeria.ajouterCommandeSiDisponible(commandeCloturee(MenuPizzeria.PIZZA_DIAVOLA)));
            verify(stock).estDisponible(Ingredients.SALAMI);
        }

        @Test
        void chaqueIngredientDeLaCommandeEstVerifie() {
            Stock stock = mock(Stock.class);
            when(stock.estDisponible(any(Ingredient.class))).thenReturn(true);
            Pizzeria pizzeria = pizzeriaAvec(stock);

            assertTrue(pizzeria.ajouterCommandeSiDisponible(commandeCloturee(MenuPizzeria.PIZZA_MARGARITA)));
            verify(stock).estDisponible(Ingredients.TOMATE);
            verify(stock).estDisponible(Ingredients.MOZZARELLA);
            verifyNoMoreInteractions(stock);
        }
    }

}
```

*Vérifié par : `PizzeriaStockTest` (classe `Question18`).*

---

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
