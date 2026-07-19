package main;

import domaine.Acces;
import util.LecteurLogs;
import util.Util;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AnalyseLogs {

    private final LecteurLogs lecteur;

    public AnalyseLogs(LecteurLogs lecteur) {
        Util.checkObject(lecteur);
        this.lecteur = lecteur;
    }

    public Acces parserLigne(String ligne) {
        Util.checkString(ligne);
        String[] champs = ligne.split(";");
        if (champs.length != 3) {
            throw new IllegalArgumentException("Nombre de champs incorrect : " + ligne);
        }
        return new Acces(champs[0], champs[1], champs[2]);
    }

    public List<Acces> parserLignes(List<String> lignes) {
        List<Acces> acces = new ArrayList<>();
        for (String ligne : lignes) {
            try {
                acces.add(parserLigne(ligne));
            } catch (IllegalArgumentException e) {
                System.out.println("Ligne ignorée : " + ligne);
            }
        }
        return acces;
    }

    public List<Acces> chargerTousLesAcces(String dossier) throws IOException {
        List<Acces> acces = new ArrayList<>();
        for (String nomFichier : lecteur.lireIndex(dossier + "/index.txt")) {
            try {
                acces.addAll(parserLignes(lecteur.lireLignes(dossier + "/" + nomFichier)));
            } catch (FileNotFoundException e) {
                System.out.println("Fichier introuvable : " + nomFichier);
            }
        }
        return acces;
    }

    public Set<String> ipUniques(List<Acces> acces) {
        return acces.stream()
                .map(Acces::getIp)
                .collect(Collectors.toSet());
    }

    public Map<String, Long> nombreAccesParUtilisateur(List<Acces> acces) {
        return acces.stream()
                .collect(Collectors.groupingBy(Acces::getUtilisateur, Collectors.counting()));
    }

    public void ecrireRapport(String chemin, List<Acces> acces) throws IOException {
        List<String> ips = ipUniques(acces).stream().sorted().toList();
        try (BufferedWriter redacteur = new BufferedWriter(new FileWriter(chemin))) {
            for (String ip : ips) {
                redacteur.write(ip);
                redacteur.newLine();
            }
            redacteur.write("Nombre d'adresses IP uniques : " + ips.size());
            redacteur.newLine();
        }
    }

    public static boolean ipValide(String ip) {
        String[] octets = ip.split("\\.");
        if (octets.length != 4) {
            return false;
        }
        for (String octet : octets) {
            try {
                int valeur = Integer.parseInt(octet);
                if (valeur < 0 || valeur > 255) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }

    public List<Acces> accesIpInvalide(List<Acces> acces) {
        return acces.stream()
                .filter(a -> !ipValide(a.getIp()))
                .toList();
    }

    public Map<String, Set<String>> utilisateursParIp(List<Acces> acces) {
        return acces.stream()
                .collect(Collectors.groupingBy(Acces::getIp,
                        Collectors.mapping(Acces::getUtilisateur, Collectors.toSet())));
    }

}
