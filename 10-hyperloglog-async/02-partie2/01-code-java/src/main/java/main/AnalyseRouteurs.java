package main;

import domaine.Hasher;
import domaine.HyperLogLog;
import util.Util;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class AnalyseRouteurs {

    private static final int NB_BITS_INDEX = 4;

    private final AnalyseLogs analyse;

    private final Hasher hasher;

    public AnalyseRouteurs(AnalyseLogs analyse, Hasher hasher) {
        Util.checkObject(analyse);
        Util.checkObject(hasher);
        this.analyse = analyse;
        this.hasher = hasher;
    }

    public HyperLogLog hllPourFichier(String dossier, String nomFichier) throws IOException {
        // TODO Question 2 : construire un HyperLogLog local (hasher, NB_BITS_INDEX) et y ajouter
        //  l'adresse IP de chaque accès du fichier (analyse.chargerAcces)
        throw new UnsupportedOperationException("À implémenter");
    }

    public HyperLogLog fusionnerTousLesRouteurs(String dossier) throws IOException {
        // TODO Question 3 : construire le HyperLogLog de chaque fichier de l'index
        //  (analyse.listerFichiers) et les fusionner un à un dans un HyperLogLog résultat
        throw new UnsupportedOperationException("À implémenter");
    }

    public CompletableFuture<HyperLogLog> hllPourFichierAsync(String dossier, String nomFichier) {
        // TODO Question 4 : lancer hllPourFichier dans un traitement asynchrone
        //  (CompletableFuture.supplyAsync) ; attraper l'IOException et relancer une RuntimeException
        throw new UnsupportedOperationException("À implémenter");
    }

    public CompletableFuture<HyperLogLog> fusionnerTousLesRouteursAsync(String dossier) throws IOException {
        // TODO Question 5 : lancer un CompletableFuture par fichier routeur (Question 4), puis
        //  combiner toutes les futures (thenCombine) pour fusionner les HyperLogLog locaux
        //  dans un seul résultat — sans jamais bloquer (pas de join ici)
        throw new UnsupportedOperationException("À implémenter");
    }

}
