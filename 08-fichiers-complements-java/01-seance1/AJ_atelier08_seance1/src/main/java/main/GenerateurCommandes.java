package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

/**
 * Fabrique des fichiers de commandes de grande taille, pour éprouver l'analyse sur un volume
 * réaliste. Les commandes citent les clients et les pizzas de DonneesPizzeria.
 */
public class GenerateurCommandes {

    private static final String[] TITRES = {"4 saisons", "4 fromages", "margarita", "du chef", "marinière",
            "diavola", "ortolana", "tonno"};

    private static final long GRAINE = 42;

    /**
     * Génère un jeu de fichiers de commandes, un par jour, et l'index qui les référence.
     *
     * @param dossier            le dossier où écrire les fichiers
     * @param nbFichiers         le nombre de fichiers de commandes à produire
     * @param nbLignesParFichier le nombre de commandes par fichier
     * @throws IOException si l'écriture échoue
     */
    public static void genererGrandVolume(String dossier, int nbFichiers, int nbLignesParFichier)
            throws IOException {
        Path racine = Path.of(dossier);
        Files.createDirectories(racine);
        Random random = new Random(GRAINE);
        String[] telephones = DonneesPizzeria.telephones();

        try (BufferedWriter index = new BufferedWriter(new FileWriter(racine.resolve("index.txt").toFile()))) {
            LocalDate jour = LocalDate.of(2026, 1, 1);
            for (int numeroFichier = 0; numeroFichier < nbFichiers; numeroFichier++) {
                String nomFichier = jour + ".csv";
                index.write(nomFichier);
                index.newLine();
                genererFichier(racine.resolve(nomFichier), jour, nbLignesParFichier, telephones, random);
                jour = jour.plusDays(1);
            }
        }
    }

    private static void genererFichier(Path chemin, LocalDate jour, int nbLignes, String[] telephones,
                                       Random random) throws IOException {
        try (BufferedWriter fichier = new BufferedWriter(new FileWriter(chemin.toFile()))) {
            LocalDateTime date = jour.atTime(11, 0);
            for (int ligne = 0; ligne < nbLignes; ligne++) {
                date = date.plusSeconds(1 + random.nextInt(3));
                String telephone = telephones[random.nextInt(telephones.length)];
                String titre = TITRES[random.nextInt(TITRES.length)];
                int quantite = 1 + random.nextInt(4);
                fichier.write(date + ";" + telephone + ";" + titre + ";" + quantite);
                fichier.newLine();
            }
        }
    }

}
