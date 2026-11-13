package domaine;

/**
 * La catégorie d'un ingrédient, et par extension celle d'une pizza.
 *
 * Une pizza est de la catégorie VIANDE dès qu'un de ses ingrédients en est, sinon POISSON dès
 * qu'un de ses ingrédients en est, et VEGETARIENNE dans tous les autres cas (voir
 * Pizza.getCategorie).
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
     * Renvoie le libellé de la catégorie, tel qu'il s'affiche sur la carte.
     *
     * @return le libellé de la catégorie
     */
    public String getLibelle() {
        return libelle;
    }

}
