package be.vinci.mocks.voiture;

import java.util.Objects;

/**
 * A car, identified by its brand and model.
 *
 * Equality is based on both fields: two cars of the same brand and model are the same car, which is
 * how the repository detects duplicates.
 */
public class Car {
    private String model;

    private String brand;

    /**
     * Creates a car.
     *
     * @param model the car model
     * @param brand the car brand
     */
    public Car(String model, String brand) {
        this.model = model;
        this.brand = brand;
    }

    /**
     * Returns the car model.
     *
     * @return the car model
     */
    public String getModel() {
        return model;
    }

    /**
     * Returns the car brand.
     *
     * @return the car brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Returns the brand and model of the car.
     *
     * @return a textual representation of the car
     */
    @Override
    public String toString() {
        return "Car{" +
                "model='" + model + '\'' +
                ", brand='" + brand + '\'' +
                '}';
    }

    /**
     * Compares two cars on both their model and their brand.
     *
     * @param o the object to compare with this car
     * @return true if o is a car with the same model and brand
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Car car = (Car) o;
        return Objects.equals(getModel(), car.getModel()) && Objects.equals(getBrand(), car.getBrand());
    }

    /**
     * Returns a hash code based on model and brand, consistent with equals(Object).
     *
     * @return the hash code of the car
     */
    @Override
    public int hashCode() {
        return Objects.hash(getModel(), getBrand());
    }
}
