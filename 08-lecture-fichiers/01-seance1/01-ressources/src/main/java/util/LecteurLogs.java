package util;

import java.io.IOException;
import java.util.List;

public interface LecteurLogs {

    /**
     * Lit le fichier d'index et renvoie les noms des fichiers de logs qu'il contient,
     * dans l'ordre du fichier.
     *
     * @param chemin le chemin du fichier d'index
     * @return la liste des noms de fichiers de logs
     * @throws IOException si le fichier d'index ne peut pas être lu
     */
    List<String> lireIndex(String chemin) throws IOException;

    /**
     * Lit un fichier de logs et renvoie toutes ses lignes, dans l'ordre du fichier.
     *
     * @param chemin le chemin du fichier de logs
     * @return la liste des lignes du fichier
     * @throws IOException si le fichier ne peut pas être lu
     */
    List<String> lireLignes(String chemin) throws IOException;

}
