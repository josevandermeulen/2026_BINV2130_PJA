package main;

import domaine.Hasher;
import domaine.HyperLogLog;
import util.Util;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

/**
 * L'estimation des IP distinctes vues par un parc de routeurs, chacun avec son fichier de logs.
 * <p>
 * Chaque routeur est estimé séparément, puis les estimateurs sont fusionnés : aucune liste d'IP
 * n'est jamais rassemblée en mémoire. La variante asynchrone traite les routeurs de front.
 */
public class AnalyseRouteurs {

    private static final int NB_BITS_INDEX = 4;

    private final AnalyseLogs analyse;

    private final Hasher hasher;

    /**
     * Crée l'analyse d'un parc de routeurs.
     *
     * @param analyse l'analyse qui charge et convertit les fichiers de logs
     * @param hasher  la fonction d'empreinte confiée aux estimateurs
     * @throws IllegalArgumentException si l'un des paramètres est null
     */
    public AnalyseRouteurs(AnalyseLogs analyse, Hasher hasher) {
        Util.checkObject(analyse);
        Util.checkObject(hasher);
        this.analyse = analyse;
        this.hasher = hasher;
    }

    /**
     * Construit l'estimateur des IP vues par un routeur.
     *
     * @param dossier     le dossier contenant les fichiers de logs
     * @param nomFichier  le fichier de logs du routeur
     * @return l'estimateur alimenté par les IP de ce routeur
     * @throws IOException si le fichier ne peut pas être lu
     */
    public HyperLogLog hllPourFichier(String dossier, String nomFichier) throws IOException {
        // TODO Question 2 : construire un HyperLogLog local (hasher, NB_BITS_INDEX) et y ajouter
        //  l'adresse IP de chaque accès du fichier (analyse.chargerAcces)
        throw new UnsupportedOperationException("À implémenter");
    }

    /**
     * Estime les IP distinctes de tout le parc, routeur après routeur.
     *
     * @param dossier le dossier contenant l'index et les fichiers de logs
     * @return l'estimateur fusionné de tous les routeurs
     * @throws IOException si l'index ou un fichier ne peut pas être lu
     */
    public HyperLogLog fusionnerTousLesRouteurs(String dossier) throws IOException {
        // TODO Question 3 : construire le HyperLogLog de chaque fichier de l'index
        //  (analyse.listerFichiers) et les fusionner un à un dans un HyperLogLog résultat
        throw new UnsupportedOperationException("À implémenter");
    }

    /**
     * Construit en tâche de fond l'estimateur des IP vues par un routeur.
     *
     * @param dossier    le dossier contenant les fichiers de logs
     * @param nomFichier le fichier de logs du routeur
     * @return une future portant l'estimateur de ce routeur
     */
    public CompletableFuture<HyperLogLog> hllPourFichierAsync(String dossier, String nomFichier) {
        // TODO Question 4 : lancer hllPourFichier dans un traitement asynchrone
        //  (CompletableFuture.supplyAsync) ; attraper l'IOException et relancer une RuntimeException
        throw new UnsupportedOperationException("À implémenter");
    }

    /**
     * Estime les IP distinctes de tout le parc, tous les routeurs étant traités de front.
     *
     * @param dossier le dossier contenant l'index et les fichiers de logs
     * @return une future portant l'estimateur fusionné de tous les routeurs
     * @throws IOException si l'index ne peut pas être lu
     */
    public CompletableFuture<HyperLogLog> fusionnerTousLesRouteursAsync(String dossier) throws IOException {
        // TODO Question 5 : lancer un CompletableFuture par fichier routeur (Question 4), puis
        //  combiner toutes les futures (thenCombine) pour fusionner les HyperLogLog locaux
        //  dans un seul résultat — sans jamais bloquer (pas de join ici)
        throw new UnsupportedOperationException("À implémenter");
    }

}
