package be.vinci.mocks.voiture;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CarServiceTest {

    // Question 9 : examen janvier — TC1 et TC2, sans mock
    @Nested
    @DisplayName("Sans mock")
    class SansMock {

        // TC1 (sans mock) : marque null -> IllegalArgumentException
        @Test
        void testAddCarTC1() {
            CarService service = new CarService(new CarRepository());
            Car voitureSansMarque = new Car("Camry", null);
            assertThrows(IllegalArgumentException.class, () -> service.addCar(voitureSansMarque));
        }

        // TC2 (sans mock) : marque déjà existante -> succès, voiture présente dans le carService
        @Test
        void testAddCarTC2() {
            CarService service = new CarService(new CarRepository());
            Car voiture = new Car("Yaris", "Toyota");
            assertAll(() -> assertTrue(service.addCar(voiture)),
                      () -> assertTrue(service.getAllCars().contains(voiture))
            );
        }
    }

    // Question 9 : TC3 et TC4, avec mock du CarRepository (verify times/never)
    @Nested
    @DisplayName("Avec mock du CarRepository")
    class AvecMock {

        // TC3 (avec mock) : marque pas encore présente -> addBrand appelée une fois, addCar appelée une fois
        @Test
        void testAddCarTC3() {
            CarRepository repository = Mockito.mock(CarRepository.class);
            Mockito.when(repository.findAllCars()).thenReturn(List.of());
            Mockito.when(repository.getBrands()).thenReturn(List.of("Toyota"));
            CarService service = new CarService(repository);

            Car voiture = new Car("Model 3", "Tesla");
            service.addCar(voiture);
            assertAll(() -> Mockito.verify(repository, Mockito.times(1)).addBrand("Tesla"),
                      () -> Mockito.verify(repository, Mockito.times(1)).addCar(voiture)
            );
        }

        // TC4 (avec mock) : marque déjà présente -> addBrand jamais appelée, addCar appelée une fois
        @Test
        void testAddCarTC4() {
            CarRepository repository = Mockito.mock(CarRepository.class);
            Mockito.when(repository.findAllCars()).thenReturn(List.of());
            Mockito.when(repository.getBrands()).thenReturn(List.of("Toyota"));
            CarService service = new CarService(repository);

            Car voiture = new Car("Yaris", "Toyota");
            service.addCar(voiture);
            assertAll(() -> Mockito.verify(repository, Mockito.never()).addBrand(Mockito.any()),
                      () -> Mockito.verify(repository, Mockito.times(1)).addCar(voiture)
            );
        }
    }
}
