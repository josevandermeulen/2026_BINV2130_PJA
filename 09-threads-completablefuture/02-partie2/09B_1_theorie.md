# Atelier 9 : Threads & CompletableFuture – partie 2 – Concepts

## Table des matières

1. [Vidéos](#vidéos)
2. [Introduction](#introduction)
3. [CompletableFuture](#completablefuture)
4. [Chaîner des traitements](#chaîner-des-traitements)
5. [Combiner et attendre plusieurs futures](#combiner-et-attendre-plusieurs-futures)
6. [Gérer les erreurs](#gérer-les-erreurs)
7. [ExecutorService](#executorservice)

## Vidéos

1. [Les Threads - Bases](https://www.youtube.com/watch?v=4cYaMxE5a58)
2. [Threads - Race Condition](https://www.youtube.com/watch?v=G-BEqoP57Lg)
3. [Threads - Deadlock](https://www.youtube.com/watch?v=SabJvW2oPxQ)
4. [Threads - Completable Future](https://www.youtube.com/watch?v=EE6a93t2c3E)
5. [CompletableFuture - thenCompose vs thenApply](https://www.youtube.com/watch?v=Wz-novO_zlk)
6. [thenCombine](https://www.youtube.com/watch?v=m_AEDUA774Q)
7. [CompletableFuture - Résumé](https://www.youtube.com/watch?v=Pw5OEjXyrSI)

## Introduction

Dans la partie 1, nous avons lancé des traitements asynchrones en manipulant directement des `Thread`. Cette approche de bas niveau montre vite ses limites :

1. récupérer le **résultat** d'un thread est laborieux (il faut un attribut partagé, un `join()`, de la synchronisation…) ;
2. chaîner des traitements (« quand ce calcul est fini, fais ceci avec son résultat ») demande beaucoup de code fragile ;
3. la gestion des erreurs survenues dans un autre thread est pénible.

Java offre depuis la version 8 une abstraction de plus haut niveau : **`CompletableFuture<T>`**, une *promesse* de valeur de type `T` qui sera disponible… dans le futur. On raisonne alors en **programmation fonctionnelle** — on décrit la chaîne de traitements à appliquer à la valeur quand elle arrivera — plutôt qu'en gestion manuelle de threads.

## CompletableFuture

Un `CompletableFuture<T>` représente le résultat (futur) d'un traitement asynchrone. Deux méthodes statiques permettent de démarrer un traitement dans un thread du *pool* par défaut (le `ForkJoinPool` commun) :

```java
// lance un traitement qui renvoie une valeur
CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> calculLong());

// lance un traitement sans valeur de retour
CompletableFuture<Void> action = CompletableFuture.runAsync(() -> faireQuelqueChose());
```

`supplyAsync` prend un `Supplier<T>` ; `runAsync` prend un `Runnable`. Dans les deux cas, l'appel **rend la main immédiatement** : le traitement s'exécute dans un autre thread.

Pour attendre (bloquer) et récupérer la valeur :

```java
Integer resultat = future.join();   // bloque jusqu'à la fin du traitement
```

`join()` est à réserver au moment où l'on a *vraiment* besoin de la valeur (typiquement en fin de `main`) — tout ce qui peut être décrit en chaîne asynchrone doit l'être.

## Chaîner des traitements

| Méthode | Signature (simplifiée) | Usage |
|---|---|---|
| `thenApply(fn)` | `CompletableFuture<U>` ← `T -> U` | transformer la valeur (comme `map` sur les streams) |
| `thenAccept(consumer)` | `CompletableFuture<Void>` ← `T -> void` | consommer la valeur (affichage, etc.) |
| `thenRun(action)` | `CompletableFuture<Void>` | exécuter une action qui n'a pas besoin de la valeur |
| `thenCompose(fn)` | `CompletableFuture<U>` ← `T -> CompletableFuture<U>` | chaîner un **autre traitement asynchrone** (comme `flatMap`) |

La différence clé entre `thenApply` et `thenCompose` : si la fonction passée renvoie elle-même un `CompletableFuture`, `thenApply` produirait un `CompletableFuture<CompletableFuture<U>>` imbriqué ; `thenCompose` « aplatit » la chaîne et produit un simple `CompletableFuture<U>`.

```java
CompletableFuture<String> page = fetchUrl("https://…")          // CompletableFuture<String>
        .thenCompose(url -> fetchData(url))                     // autre appel asynchrone
        .thenApply(String::toUpperCase);                        // transformation synchrone
```

## Combiner et attendre plusieurs futures

**Combiner deux futures indépendantes** — `thenCombine` attend les deux résultats puis applique une fonction binaire :

```java
CompletableFuture<String> comments = apiService.fetchCommentsForPost(postId);
CompletableFuture<String> user = apiService.fetchUser(userId);

CompletableFuture<String> fiche = comments.thenCombine(user,
        (c, u) -> "Commentaires : " + c + " / Utilisateur : " + u);
```

**Attendre un ensemble de futures** — `allOf` renvoie un `CompletableFuture<Void>` complété quand *toutes* les futures passées en argument le sont :

```java
CompletableFuture<?>[] futures = …;
CompletableFuture.allOf(futures).join();   // attend la fin de toutes les tâches
```

Attention : les affichages faits depuis plusieurs futures s'entrelacent dans le terminal. Si un bloc de `println` doit rester groupé, il faut synchroniser ce bloc (par exemple `synchronized (System.out) { … }`).

## Gérer les erreurs

Une exception lancée dans la chaîne asynchrone complète la future **exceptionnellement**. `exceptionally` permet de rattraper l'erreur et de fournir une valeur de repli :

```java
apiService.fetchData("http://unexistingapi/things")
        .thenAccept(System.out::println)
        .exceptionally(throwable -> {
            System.out.println("Message d'erreur : " + throwable.getMessage());
            return null;
        })
        .join();
```

Sans `exceptionally` (ou `handle`/`whenComplete`), l'exception ressort au moment du `join()` sous forme de `CompletionException`.

## ExecutorService

Par défaut, `supplyAsync`/`runAsync` utilisent le pool de threads commun de la JVM. On peut fournir son propre pool via un **`ExecutorService`**, notamment pour **limiter le nombre de threads** utilisés :

```java
ExecutorService executor = Executors.newFixedThreadPool(2);   // pool de 2 threads maximum

CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> calcul(), executor);

// à la fin du programme
executor.shutdown();
```

Toutes les tâches soumises se partagent alors les threads du pool : au plus 2 tâches s'exécutent en même temps, les autres attendent qu'un thread se libère. Ne pas oublier `shutdown()`, sans quoi les threads du pool maintiennent la JVM en vie.

---

*Passez aux [exercices](09B_2_exercices.md).*

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cheat sheet de cette semaine : [consultez-la en ligne](https://astounding-queijadas-0f428a.netlify.app/09-threads-completablefuture-fr.html).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
