package Patterns.Factory;

public class Main {
    public static void main(String[] args) {
        VehicleFactory factory = new VehicleFactory();

        Vehicle car = factory.createVehicle("Car");
        car.drive();  // Driving a car.

        Vehicle bike = factory.createVehicle("Bike");
        bike.drive();  // Riding a bike.
    }
}
