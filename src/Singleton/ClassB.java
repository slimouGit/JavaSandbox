package Singleton;

public class ClassB {
    public void addErrorMessage() {
        ErrorManager.getInstance().addErrorMessage("Error message from ClassB");
    }
}
