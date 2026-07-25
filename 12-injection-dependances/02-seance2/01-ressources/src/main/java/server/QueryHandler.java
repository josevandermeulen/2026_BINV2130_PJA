package server;

import domaine.Query;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class QueryHandler {

    private Query query;

    public QueryHandler(Query query) {
        this.query = query;
    }

    public CompletableFuture<Void> sendQueryAndPrintResponse() {
        if (query.getMethod() != Query.QueryMethod.GET) {
            throw new IllegalStateException("Only GET method is currently supported");
        }

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(query.getUrl()))
                .GET()
                .build();

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    System.out.println(response.statusCode());
                    return response;
                })
                .thenApply(HttpResponse::body)
                .thenAccept(System.out::println);
    }
}
