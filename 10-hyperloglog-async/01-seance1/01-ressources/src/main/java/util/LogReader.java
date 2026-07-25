package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LogReader implements LecteurLogs {

    @Override
    public List<String> lireIndex(String chemin) throws IOException {
        List<String> noms = new ArrayList<>();
        try (BufferedReader lecteur = new BufferedReader(new FileReader(chemin))) {
            String ligne;
            while ((ligne = lecteur.readLine()) != null) {
                noms.add(ligne);
            }
        }
        return noms;
    }

    @Override
    public List<String> lireLignes(String chemin) throws IOException {
        try (BufferedReader lecteur = new BufferedReader(new FileReader(chemin))) {
            return lecteur.lines().collect(Collectors.toList());
        }
    }

}
