package compteur_future;

import java.util.concurrent.CompletableFuture;

/**
 * Une course entre compteurs, orchestrée par des CompletableFuture.
 *
 * Tout l'intérêt est de lancer les compteurs sans les attendre un à un : la course dure le temps
 * du plus lent, non la somme des temps de chacun.
 */
public class CourseCompteurs {

    // Question 1
    /**
     * Lance tous les compteurs et rend une future qui s'achève quand le dernier est arrivé.
     *
     * @param compteurs les compteurs engagés dans la course
     * @return une future achevée à la fin de la course
     */
    public CompletableFuture<Void> attendreResultatsAsync(CompteurEtPosition[] compteurs) {
        CompletableFuture<?>[] futures = new CompletableFuture<?>[compteurs.length];
        for (int i = 0; i < compteurs.length; i++) {
            CompteurEtPosition compteur = compteurs[i];
            futures[i] = compteur.countAndGetPositionAsync()
                    .thenAccept(position -> System.out.println("Compteur : " + compteur.getNom() + " - Position : " + position));
        }
        return CompletableFuture.allOf(futures);
    }

    // Question 2
    /**
     * Variante construisant les futures depuis la méthode bloquante de chaque compteur.
     *
     * @param compteurs les compteurs engagés dans la course
     * @return une future achevée à la fin de la course
     */
    public CompletableFuture<Void> creerFuturesDepuisCountAndGetPosition(CompteurEtPosition[] compteurs) {
        CompletableFuture<?>[] futures = new CompletableFuture<?>[compteurs.length];
        for (int i = 0; i < compteurs.length; i++) {
            CompteurEtPosition compteur = compteurs[i];
            futures[i] = CompletableFuture.runAsync(() ->
                    System.out.println("Compteur : " + compteur.getNom() + " - Position : " + compteur.countAndGetPosition()));
        }
        return CompletableFuture.allOf(futures);
    }

}
