package exceptions;

/**
 * Signale l'achat d'une quantité pour laquelle aucun palier de prix n'a été défini.
 *
 * Non contrôlée : c'est une erreur d'usage, pas un cas de fonctionnement normal.
 */
public class QuantiteNonAutoriseeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Crée l'exception sans message.
     */
    public QuantiteNonAutoriseeException() {
    }

    /**
     * Crée l'exception avec un message précisant en quoi la quantité achetée est
     * inférieure au plus petit palier défini.
     *
     * @param message le message décrivant la situation rencontrée
     */
    public QuantiteNonAutoriseeException(String message) {
        super(message);
    }

}
