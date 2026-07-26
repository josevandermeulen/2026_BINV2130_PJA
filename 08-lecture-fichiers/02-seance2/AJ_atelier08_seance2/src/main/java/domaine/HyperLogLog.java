package domaine;

import util.Util;

/**
 * Estime le nombre de valeurs distinctes vues, sans les mémoriser.
 *
 * Là où un `Set` garde chaque valeur, cette structure ne retient qu'un tableau de registres
 * de taille fixe : la mémoire consommée ne dépend pas du nombre de valeurs traitées, au prix d'un
 * résultat approché. La précision se règle au constructeur, par le nombre de bits d'index.
 */
public class HyperLogLog {

    private final Hasher hasher;
    private final int nbBitsIndex;
    private final double alpha;
    final int[] registres;

    /**
     * Crée un estimateur vide.
     *
     * @param hasher      la fonction d'empreinte à employer
     * @param nbBitsIndex le nombre de bits d'index, entre 4 et 16 : plus il est grand, plus
     *                    l'estimation est précise et plus la mémoire occupée croît
     * @throws IllegalArgumentException si le hasher est null ou si la précision sort de [4, 16]
     */
    public HyperLogLog(Hasher hasher, int nbBitsIndex) {
        Util.checkObject(hasher);
        if (nbBitsIndex < 4 || nbBitsIndex > 16) {
            throw new IllegalArgumentException(
                    "La précision doit être comprise entre 4 et 16 bits");
        }
        this.hasher = hasher;
        this.nbBitsIndex = nbBitsIndex;
        this.registres = new int[1 << nbBitsIndex];
        this.alpha = alphaPour(registres.length);
    }

    // constantes du papier original, selon le nombre de registres m
    private static double alphaPour(int nbRegistres) {
        return switch (nbRegistres) {
            case 16 -> 0.673;
            case 32 -> 0.697;
            case 64 -> 0.709;
            default -> 0.7213 / (1 + 1.079 / nbRegistres);
        };
    }

    /**
     * Prend en compte une valeur dans l'estimation.
     *
     * Ajouter deux fois la même valeur ne change rien : c'est ce qui permet de compter des
     * valeurs distinctes sans les stocker.
     *
     * @param valeur la valeur observée
     * @throws IllegalArgumentException si la valeur est null ou vide
     */
    public void ajouter(String valeur) {
        Util.checkString(valeur);
        int hash = hasher.hash(valeur);
        int index = hash >>> (Integer.SIZE - nbBitsIndex);
        int reste = hash << nbBitsIndex;
        int nombreZeros = Integer.numberOfLeadingZeros(reste) + 1;
        if (nombreZeros > registres[index]) {
            registres[index] = nombreZeros;
        }
    }

    /**
     * Estime le nombre de valeurs distinctes ajoutées jusqu'ici.
     *
     * @return le nombre estimé de valeurs distinctes
     */
    public long estimerCardinalite() {
        // TODO Question 1 : appliquer la formule alpha * m² / Σ(2 ^ -registre[i]),
        //  où m = registres.length et alpha est l'attribut initialisé par le constructeur,
        //  avec l'API Stream (IntStream.of(registres), mapToDouble, sum).
        throw new UnsupportedOperationException("À implémenter");
    }
}
