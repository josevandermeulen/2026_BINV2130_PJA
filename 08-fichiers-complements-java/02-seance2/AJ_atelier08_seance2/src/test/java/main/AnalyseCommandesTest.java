package main;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import domaine.Categorie;
import domaine.Client;
import domaine.Commande;
import domaine.MenuPizzeria;
import domaine.Pizzeria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import util.LecteurCommandes;
import util.LecteurFichiers;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

class AnalyseCommandesTest {

    private static final String EMMELINE = "0488/98.23.85";

    private static final String STEPHANIE = "0475/51.30.80";

    private Pizzeria pizzeria;

    private LecteurCommandes lecteur;

    private AnalyseCommandes analyse;

    private Client emmeline;

    private Client stephanie;

    @BeforeEach
    void setUp() {
        Client.reinitialiserCompteur();
        Commande.reinitialiserCompteur();

        pizzeria = new Pizzeria(ingredient -> true);
        emmeline = new Client("Leconte", "Emmeline", EMMELINE);
        stephanie = new Client("Ferneeuw", "Stéphanie", STEPHANIE);
        pizzeria.ajouterClient(emmeline);
        pizzeria.ajouterClient(stephanie);
        pizzeria.ajouterPizza(MenuPizzeria.PIZZA_MARGARITA);
        pizzeria.ajouterPizza(MenuPizzeria.PIZZA_DIAVOLA);
        pizzeria.ajouterPizza(MenuPizzeria.PIZZA_MARINIERE);

        lecteur = mock(LecteurCommandes.class);
        analyse = new AnalyseCommandes(pizzeria, lecteur);
    }

    private void enregistrer(Client client, String date, int quantiteMargarita, int quantiteDiavola) {
        Commande commande = new Commande(client, LocalDateTime.parse(date));
        if (quantiteMargarita > 0) {
            commande.ajouter(MenuPizzeria.PIZZA_MARGARITA, quantiteMargarita);
        }
        if (quantiteDiavola > 0) {
            commande.ajouter(MenuPizzeria.PIZZA_DIAVOLA, quantiteDiavola);
        }
        client.cloturerCommandeEnCours();
        pizzeria.ajouterCommande(commande);
    }

    private void troisCommandesDeDemo() {
        enregistrer(emmeline, "2026-11-09T12:00:00", 2, 0);
        enregistrer(stephanie, "2026-11-09T19:30:00", 0, 1);
        enregistrer(emmeline, "2026-11-10T12:15:00", 1, 3);
    }

    // Les vrais fichiers du dossier commandes/, lus par le vrai LecteurFichiers : les mêmes données que Main.
    private AnalyseCommandes analyseDesVraisFichiers() throws IOException {
        AnalyseCommandes analyseReelle = new AnalyseCommandes(DonneesPizzeria.creerPizzeria(), new LecteurFichiers());
        analyseReelle.chargerToutesLesCommandes("commandes");
        return analyseReelle;
    }

    @Nested
    @DisplayName("Séance 1, question 3 : parserLigne")
    class Seance1Question3 {

        @Test
        void parserLigneConstruitUneCommandeCloturee() {
            Commande commande = analyse.parserLigne("2026-11-09T12:15:00;" + EMMELINE + ";margarita;2");

            assertAll(
                    () -> assertEquals(LocalDateTime.of(2026, 11, 9, 12, 15), commande.getDate()),
                    () -> assertSame(emmeline, commande.getClient()),
                    () -> assertEquals(1, commande.getLignes().size()),
                    () -> assertEquals(MenuPizzeria.PIZZA_MARGARITA, commande.getLignes().get(0).getPizza()),
                    () -> assertEquals(2, commande.getLignes().get(0).getQuantite()),
                    () -> assertNull(emmeline.getCommandeEnCours()),
                    () -> assertEquals(List.of(commande), emmeline.getCommandesPassees())
            );
        }

        @Test
        void parserLigneRefuseUnChampManquant() {
            assertThrows(IllegalArgumentException.class,
                    () -> analyse.parserLigne("2026-11-09T12:15:00;" + EMMELINE + ";margarita"));
        }

        @Test
        void parserLigneRefuseUnChampEnTrop() {
            assertThrows(IllegalArgumentException.class,
                    () -> analyse.parserLigne("2026-11-09T12:15:00;" + EMMELINE + ";margarita;2;midi"));
        }

        @Test
        void parserLigneRefuseUneDateMalFormee() {
            assertThrows(IllegalArgumentException.class,
                    () -> analyse.parserLigne("09/11/2026 12:15;" + EMMELINE + ";margarita;2"));
        }

        @Test
        void parserLigneRefuseUnClientInconnu() {
            assertThrows(IllegalArgumentException.class,
                    () -> analyse.parserLigne("2026-11-09T12:15:00;0499/00.00.00;margarita;2"));
        }

        @Test
        void parserLigneRefuseUnePizzaInconnue() {
            assertThrows(IllegalArgumentException.class,
                    () -> analyse.parserLigne("2026-11-09T12:15:00;" + EMMELINE + ";hawaïenne;2"));
        }

        @Test
        void parserLigneRefuseUneQuantiteNonNumerique() {
            assertThrows(IllegalArgumentException.class,
                    () -> analyse.parserLigne("2026-11-09T12:15:00;" + EMMELINE + ";margarita;deux"));
        }

        @Test
        void parserLigneRefuseUneQuantiteNulle() {
            assertThrows(IllegalArgumentException.class,
                    () -> analyse.parserLigne("2026-11-09T12:15:00;" + EMMELINE + ";margarita;0"));
        }

        @Test
        void uneLigneRefuseeNeBloquePasLeClient() {
            assertThrows(IllegalArgumentException.class,
                    () -> analyse.parserLigne("2026-11-09T12:15:00;" + EMMELINE + ";margarita;0"));

            assertAll(
                    () -> assertNull(emmeline.getCommandeEnCours()),
                    () -> assertDoesNotThrow(
                            () -> analyse.parserLigne("2026-11-09T12:20:00;" + EMMELINE + ";margarita;1"))
            );
        }
    }

    @Nested
    @DisplayName("Séance 1, question 4 : parserLignes ignore les lignes invalides")
    class Seance1Question4 {

        @Test
        void parserLignesIgnoreLesLignesInvalidesEtGardeLesValides() {
            List<String> lignes = List.of(
                    "2026-11-09T12:15:00;" + EMMELINE + ";margarita;2",
                    "2026-11-09T12:20:00;" + STEPHANIE + ";hawaïenne;1",
                    "2026-11-09T19:00:00;" + STEPHANIE + ";diavola;1"
            );

            List<Commande> commandes = analyse.parserLignes(lignes);

            assertAll(
                    () -> assertEquals(2, commandes.size()),
                    () -> assertSame(emmeline, commandes.get(0).getClient()),
                    () -> assertSame(stephanie, commandes.get(1).getClient())
            );
        }
    }

    @Nested
    @DisplayName("Séance 1, question 5 : chargerToutesLesCommandes")
    class Seance1Question5 {

        @Test
        void chargerToutesLesCommandesLitTousLesFichiersDeLIndex() throws IOException {
            when(lecteur.lireIndex("commandes/index.txt")).thenReturn(List.of("a.csv", "b.csv"));
            when(lecteur.lireLignes("commandes/a.csv")).thenReturn(List.of(
                    "2026-11-09T12:15:00;" + EMMELINE + ";margarita;2",
                    "2026-11-09T19:00:00;" + STEPHANIE + ";diavola;1"
            ));
            when(lecteur.lireLignes("commandes/b.csv")).thenReturn(List.of(
                    "2026-11-10T12:00:00;" + EMMELINE + ";marinière;1"
            ));

            List<Commande> commandes = analyse.chargerToutesLesCommandes("commandes");

            assertAll(
                    () -> assertEquals(3, commandes.size()),
                    () -> assertSame(emmeline, commandes.get(0).getClient()),
                    () -> assertEquals(LocalDate.of(2026, 11, 10), commandes.get(2).getDate().toLocalDate())
            );
        }

        @Test
        void chargerToutesLesCommandesLesEnregistreDansLaPizzeria() throws IOException {
            when(lecteur.lireIndex("commandes/index.txt")).thenReturn(List.of("a.csv"));
            when(lecteur.lireLignes("commandes/a.csv")).thenReturn(List.of(
                    "2026-11-09T12:15:00;" + EMMELINE + ";margarita;2",
                    "2026-11-09T19:00:00;" + STEPHANIE + ";diavola;1"
            ));

            List<Commande> commandes = analyse.chargerToutesLesCommandes("commandes");

            assertEquals(commandes, pizzeria.getCommandes());
        }

        @Test
        void chargerToutesLesCommandesContinueApresUnFichierIntrouvable() throws IOException {
            when(lecteur.lireIndex("commandes/index.txt")).thenReturn(List.of("absent.csv", "b.csv"));
            when(lecteur.lireLignes("commandes/absent.csv"))
                    .thenThrow(new FileNotFoundException("commandes/absent.csv"));
            when(lecteur.lireLignes("commandes/b.csv")).thenReturn(List.of(
                    "2026-11-10T12:00:00;" + EMMELINE + ";marinière;1"
            ));

            List<Commande> commandes = analyse.chargerToutesLesCommandes("commandes");

            assertAll(
                    () -> assertEquals(1, commandes.size()),
                    () -> assertEquals(MenuPizzeria.PIZZA_MARINIERE, commandes.get(0).getLignes().get(0).getPizza())
            );
        }

        @Test
        void chargerToutesLesCommandesPropageUnIndexIllisible() throws IOException {
            when(lecteur.lireIndex("commandes/index.txt")).thenThrow(new IOException("disque illisible"));

            assertThrows(IOException.class, () -> analyse.chargerToutesLesCommandes("commandes"));
        }

        @Test
        void chargerToutesLesCommandesSurLesVraisFichiers() throws IOException {
            AnalyseCommandes analyseReelle =
                    new AnalyseCommandes(DonneesPizzeria.creerPizzeria(), new LecteurFichiers());
            ByteArrayOutputStream console = new ByteArrayOutputStream();
            PrintStream sortieStandard = System.out;
            System.setOut(new PrintStream(console, true, StandardCharsets.UTF_8));
            int nombreDeCommandes;
            try {
                nombreDeCommandes = analyseReelle.chargerToutesLesCommandes("commandes").size();
            } finally {
                System.setOut(sortieStandard);
            }

            List<String> affichage = console.toString(StandardCharsets.UTF_8).lines().toList();
            assertAll(
                    () -> assertEquals(100, nombreDeCommandes),
                    () -> assertEquals(7, affichage.stream()
                            .filter(ligne -> ligne.startsWith("Ligne ignorée : "))
                            .count()),
                    () -> assertTrue(affichage.contains("Fichier introuvable : 2026-11-12.csv"))
            );
        }
    }

    @Nested
    @DisplayName("Séance 1, question 6 : chiffreAffairesParJour")
    class Seance1Question6 {

        @Test
        void chiffreAffairesParJourAdditionneLesCommandesDeChaqueJour() {
            troisCommandesDeDemo();

            Map<LocalDate, Double> parJour = analyse.chiffreAffairesParJour();

            double margarita = MenuPizzeria.PIZZA_MARGARITA.calculerPrix();
            double diavola = MenuPizzeria.PIZZA_DIAVOLA.calculerPrix();
            assertEquals(Map.of(
                    LocalDate.of(2026, 11, 9), 2 * margarita + diavola,
                    LocalDate.of(2026, 11, 10), margarita + 3 * diavola
            ), parJour);
        }

        @Test
        void chiffreAffairesParJourEstVideSansCommande() {
            assertTrue(analyse.chiffreAffairesParJour().isEmpty());
        }

        @Test
        void chiffreAffairesParJourSurLesVraisFichiers() throws IOException {
            Map<LocalDate, Double> parJour = analyseDesVraisFichiers().chiffreAffairesParJour();

            assertEquals(Map.of(
                    LocalDate.of(2026, 11, 9), 829.0,
                    LocalDate.of(2026, 11, 10), 773.0,
                    LocalDate.of(2026, 11, 11), 778.0
            ), parJour);
        }
    }

    @Nested
    @DisplayName("Séance 1, question 7 : pizzasVenduesParCategorie")
    class Seance1Question7 {

        @Test
        void pizzasVenduesParCategorieAdditionneLesQuantitesDeToutesLesLignes() {
            troisCommandesDeDemo();

            Map<Categorie, Integer> parCategorie = analyse.pizzasVenduesParCategorie();

            assertEquals(Map.of(Categorie.VEGETARIENNE, 3, Categorie.VIANDE, 4), parCategorie);
        }

        @Test
        void pizzasVenduesParCategorieSurLesVraisFichiers() throws IOException {
            Map<Categorie, Integer> parCategorie = analyseDesVraisFichiers().pizzasVenduesParCategorie();

            assertEquals(Map.of(Categorie.VEGETARIENNE, 78, Categorie.VIANDE, 64, Categorie.POISSON, 73), parCategorie);
        }
    }

    @Nested
    @DisplayName("Séance 1, question 8 : ecrireRapport")
    class Seance1Question8 {

        @TempDir
        Path dossierTemporaire;

        @Test
        void ecrireRapportEcritChaqueJourPuisLeTotal() throws IOException {
            troisCommandesDeDemo();
            Path rapport = dossierTemporaire.resolve("rapport.txt");

            analyse.ecrireRapport(rapport.toString());

            double margarita = MenuPizzeria.PIZZA_MARGARITA.calculerPrix();
            double diavola = MenuPizzeria.PIZZA_DIAVOLA.calculerPrix();
            double jour1 = 2 * margarita + diavola;
            double jour2 = margarita + 3 * diavola;
            assertEquals(List.of(
                    "2026-11-09 : " + jour1 + " euros",
                    "2026-11-10 : " + jour2 + " euros",
                    "Chiffre d'affaires total : " + (jour1 + jour2) + " euros"
            ), Files.readAllLines(rapport));
        }

        @Test
        void ecrireRapportSurLesVraisFichiers() throws IOException {
            Path rapport = dossierTemporaire.resolve("rapport.txt");

            analyseDesVraisFichiers().ecrireRapport(rapport.toString());

            assertEquals(List.of(
                    "2026-11-09 : 829.0 euros",
                    "2026-11-10 : 773.0 euros",
                    "2026-11-11 : 778.0 euros",
                    "Chiffre d'affaires total : 2380.0 euros"
            ), Files.readAllLines(rapport));
        }
    }

    @Nested
    @DisplayName("Séance 1, question 9 (optionnelle) : meilleurClient")
    class Seance1Question9 {

        @Test
        void meilleurClientEstCeluiQuiADepenseLePlus() {
            troisCommandesDeDemo();

            assertEquals(Optional.of(emmeline), analyse.meilleurClient());
        }

        @Test
        void meilleurClientEstVideSansCommande() {
            assertEquals(Optional.empty(), analyse.meilleurClient());
        }
    }

}
