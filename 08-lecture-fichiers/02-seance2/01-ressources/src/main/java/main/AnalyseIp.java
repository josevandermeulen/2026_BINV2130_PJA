package main;

import domaine.Acces;
import domaine.Hasher;
import domaine.HyperLogLog;

import java.util.List;
import java.util.Map;

public class AnalyseIp {

    public static long compterIpUniques(List<Acces> acces) {
        // TODO Question 2
        throw new UnsupportedOperationException("À implémenter");
    }

    public static long estimerIpUniques(List<Acces> acces, HyperLogLog hyperLogLog) {
        // TODO Question 3
        throw new UnsupportedOperationException("À implémenter");
    }

    public static Map<String, Long> estimerIpUniquesParJour(List<Acces> acces, Hasher hasher) {
        // TODO Question 4 : regrouper les accès par jour (Collectors.groupingBy, TreeMap::new
        //  pour un ordre chronologique), en calculant l'estimation de chaque groupe avec
        //  Collectors.collectingAndThen(Collectors.toList(), ...estimerIpUniques...).
        //  La clé de groupe est a.getHorodatage().substring(0, 10) + ".log".
        throw new UnsupportedOperationException("À implémenter");
    }

}
