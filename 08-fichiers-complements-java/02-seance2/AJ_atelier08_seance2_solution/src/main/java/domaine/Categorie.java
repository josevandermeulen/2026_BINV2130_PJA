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

    // Question 2 : un deuxième attribut
    VEGETARIENNE("végétarienne", "V"),

    VIANDE("viande", "VI"),

    POISSON("poisson", "P");

    private final String libelle;

    private final String abreviation;

    Categorie(String libelle, String abreviation) {
        this.libelle = libelle;
        this.abreviation = abreviation;
    }

    /**
     * Renvoie l'abréviation de la catégorie, imprimée à côté du titre sur la carte.
     *
     * @return l'abréviation de la catégorie
     */
    public String getAbreviation() {
        return abreviation;
    }

    // FinQuestion

    /**
     * Renvoie le libellé de la catégorie, tel qu'il s'affiche sur la carte.
     *
     * @return le libellé de la catégorie
     */
    public String getLibelle() {
        return libelle;
    }

    // Question 1
    /**
     * Indique si la catégorie est végétarienne.
     *
     * @return true pour VEGETARIENNE, false pour les autres catégories
     */
    public boolean estVegetarienne() {
        return this == VEGETARIENNE;
    }

}
