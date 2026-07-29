package profil.service;

import java.util.concurrent.CompletableFuture;

import profil.domaine.BasicInfo;

/**
 * Service simulé renvoyant les informations de base d'un utilisateur.
 * Temps de réponse moyen : ~200 ms.
 */
public class UserBasicInfoService {

    /**
     * Récupère les informations de base de l'utilisateur de manière asynchrone.
     *
     * @param userId identifiant de l'utilisateur
     * @return future contenant les informations de base
     */
    public CompletableFuture<BasicInfo> fetchBasicInfo(String userId) {
        return CompletableFuture.supplyAsync(() -> {
            SimulationUtil.simulateDelay(200);

            return new BasicInfo("Utilisateur " + userId, userId + "@example.com");
        });
    }
}
