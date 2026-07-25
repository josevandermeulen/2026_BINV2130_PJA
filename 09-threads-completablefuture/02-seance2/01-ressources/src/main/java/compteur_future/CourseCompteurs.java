package compteur_future;

import java.util.concurrent.CompletableFuture;

public class CourseCompteurs {

    public CompletableFuture<Void> attendreResultatsAsync(CompteurEtPosition[] compteurs) {
        // TODO Question 1 : pour chaque compteur, lancer countAndGetPositionAsync() puis
        //  afficher sa position dès qu'elle arrive (thenAccept, comme dans le style synchrone
        //  "Compteur : <nom> - Position : <position>"). Renvoyer une future qui se termine
        //  quand TOUS les compteurs ont fini (CompletableFuture.allOf).
        throw new UnsupportedOperationException("À implémenter");
    }

    public CompletableFuture<Void> creerFuturesDepuisCountAndGetPosition(CompteurEtPosition[] compteurs) {
        // TODO Question 2 : même résultat que la Question 1, mais en lançant chaque traitement
        //  à partir de la méthode SYNCHRONE countAndGetPosition, via CompletableFuture.runAsync
        throw new UnsupportedOperationException("À implémenter");
    }

}
