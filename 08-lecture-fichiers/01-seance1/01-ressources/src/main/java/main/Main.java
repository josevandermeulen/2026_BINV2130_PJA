package main;

import domaine.Acces;
import util.LogReader;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {
        AnalyseLogs analyse = new AnalyseLogs(new LogReader());
        List<Acces> acces = analyse.chargerTousLesAcces("logs");

        System.out.println();
        System.out.println("Nombre d'accès valides : " + acces.size());

        System.out.println();
        System.out.println("Adresses IP uniques :");
        analyse.ipUniques(acces).stream()
                .sorted()
                .forEach(ip -> System.out.println("  " + ip));

        System.out.println();
        System.out.println("Nombre d'accès par utilisateur :");
        analyse.nombreAccesParUtilisateur(acces).entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entree -> System.out.println("  " + entree.getKey() + " : " + entree.getValue()));

        analyse.ecrireRapport("rapport.txt", acces);
        System.out.println();
        System.out.println("Rapport écrit dans rapport.txt");
    }

}
