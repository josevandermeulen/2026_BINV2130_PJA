package util;

import java.io.IOException;
import java.util.List;

/**
 * La lecture des fichiers de commandes, isolée derrière une interface.
 *
 * C'est cette abstraction qui rend l'analyse testable : un stub ou un mock peut renvoyer des
 * lignes en dur, sans toucher au disque.
 */
public interface LecteurCommandes {

    /**
     * Lit le fichier d'index et renvoie les noms des fichiers de commandes qu'il contient,
     * dans l'ordre du fichier.
     *
     * @param chemin le chemin du fichier d'index
     * @return la liste des noms de fichiers de commandes
     * @throws IOException si le fichier d'index ne peut pas être lu
     */
    List<String> lireIndex(String chemin) throws IOException;

    /**
     * Lit un fichier de commandes et renvoie toutes ses lignes, dans l'ordre du fichier.
     *
     * @param chemin le chemin du fichier de commandes
     * @return la liste des lignes du fichier
     * @throws IOException si le fichier ne peut pas être lu
     */
    List<String> lireLignes(String chemin) throws IOException;

}
