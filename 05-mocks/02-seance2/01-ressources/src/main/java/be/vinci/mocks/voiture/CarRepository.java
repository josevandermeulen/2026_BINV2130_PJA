package be.vinci.mocks.voiture;


import java.util.ArrayList;
import java.util.List;

public class CarRepository {
    private final static int SLEEP_TIME = 500;

    private List<String> brands = new ArrayList<>(List.of("Toyota", "Honda"));

    private List<String> cars = new ArrayList<>(List.of("Camry, Toyota",
            "Civic, Honda"));

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

    public List<String> getBrands() {
        // Simule l'accès à une base de données ou une autre source de données
        try {
            Thread.sleep(SLEEP_TIME);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return brands;
    }

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

