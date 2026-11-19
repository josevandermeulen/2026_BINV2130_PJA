package deadlock;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

class GestionnaireTransfertsTest {

    @Nested
    @DisplayName("Question 14 : correction du deadlock")
    class Question14 {

        @Test
        @Timeout(value = 10, unit = TimeUnit.SECONDS)
        void transfertsConcurrentsEnSensInverseSansDeadlock() throws InterruptedException {
            Compte compteA = new Compte(1, 1000);
            Compte compteB = new Compte(2, 1000);
            GestionnaireTransferts gestionnaire = new GestionnaireTransferts();
            int nbTransferts = 200;

            Thread versB = new Thread(() -> {
                for (int i = 0; i < nbTransferts; i++) {
                    gestionnaire.transferer(compteA, compteB, 1);
                }
            });
            Thread versA = new Thread(() -> {
                for (int i = 0; i < nbTransferts; i++) {
                    gestionnaire.transferer(compteB, compteA, 1);
                }
            });

            versB.start();
            versA.start();
            versB.join();
            versA.join();

            assertAll(
                    () -> assertEquals(1000, compteA.getSolde()),
                    () -> assertEquals(1000, compteB.getSolde())
            );
        }
    }

}
