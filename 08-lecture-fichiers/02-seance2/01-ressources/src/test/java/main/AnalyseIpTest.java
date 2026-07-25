package main;

import static org.junit.jupiter.api.Assertions.*;

import domaine.Acces;
import domaine.HasherStub;
import domaine.HyperLogLog;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

class AnalyseIpTest {

    private List<Acces> troisAccesDeuxIpDistinctes() {
        return List.of(
                new Acces("2026-11-09T08:12:44", "alice", "192.168.0.12"),
                new Acces("2026-11-09T08:15:03", "bob", "10.0.0.7"),
                new Acces("2026-11-09T08:20:51", "alice", "192.168.0.12")
        );
    }

    @Nested
    @DisplayName("Question 2 : compter les adresses IP uniques")
    class Question2 {

        @Test
        void compterIpUniquesCompteChaqueAdresseUneSeuleFois() {
            long nombre = AnalyseIp.compterIpUniques(troisAccesDeuxIpDistinctes());

            assertEquals(2, nombre);
        }
    }

    @Nested
    @DisplayName("Question 3 : estimer les adresses IP uniques")
    class Question3 {

        @Test
        void estimerIpUniquesAjouteChaqueAccesAuHyperLogLog() {
            // HasherStub renvoie toujours 0x12345678 : un seul registre change (voir 07B_1_theorie.md),
            // l'estimation est donc déterministe, quel que soit le nombre d'accès ajoutés.
            HyperLogLog hyperLogLog = new HyperLogLog(new HasherStub(0x12345678), 4);

            long estimation = AnalyseIp.estimerIpUniques(troisAccesDeuxIpDistinctes(), hyperLogLog);

            assertEquals(11, estimation);
        }
    }

    @Nested
    @DisplayName("Question 4 : une estimation par jour")
    class Question4 {

        private List<Acces> accesSurDeuxJours() {
            return List.of(
                    new Acces("2026-11-09T08:12:44", "alice", "192.168.0.12"),
                    new Acces("2026-11-10T07:59:12", "david", "10.0.0.8")
            );
        }

        @Test
        void estimerIpUniquesParJourRegroupeParJourDHorodatage() {
            Map<String, Long> estimations = AnalyseIp.estimerIpUniquesParJour(
                    accesSurDeuxJours(), new HasherStub(0x12345678));

            assertEquals(List.of("2026-11-09.log", "2026-11-10.log"), List.copyOf(estimations.keySet()));
        }

        @Test
        void estimerIpUniquesParJourEstimeChaqueJourIndependamment() {
            // un seul accès par jour, HasherStub fixe : même estimation déterministe des deux côtés
            Map<String, Long> estimations = AnalyseIp.estimerIpUniquesParJour(
                    accesSurDeuxJours(), new HasherStub(0x12345678));

            assertEquals(Map.of("2026-11-09.log", 11L, "2026-11-10.log", 11L), estimations);
        }
    }

}
