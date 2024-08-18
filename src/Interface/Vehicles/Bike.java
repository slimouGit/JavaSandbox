package Interface.Vehicles;

public class Bike implements MyVehicle {
    @Override
    public void moveVehicle(String message) {
        System.out.println("Bike: " + message);

    }
}
