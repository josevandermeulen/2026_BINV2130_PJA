package exceptions;

/**
 * Signale l'ajout d'un prix à une date déjà présente dans l'historique d'un produit.
 *
 * Non contrôlée : un même produit ne peut pas avoir deux prix valables à la même date.
 */
public class DateDejaPresenteException extends RuntimeException {

    private static final long serialVersionUID = 3027926842047880357L;

    /**
     * Crée l'exception sans message.
     */
    public DateDejaPresenteException() {
    }

    /**
     * Crée l'exception avec un message précisant en quoi la date porte déjà un prix.
     *
     * @param message le message décrivant la situation rencontrée
     */
    public DateDejaPresenteException(String message) {
        super(message);
    }

}
