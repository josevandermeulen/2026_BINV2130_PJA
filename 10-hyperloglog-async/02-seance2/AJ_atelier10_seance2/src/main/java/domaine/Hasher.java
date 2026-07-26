package domaine;

/**
 * Le calcul d'empreinte dont HyperLogLog a besoin, isolé derrière une interface.
 *
 * L'estimation ne dépend que de la qualité de ce hachage : le remplacer par une implémentation
 * prévisible rend le comportement de HyperLogLog reproductible dans un test.
 */
public interface Hasher {

    /**
     * Calcule une empreinte (hash) pour la valeur donnée.
     *
     * @param valeur la valeur à hacher
     * @return un entier représentant le hash de la valeur
     */
    int hash(String valeur);

}
