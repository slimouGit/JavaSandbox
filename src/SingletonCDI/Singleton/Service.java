package SingletonCDI.Singleton;

import javax.inject.Inject;

public class Service {

    @Inject
    private ErrorManager errorManager;

    public void printMessages() {
        ErrorManager.getInstance().printErrorMessages();

}
}
