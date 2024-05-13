package Singleton;

public class Main {
    public static void main(String[] args) {
        ClassA classA = new ClassA();
        classA.addErrorMessage();

        ClassB classB = new ClassB();
        classB.addErrorMessage();

        ErrorManager.getInstance().printErrorMessages();
    }
}
