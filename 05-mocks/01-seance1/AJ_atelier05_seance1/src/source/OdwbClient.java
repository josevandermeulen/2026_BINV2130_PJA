package source;

import domaine.Bibliotheque;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Source de production : interroge l'API Explore d'ODWB (Open Data Wallonie-Bruxelles) par
 * Internet et renvoie les bibliothèques publiques de la Fédération Wallonie-Bruxelles.
 *
 * Données : Fédération Wallonie-Bruxelles, jeu « Bibliothèques publiques en FWB + Fonds
 * spécialisés », publié sur https://www.odwb.be sous licence CC BY.
 *
 * Chaque appel à rechercher part sur le réseau : il est lent (de l'ordre d'une seconde), il échoue
 * sans connexion et sa réponse change quand le jeu de données est mis à jour. C'est pour cela
 * qu'on ne l'utilise jamais dans les tests unitaires.
 *
 * L'API renvoie un export CSV : une ligne d'en-tête, puis une ligne par bibliothèque, champs
 * séparés par `;`. Un champ qui contient un `;` ou un guillemet est entouré de guillemets, et ses
 * guillemets sont doublés. Le texte commence par une marque d'ordre des octets (BOM), à ignorer.
 *
 * Classe fournie : vous n'avez pas à la modifier.
 */
public class OdwbClient implements SourceBibliotheques {

    private static final String URL_EXPORT = "https://www.odwb.be/api/explore/v2.1/catalog/datasets/"
            + "bibliotheques-publiques-en-fwb-fonds-specialises/exports/csv";

    private static final String COLONNES = "denomination,province,cp,ville,categorie,"
            + "thematiques_de_recherche,fonds_specialises,geolocalisation";

    private static final int NOMBRE_DE_COLONNES = 8;

    private static final Duration DELAI_MAXIMUM = Duration.ofSeconds(10);

    private final HttpClient client;

    /**
     * Crée un client pour l'API d'ODWB.
     */
    public OdwbClient() {
        this.client = HttpClient.newBuilder().connectTimeout(DELAI_MAXIMUM).build();
    }

    /**
     * Interroge l'API d'ODWB.
     *
     * @param filtre un filtre au format de l'API, par exemple `cp="5000"`, ou une chaîne vide
     * @return les bibliothèques renvoyées par l'API
     * @throws SourceIndisponibleException si l'API ne répond pas, répond par une erreur ou par un
     *                                     contenu illisible, ou si la connexion est interrompue
     */
    @Override
    public List<Bibliotheque> rechercher(String filtre) {
        String url = URL_EXPORT + "?select=" + encoder(COLONNES);
        if (filtre != null && !filtre.isBlank()) {
            url += "&where=" + encoder(filtre);
        }
        HttpRequest requete = HttpRequest.newBuilder(URI.create(url)).timeout(DELAI_MAXIMUM).GET().build();

        try {
            HttpResponse<String> reponse = client.send(requete, HttpResponse.BodyHandlers.ofString());
            if (reponse.statusCode() != 200) {
                throw new SourceIndisponibleException("ODWB a répondu par le code HTTP " + reponse.statusCode());
            }
            return lireCsv(reponse.body());
        } catch (IOException e) {
            throw new SourceIndisponibleException("ODWB est injoignable", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new SourceIndisponibleException("L'appel à ODWB a été interrompu", e);
        }
    }

    private static String encoder(String texte) {
        return URLEncoder.encode(texte, StandardCharsets.UTF_8);
    }

    private static List<Bibliotheque> lireCsv(String contenu) {
        if (contenu.startsWith("﻿")) {
            contenu = contenu.substring(1);
        }

        List<Bibliotheque> bibliotheques = new ArrayList<>();
        String[] lignes = contenu.split("\r?\n");
        for (int i = 1; i < lignes.length; i++) {
            if (!lignes[i].isBlank()) {
                bibliotheques.add(lireLigne(lignes[i]));
            }
        }
        return bibliotheques;
    }

    private static Bibliotheque lireLigne(String ligne) {
        List<String> champs = decouper(ligne);
        if (champs.size() != NOMBRE_DE_COLONNES) {
            throw new SourceIndisponibleException("Ligne CSV mal formée : " + ligne);
        }

        String[] coordonnees = champs.get(7).split(",");
        try {
            double latitude = Double.parseDouble(coordonnees[0].trim());
            double longitude = Double.parseDouble(coordonnees[1].trim());
            return new Bibliotheque(champs.get(0), champs.get(1), champs.get(2), champs.get(3),
                    champs.get(4), champs.get(5), champs.get(6), latitude, longitude);
        } catch (RuntimeException e) {
            throw new SourceIndisponibleException("Ligne CSV mal formée : " + ligne, e);
        }
    }

    private static List<String> decouper(String ligne) {
        List<String> champs = new ArrayList<>();
        StringBuilder champ = new StringBuilder();
        boolean entreGuillemets = false;

        for (int i = 0; i < ligne.length(); i++) {
            char c = ligne.charAt(i);
            if (entreGuillemets) {
                if (c == '"' && i + 1 < ligne.length() && ligne.charAt(i + 1) == '"') {
                    champ.append('"');
                    i++;
                } else if (c == '"') {
                    entreGuillemets = false;
                } else {
                    champ.append(c);
                }
            } else if (c == '"') {
                entreGuillemets = true;
            } else if (c == ';') {
                champs.add(champ.toString());
                champ.setLength(0);
            } else {
                champ.append(c);
            }
        }
        champs.add(champ.toString());
        return champs;
    }
}
