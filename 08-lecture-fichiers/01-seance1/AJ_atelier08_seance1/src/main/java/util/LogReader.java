package util;

import java.io.IOException;
import java.util.List;

/**
 * La lecture réelle des fichiers de logs, sur le disque.
 * <p>
 * Chaque méthode ouvre le fichier dans un try-with-resources : le flux est refermé même si la
 * lecture échoue en cours de route.
 */
public class LogReader implements LecteurLogs {

    /**
     * Lit le fichier d'index et renvoie les noms des fichiers de logs qu'il contient.
     *
     * @param chemin le chemin du fichier d'index
     * @return la liste des noms de fichiers, dans l'ordre du fichier
     * @throws IOException si le fichier d'index ne peut pas être lu
     */
    @Override
    public List<String> lireIndex(String chemin) throws IOException {
        // TODO Question 1
        throw new UnsupportedOperationException("À implémenter");
    }

    /**
     * Lit un fichier de logs et renvoie toutes ses lignes.
     *
     * @param chemin le chemin du fichier de logs
     * @return la liste des lignes, dans l'ordre du fichier
     * @throws IOException si le fichier ne peut pas être lu
     */
    @Override
    public List<String> lireLignes(String chemin) throws IOException {
        // TODO Question 2
        throw new UnsupportedOperationException("À implémenter");
    }

}
