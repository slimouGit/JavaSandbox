package Patterns.Singleton;

public class Main {
    public static void main(String[] args) {
        Logger logger1 = Logger.getInstance();
        Logger logger2 = Logger.getInstance();

        logger1.log("This is a log message.");
        logger2.log("This is another log message.");

        System.out.println(logger1 == logger2);  // true, da beide die gleiche Instanz sind
    }
}
