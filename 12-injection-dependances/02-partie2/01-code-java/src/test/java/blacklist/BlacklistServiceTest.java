package blacklist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import domaine.Query;
import domaine.Query.QueryMethod;
import domaine.QueryFactory;
import domaine.QueryFactoryImpl;

public class BlacklistServiceTest {

    private final QueryFactory queryFactory = new QueryFactoryImpl();

    private Query queryVers(String url) {
        Query query = queryFactory.getQuery();
        query.setMethod(QueryMethod.GET);
        query.setUrl(url);
        return query;
    }

    @Nested
    @DisplayName("Question 1 : fichier blacklist.properties")
    class Question1 {

        @Test
        @DisplayName("Le fichier existe à la racine du projet")
        void fichierPresentALaRacine() {
            assertTrue(Files.exists(Path.of("blacklist.properties")));
        }

        @Test
        @DisplayName("La clé blacklistedDomains contient exactement les domaines attendus")
        void cleBlacklistedDomains() throws IOException {
            assertTrue(Files.exists(Path.of("blacklist.properties")),
                    "blacklist.properties doit exister à la racine du projet (Question 1)");
            Properties properties = new Properties();
            try (FileInputStream input = new FileInputStream("blacklist.properties")) {
                properties.load(input);
            }
            assertEquals("google.be;facebook.com;instagram.com",
                    properties.getProperty("blacklistedDomains"));
        }

    }

    @Nested
    @DisplayName("Question 2 : BlacklistService")
    class Question2 {

        private final BlacklistService blacklistService = new BlacklistServiceImpl();

        @Test
        @DisplayName("URL contenant un domaine blacklisté détectée")
        void domaineBlacklisteDetecte() {
            assertTrue(blacklistService.check(queryVers("https://google.be/search?q=java")));
        }

        @Test
        @DisplayName("Chaque domaine du fichier est pris en compte")
        void tousLesDomainesPrisEnCompte() {
            assertTrue(blacklistService.check(queryVers("https://facebook.com/")));
            assertTrue(blacklistService.check(queryVers("https://instagram.com/")));
        }

        @Test
        @DisplayName("URL d'un domaine autorisé acceptée")
        void domaineAutoriseAccepte() {
            assertFalse(blacklistService.check(queryVers("https://openjdk.org/")));
        }

    }

}
