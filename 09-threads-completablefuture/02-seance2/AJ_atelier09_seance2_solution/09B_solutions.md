# Atelier 9 : Threads & CompletableFuture – séance 2 : solutions

*Énoncé : [`09B_2_exercices.md`](../AJ_atelier09_seance2/09B_2_exercices.md) — théorie : [`09B_1_theorie.md`](../AJ_atelier09_seance2/09B_1_theorie.md).*

## Programmation asynchrone moderne

### Question 1

**Énoncé :** Complétez `attendreResultatsAsync` de `CourseCompteurs` : pour chaque compteur, lancez `countAndGetPositionAsync` et affichez sa position dès qu'elle arrive. L'affichage doit être du même style que pour les traitements synchrones :

**lancer tout, puis attendre le dernier**

Une simple boucle suffit : chaque tour **lance** un compteur et range sa future
(avec l'affichage accroché par `thenAccept`) dans un tableau, sans l'attendre.
`CompletableFuture.allOf(futures)` rend ensuite une future qui s'achève quand
la dernière est terminée. `allOf` accepte des `CompletableFuture<?>`, d'où le
type `CompletableFuture<?>[]` du tableau.

Piège classique : un `join()` dans la boucle. Le code compile et l'affichage
est correct, mais chaque tour attend la fin du compteur avant de lancer le
suivant : la course dure la **somme** des temps au lieu du temps du plus lent.

La Question 2 a la même structure, avec `CompletableFuture.runAsync(...)`
autour de l'appel synchrone `countAndGetPosition()`.

[`src/main/java/compteur_future/CourseCompteurs.java`](src/main/java/compteur_future/CourseCompteurs.java) :

```java
public class CourseCompteurs {
    // ...
    /**
     * Lance tous les compteurs et rend une future qui s'achève quand le dernier est arrivé.
     *
     * @param compteurs les compteurs engagés dans la course
     * @return une future achevée à la fin de la course
     */
    public CompletableFuture<Void> attendreResultatsAsync(CompteurEtPosition[] compteurs) {
        CompletableFuture<?>[] futures = new CompletableFuture<?>[compteurs.length];
        for (int i = 0; i < compteurs.length; i++) {
            CompteurEtPosition compteur = compteurs[i];
            futures[i] = compteur.countAndGetPositionAsync()
                    .thenAccept(position -> System.out.println("Compteur : " + compteur.getNom() + " - Position : " + position));
        }
        return CompletableFuture.allOf(futures);
    }
    // ...
}
```

*Vérifié par : `CourseCompteursTest` (classe `Question1`).*

### Question 2

**Énoncé :** Complétez `creerFuturesDepuisCountAndGetPosition` de `CourseCompteurs` : même résultat que la Question 1, mais en lançant chaque traitement à partir de la fonction **synchrone** `countAndGetPosition` (`CompletableFuture.runAsync`).

[`src/main/java/compteur_future/CourseCompteurs.java`](src/main/java/compteur_future/CourseCompteurs.java) :

```java
public class CourseCompteurs {
    // ...
    /**
     * Variante construisant les futures depuis la méthode bloquante de chaque compteur.
     *
     * @param compteurs les compteurs engagés dans la course
     * @return une future achevée à la fin de la course
     */
    public CompletableFuture<Void> creerFuturesDepuisCountAndGetPosition(CompteurEtPosition[] compteurs) {
        CompletableFuture<?>[] futures = new CompletableFuture<?>[compteurs.length];
        for (int i = 0; i < compteurs.length; i++) {
            CompteurEtPosition compteur = compteurs[i];
            futures[i] = CompletableFuture.runAsync(() ->
                    System.out.println("Compteur : " + compteur.getNom() + " - Position : " + compteur.countAndGetPosition()));
        }
        return CompletableFuture.allOf(futures);
    }

}
```

*Vérifié par : `CourseCompteursTest` (classe `Question2`).*

## Appels asynchrones à une API REST

### Question 3

**Énoncé :** Dans `printAllPosts` (`TestApiService`), faites un fetch de tous les posts et affichez-les dans le terminal.

[`src/main/java/api_call/TestApiService.java`](src/main/java/api_call/TestApiService.java) :

```java
public class TestApiService {
    // ...
    /**
     * Affiche tous les posts, en attendant la réponse de l'API.
     */
    public static void printAllPosts() {
        System.out.println("1. Imprimer tous les posts");
        long start = System.currentTimeMillis();

        apiService.fetchPosts().thenAccept(System.out::println).join();

        long end = System.currentTimeMillis();
        System.out.println("1. Total execution time: " + (end - start) + " ms");

    }
    // ...
}
```

### Question 4

**Énoncé :** Dans `printAllPostsWithCount`, en programmation fonctionnelle (une seule suite d'appels), faites un fetch de tous les posts, affichez-les, retournez-les en tant que `JsonNode`, puis affichez leur nombre.

[`src/main/java/api_call/TestApiService.java`](src/main/java/api_call/TestApiService.java) :

```java
public class TestApiService {
    // ...
    /**
     * Affiche tous les posts et leur nombre.
     */
    public static void printAllPostsWithCount() {
        System.out.println("2. Imprimer tous les posts suivi du nombre de posts");
        long start = System.currentTimeMillis();

        apiService.fetchPosts().thenApply(postsJson -> {
            try {
                JsonNode posts = objectMapper.readTree(postsJson);
                System.out.println(posts);
                return posts;
            } catch (Exception e) {
                throw new RuntimeException("Erreur de parsing des posts", e);
            }
        }).thenAccept(posts -> {
            System.out.println("Nombre de posts: " + posts.size());
        }).join();

        long end = System.currentTimeMillis();
        System.out.println("2. Total execution time: " + (end - start) + " ms");

    }
    // ...
}
```

### Question 5

**Énoncé :** Dans `dealWithUnexistedApi`, tentez d'afficher le résultat d'une API qui n'existe pas via `fetchData` (URL `"http://unexistingapi/things"`). Toujours en programmation fonctionnelle, gérez l'exception en affichant le message d'erreur retourné par `fetchData`.

[`src/main/java/api_call/TestApiService.java`](src/main/java/api_call/TestApiService.java) :

```java
public class TestApiService {
    // ...
    /**
     * Appelle une adresse inexistante, pour montrer comment l'échec se propage dans la chaîne de futures.
     */
    public static void dealWithUnexistedApi() {
        System.out.println("3. Gérer une erreur lorsqu'une API n'existe pas");
        long start = System.currentTimeMillis();

        apiService.fetchData("http://unexistingapi/things").thenAccept(System.out::println)
                .exceptionally(throwable -> {
                    System.out.println("Message d'erreur : " + throwable.getMessage());
                    return null;
                })
                .join();

        long end = System.currentTimeMillis();
        System.out.println("3. Total execution time: " + (end - start) + " ms");
    }
    // ...
}
```

### Question 6

**Énoncé :** Dans `printAllPostsWithUserAndComments`, affichez tous les posts, les commentaires et les détails de l'utilisateur, pour chaque post :

**composer les trois appels par post**

La méthode `printAllPostsWithUserAndComments`
(`src/main/java/api_call/TestApiService.java`) enchaîne trois combinateurs,
chacun choisi pour une dépendance précise :

1. `fetchPosts().thenCompose(...)` — la suite dépend du **résultat** de la
   liste des posts (il faut les ids), donc composition et non simple `thenApply`
   (qui donnerait un `CompletableFuture<CompletableFuture<...>>`) ;
2. pour chaque post, `fetchCommentsForPost(postId)` et `fetchUser(userId)`
   sont **indépendants** l'un de l'autre : ils partent en parallèle et se
   rejoignent avec `commentsFuture.thenCombine(userFuture, ...)` ;
3. `CompletableFuture.allOf(futures)` attend l'ensemble des posts, et le
   `join()` final bloque le main jusqu'à complétion.

L'affichage est dans un bloc `synchronized (System.out)` pour que les trois
lignes d'un même post restent groupées malgré la concurrence.

*printAllPostsWithUserAndComments* — voir [`src/main/java/api_call/TestApiService.java`](src/main/java/api_call/TestApiService.java).

### Question 7

**Énoncé :** Est-ce que votre affichage est correct ? Il est fort probable que l'affichage dans le terminal se fasse aussi de manière asynchrone… Et que donc vous n'ayez pas les commentaires et les détails d'un utilisateur toujours liés au bon post… Comment pourriez-vous corriger ça ?

**l'affichage est-il correct ?**

Sans précaution, non : chaque post lance ses deux requêtes (commentaires, utilisateur) dans des futures indépendantes, et plusieurs posts sont traités en parallèle. Les trois lignes `System.out.println` d'un même post (post, commentaires, utilisateur) peuvent donc se retrouver entrelacées avec celles d'un autre post en cours de traitement — l'affichage global reste asynchrone même après que les données d'un post précis ont été récupérées.

La correction consiste à rendre atomique, à l'affichage, le groupe des trois lignes d'un même post : un bloc `synchronized` sur un verrou commun à tous les threads (par exemple `System.out` lui-même, puisqu'il est partagé) empêche un autre thread d'intercaler ses propres lignes pendant que ce groupe s'affiche :

```java
synchronized (System.out) {
    System.out.println("Post (postId:" + postId + ") : " + post);
    System.out.println("Comments: (postId:" + postId + ") : " + comments);
    System.out.println("User: (postId:" + postId + ") : " + user);
}
```

Cela ne change rien à l'ordre **relatif** des posts entre eux (toujours asynchrone), seulement à la cohérence de chaque groupe de trois lignes.

### Question 8

**Énoncé :** Combien de requêtes sont faites à l'API au total pour afficher tous les posts avec les commentaires et détails des utilisateurs ?

**nombre de requêtes**

Comme il y a 100 posts : une requête pour obtenir tous les posts, et par post, deux requêtes (commentaires + utilisateur) → 201 requêtes.

### Question 9

**Énoncé :** Quel est le délai d'attente lors de chaque requête ? (voir le code d'`ApiService`)

**délai d'attente par requête**

100 ms (voir le code d'`ApiService`), afin d'éviter que toutes les requêtes soient faites en même temps et que l'API bloque les réponses.

### Question 10

**Énoncé :** Est-ce que le temps total d'exécution de vos requêtes semble bien correspondre à des traitements parallélisés ?

**le temps total correspond-il à un traitement parallélisé ?**

Oui — cela prendrait plus de 20 secondes si les 201 requêtes étaient faites de manière synchrone (201 × 100 ms), alors que l'exécution asynchrone les chevauche largement.

## Parties optionnelles

### `ExecutorService` avec pool de threads fixe

#### Question 11

**Énoncé :** Dans `printAllPostsWithUserAndCommentsAnd2Threads`, refaites la Question 6 mais en utilisant un `ExecutorService` avec un pool de threads fixe. Limitez le nombre de threads à 2.

**la même chose sur 2 threads**

`printAllPostsWithUserAndCommentsAnd2Threads` est identique à la Question 6 —
seule différence : les appels passent par un `ApiServiceWithExecutor(2)`,
une copie d'`ApiService` dont `fetchData` donne son propre pool à `supplyAsync`.
Toutes les requêtes se partagent donc un pool fixe de 2 threads
au lieu du `ForkJoinPool` commun. La structure de la chaîne ne change pas ;
comparez les temps d'exécution affichés pour voir l'effet du pool réduit.

*ExecutorService avec pool de threads fixe — attributs et constructeurs* — [`src/main/java/api_call/ApiServiceWithExecutor.java`](src/main/java/api_call/ApiServiceWithExecutor.java) :

```java
package api_call;

/**
 * La même API que ApiService, mais exécutée sur un pool de threads dédié.
 *
 * Par défaut, `supplyAsync` travaille sur le ForkJoinPool commun, partagé par toute
 * l'application. Fournir son propre executor donne la maîtrise du nombre de threads — et permet
 * d'observer l'effet de cette taille sur la durée totale des appels.
 */
public class ApiServiceWithExecutor {
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private ExecutorService executorService;

    /**
     * Crée le service avec un pool dimensionné au nombre de processeurs disponibles.
     */
    public ApiServiceWithExecutor() {
        this(Runtime.getRuntime().availableProcessors());
    }

    /**
     * Crée le service avec un pool de taille fixe.
     *
     * @param nbrThreads le nombre de threads du pool
     */
    public ApiServiceWithExecutor(int nbrThreads) {
        executorService = Executors.newFixedThreadPool(nbrThreads);
        System.out.println("Pool de Threads fixé à : " + nbrThreads);
    }
    // ...
}
```

*fetchData — supplyAsync exécuté sur le pool fourni au lieu du ForkJoinPool commun* — [`src/main/java/api_call/ApiServiceWithExecutor.java`](src/main/java/api_call/ApiServiceWithExecutor.java) :

```java
public class ApiServiceWithExecutor {
    private ExecutorService executorService;
    // ...
    /**
     * Appelle une URL et rend sa réponse, sur le pool dédié du service.
     *
     * @param url l'adresse à appeler
     * @return une future portant le corps de la réponse
     */
    public CompletableFuture<String> fetchData(String url) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(100); // Délai pour éviter que l'API ne bloque les requêtes
            } catch (InterruptedException e) {
                throw new RuntimeException("Attente interrompue avant appel API", e);
            }
            return request;
        }, executorService).thenCompose(req -> httpClient.sendAsync(req, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body));
    }
}
```

*identique à la Question 6, avec ApiServiceWithExecutor(2)* — voir [`src/main/java/api_call/TestApiService.java`](src/main/java/api_call/TestApiService.java).

---

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
