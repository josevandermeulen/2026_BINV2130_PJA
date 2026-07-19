package main;

import domaine.Acces;
import util.LecteurLogs;
import util.Util;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AnalyseLogs {

    private final LecteurLogs lecteur;

    public AnalyseLogs(LecteurLogs lecteur) {
        Util.checkObject(lecteur);
        this.lecteur = lecteur;
    }

    public Acces parserLigne(String ligne) {
        // TODO Question 4
        throw new UnsupportedOperationException("À implémenter");
    }

    public List<Acces> parserLignes(List<String> lignes) {
        // TODO Question 5
        throw new UnsupportedOperationException("À implémenter");
    }

    public List<Acces> chargerTousLesAcces(String dossier) throws IOException {
        // TODO Questions 6 et 7
        throw new UnsupportedOperationException("À implémenter");
    }

    public Set<String> ipUniques(List<Acces> acces) {
        // TODO Question 8
        throw new UnsupportedOperationException("À implémenter");
    }

    public Map<String, Long> nombreAccesParUtilisateur(List<Acces> acces) {
        // TODO Question 9
        throw new UnsupportedOperationException("À implémenter");
    }

    public void ecrireRapport(String chemin, List<Acces> acces) throws IOException {
        // TODO Question 10
        throw new UnsupportedOperationException("À implémenter");
    }

    public static boolean ipValide(String ip) {
        // TODO Question 11 (optionnelle)
        throw new UnsupportedOperationException("À implémenter");
    }

    public List<Acces> accesIpInvalide(List<Acces> acces) {
        // TODO Question 11 (optionnelle)
        throw new UnsupportedOperationException("À implémenter");
    }

    public Map<String, Set<String>> utilisateursParIp(List<Acces> acces) {
        // TODO Question 12 (optionnelle)
        throw new UnsupportedOperationException("À implémenter");
    }

}
