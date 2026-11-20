package api_call;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.concurrent.CompletableFuture;

/**
 * Programme de démonstration : enchaîne des appels asynchrones à l'API REST et affiche les réponses obtenues.
 */
public class TestApiService {
    private static ApiService apiService = new ApiService();
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) {
        printAllPosts();
        printAllPostsWithCount();
        dealWithUnexistedApi();
        printAllPostsWithUserAndComments();
        printAllPostsWithUserAndCommentsAnd2Threads(); // Question 11 (optionnelle)
    }


    // Question 3
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

    // Question 4
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

    // Question 5
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

    // Question 6 [pointeur] : printAllPostsWithUserAndComments
    /**
     * Pour chaque post, récupère son auteur et ses commentaires en enchaînant les futures.
     */
    public static void printAllPostsWithUserAndComments() {
        System.out.println("4. Imprimer tous les posts avec les commentaires et les détails de l'utilisateur");
        long start = System.currentTimeMillis();

        apiService.fetchPosts()
                .thenCompose(postsJson -> {
                    try {
                        JsonNode posts = objectMapper.readTree(postsJson);
                        int postLimit = posts.size();
                        CompletableFuture<Void>[] futures = new CompletableFuture[postLimit];

                        for (int index = 0; index < postLimit; index++) {
                            JsonNode post = posts.get(index);
                            int postId = post.get("id").asInt();
                            int userId = post.get("userId").asInt();

                            CompletableFuture<String> commentsFuture = apiService.fetchCommentsForPost(postId);
                            CompletableFuture<String> userFuture = apiService.fetchUser(userId);

                            futures[index] = commentsFuture.thenCombine(userFuture, (commentsJson, userJson) -> {
                                try {
                                    JsonNode comments = objectMapper.readTree(commentsJson);
                                    JsonNode user = objectMapper.readTree(userJson);
                                    synchronized (System.out) {
                                        System.out.println("Post (postId:" + postId + ") : " + post);
                                        System.out.println("Comments: (postId:" + postId + ") : " + comments);
                                        System.out.println("User: (postId:" + postId + ") : " + user + "\n");
                                    }

                                } catch (Exception e) {
                                    throw new RuntimeException(
                                            "Erreur de parsing des commentaires ou de l'utilisateur", e);
                                }
                                return null;
                            });
                        }

                        return CompletableFuture.allOf(futures);
                    } catch (Exception e) {
                        throw new RuntimeException("Erreur de parsing des posts", e);
                    }
                }).join();

        long end = System.currentTimeMillis();
        System.out.println("4. Total execution time: " + (end - start) + " ms");
    }

    // Question 11 [pointeur] : identique à la Question 6, avec ApiServiceWithExecutor(2)
    /**
     * Même traitement, mais sur un pool de deux threads : la durée totale change, pas le résultat.
     */
    public static void printAllPostsWithUserAndCommentsAnd2Threads() {
        System.out.println("5. Imprimer tous les posts avec les commentaires et les détails de "
                + "l'utilisateur en utilisant 2 threads uniquement");
        long start = System.currentTimeMillis();

        ApiServiceWithExecutor apiServiceWithExecutor = new ApiServiceWithExecutor(2);
        apiServiceWithExecutor.fetchPosts()
                .thenCompose(postsJson -> {
                    try {
                        JsonNode posts = objectMapper.readTree(postsJson);
                        int postLimit = posts.size();
                        CompletableFuture<Void>[] futures = new CompletableFuture[postLimit];

                        for (int index = 0; index < postLimit; index++) {
                            JsonNode post = posts.get(index);
                            int postId = post.get("id").asInt();
                            int userId = post.get("userId").asInt();

                            CompletableFuture<String> commentsFuture =
                                    apiServiceWithExecutor.fetchCommentsForPost(postId);
                            CompletableFuture<String> userFuture = apiServiceWithExecutor.fetchUser(userId);

                            futures[index] = commentsFuture.thenCombine(userFuture, (commentsJson, userJson) -> {
                                try {
                                    JsonNode comments = objectMapper.readTree(commentsJson);
                                    JsonNode user = objectMapper.readTree(userJson);
                                    synchronized (System.out) {
                                        System.out.println("Post (postId:" + postId + ") : " + post);
                                        System.out.println("Comments: (postId:" + postId + ") : " + comments);
                                        System.out.println("User: (postId:" + postId + ") : " + user + "\n");
                                    }

                                } catch (Exception e) {
                                    throw new RuntimeException(
                                            "Erreur de parsing des commentaires ou de l'utilisateur", e);
                                }
                                return null;
                            });
                        }

                        return CompletableFuture.allOf(futures);
                    } catch (Exception e) {
                        throw new RuntimeException("Erreur de parsing des posts", e);
                    }
                }).join();

        long end = System.currentTimeMillis();
        System.out.println("5. Total execution time: " + (end - start) + " ms");
    }

}
