package source;

/**
 * Signale qu'une source de bibliothèques n'a pas pu répondre : réseau coupé, serveur en panne,
 * réponse illisible…
 *
 * C'est une exception non vérifiée : on n'est pas obligé de la déclarer ni de l'attraper.
 */
public class SourceIndisponibleException extends RuntimeException {

    /**
     * Crée l'exception avec un message.
     *
     * @param message la description du problème
     */
    public SourceIndisponibleException(String message) {
        super(message);
    }

    /**
     * Crée l'exception avec un message et la cause d'origine.
     *
     * @param message la description du problème
     * @param cause   l'exception qui a provoqué le problème
     */
    public SourceIndisponibleException(String message, Throwable cause) {
        super(message, cause);
    }
}
