package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class GenerateurLogs {

    private static final DateTimeFormatter FORMAT_HORODATAGE = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    private static final int NB_UTILISATEURS = 50;
    private static final long GRAINE = 42;

    public static void genererGrandVolume(String dossier, int nbFichiers, int nbLignesParFichier) throws IOException {
        Path racine = Path.of(dossier);
        Files.createDirectories(racine);
        Random random = new Random(GRAINE);

        try (BufferedWriter index = new BufferedWriter(new FileWriter(racine.resolve("index.txt").toFile()))) {
            LocalDateTime jour = LocalDateTime.of(2026, 1, 1, 0, 0, 0);
            for (int numeroFichier = 0; numeroFichier < nbFichiers; numeroFichier++) {
                String nomFichier = jour.toLocalDate() + ".log";
                index.write(nomFichier);
                index.newLine();
                genererFichier(racine.resolve(nomFichier), jour, nbLignesParFichier, random);
                jour = jour.plusDays(1);
            }
        }
    }

    private static void genererFichier(Path chemin, LocalDateTime debutJournee, int nbLignes, Random random) throws IOException {
        try (BufferedWriter fichier = new BufferedWriter(new FileWriter(chemin.toFile()))) {
            LocalDateTime horodatage = debutJournee;
            for (int ligne = 0; ligne < nbLignes; ligne++) {
                horodatage = horodatage.plusSeconds(1 + random.nextInt(5));
                String utilisateur = "user" + random.nextInt(NB_UTILISATEURS);
                String ip = random.nextInt(256) + "." + random.nextInt(256) + "." + random.nextInt(256) + "." + random.nextInt(256);
                fichier.write(horodatage.format(FORMAT_HORODATAGE) + ";" + utilisateur + ";" + ip);
                fichier.newLine();
            }
        }
    }

}
