package main;

import domaine.Acces;
import domaine.Hasher;
import domaine.HyperLogLog;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class AnalyseIp {

    public static long compterIpUniques(List<Acces> acces) {
        return acces.stream()
                .map(Acces::getIp)
                .distinct()
                .count();
    }

    public static long estimerIpUniques(List<Acces> acces, HyperLogLog hyperLogLog) {
        acces.stream()
                .map(Acces::getIp)
                .forEach(hyperLogLog::ajouter);
        return hyperLogLog.estimerCardinalite();
    }

    public static Map<String, Long> estimerIpUniquesParJour(List<Acces> acces, Hasher hasher) {
        return acces.stream()
                .collect(Collectors.groupingBy(
                        a -> a.getHorodatage().substring(0, 10) + ".log",
                        TreeMap::new,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                accesDuJour -> estimerIpUniques(accesDuJour, new HyperLogLog(hasher, 4)))
                ));
    }

}
