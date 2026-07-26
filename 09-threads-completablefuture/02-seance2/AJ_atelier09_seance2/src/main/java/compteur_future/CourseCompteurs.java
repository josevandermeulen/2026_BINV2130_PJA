package compteur_future;

import java.util.concurrent.CompletableFuture;

/**
 * Une course entre compteurs, orchestrée par des CompletableFuture.
 *
 * Tout l'intérêt est de lancer les compteurs sans les attendre un à un : la course dure le temps
 * du plus lent, non la somme des temps de chacun.
 */
public class CourseCompteurs {

    /**
     * Lance tous les compteurs et rend une future qui s'achève quand le dernier est arrivé.
     *
     * @param compteurs les compteurs engagés dans la course
     * @return une future achevée à la fin de la course
     */
    public CompletableFuture<Void> attendreResultatsAsync(CompteurEtPosition[] compteurs) {
        // TODO Question 1 : pour chaque compteur, lancer countAndGetPositionAsync() puis
        //  afficher sa position dès qu'elle arrive (thenAccept, comme dans le style synchrone
        //  "Compteur : <nom> - Position : <position>"). Renvoyer une future qui se termine
        //  quand TOUS les compteurs ont fini (CompletableFuture.allOf).
        throw new UnsupportedOperationException("À implémenter");
    }

    /**
     * Variante construisant les futures depuis la méthode bloquante de chaque compteur.
     *
     * @param compteurs les compteurs engagés dans la course
     * @return une future achevée à la fin de la course
     */
    public CompletableFuture<Void> creerFuturesDepuisCountAndGetPosition(CompteurEtPosition[] compteurs) {
        // TODO Question 2 : même résultat que la Question 1, mais en lançant chaque traitement
        //  à partir de la méthode SYNCHRONE countAndGetPosition, via CompletableFuture.runAsync
        throw new UnsupportedOperationException("À implémenter");
    }

}
