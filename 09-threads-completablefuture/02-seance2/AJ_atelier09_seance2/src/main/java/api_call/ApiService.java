package api_call;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;


/**
 * Les appels à l'API REST distante, rendus asynchrones par CompletableFuture.
 *
 * Chaque méthode rend immédiatement la main : le résultat n'arrive que plus tard, quand le réseau
 * a répondu. Enchaîner ces futures évite d'attendre chaque appel avant de lancer le suivant.
 */
public class ApiService {
    private final HttpClient httpClient = HttpClient.newHttpClient();

    /**
     * Demande la liste des posts.
     *
     * @return une future portant le corps de la réponse
     */
    public CompletableFuture<String> fetchPosts() {
        return fetchData("https://jsonplaceholder.typicode.com/posts");
    }

    /**
     * Demande les commentaires d'un post.
     *
     * @param postId l'identifiant du post
     * @return une future portant le corps de la réponse
     */
    public CompletableFuture<String> fetchCommentsForPost(int postId) {
        return fetchData("https://jsonplaceholder.typicode.com/posts/" + postId + "/comments");
    }

    /**
     * Demande la fiche d'un utilisateur.
     *
     * @param userId l'identifiant de l'utilisateur
     * @return une future portant le corps de la réponse
     */
    public CompletableFuture<String> fetchUser(int userId) {
        return fetchData("https://jsonplaceholder.typicode.com/users/" + userId);
    }


    /**
     * Appelle une URL et rend sa réponse, sur le pool commun.
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
                e.printStackTrace();
            }
            return request;
        }).thenCompose(req -> httpClient.sendAsync(req, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body));
    }
}
