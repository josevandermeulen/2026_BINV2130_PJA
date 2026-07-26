package domaine;

/**
 * L'empreinte par défaut employée par HyperLogLog.
 *
 * Le calcul est fourni tout fait : il relève de l'algorithme, pas de l'exercice.
 */
public class DefaultHasher implements Hasher {

    /**
     * Calcule l'empreinte de la valeur donnée.
     *
     * @param valeur la valeur à hacher
     * @return l'empreinte de la valeur
     */
    @Override
    public int hash(String valeur) {
        int hash = valeur.hashCode();
        hash ^= (hash >>> 16);
        hash *= 0x85ebca6b;
        hash ^= (hash >>> 13);
        hash *= 0xc2b2ae35;
        hash ^= (hash >>> 16);
        return hash;
    }

}
