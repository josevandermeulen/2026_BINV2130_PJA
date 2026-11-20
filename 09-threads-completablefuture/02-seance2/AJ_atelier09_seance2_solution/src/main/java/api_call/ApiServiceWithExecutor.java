package api_call;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Question 11 : ExecutorService avec pool de threads fixe — attributs et constructeurs
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
    // FinQuestion

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


    // Question 11 : fetchData — supplyAsync exécuté sur le pool fourni au lieu du ForkJoinPool commun
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
