import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) {
        try {
            StudentImpl stub = new StudentImpl();
            Registry registry = LocateRegistry.createRegistry(9100);
            registry.rebind("student", stub);
            
            System.out.println("Server is up!");
        } catch (Exception e) {
            System.out.println("Error server:");
            e.printStackTrace();
        }
    }
}
