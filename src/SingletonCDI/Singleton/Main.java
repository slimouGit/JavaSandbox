import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

public class Main {
    public static void main(String[] args) {
        Weld weld = new Weld();
        WeldContainer container = weld.initialize();

        ClassA classA = container.select(ClassA.class).get();
        classA.addErrorMessage();

        ClassB classB = container.select(ClassB.class).get();
        classB.addErrorMessage();

        Service service = container.select(Service.class).get();
        service.printMessages();

        weld.shutdown();
    }
}
