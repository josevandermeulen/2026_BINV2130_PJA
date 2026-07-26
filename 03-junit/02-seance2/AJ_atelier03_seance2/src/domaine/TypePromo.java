package domaine;

/**
 * Les formes de promotion applicables à un Prix.
 *
 * Le type détermine comment interpréter la valeur de la promo : montant fixe en euros pour
 * PUB, pourcentage de réduction pour SOLDE et DESTOCKAGE, ce dernier
 * garantissant en outre un prix plancher d'un euro.
 */
public enum TypePromo {
    PUB("Remise publicitaire"), SOLDE("Remise pour solde"),
    DESTOCKAGE("Remise pour déstockage");

    private String typePromo;

    /**
     * Crée un type de promotion.
     *
     * @param typePromo le libellé affiché de la promotion
     */
    private TypePromo(String typePromo) {
        this.typePromo = typePromo;
    }

    /**
     * Renvoie le libellé de la promotion.
     *
     * @return le libellé de la promotion
     */
    public String getTypePromo() {
        return typePromo;
    }


}
