package profil;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import profil.domaine.CompleteUserProfile;
import profil.service.UserBasicInfoService;
import profil.service.UserPreferencesService;

/**
 * Agrège en un profil unique les données renvoyées par plusieurs services backend,
 * chacun ayant son propre temps de réponse.
 */
public class UserProfileAggregator {

    private final UserBasicInfoService basicInfoService;

    private final UserPreferencesService preferencesService;

    /**
     * Construit l'agrégateur au-dessus des deux services à interroger.
     *
     * @param basicInfoService   service des informations de base
     * @param preferencesService service des préférences
     */
    public UserProfileAggregator(UserBasicInfoService basicInfoService,
            UserPreferencesService preferencesService) {
        this.basicInfoService = basicInfoService;
        this.preferencesService = preferencesService;
    }

    /**
     * Exerce les deux méthodes de l'agrégateur : un profil seul, puis plusieurs profils.
     *
     * @param args arguments de la ligne de commande, inutilisés
     */
    public static void main(String[] args) {
        UserProfileAggregator aggregator = new UserProfileAggregator(new UserBasicInfoService(),
                new UserPreferencesService());

        System.out.println("=== Test 1: Récupération d'un Profil Unique ===");
        System.out.println(aggregator.fetchCompleteProfile("user123").join());

        System.out.println("\n=== Test 2: Récupération de Plusieurs Profils ===");
        List<CompleteUserProfile> profiles = aggregator.fetchMultipleProfiles(List.of("alice", "bob", "charlie"));
        System.out.println("Récupéré " + profiles.size() + " profils :");
        profiles.forEach(System.out::println);

        System.out.println("\n=== Tous les tests sont terminés ===");
    }

    /**
     * Récupère le profil complet d'un utilisateur en interrogeant les deux services en parallèle.
     * La méthode ne bloque pas : elle décrit la chaîne et rend la main immédiatement.
     *
     * @param userId identifiant de l'utilisateur
     * @return future contenant le profil complet
     */
    public CompletableFuture<CompleteUserProfile> fetchCompleteProfile(String userId) {
        // TODO Question 9 : combiner les deux services avec thenCombine, sans jamais bloquer
        throw new UnsupportedOperationException("Pas encore implémenté");
    }

    /**
     * Récupère les profils complets de plusieurs utilisateurs, tous traités en parallèle.
     *
     * @param userIds identifiants des utilisateurs
     * @return profils complets, dans l'ordre de la liste reçue
     */
    public List<CompleteUserProfile> fetchMultipleProfiles(List<String> userIds) {
        // TODO Question 10 : une future par utilisateur, allOf pour les attendre, puis join sur chacune
        throw new UnsupportedOperationException("Pas encore implémenté");
    }
}
