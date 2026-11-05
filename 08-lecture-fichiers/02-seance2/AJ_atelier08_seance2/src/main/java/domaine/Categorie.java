package domaine;

/**
 * La catégorie d'un ingrédient, et par extension celle d'une pizza.
 *
 * Une pizza est de la catégorie VIANDE dès qu'un de ses ingrédients en est, sinon POISSON dès
 * qu'un de ses ingrédients en est, et VEGETARIENNE dans tous les autres cas (voir
 * Pizza.getCategorie).
 *
 * Un énuméré est une classe : chaque constante en est une instance, créée une fois pour toutes
 * par le constructeur, avec ses propres valeurs d'attributs.
 */
public enum Categorie {

    VEGETARIENNE("végétarienne"),

    VIANDE("viande"),

    POISSON("poisson");

    private final String libelle;

    Categorie(String libelle) {
        this.libelle = libelle;
    }

    /**
     * Renvoie l'abréviation de la catégorie, imprimée à côté du titre sur la carte : V pour
     * VEGETARIENNE, VI pour VIANDE, P pour POISSON.
     *
     * @return l'abréviation de la catégorie
     */
    public String getAbreviation() {
        // TODO Question 2 : un deuxième attribut, initialisé par le constructeur de chaque constante
        throw new UnsupportedOperationException("À implémenter");
    }

    /**
     * Renvoie le libellé de la catégorie, tel qu'il s'affiche sur la carte.
     *
     * @return le libellé de la catégorie
     */
    public String getLibelle() {
        return libelle;
    }

    /**
     * Indique si la catégorie est végétarienne.
     *
     * @return true pour VEGETARIENNE, false pour les autres catégories
     */
    public boolean estVegetarienne() {
        // TODO Question 1
        throw new UnsupportedOperationException("À implémenter");
    }

}
