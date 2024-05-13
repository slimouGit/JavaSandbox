package Singleton;

public class ClassA {
    public void addErrorMessage() {
        ErrorManager.getInstance().addErrorMessage("Error message from ClassA");
    }
}
