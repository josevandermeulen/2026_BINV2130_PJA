package domaine;

/**
 * Les unités de mesure utilisables pour quantifier un ingrédient.
 *
 * Chaque unité connaît son abréviation, seule forme affichée dans une recette. NEANT est
 * l'unité vide, pour les ingrédients qui se comptent sans unité (« 3 œufs »).
 */
public enum Unite {
    GRAMME("gr"), KILOGRAMME("kg"), LITRE("l"), MILLILITRE("ml"),
    CENTILITRE("cl"), DECILITRE("dl"), CUILLER_A_CAFE("cc"), CUILLER_A_THE("ct"),
    CUILLER_A_DESSERT("cd"), CUILLER_A_SOUPE("cs"), PINCEE("pincée"), UN_PEU("peu"),
    NEANT("");

    private String abreviation;

    /**
     * Crée une unité.
     *
     * @param abreviation l'abréviation affichée dans les recettes
     */
    Unite(String abreviation) {
        this.abreviation = abreviation;
    }

    /**
     * Renvoie l'abréviation de l'unité.
     *
     * @return l'abréviation de l'unité
     */
    @Override
    public String toString() {
        return abreviation;
    }
}
