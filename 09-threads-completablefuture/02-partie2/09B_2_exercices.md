# Atelier 9 : Threads & CompletableFuture – partie 2

## Objectif

L'objectif de cette partie est de lancer des traitements asynchrones et de récupérer leurs résultats à l'aide de `CompletableFuture`, puis d'appliquer cette approche à un cas concret : des appels asynchrones à une API REST.

## Concepts

1. `CompletableFuture`
2. `supplyAsync` / `runAsync`
3. `thenApply`, `thenAccept`, `thenCompose`, `thenCombine`
4. `allOf`, `join`
5. Gestion des erreurs (`exceptionally`)
6. Appels asynchrones à une API REST
7. Mesure du temps d'exécution

## Vidéos

1. [Les Threads - Bases](https://www.youtube.com/watch?v=4cYaMxE5a58)
2. [Threads - Race Condition](https://www.youtube.com/watch?v=G-BEqoP57Lg)
3. [Threads - Deadlock](https://www.youtube.com/watch?v=SabJvW2oPxQ)
4. [Threads - Completable Future](https://www.youtube.com/watch?v=EE6a93t2c3E)
5. [CompletableFuture - thenCompose vs thenApply](https://www.youtube.com/watch?v=Wz-novO_zlk)
6. [thenCombine](https://www.youtube.com/watch?v=m_AEDUA774Q)
7. [CompletableFuture - Résumé](https://www.youtube.com/watch?v=Pw5OEjXyrSI)

## Exercices

### Introduction

Dans la partie 1, chaque compteur était un `Thread` : lancer la course était facile, mais récupérer un **résultat** (la position d'arrivée de chaque compteur) demandait un attribut partagé et de la synchronisation. Dans cette partie, les compteurs renvoient leur position via des `CompletableFuture`, puis vous appliquerez la même mécanique à des appels asynchrones vers une API REST publique.

### Consignes

Veuillez créer un nouveau Projet Maven au sein d'IntelliJ nommé `AJ_atelier09_partie2` (voir le [tutoriel Maven](../../05-mocks/02-partie2/05B_3_tutoriel-maven.md) si besoin). Récupérez les classes fournies dans `01-code-java/src/main/java/` (packages `compteur_future` et `api_call`) et les tests fournis dans `01-code-java/src/test/java/` en conservant l'arborescence. Ajoutez les dépendances JUnit 5 et jackson-databind à votre `pom.xml` (voir le `pom.xml` fourni) :

```xml
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.17.0</version>
</dependency>
```

NB : le `pom.xml` fourni a été configuré pour la version 21 du JDK. Cette version se change facilement pour correspondre à la version présente sur votre laptop, en mettant à jour `pom.xml`. Si vous souhaitez installer une nouvelle version du JDK, vous pouvez le faire via : File → Project Structure… → Project → SDK → Download JDK…

### Programmation asynchrone moderne

Au sein de la classe [`CompteurEtPosition`](01-code-java/src/main/java/compteur_future/CompteurEtPosition.java) du package `compteur_future`, nous vous offrons `countAndGetPosition` (compter et déterminer la position, de manière synchrone) et `countAndGetPositionAsync` (la même chose, de manière asynchrone). [`TestCompteurEtPositionCompletableFuture`](01-code-java/src/main/java/compteur_future/TestCompteurEtPositionCompletableFuture.java) affiche déjà 4 compteurs et leur ordre d'arrivée de manière synchrone, puis délègue les deux questions suivantes à la classe [`CourseCompteurs`](01-code-java/src/main/java/compteur_future/CourseCompteurs.java). Lisez ce code attentivement et exécutez-le.

Chaque question est vérifiée par les tests JUnit fournis ([`CourseCompteursTest`](01-code-java/src/test/java/compteur_future/CourseCompteursTest.java)) : exécutez-les directement dans IntelliJ (bouton ▶) pour vérifier votre implémentation au fur et à mesure.

**Question 1** :
Complétez `attendreResultatsAsync` de `CourseCompteurs` : pour chaque compteur, lancez `countAndGetPositionAsync` et affichez sa position dès qu'elle arrive. L'affichage doit être du même style que pour les traitements synchrones :

```
Compteur : Robert - Position : 1
Compteur : Bolt - Position : 2
Compteur : Jakson - Position : 3
Compteur : Stéphanie - Position : 4
```

**Question 2** :

✏️ *A corriger au tableau*

Complétez `creerFuturesDepuisCountAndGetPosition` de `CourseCompteurs` : même résultat que la Question 1, mais en lançant chaque traitement à partir de la fonction **synchrone** `countAndGetPosition` (`CompletableFuture.runAsync`).

### Appels asynchrones à une API REST

Jusque-là, nous avons simulé des traitements asynchrones à l'aide de `Thread.sleep`. Il serait intéressant de découvrir une application concrète de traitements asynchrones. Vous allez donc maintenant réaliser des appels asynchrones à une API REST publique et gratuite nommée https://jsonplaceholder.typicode.com/.

Vous trouverez le code pour réaliser les appels à cette API au sein du package `api_call`, dans la classe [`ApiService`](01-code-java/src/main/java/api_call/ApiService.java). Veuillez lire ce code et remarquer que les méthodes fetch (`fetchPosts`, `fetchCommentsForPost` et `fetchUser`) renvoient des `CompletableFuture` de `String` : le body JSON de la réponse HTTP GET, disponible dans le futur. Voir le [tutoriel ApiService & CompletableFuture](09B_3_tutoriel-api-service.md) pour le détail de leur fonctionnement.

**Question 3** :
Dans `printAllPosts` ([`TestApiService`](01-code-java/src/main/java/api_call/TestApiService.java)), faites un fetch de tous les posts et affichez-les dans le terminal.

**Question 4** :
Dans `printAllPostsWithCount`, en programmation fonctionnelle (une seule suite d'appels), faites un fetch de tous les posts, affichez-les, retournez-les en tant que `JsonNode`, puis affichez leur nombre.

TIPS — à partir de la string `posts` (JSON retourné par l'API), créer un `JsonNode` (un tableau d'objets représentant des posts) :

```java
JsonNode postsJson = objectMapper.readTree(posts);
```

**Question 5** :
Dans `dealWithUnexistedApi`, tentez d'afficher le résultat d'une API qui n'existe pas via `fetchData` (URL `"http://unexistingapi/things"`). Toujours en programmation fonctionnelle, gérez l'exception en affichant le code d'erreur retourné par `fetchData`.

**Question 6** :
Dans `printAllPostsWithUserAndComments`, affichez tous les posts, les commentaires et les détails de l'utilisateur, pour chaque post :

```
Post (postId:1) : {post details}
Comments: (postId:1) : [{comments details}]
User: (postId:1) : {user details}
```

Pour chaque post, lancez en parallèle les requêtes commentaires et utilisateur, et attendez que les deux soient arrivées avant d'afficher les infos de ce post.

TIPS — récupérer un objet par index dans un `JsonNode` représentant un tableau :

```java
JsonNode posts = objectMapper.readTree(postsJson);
JsonNode post = posts.get(index);
```

Récupérer une propriété d'un `JsonNode` représentant un objet :

```java
int userId = post.get("userId").asInt();
```

Réponses dans [`02-solution/09B_solutions-observations.md`](02-solution/09B_solutions-observations.md) : réfléchissez (et testez !) avant de la consulter.

**Question 7** :
Est-ce que votre affichage est correct ? Il est fort probable que l'affichage dans le terminal se fasse aussi de manière asynchrone… Et que donc vous n'ayez pas les commentaires et les détails d'un utilisateur toujours liés au bon post… Comment pourriez-vous corriger ça ?

**Question 8** :
Combien de requêtes sont faites à l'API au total pour afficher tous les posts avec les commentaires et détails des utilisateurs ?

**Question 9** :
Quel est le délai d'attente lors de chaque requête ? (voir le code d'`ApiService`)

**Question 10** :
Est-ce que le temps total d'exécution de vos requêtes semble bien correspondre à des traitements parallélisés ?

## Parties optionnelles

### `ExecutorService` avec pool de threads fixe

**Question 11** :
Dans `printAllPostsWithUserAndCommentsAnd2Threads`, refaites la Question 6 mais en utilisant un `ExecutorService` avec un pool de threads fixe. Limitez le nombre de threads à 2.

---

*Passez à la [théorie suivante](../../10-hyperloglog-async/01-partie1/10A_1_theorie.md).*

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cheat sheet de cette semaine : [consultez-la en ligne](https://astounding-queijadas-0f428a.netlify.app/09-threads-completablefuture-fr.html).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
