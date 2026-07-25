package domaine;

public interface Hasher {

    /**
     * Calcule une empreinte (hash) pour la valeur donnée.
     *
     * @param valeur la valeur à hacher
     * @return un entier représentant le hash de la valeur
     */
    int hash(String valeur);

}
