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

/**
 * L'analyse des fichiers de logs : lecture, conversion en {@link Acces} et statistiques.
 * <p>
 * La lecture proprement dite est déléguée au {@link LecteurLogs} reçu à la construction, ce qui
 * permet de tester l'analyse sans toucher au disque. Les lignes malformées et les fichiers
 * introuvables sont signalés puis ignorés : un log abîmé ne doit pas interrompre l'analyse.
 */
public class AnalyseLogs {

    private final LecteurLogs lecteur;

    /**
     * Crée une analyse s'appuyant sur le lecteur donné.
     *
     * @param lecteur le lecteur qui fournit les lignes de logs
     * @throws IllegalArgumentException si le lecteur est null
     */
    public AnalyseLogs(LecteurLogs lecteur) {
        Util.checkObject(lecteur);
        this.lecteur = lecteur;
    }

    /**
     * Convertit une ligne de log en accès.
     *
     * @param ligne la ligne à convertir, aux trois champs séparés par des points-virgules
     * @return l'accès correspondant
     * @throws IllegalArgumentException si la ligne est null, vide, ou ne compte pas trois champs
     */
    public Acces parserLigne(String ligne) {
        Util.checkString(ligne);
        String[] champs = ligne.split(";");
        if (champs.length != 3) {
            throw new IllegalArgumentException("Nombre de champs incorrect : " + ligne);
        }
        return new Acces(champs[0], champs[1], champs[2]);
    }

    /**
     * Convertit un ensemble de lignes en accès, en ignorant celles qui sont malformées.
     * <p>
     * Une ligne rejetée est signalée sur la sortie standard, mais n'interrompt pas la conversion
     * des suivantes.
     *
     * @param lignes les lignes à convertir
     * @return les accès obtenus, sans les lignes rejetées
     */
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

    /**
     * Charge tous les accès des fichiers de logs référencés par l'index du dossier.
     * <p>
     * Un fichier annoncé par l'index mais introuvable est signalé puis ignoré ; en revanche, un
     * index illisible interrompt le chargement.
     *
     * @param dossier le dossier contenant l'index et les fichiers de logs
     * @return les accès de tous les fichiers lisibles
     * @throws IOException si le fichier d'index ne peut pas être lu
     */
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

    /**
     * Renvoie les adresses IP distinctes apparaissant dans les accès.
     *
     * @param acces les accès à examiner
     * @return l'ensemble des adresses IP rencontrées
     */
    public Set<String> ipUniques(List<Acces> acces) {
        return acces.stream()
                .map(Acces::getIp)
                .collect(Collectors.toSet());
    }

    /**
     * Compte les accès de chaque utilisateur.
     *
     * @param acces les accès à examiner
     * @return une table associant chaque utilisateur à son nombre d'accès
     */
    public Map<String, Long> nombreAccesParUtilisateur(List<Acces> acces) {
        return acces.stream()
                .collect(Collectors.groupingBy(Acces::getUtilisateur, Collectors.counting()));
    }

    /**
     * Écrit dans un fichier les adresses IP distinctes des accès, triées par ordre alphabétique.
     *
     * @param chemin le chemin du fichier à écrire
     * @param acces  les accès à résumer
     * @throws IOException si le fichier ne peut pas être écrit
     */
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

    /**
     * Vérifie qu'une adresse est une IPv4 bien formée : quatre octets de 0 à 255.
     *
     * @param ip l'adresse à vérifier
     * @return true si l'adresse est une IPv4 valide
     */
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

    /**
     * Renvoie les accès dont l'adresse IP est malformée.
     *
     * @param acces les accès à examiner
     * @return les accès portant une IP invalide
     */
    public List<Acces> accesIpInvalide(List<Acces> acces) {
        return acces.stream()
                .filter(a -> !ipValide(a.getIp()))
                .toList();
    }

    /**
     * Regroupe les utilisateurs par adresse IP.
     *
     * @param acces les accès à examiner
     * @return une table associant chaque IP aux utilisateurs qui s'y sont connectés
     */
    public Map<String, Set<String>> utilisateursParIp(List<Acces> acces) {
        return acces.stream()
                .collect(Collectors.groupingBy(Acces::getIp,
                        Collectors.mapping(Acces::getUtilisateur, Collectors.toSet())));
    }

}
