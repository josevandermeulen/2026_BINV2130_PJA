# Atelier 6 : Streams – les bases – partie 2

## Objectif

L'objectif de cet atelier est d'approfondir l'API Stream : gérer l'absence de résultat avec `Optional` et regrouper des données avec `groupingBy`.

## Concepts

1. `Optional`
2. `orElse`
3. `reduce` sans valeur neutre
4. `groupingBy`
5. `counting`
6. Collectors

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

Cet atelier continue la partie 1 : on retrouve les transactions financières effectuées par des traders (classes [`Trader`](01-code-java/src/main/java/domaine/Trader.java) et [`Transaction`](01-code-java/src/main/java/domaine/Transaction.java) (package `domaine`)), cette fois pour agréger, gérer le vide et grouper les résultats.

### Consignes

Veuillez créer un nouveau Projet Maven au sein d'IntelliJ nommé `AJ_atelier06_partie2` (voir le [tutoriel Maven](../../05-mocks/02-partie2/05B_3_tutoriel-maven.md) si besoin). Récupérez les classes fournies dans `01-code-java/src/main/java/` (packages `domaine`, `main`) et les tests fournis dans `01-code-java/src/test/java/` en conservant l'arborescence. Ajoutez la dépendance JUnit 5 à votre `pom.xml`.

Comme en partie 1, chaque méthode à compléter renvoie une valeur plutôt que de l'afficher : les tests JUnit fournis appellent ces méthodes et vérifient le résultat.

### Gestion du vide (type Optional<>)

Ouvrez la classe [`ExercicesOptional`](01-code-java/src/main/java/main/ExercicesOptional.java) (package `main`). Dans les exercices sur les reduce de la partie 1, on utilisait la version de `reduce` avec deux paramètres : une valeur neutre et un accumulateur. Copiez-collez vos solutions pour le `reduce1` et `reduce2` (ou reprenez-les dans `../01-partie1/02-solution/`), puis modifiez-les pour utiliser maintenant la version qui prend uniquement un accumulateur en paramètre et qui renvoie un objet `Optional`.

**Question 1** :
Dans la méthode `optional1` (exercice trouvant la valeur max), utilisez `orElse` et renvoyez -1 s'il n'y a pas de transactions.

**Question 2** :
Dans la méthode `optional2` (transaction de valeur minimale), renvoyez directement l'`Optional<Transaction>` obtenu après réduction (vide s'il n'y a pas de transactions).

✏️ *A corriger au tableau*

### Grouper

Ouvrez la classe [`ExerciceGroupingBy`](01-code-java/src/main/java/main/ExerciceGroupingBy.java) et complétez les méthodes suivantes en utilisant les streams afin de construire et renvoyer les dictionnaires demandés à partir de la liste de transactions présente dans la classe.

**Question 3** :
Dans la méthode `groupBy1`, construire une `Map<Trader, List<Transaction>>` — transactions du trader.

✏️ *A corriger au tableau*

**Question 4** :
Dans la méthode `groupBy2`, construire une `Map<Trader, Long>` — nombre de transactions de ce trader. Pour ceci il faudra utiliser la méthode `counting` dans le collector.

**Question 5** :
Dans la méthode `groupBy3`, construire une `Map<TransactionsLevel, List<Transaction>>` — considérant l'enum `TransactionsLevel {VERY_HI, HI, LO, ME}`, les transactions sont réparties selon les critères suivants :

1. si valeur >= 1000, elle est `VERY_HI` ;
2. si 800 <= valeur < 1000, elle est `HI` ;
3. si 600 <= valeur < 800, elle est `ME` ;
4. sinon elle est `LO`.

La map ne doit pas être triée.

### Exercices panachés

Complétez les méthodes de la classe [`ExercicesPanaches`](01-code-java/src/main/java/main/ExercicesPanaches.java), qui doivent chacune renvoyer le résultat demandé (pas d'affichage).

**Question 6** :
Dans la méthode `exercice1`, renvoyer la valeur de la transaction avec la plus grande valeur parmi celles effectuées à Cambridge, sous forme d'`Optional<Integer>` (vide si aucune transaction à Cambridge).

**Question 7** :
Dans la méthode `exercice2`, renvoyer combien de transactions chaque trader basé à Cambridge a effectuées, sous forme de `Map<Trader, Long>`.

**Question 8** :
Dans la méthode `exercice3`, renvoyer, parmi les transactions dont la valeur est supérieure à 500, le nom du trader ayant le nom le plus long, sous forme d'`Optional<String>`. En cas d'ex æquo, le premier trader de la liste.

**Question 9** :
Dans la méthode `exercice4`, renvoyer la moyenne des valeurs des transactions pour chaque ville où sont basés les traders, sous forme de `Map<String, Double>`.

**Question 10** :
Dans la méthode `exercice5`, renvoyer la transaction de plus faible valeur parmi celles effectuées à Milan, sous forme d'`Optional<Transaction>` (vide si aucune transaction à Milan).

**Question 11** :
Dans la méthode `exercice6`, renvoyer les transactions regroupées par année, sous forme de `Map<Integer, List<Transaction>>`.

## Parties optionnelles

### Trader dominant par ville

**Question 12** :
Dans la méthode `villeTraderMaxTotal` de la classe `ExerciceGroupingBy`, construire une `Map<String, Trader>` associant à chaque ville le trader y ayant généré la plus grande valeur totale de transactions. Combinez deux niveaux de `groupingBy` : un premier par ville, puis pour chaque ville un total par trader (`summingInt`) dont vous ne conservez que le trader au total maximal (`Collectors.collectingAndThen`).

---

*Passez à la [théorie suivante](../../07-programmation-fonctionnelle/01-partie1/07A_1_theorie.md).*

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cheat sheet de cette semaine : [consultez-la en ligne](https://astounding-queijadas-0f428a.netlify.app/06-streams-fr.html).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
