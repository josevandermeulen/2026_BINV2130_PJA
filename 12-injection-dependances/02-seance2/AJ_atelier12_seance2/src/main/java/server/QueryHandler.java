package server;

import domaine.Query;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

/**
 * L'envoi effectif d'une requête et l'affichage de la réponse.
 *
 * L'appel part de façon asynchrone : le proxy reste disponible pour la requête suivante sans
 * attendre la réponse de la précédente.
 */
public class QueryHandler {

    private Query query;

    /**
     * Crée le gestionnaire d'une requête.
     *
     * @param query la requête à envoyer
     */
    public QueryHandler(Query query) {
        this.query = query;
    }

    /**
     * Envoie la requête et affiche la réponse dès qu'elle arrive.
     *
     * @return une future achevée quand la réponse a été affichée
     * @throws IllegalStateException si la requête emploie une méthode HTTP autre que GET
     */
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
