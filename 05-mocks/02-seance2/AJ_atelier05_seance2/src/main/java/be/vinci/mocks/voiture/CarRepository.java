package be.vinci.mocks.voiture;


import java.util.ArrayList;
import java.util.List;

/**
 * A stand-in data source for cars and brands.
 *
 * Every method deliberately sleeps before answering, to imitate a slow database. That delay is the
 * reason tests must mock this class rather than use it: it is what makes the difference between a
 * suite that runs instantly and one that takes seconds.
 */
public class CarRepository {
    private final static int SLEEP_TIME = 500;

    private List<String> brands = new ArrayList<>(List.of("Toyota", "Honda"));

    private List<String> cars = new ArrayList<>(List.of("Camry, Toyota",
            "Civic, Honda"));

    /**
     * Returns every car known to the repository.
     *
     * @return the list of stored cars
     */
    public List<Car> findAllCars() {
        // Simule l'accès à une base de données ou une autre source de données
        try {
            Thread.sleep(SLEEP_TIME);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return parseCars();
    }

    /**
     * Déserialiser tous les véhicules (de la fake DB)
     * et les transformer en objets Car
     */
    private List<Car> parseCars() {
        return cars.stream()
                .map(car -> {
                    String[] parts = car.split(",");
                    return new Car(parts[0].trim(), parts[1].trim());
                })
                .toList();

    }

    /**
     * Returns every brand known to the repository.
     *
     * @return the list of stored brands
     */
    public List<String> getBrands() {
        // Simule l'accès à une base de données ou une autre source de données
        try {
            Thread.sleep(SLEEP_TIME);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return brands;
    }

    /**
     * Registers a new brand.
     *
     * @param brand the brand to register
     * @return the brand that was registered
     */
    public String addBrand(String brand) {
        // Simule l'accès à une base de données ou une autre source de données

        try {
            Thread.sleep(SLEEP_TIME);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        brands.add(brand);
        return brand;
    }

    /**
     * Stores a car, without checking for duplicates — that check belongs to CarService.
     *
     * @param car the car to store
     * @return true if the car was stored
     */
    public boolean addCar(Car car) {
        // Simule l'accès à une base de données ou une autre source de données
        try {
            Thread.sleep(SLEEP_TIME);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return cars.add(serializeCar(car));
    }

    /**
     * Sérialiser une voiture pour la stocker dans la fake database
     */
    private String serializeCar(Car car) {
        return car.getModel() + ", " + car.getBrand();
    }

}

