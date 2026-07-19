package domaine;

public class DefaultHasher implements Hasher {

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
