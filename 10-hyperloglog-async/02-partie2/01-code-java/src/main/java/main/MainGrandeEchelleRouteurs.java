package main;

import domaine.DefaultHasher;
import util.LogReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MainGrandeEchelleRouteurs {

    private static final String DOSSIER = "logs-routeurs";
    private static final int NB_ROUTEURS = 20;
    private static final int NB_LIGNES_PAR_ROUTEUR = 50_000;

    public static void main(String[] args) throws IOException {
        if (!Files.exists(Path.of(DOSSIER))) {
            System.out.println("Génération de " + NB_ROUTEURS + " fichiers routeurs de " + NB_LIGNES_PAR_ROUTEUR
                    + " accès dans " + DOSSIER + "... (une seule fois)");
            GenerateurLogs.genererParRouteur(DOSSIER, NB_ROUTEURS, NB_LIGNES_PAR_ROUTEUR);
        }

        AnalyseRouteurs analyseRouteurs =
                new AnalyseRouteurs(new AnalyseLogs(new LogReader()), new DefaultHasher());

        long debutSequentiel = System.currentTimeMillis();
        long estimationSequentielle = analyseRouteurs.fusionnerTousLesRouteurs(DOSSIER).estimerCardinalite();
        long dureeSequentielle = System.currentTimeMillis() - debutSequentiel;

        long debutAsync = System.currentTimeMillis();
        long estimationAsync = analyseRouteurs.fusionnerTousLesRouteursAsync(DOSSIER).join().estimerCardinalite();
        long dureeAsync = System.currentTimeMillis() - debutAsync;

        System.out.println();
        System.out.println("Estimation séquentielle (un routeur après l'autre) : "
                + estimationSequentielle + " en " + dureeSequentielle + " ms");
        System.out.println("Estimation asynchrone (un CompletableFuture par routeur) : "
                + estimationAsync + " en " + dureeAsync + " ms");
        System.out.println();
        System.out.println("Les deux estimations sont identiques : la fusion (max registre par registre)");
        System.out.println("est commutative et associative, l'ordre des traitements n'a donc aucune importance.");
    }

}
