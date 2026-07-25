package main;

import domaine.Acces;
import domaine.DefaultHasher;
import domaine.HyperLogLog;
import util.LogReader;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {
        AnalyseLogs analyse = new AnalyseLogs(new LogReader());
        List<Acces> acces = analyse.chargerTousLesAcces("logs");

        HyperLogLog hyperLogLog = new HyperLogLog(new DefaultHasher(), 4);

        System.out.println();
        System.out.println("Nombre d'accès valides : " + acces.size());
        System.out.println("Nombre réel d'adresses IP uniques : " + AnalyseIp.compterIpUniques(acces));
        System.out.println("Estimation HyperLogLog : " + AnalyseIp.estimerIpUniques(acces, hyperLogLog));

        System.out.println();
        System.out.println("Estimation par jour :");
        Map<String, Long> estimationsParJour = AnalyseIp.estimerIpUniquesParJour(acces, new DefaultHasher());
        estimationsParJour.forEach((jour, estimation) ->
                System.out.println(jour + " : " + estimation));
    }

}
