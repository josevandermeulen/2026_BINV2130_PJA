package profil.service;

import java.util.Random;

/**
 * Simule la latence réseau des services backend.
 */
public class SimulationUtil {

    private static final Random RANDOM = new Random();

    private SimulationUtil() {
        // classe utilitaire : pas d'instanciation
    }

    /**
     * Endort le thread courant pendant la durée demandée, augmentée d'un aléa de 0 à 49 ms.
     *
     * @param milliseconds durée de base du délai, en millisecondes
     */
    public static void simulateDelay(int milliseconds) {
        try {
            Thread.sleep(milliseconds + RANDOM.nextInt(50));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
