package Interface.Logger;

public class Application {
    private Logger logger;

    public Application(Logger logger) {
        this.logger = logger;
    }

    public void performAction() {
        logger.log("Action performed");
    }

    public static void main(String[] args) {
        Logger consoleLogger = new ConsoleLogger();
        Application app = new Application(consoleLogger);
        app.performAction();

        Logger fileLogger = new FileLogger();
        app = new Application(fileLogger);
        app.performAction();
    }
}
