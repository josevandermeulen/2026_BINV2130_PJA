package be.vinci.mocks.voiture;

import java.util.List;

/**
 * The business layer above CarRepository: it holds the rules the repository does not.
 *
 * The repository stores whatever it is given; this service is what refuses invalid or duplicate
 * cars, and what registers an unknown brand before storing a car that uses it.
 */
public class CarService {
    private final CarRepository repository;

    /**
     * Creates a service backed by the given repository.
     *
     * @param repository the repository holding cars and brands; must not be null
     * @throws IllegalArgumentException if repository is null
     */
    public CarService(CarRepository repository) {
        if (repository == null) {
            throw new IllegalArgumentException("repository cannot be null");
        }
        this.repository = repository;
    }

    /**
     * Returns every known car.
     *
     * @return the list of cars held by the repository
     */
    public List<Car> getAllCars() {
        return repository.findAllCars();
    }

    /**
     * Returns every known brand.
     *
     * @return the list of brands held by the repository
     */
    public List<String> getAllBrands() {
        return repository.getBrands();
    }

    /**
     * Adds a car to the repository.
     *
     * The car is added only if it does not already exist in the repository.
     * A car is considered identical if another car with the same brand and model
     * is already present.
     *
     * If the car's brand is not yet registered in the repository, it is added
     * automatically before adding the car.
     *
     * @param car the car to add; must not be `null`. Its brand and model
     *            must be non-null and non-empty.
     * @return `true` if the car was successfully added, `false` otherwise
     * @throws IllegalArgumentException if `car` is `null`, if its brand
     *         or model is `null` or empty, or if a car with the same brand
     *         and model already exists in the repository
     */
    public boolean addCar(Car car) {
        if (car == null) {
            throw new IllegalArgumentException("car cannot be null");
        }
        if (car.getBrand() == null) {
            throw new IllegalArgumentException("car brand cannot be null");
        }
        if (car.getBrand().isEmpty()) {
            throw new IllegalArgumentException("car brand name cannot be empty");
        }
        if (car.getModel() == null) {
            throw new IllegalArgumentException("car model cannot be null");
        }
        if (car.getModel().isEmpty()) {
            throw new IllegalArgumentException("car model cannot be empty");
        }

        if (getAllCars().stream()
                .anyMatch(c -> c.getModel().equals(car.getModel()) && c.getBrand().equals(car.getBrand()))) {
            throw new IllegalArgumentException("car already exists");
        }
        if (getAllBrands().stream().noneMatch(b -> b.equals(car.getBrand()))) {
            repository.addBrand(car.getBrand());
        }
        return repository.addCar(car);
    }
}
