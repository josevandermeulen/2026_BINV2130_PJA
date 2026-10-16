package be.vinci.mocks.club;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EquipmentManagerTest {

    // Question 10 : examen septembre — TC1 et TC2, sans mock
    @Nested
    @DisplayName("Sans mock")
    class SansMock {

        // TC1 (sans mock) : nom de membre vide -> IllegalArgumentException
        @Test
        void testAddEquipmentTC1() {
            Equipment ballon = new Equipment("BALL01", "Ballon");
            EquipmentManager manager = new EquipmentManager(new ClubStock(List.of(ballon)));
            assertThrows(IllegalArgumentException.class, () -> manager.addEquipment("BALL01", ""));
        }

        // TC2 (sans mock) : équipement disponible -> succès, objet plus empruntable
        @Test
        void testAddEquipmentTC2() {
            Equipment ballon = new Equipment("BALL01", "Ballon");
            EquipmentManager manager = new EquipmentManager(new ClubStock(List.of(ballon)));
            assertAll(() -> assertTrue(manager.addEquipment("BALL01", "Alice")),
                      () -> assertTrue(ballon.isBorrowed())
            );
        }
    }

    // Question 10 : TC3 et TC4, avec mock du ClubStock (verify times/never)
    @Nested
    @DisplayName("Avec mock du ClubStock")
    class AvecMock {

        // TC3 (avec mock) : équipement disponible -> markAsBorrowed appelée une fois avec les bons arguments
        @Test
        void testAddEquipmentTC3() {
            ClubStock stock = Mockito.mock(ClubStock.class);
            Equipment ballon = new Equipment("BALL01", "Ballon");
            Mockito.when(stock.findByCode("BALL01")).thenReturn(ballon);
            EquipmentManager manager = new EquipmentManager(stock);

            assertAll(() -> assertTrue(manager.addEquipment("BALL01", "Alice")),
                      () -> Mockito.verify(stock, Mockito.times(1)).markAsBorrowed(ballon, "Alice")
            );
        }

        // TC4 (avec mock) : équipement déjà emprunté -> échec, markAsBorrowed jamais appelée
        @Test
        void testAddEquipmentTC4() {
            ClubStock stock = Mockito.mock(ClubStock.class);
            Equipment ballon = new Equipment("BALL01", "Ballon");
            ballon.setBorrowed(true);
            Mockito.when(stock.findByCode("BALL01")).thenReturn(ballon);
            EquipmentManager manager = new EquipmentManager(stock);

            assertAll(() -> assertFalse(manager.addEquipment("BALL01", "Alice")),
                      () -> Mockito.verify(stock, Mockito.never()).markAsBorrowed(Mockito.any(), Mockito.any())
            );
        }
    }
}
