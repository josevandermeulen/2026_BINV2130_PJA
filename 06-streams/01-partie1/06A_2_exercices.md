# Atelier 6 : Streams – les bases – partie 1

## Objectif

L'objectif de cet atelier est de découvrir l'API Stream de Java pour filtrer, transformer, trier, collecter et agréger des données de manière déclarative.

## Concepts

1. Streams
2. Boucle implicite
3. Prédicats
4. `filter`
5. `map`
6. `sorted`
7. `collect`
8. Agrégations et réductions

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

## Exercices

### Introduction

Cet atelier traite de streams appliqués à des transactions financières effectuées par des traders (classes [`Trader`](01-code-java/src/main/java/domaine/Trader.java) et [`Transaction`](01-code-java/src/main/java/domaine/Transaction.java) (package `domaine`)).

### Consignes

Veuillez créer un nouveau Projet Maven au sein d'IntelliJ nommé `AJ_atelier06_partie1` (voir le [tutoriel Maven](../../05-mocks/02-partie2/05B_3_tutoriel-maven.md) si besoin). Récupérez les classes fournies dans `01-code-java/src/main/java/` (packages `domaine`, `main`, `code_theorie`) et les tests fournis dans `01-code-java/src/test/java/` en conservant l'arborescence. Ajoutez la dépendance JUnit 5 à votre `pom.xml`.

Chaque méthode à compléter renvoie une valeur (liste, `Map`, `Optional`, …) plutôt que de l'afficher : les tests JUnit fournis appellent ces méthodes et vérifient le résultat. Exécutez-les directement dans IntelliJ (bouton ▶) pour vérifier votre implémentation au fur et à mesure.

### Écrivons quelques prédicats

✏️ *A corriger au tableau*

Ouvrez la classe [`ExercicesDeBase`](01-code-java/src/main/java/main/ExercicesDeBase.java) (package `main`).

Le projet contient les classes `Trader` et `Transaction` décrivant des transactions effectuées par des traders. [`ExercicesDeBaseTest`](01-code-java/src/test/java/main/ExercicesDeBaseTest.java) construit une liste de transactions et teste les méthodes ci-dessous.

On vous demande, en utilisant les streams, de filtrer ces transactions de différentes façons et de renvoyer le résultat.

**Question 1** :
Dans la méthode `predicats1`, construire la liste de toutes les transactions de 2011.

**Question 2** :
Dans la méthode `predicats2`, construire la liste de toutes les transactions dont la valeur est > 600.

**Question 3** :
Dans la méthode `predicats3`, construire la liste de toutes les transactions de Raoul.

### Comprendre la boucle implicite des Stream

**Question 4** :
Ouvrez la classe [`ExercicesEmployes`](01-code-java/src/main/java/main/ExercicesEmployes.java). La méthode `listeDesHommes` contient le code suivant :

```java
List<Employe> listDesHommes = new ArrayList<>();
for (Employe e : employes) {
    if (e.getGenre() == Genre.HOMME) {
        listDesHommes.add(e);
    }
}
return listDesHommes;
```

Réécrivez ce code en utilisant l'API Stream (`stream`, `filter`, `collect`) plutôt qu'une boucle.

### Transformer (map)

✏️ *A corriger au tableau*

Reprenez les classes sur les transactions. Attention : ce sont bien des listes que l'on veut, pas des streams.

**Question 5** :
Dans la méthode `map1`, construire la liste des villes où travaillent les courtiers (sans doublon).

Observation : vous avez des doublons ? Utilisez la méthode `distinct` après votre `map` !

**Question 6** :
Dans la méthode `map2`, construire la liste de tous les courtiers de Cambridge (sans doublon).

**Question 7** :
Dans la méthode `map3`, construire une `String` contenant tous les noms des traders (sans doublon) séparés par une virgule. Comme il s'agit d'une `String` et non d'une liste, utilisez la méthode `joining` dans votre `collect`.

### Trier

**Question 8** :
Dans la méthode `sort1`, construire la liste de toutes les transactions triées par ordre croissant de valeurs.

**Question 9** :
Dans la méthode `sort2`, construire une `String` contenant tous les noms de traders (sans doublon) triés par ordre alphabétique.

### Réduire (reduce)

**Question 10** :
Dans la méthode `reduce1`, renvoyer la valeur max des transactions.

**Question 11** :
Dans la méthode `reduce2`, renvoyer la transaction dont la valeur est la plus petite. Attention : on demande bien de renvoyer la transaction et non sa valeur ! Vous ne pouvez pas utiliser la méthode `min`. Au besoin, créez une transaction « neutre » avec une valeur de `Integer.MAX_VALUE`.

## Parties optionnelles

### Total par trader

**Question 12** :
Dans la méthode `traderValeurTotaleMax`, renvoyer le trader ayant généré la plus grande valeur totale de transactions (somme des valeurs de toutes ses transactions) — et non celui possédant la transaction individuelle la plus élevée. Utilisez `Collectors.groupingBy` avec un collecteur `summingInt` en aval pour obtenir le total par trader, puis retrouvez le trader dont ce total est maximal.

---

*Passez à la [théorie suivante](../02-partie2/06B_1_theorie.md).*

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cheat sheet de cette semaine : [consultez-la en ligne](https://astounding-queijadas-0f428a.netlify.app/06-streams-fr.html).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
