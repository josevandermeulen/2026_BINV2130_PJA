package exceptions;

/**
 * Signale une opération sur un produit absent de la liste.
 *
 * Non contrôlée : c'est une erreur d'usage, pas un cas de fonctionnement normal.
 */
public class ProduitNonPresentException extends RuntimeException {

    /**
     * Crée l'exception sans message.
     */
    public ProduitNonPresentException() {
    }

    /**
     * Crée l'exception avec un message précisant quel produit est absent.
     *
     * @param arg0 le message décrivant la situation rencontrée
     */
    public ProduitNonPresentException(String arg0) {
        super(arg0);
    }

    /**
     * Crée l'exception à partir de la cause qui l'a provoquée.
     *
     * @param arg0 l'exception d'origine
     */
    public ProduitNonPresentException(Throwable arg0) {
        super(arg0);
    }

    /**
     * Crée l'exception avec un message et la cause qui l'a provoquée.
     *
     * @param arg0 le message décrivant la situation rencontrée
     * @param arg1 l'exception d'origine
     */
    public ProduitNonPresentException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    /**
     * Crée l'exception en réglant la suppression et la trace d'appels.
     *
     * @param arg0 le message décrivant la situation rencontrée
     * @param arg1 l'exception d'origine
     * @param arg2 true pour autoriser la suppression de l'exception
     * @param arg3 true pour que la trace d'appels soit remplie
     */
    public ProduitNonPresentException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
        super(arg0, arg1, arg2, arg3);
    }

}
