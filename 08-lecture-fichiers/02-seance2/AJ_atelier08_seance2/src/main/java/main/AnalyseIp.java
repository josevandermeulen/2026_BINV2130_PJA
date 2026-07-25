package main;

import domaine.Acces;
import domaine.Hasher;
import domaine.HyperLogLog;

import java.util.List;
import java.util.Map;

/**
 * Comparaison des deux façons de compter des adresses IP distinctes : exactement, ou par
 * estimation.
 * <p>
 * Le comptage exact mémorise chaque adresse rencontrée ; l'estimation par HyperLogLog n'en retient
 * aucune. C'est l'écart de mémoire entre les deux qui fait l'intérêt de la séance, le résultat
 * approché étant le prix à payer.
 */
public class AnalyseIp {

    /**
     * Compte exactement les adresses IP distinctes, en les mémorisant toutes.
     *
     * @param acces les accès à examiner
     * @return le nombre exact d'adresses IP distinctes
     */
    public static long compterIpUniques(List<Acces> acces) {
        // TODO Question 2
        throw new UnsupportedOperationException("À implémenter");
    }

    /**
     * Estime le nombre d'adresses IP distinctes sans les mémoriser.
     *
     * @param acces        les accès à examiner
     * @param hyperLogLog  l'estimateur à alimenter
     * @return le nombre estimé d'adresses IP distinctes
     */
    public static long estimerIpUniques(List<Acces> acces, HyperLogLog hyperLogLog) {
        // TODO Question 3
        throw new UnsupportedOperationException("À implémenter");
    }

    /**
     * Estime, jour par jour, le nombre d'adresses IP distinctes.
     *
     * @param acces  les accès à examiner
     * @param hasher la fonction d'empreinte confiée à chaque estimateur
     * @return une table associant chaque jour au nombre estimé d'IP distinctes
     */
    public static Map<String, Long> estimerIpUniquesParJour(List<Acces> acces, Hasher hasher) {
        // TODO Question 4 : regrouper les accès par jour (Collectors.groupingBy, TreeMap::new
        //  pour un ordre chronologique), en calculant l'estimation de chaque groupe avec
        //  Collectors.collectingAndThen(Collectors.toList(), ...estimerIpUniques...).
        //  La clé de groupe est a.getHorodatage().substring(0, 10) + ".log".
        throw new UnsupportedOperationException("À implémenter");
    }

}
