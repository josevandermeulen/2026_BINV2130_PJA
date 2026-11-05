package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * La lecture réelle des fichiers de commandes, sur le disque.
 *
 * Chaque méthode ouvre le fichier dans un try-with-resources : le flux est refermé même si la
 * lecture échoue en cours de route.
 */
public class LecteurFichiers implements LecteurCommandes {

    // (séance 1, Question 1)
    /**
     * Lit le fichier d'index et renvoie les noms des fichiers de commandes qu'il contient.
     *
     * @param chemin le chemin du fichier d'index
     * @return la liste des noms de fichiers, dans l'ordre du fichier
     * @throws IOException si le fichier d'index ne peut pas être lu
     */
    @Override
    public List<String> lireIndex(String chemin) throws IOException {
        List<String> noms = new ArrayList<>();
        try (BufferedReader lecteur = new BufferedReader(new FileReader(chemin))) {
            String ligne = lecteur.readLine();
            while (ligne != null) {
                noms.add(ligne);
                ligne = lecteur.readLine();
            }
        }
        return noms;
    }

    // (séance 1, Question 2)
    /**
     * Lit un fichier de commandes et renvoie toutes ses lignes.
     *
     * @param chemin le chemin du fichier de commandes
     * @return la liste des lignes, dans l'ordre du fichier
     * @throws IOException si le fichier ne peut pas être lu
     */
    @Override
    public List<String> lireLignes(String chemin) throws IOException {
        try (BufferedReader lecteur = new BufferedReader(new FileReader(chemin))) {
            return lecteur.lines().collect(Collectors.toList());
        }
    }

}
