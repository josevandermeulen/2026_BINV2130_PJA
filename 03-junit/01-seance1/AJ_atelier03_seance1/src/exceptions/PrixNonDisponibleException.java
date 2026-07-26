package exceptions;

/**
 * Signale qu'aucun prix n'était en vigueur à la date demandée.
 *
 * Non contrôlée : elle survient quand la date interrogée précède le premier prix connu du produit.
 */
public class PrixNonDisponibleException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Crée l'exception sans message.
     */
    public PrixNonDisponibleException() {
    }

    /**
     * Crée l'exception avec un message précisant en quoi aucun prix n'est disponible à cette date.
     *
     * @param message le message décrivant la situation rencontrée
     */
    public PrixNonDisponibleException(String message) {
        super(message);
    }

}
