package api_call;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

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


    /**
     * Affiche tous les posts, en attendant la réponse de l'API.
     */
    public static void printAllPosts() {
        System.out.println("1. Imprimer tous les posts");
        long start = System.currentTimeMillis();

        // TODO Question 3 : affichez tous les posts dans le terminal (utiliser la méthode fetchPosts de ApiService)

        long end = System.currentTimeMillis();
        System.out.println("1. Total execution time: " + (end - start) + " ms");

    }

    /**
     * Affiche tous les posts et leur nombre.
     */
    public static void printAllPostsWithCount() {
        System.out.println("2. Imprimer tous les posts suivi du nombre de posts");
        long start = System.currentTimeMillis();


        // TODO Question 4 : affichez tous les posts, retourner les posts sous forme d'un JsonNode
        //  puis, affichez le nombre de posts


        long end = System.currentTimeMillis();
        System.out.println("2. Total execution time: " + (end - start) + " ms");

    }

    /**
     * Appelle une adresse inexistante, pour montrer comment l'échec se propage dans la chaîne de futures.
     */
    public static void dealWithUnexistedApi() {
        System.out.println("3. Gérer une erreur lorsqu'une API n'existe pas");
        long start = System.currentTimeMillis();

        // TODO Question 5 : tentez d'afficher le résultat d'une API qui n'existe pas
        //  en utilisant la méthode fetchData de ApiService.
        //  Gérez l'exception en affichant le code d'erreur retourné
        //  par la méthode fetchData.

        long end = System.currentTimeMillis();
        System.out.println("3. Total execution time: " + (end - start) + " ms");
    }

    /**
     * Pour chaque post, récupère son auteur et ses commentaires en enchaînant les futures.
     */
    public static void printAllPostsWithUserAndComments() {
        System.out.println("4. Imprimer tous les posts avec les commentaires et les détails de l'utilisateur");
        long start = System.currentTimeMillis();

        // TODO Question 6 : affichez tous les posts, les commentaires et les détails de l'utilisateur sous un format du
        //  genre : "Post (postId:1) : {post details}
        //           Comments: (postId:1) : [{comments details}]
        //           User: (postId:1) : {user details}"
        //  Pour chaque "post", vous devez lancer en parallèle toute les requêtes pour
        //  récupérer les commentaires et les détails de l'utilisateur. De plus, vous devez faire attention à
        //  attendre que tant les commentaires que les détails de l'utilisateur soient récupérés avant d'afficher
        //  toutes les infos pour un post donné.

        long end = System.currentTimeMillis();
        System.out.println("4. Total execution time: " + (end - start) + " ms");
    }

    /**
     * Même traitement, mais sur un pool de deux threads : la durée totale change, pas le résultat.
     */
    public static void printAllPostsWithUserAndCommentsAnd2Threads() {
        System.out.println("5. Imprimer tous les posts avec les commentaires et les détails de "
                + "l'utilisateur en utilisant 2 threads uniquement");
        long start = System.currentTimeMillis();

        // TODO Question 11 (optionnelle) : même exercice que précédemment (Question 6), mais en utilisant
        //  que deux Threads pour ApiService.
        //  Nous vous conseillons de créer une nouvelle classe ApiServiceWithExecutor qui contiendra un ExecutorService.
        //  Affichez tous les posts, les commentaires et les détails de l'utilisateur sous un format du
        //  genre : "Post (postId:1) : {post details}
        //           Comments: (postId:1) : [{comments details}]
        //           User: (postId:1) : {user details}"
        //  Pour chaque "post", vous devez lancer en parallèle toute les requêtes pour
        //  récupérer les commentaires et les détails de l'utilisateur. De plus, vous devez faire attention à
        //  attendre que tant les commentaires que les détails de l'utilisateur soient récupérés avant d'afficher
        //  toutes les infos pour un post donné.

        long end = System.currentTimeMillis();
        System.out.println("5. Total execution time: " + (end - start) + " ms");
    }
}
