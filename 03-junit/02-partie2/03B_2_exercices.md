# Atelier 3 : JUnit – partie 2

## Objectif

L'objectif de cet atelier est de poursuivre l'apprentissage des tests automatisés avec JUnit. Cette deuxième partie se concentre sur les tests des classes [`Produit`](01-code-java/src/domaine/Produit.java) et [`ListeProduits`](01-code-java/src/usecase/ListeProduits.java) (package `usecase`).

## Concepts

1. Tests unitaires (suite) — réemploi des acquis de la partie 1 (`assertAll`, `assertThrows`, `@ValueSource`)
2. Tests d'exceptions appliqués à plusieurs types d'exceptions métier sur une même méthode
3. Tests d'une logique temporelle (recherche de prix selon une date)
4. Contrat `equals`/`hashCode`
5. Égalité vs identité d'objet (référence différente, valeur égale)
6. Couverture de comportements métier sur une classe composée (`ListeProduits`)
7. Filtrage et tri d'une collection selon un critère calculé, avec exclusion sur exception (multi-catch)
8. Organisation des tests avec `@Nested`
9. Relecture critique de tests générés par IA

## Vidéos

1. [Tests unitaires introduction](https://www.youtube.com/watch?v=UKUbHaxJjRw)
2. [JUnit premiers tests](https://www.youtube.com/watch?v=TkvuBBG-V9o)
3. [Les asserts en JUnit](https://www.youtube.com/watch?v=5swvHf57od8)
4. [Les tests paramétrés en JUnit](https://www.youtube.com/watch?v=fPKqt6oPMdY)
5. [Bonnes pratiques tests unitaires](https://www.youtube.com/watch?v=WwKmx9ghJHU)

## Exercices

### Introduction

Cette partie poursuit le travail entamé sur l'application de gestion de prix de la partie 1 : après avoir testé la classe [`Prix`](01-code-java/src/domaine/Prix.java) (package `domaine`) (y compris `getPrixPromo`, qui applique la promotion en cours au prix de base), on teste maintenant la classe `Produit`, qui garde l'historique des prix appliqués au cours du temps, ainsi que la classe `ListeProduits`.

`ListeProduits` propose aussi `produitsTriesParPrix`, qui renvoie les produits d'un rayon donné triés par prix croissant à une date et une quantité données. Un produit du rayon est exclu du résultat s'il n'a pas de prix disponible à cette date, ou si la quantité demandée est inférieure à sa quantité minimale autorisée (voir la javadoc de la classe fournie).

### Consignes

Pour cette partie, nous allons repartir de la solution de la partie 1.

Dans IntelliJ, créez un projet intitulé `AJ_atelier03_partie2`. Ajoutez-y les classes de la solution de l'atelier 3 partie 1 (voir `../01-partie1/02-solution/`), qui contiennent déjà la classe [`PrixTest`](01-code-java/tests/domaine/PrixTest.java). Faites attention aux packages !

Vérifiez que tout fonctionne bien en exécutant la classe [`Main`](01-code-java/src/main/Main.java) du package `main` et en relançant `PrixTest`.

**Créer une classe de test :**

Comme dans la partie 1, créez une classe de test JUnit 5 pour la classe `Produit`. Elle doit s'appeler `ProduitTest`, être placée dans le package `domaine` du répertoire `tests`, et contenir une méthode `setUp` annotée avec `@BeforeEach`.

### Tests de la classe `Produit`

**Question 1** : Préparation du test

✏️ *A corriger au tableau*

Créez la classe `ProduitTest` de façon similaire à `PrixTest`. Ajoutez-y 3 attributs de type `Prix` et initialisez-les comme dans `PrixTest` (copier-coller ok juste une fois !). Ajoutez-y aussi 2 attributs `Produit` et dans l'un d'eux insérez les 3 prix. Attention, on ne peut pas ajouter deux prix à la même date !

**Question 2** : Test des prix (`ajouterPrix` et `getPrix`)

Une même méthode peut échouer de plusieurs façons différentes (trois types d'exceptions distincts). Et quand ça marche, il faut retrouver le bon prix parmi plusieurs prix historisés selon la date demandée.

Demandez un prix pour une date comprise entre deux dates pour lesquelles un prix a été défini. C'est celui de la date antérieure (la plus récente parmi celles passées) qui doit être renvoyé — la logique est similaire à celle des paliers de quantité vus en partie 1, mais appliquée à des dates.

**Question 3** : `equals` — deux produits de même état

✏️ *A corriger au tableau*

Il s'agit ici de tester un **contrat** plutôt qu'un comportement isolé. Le contrat `equals`/`hashCode` impose que deux objets égaux aient le même `hashCode`, et que l'égalité ne tienne que si les trois attributs (nom, marque, rayon) correspondent tous. Commencez par l'égalité attendue : deux instances distinctes mais avec les mêmes nom, marque et rayon doivent être `equals`.

**Question 4** : `equals` — un seul attribut différent

Faites ensuite varier un seul attribut à la fois (nom différent, puis marque différente, puis rayon différent, les deux autres restant identiques) : dans chacun des trois cas, `equals` doit renvoyer faux.

**Question 5** : `hashCode` cohérent avec `equals`

Ces mêmes deux instances égales (question 3) doivent aussi avoir le même `hashCode` — c'est l'autre moitié du contrat, à tester dans une méthode dédiée.

### 🤖 À partir d'ici, testez avec l'IA

À partir de la question 6, aidez-vous d'un assistant IA pour écrire vos tests (voir la section « Développer des tests avec l'IA » de la théorie) : relisez et exécutez chaque test généré, et vérifiez qu'il passe bien au rouge si vous cassez volontairement le code testé. Attention en particulier au contrat `equals`/`hashCode` et à la distinction égalité/identité, deux pièges où l'IA se trompe facilement.

### Tests de la classe `ListeProduits`

**Question 6** :

🤖 *À faire avec l'IA*

Tests des méthodes de `ListeProduits`

Créez la classe `ListeProduitsTest` et testez-y la classe `ListeProduits` du package `usecase`. Pour chaque méthode, pensez à bien tester tous les cas où la méthode doit échouer (quand elle doit renvoyer faux ou lancer une exception) comme cela a été fait, par exemple, pour le test des prix de la classe `Produit`. Dans le cas où la méthode doit réussir, pensez à vérifier que ce qui devait être fait l'a bien été (par exemple, vérifiez la présence du produit après l'avoir ajouté).

Le point le plus subtil, déjà entrevu aux questions 3 à 5 : **égalité n'est pas identité**. Pour la méthode permettant d'ajouter un prix à un produit, pensez à tester que le travail est bien fait sur le bon produit (celui stocké et non celui passé en paramètre) en passant en paramètre un produit égal (mêmes nom/marque/rayon) mais de référence différente à celui stocké. De même pour la méthode permettant de retrouver le prix.

**Question 7** :

🤖 *À faire avec l'IA*

Scénario complet sur `ListeProduits`

Ajoutez un test de scénario complet dans `ListeProduitsTest`. Ce test doit enchaîner plusieurs actions dans une situation réaliste. Créez une `ListeProduits` vide, un produit `p1` et un autre produit `p2` égal à `p1`, mais créé avec `new` dans une autre variable. Ajoutez `p1` à la liste et vérifiez que l'ajout renvoie `true`, puis essayez d'ajouter `p2` et vérifiez que l'ajout renvoie `false`, puisque le produit est déjà présent au sens de `equals`. Ajoutez deux prix à deux dates différentes en passant `p2` à `ajouterPrix` : le test doit donc vérifier que la liste retrouve le produit stocké (`p1`) même si l'objet reçu en paramètre est une autre référence. Retrouvez ensuite le prix avec `trouverPrix`, toujours en passant `p2`, pour une date exacte et pour une date située entre les deux dates de prix, et vérifiez que le bon `Prix` est renvoyé dans les deux cas. Vérifiez enfin deux cas d'erreur, dans ce même scénario ou dans des méthodes séparées : rechercher le prix d'un produit absent doit lever [`ProduitNonPresentException`](01-code-java/src/exceptions/ProduitNonPresentException.java), et rechercher un prix à une date trop ancienne doit lever [`PrixNonDisponibleException`](01-code-java/src/exceptions/PrixNonDisponibleException.java) (package `exceptions`).

Ce scénario doit être plus conséquent qu'un test de getter : il doit montrer que `ListeProduits` orchestre correctement l'ajout, la détection des doublons, l'égalité entre produits, l'historique des prix et les exceptions métier.

### Tests de `produitsTriesParPrix`

`produitsTriesParPrix` combine filtrage, tri et gestion de plusieurs cas d'exclusion : les questions suivantes construisent un scénario avec plusieurs produits pour couvrir chaque branche. Vous pouvez enrichir un même scénario de question en question, ou écrire des méthodes de test séparées.

**Question 8** :

🤖 *À faire avec l'IA*

Tri par prix croissant

Créez au moins 3 produits dans un même rayon, avec des prix différents, et vérifiez que la méthode les renvoie triés par prix croissant.

**Question 9** :

🤖 *À faire avec l'IA*

Exclusion — pas de prix à la date demandée

Ajoutez un produit du même rayon sans aucun prix défini à la date demandée : vérifiez qu'il est exclu du résultat (pas d'exception).

**Question 10** :

🤖 *À faire avec l'IA*

Exclusion — quantité minimale trop élevée

Ajoutez un produit du même rayon dont le prix a une quantité minimale supérieure à celle demandée : vérifiez qu'il est également exclu.

**Question 11** :

🤖 *À faire avec l'IA*

Exclusion — autre rayon

Ajoutez un produit dans un autre rayon : vérifiez qu'il n'apparaît jamais dans le résultat, quel que soit son prix.

**Question 12** :

🤖 *À faire avec l'IA*

Rayon sans produit

Vérifiez qu'un rayon sans aucun produit renvoie une liste vide, sans exception.

**Question 13** :

🤖 *À faire avec l'IA*

Paramètres invalides

Vérifiez qu'un rayon `null`, une date `null` ou une quantité négative ou nulle lèvent une `IllegalArgumentException`.

### Réorganiser les tests avec `@Nested`

**Question 14** :

🤖 *À faire avec l'IA*

Regrouper les tests de `ListeProduitsTest` avec `@Nested`

Votre `ListeProduitsTest` contient maintenant beaucoup de méthodes de test mélangées. Réorganisez-la en regroupant les tests de chaque méthode testée (`contient`, `ajouterProduit`, `supprimerProduit`, `trouverProduit`, `ajouterPrix`, `trouverPrix`, ainsi que le scénario complet et `produitsTriesParPrix`) dans une classe interne **non statique** annotée `@Nested` et `@DisplayName` (voir la section « Organiser les tests avec `@Nested` » de la théorie). Laissez les attributs et le `@BeforeEach` sur la classe externe : les classes `@Nested` y ont accès.

C'est une réorganisation mécanique et répétitive : un bon usage de l'IA. Demandez à l'assistant de déplacer les tests dans les groupes `@Nested`, **sans modifier le corps des tests**, puis relancez toute la classe. Comme `@Nested` ne change que l'organisation, les résultats doivent être exactement les mêmes qu'avant (tout vert) — si un test devient rouge ou disparaît, c'est que la réorganisation a cassé quelque chose, pas que le comportement testé a changé.

---

*Passez à la [théorie suivante](../../04-tdd/01-partie1/04A_1_theorie.md).*

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cheat sheet de cette semaine : [consultez-la en ligne](https://astounding-queijadas-0f428a.netlify.app/03-junit-fr.html).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
