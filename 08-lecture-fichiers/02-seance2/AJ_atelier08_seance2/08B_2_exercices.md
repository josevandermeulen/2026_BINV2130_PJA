# Atelier 8 : Lecture de fichiers, try-with-resources – séance 2

## Objectif

Cet atelier prolonge la pizzeria de la séance 1 pour revenir sur sept points du langage : l'énuméré vu comme une classe, la `Map` dont la valeur est une collection, `Comparable` et `Comparator` écrits de trois façons, les varargs, le type de retour covariant, le clonage et les classes internes. Il se termine par des tests avec un stub puis un mock, en rappel de l'atelier 5, dans un projet qui fonctionne sans connexion internet.

## Concepts

1. Énuméré avec attributs et méthodes
2. `Map` dont la valeur est une collection (`computeIfAbsent`)
3. `Comparable` et `Comparator` (classe nommée, classe anonyme, lambda)
4. Varargs
5. Type de retour covariant
6. Clonage (`Cloneable`, copie profonde)
7. Classes internes statiques et non statiques
8. Stubs et mocks Mockito (révision de l'atelier 5)
9. Dépôt Maven embarqué, pour travailler hors ligne

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

## Exercices

### Introduction

La pizzeria de la séance 1 veut imprimer une carte rangée par catégorie, trier son menu de plusieurs façons, faire son bilan et récompenser ses clients fidèles. Elle veut aussi refuser une commande quand un ingrédient manque au stock : la méthode `Pizzeria.ajouterCommandeSiDisponible` est fournie, il vous reste à la tester.

Lisez d'abord [`08B_1_theorie.md`](08B_1_theorie.md) : chaque section des exercices s'appuie sur un de ses encadrés.

### Consignes

Dans IntelliJ, créez un projet Maven intitulé `AJ_atelier08_seance2` (voir le [tutoriel Maven](../../../05-mocks/02-seance2/AJ_atelier05_seance2/05B_3_tutoriel-maven.md) si besoin). Reprenez tel quel le `pom.xml` fourni **avec le dossier `repo/` qui l'accompagne**, à placer à la racine de votre projet : les dépendances JUnit 5 et Mockito y sont **embarquées**, si bien que le projet se construit aussi sans connexion internet.

Récupérez les classes fournies dans `AJ_atelier08_seance2/src/main/java/` et les tests fournis dans `AJ_atelier08_seance2/src/test/java/` en conservant l'arborescence, ainsi que le dossier `commandes/`, à la racine du projet. Le code fourni est la solution de la séance 1 (`../../01-seance1/AJ_atelier08_seance1_solution/`), à laquelle s'ajoutent les signatures des méthodes de cette séance, la classe [`ComparateurNombreIngredients`](src/main/java/domaine/ComparateurNombreIngredients.java) (package `domaine`), les classes internes `Pizzeria.Bilan` et `Pizzeria.Fidelite`, et la méthode abstraite `copier` de [`Pizza`](src/main/java/domaine/Pizza.java).

Exécutez les tests directement dans IntelliJ (clic droit sur `src/test/java` → *Run 'All Tests'*) pour vérifier votre implémentation au fur et à mesure. Les tests de la séance 1 sont verts dès le départ.

### Vérification de l'installation

✏️ *A corriger au tableau*

Avant de commencer, vérifiez que le dépôt Maven embarqué (`repo/`) fonctionne bien sans connexion internet : exécutez [`MockitoSmokeTest`](src/test/java/main/MockitoSmokeTest.java) (fourni, package `main` des tests). Il crée un mock de `Stock` avec Mockito et vérifie son comportement. S'il passe, JUnit 5 et Mockito sont correctement résolus depuis `repo/`, sans toucher au réseau.

### L'énuméré `Categorie`

**Question 1** :

✏️ *A corriger au tableau*

Ouvrez l'énuméré [`Categorie`](src/main/java/domaine/Categorie.java) (package `domaine`). Complétez la méthode `estVegetarienne`, qui renvoie `true` pour la seule constante `VEGETARIENNE`. Une méthode d'un énuméré s'appelle sur une constante, comme sur n'importe quel objet : `MenuPizzeria.PIZZA_MARGARITA.getCategorie().estVegetarienne()`.

**Question 2** :
Ajoutez à `Categorie` un deuxième attribut, l'abréviation imprimée sur la carte (`"V"`, `"VI"` et `"P"`), initialisé par le constructeur de l'énuméré à partir d'un second argument donné à chaque constante, puis complétez `getAbreviation`.

### La carte par catégorie

**Question 3** :
La `Pizzeria` a un nouvel attribut, `carte`, une `Map<Categorie, SortedSet<Pizza>>` qui range les pizzas du menu par catégorie. Complétez `ajouterPizza` pour qu'elle range aussi la pizza ajoutée sur la carte, en créant l'ensemble de sa catégorie au premier ajout (`computeIfAbsent`, avec un `TreeSet`). La méthode fournie `aDesPizzas` indique si la carte a une entrée pour une catégorie.

**Question 4** :
Complétez `getPizzas(Categorie)`, qui renvoie l'ensemble des pizzas d'une catégorie en lecture seule (`Collections.unmodifiableSortedSet`), ou un ensemble vide (`Collections.emptySortedSet`) si la carte n'en a aucune.

**Question 5** :
Complétez `retirerPizza(String titre)` : la pizza quitte le menu et la carte, et une catégorie dont la dernière pizza est retirée disparaît de la carte (la méthode fournie `aDesPizzas` renvoie alors `false`).

### Trier le menu

**Question 6** :
Complétez la méthode `compare` de la classe `ComparateurNombreIngredients` : elle range les pizzas de la plus garnie à la moins garnie et, à nombre d'ingrédients égal, dans leur ordre naturel. Utilisez `Integer.compare` plutôt qu'une soustraction.

**Question 7** :
Complétez `menuTrieParNombreIngredients` dans `Pizzeria`, qui trie le menu dans le même ordre, mais sans utiliser `ComparateurNombreIngredients` : passez à `sort` un `Comparator` écrit en **classe anonyme**.

**Question 8** :
Complétez `menuTrieParCategorie`, qui trie le menu par catégorie (dans l'ordre de déclaration de l'énuméré : les énumérés sont `Comparable`) puis par titre, avec un `Comparator` écrit en **lambda**.

### Varargs et type de retour covariant

**Question 9** :
Complétez, dans `Pizza`, la méthode `ajouter(Ingredient... ingredients)`, qui ajoute en une fois tous les ingrédients reçus (en ignorant ceux déjà présents) et renvoie le nombre d'ingrédients effectivement ajoutés. Appuyez-vous sur la méthode `ajouter(Ingredient)` existante : une pizza de la carte refusera donc l'ajout de la même façon.

**Question 10** :
`Pizza` déclare une méthode abstraite `Pizza copier()`, redéfinie dans `PizzaComposable` (fournie). Complétez sa redéfinition dans [`PizzaComposee`](src/main/java/domaine/PizzaComposee.java) : une nouvelle pizza de même titre, même description et mêmes ingrédients (dans une nouvelle liste).

**Question 11** :
Les deux redéfinitions de `copier` ne renvoient pas une `Pizza`, mais une `PizzaComposable` et une `PizzaComposee`. Pourquoi le compilateur l'accepte-t-il, alors que la signature de `Pizza.copier` annonce une `Pizza` ? Qu'est-ce que cela change pour le code qui appelle `copier` ? Le changement inverse (une redéfinition qui annoncerait `Object`) serait-il accepté ?

Réponses dans [`08B_reponses-observations.md`](08B_reponses-observations.md).

### Cloner une pizza

Un client veut recommander la pizza qu'il a composée la semaine passée, en y ajoutant un ingrédient : on part d'un clone de sa pizza, et l'originale, déjà facturée, ne doit pas changer. Contrairement à `copier`, qui passe par un constructeur, `clone` recopie l'objet tel quel : même créateur, même date de création. Voir la section *Le clonage* de la théorie.

**Question 12** :
Rendez `Pizza` clonable et complétez sa méthode `clone` : le clone ne doit pas partager sa liste d'ingrédients avec l'original, de sorte qu'ajouter ou retirer un ingrédient de l'un ne change pas l'autre. Les ingrédients eux-mêmes, eux, restent partagés. Les tests de la classe `Question12` de [`PizzaSeance2Test`](src/test/java/domaine/PizzaSeance2Test.java) vérifient cette indépendance.

**Question 13** :
Complétez `clone` dans `PizzaComposable` en vous appuyant sur `Pizza.clone` : elle renvoie une `PizzaComposable`, et non une `Pizza`, de sorte que `PizzaComposable copie = pizza.clone();` compile sans transtypage.

### Les classes internes

**Question 14** :
La classe interne **statique** `Pizzeria.Bilan` est fournie. Complétez la méthode `bilan` de `Pizzeria`, qui compte les commandes enregistrées, les pizzas vendues (la somme des quantités des lignes) et le chiffre d'affaires, et renvoie le `Bilan` correspondant. Remarquez que `Bilan` reçoit toutes ses valeurs par son constructeur : étant statique, elle n'a pas accès aux attributs de la `Pizzeria`.

**Question 15** :
La classe interne **non statique** `Pizzeria.Fidelite` est fournie, avec son constructeur ; on l'obtient par `pizzeria.fidelite(seuil)`. Complétez sa méthode `estFidele(Client)`, qui renvoie `true` si le client a passé au moins `seuil` commandes enregistrées dans la pizzeria. Lisez directement l'attribut `commandes` de la `Pizzeria` englobante, sans passer par `getCommandes`.

### Tester `ajouterCommandeSiDisponible`

Les dernières questions ne demandent pas de code de production : la méthode `ajouterCommandeSiDisponible` est fournie, et c'est vous qui écrivez ses tests, d'abord avec un stub, puis avec un mock. Vérifiez que chaque test échoue quand on casse la méthode testée.

**Question 16** :
Complétez, dans `src/test/java/domaine/`, la classe [`StockStub`](src/test/java/domaine/StockStub.java) : un attribut, l'ensemble des ingrédients manquants reçus par le constructeur (un varargs, à convertir avec `Set.of`), et `estDisponible`, qui renvoie `false` pour ces seuls ingrédients.

**Question 17** :
Complétez les deux tests de la classe `Question17` de [`PizzeriaStockTest`](src/test/java/domaine/PizzeriaStockTest.java), avec un `StockStub` : une commande de diavola refusée par une pizzeria qui manque de salami, et une commande de margarita acceptée par la même pizzeria.

**Question 18** :
Complétez les deux tests de la classe `Question18` de `PizzeriaStockTest`, en remplaçant le `StockStub` par un mock Mockito : configurez-le avec `when(...).thenReturn(...)` (et `any(Ingredient.class)` pour la règle générale), puis vérifiez avec `verify` les ingrédients sur lesquels le stock a été interrogé.

---

*Passez à la [théorie suivante](../../../09-threads-completablefuture/01-seance1/AJ_atelier09_seance1/09A_1_theorie.md).*

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cheat sheet de cette semaine : [consultez-la en ligne](https://astounding-queijadas-0f428a.netlify.app/08-lecture-fichiers-fr.html).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
