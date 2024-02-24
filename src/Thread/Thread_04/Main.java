package Thread.Thread_04;

public class Main {
    public static void main(String[] args) {
        printHelloWorld();
    }
    public static void printHelloWorld() {
        for (int i = 0; i < 10; i++) {
            System.out.println("Hello World");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

