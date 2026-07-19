package api_call;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;


public class ApiService {
    private final HttpClient httpClient = HttpClient.newHttpClient();

    public CompletableFuture<String> fetchPosts() {
        return fetchData("https://jsonplaceholder.typicode.com/posts");
    }

    public CompletableFuture<String> fetchCommentsForPost(int postId) {
        return fetchData("https://jsonplaceholder.typicode.com/posts/" + postId + "/comments");
    }

    public CompletableFuture<String> fetchUser(int userId) {
        return fetchData("https://jsonplaceholder.typicode.com/users/" + userId);
    }


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
