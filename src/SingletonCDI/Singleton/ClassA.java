package SingletonCDI.Singleton;

import javax.inject.Inject;

public class ClassA {

    @Inject
    private ErrorManager errorManager;
    public void addErrorMessage() {
        errorManager.addErrorMessage("Error message from ClassA");
    }
}
