package Interface.Vehicles;

public class Car implements MyVehicle{
    @Override
    public void moveVehicle(String message) {
        System.out.println("Car: " + message);
    }
}
