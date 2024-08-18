package Interface.Vehicles;

public class Application {
    private MyVehicle vehicle;

    public Application(MyVehicle vehicle) {
        this.vehicle = vehicle;
    }

    public void drive() {
        vehicle.moveVehicle("Driving");
    }

    public static void main(String[] args) {
        MyVehicle car = new Car();
        MyVehicle bike = new Bike();

        Application carApp = new Application(car);
        Application bikeApp = new Application(bike);

        carApp.drive();
        bikeApp.drive();
    }
}
