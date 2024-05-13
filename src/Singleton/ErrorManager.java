package Singleton;

import java.util.ArrayList;
import java.util.List;

public class ErrorManager {
    private static ErrorManager instance;
    private List<String> errorMessages;

    private ErrorManager() {
        errorMessages = new ArrayList<>();
    }

    public static ErrorManager getInstance() {
        if (instance == null) {
            instance = new ErrorManager();
        }
        return instance;
    }

    public void addErrorMessage(String message) {
        errorMessages.add(message);
    }

    public void printErrorMessages() {
        for (String message : errorMessages) {
            System.out.println(message);
        }
    }
}
