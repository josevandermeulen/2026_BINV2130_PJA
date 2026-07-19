package domaine;

import util.Util;

import java.util.stream.IntStream;

public class HyperLogLog {

    private final Hasher hasher;
    private final int nbBitsIndex;
    private final double alpha;
    final int[] registres;

    public HyperLogLog(Hasher hasher, int nbBitsIndex) {
        Util.checkObject(hasher);
        if (nbBitsIndex < 4 || nbBitsIndex > 16)
            throw new IllegalArgumentException(
                    "La précision doit être comprise entre 4 et 16 bits");
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

    public long estimerCardinalite() {
        double sommeInverses = IntStream.of(registres)
                .mapToDouble(registre -> Math.pow(2, -registre))
                .sum();
        return Math.round(alpha * registres.length * registres.length / sommeInverses);
    }

    public void fusionner(HyperLogLog autre) {
        // TODO Question 1 : valider autre (Util.checkObject, même nombre de registres sinon
        //  IllegalArgumentException), puis garder dans chaque registre le max des deux estimateurs
        throw new UnsupportedOperationException("À implémenter");
    }

}
