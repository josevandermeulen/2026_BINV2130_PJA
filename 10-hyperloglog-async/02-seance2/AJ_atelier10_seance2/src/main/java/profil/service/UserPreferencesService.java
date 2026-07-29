package profil.service;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

import profil.domaine.Preferences;

/**
 * Service simulé renvoyant les préférences d'un utilisateur.
 * Temps de réponse moyen : ~150 ms.
 */
public class UserPreferencesService {

    private static final String[] THEMES = {"light", "dark", "auto"};

    private static final String[] LANGUAGES = {"fr", "en", "de", "es"};

    private final Random random = new Random();

    /**
     * Récupère les préférences de l'utilisateur de manière asynchrone.
     * Le thème et la langue sont tirés au hasard : deux exécutions ne donnent pas le même résultat.
     *
     * @param userId identifiant de l'utilisateur
     * @return future contenant les préférences
     */
    public CompletableFuture<Preferences> fetchPreferences(String userId) {
        return CompletableFuture.supplyAsync(() -> {
            SimulationUtil.simulateDelay(150);

            return new Preferences(THEMES[random.nextInt(THEMES.length)],
                    LANGUAGES[random.nextInt(LANGUAGES.length)]);
        });
    }
}
