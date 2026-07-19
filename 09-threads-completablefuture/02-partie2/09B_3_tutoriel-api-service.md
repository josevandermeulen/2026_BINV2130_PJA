# Tutoriel : comprendre `ApiService` et `CompletableFuture<String>`

Ce tutoriel s'applique à la partie 2 de l'atelier 9, section « Appels asynchrones à une API REST ». Il détaille le fonctionnement des méthodes `fetchPosts`, `fetchCommentsForPost` et `fetchUser` de `ApiService`, avant de vous lancer dans les Questions 3 à 7.

## Étape 1 — Les méthodes fournies : `ApiService`

`ApiService` expose trois méthodes, chacune interrogeant l'API publique [jsonplaceholder.typicode.com](https://jsonplaceholder.typicode.com/) :

| Méthode | Ce qu'elle fait |
|---|---|
| `fetchPosts()` | récupère tous les posts |
| `fetchCommentsForPost(int postId)` | récupère les commentaires d'un post donné |
| `fetchUser(int userId)` | récupère les détails d'un utilisateur donné |

Chacune renvoie un `CompletableFuture<String>` : **le JSON n'est pas encore là au moment où la méthode retourne** — il arrivera plus tard, dans le futur. Rien n'est encore parsé : c'est à vous de le faire avec Jackson (voir Étape 4).

## Étape 2 — Récupérer le résultat

Un `CompletableFuture<String>` propose plusieurs façons de consommer le body une fois arrivé.

**Bloquant** (attend le résultat avant de continuer) :

```java
String json = apiService.fetchUser(1).join(); // ou .get() qui déclare une exception vérifiée
System.out.println(json);
```

Résultat :

```json
{
  "id": 1,
  "name": "Leanne Graham",
  "username": "Bret",
  "email": "Sincere@april.biz",
  "address": {
    "street": "Kulas Light",
    "suite": "Apt. 556",
    "city": "Gwenborough",
    "zipcode": "92998-3874",
    "geo": { "lat": "-37.3159", "lng": "81.1496" }
  },
  "phone": "1-770-736-8031 x56442",
  "website": "hildegard.org",
  "company": {
    "name": "Romaguera-Crona",
    "catchPhrase": "Multi-layered client-server neural-net",
    "bs": "harness real-time e-markets"
  }
}
```

**Non bloquant**, en enchaînant une action sur le résultat :

```java
apiService.fetchUser(1)
        .thenAccept(json -> System.out.println(json)) // consomme le résultat, ne retourne rien
        .join(); // pour empêcher le programme (ou le test) de se terminer avant la fin de l'appel
```

Résultat : identique au bloc précédent, mais affiché sans bloquer le thread appelant pendant l'attente.

`thenAccept` prend une `Consumer<String>` et ne retourne pas de nouvelle valeur utile (`CompletableFuture<Void>`). Le `.join()` final reste nécessaire : sans lui, `main` peut se terminer avant que la requête HTTP asynchrone n'ait fini.

## Étape 3 — Transformer le résultat avec `thenApply`

`thenApply` permet de transformer le `String` JSON en autre chose (par exemple un `JsonNode` Jackson), tout en restant dans un `CompletableFuture` :

```java
ObjectMapper objectMapper = new ObjectMapper();

apiService.fetchUser(1)
        .thenApply(json -> {
            try {
                return objectMapper.readTree(json); // String -> JsonNode
            } catch (Exception e) {
                throw new RuntimeException("Erreur de parsing", e);
            }
        })
        .thenAccept(userJson -> System.out.println("Nom : " + userJson.get("name").asText()))
        .join();
```

Résultat :

```
Nom : Leanne Graham
```

`objectMapper.readTree(json)` lance une exception vérifiée (`JsonProcessingException`) : comme une lambda de `thenApply` ne peut pas déclarer d'exception vérifiée, on l'attrape et on la relance en `RuntimeException` (`printStackTrace()` est à éviter ici — préférez toujours propager la cause avec `throw new RuntimeException(message, e)`).

## Étape 4 — Naviguer dans un `JsonNode`

À partir de la `String` `posts` (JSON retourné par `fetchPosts`), créez un `JsonNode` :

```java
JsonNode postsJson = objectMapper.readTree(posts);
```

Une fois le JSON parsé, on navigue avec `get(...)` :

```java
JsonNode posts = objectMapper.readTree(postsJson);   // tableau -> get(index)
JsonNode post = posts.get(0);
int postId = post.get("id").asInt();                  // objet -> get("propriété")
String title = post.get("title").asText();

System.out.println("postId = " + postId);
System.out.println("title = " + title);
System.out.println("nombre de posts = " + posts.size());
```

Résultat :

```
postId = 1
title = sunt aut facere repellat provident occaecati excepturi optio reprehenderit
nombre de posts = 100
```

## Étape 5 — Enchaîner deux appels dépendants avec `thenCompose`

Si le deuxième appel a besoin du résultat du premier (ex. : parser les posts puis, pour l'un d'eux, aller chercher son utilisateur), il faut `thenCompose` plutôt que `thenApply` : la lambda retourne elle-même un `CompletableFuture`, et `thenCompose` « aplatit » le résultat (évite un `CompletableFuture<CompletableFuture<...>>`).

```java
apiService.fetchPosts()
        .thenCompose(postsJson -> {
            try {
                JsonNode posts = objectMapper.readTree(postsJson);
                int userId = posts.get(0).get("userId").asInt();
                return apiService.fetchUser(userId); // renvoie un CompletableFuture<String>
            } catch (Exception e) {
                throw new RuntimeException("Erreur de parsing", e);
            }
        })
        .thenAccept(userJson -> System.out.println(userJson))
        .join();
```

Résultat : le `userId` du premier post vaut `1`, donc le JSON affiché est celui de l'utilisateur `1` (identique au résultat de l'Étape 2).

## Étape 6 — Lancer deux appels indépendants en parallèle avec `thenCombine`

Quand deux appels **ne dépendent pas l'un de l'autre** (ex. : les commentaires d'un post et l'utilisateur qui l'a écrit), lancez-les tous les deux avant d'attendre quoi que ce soit, puis combinez-les :

```java
CompletableFuture<String> commentsFuture = apiService.fetchCommentsForPost(1);
CompletableFuture<String> userFuture = apiService.fetchUser(1);

commentsFuture.thenCombine(userFuture, (commentsJson, userJson) -> {
    System.out.println("Commentaires : " + commentsJson);
    System.out.println("Utilisateur : " + userJson);
    return null;
}).join();
```

Résultat (extrait — 5 commentaires au total pour le post 1) :

```
Commentaires : [
  {
    "postId": 1,
    "id": 1,
    "name": "id labore ex et quam laborum",
    "email": "Eliseo@gardner.biz",
    "body": "laudantium enim quasi est quidem magnam voluptate ipsam eos\ntempora quo necessitatibus\ndolor quam autem quasi\nreiciendis et nam sapiente accusantium"
  },
  ...
]
Utilisateur : { "id": 1, "name": "Leanne Graham", ... }
```

Les deux requêtes HTTP partent **en même temps** (chacune sur son propre thread du pool commun) : le temps total est proche du plus lent des deux appels, pas de leur somme.

## Étape 7 — Gérer les erreurs

`fetchData` ne vérifie pas le code de statut HTTP : si l'hôte n'existe pas ou que la connexion échoue, l'exception remonte dans le `CompletableFuture`. Pour la récupérer sans faire planter le programme, utilisez `exceptionally` (ou `handle` si vous avez aussi besoin du résultat en cas de succès) :

```java
apiService.fetchData("http://unexistingapi/things")
        .thenAccept(System.out::println)
        .exceptionally(throwable -> {
            System.out.println("Erreur : " + throwable.getMessage());
            return null; // valeur de remplacement, requise par la signature
        })
        .join();
```

Résultat (le message exact dépend de l'OS/réseau, mais l'idée reste la même) :

```
Erreur : java.net.ConnectException
```

`throwable` reçu par `exceptionally` est le plus souvent un `CompletionException` qui **enveloppe** l'exception réelle (accessible via `throwable.getCause()`) — mais `throwable.getMessage()` suffit déjà pour afficher un message clair dans la plupart des cas.

## Dépannage

- **Rien ne s'affiche / le programme se termine trop tôt** : il manque un `.join()` (ou `.get()`) en bout de chaîne — sans lui, `main` ne bloque pas et se termine avant que la requête réseau soit terminée.
- **`JsonProcessingException` non déclarée** dans une lambda `thenApply`/`thenAccept` : encapsulez le parsing dans un bloc `try/catch` et relancez en `RuntimeException(message, e)`.
- **L'ordre d'affichage semble mélangé** quand plusieurs posts sont traités en parallèle (Question 6) : c'est normal, chaque `CompletableFuture` s'exécute sur son propre thread — voir les observations de la Question 6.

---

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
