package main;

import domaine.Acces;
import domaine.Hasher;
import domaine.HyperLogLog;
import util.Util;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AnalyseIpAsync {

    private final AnalyseLogs analyse;

    public AnalyseIpAsync(AnalyseLogs analyse) {
        Util.checkObject(analyse);
        this.analyse = analyse;
    }

    public CompletableFuture<Long> estimerIpUniquesAsync(List<Acces> acces, HyperLogLog hyperLogLog) {
        // TODO Question 1 : lancer AnalyseIp.estimerIpUniques dans un traitement asynchrone
        //  (CompletableFuture.supplyAsync)
        throw new UnsupportedOperationException("À implémenter");
    }

    public CompletableFuture<List<Acces>> chargerAccesAsync(String dossier) {
        // TODO Question 2 : charger les accès (analyse.chargerTousLesAcces) dans un traitement
        //  asynchrone. La lambda ne peut pas laisser passer l'IOException : l'attraper et
        //  relancer une RuntimeException.
        throw new UnsupportedOperationException("À implémenter");
    }

    public CompletableFuture<Long> estimerDepuisDossierAsync(String dossier, HyperLogLog hyperLogLog) {
        // TODO Question 3 : chaîner le chargement asynchrone (Question 2) et l'estimation
        //  (thenApply), sans jamais bloquer (pas de join ici)
        throw new UnsupportedOperationException("À implémenter");
    }

    public CompletableFuture<Long> estimerDepuisDossierAsyncAvecRepli(String dossier, HyperLogLog hyperLogLog,
                                                                      long valeurRepli) {
        // TODO Question 5 : même chose que la Question 3, mais si la chaîne se
        //  termine en erreur (dossier inexistant...), renvoyer valeurRepli (exceptionally)
        throw new UnsupportedOperationException("À implémenter");
    }

    public CompletableFuture<Long> estimerDeuxDossiersAsync(String dossier1, String dossier2,
                                                              HyperLogLog hyperLogLog) {
        // TODO Question 6 : charger les deux dossiers en parallèle (chargerAccesAsync x2),
        //  combiner les deux listes d'accès avec thenCombine, puis estimer sur la liste combinée
        throw new UnsupportedOperationException("À implémenter");
    }

    public CompletableFuture<List<Long>> estimerPlusieursDossiersAsync(List<String> dossiers, Hasher hasher) {
        // TODO Question 7 : lancer estimerDepuisDossierAsync pour chaque dossier (avec sa propre
        //  instance HyperLogLog), attendre toutes les futures avec CompletableFuture.allOf, puis
        //  renvoyer la liste des estimations dans le même ordre que la liste reçue
        throw new UnsupportedOperationException("À implémenter");
    }

    public List<Long> estimerPlusieursDossiersSequentiellement(List<String> dossiers, Hasher hasher) {
        // TODO Question 8 : même résultat que la Question 7, mais un dossier à la fois
        //  (estimerDepuisDossierAsync(...).join() dans une boucle) : aucun parallélisme
        throw new UnsupportedOperationException("À implémenter");
    }

}
