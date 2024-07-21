package Patterns.Factory;

public class VehicleFactory {
    public Vehicle createVehicle(String type) {
        if (type.equals("Car")) {
            return new Car();
        } else if (type.equals("Bike")) {
            return new Bike();
        } else {
            throw new IllegalArgumentException("Unknown vehicle type: " + type);
        }
    }
}
